package com.fish.server.web.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.server.base.util.DateUtil;
import com.fish.server.redis.RedisCacheUtil;
import com.fish.server.web.bean.finance.Finance;
import com.fish.server.web.bean.userstatic.UserStatic;
import com.fish.server.web.dao.userstatic.UserStaticDao;
import com.fish.server.web.service.FinanceService;
import com.fish.server.web.service.UserLogService;
import com.fish.server.web.service.UserStaticService;

@Service("userStaticService")
public class UserStaticServiceImpl implements UserStaticService {

	@Autowired
	private UserStaticDao userStaticDao;
	@Autowired
	private FinanceService financeService;
	@Autowired
	private RedisCacheUtil redisCacheUtil;
	@Autowired
	private UserLogService userLogService;


	
	@Override
	public UserStatic addUserStatic(UserStatic obj) {
		// TODO Auto-generated method stub
		return this.userStaticDao.saveUserStatic(obj);
	}

	@Override
	public void updateUserStatic(UserStatic obj) {
		// TODO Auto-generated method stub
		this.userStaticDao.updateUserStatic(obj);
	}



	@Override
	public UserStatic getUserStaticByUId(int uid) {
		// TODO Auto-generated method stub
		return this.userStaticDao.getUserStaticByUid(uid);
	}

	@Override
	public UserStatic prizeOrReduceGCoin(int uid, int getG_coin, String desc, int type) {
		// TODO Auto-generated method stub
		UserStatic userStatic = getUserStaticByUId(uid);
		userStatic.setG_coin(userStatic.getG_coin() + (int) getG_coin);
		updateUserStatic(userStatic);

		// 流水记录
		Finance fiance = new Finance();
		fiance.setCreateTime(new Date());
		fiance.setUid(uid);
		fiance.setG_coin((int) getG_coin);
		fiance.setType(type);
		fiance.setRemark(desc);
		financeService.addFinance(fiance);
		
		return userStatic;
	}
	
	@Override
	public UserStatic prizeOrReduceMCoin(int uid, int getM_coin, String desc, int type) {
		// TODO Auto-generated method stub
		UserStatic userStatic = getUserStaticByUId(uid);
		userStatic.setG_coin(userStatic.getM_coin() + (int) getM_coin);
		updateUserStatic(userStatic);

		// 流水记录
		Finance fiance = new Finance();
		fiance.setCreateTime(new Date());
		fiance.setUid(uid);
		fiance.setM_coin((int) getM_coin);
		fiance.setType(type);
		fiance.setRemark(desc);
		financeService.addFinance(fiance);
		
		return userStatic;
	}

	@Override
	public void ifDayFirstLoginPrizeGCoin(int uid) {
		// TODO Auto-generated method stub
		Object firstDayLogin = redisCacheUtil.getCacheObject(DateUtil.getDateString("yyyy-MM-dd") + "_" + uid);
		if (firstDayLogin == null) {
			redisCacheUtil.setCacheObject(DateUtil.getDateString("yyyy-MM-dd") + "_" + uid, true);
			prizeOrReduceGCoin(uid, 200, "用户首次登陆,奖励金币:200", Finance.INCOME);
		}
	}

	

}
