package com.fish.server.websocket.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.fish.server.redis.RedisCacheUtil;
import com.fish.server.web.bean.user.User;
import com.fish.server.web.bean.userstatic.UserStatic;
import com.fish.server.web.service.UserService;
import com.fish.server.web.service.UserStaticService;
import com.fish.server.websocket.base.inter.IReceiveDataService;
import com.fish.server.websocket.base.key.CmdConst;
import com.fish.server.websocket.base.service.BaseService;
import com.fish.server.websocket.bean.DataObj;
import com.fish.server.websocket.session.SessionService;

/*
 * 对女孩子加护
 */
public class UserDataService extends BaseService implements IReceiveDataService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserStaticService userStaticService;
	@Autowired
	private RedisCacheUtil redisCacheUtil;

	public UserDataService() {
		
	}

	@Override
	public void receiveData(SocketIOClient client, DataObj data, AckRequest ack) {

		Map<String, Object> map = (Map<String, Object>) data.getJsonObj();
		String account = SessionService.getAccount(client);
		User user = SessionService.getUser(client);
		Map<String, Object> resMap = null;
		
		

	}

	public void noticeUserStaticUpdate(String account) {
		// TODO Auto-generated method stub
		if(account != null)
		{
			UserStatic userStatic = userStaticService.getUserStaticByAccount(account);
			DataObj data = new DataObj();
			data.setJsonObj(userStatic);
			data.setCmd(CmdConst.UPDATE_USER_INFO_BRO);
			data.setServiceId(CmdConst.USER_SERVICE_ID);
			brocastDataToAccount(account, data);
		}
	}

}
