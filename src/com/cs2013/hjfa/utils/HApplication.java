package com.cs2013.hjfa.utils;

import android.app.Application;

import com.cs2013.hjfa.pojo.User;
import com.xjtu.activity.ZXingLibrary;

/**
 * �Զ���Application,��ǰApplication����������Ӧ�õ���
 * 
 */
public class HApplication extends Application {

	private static HApplication application = null;
	private User user=null;
	private  boolean isLogin = false;// �����ж��û��Ƿ��½

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
	 * �жϵ�ǰ�������Ƿ��½
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
	 * �û��ĵ�½�ɹ�֮����õ�ǰ�ķ������û�����Ϣ����
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
