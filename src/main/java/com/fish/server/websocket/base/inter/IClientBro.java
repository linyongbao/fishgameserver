package com.fish.server.websocket.base.inter;

import com.fish.server.websocket.bean.DataObj;

public interface IClientBro {
	void brocastDataToUid(int uid, DataObj data) ;
}
