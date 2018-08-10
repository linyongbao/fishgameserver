package com.fish.server.websocket.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.fish.server.redis.RedisCacheUtil;
import com.fish.server.web.bean.bet.BetRound;
import com.fish.server.web.bean.bet.UserBetRocord;
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
import com.fish.server.websocket.vo.BetStatic;
import com.fish.server.websocket.vo.MyBetStatic;

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

		if (data.getCmd() == CmdConst.MY_BET_REQ)//
		{
			BetRound currentBetRound = betService.getCurrentBetRound();
			if (currentBetRound != null && currentBetRound.getState() != 0) {

				returnData = new DataObj();
				returnData.setJsonObj(null);
				returnData.setCode(1);
				returnData.setCmd(CmdConst.MY_BET_RSP);
				returnData.setServiceId(CmdConst.BET_SERVICE_ID);
				brocastDataToAccount(account, returnData);
			} else if (currentBetRound != null) {

				String moneyType = (String) map.get("moneyType");
				int betCount1 = (Integer) map.get("betCount1");
				int betCount2 = (Integer) map.get("betCount2");
				int betCount3 = (Integer) map.get("betCount3");
				UserBetRocord userBetRecord = new UserBetRocord();
				userBetRecord.setAccount(account);
				userBetRecord.setBetCount1(betCount1);
				userBetRecord.setBetCount2(betCount2);
				userBetRecord.setBetCount3(betCount3);
				userBetRecord.setMoneyType(moneyType);
				userBetRecord.setCreateTime(new Date());
				userBetRecord.setBetRoundId(currentBetRound.getId());
				userBetRecord.setBetResult(0);
				int saveId = betService.saveUserBetRecord(userBetRecord);

				if (saveId > 0) {
					Object myBetStaticObj = redisCacheUtil
							.getCacheObject("mybet_" + account + "_"
									+ currentBetRound.getId());
					if (myBetStaticObj == null) {
						myBetStaticObj = new MyBetStatic();
						redisCacheUtil.setCacheObject("mybet_" + account + "_"
								+ currentBetRound.getId(), myBetStaticObj);
					}

					MyBetStatic myBetStatic = (MyBetStatic) myBetStaticObj;
					
					myBetStatic.setBetCount1(myBetStatic.getBetCount1()
							+ betCount1);
		
					myBetStatic.setBetCount2(myBetStatic.getBetCount2()
							+ betCount2);
				
					myBetStatic.setBetCount3(myBetStatic.getBetCount3()
							+ betCount3);

					myBetStaticObj = myBetStatic;
					redisCacheUtil.setCacheObject("mybet_" + account + "_"
							+ currentBetRound.getId(), myBetStaticObj);

					returnData = new DataObj();
					returnData.setJsonObj(myBetStaticObj);
					returnData.setCode(0);
					returnData.setCmd(CmdConst.MY_BET_RSP);
					returnData.setServiceId(CmdConst.BET_SERVICE_ID);
					brocastDataToAccount(account, returnData);

					Object betStaticObj = redisCacheUtil.getCacheObject("bet_"
							+ currentBetRound.getId());
					if (betStaticObj == null) {
						betStaticObj = new BetStatic();
						redisCacheUtil.setCacheObject(
								"bet_" + currentBetRound.getId(), betStaticObj);
					}

					BetStatic betStatic = (BetStatic) betStaticObj;
				
					betStatic.setBetCount1(betStatic.getBetCount1()
							+ betCount1);
				
					betStatic.setBetCount2(betStatic.getBetCount2()
							+ betCount2);
				
					betStatic.setBetCount3(betStatic.getBetCount3()
							+ betCount3);

					betStaticObj = betStatic;
					redisCacheUtil.setCacheObject(
							"bet_" + currentBetRound.getId(), betStaticObj);

					HashMap betData = new HashMap();
					betData.put("betData", map);
					betData.put(betStatic, betStatic);
					returnData = new DataObj();
					returnData.setJsonObj(betData);
					returnData.setCode(0);
					returnData.setCmd(CmdConst.BET_BRO);
					returnData.setServiceId(CmdConst.BET_SERVICE_ID);
					brocastDataToAll(returnData);

				} else {
					returnData = new DataObj();
					returnData.setJsonObj(null);
					returnData.setCode(3);
					returnData.setCmd(CmdConst.MY_BET_RSP);
					returnData.setServiceId(CmdConst.BET_SERVICE_ID);
					brocastDataToAccount(account, returnData);
				}
			} else {
				returnData = new DataObj();
				returnData.setJsonObj(null);
				returnData.setCode(2);
				returnData.setCmd(CmdConst.MY_BET_RSP);
				returnData.setServiceId(CmdConst.BET_SERVICE_ID);
				brocastDataToAccount(account, returnData);

			}

		}

		else if (data.getCmd() == CmdConst.BET_ROUND_REQ)//
		{
			BetRound currentBetRound = betService.getCurrentBetRound();
			Object betStaticObj = redisCacheUtil.getCacheObject("bet_"
					+ currentBetRound.getId());
			Object myBetStaticObj = redisCacheUtil.getCacheObject("mybet_"
					+ account + "_" + currentBetRound.getId());
			Map betRoundDataMap = new HashMap();
			betRoundDataMap.put("currentBetRound", currentBetRound);
			betRoundDataMap.put("betStatic", betStaticObj);
			betRoundDataMap.put("myBetStatic", myBetStaticObj);
			returnData = new DataObj();
			returnData.setJsonObj(betRoundDataMap);
			returnData.setCode(0);
			returnData.setCmd(CmdConst.BET_ROUND_RSP);
			returnData.setServiceId(CmdConst.BET_SERVICE_ID);
			brocastDataToAccount(account, returnData);
		}

	}

}
