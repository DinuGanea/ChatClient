package client.net.socket.tcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class SocketTCP {
	
	private Socket socket;

	private InetAddress ipAddress;
	private int port;
	
	
	public SocketTCP(InetAddress ipAddress, int port) {
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
	public void sendMessage(String message) throws IOException {
		socket = new Socket(ipAddress, port);
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		out.writeBytes(message);
		out.close();
		socket.close();
	}
	
	
	public String recieveMessage(BufferedReader input) throws IOException {
		String text = input.readLine();
		return text;
	}
	
}
