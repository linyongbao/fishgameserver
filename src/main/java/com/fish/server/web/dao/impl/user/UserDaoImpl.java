package com.fish.server.web.dao.impl.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import com.fish.server.web.base.dao.BaseDaoImpl;
import com.fish.server.web.bean.user.User;
import com.fish.server.web.dao.user.UserDao;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	public UserDaoImpl() {

		setTableName("fish_user");
	}

	@Override
	public User getUserByAccount(String account) {
		// TODO Auto-generated method stub
		User user = this.getByKey("account", account);
		return user;
	}

	@Override
	public List<User> findAll() {

		List<User> customers = this.findAll();

		return customers;
	}

	public User saveUser(final User user) {
		final String addSql = "insert into " + this.tableName
				+ "(`account`,`nick`,`logo`,`sex`,`createTime`,`avalible`,`mobile`) values (?,?,?,?,?,?,?)";
		int id = insert(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(addSql, Statement.RETURN_GENERATED_KEYS);
				ps.setObject(1, user.getAccount());
				ps.setObject(2, user.getNick());
				ps.setObject(3, user.getLogo());
				ps.setObject(4, user.getSex());
				ps.setObject(5, user.getCreateTime());
				ps.setObject(6, user.getAvalible());
				ps.setObject(7, user.getMobile());
				return ps;
			}
		});
		user.setId(id);
		return user;

	}

	

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub

		Map<String, Object> set = new HashMap();
		set.put("nick", user.getNick());
		set.put("sex", user.getSex());
		set.put("logo", user.getLogo());
		Map<String, Object> query = new HashMap();
		query.put("id", user.getId());
		this.updateOne(set, query);
	}

	@Override
	public User getUserByUid(int uid) {
		// TODO Auto-generated method stub
		return getByKey("id", uid);
	}

}
