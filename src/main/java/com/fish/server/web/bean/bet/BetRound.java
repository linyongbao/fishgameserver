package com.fish.server.web.bean.bet;

import java.util.Date;

public class BetRound {

	private int id;
	private Date createTime;
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	private int betTimeLeft = 0;//
	public int getBetTimeLeft() {
		return betTimeLeft;
	}
	public void setBetTimeLeft(int betTimeLeft) {
		this.betTimeLeft = betTimeLeft;
	}
	public int getGameTimeLeft() {
		return gameTimeLeft;
	}
	public void setGameTimeLeft(int gameTimeLeft) {
		this.gameTimeLeft = gameTimeLeft;
	}
	private int gameTimeLeft = 0;
	private int gameTimeTotal= 20;//
	public int getGameTimeTotal() {
		return gameTimeTotal;
	}
	public void setGameTimeTotal(int gameTimeTotal) {
		this.gameTimeTotal = gameTimeTotal;
	}
	public int getBetTimeTotal() {
		return betTimeTotal;
	}
	public void setBetTimeTotal(int betTimeTotal) {
		this.betTimeTotal = betTimeTotal;
	}
	private int betTimeTotal= 20;//20秒下注
	private int state = 0;//-1,无状态 ,0下注阶段，1，钓鱼阶段  2，结束阶段
	
	public int getBetLeftWinValue() {
		return betLeftWinValue;
	}
	public void setBetLeftWinValue(int betLeftWinValue) {
		this.betLeftWinValue = betLeftWinValue;
	}
	public int getBetPeaceValue() {
		return betPeaceValue;
	}
	public void setBetPeaceValue(int betPeaceValue) {
		this.betPeaceValue = betPeaceValue;
	}
	public int getBetRigtWinValue() {
		return betRigtWinValue;
	}
	public void setBetRigtWinValue(int betRigtWinValue) {
		this.betRigtWinValue = betRigtWinValue;
	}
	private int betLeftWinValue = 0;
	private int betPeaceValue = 0;
	private int betRigtWinValue = 0;
	
}
