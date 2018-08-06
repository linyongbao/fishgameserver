package com.fish.server.websocket.service;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.fish.server.redis.RedisCacheUtil;
import com.fish.server.web.bean.bet.BetRound;
import com.fish.server.web.bean.user.User;
import com.fish.server.web.bean.userstatic.UserStatic;
import com.fish.server.web.service.BetService;
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
public class BetDataService extends BaseService implements IReceiveDataService {

	@Autowired
	private BetService betService;
	@Autowired
	private RedisCacheUtil redisCacheUtil;
	public BetDataService() {
		
	
	}

	@Override
	public void receiveData(SocketIOClient client, DataObj data, AckRequest ack) {

		Map<String, Object> map = (Map<String, Object>) data.getJsonObj();
		String account = SessionService.getAccount(client);
		User user = SessionService.getUser(client);
		Map<String, Object> resMap = null;
		DataObj returnData = null;
		
		if (data.getCmd() == CmdConst.BET_REQ)// 请求参加答题
		{
			String betTypeStr = (String) map.get("betType");
			int betCount = Integer.parseInt(betTypeStr.split("-")[0]);
			String moneyType = betTypeStr.split("-")[1];
			int winType = Integer.parseInt( map.get("winType").toString());
			
		}
		
		else if (data.getCmd() == CmdConst.BET_ROUND_REQ)//
		{
			BetRound currentBetRound = betService.getCurrentBetRound();
			
			returnData = new DataObj();
			returnData.setJsonObj(currentBetRound);
			returnData.setCode(0);
			returnData.setCmd(CmdConst.BET_ROUND_RSP);
			returnData.setServiceId(CmdConst.BET_SERVICE_ID);
			brocastDataToAccount(account,returnData);
		}

	}

	

}
