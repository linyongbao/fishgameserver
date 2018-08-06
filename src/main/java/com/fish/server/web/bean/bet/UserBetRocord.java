package com.fish.server.web.bean.bet;

import java.util.Date;

public class UserBetRocord {
	private int betRoundId;
	private int id;
	private String account;
	private String moneyType;//trx
	private double betCount;//下注数量
	private Date createDate;
	private int winType;//玩法，0左赢，1，平，2，右赢
	private int betResult;//结果,0还没结果，1，中，2，不中
}
