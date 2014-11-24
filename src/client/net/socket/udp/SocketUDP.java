package client.net.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class SocketUDP {
	
	private DatagramSocket socket;
	private byte[] sendData;
	private byte[] receiveData;
	
	private InetAddress ip;
	private int port;
	
	public SocketUDP(InetAddress ip, int port) throws SocketException {
		socket = new DatagramSocket();
		this.ip = ip;
		this.port = port;
	}
	
	public void sendMessage(String message, int dataLength) throws IOException {
		sendData = new byte[dataLength];
		sendData = message.getBytes();
		
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, port);  
		
		socket.send(sendPacket);
	}
	
	public String receiveMessage(int dataLength) throws IOException {
		receiveData = new byte[dataLength];
		
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);   
				
		socket.receive(receivePacket);
		
		System.out.println("received");
		
		return new String(receivePacket.getData());
	}
	
	public void closeConnection() {
		socket.close();
	}
	
}
