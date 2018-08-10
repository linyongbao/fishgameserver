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
import com.fish.server.web.bean.bet.BetRoundResult;
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
		
	}
	@Override
	public void init() {
		super.init();
		currentRoundBro(); 
	}

	private int waitTime = 0;
	private void currentRoundBro() {
		// TODO Auto-generated method stub
		Timer playTimer = new Timer();
		
		playTimer.schedule(new TimerTask() {
			public void run() {
				doTime();
			}
		}, 1000, 1000);// 设定指定的时间time
	}
	private void doTime(){
		
		DataObj data = null;
		boolean endFlag = false;
		boolean startFlag = false;
		waitTime--;
		if (waitTime > 0)
			return;
	
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
					//计算
					betService.cauBetRoundResult(currentBetRound.getId());
				}
				

			} else if (currentBetRound.getState() == 1) {
				currentBetRound.setGameTimeLeft(currentBetRound
						.getGameTimeLeft() - 1);
				if (currentBetRound.getGameTimeLeft() <= 0)
				{
					endFlag = true;
					currentBetRound.setState(2);
					waitTime  = 10;
					//计算中
					cauUserBetResult(currentBetRound.getId());
				}
			
			}
			
			else if (currentBetRound.getState() == 2) {
				currentBetRound = betService.createBetRound();

			}
			betService.updateBetRound(currentBetRound);

		}

		if (currentBetRound != null) {

		
			data = new DataObj();
			data.setJsonObj(currentBetRound);
			data.setCode(0);
			data.setServiceid(CmdConst.BET_SERVICE_ID);
			data.setCmd(CmdConst.BET_ROUND_BRO);
			SessionService.sendDataToAll(data);
			
			if(endFlag)
			{
				endFlag = false;
				data = new DataObj();
				data.setJsonObj(null);
				data.setCode(0);
				data.setServiceid(CmdConst.BET_SERVICE_ID);
				data.setCmd(CmdConst.PLAY_END_BRO);
				SessionService.sendDataToAll( data);
			}
			
			if(startFlag)
			{
				startFlag = false;
				data = new DataObj();
				data.setJsonObj(null);
				data.setCode(0);
				data.setServiceid(CmdConst.BET_SERVICE_ID);
				data.setCmd(CmdConst.PLAY_START_BRO);
				SessionService.sendDataToAll(data);
			}
			
			
		}

		
	}
	
	private void cauUserBetResult(int betRoundId){
		
		betService.cauUserBetResult(betRoundId);
		
	}
	@Override
	public void receiveData(SocketIOClient client, DataObj data, AckRequest ack) {
		// TODO Auto-generated method stub

	}

}
