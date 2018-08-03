package com.fish.server.web.dao.impl.userlog;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import com.fish.server.web.base.dao.BaseDaoImpl;
import com.fish.server.web.bean.userlog.UserLoginLog;
import com.fish.server.web.dao.userlog.UserLoginLogDao;
import com.fish.server.web.util.CurrentPage;
import com.fish.server.web.util.Page;

@Repository("userLoginLogDao")
public class UserLoginLogDaoImpl extends BaseDaoImpl<UserLoginLog> implements UserLoginLogDao {

	public UserLoginLogDaoImpl() {

		setTableName("fish_user_login_log");
	}

	@Override
	public CurrentPage<UserLoginLog> queryUserLoginLogPage(int uid, Page page) {
		String sql = "select * from " + this.tableName + " where userId = ?";
		Object[] args = new Object[] { uid };
		BeanPropertyRowMapper<UserLoginLog> rowMapper = new BeanPropertyRowMapper(UserLoginLog.class);
		return this.findByPage(sql, args, page, rowMapper);
	}

	@Override
	public UserLoginLog saveUserLoginLog(final UserLoginLog loginLog) {
		// TODO Auto-generated method stub
		final String addSql = "insert into " + this.tableName
				+ "(`loginTime`,`ip`,`userId`,`osName`,`type`) values (?,?,?,?,?)";

		int id = insert(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(addSql, Statement.RETURN_GENERATED_KEYS);
				ps.setObject(1, loginLog.getLoginTime());
				ps.setObject(2, loginLog.getIp());
				ps.setObject(3, loginLog.getUserId());
				ps.setObject(4, loginLog.getOsName());
				ps.setObject(5, loginLog.getType());
				return ps;
			}
		});
		loginLog.setId(id);
		return loginLog;
	}

	@Override
	public UserLoginLog getLastLoginLog(int uid, int type) {
		// TODO Auto-generated method stub
		String sql = "select * from " + this.tableName
				+ " where userId = ?  and type = ? order by loginTime desc limit 1";
		Object[] args = new Object[] { uid, type };
		List<UserLoginLog> list = this.findAll(sql, args);
		if (list != null && !list.isEmpty())
			return list.get(0);
		return null;
	}

}
