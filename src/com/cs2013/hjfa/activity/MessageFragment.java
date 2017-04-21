package com.cs2013.hjfa.activity;

import com.cs2013.hjfa.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MessageFragment extends BaseFragment {

	private ListView mLvMsg=null;
	
	
	
	@Override
	public void onResponse(String res, int code) {
		
	}

	@Override
	protected View initLayout(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.fragment_msg,container, false);
	}

	@Override
	protected void initEvents(View view) {
		
	}

	@Override
	protected void initViews(View view) {	
//		mLvMsg=(ListView) view.findViewById(R.id.lv_msg);
	}

	@Override
	public void successResult(int code) {

	}

	@Override
	public void errorResult(int code) {

	}

}
