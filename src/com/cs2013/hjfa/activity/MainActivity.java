package com.cs2013.hjfa.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.cs2013.hjfa.R;
import com.cs2013.hjfa.adapter.FragmentAdapter;
import com.cs2013.hjfa.pojo.Order;
import com.cs2013.hjfa.pojo.json.JsonObject;
import com.cs2013.hjfa.utils.Constants;
import com.cs2013.hjfa.views.CoordinatorMenu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xjtu.activity.CaptureActivity;
import com.xjtu.activity.CodeUtils;

public class MainActivity extends BaseActivity implements
		OnCheckedChangeListener {

	private CoordinatorMenu mCoordinatorMenu;
	// 菜单按钮
	private LinearLayout mLLOne = null;
	// private LinearLayout mLLTwo = null;
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
	private String oid = null;
	private String sid=null;
	private String msg = null;
	private Order order = null;
	private boolean flag = false;

	protected void initViews() {
		setContentView(R.layout.activity_main);

		mVPMain = (ViewPager) findViewById(R.id.vp_main);

		mRGMain = (RadioGroup) findViewById(R.id.rg_main);
		mRBLibrary = (RadioButton) findViewById(R.id.rb_library);
		mRBMsg = (RadioButton) findViewById(R.id.rb_msg);
		mHeadIv = (ImageView) findViewById(R.id.iv_head);
		mCoordinatorMenu = (CoordinatorMenu) findViewById(R.id.menu);
		mLLOne = (LinearLayout) findViewById(R.id.ll_history);
		// mLLTwo = (LinearLayout) findViewById(R.id.ll_collection);
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
		// mLLTwo.setOnClickListener(this);
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
		case R.id.iv_scan:
			this.goActivityForResult(CaptureActivity.class, null,
					Constants.RESULT_OK);
			break;
		default:
			break;
		}
	}

	@Override
	public void onResponse(String res, int code) {
		Gson gson = new Gson();// 创建Gson对象，用于解析返回的json字符串
		Type objectType = null;
		switch (code) {
		case Constants.CODE_ORDER_CONFIRM:
			msg = res;
			// 将JSON数据转为对象，可以使用泛型
			objectType = new TypeToken<JsonObject<Order>>() {
			}.getType();
			// 开始转型
			JsonObject<Order> object = gson.fromJson(res, objectType);
			Log.e("Log_confirm", object.getCode() + "");
			Log.e("Log_confirm", object.getMsg() + "");
			if (object.getCode() == Constants.JSON_OK) {// JSON数据中返回的数据正常
				order = object.getResult();// 获取对象数据
				oid = null;
				msg = null;
				sid=null;
				Log.e("Log_confirm", 200+"");
			} else if (object.getCode() == 100) {
				Log.e("Log_cancle111", 100+":"+object.getMsg());
				msg = object.getMsg();
				order = object.getResult();
				oid = order.getId();
				sid=order.getSid();
			} else {
				msg = null;
			}
			break;
		case Constants.CODE_ORDER_CANCLE:
			Log.e("Log_Cancle", res);
			// 将JSON数据转为对象，可以使用泛型
			objectType = new TypeToken<JsonObject<String>>() {
			}.getType();
			// 开始转型
			JsonObject<String> obj = gson.fromJson(res, objectType);
			if (obj.getCode() == Constants.JSON_OK) {
				flag = true;
				msg = null;
			} else {
				flag = false;
				msg = obj.getMsg();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void successResult(int code) {
		switch (code) {
		case Constants.CODE_ORDER_CONFIRM:
//			toastShow(msg, Toast.LENGTH_SHORT);
			if (oid != null) {
//				toastShow("oid"+oid,Toast.LENGTH_LONG);
				orderDialog(oid,sid);
			} else {
				toastShow("预定成功", Toast.LENGTH_SHORT);
			}
			break;
		case Constants.CODE_ORDER_CANCLE:
			if (flag) {
				toastShow("您已经取消当前座位", Toast.LENGTH_SHORT);
				flag=false;
				oid=null;
				sid=null;
			}
		}
	}

	@Override
	public void errorResult(int code) {
		// switch (code) {
		// case Constants.CODE_ORDER_CONFIRM:
		toastShow(msg, Toast.LENGTH_SHORT);
		// break;
		// }
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
				// Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG)
				// .show();
				com.google.gson.JsonObject jsonObj = new com.google.gson.JsonObject();
				jsonObj.addProperty("sid", result);
				jsonObj.addProperty("uid", getHApplication().getUser().getId());
				connServer(Constants.URL_ORDER_CONFIRM, jsonObj.toString(),
						Constants.CODE_ORDER_CONFIRM);
			} else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
				Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG)
						.show();
			}
		}
	}

	/**
	 * 预定提示对话框
	 */
	private void orderDialog(final String oid,final String sid) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("座位取消");
		builder.setMessage("确定取消吗？");
		builder.setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				com.google.gson.JsonObject jsonObj = new com.google.gson.JsonObject();
				jsonObj.addProperty("sid",sid );
				jsonObj.addProperty("oid", oid);
				connServer(Constants.URL_ORDER_CANCLE, jsonObj.toString(),
						Constants.CODE_ORDER_CANCLE);
				dialog.dismiss();
			}
		});

		builder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				dialog.dismiss();
			}
		});
		builder.create().show();
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
			if (mVPMain.getCurrentItem() != TAB_LIBRARY) {
				mVPMain.setCurrentItem(TAB_LIBRARY);
			}
			// }
			break;
		case R.id.rb_msg:
			if (mVPMain.getCurrentItem() != TAB_MSG) {
				mVPMain.setCurrentItem(TAB_MSG);
			}
			break;
		}
	}

}
