package com.fish.server.web.dao.impl.bet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import com.fish.server.web.base.dao.BaseDaoImpl;
import com.fish.server.web.bean.bet.UserBetRocord;
import com.fish.server.web.dao.bet.UserBetRecordDao;

@Repository("userBetRecordDao")
public class UserBetRecordDaoImpl extends BaseDaoImpl<UserBetRocord> implements UserBetRecordDao {

	public UserBetRecordDaoImpl() {

		setTableName("fish_user_betrecord");
	}

	@Override
	public UserBetRocord createUserBetRecord(final UserBetRocord obj) {
		final String addSql = "insert into " + this.tableName
				+ "(`createTime`,`betCount1`,`betCount2`,`moneyType`,`betResult`,`account`,`betRoundId`,`betCount3`) values(?,?,?,?,?,?,?,?)";

		int id = insert(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(addSql, new String[] { "id" });
				ps.setObject(1, obj.getCreateTime());
				ps.setInt(2, obj.getBetCount1());
				ps.setInt(3, obj.getBetCount2());
				ps.setString(4, obj.getMoneyType());
				ps.setInt(5, obj.getBetResult());
				ps.setString(6, obj.getAccount());
				ps.setInt(7, obj.getBetRoundId());
				ps.setInt(8, obj.getBetCount3());
				return ps;
			}
		});
		obj.setId(id);
		return obj;
	}

	@Override
	public int getTotalBetCount(String betType,int betRoundId) {
		String sql="select sum(?) from " + this.tableName + " where betRoundId= ?";
		
		int sum = 0;
		try{
			Object[] args = new Object[] {betType,betRoundId};
			Integer o  = jdbcTemplate.queryForObject(sql,args,Integer.class); 
			if(o != null)
				sum = o.intValue();
		}catch(Error e)
		{
			e.printStackTrace();
		}
		return sum;
	}




	@Override
	public List<String> getBetUsersByRoundId(int betRoundId) {
		// TODO Auto-generated method stub
		String sql="select distinct(account) from " + this.tableName + " where betRoundId = ?";
		List<String> list = new ArrayList<String>();
		try{
			Object[] args = new Object[] {betRoundId};
			Object o  =	jdbcTemplate.queryForList(sql,args,String.class); 
			if(o != null)
				list = (List<String>) o;
		}catch(Error e)
		{
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int getBetCountByAccount(String betType, String account,
			int betRoundId) {
			String sql="select sum("+betType+") from " + this.tableName + " where betRoundId= ? and account=?";
		
		int sum = 0;
		try{
			Object[] args = new Object[] {betRoundId,account};
			Integer o  = jdbcTemplate.queryForObject(sql,args,Integer.class); 
			if(o != null)
				sum = o.intValue();
		}catch(Error e)
		{
			e.printStackTrace();
		}
		return sum;
	}

	

}
