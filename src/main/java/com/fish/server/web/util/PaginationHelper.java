package com.fish.server.web.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

public class PaginationHelper<E> {
	public CurrentPage<E> fetchPage(final JdbcTemplate jt, final String sql, final Object args[], final Page page,
			final BeanPropertyRowMapper<E> rowMapper) {
		// determine how many rows are available
		final String sqlCountRows = "select  COUNT(1)  from  " + sql.substring(sql.indexOf("from") + 4);
		final int rowCount = jt.queryForObject(sqlCountRows, args, Integer.class);

		// calculate the number of pages
		int pageCount = rowCount / page.getPerPageSize();
		if (rowCount > page.getPerPageSize() * pageCount) {
			pageCount++;
		}
		// create the page object
		final CurrentPage<E> cpage = new CurrentPage<E>();
		cpage.setCurrentPage(page.getCurrentPage());
		cpage.setTotalPageSize(pageCount);
		cpage.setTotalResultSize(rowCount);

		page.setCurrentPage(page.getCurrentPage());
		page.setTotalPageSize(pageCount);
		page.setTotalResultSize(rowCount);

		// fetch a single page of results
		final int startRow = (page.getCurrentPage() - 1) * page.getPerPageSize();
		jt.query(sql, args, new ResultSetExtractor() {
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				final List<E> pageItems = cpage.getPageItems();
				int currentRow = 0;
				while (rs.next() && currentRow < startRow + page.getPerPageSize()) {
					if (currentRow >= startRow) {
						pageItems.add(rowMapper.mapRow(rs, currentRow));
					}
					currentRow++;
				}
				return cpage;
			}
		});

		return cpage;
	}

	public CurrentPage<Map> fetchPage(final JdbcTemplate jt, final String sql, final Object args[], final Page page) {
		// determine how many rows are available
		final String sqlCountRows = "select  COUNT(1)  from  " + sql.substring(sql.indexOf("from") + 4);
		final int rowCount = jt.queryForObject(sqlCountRows, args, Integer.class);

		// calculate the number of pages
		int pageCount = rowCount / page.getPerPageSize();
		if (rowCount > page.getPerPageSize() * pageCount) {
			pageCount++;
		}
		// create the page object
		final CurrentPage<Map> cpage = new CurrentPage<Map>();
		cpage.setCurrentPage(page.getCurrentPage());
		cpage.setTotalPageSize(pageCount);
		cpage.setTotalResultSize(rowCount);

		page.setCurrentPage(page.getCurrentPage());
		page.setTotalPageSize(pageCount);
		page.setTotalResultSize(rowCount);

		// fetch a single page of results
		final int startRow = (page.getCurrentPage() - 1) * page.getPerPageSize();
		jt.query(sql, args, new ResultSetExtractor() {
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				final List<Map> pageItems = cpage.getPageItems();
				int currentRow = 0;
				while (rs.next() && currentRow < startRow + page.getPerPageSize()) {
					if (currentRow >= startRow) {

						ResultSetMetaData md = rs.getMetaData();
						int columnCount = md.getColumnCount(); // Map rowData;
						Map rowData = new HashMap();
						for (int i = 1; i <= columnCount; i++) {

							rowData.put(md.getColumnName(i), rs.getObject(i));

						}

						pageItems.add(rowData);
					}
					currentRow++;
				}
				return cpage;
			}
		});

		return cpage;
	}
}