package com.fish.server.web.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.server.web.bean.finance.Finance;
import com.fish.server.web.bean.finance.query.QueryFinance;
import com.fish.server.web.bean.userstatic.UserStatic;
import com.fish.server.web.dao.finance.FinanceDao;
import com.fish.server.web.service.FinanceService;
import com.fish.server.web.service.UserStaticService;
import com.fish.server.web.util.CurrentPage;
import com.fish.server.web.util.Page;

@Service("financeService")
public class FinanceServiceImpl implements FinanceService {

	@Autowired
	private FinanceDao financeDao;

	@Override
	public Finance addFinance(Finance obj) {
		// TODO Auto-generated method stub
		return financeDao.saveFinance(obj);
	}

	@Override
	public CurrentPage<Finance> queryFinancePage(QueryFinance query, Page page) {
		// TODO Auto-generated method stub
		return financeDao.queryFinanceListPage(query, page);
	}

	@Override
	public Finance getFinanceById(int id) {
		// TODO Auto-generated method stub
		return financeDao.getFinanceById(id);
	}

}
