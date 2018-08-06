package com.fish.server.web.dao.impl.bet;

import org.springframework.stereotype.Repository;

import com.fish.server.web.base.dao.BaseDaoImpl;
import com.fish.server.web.bean.bet.BetRound;
import com.fish.server.web.bean.user.User;
import com.fish.server.web.dao.bet.BetRoundDao;

@Repository("betRoundDao")
public class BetRoundDaoImpl extends BaseDaoImpl<User> implements BetRoundDao {

	public BetRoundDaoImpl() {

		setTableName("fish_betround");
	}

	@Override
	public void createBetRound(BetRound obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBetRound(BetRound obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BetRound getLatesBetRound() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BetRound getBetRoundById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
