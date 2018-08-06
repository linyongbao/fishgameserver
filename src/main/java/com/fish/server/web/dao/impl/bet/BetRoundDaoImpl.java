package com.fish.server.web.dao.impl.bet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import com.fish.server.web.base.dao.BaseDaoImpl;
import com.fish.server.web.bean.bet.BetRound;
import com.fish.server.web.dao.bet.BetRoundDao;

@Repository("betRoundDao")
public class BetRoundDaoImpl extends BaseDaoImpl<BetRound> implements BetRoundDao {

	public BetRoundDaoImpl() {

		setTableName("fish_betround");
	}

	@Override
	public BetRound createBetRound(final BetRound obj) {
		// TODO Auto-generated method stub
		final String addSql = "insert into " + this.tableName
				+ "(`createTime`,`betTimeLeft`,`gameTimeLeft`,`betTimeTotal`,`gameTimeTotal`,`state`) values(?,?,?,?,?,?)";

		int id = insert(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(addSql, new String[] { "id" });
				ps.setObject(1, obj.getCreateTime());
				ps.setInt(2, obj.getBetTimeLeft());
				ps.setInt(3, obj.getGameTimeLeft());
				ps.setInt(4, obj.getBetTimeTotal());
				ps.setInt(5, obj.getGameTimeTotal());
				ps.setInt(6, obj.getState());
				return ps;
			}
		});
		obj.setId(id);
		return obj;
	}

	@Override
	public void updateBetRound(BetRound obj) {
		// TODO Auto-generated method stub
		Map<String, Object> set = new HashMap<>();
		set.put("betTimeLeft", obj.getBetTimeLeft());
		set.put("gameTimeLeft", obj.getGameTimeLeft());
		set.put("state", obj.getState());
		Map<String, Object> query = new HashMap();
		query.put("id", obj.getId());
		this.updateOne(set, query);
	}

	@Override
	public BetRound getLatesBetRound() {
		// TODO Auto-generated method stub
		String sql = "select * from " + this.tableName
				+ "  order by id desc limit 1";
		Object[] args = new Object[] { };
		List<BetRound> list = this.findAll(sql, args);
		if (list != null && !list.isEmpty())
			return list.get(0);
		return null;
	}

	@Override
	public BetRound getBetRoundById(int id) {
		// TODO Auto-generated method stub
		return this.getByKey("id", id);
	}

	

}
