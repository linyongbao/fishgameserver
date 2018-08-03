package com.fish.server.web.bean.userstatic;

import java.io.Serializable;

public class UserStatic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
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
	 * @return the m_coin
	 */
	public int getM_coin() {
		return m_coin;
	}
	/**
	 * @param m_coin the m_coin to set
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
	 * @param g_coin the g_coin to set
	 */
	public void setG_coin(int g_coin) {
		this.g_coin = g_coin;
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
	 * @return the uid
	 */
	public int getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(int uid) {
		this.uid = uid;
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
	private int m_coin;
	private int g_coin;
	private int playTime;
	private int uid;
	private int level;
}
