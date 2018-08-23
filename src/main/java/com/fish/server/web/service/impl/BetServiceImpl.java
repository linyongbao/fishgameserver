package com.fish.server.web.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fish.server.base.key.ConstantKey;
import com.fish.server.base.util.NumberUtil;
import com.fish.server.web.base.service.BaseService;
import com.fish.server.web.bean.bet.BetRound;
import com.fish.server.web.bean.bet.BetRoundResult;
import com.fish.server.web.bean.bet.UserBetRocord;
import com.fish.server.web.bean.finance.Finance;
import com.fish.server.web.dao.bet.BetRoundDao;
import com.fish.server.web.dao.bet.BetRoundResultDao;
import com.fish.server.web.dao.bet.UserBetRecordDao;
import com.fish.server.web.service.BetService;
import com.fish.server.web.service.FinanceService;
import com.fish.server.web.service.UserStaticService;
import com.fish.server.web.vo.NumItem;
import com.fish.server.web.vo.PrizeVO;

@Transactional
@Service("betService")
public class BetServiceImpl extends BaseService implements BetService {

	@Autowired
	private BetRoundResultDao betRoundResultDao;
	@Autowired
	private BetRoundDao betRoundDao;
	@Autowired
	private UserBetRecordDao userBetRecordDao;

	@Autowired
	private FinanceService financeService;
	
	@Autowired
	private UserStaticService userStaticService;
	
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
	public BetRoundResult createBetRoundResult(int betRoundId) {

		BetRound  betRound = this.getCurrentBetRoundById(betRoundId);
	
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

			int max = ConstantKey.MAX_TOTAL_FISH_COUNT;
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

			int max = ConstantKey.MAX_TOTAL_FISH_COUNT;
			int min = 0;
			Random random = new Random();
			fishCount2 = fishCount1 = random.nextInt(max) % (max - min + 1)
					+ min;

		}

		else if (GET.getValue() == 3) {
			int max = ConstantKey.MAX_TOTAL_FISH_COUNT;
			int min = 1;
			Random random = new Random();
			fishCount2 = random.nextInt(max) % (max - min + 1) + min;

			max = fishCount2 - 1;
			min = 0;
			if(max > 0)
			{
				random = new Random();
				fishCount1 = random.nextInt(max) % (max - min + 1) + min;
			}
			else
			{
				fishCount1 = 0;
				
			}
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
		{
			
			//产生钓鱼过程
			int totalTime = betRound.getGameTimeTotal();
			
			List   leftGetFishList = new ArrayList();
			List   rightGetFishList = new ArrayList ();
			
			JSONObject  getFishMap = new JSONObject ();
			
			Map indexSecs = new HashMap();
			int index;
			int max = totalTime - 1;
			int min = 3;
			for(int i = 1; i <= fishCount1; i++)
			{
				index= i;
				int sec = getIndexSec(indexSecs,max,min);
				JSONObject  leftGetFishMap = new JSONObject ();
				leftGetFishMap.put("index", index);
				leftGetFishMap.put("sec", sec);
				leftGetFishList.add(leftGetFishMap);
			
			}
			indexSecs.clear();
			for(int i = 1; i <= fishCount2; i++)
			{
				index = i;
				int sec = getIndexSec(indexSecs,max,min);
				JSONObject  rightGetFishMap = new JSONObject ();
				rightGetFishMap.put("index", index);
				rightGetFishMap.put("sec", sec);
				rightGetFishList.add(rightGetFishMap);
			
			}
			leftGetFishList.sort(new SortBySec());
			getFishMap.put("leftGetFishList", leftGetFishList);
			getFishMap.put("rightGetFishList", rightGetFishList);
			String fishGetProJsonStr = getFishMap.toString();
			result.setFishGetProJsonStr(fishGetProJsonStr);
			betRoundResultDao.createBetRoundResult(result);
			
		}
		else
		{
			betRoundResultDao.updateBetRoundResult(result);
		}
		return result;
	}



	private int getIndexSec(Map indexSecs,int max,int min) {
		Random random = new Random();
		int sec = random.nextInt(max)%(max-min+1) + min;
		while(indexSecs.get(sec) != null)
		{
			return getIndexSec(indexSecs,max,min);
		}
		indexSecs.put(sec, true);
		return sec;
	}

	private BetRound getCurrentBetRoundById(int id) {
		// TODO Auto-generated method stub
		return this.betRoundDao.getBetRoundById(id);
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
	public void createUserBetResult(int betRoundId) throws Exception {
		// TODO Auto-generated method stub
		BetRoundResult  result = this.getBetRoundResultById(betRoundId);
		//下注的用户
		List<String> accounts = this.getBetUsersByRoundId(betRoundId);
		
		//开奖结果
		int fishCountLeft = result.getFishCountLeft();
		int fishCountRight = result.getFishCountRight();
		List addFinances = new ArrayList();
		List prizes = new ArrayList();
		for(String account : accounts){
			
			//此用户下注左赢的数量
			int betCount1 = this.getBetCountByAccount("betCount1",account,betRoundId);
			int betCount2 = this.getBetCountByAccount("betCount2",account,betRoundId);
			int betCount3 = this.getBetCountByAccount("betCount3",account,betRoundId);
			int prizeCount = 0;
			String remark = "";
			if(fishCountLeft == fishCountRight)//11倍
			{
				 prizeCount = betCount2 * ConstantKey.BEI_SHU_PEACE;
				 remark += account + "买:平，投注：" +betCount2+ "，开出结果:平,中奖:" + prizeCount;
			}
			else if(fishCountLeft > fishCountRight)//2倍
			{
				 prizeCount = betCount1 * ConstantKey.BEI_SHU_LEFT_WIN;
				 remark += account + "买:左赢，投注：" +betCount1+ "，开出结果:左赢,中奖:" + prizeCount;
			}
			else  if(fishCountLeft < fishCountRight)//2倍
			{
				 prizeCount = betCount3 * ConstantKey.BEI_SHU_RIGHT_WIN;
				 remark += account + "买:右赢，投注：" +betCount3+ "，开出结果:右赢,中奖:" + prizeCount;
			}
		
			Finance addFinance = new Finance();
			addFinance.setAccount(account);
			addFinance.setCreateTime(new Date());
			addFinance.setRemark(remark);
			addFinance.setTrxMoney(prizeCount);
			addFinance.setType(1);
			addFinances.add(addFinance);
			
			
			PrizeVO prize = new PrizeVO();
			prize.setAccount(account);
			prize.setTrx(prizeCount);
			prizes.add(prize);
			
		}
		
		financeService.addFinances(addFinances);
		userStaticService.updateTrxs(prizes);
		
	}

	@Override
	public BetRoundResult getBetRounResultByRoundId(int betRoundId) {
		// TODO Auto-generated method stub
		return this.betRoundResultDao.getBetResultByRoundId(betRoundId);
	}

}

class SortBySec implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		JSONObject  getFishMap1 = (JSONObject) o1;
		JSONObject  getFishMap2 = (JSONObject) o2;
		if(getFishMap1.getInt("sec") > getFishMap2.getInt("sec"))
			return 1;
		
		else
			return -1;

	}

}
