package com.cs2013.hjfa.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;

import com.cs2013.hjfa.utils.Constants;
import com.cs2013.hjfa.utils.HApplication;
import com.cs2013.hjfa.utils.MResponseHandler;
import com.cs2013.hjfa.utils.NetWorkResponse;

/**
 * 自定义的BaseActivity，所有的activity都继承自它
 * 
 */
public abstract class BaseActivity extends FragmentActivity implements OnClickListener,
		MResponseHandler {
	// 用于访问请求网络时使用
	private NetWorkResponse netWorkResponse;
	// 请求网络时使用的加载进度的友好提示
	protected Dialog dialog;
	protected String msg = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		initViews();
		initEvents();
	}

	protected void goActivity(Class clazz, Bundle bundle) {
		Intent intent = null;
		if (getHApplication().isUserLogin()) {
			intent = new Intent(this, clazz);
			if (bundle != null) {
				intent.putExtras(bundle);
			}
		} else {
			intent = new Intent(this, LoginActivity.class);
			bundle=new Bundle();
			bundle.putSerializable(LoginActivity.PRE_CLASS, clazz);
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

	protected void goActivityForResult(Class clazz, Bundle bundle, int code) {
		Intent intent = null;

		if (getHApplication().isUserLogin()) {
			intent = new Intent(this, clazz);
			if (bundle != null) {
				intent.putExtras(bundle);
			}
		} else {
			intent = new Intent(this, LoginActivity.class);
		}
		startActivityForResult(intent, code);
	}

	/**
	 * 初始化相关组件，findViewById
	 */
	protected abstract void initEvents();

	/**
	 * 注册点击事件 *.setOnclicker**
	 */
	protected abstract void initViews();

	@Override
	public void preResponse() {
	}

	@Override
	public void onError(String msg, int code, int state) {
		toastShow(msg, Toast.LENGTH_SHORT);
		afterResponse(code, state);
	}

	protected void toastShow(String msg, int time) {
		if (msg == null || msg.length() < 1) {
			return;
		}
		Toast.makeText(this, msg, time).show();
	}

	@Override
	public void afterResponse(int code, int state) {
		dialogClose();
		if (state == Constants.STATE_ERROR) {
			errorResult(code);
			return;
		}
		successResult(code);
	}

	/**
	 * 请求网络获得正确的数据
	 * 
	 * @param code
	 */
	public abstract void successResult(int code);

	/**
	 * 请求网络失败
	 * 
	 * @param code
	 */
	public abstract void errorResult(int code);

	protected void connServer(String url, String entity, int code) {
		netWorkResponse = new NetWorkResponse(url, entity, code, this);
		netWorkResponse.postResponse();
	}

	@Override
	protected void onDestroy() {
		if (netWorkResponse != null && netWorkResponse.getHandler() != null) {
			netWorkResponse.getHandler().removeCallbacksAndMessages(null);
		}
		super.onDestroy();
	}

	protected void dialogClose() {
		if (dialog != null) {
			dialog.dismiss();
		}
	}

	protected HApplication getHApplication() {
		return (HApplication) getApplication();
	}
}
