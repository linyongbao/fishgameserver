package com.fish.server.web.dao.impl.finance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import com.fish.server.web.base.dao.BaseDaoImpl;
import com.fish.server.web.bean.finance.Finance;
import com.fish.server.web.bean.finance.query.QueryFinance;
import com.fish.server.web.dao.finance.FinanceDao;
import com.fish.server.web.util.CurrentPage;
import com.fish.server.web.util.Page;

@Repository("financeDao")
public class FinanceDaoImpl extends BaseDaoImpl<Finance> implements FinanceDao {

	public FinanceDaoImpl() {

		setTableName("fish_finance");
	}

	@Override
	public CurrentPage<Finance> queryFinanceListPage(QueryFinance queryFinance, Page page) {
		// TODO Auto-generated method stubList args = new ArrayList();
		StringBuilder sql = new StringBuilder("select * from " + this.tableName + " where 1=1");
		List args = new ArrayList();

		if (!StringUtils.isEmpty(queryFinance.getKeyWord())) {
			sql.append(" and (remark like ?  ) ");
			args.add("%" + queryFinance.getKeyWord() + "%");

		}
	
		if (queryFinance.getAccount() != null) {
			sql.append(" and account = ?");
			args.add(queryFinance.getAccount());
		}

		BeanPropertyRowMapper<Finance> rowMapper = new BeanPropertyRowMapper(Finance.class);
		return this.findByPage(sql.toString(), args.toArray(), page, rowMapper);
	}

	@Override
	public Finance saveFinance(final Finance finance) {
		// TODO Auto-generated method stub
		final String addSql = "insert into " + this.tableName
				+ "(`trxMoney`,`remark`,`type`,`createTime`,`account`) values(?,?,?,?,?)";

		int id = insert(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(addSql, new String[] { "id" });
				ps.setInt(1, finance.getTrxMoney());
				ps.setString(2, finance.getRemark());
				ps.setInt(3, finance.getType());
				ps.setObject(4, finance.getCreateTime());
				ps.setString(5, finance.getAccount());
				return ps;
			}
		});
		finance.setId(id);
		return finance;
	}

	@Override
	public Finance getFinanceById(int id) {
		// TODO Auto-generated method stub
		return this.getByKey("id", id);
	}

}
