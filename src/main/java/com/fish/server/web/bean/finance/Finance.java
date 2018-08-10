package com.fish.server.web.bean.finance;

import java.io.Serializable;
import java.util.Date;

public class Finance implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int INCOME = 1;// 收入
	public static final int OUTCOME = 0;//消费

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

	

	private int trxMoney;
	public int getTrxMoney() {
		return trxMoney;
	}

	public void setTrxMoney(int trxMoney) {
		this.trxMoney = trxMoney;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}



	private int type;//0消费，1，收入
	private String remark;
	private Date createTime;
	private String account;
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
