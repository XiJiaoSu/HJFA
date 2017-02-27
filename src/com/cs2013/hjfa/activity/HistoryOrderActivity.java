package com.cs2013.hjfa.activity;

import com.cs2013.hjfa.R;

import android.view.View;
import android.widget.ImageView;

public class HistoryOrderActivity extends BaseActivity {
	private ImageView mIVBack = null;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_top_back:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void onResponse(String res, int code) {

	}

	@Override
	protected void initEvents() {
		mIVBack.setOnClickListener(this);
	}

	@Override
	protected void initViews() {
		setContentView(R.layout.activity_history);
		mIVBack = (ImageView) findViewById(R.id.iv_top_back);
	}

	@Override
	public void successResult(int code) {

	}

	@Override
	public void errorResult(int code) {

	}

}
