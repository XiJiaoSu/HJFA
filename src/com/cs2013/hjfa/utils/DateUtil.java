package com.cs2013.hjfa.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.util.Log;

public class DateUtil {
	
	public static String getTime(String time)
	{
		long longTime = Long.valueOf(time);
		Date date = new Date(longTime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		return sdf.format(date);
	}
	
	public static String getTime2(long time)
	{
		Log.e("Log ---",time+"");
		Date date = new Date(time);
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf2.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		return sdf2.format(date);
	}
}
