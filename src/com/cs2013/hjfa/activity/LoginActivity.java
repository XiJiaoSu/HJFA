package com.cs2013.hjfa.activity;

import java.lang.reflect.Type;

import com.cs2013.hjfa.R;
import com.cs2013.hjfa.pojo.User;
import com.cs2013.hjfa.pojo.json.JsonObject;
import com.cs2013.hjfa.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ��½���湦�ܣ��̳�BaseActivity,��ʱ����Ҫ��дonCreate����
 * 
 */
public class LoginActivity extends BaseActivity {
	// �û���½�ɹ����ȡ��User���ݶ���
	private User user = null;

	private Intent intent;
	
	private EditText mETUserName = null;
	private EditText mETPwd = null;
	private Button mBtLogin = null;
	private TextView mTvChangpsd,mTvRegister;
	@Override
	protected void initViews() {
		// ��һ��������setContentView
		setContentView(R.layout.activity_login);
		mETUserName = (EditText) findViewById(R.id.et_username);
		mETPwd = (EditText) findViewById(R.id.et_pwd);
		mBtLogin = (Button) findViewById(R.id.bt_login);
		mTvChangpsd = (TextView) findViewById(R.id.tv_forgetpsd);
		mTvRegister = (TextView) findViewById(R.id.tv_newuser);
	}

	@Override
	protected void initEvents() {
		mBtLogin.setOnClickListener(this);
		mTvChangpsd.setOnClickListener(this);
		mTvRegister.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_login:
			User u=new User();
			u.setUsername(mETUserName.getText().toString().trim());
			u.setPassword(mETPwd.getText().toString().trim());
			String json=new Gson().toJson(u);
			connServer(Constants.URL_LOGIN, json, Constants.CODE_LOGIN);
			break;
		case R.id.tv_forgetpsd:
			intent = new Intent(this,ChangePsdActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_newuser:
			intent = new Intent(this,RegisterActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	public void onResponse(String res, int code) {
		Log.e("Log", res);
		Gson gson = new Gson();// ����Gson�������ڽ������ص�json�ַ���
		Type objectType = null;
		switch (code) {
		case Constants.CODE_LOGIN:// ��½���󷵻ص�����
			// ��JSON����תΪ���󣬿���ʹ�÷���
			objectType = new TypeToken<JsonObject<User>>() {
			}.getType();
			// ��ʼת��
			JsonObject<User> object = gson.fromJson(res, objectType);
			Log.e("Log", object.getCode()+"");
			Log.e("Log", object.getMsg()+"");
			if (object.getCode() == Constants.JSON_OK) {// JSON�����з��ص���������
				user = object.getResult();// ��ȡ��������
				Log.e("Log", user.getUsername());
				msg = null;
			} else {
				msg = object.getMsg();
				user = null;
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void successResult(int code) {
		switch (code) {
		case Constants.CODE_LOGIN:
			Log.e("Log", "success");
			if (user != null && msg == null) {// ��¼�ɹ�����ҳ�����ת
				Log.e("Log", "intent");
				Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(intent);
				finish();
			}
			toastShow(msg, Toast.LENGTH_SHORT);
			break;

		default:
			break;
		}
	}

	@Override
	public void errorResult(int code) {
		switch (code) {
		case Constants.CODE_LOGIN:
			// ��ǰ������ָ������������ɹ����������������磺�û��������������
			if (user == null && msg != null) {
				toastShow(msg, Toast.LENGTH_SHORT);
			}
			break;

		default:
			break;
		}
	}

}
