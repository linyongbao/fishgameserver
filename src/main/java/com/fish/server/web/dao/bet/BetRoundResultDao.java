package com.fish.server.web.dao.bet;

import com.fish.server.web.bean.bet.BetRoundResult;


public interface BetRoundResultDao {

	public BetRoundResult createBetRoundResult(BetRoundResult obj);

	public BetRoundResult getBetResultByRoundId(int betRoundId);

	public void updateBetRoundResult(BetRoundResult result);

	
}
