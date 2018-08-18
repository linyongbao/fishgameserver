package com.fish.server.web.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.server.redis.RedisCacheUtil;
import com.fish.server.web.bean.user.User;
import com.fish.server.web.bean.userlog.UserLoginLog;
import com.fish.server.web.bean.userstatic.UserStatic;
import com.fish.server.web.dao.user.UserDao;
import com.fish.server.web.dao.userstatic.UserStaticDao;
import com.fish.server.web.service.UserLogService;
import com.fish.server.web.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private RedisCacheUtil redisCacheUtil;
	@Autowired
	private UserDao userDao;

	@Autowired
	private UserStaticDao userStaticDao;
	@Autowired
	private UserLogService userLogService;
	

	@Override
	public User loginUser(String authKey) {
		// TODO Auto-generated method stub
		User user = null;
		if (redisCacheUtil.getCacheObject(authKey) != null) {
			user = (User) redisCacheUtil.getCacheObject(authKey);
			redisCacheUtil.setCacheObject(authKey, null);

		}
		if (user != null) {
			UserLoginLog userLoginLog = new UserLoginLog();
			userLoginLog.setLoginTime(new Date());
			userLoginLog.setUserId(user.getId());
			userLogService.createLoginLog(userLoginLog);
		}
		return user;
	}



	@Override
	public void initUser(String account) {
		if(account != "")
		{
		// TODO Auto-generated method stub
			UserStatic userStatic = userStaticDao.getUserStaticByAccount(account);
			if (userStatic == null) {
				userStatic = new UserStatic();
				userStatic.setTrxMoney(100000);
				userStatic.setLevel(1);
				userStatic.setAccount(account);
				userStaticDao.saveUserStatic(userStatic);
			}
		}
		
	}

	@Override
	public User getUserByAccount(String account) {
		// TODO Auto-generated method stub
		return this.userDao.getUserByAccount(account);
	}

	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		userDao.saveUser(user);
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		userDao.updateUser(user);
	}

	@Override
	public User getUserByUid(int uid) {
		// TODO Auto-generated method stub
		return userDao.getUserByUid(uid);
	}

	@Override
	public void addLoginOutTime(int uid) {
		// TODO Auto-generated method stub
		UserLoginLog ul = new UserLoginLog();
		ul.setUserId(uid);
		ul.setType(1);
		ul.setLoginTime(new Date());
		userLogService.createLoginLog(ul);
	}


}
