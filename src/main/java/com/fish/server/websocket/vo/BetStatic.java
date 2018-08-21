package com.fish.server.websocket.vo;

import java.io.Serializable;

public class BetStatic implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5885113862663806161L;
	private int betCount1;//买左
	public int getBetCount1() {
		return betCount1;
	}
	public void setBetCount1(int betCount1) {
		this.betCount1 = betCount1;
	}
	public int getBetCount2() {
		return betCount2;
	}
	public void setBetCount2(int betCount2) {
		this.betCount2 = betCount2;
	}
	public int getBetCount3() {
		return betCount3;
	}
	public void setBetCount3(int betCount3) {
		this.betCount3 = betCount3;
	}
	public String getMoneyType() {
		return moneyType;
	}
	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}
	private int betCount2;//买平
	private int betCount3;//岽右
	private String moneyType;
}
