package com.fish.server.web.bean.finance;

import java.io.Serializable;
import java.util.Date;

public class Finance implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int INCOME = 1;// 收入
	public static final int OUTCOME = 0;//消费
	private int m_coin;

	/**
	 * @return the m_coin
	 */
	public int getM_coin() {
		return m_coin;
	}

	/**
	 * @param m_coin
	 *            the m_coin to set
	 */
	public void setM_coin(int m_coin) {
		this.m_coin = m_coin;
	}

	/**
	 * @return the g_coin
	 */
	public int getG_coin() {
		return g_coin;
	}

	/**
	 * @param g_coin
	 *            the g_coin to set
	 */
	public void setG_coin(int g_coin) {
		this.g_coin = g_coin;
	}



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
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	private int g_coin;
	private int type;//0消费，1，收入
	private String remark;
	private Date createTime;
	private int uid;
	private int id;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
}
