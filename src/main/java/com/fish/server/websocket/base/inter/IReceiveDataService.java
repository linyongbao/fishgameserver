package com.fish.server.websocket.base.inter;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.fish.server.websocket.bean.DataObj;

public interface IReceiveDataService {

	public void receiveData(SocketIOClient client, DataObj data, AckRequest ack);
}
