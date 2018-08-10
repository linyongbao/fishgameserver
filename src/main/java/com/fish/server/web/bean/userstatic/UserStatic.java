package com.fish.server.web.bean.userstatic;

import java.io.Serializable;

public class UserStatic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int trxMoney = 100000;

	public int getTrxMoney() {
		return trxMoney;
	}

	public void setTrxMoney(int trxMoney) {
		this.trxMoney = trxMoney;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the playTime
	 */
	public int getPlayTime() {
		return playTime;
	}
	/**
	 * @param playTime the playTime to set
	 */
	public void setPlayTime(int playTime) {
		this.playTime = playTime;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	
	private int playTime;
	private String account;
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	private int level;
}
