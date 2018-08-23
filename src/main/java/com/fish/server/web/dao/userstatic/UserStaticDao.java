package com.fish.server.web.dao.userstatic;

import java.util.List;

import com.fish.server.web.bean.userstatic.UserStatic;
import com.fish.server.web.vo.PrizeVO;

public interface UserStaticDao {

	public UserStatic getUserStatic(int id);

	public void updateUserStatic(UserStatic obj);

	public UserStatic saveUserStatic(UserStatic obj);

	public UserStatic getUserStaticByAccount(String account);

	public void updateStatics(List<PrizeVO> prizes);

}
