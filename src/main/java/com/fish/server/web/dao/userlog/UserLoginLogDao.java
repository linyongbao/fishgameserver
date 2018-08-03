package com.fish.server.web.dao.userlog;

import com.fish.server.web.bean.userlog.UserLoginLog;
import com.fish.server.web.util.CurrentPage;
import com.fish.server.web.util.Page;

public interface UserLoginLogDao {

	public CurrentPage<UserLoginLog> queryUserLoginLogPage(int uid, Page page);

	public UserLoginLog saveUserLoginLog(UserLoginLog loginLog);

	public UserLoginLog getLastLoginLog(int uid, int type);
}
