package com.fish.server.web.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.server.base.util.NumberUtil;
import com.fish.server.web.bean.bet.BetRound;
import com.fish.server.web.bean.bet.BetRoundResult;
import com.fish.server.web.bean.bet.NumItem;
import com.fish.server.web.bean.bet.UserBetRocord;
import com.fish.server.web.dao.bet.BetRoundDao;
import com.fish.server.web.dao.bet.BetRoundResultDao;
import com.fish.server.web.dao.bet.UserBetRecordDao;
import com.fish.server.web.service.BetService;

@Service("betService")
public class BetServiceImpl implements BetService {

	@Autowired
	private BetRoundResultDao betRoundResultDao;
	@Autowired
	private BetRoundDao betRoundDao;
	@Autowired
	private UserBetRecordDao userBetRecordDao;

	@Override
	public BetRound getCurrentBetRound() {
		// TODO Auto-generated method stub
		return betRoundDao.getLatesBetRound();
	}

	@Override
	public BetRound createBetRound() {
		BetRound betRound = new BetRound();
		betRound.setState(0);
		betRound.setCreateTime(new Date());
		betRound.setBetTimeTotal(30);
		betRound.setBetTimeLeft(betRound.getBetTimeTotal());

		betRound.setGameTimeTotal(20);
		betRound.setGameTimeLeft(betRound.getGameTimeTotal());

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
		if ((nowTime - startTime) > currentBetRound.getGameTimeLeft() * 1000) {

			endFlag = true;
		}
		return endFlag;
	}

	@Override
	public int saveUserBetRecord(UserBetRocord obj) {
		// TODO Auto-generated method stub
		userBetRecordDao.createUserBetRecord(obj);
		return obj.getId();
	}

	private static int T = 100000;

	@Override
	public BetRoundResult cauBetRoundResult(int betRoundId) {

		// 买左赢总数
		int totalBetCount1 = userBetRecordDao.getTotalBetCount("betCount1",betRoundId);
		// 买平赢总数
		int totalBetCount2 = userBetRecordDao.getTotalBetCount("betCount2",betRoundId);
		// 买右赢总数
		int totalBetCount3 = userBetRecordDao.getTotalBetCount("betCount3",betRoundId);

		int toatalBetCount = totalBetCount1 + totalBetCount2 + totalBetCount3;// 总投注

		int AW = totalBetCount1 * 2;// 假如左赢用户能拿回金额
		int BW = totalBetCount2 * 11;// 假如平用户能拿回金额
		int CW = totalBetCount3 * 2;// 加如右赢用户能拿回金额

		// 计算假如左赢，庄家赢
		int AN = toatalBetCount - AW;
		int BN = toatalBetCount - BW;
		int CN = toatalBetCount - CW;

		int TA = T + AN;
		int TB = T + BN;
		int TC = T + CN;

		List G = new ArrayList();
		if (TA >= T * 0.9) {
			G.add(new NumItem(1, 45));
		}

		if (TB >= T * 0.9) {

			G.add(new NumItem(2, 10));
		}

		if (TC >= T * 0.9) {

			G.add(new NumItem(3, 45));
		}

		NumItem GET = NumberUtil.getNumItemByList(G);
		int fishCount2 = 0;
		int fishCount1 = 0;
		if (GET.getValue() == 1) {// 左边赢

			int max = 11;
			int min = 1;
			Random random = new Random();
			fishCount1 = random.nextInt(max) % (max - min + 1) + min;

			max = fishCount1 - 1;
			min = 0;
			if (max == 0) {
				fishCount2 = 0;
			} else {
				random = new Random();
				fishCount2 = random.nextInt(max) % (max - min + 1) + min;
			}
		}

		else if (GET.getValue() == 2) {// 平

			int max = 11;
			int min = 0;
			Random random = new Random();
			fishCount2 = fishCount1 = random.nextInt(max) % (max - min + 1)
					+ min;

		}

		else if (GET.getValue() == 3) {
			int max = 11;
			int min = 1;
			Random random = new Random();
			fishCount2 = random.nextInt(max) % (max - min + 1) + min;

			max = fishCount2 - 1;
			min = 0;
			random = new Random();
			fishCount1 = random.nextInt(max) % (max - min + 1) + min;

		}

		BetRoundResult result = betRoundResultDao
				.getBetResultByRoundId(betRoundId);
		Boolean updateFlag = false;
		if (result == null) {
			result = new BetRoundResult();
		} else {
			updateFlag = true;

		}
		result.setBetRoundId(betRoundId);
		result.setCreateTime(new Date());
		result.setFishCountLeft(fishCount1);
		result.setFishCountRight(fishCount2);
		if (!updateFlag)
			betRoundResultDao.createBetRoundResult(result);
		else
			betRoundResultDao.updateBetRoundResult(result);
		return result;
	}



	@Override
	public BetRoundResult getBetRoundResultById(int betRoundId) {
		// TODO Auto-generated method stub
		return betRoundResultDao.getBetResultByRoundId(betRoundId);
	}

	@Override
	public List<String> getBetUsersByRoundId(int betRoundId) {
		// TODO Auto-generated method stub
		return userBetRecordDao.getBetUsersByRoundId(betRoundId);
	}

	@Override
	public int getBetCountByAccount(String betType, String account,int betRoundId) {
		// TODO Auto-generated method stub
		return userBetRecordDao.getBetCountByAccount(betType,account,betRoundId);
	}

	@Override
	public void cauUserBetResult(int betRoundId) {
		// TODO Auto-generated method stub
		BetRoundResult  result = this.getBetRoundResultById(betRoundId);
		//下注的用户
		List<String> accounts = this.getBetUsersByRoundId(betRoundId);
		
		//开奖结果
		int fishCountLeft = result.getFishCountLeft();
		int fishCountRight = result.getFishCountRight();
		
		for(String account : accounts){
			
			//此用户下注左赢的数量
			int betCount1 = this.getBetCountByAccount("betCount1",account,betRoundId);
			int betCount2 = this.getBetCountByAccount("betCount2",account,betRoundId);
			int betCount3 = this.getBetCountByAccount("betCount3",account,betRoundId);
			int prizeCount = 0;
			if(fishCountLeft == fishCountRight)//11倍
			{
				 prizeCount = betCount2 * 11;
			}
			else if(fishCountLeft > fishCountRight)//11倍
			{
				 prizeCount = betCount1 * 2;
			}
			else  if(fishCountLeft < fishCountRight)//11倍
			{
				 prizeCount = betCount3 * 2;
			}
		}
		
		
	}

}
