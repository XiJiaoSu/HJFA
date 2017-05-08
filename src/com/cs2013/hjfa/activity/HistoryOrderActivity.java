package com.cs2013.hjfa.activity;

import java.util.ArrayList;
import java.util.List;

import com.cs2013.hjfa.R;
import com.cs2013.hjfa.adapter.HistoryAdapter;
import com.cs2013.hjfa.pojo.Order;
import com.cs2013.hjfa.utils.Constants;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class HistoryOrderActivity extends BaseActivity implements OnItemClickListener {
	private ImageView mIVBack = null;
	
	private ListView mLvHistory;
	private HistoryAdapter adapter=null;

	private List<Order> datas;
	
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
		switch (code) {
		case Constants.CODE_ORDER_HISTORY:
			Log.e("Log", res);
			break;

		default:
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
		mLvHistory=(ListView) findViewById(R.id.lv_history);
		
		datas=new ArrayList<Order>();
		adapter=new HistoryAdapter(HistoryOrderActivity.this, datas);
		mLvHistory.setAdapter(adapter);
		
	}

	@Override
	public void successResult(int code) {

	}

	@Override
	public void errorResult(int code) {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

}
