package com.fish.server.websocket.event;

public class CommonEvent {
	public Object data;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public CommonEvent(String type) {
		// TODO Auto-generated constructor stub
		this.type = type;
	}

	public static final String REMOVE_APPLY_USER = "REMOVE_APPLY_USER";

}
