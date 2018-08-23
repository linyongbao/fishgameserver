package com.fish.server.web.service;

import java.util.List;

import com.fish.server.web.bean.userstatic.UserStatic;
import com.fish.server.web.vo.PrizeVO;

public interface UserStaticService {

	public UserStatic addUserStatic(UserStatic obj);

	public void updateUserStatic(UserStatic obj);

	public UserStatic getUserStaticByAccount(String account);

	public void updateTrxs(List<PrizeVO> prizes);

}
