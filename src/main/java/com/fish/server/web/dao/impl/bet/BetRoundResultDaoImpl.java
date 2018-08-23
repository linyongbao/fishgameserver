package com.fish.server.web.dao.impl.bet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.fish.server.web.base.dao.BaseDaoImpl;
import com.fish.server.web.bean.bet.BetRoundResult;
import com.fish.server.web.dao.bet.BetRoundResultDao;

@Repository("betRoundResultDao")
public class BetRoundResultDaoImpl extends BaseDaoImpl<BetRoundResult> implements BetRoundResultDao {

	public BetRoundResultDaoImpl() {

		setTableName("fish_betround_result");
	}

	@Override
	public BetRoundResult createBetRoundResult(final BetRoundResult obj) {
		final String addSql = "insert into " + this.tableName
				+ "(`createTime`,`fishCountLeft`,`fishCountRight`,`betRoundId`,`fishGetProJsonStr`) values(?,?,?,?,?)";

		insert(addSql,new PreparedStatementSetter() {
	    public void setValues(PreparedStatement ps) throws SQLException {
		    ps.setObject(1, obj.getCreateTime());
			ps.setInt(2, obj.getFishCountLeft());
			ps.setInt(3, obj.getFishCountRight());
			ps.setInt(4, obj.getBetRoundId());
			ps.setString(5, obj.getFishGetProJsonStr());
		  }});
		return obj;
	}

	@Override
	public BetRoundResult getBetResultByRoundId(int betRoundId) {
		// TODO Auto-generated method stub
		return this.getByKey("betRoundId", betRoundId);
	}

	@Override
	public void updateBetRoundResult(BetRoundResult obj) {
		// TODO Auto-generated method stub
		Map<String, Object> set = new HashMap<>();
		set.put("fishCountLeft", obj.getFishCountLeft());
		set.put("fishCountRight", obj.getFishCountRight());
		Map<String, Object> query = new HashMap();
		query.put("betRoundId", obj.getBetRoundId());
		this.updateOne(set, query);
	}

}
