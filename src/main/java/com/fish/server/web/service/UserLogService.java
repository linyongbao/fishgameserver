package com.fish.server.web.service;

import com.fish.server.web.bean.userlog.UserLoginLog;
import com.fish.server.web.util.CurrentPage;
import com.fish.server.web.util.Page;

public interface UserLogService {

	CurrentPage<UserLoginLog> queryUserLoginLogPage(int uid, Page page);

	void createLoginLog(UserLoginLog loginLog);

	UserLoginLog getLastLoginLog(int id, int type);

}
