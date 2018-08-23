package com.fish.server.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fish.server.web.base.service.BaseService;
import com.fish.server.web.bean.userlog.UserLoginLog;
import com.fish.server.web.dao.userlog.UserLoginLogDao;
import com.fish.server.web.service.UserLogService;
import com.fish.server.web.util.CurrentPage;
import com.fish.server.web.util.Page;

@Transactional
@Service("userLogService")
public class UserLogServiceImpl extends BaseService implements UserLogService {

	@Autowired
	private UserLoginLogDao userLoginLogDao;

	@Override
	public CurrentPage<UserLoginLog> queryUserLoginLogPage(int uid, Page page) {
		// TODO Auto-generated method stub
		return this.userLoginLogDao.queryUserLoginLogPage(uid, page);

	}

	@Override
	public void createLoginLog(UserLoginLog loginLog) {
		// TODO Auto-generated method stub
		this.userLoginLogDao.saveUserLoginLog(loginLog);
	}

	@Override
	public UserLoginLog getLastLoginLog(int uid, int type) {
		// TODO Auto-generated method stub
		return this.userLoginLogDao.getLastLoginLog(uid, type);
	}

}
