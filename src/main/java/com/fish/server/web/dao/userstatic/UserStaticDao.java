package com.fish.server.web.dao.userstatic;

import com.fish.server.web.bean.userstatic.UserStatic;

public interface UserStaticDao {

	public UserStatic getUserStatic(int id);

	public void updateUserStatic(UserStatic obj);

	public UserStatic saveUserStatic(UserStatic obj);

	public UserStatic getUserStaticByUid(int uid);

}
