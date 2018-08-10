package com.fish.server.web.service;

import com.fish.server.web.bean.userstatic.UserStatic;

public interface UserStaticService {

	public UserStatic addUserStatic(UserStatic obj);

	public void updateUserStatic(UserStatic obj);

	public UserStatic getUserStaticByAccount(String account);

	public UserStatic prizeOrReduceTrx(String account, int trx, String desc, int type);

}
