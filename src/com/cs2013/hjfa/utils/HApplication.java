package com.cs2013.hjfa.utils;

import android.app.Application;

import com.cs2013.hjfa.pojo.User;
import com.xjtu.activity.ZXingLibrary;

/**
 * 自定义Application,当前Application存在于整个应用当中
 * 
 */
public class HApplication extends Application {

	private static HApplication application = null;
	private User user=null;
	private  boolean isLogin = false;// 用于判断用户是否登陆

	@Override
	public void onCreate() {
		super.onCreate();
		ZXingLibrary.initDisplayOpinion(this);
	}

	

	public static HApplication getInstance() {
		if (application == null) {
			application = new HApplication();
		}
		return application;
	}

	/**
	 * 判断当前的用书是否登陆
	 * 
	 * @return
	 */
	public boolean isUserLogin() {
		return this.isLogin;
	}

	public void logout(){
		this.user=null;
		this.isLogin=false;
	}
	
	/**
	 * 用户的登陆成功之后调用当前的方法将用户的信息保存
	 * 
	 * @param user
	 */
	public void loginSuccess(User user) {
		if (SharedPreferencesUtils.saveLoginInfo(user, this)){
			this.isLogin = true;
			this.user=user;
		}
	}

	public User getUser() {
		return user;
	}

}
