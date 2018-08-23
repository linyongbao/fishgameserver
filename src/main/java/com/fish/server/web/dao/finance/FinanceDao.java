package com.fish.server.web.dao.finance;

import java.util.List;

import com.fish.server.web.bean.finance.Finance;
import com.fish.server.web.bean.finance.query.QueryFinance;
import com.fish.server.web.util.CurrentPage;
import com.fish.server.web.util.Page;

public interface FinanceDao {

	public Finance getFinanceById(int id);

	public Finance saveFinance(Finance obj);

	public CurrentPage<Finance> queryFinanceListPage(QueryFinance queryFinance, Page page);

	public void saveFinances(List<Finance> objs);


}
