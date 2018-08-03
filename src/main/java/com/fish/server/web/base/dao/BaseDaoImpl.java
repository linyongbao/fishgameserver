package com.fish.server.web.base.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.fish.server.web.util.CurrentPage;
import com.fish.server.web.util.Page;
import com.fish.server.web.util.PaginationHelper;

public abstract class BaseDaoImpl<T> {

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	protected String tableName;

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName
	 *            the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	protected Class<? extends T> type;

	/**
	 * 构造方法，获取运行时的具体实体对象
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BaseDaoImpl() {

		// 获取真实泛型数据类型
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class) pt.getActualTypeArguments()[0];
	}

	/**
	 * 插入一条记录
	 *
	 * @param entity
	 */

	public int insert(PreparedStatementCreator params) {

		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.jdbcTemplate.update(params, keyHolder);
		return keyHolder.getKey().intValue();
	}

	/**
	 * 插入一条记录
	 *
	 * @param entity
	 */

	public int insert(String sql, PreparedStatementSetter params) {

		return this.jdbcTemplate.update(sql, params);
	}

	/**
	 * 更新记录
	 * 
	 * @param entity
	 */

	public int[] updateList(final String sql, List<Object[]> params) {
		int[] updateCounts = jdbcTemplate.batchUpdate(sql, params);
		return updateCounts;
	}

	/**
	 * 更新一条记录
	 * 
	 * @param entity
	 */

	public int updateOne(Map<String, Object> set, Map<String, Object> query) {
		StringBuilder sql2 = new StringBuilder("update " + this.tableName + " as b set ");

		for (String key : set.keySet()) {
			sql2.append("b." + key + " = ");
			if (set.get(key) instanceof String)
				sql2.append("'" + set.get(key) + "'");
			else
				sql2.append(set.get(key));
			sql2.append(",");
		}
		sql2.deleteCharAt(sql2.lastIndexOf(","));
		sql2.append(" where ");

		for (String key : query.keySet()) {
			sql2.append("b." + key + " = ");
			sql2.append(query.get(key));
			sql2.append(",");
		}

		sql2.deleteCharAt(sql2.lastIndexOf(","));

		return this.jdbcTemplate.update(sql2.toString());
	}

	public int updateOne(String addSql, Object[] args) {
		// TODO Auto-generated method stub
		return this.jdbcTemplate.update(addSql, args);
	}

	/**
	 * 删除记录
	 *
	 * @param id
	 */

	public void deleteByKey(String keyName, Object value) {

		String sql = "DELETE FROM " + tableName + " WHERE " + keyName + " = ?";
		jdbcTemplate.update(sql, value);
	}

	/**
	 * 得到记录
	 *
	 * @param id
	 * @return
	 */

	public T getByKey(String keyName, Object keyValue) {

		final Class<? extends T> tempType = type;

		try {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			T find = (T) this.jdbcTemplate.queryForObject("select * from " + tableName + " where " + keyName + " = ?",
					new Object[] { keyValue }, new BeanPropertyRowMapper(tempType));
			return find;
		} catch (DataAccessException e) {
			return null;
		}

	}

	/**
	 * 查询所有记录
	 * 
	 * @return
	 */

	public List<T> findAll() {
		final Class<?> tempType = type;
		String sql = "SELECT * FROM " + tableName;

		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<T> finds = jdbcTemplate.query(sql, new BeanPropertyRowMapper(tempType));

		return finds;
	}

	/**
	 * 查询记录数
	 * 
	 * @param entity
	 * @return
	 */
	public CurrentPage<T> findByPage(final String sql, final Object args[], final Page page,
			final BeanPropertyRowMapper<T> rowMapper) {

		PaginationHelper<T> ph = new PaginationHelper<T>();
		return ph.fetchPage(this.jdbcTemplate, sql, args, page, rowMapper);
	}

	/**
	 * 查询记录数
	 * 
	 * @param entity
	 * @return
	 */
	public CurrentPage<Map> findByPage(final String sql, final Object args[], final Page page) {

		PaginationHelper<Object[]> ph = new PaginationHelper<Object[]>();
		return ph.fetchPage(this.jdbcTemplate, sql, args, page);
	}

	/**
	 * 查询所有记录
	 * 
	 * @return
	 */

	public List<T> findAll(final String sql, final Object args[]) {
		final Class<?> tempType = type;

		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<T> finds = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper(tempType));

		return finds;
	}

}