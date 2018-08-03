package com.fish.server.web.dao.user;

import com.fish.server.web.bean.user.User;

public interface UserDao {

	public User getUserByAccount(String account);

	public void updateUser(User user);

	public User saveUser(User user);
	
	public User getUserByUid(int uid);

}
