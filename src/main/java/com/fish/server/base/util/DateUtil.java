package com.fish.server.base.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String getDateString(String format) {
		String str = new SimpleDateFormat(format).format(new Date());
		return str;
	}

}
