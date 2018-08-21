package com.fish.server.web.dao.bet;

import java.util.List;

import com.fish.server.web.bean.bet.UserBetRocord;


public interface UserBetRecordDao {

	public UserBetRocord createUserBetRecord(UserBetRocord obj);
	
	public int getTotalBetCount(String betType,int betRoundId);
	
	public List<String> getBetUsersByRoundId(int betRoundId);
	
	public int getBetCountByAccount(String betType, String account,int betRoundId);

	
}
