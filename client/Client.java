package client;


import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.camera.Camera;
import client.camera.image.CaptureImage;
import client.gui.Window;
import client.gui.WindowVideoChat;
import client.net.packets.Packet;
import client.net.packets.Packet00Disconnect;
import client.net.packets.Packet01Connect;
import client.net.packets.Packet02Message;
import client.net.socket.tcp.SocketTCP;
import client.net.socket.udp.SocketUDP;
import client.util.YamlUtil;

public class Client {
	
	final static String STYLE_USER = "style='color:#00CC00'";
	final static String STYLE_PEER = "style='color:#3333CC'";
	final static String STYLE_TIME = "style='color:#A0A0A0'";
	
	final static File CFG_FILE = new File("cfg/client.cfg");
	final static Logger log = LoggerFactory.getLogger(Client.class);
	
	private String username;
	
	private Window clientWindow;
	
	private static Map<String, Object> cfgs;
	

	private Camera camera;
	
	private Socket primarySocket;
	private InetAddress ipAddress;
	private int port;
	
	private SocketTCP tcp;
	private SocketUDP udp;
	
	private BufferedReader input;
	
	private Thread receiveTCP, receiveUDP;

	private Packet packet;
	
	public Client(Window clientWindow, String name) {
		this.clientWindow = clientWindow;
		
		this.username = name;
		
		init();
		
		tcp = new SocketTCP(ipAddress, port);

		
	}
	
	public void disconnect() {
		try {
			packet = new Packet00Disconnect(username);

			tcp.sendMessage(packet.returnMessage());

		} catch (IOException e) {
			log.info("Error while sending disconnecting packet to server. {}", e.getMessage());
		}
	}
	
	public void sendMessage(String message) {
		packet = new Packet02Message(username, reformatMessage(message));
		try {
			tcp.sendMessage(packet.returnMessage());
			
		} catch (IOException e) {
			log.warn("Could not send message to server! {}", e.getMessage());
		}
	}
	
	public void recieveMessages() {
		log.info("Connected! Recieving messages from server!");
		while (primarySocket.isConnected()) {
			String message;
			try {
				message = tcp.recieveMessage(input);

				if (message != null) {
					parseMessage(message);
				}
			} catch (IOException e) {
				log.warn("Cannot recieve message from server! Error message: {}", e.getMessage());
			}
		}
	}

	public void receiveUDP(int dataLenght) {
		System.out.println("UDP Server started! Ready to receive messages!");
		while (true) {
			try {
				System.out.println("UDP: " + udp.receiveMessage(dataLenght));
			} catch (IOException e) {
				log.warn("Cannot recieve message from server! Error message: {}", e.getMessage());
			}
		}
	}
	
	public void startVideoChat() {
		System.out.println("Starting video chat");

		WindowVideoChat vChat = new WindowVideoChat("Chat", 640, 480);
		
		camera = new Camera(640, 480, 0);
		CaptureImage capture = new CaptureImage(camera.getCamera(), camera.getCameraWidth(), camera.getCameraHeight());
		
		
		receiveUDP = new Thread() {
			public void run() {
				int repeat = 1000;
				while (repeat > 0) {
					repeat--;
					BufferedImage image = capture.bgr2Rgb(capture.mat2BufferedImage(capture.takePhoto()));
					vChat.updateDisplay(image);
					
				}
				camera.stopCamera();
				System.out.println(camera.getCameraHeight());
			}
		};
		
		receiveUDP.start();
	
	}


	private void parseMessage(String message) throws IOException {
		String parts[] = message.split(",");
		
		String code = parts[0];
		String time = parts[1];
		String username = parts[2];
		
		String text = "";
		if (parts.length > 3) {
			text = parts[3];
		}
		
		addMessageByCode(code, time, username, text);
	}
	
	
	public void addMessageByCode(String code, String time, String username, String text) {
		
		String user_style = username.equalsIgnoreCase(this.username) ? STYLE_USER : STYLE_PEER;
		
		switch(code) {
			case "00" :
				clientWindow.addToHistory("<span " + STYLE_TIME + ">[" + time + "]</span> <span " + user_style + ">" + username + "</span> is disconnected!");
				break;
			case "01" :
				clientWindow.addToHistory("<span " + STYLE_TIME + ">[" + time + "]</span> <span " + user_style + ">" + username + "</span> is connected!");
				break;
			case "02" :
				clientWindow.addToHistory("<span " + STYLE_TIME + ">[" + time + "]</span> <span " + user_style + ">" + username + "</span>: " + restoreMessageText(text));
				break;
		}
	}
	
	
	private String restoreMessageText(String message) {
		return message.replace(";", ",");
	}
	
	private String reformatMessage(String message) {
		return message.replace(",", ";") + "\n";
	}
	
	
	private Map<String, Object> loadCfg(File cfgFile) {
		Map<String, Object> cfgs = new HashMap<String, Object>();
		try {
			cfgs = new YamlUtil(CFG_FILE).loadYaml();
		} catch (FileNotFoundException e) {
			log.error("Can't load cfg file! {}", e.getMessage());
			clientWindow.displayMessage("Could not load config file!", "Missing cfg file!");
			System.exit(0);
		}
		
		return cfgs; 
	}
	
	private void init() {
		try {
			cfgs = loadCfg(CFG_FILE);

			this.port = Integer.parseInt(cfgs.get("port") + ""); 
			this.ipAddress = InetAddress.getByName(cfgs.get("ipAddress") + "");

		} catch (NumberFormatException e) {
			log.error("Invalid server port value.");
			System.exit(2);
		} catch (UnknownHostException e) {
			log.error("Unknown host {}. Error message: ", ipAddress, e.getMessage());
			System.exit(3);
		}
		

		try {
			udp = new SocketUDP(ipAddress, port);
		} catch (SocketException e) {
			log.warn("Cannot connect to server (UDP with {}:{}. {}", ipAddress, port, e.getMessage());
		}

		
		try {
			primarySocket = new Socket(ipAddress, port);
			input = new BufferedReader(new InputStreamReader(primarySocket.getInputStream()));
			packet = new Packet01Connect(username);
			packet.send(new DataOutputStream(primarySocket.getOutputStream()));
		}  catch (IOException e) {
			log.warn("Cannot estabilish connection to {}:{}.", ipAddress, port);
			clientWindow.displayMessage(cfgs.get("couldNotConnect") + "", "No connection");
			System.exit(4);
		} 
		

		receiveTCP = new Thread("TCP receiver") {
			public void run() {
				recieveMessages();
			}
		};
		

		receiveTCP.start();						

	}

}
