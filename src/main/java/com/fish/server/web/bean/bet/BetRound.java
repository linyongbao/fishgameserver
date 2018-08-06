package com.fish.server.web.bean.bet;

import java.util.Date;

public class BetRound {

	private int id;
	private Date createDate;
	private int betTime = 20;//20秒下注
	private int gameTime = 10;//10秒过程
	private int fishCount1 = 0;//蓝猫钓鱼
	private int fishCount2 = 0;//红猫钓鱼
	private int state = 0;//0下注阶段，1，钓鱼阶段  2，结束阶段
	
}
