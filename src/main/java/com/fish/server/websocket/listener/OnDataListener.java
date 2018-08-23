package com.fish.server.websocket.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;

import com.corundumstudio.socketio.listener.DataListener;
import com.fish.server.websocket.base.service.BaseDataService;
import com.fish.server.websocket.bean.DataObj;

public class OnDataListener implements DataListener<DataObj> {

	@Override
	public void onData(SocketIOClient client, DataObj msg, AckRequest ack) throws Exception {
		// TODO Auto-generated method stub
		BaseDataService service = BaseDataService.findService(msg.getServiceId());
		if (service != null) {
			service.onData(client, msg, ack);
		}

	}

}
