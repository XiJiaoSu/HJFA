package com.cs2013.hjfa.utils;

import com.xjtu.activity.ZXingLibrary;

import android.app.Application;

/**
 * �Զ���Application,��ǰApplication����������Ӧ�õ���
 *
 */
public class HApplication extends Application{

	@Override
	public void onCreate() {
		super.onCreate();
		ZXingLibrary.initDisplayOpinion(this);
	}
}
