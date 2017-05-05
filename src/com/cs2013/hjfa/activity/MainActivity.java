package com.cs2013.hjfa.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.cs2013.hjfa.R;
import com.cs2013.hjfa.adapter.FragmentAdapter;
import com.cs2013.hjfa.utils.Constants;
import com.cs2013.hjfa.views.CoordinatorMenu;
import com.xjtu.activity.CaptureActivity;
import com.xjtu.activity.CodeUtils;

public class MainActivity extends BaseActivity implements 
		OnCheckedChangeListener {

	private CoordinatorMenu mCoordinatorMenu;
	// 菜单按钮
	private LinearLayout mLLOne = null;
//	private LinearLayout mLLTwo = null;
	private LinearLayout mLLThree = null;
	private TextView mTVExit = null;
	// 主题页面按钮
	// private TextView mTVSearch=null;
	private ImageView mHeadIv;
	private ImageView mIvScan = null;
	// private ListView mLvContent = null;
	// private MainAdapter mAdapter = null;
	private static final int TAB_LIBRARY = 0;
	private static final int TAB_MSG = 1;
	private ViewPager mVPMain = null;
	private RadioButton mRBLibrary, mRBMsg;
	private RadioGroup mRGMain;
	private FragmentAdapter<BaseFragment> mViewPagerAdapter;
	private List<BaseFragment> mFragments = null;

	protected void initViews() {
		setContentView(R.layout.activity_main);

		mVPMain = (ViewPager) findViewById(R.id.vp_main);

		mRGMain = (RadioGroup) findViewById(R.id.rg_main);
		mRBLibrary=(RadioButton) findViewById(R.id.rb_library);
		mRBMsg=(RadioButton) findViewById(R.id.rb_msg);
		mHeadIv = (ImageView) findViewById(R.id.iv_head);
		mCoordinatorMenu = (CoordinatorMenu) findViewById(R.id.menu);
		mLLOne = (LinearLayout) findViewById(R.id.ll_history);
//		mLLTwo = (LinearLayout) findViewById(R.id.ll_collection);
		mLLThree = (LinearLayout) findViewById(R.id.ll_exit);
		mIvScan = (ImageView) findViewById(R.id.iv_scan);
		mTVExit = (TextView) findViewById(R.id.tv_exit_menu);

		mFragments = new ArrayList<BaseFragment>();
		mFragments.add(new MainFragment());
		mFragments.add(new MessageFragment());
		mViewPagerAdapter = new FragmentAdapter<BaseFragment>(
				getSupportFragmentManager(), mFragments);
		mVPMain.setAdapter(mViewPagerAdapter);
		mVPMain.setOffscreenPageLimit(2);
		mVPMain.setCurrentItem(0);
		// mVPMain.setOnPageChangeListener(this);

	}

	@Override
	protected void onStart() {
		super.onStart();
		updateMenu();
	}

	private void updateMenu() {
		mTVExit.setText(getResources().getString(R.string.menu_login));
		if (getHApplication().isUserLogin()) {
			mTVExit.setText(getResources().getString(R.string.menu_exit));
		}
	}

	@Override
	protected void initEvents() {
		mIvScan.setOnClickListener(this);
		mHeadIv.setOnClickListener(this);
		mLLOne.setOnClickListener(this);
//		mLLTwo.setOnClickListener(this);
		mLLThree.setOnClickListener(this);
		mRGMain.setOnCheckedChangeListener(this);
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
		case R.id.ll_history:
			goActivity(HistoryOrderActivity.class, null);
			break;
		case R.id.ll_exit:
			this.mCoordinatorMenu.closeMenu();
			if (getHApplication().isUserLogin()) {
				getHApplication().logout();
			} else {
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
			}
			updateMenu();
			break;
		// case R.id.tv_search:
		// goActivity(SearchActivity.class, null);
		// break;
		case R.id.iv_scan:
			this.goActivityForResult(CaptureActivity.class, null,
					Constants.RESULT_OK);
			// Intent intent = new Intent(this, CaptureActivity.class);
			// startActivityForResult(intent, Constants.RESULT_OK);
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


	@Override
	protected void onPause() {
		super.onPause();
		if (this.mCoordinatorMenu.isOpened()) {
			this.mCoordinatorMenu.closeMenu();
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rb_library:
			if (mVPMain.getCurrentItem()!=TAB_LIBRARY) {
				mVPMain.setCurrentItem(TAB_LIBRARY);
			}
//			}
			break;
		case R.id.rb_msg:
			if (mVPMain.getCurrentItem()!=TAB_MSG) {
				mVPMain.setCurrentItem(TAB_MSG);
			}
			break;
		}
	}

}
