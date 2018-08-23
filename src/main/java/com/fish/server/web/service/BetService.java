package com.fish.server.web.service;

import java.util.List;

import com.fish.server.web.bean.bet.BetRound;
import com.fish.server.web.bean.bet.BetRoundResult;
import com.fish.server.web.bean.bet.UserBetRocord;

public interface BetService {

	BetRound getCurrentBetRound( );

	BetRound createBetRound();

	void updateBetRound(BetRound betRound);

	boolean checkIfCurrentRoundEnd(BetRound current);

	int saveUserBetRecord(UserBetRocord obj);
	
	BetRoundResult createBetRoundResult(int betRoundId);
	BetRoundResult getBetRoundResultById(int betRoundId);
	List<String> getBetUsersByRoundId(int betRoundId);

	//查询下注的数量,betCount1,左赢，betCount2平，betCount3右赢
	int getBetCountByAccount(String betType, String account,int betRoundId);

	void createUserBetResult(int betRoundId) throws Exception ;
	
	BetRoundResult getBetRounResultByRoundId(int betRoundId);
	
	
	
	
	
}
