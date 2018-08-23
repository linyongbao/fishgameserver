package com.fish.server.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fish.server.redis.RedisCacheUtil;
import com.fish.server.web.base.service.BaseService;
import com.fish.server.web.bean.userstatic.UserStatic;
import com.fish.server.web.dao.userstatic.UserStaticDao;
import com.fish.server.web.service.FinanceService;
import com.fish.server.web.service.UserLogService;
import com.fish.server.web.service.UserStaticService;
import com.fish.server.web.vo.PrizeVO;

@Transactional
@Service("userStaticService")
public class UserStaticServiceImpl  extends BaseService implements UserStaticService {

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
	public void updateTrxs(List<PrizeVO> prizes) {
		// TODO Auto-generated method stub
	
		this.userStaticDao.updateStatics(prizes);
	}
	

}
