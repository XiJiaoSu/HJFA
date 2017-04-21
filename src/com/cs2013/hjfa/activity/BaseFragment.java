package com.cs2013.hjfa.activity;

import com.cs2013.hjfa.utils.Constants;
import com.cs2013.hjfa.utils.MResponseHandler;
import com.cs2013.hjfa.utils.NetWorkResponse;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment implements MResponseHandler {

	
	private NetWorkResponse netWorkResponse;
	protected Dialog dialog;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = initLayout(inflater, container);
		if (view == null) {
			view = super.onCreateView(inflater, container, savedInstanceState);
		}
		return view;
	}

	
	protected abstract View initLayout(LayoutInflater inflater,
			ViewGroup container);
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initViews(view);
		initEvents(view);
	}

	/**
	 * 相关点击事件
	 */
	protected abstract void initEvents(View view);


	/**
	 * 初始化相关组件与对象
	 */
	protected abstract void initViews(View view);
	
	
	
	@Override
	public void preResponse() {
		// TODO Auto-generated method stub
		
	}
	protected void connServer(String url,String entity,int code) {
		netWorkResponse=new NetWorkResponse(url, entity, code,this);
		netWorkResponse.postResponse();
	}
	@Override
	public void onError(String msg, int code, int state) {
		toastShow(msg, Toast.LENGTH_SHORT);
		afterResponse(code, state);
	}
	protected void toastShow(String msg,int time){
	
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
	public abstract void successResult(int code);

	public abstract void errorResult(int code);

	protected void dialogClose(){
		if (dialog!=null) {
			dialog.dismiss();
		}
	}

	
}
