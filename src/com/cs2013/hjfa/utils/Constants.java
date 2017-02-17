package com.cs2013.hjfa.utils;

public class Constants {
	private static final String URL_PREFFIX = "http://118.89.106.93:8080/library";
	public static final String URL_LOGIN = URL_PREFFIX + "/user/login";// 用户登陆的接口
	public static final int STATE_OK = 0;// 请求网络成功
	public static final int STATE_ERROR = -1000;// 请求网络失败
	public static final int JSON_OK=1;//返回的JSON字符串中数据正确的code
	public static final int JSON_ERROR=-1;//返回的JSON字符串中数据有误的code
	//请求验证码
	public static final int CODE_LOGIN = 100;//用户登陆
	public static final int CODE_LOGINCOOD = 101;// 快速登录
	public static final int CODE_SYS_NOTI = 200;// 系统消息
	
}