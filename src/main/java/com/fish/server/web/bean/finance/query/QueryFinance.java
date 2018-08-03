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

	/**
	 * @return the uid
	 */
	public int getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(int uid) {
		this.uid = uid;
	}

	private int uid;

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

	/**
	 * @return the beginCreateTime
	 */
	public Date getBeginCreateTime() {
		return beginCreateTime;
	}

	/**
	 * @param beginCreateTime
	 *            the beginCreateTime to set
	 */
	public void setBeginCreateTime(Date beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}

	/**
	 * @return the endCreateTime
	 */
	public Date getEndCreateTime() {
		return endCreateTime;
	}

	/**
	 * @param endCreateTime
	 *            the endCreateTime to set
	 */
	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	private String keyWord;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date beginCreateTime;// 查询 开始注册时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endCreateTime;// 查询 结束注册时间
}