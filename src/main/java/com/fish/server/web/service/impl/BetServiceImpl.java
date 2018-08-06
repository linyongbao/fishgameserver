package com.fish.server.web.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.server.web.bean.bet.BetRound;
import com.fish.server.web.dao.bet.BetRoundDao;
import com.fish.server.web.service.BetService;

@Service("betService")
public class BetServiceImpl implements BetService {

	
	@Autowired
	private BetRoundDao betRoundDao;

	@Override
	public BetRound getCurrentBetRound() {
		// TODO Auto-generated method stub
		return betRoundDao.getLatesBetRound();
	}

	@Override
	public BetRound createBetRound() {
		BetRound betRound = new BetRound();
		betRound.setState(0);
		betRound.setCreateTime( new Date());
		betRound.setBetTimeLeft(20);
		betRound.setGameTimeLeft(20);
		betRound.setBetTimeTotal(20);
		betRound.setGameTimeTotal(20);
		return betRoundDao.createBetRound(betRound);
	}

	@Override
	public void updateBetRound(BetRound obj) {
		// TODO Auto-generated method stub
		betRoundDao.updateBetRound(obj);
	}

	@Override
	public boolean checkIfCurrentRoundEnd(BetRound currentBetRound) {
		long startTime = currentBetRound.getCreateTime().getTime();
		long nowTime = new Date().getTime();
		boolean endFlag = false;
		if((nowTime - startTime) > currentBetRound.getBetTimeLeft() * 1000)
		{
			currentBetRound.setState(1);	
			currentBetRound.setBetTimeLeft(0);
		}
		else
		{
			currentBetRound.setBetTimeLeft((int) (currentBetRound.getBetTimeLeft() - (nowTime - startTime)));
			currentBetRound.setState(0);
		}
		if((nowTime - startTime) > currentBetRound.getGameTimeLeft()  * 1000)
		{
			currentBetRound.setGameTimeLeft(0);
			currentBetRound.setState(2);
			endFlag = true;
		}
		else
		{
			currentBetRound.setBetTimeLeft((int) (currentBetRound.getGameTimeLeft() - (nowTime - startTime)));
			currentBetRound.setState(1);
		}
		this.updateBetRound(currentBetRound);
		return endFlag;
	}

	

}
