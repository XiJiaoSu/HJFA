package com.cs2013.hjfa.activity;

import com.cs2013.hjfa.R;

import android.view.View;
import android.widget.ImageView;

public class SearchActivity extends BaseActivity {
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
		// TODO Auto-generated method stub

	}

	@Override
	protected void initEvents() {
		mIVBack.setOnClickListener(this);
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_search);
		mIVBack = (ImageView) findViewById(R.id.iv_top_back);
	}

	@Override
	public void successResult(int code) {
		// TODO Auto-generated method stub

	}

	@Override
	public void errorResult(int code) {
		// TODO Auto-generated method stub

	}

}
