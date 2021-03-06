package com.cs2013.hjfa.utils;

public class Constants {
//	private static final String URL_PREFFIX = "http://192.168.10.86:8080/library";
	private static final String URL_PREFFIX = "http://118.89.106.93:8080/library";
	public static final String URL_LOGIN = URL_PREFFIX + "/user/login";// 用户登陆的接口
	public static final int STATE_OK = 0;// 请求网络成功
	public static final int STATE_ERROR = -1000;// 请求网络失败
	public static final int JSON_OK=200;//返回的JSON字符串中数据正确的code
	public static final int JSON_ERROR=-1;//返回的JSON字符串中数据有误的code
	//请求验证码
	public static final int CODE_LOGIN = 100;//用户登陆
	public static final int CODE_LOGINCOOD = 101;// 快速登录
	public static final int CODE_SYS_NOTI = 200;// 系统消息
	
	//Activity 跳转请求码
	public static final int RESULT_OK=1;//MainActivity与CaptureActivity之间的跳转编码
	
	//咨询列表请求地址
	public static final String URL_MESSAGE = URL_PREFFIX + "/msg/list";
	public static final int CODE_MESSAGE = 102;//消息列表
	
	//图书馆信息请求地址
	public static final String URL_LIBRARY_INFORMATION = URL_PREFFIX + "/library/list";
	public static final int CODE_LIBRARY_INFORMATION = 103;//图书馆信息列表
	
	//图书馆位置请求地址
	public static final String URL_LIBRARY_FLOOR_SEAT_INFORMATION = URL_PREFFIX + "/seat/level";
	public static final int CODE_LIBRARY_FLOOR_SEAT_INFORMATION = 104;//图书馆座位信息列表
	
	//图书馆座位预定地址
	public static final String URL_LIBRARY_FLOOR_SEAT_ORDER = URL_PREFFIX + "/order/add";
	public static final int CODE_LIBRARY_FLOOR_SEAT_ORDER = 105;//图书馆座位信息列表
	
	public static final String URL_ORDER_HISTORY=URL_PREFFIX+"/order/list";//查询自己的订单
	public static final int CODE_ORDER_HISTORY=108;
	
	public static final String URL_ORDER_CONFIRM=URL_PREFFIX+"/order/confirm";
	public static final int CODE_ORDER_CONFIRM=110;
	
	public static final String URL_USER_REGISTER=URL_PREFFIX+"/user/register";
	public static final int CODE_USER_REGISTER=120;
	
	public static final String URL_ORDER_CANCLE = URL_PREFFIX + "/order/cancle";
	public static final int CODE_ORDER_CANCLE = 130;//取消座位预定
	
}
