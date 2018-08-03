package com.fish.server.websocket.bean;

import java.util.HashMap;
import java.util.Map;

import com.corundumstudio.socketio.SocketIOClient;
import com.fish.server.web.bean.user.User;
import com.fish.server.websocket.session.SessionService;

public class SessionUser {

	private String account;

	public String getAccount() {
		return account;
	}


	public void setAccount(String account) {
		this.account = account;
	}

	private SocketIOClient client;

	private User user;

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	
	public void setClient(SocketIOClient client) {
		// TODO Auto-generated method stub
		this.client = client;
	}

	public SocketIOClient getClient() {
		// TODO Auto-generated method stub
		return this.client;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
