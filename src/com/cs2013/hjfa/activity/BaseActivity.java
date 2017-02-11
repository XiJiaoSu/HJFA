package com.cs2013.hjfa.activity;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;

import com.cs2013.hjfa.utils.Constants;
import com.cs2013.hjfa.utils.MResponseHandler;
import com.cs2013.hjfa.utils.NetWorkResponse;

/**
 * �Զ����BaseActivity�����е�activity���̳�����
 * 
 */
public abstract class BaseActivity extends Activity implements OnClickListener , MResponseHandler{
	//���ڷ�����������ʱʹ��
	private NetWorkResponse netWorkResponse;
	//��������ʱʹ�õļ��ؽ��ȵ��Ѻ���ʾ
	protected Dialog dialog;
	protected String msg=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		initViews();
		initEvents();
	}

	/**
	 * ��ʼ����������findViewById
	 */
	protected abstract void initEvents();
	
	/**
	 * ע�����¼� *.setOnclicker**
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

	protected void toastShow(String msg,int time){
		if (msg==null||msg.length()<1) {
			return;
		}
		Toast.makeText(this, msg, time).show();
	}
	
	@Override
	public void afterResponse(int code, int state) {
		dialogClose();
		if (state==Constants.STATE_ERROR) {
			errorResult(code);
			return;
		}
		 successResult(code);
	}
	
	/**
	 * ������������ȷ������
	 * @param code
	 */
	public abstract void successResult(int code);
	
	/**
	 * ��������ʧ��
	 * @param code
	 */
	public abstract void errorResult(int code);
	
	protected void connServer(String url,String entity,int code) {
		netWorkResponse=new NetWorkResponse(url, entity, code,this);
		netWorkResponse.postResponse();
	}
	
	
	@Override
	protected void onDestroy() {
		if (netWorkResponse!=null&&netWorkResponse.getHandler()!=null) {
			netWorkResponse.getHandler().removeCallbacksAndMessages(null);
		}
		super.onDestroy();
	}
	
	protected void dialogClose(){
		if (dialog!=null) {
			dialog.dismiss();
		}
	}
}
