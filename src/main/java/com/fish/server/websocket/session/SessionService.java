package com.fish.server.websocket.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.corundumstudio.socketio.SocketIOClient;
import com.fish.server.web.bean.user.User;
import com.fish.server.websocket.base.key.BaseKey;
import com.fish.server.websocket.base.key.CmdConst;
import com.fish.server.websocket.base.key.SessionKey;
import com.fish.server.websocket.bean.DataObj;
import com.fish.server.websocket.bean.SessionUser;

public class SessionService {

	// 客户端暂存
	private static HashMap<String, SessionUser> client_cache = new HashMap<String, SessionUser>();

	public static SessionUser addClient(SocketIOClient client) {
		String account = client.get(SessionKey.SESSION_ACCOUNT);
		if (StringUtils.isBlank(account))
			return null;

		SessionUser user = getClient(account);
		if (user == null) {
			user = new SessionUser();
			user.setAccount(account);
			user.setClient(client);
		}
		client_cache.put(account, user);

		return user;
	}

	public static SessionUser getClient(String account) {
		// TODO Auto-generated method stub
		if (account != null)
			return client_cache.get(account);
		return null;
	}

	public static Object[] getClientAccounts() {

		return client_cache.keySet().toArray();

	}

	public static void removeClient(SocketIOClient client) {

		client_cache.remove(client.get(SessionKey.SESSION_ACCOUNT));
	}

	public static void sendErrorEvent(SocketIOClient client, String error) {
		// TODO Auto-generated method stub
		client.sendEvent(BaseKey.ERROR, error);
	}

	public static SessionUser getClient(SocketIOClient client) {
		String account = getAccount(client);
		return getClient(account);
	}

	public static void sendDataToClient(String account, DataObj data) {
		// TODO Auto-generated method stub
		SessionUser user = getClient(account);
		if (user != null) {
			SocketIOClient client = user.getClient();
			if (client != null && client.isChannelOpen())
				client.sendEvent(BaseKey.DATA_EVENT, data);
		}
	}

	public static void sendDataToClient(SocketIOClient client, DataObj data) {
		// TODO Auto-generated method stub

		if (client != null && client.isChannelOpen())
			client.sendEvent(BaseKey.DATA_EVENT, data);
	}

	public static String getAccount(SocketIOClient client) {
		// TODO Auto-generated method stub
		String account = "";
		try {
			if (client != null)
				account = client.get(SessionKey.SESSION_ACCOUNT);
		} catch (Exception e) {

		}
		return account;
	}

	public static User getUser(SocketIOClient client) {
		SessionUser user = getClient(client);
		if (user != null)
			return user.getUser();
		return null;
	}

	public static int getClientCount() {
		// TODO Auto-generated method stub
		return client_cache.size();
	}

	public static void sendDataToAll( DataObj data) {
		Object[] accounts = SessionService.getClientAccounts();
		for (int i = 0; i < accounts.length; i++) {
			String account = (String) accounts[i];
			sendDataToClient(account,data);
		}
	}
}
