package com.fish.server.web.bean.bet;

import java.util.Date;

public class UserBetRocord {
	private int betRoundId;
	private int id;
	private String account;
	private String moneyType;//trx
	private int betCount1;//左赢
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

	private int betCount2;//平
	private int betCount3;//右赢
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
	
	public int getBetResult() {
		return betResult;
	}
	public void setBetResult(int betResult) {
		this.betResult = betResult;
	}

	private int betResult;//结果,0还没结果，1，中，2，不中
}
