package com.cs2013.hjfa.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.cs2013.hjfa.pojo.User;

/**
 * 
 *��ǰ������������ڱ����û���Ϣ
 */
public class SharedPreferencesUtils {
	private static final String USER_INFO="userInfo";//������ļ�����
	
	private static final String USER_ID="user_Id";//�û���ID��key
	private static final String USER_NAME="user_name";//�û���ID��key
	
	private static final String USER_PWD="user_pwd";//�û�������
	
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
