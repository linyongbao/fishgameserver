package com.fish.server.base.util;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.fish.server.web.vo.NumItem;

public class NumberUtil {

	public static  NumItem getNumItemByList(List list) {

		int perTotal = 0;
		Iterator<NumItem> it = list.iterator();
		while (it.hasNext()) {
			NumItem gem = it.next();
			perTotal += gem.getPriority();
		}
		int random = new Random().nextInt(perTotal);
		int prizeRate = 0;// 中奖率
		it = list.iterator();
		while (it.hasNext()) {
			NumItem gem = it.next();
			prizeRate += gem.getPriority();
			if (random < prizeRate) {
				return gem;
			}

		}
		return null;

	}
}
