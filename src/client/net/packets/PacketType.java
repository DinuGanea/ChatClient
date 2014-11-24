package client.net.packets;

public enum PacketType {
	
	CONNECT("01"), DISCONNECT("00"), MESSAGE("02");
	
	private String code;
	
	private PacketType(String code) {
		this.code = code;
	}
	
	public String sayCode() {
		return code;
	}
	
}
