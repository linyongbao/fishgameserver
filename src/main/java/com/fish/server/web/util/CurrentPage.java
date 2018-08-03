package com.fish.server.web.util;

import java.util.ArrayList;
import java.util.List;

public class CurrentPage<E> extends Page {

	private List<E> pageItems = new ArrayList<E>();

	public void setPageItems(List<E> pageItems) {
		this.pageItems = pageItems;
	}

	public List<E> getPageItems() {
		return pageItems;
	}

	public int getTotalResultSize() {
		if (pageItems != null)
			return pageItems.size();
		return 0;
	}
}