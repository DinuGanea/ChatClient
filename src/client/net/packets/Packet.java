package client.net.packets;

import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Packet {
	
	protected PacketType type;
	protected String username;
	protected String message;
	protected String code;
	
	public Packet(PacketType type) {
		this.type = type;
	}
	
	public Packet(PacketType type, String username, String message) {
		this.type = type;
		this.username = username;
		this.message = message;
	}
	
	public abstract String returnMessage();
	
	public void send(DataOutputStream out) throws IOException {
		out.writeBytes(this.returnMessage() + "\n");
	}
		
}
