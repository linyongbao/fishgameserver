package com.fish.server.web.util;

public class Page {

	private boolean last;

	/**
	 * @return the last
	 */
	public boolean getLast() {
		if (currentPage == totalPageSize)
			last = true;
		else
			last = false;
		return last;
	}

	/**
	 * @return the first
	 */
	public boolean getFirst() {
		if (currentPage == 1)
			first = true;
		else
			first = false;
		return first;
	}

	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage
	 *            the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return the totalPageSize
	 */
	public int getTotalPageSize() {
		return totalPageSize;
	}

	/**
	 * @param totalPageSize
	 *            the totalPageSize to set
	 */
	public void setTotalPageSize(int totalPageSize) {
		this.totalPageSize = totalPageSize;
	}

	private boolean first;
	private int currentPage = 1;
	private int totalPageSize;
	private int totalResultSize;
	private int perPageSize = 15;

	/**
	 * @return the perPageSize
	 */
	public int getPerPageSize() {
		return perPageSize;
	}

	/**
	 * @param perPageSize
	 *            the perPageSize to set
	 */
	public void setPerPageSize(int perPageSize) {
		this.perPageSize = perPageSize;
	}

	/**
	 * @return the totalResultSize
	 */
	public int getTotalResultSize() {
		return totalResultSize;
	}

	/**
	 * @param totalResultSize
	 *            the totalResultSize to set
	 */
	public void setTotalResultSize(int totalResultSize) {
		this.totalResultSize = totalResultSize;
	}

}
