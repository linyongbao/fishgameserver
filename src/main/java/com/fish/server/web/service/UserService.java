package com.fish.server.web.service;

import com.fish.server.web.bean.user.User;

public interface UserService {

	User loginUser( String authKey);

	void initUser(String account);

	User getUserByAccount(String account);

	void saveUser(User user);

	void updateUser(User user);

	User getUserByUid(int uid);
	void addLoginOutTime(int uid);

	
}
