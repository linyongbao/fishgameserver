package com.fish.server.web.service;

import com.fish.server.web.bean.bet.BetRound;

public interface BetService {

	BetRound getCurrentBetRound( );

	BetRound createBetRound();

	void updateBetRound(BetRound betRound);

	boolean checkIfCurrentRoundEnd(BetRound current);


	
}
