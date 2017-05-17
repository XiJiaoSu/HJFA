package com.cs2013.hjfa.activity;

import java.lang.reflect.Type;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs2013.hjfa.R;
import com.cs2013.hjfa.pojo.User;
import com.cs2013.hjfa.pojo.json.JsonObject;
import com.cs2013.hjfa.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class RegisterActivity extends BaseActivity{

	private ImageView mIvBack;
	private TextView mTvTitle;
	private EditText mEtName;
	private EditText mEtEmail;
	private EditText mEtPwd;
	private EditText mEtPwd2;
	private Button mBtRegister;
	private User user = null;
	
	@Override
	public void onClick(View v) {
		Intent intent=null;
		switch (v.getId()) {
		case R.id.iv_top_back:
			intent=new Intent(this,LoginActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.bt_register:
			String pwd=mEtPwd.getText().toString().trim();
			String pwd2=mEtPwd2.getText().toString().trim();
			if (!pwd.equals(pwd2)) {
				toastShow("两次密码不一致", Toast.LENGTH_SHORT);
				return;
			}
			User user=new User();
			user.setPassword(pwd);
			user.setUsername(mEtName.getText().toString().trim());
			user.setEmail(mEtEmail.getText().toString().trim());
			connServer(Constants.URL_USER_REGISTER, (new Gson()).toJson(user),Constants.CODE_USER_REGISTER);
			break;
		default:
			break;
		}
	}

	@Override
	public void onResponse(String res, int code) {
		Gson gson = new Gson();// 创建Gson对象，用于解析返回的json字符串
		Type objectType = null;
		switch (code) {
		case Constants.CODE_USER_REGISTER:
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
	protected void initEvents() {
		mIvBack.setOnClickListener(this);
		mBtRegister.setOnClickListener(this);
	}

	@Override
	protected void initViews() {
		setContentView(R.layout.activity_register);
		
		mIvBack=(ImageView) findViewById(R.id.iv_top_back);
		mTvTitle=(TextView) findViewById(R.id.tv_top_title);
		mTvTitle.setText("注册");
		mEtName=(EditText) findViewById(R.id.et_name);
		mEtEmail=(EditText) findViewById(R.id.et_email);
		mEtPwd=(EditText) findViewById(R.id.et_reg_pwd);
		mEtPwd2=(EditText) findViewById(R.id.et_sure_pwd);
		mBtRegister=(Button) findViewById(R.id.bt_register);
		
	}

	@Override
	public void successResult(int code) {
		switch (code) {
		case Constants.CODE_USER_REGISTER:
			if (user != null && msg == null) {// 登录成功进行页面的跳转
				getHApplication().loginSuccess(user);
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
		case Constants.CODE_USER_REGISTER:
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
