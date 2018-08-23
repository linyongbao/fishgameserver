package com.fish.server.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fish.server.web.base.service.BaseService;
import com.fish.server.web.bean.finance.Finance;
import com.fish.server.web.bean.finance.query.QueryFinance;
import com.fish.server.web.dao.finance.FinanceDao;
import com.fish.server.web.service.FinanceService;
import com.fish.server.web.util.CurrentPage;
import com.fish.server.web.util.Page;

@Transactional
@Service("financeService")
public class FinanceServiceImpl extends BaseService implements FinanceService {

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

	@Override
	public void addFinances(List<Finance> objs) {
		// TODO Auto-generated method stub
		
		this.financeDao.saveFinances(objs);
		
	}

}
