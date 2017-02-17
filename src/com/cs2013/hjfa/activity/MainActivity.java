package com.cs2013.hjfa.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.cs2013.hjfa.R;
import com.cs2013.hjfa.views.CoordinatorMenu;

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
		mLvContent=(ListView) findViewById(R.id.lv_main_content);
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

}
