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
	public UserStatic getUserStaticByAccount(String account) {
		// TODO Auto-generated method stub
		return this.userStaticDao.getUserStaticByAccount(account);
	}

	@Override
	public UserStatic prizeOrReduceTrx(String account, int trx, String desc, int type) {
		// TODO Auto-generated method stub
		UserStatic userStatic = getUserStaticByAccount(account);
		userStatic.setTrxMoney(userStatic.getTrxMoney() + (int) trx);
		updateUserStatic(userStatic);

		// 流水记录
		Finance fiance = new Finance();
		fiance.setCreateTime(new Date());
		fiance.setAccount(account);
		fiance.setTrxMoney((int) trx);
		fiance.setType(type);
		fiance.setRemark(desc);
		financeService.addFinance(fiance);
		
		return userStatic;
	}
	

}
