package com.fish.server.web.service;

import com.fish.server.web.bean.userstatic.UserStatic;

public interface UserStaticService {

	public UserStatic addUserStatic(UserStatic obj);

	public void updateUserStatic(UserStatic obj);

	public UserStatic getUserStaticByUId(int uid);

	public UserStatic prizeOrReduceGCoin(int uid, int getG_coin, String desc, int type);

	public void ifDayFirstLoginPrizeGCoin(int uid);

	public UserStatic prizeOrReduceMCoin(int uid, int getM_coin, String desc, int type);
}
