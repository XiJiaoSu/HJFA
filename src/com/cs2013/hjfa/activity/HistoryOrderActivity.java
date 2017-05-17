package com.cs2013.hjfa.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.cs2013.hjfa.R;
import com.cs2013.hjfa.adapter.HistoryAdapter;
import com.cs2013.hjfa.pojo.Order;
import com.cs2013.hjfa.pojo.User;
import com.cs2013.hjfa.pojo.json.JsonList;
import com.cs2013.hjfa.pojo.json.JsonObject;
import com.cs2013.hjfa.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HistoryOrderActivity extends BaseActivity implements OnItemClickListener {
	private ImageView mIVBack = null;
	private TextView mTvTitle=null;
	private ListView mLvHistory;
	private HistoryAdapter adapter=null;

	private List<Order> datas;
	private List<Order> orders;
	protected String msg = null;
	
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
	protected void onResume() {
		super.onResume();
		connServer(Constants.URL_ORDER_HISTORY, getHApplication().getUser().getId(), Constants.CODE_ORDER_HISTORY);
	}
	
	@Override
	public void onResponse(String res, int code) {
		Gson gson = new Gson();// 创建Gson对象，用于解析返回的json字符串
		Type objectType = null;
		switch (code) {
		case Constants.CODE_ORDER_HISTORY:
			Log.e("Log", res);
			objectType = new TypeToken<JsonList<Order>>() {
			}.getType();
			// 开始转型
			JsonList<Order> object = gson.fromJson(res, objectType);
			if(object.getCode()==Constants.JSON_OK){
				orders=object.getResult();
				 msg=null;
			}else{
				orders=null;
				msg=object.getMsg();
			}
			break;
		}
	}

	@Override
	protected void initEvents() {
		mIVBack.setOnClickListener(this);
		mLvHistory.setOnItemClickListener(this);
	}

	@Override
	protected void initViews() {
		setContentView(R.layout.activity_history);
		mIVBack = (ImageView) findViewById(R.id.iv_top_back);
		mTvTitle=(TextView) findViewById(R.id.tv_top_title);
		mLvHistory=(ListView) findViewById(R.id.lv_history);
		
		datas=new ArrayList<Order>();
		adapter=new HistoryAdapter(HistoryOrderActivity.this, datas);
		mLvHistory.setAdapter(adapter);
		mTvTitle.setText("预约历史");
	}

	@Override
	public void successResult(int code) {
		switch (code) {
		case Constants.CODE_ORDER_HISTORY:
			if (orders!=null&&msg==null) {
				datas.clear();
				Log.e("LOg",orders.get(0).toString());
				datas.addAll(orders);
				adapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void errorResult(int code) {
		switch (code) {
		case Constants.CODE_ORDER_HISTORY:
			if (orders==null&&msg!=null) {
				toastShow(msg, Toast.LENGTH_SHORT);
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

}
