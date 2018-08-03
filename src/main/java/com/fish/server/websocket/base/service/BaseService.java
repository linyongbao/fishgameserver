package com.fish.server.websocket.base.service;

import java.util.HashMap;

import com.corundumstudio.socketio.AckCallback;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.fish.server.websocket.base.inter.IBaseService;
import com.fish.server.websocket.base.inter.IReceiveDataService;
import com.fish.server.websocket.base.key.BaseKey;
import com.fish.server.websocket.base.key.SessionKey;
import com.fish.server.websocket.bean.DataObj;
import com.fish.server.websocket.session.SessionService;

public class BaseService {

	public BaseService() {
	}

	public void init() {

	}

	private int serviceId;
	public SocketIOClient currentClient;
	// 客户端暂存
	private static HashMap<Integer, BaseService> service_cach = new HashMap<Integer, BaseService>();

	/**
	 * @return the serviceid
	 */
	public int getServiceId() {
		return serviceId;
	}

	/**
	 * @param serviceid
	 *            the serviceid to set
	 */
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
		service_cach.put(serviceId, this);
	}

	public void onData(SocketIOClient client, DataObj data, AckRequest ack) throws Exception {

		currentClient = client;
		if (this instanceof IReceiveDataService) {
			((IReceiveDataService) this).receiveData(client, data, ack);
		}

	}

	public void brocastDataToAccount(String account, DataObj data) {
		SessionService.sendDataToClient(account, data);
	}

	public void brocastDataToClient(SocketIOClient client, DataObj data) {
		if (client != null) {
			SessionService.sendDataToClient(client, data);
		}
	}

	public static BaseService findService(int serviceid) {
		// TODO Auto-generated method stub
		BaseService service = service_cach.get(serviceid);
		return service;
	}

}
