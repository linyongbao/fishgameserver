package com.fish.server.websocket.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.fish.server.base.key.ConstantKey;
import com.fish.server.web.bean.bet.BetRound;
import com.fish.server.web.bean.finance.Finance;
import com.fish.server.web.bean.userstatic.UserStatic;
import com.fish.server.web.service.BetService;
import com.fish.server.websocket.base.inter.IReceiveDataService;
import com.fish.server.websocket.base.key.CmdConst;
import com.fish.server.websocket.base.service.BaseService;
import com.fish.server.websocket.bean.DataObj;
import com.fish.server.websocket.session.SessionService;

public class TaskTimeDataService extends BaseService implements
		IReceiveDataService {

	@Autowired
	private BetService betService;

	public TaskTimeDataService() {
		currentRoundBro();

	}

	private int waitTime = 0;
	private void currentRoundBro() {
		// TODO Auto-generated method stub
		Timer playTimer = new Timer();

		playTimer.schedule(new TimerTask() {
			public void run() {
				DataObj data = null;
				boolean endFlag = false;
				boolean startFlag = false;
				waitTime--;
				if (waitTime > 0)
					return;
				Object[] accounts = SessionService.getClientAccounts();

				BetRound currentBetRound = betService.getCurrentBetRound();
				if (currentBetRound == null) {
					currentBetRound = betService.createBetRound();
				} else {
					if (currentBetRound.getState() == 0) {
						currentBetRound.setBetTimeLeft(currentBetRound
								.getBetTimeLeft() - 1);
						if (currentBetRound.getBetTimeLeft() <= 0)
						{
							startFlag = true;
							currentBetRound.setState(1);
						}
						

					} else if (currentBetRound.getState() == 1) {
						currentBetRound.setGameTimeLeft(currentBetRound
								.getGameTimeLeft() - 1);
						if (currentBetRound.getGameTimeLeft() <= 0)
						{
							endFlag = true;
							currentBetRound.setState(2);
							waitTime  = 10;
						}
					
					}
					
					else if (currentBetRound.getState() == 2) {
						currentBetRound = betService.createBetRound();
					}


				}

				if (currentBetRound != null) {

					betService.updateBetRound(currentBetRound);
					for (int i = 0; i < accounts.length; i++) {
						String account = (String) accounts[i];
						
						if(endFlag)
						{
							endFlag = false;
							data = new DataObj();
							data.setJsonObj(null);
							data.setCode(0);
							data.setServiceid(CmdConst.BET_SERVICE_ID);
							data.setCmd(CmdConst.PLAY_END_BRO);
							SessionService.sendDataToClient(account, data);
						}
						
						if(startFlag)
						{
							startFlag = false;
							data = new DataObj();
							data.setJsonObj(null);
							data.setCode(0);
							data.setServiceid(CmdConst.BET_SERVICE_ID);
							data.setCmd(CmdConst.PLAY_START_BRO);
							SessionService.sendDataToClient(account, data);
						}
						
						data = new DataObj();
						data.setJsonObj(currentBetRound);
						data.setCode(0);
						data.setServiceid(CmdConst.BET_SERVICE_ID);
						data.setCmd(CmdConst.BET_ROUND_BRO);
						SessionService.sendDataToClient(account, data);
					}
				}

			}
		}, 1000, 1000);// 设定指定的时间time
	}

	@Override
	public void receiveData(SocketIOClient client, DataObj data, AckRequest ack) {
		// TODO Auto-generated method stub

	}

}
