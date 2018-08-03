package com.fish.server.web.dao.impl.userstatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import com.fish.server.web.base.dao.BaseDaoImpl;
import com.fish.server.web.bean.userstatic.UserStatic;
import com.fish.server.web.dao.userstatic.UserStaticDao;

@Repository("userStaticDao")
public class UserStaticDaoImpl extends BaseDaoImpl<UserStatic> implements UserStaticDao {

	public UserStaticDaoImpl() {

		setTableName("fish_user_static");
	}

	@Override
	public UserStatic getUserStatic(int id) {
		// TODO Auto-generated method stub
		return this.getByKey("id", id);
	}

	@Override
	public void updateUserStatic(UserStatic obj) {

		Map<String, Object> set = new HashMap();
		set.put("g_coin", obj.getG_coin());
		set.put("m_coin", obj.getM_coin());
		set.put("level", obj.getLevel());
		set.put("playTime", obj.getPlayTime());
		Map<String, Object> query = new HashMap();
		query.put("id", obj.getId());
		this.updateOne(set, query);
	}

	@Override
	public UserStatic saveUserStatic(final UserStatic obj) {
		// TODO Auto-generated method stub
		final String addSql = "insert into " + this.tableName
				+ "(`uid`,`level`,`m_coin`,`g_coin`,'playTime') values(?,?,?,?,?)";

		int id = insert(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(addSql, new String[] { "id" });
				ps.setInt(1, obj.getUid());
				ps.setInt(2, obj.getLevel());
				ps.setInt(3, obj.getM_coin());
				ps.setInt(4, obj.getG_coin());
				ps.setInt(5, obj.getPlayTime());
				return ps;
			}
		});
		obj.setId(id);
		return obj;
	}


	@Override
	public UserStatic getUserStaticByUid(int uid) {
		// TODO Auto-generated method stub
		return this.getByKey("uid", uid);
	}



}
