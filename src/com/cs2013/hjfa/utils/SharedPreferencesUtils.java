package com.cs2013.hjfa.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.cs2013.hjfa.pojo.User;

/**
 * 
 *当前类的作用是用于保存用户信息
 */
public class SharedPreferencesUtils {
	private static final String USER_INFO="userInfo";//保存的文件名称
	
	private static final String USER_ID="user_Id";//用户的ID的key
	private static final String USER_NAME="user_name";//用户的ID的key
	
	private static final String USER_PWD="user_pwd";//用户的密码
	
	public static boolean saveLoginInfo(User user, Context context) {
		SharedPreferences preferences = context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		Editor editor=preferences.edit();
		editor.putString(USER_ID, user.getId());
		editor.putString(USER_NAME, user.getUsername());
		editor.commit();
		return true;
	}

}
