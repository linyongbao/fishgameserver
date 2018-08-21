package com.fish.server.web.bean.bet;

import java.util.Date;

public class BetRoundResult {

	private Date createTime;
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	private String fishGetProJsonStr;


	public String getFishGetProJsonStr() {
		return fishGetProJsonStr;
	}
	public void setFishGetProJsonStr(String fishGetProJsonStr) {
		this.fishGetProJsonStr = fishGetProJsonStr;
	}

	private int fishCountLeft;
	
	public int getFishCountLeft() {
		return fishCountLeft;
	}
	public void setFishCountLeft(int fishCountLeft) {
		this.fishCountLeft = fishCountLeft;
	}
	public int getFishCountRight() {
		return fishCountRight;
	}
	public void setFishCountRight(int fishCountRight) {
		this.fishCountRight = fishCountRight;
	}
	private int fishCountRight;
	private int betRoundId;
	public int getBetRoundId() {
		return betRoundId;
	}
	public void setBetRoundId(int betRoundId) {
		this.betRoundId = betRoundId;
	}
}
