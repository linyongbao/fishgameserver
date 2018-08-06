package com.fish.server.web.dao.bet;

import com.fish.server.web.bean.bet.BetRound;

public interface BetRoundDao {

	public BetRound createBetRound(BetRound obj);

	public void updateBetRound(BetRound obj);

	public BetRound getLatesBetRound();
	
	public BetRound getBetRoundById(int id);

}
