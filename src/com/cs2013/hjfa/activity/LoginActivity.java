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
 * 登陆界面功能，继承BaseActivity,此时不需要重写onCreate方法
 * 
 */
public class LoginActivity extends BaseActivity {
	// 用户登陆成功后获取的User数据对象
	public static final String PRE_CLASS = "preClazz";
	private Class preClazz = MainActivity.class;
	private User user = null;

	private Intent intent;

	private EditText mETUserName = null;
	private EditText mETPwd = null;
	private Button mBtLogin = null;
	private Button mBtExit = null;
	private TextView mTvChangpsd, mTvRegister;

	@Override
	protected void initViews() {
		// 第一步必须是setContentView
		setContentView(R.layout.activity_login);
		mETUserName = (EditText) findViewById(R.id.et_username);
		mETPwd = (EditText) findViewById(R.id.et_pwd);
		mBtLogin = (Button) findViewById(R.id.bt_login);
		mTvChangpsd = (TextView) findViewById(R.id.tv_forgetpsd);
		mTvRegister = (TextView) findViewById(R.id.tv_newuser);
		mBtExit=(Button) findViewById(R.id.bt_exit);
		if (getIntent() != null) {
			if (getIntent().getExtras() != null) {
				this.preClazz = (Class) getIntent().getExtras().getSerializable(PRE_CLASS);
			}
		}
	}

	@Override
	protected void initEvents() {
		mBtLogin.setOnClickListener(this);
		mTvChangpsd.setOnClickListener(this);
		mTvRegister.setOnClickListener(this);
		mBtExit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_login:
			User u = new User();
			u.setUsername(mETUserName.getText().toString().trim());
			u.setPassword(mETPwd.getText().toString().trim());
			String json = new Gson().toJson(u);
			connServer(Constants.URL_LOGIN, json, Constants.CODE_LOGIN);
			break;
		case R.id.tv_forgetpsd:
			intent = new Intent(this, ChangePsdActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_newuser:
			intent = new Intent(this, RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.bt_exit:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void onResponse(String res, int code) {
		Log.e("Log", res);
		Gson gson = new Gson();// 创建Gson对象，用于解析返回的json字符串
		Type objectType = null;
		switch (code) {
		case Constants.CODE_LOGIN:// 登陆请求返回的数据
			// 将JSON数据转为对象，可以使用泛型
			objectType = new TypeToken<JsonObject<User>>() {
			}.getType();
			// 开始转型
			JsonObject<User> object = gson.fromJson(res, objectType);
			Log.e("Log", object.getCode() + "");
			Log.e("Log", object.getMsg() + "");
			if (object.getCode() == Constants.JSON_OK) {// JSON数据中返回的数据正常
				user = object.getResult();// 获取对象数据
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
			if (user != null && msg == null) {// 登录成功进行页面的跳转
				getHApplication().loginSuccess(user);
				Log.e("Log", "intent");
				Intent intent = new Intent(getApplicationContext(),
						preClazz);
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
			// 当前错误是指，请求服务器成功，但是数据有误，如：用户名或者密码错误
			if (user == null && msg != null) {
				toastShow(msg, Toast.LENGTH_SHORT);
			}
			break;

		default:
			break;
		}
	}

}
