package com.fish.server.web.bean.bet;

import java.util.Date;

public class UserBetRocord {
	private int betRoundId;
	private int id;
	private String account;
	private String moneyType;//trx
	private double betCount;//下注数量
	private Date createTime;
	public int getBetRoundId() {
		return betRoundId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setBetRoundId(int betRoundId) {
		this.betRoundId = betRoundId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getMoneyType() {
		return moneyType;
	}
	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}
	public double getBetCount() {
		return betCount;
	}
	public void setBetCount(double betCount) {
		this.betCount = betCount;
	}

	public int getWinType() {
		return winType;
	}
	public void setWinType(int winType) {
		this.winType = winType;
	}
	public int getBetResult() {
		return betResult;
	}
	public void setBetResult(int betResult) {
		this.betResult = betResult;
	}
	private int winType;//玩法，0左赢，1，平，2，右赢
	private int betResult;//结果,0还没结果，1，中，2，不中
}
