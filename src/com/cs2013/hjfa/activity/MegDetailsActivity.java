package com.cs2013.hjfa.activity;

import com.cs2013.hjfa.R;
import com.cs2013.hjfa.utils.DateUtil;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MegDetailsActivity extends BaseActivity{
	
	private TextView mTvTitle,mTvContent,mTvDate;
	private String messageTitle,messageContent,messageDate;
	private ImageView mIvClose;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void onResponse(String res, int code) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		mTvTitle.setText(messageTitle);
		mTvContent.setText("\t\t"+messageContent);
		mTvDate.setText(DateUtil.getTime(messageDate));
		mIvClose.setOnClickListener(this);
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_msgdetails);
		mTvTitle = (TextView) findViewById(R.id.tv_messaged_title);
		mTvContent = (TextView) findViewById(R.id.tv_messaged_content);
		mTvDate = (TextView) findViewById(R.id.tv_messaged_date);
		mIvClose = (ImageView) findViewById(R.id.iv_messaged_close);
		messageTitle = getIntent().getStringExtra("title");
		messageContent = getIntent().getStringExtra("content");
		messageDate = getIntent().getStringExtra("date");
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
