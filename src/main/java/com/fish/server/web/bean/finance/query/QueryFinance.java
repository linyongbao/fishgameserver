package com.fish.server.web.bean.finance.query;


import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class QueryFinance {

	private int type;

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}


	private String account;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the keyWord
	 */
	public String getKeyWord() {
		return keyWord;
	}

	/**
	 * @param keyWord
	 *            the keyWord to set
	 */
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}


	private String keyWord;


}
