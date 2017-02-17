package com.cs2013.hjfa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.cs2013.hjfa.R;
import com.cs2013.hjfa.utils.Constants;
import com.cs2013.hjfa.views.CoordinatorMenu;
import com.xjtu.activity.CaptureActivity;
import com.xjtu.activity.CodeUtils;

public class MainActivity extends BaseActivity {

	private CoordinatorMenu mCoordinatorMenu;
	// 菜单按钮
	private LinearLayout mLLOne = null;
	// 主题页面按钮
	private ImageView mHeadIv;
	private ImageView mIvScan = null;
	private ListView mLvContent = null;

	protected void initViews() {
		setContentView(R.layout.activity_main);
		mHeadIv = (ImageView) findViewById(R.id.iv_head);
		mCoordinatorMenu = (CoordinatorMenu) findViewById(R.id.menu);
		mLLOne = (LinearLayout) findViewById(R.id.ll_one);
		mIvScan = (ImageView) findViewById(R.id.iv_scan);
		mLvContent = (ListView) findViewById(R.id.lv_main_content);
	}

	@Override
	protected void initEvents() {
		mIvScan.setOnClickListener(this);
		mHeadIv.setOnClickListener(this);
		mLLOne.setOnClickListener(this);
	}

	@Override
	public void onBackPressed() {
		if (mCoordinatorMenu.isOpened()) {
			mCoordinatorMenu.closeMenu();
			return;
		}
		super.onBackPressed();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_head:// 头像点击
			if (mCoordinatorMenu.isOpened()) {
				mCoordinatorMenu.closeMenu();
			} else {
				mCoordinatorMenu.openMenu();
			}
			break;
		case R.id.ll_one:
			Toast.makeText(MainActivity.this, "点击事件", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.iv_scan:
			Intent intent = new Intent(this, CaptureActivity.class);
			startActivityForResult(intent, Constants.RESULT_OK);
			break;
		default:
			break;
		}
	}

	@Override
	public void onResponse(String res, int code) {

	}

	@Override
	public void successResult(int code) {

	}

	@Override
	public void errorResult(int code) {

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == Constants.RESULT_OK && data != null) {
			Bundle bundle = data.getExtras();
			if (bundle == null) {
				return;
			}
			if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
				String result = bundle.getString(CodeUtils.RESULT_STRING);
				Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG)
						.show();
			} else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
				Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG)
						.show();
			}
		}
	}

}
