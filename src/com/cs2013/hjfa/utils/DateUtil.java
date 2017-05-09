package com.cs2013.hjfa.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static String getTime(String time)
	{
		long longTime = Long.valueOf(time);
		Date date = new Date(longTime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	
	public static String getTime2(String time)
	{
		long longTime = Long.valueOf(time);
		Date date = new Date(longTime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(date);
	}
}
