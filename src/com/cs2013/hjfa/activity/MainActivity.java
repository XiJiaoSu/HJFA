package com.cs2013.hjfa.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs2013.hjfa.R;
import com.cs2013.hjfa.adapter.MainAdapter;
import com.cs2013.hjfa.pojo.Library;
import com.cs2013.hjfa.utils.Constants;
import com.cs2013.hjfa.views.CoordinatorMenu;
import com.xjtu.activity.CaptureActivity;
import com.xjtu.activity.CodeUtils;

public class MainActivity extends BaseActivity implements OnItemClickListener {

	private CoordinatorMenu mCoordinatorMenu;
	// 菜单按钮
	private LinearLayout mLLOne = null;
	private LinearLayout mLLTwo = null;
	private LinearLayout mLLThree = null;
	private TextView mTVExit=null;
	private View view=null;
	// 主题页面按钮
	private TextView mTVSearch=null;
	private ImageView mHeadIv;
	private ImageView mIvScan = null;
	private ListView mLvContent = null;
	private MainAdapter mAdapter = null;
	
	protected void initViews() {
		setContentView(R.layout.activity_main);
		mHeadIv = (ImageView) findViewById(R.id.iv_head);
		mCoordinatorMenu = (CoordinatorMenu) findViewById(R.id.menu);
		mLLOne = (LinearLayout) findViewById(R.id.ll_history);
		mLLTwo = (LinearLayout) findViewById(R.id.ll_collection);
		mLLThree = (LinearLayout) findViewById(R.id.ll_exit);
		mIvScan = (ImageView) findViewById(R.id.iv_scan);
		view=LayoutInflater.from(this).inflate(R.layout.item_search, null);
		mLvContent = (ListView) findViewById(R.id.lv_main_content);
		mLvContent.addHeaderView(view);
		mTVSearch=(TextView) view.findViewById(R.id.tv_search);
		mTVExit=(TextView) findViewById(R.id.tv_exit_menu);
		// 初始化ListView相关对象
		List<Library> datas = new ArrayList<Library>();
		datas.add(new Library());
		datas.add(new Library());
		datas.add(new Library());
		datas.add(new Library());
		datas.add(new Library());
		mAdapter = new MainAdapter(this, datas);
		mLvContent.setAdapter(mAdapter);

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
		mLLTwo.setOnClickListener(this);
		mLLThree.setOnClickListener(this);
		mTVSearch.setOnClickListener(this);
		mLvContent.setOnItemClickListener(this);
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
		case R.id.ll_collection:
			goActivity(CollectionActivity.class, null);
			break;
		case R.id.ll_exit:
			this.mCoordinatorMenu.closeMenu();
			if (getHApplication().isUserLogin()) {
				getHApplication().logout();
			}else{
				Intent intent=new Intent(this, LoginActivity.class);
				startActivity(intent);
			}
			updateMenu();
			break;
		case R.id.tv_search:
			goActivity(SearchActivity.class, null);
			break;
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
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		toastShow(position + "", Toast.LENGTH_SHORT);
	}

	
	@Override
	protected void onPause() {
		super.onPause();
		if (this.mCoordinatorMenu.isOpened()) {
			this.mCoordinatorMenu.closeMenu();
		}
	}
	
}
