package com.fish.server.web.service;

import com.fish.server.web.bean.finance.Finance;
import com.fish.server.web.bean.finance.query.QueryFinance;
import com.fish.server.web.util.CurrentPage;
import com.fish.server.web.util.Page;

public interface FinanceService {

	public Finance addFinance(Finance obj);

	CurrentPage<Finance> queryFinancePage(QueryFinance query, Page page);

	public Finance getFinanceById(int id);

}
