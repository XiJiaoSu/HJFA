package com.cs2013.hjfa.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.cs2013.hjfa.R;
import com.cs2013.hjfa.adapter.MainAdapter;
import com.cs2013.hjfa.pojo.Library;

public class MainFragment extends BaseFragment implements OnItemClickListener, OnClickListener {

	private TextView mTVSearch = null;
	private ListView mLvContent = null;
	private MainAdapter mAdapter = null;
	

	@Override
	public void onResponse(String res, int code) {

	}

	@Override
	protected View initLayout(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.fragment_main,container,false);
	}

	@Override
	protected void initEvents(View view) {
		mLvContent.setOnItemClickListener(this);
		mTVSearch.setOnClickListener(this);
	}

	@Override
	protected void initViews(View view) {
		View vHeader = LayoutInflater.from(getActivity()).inflate(
				R.layout.item_search, null);
		mLvContent = (ListView) view.findViewById(R.id.lv_main_content);
		mLvContent.addHeaderView(vHeader);
		mTVSearch = (TextView) vHeader.findViewById(R.id.tv_search);

		// 初始化ListView相关对象
		List<Library> datas = new ArrayList<Library>();
		datas.add(new Library());
		datas.add(new Library());
		datas.add(new Library());
		datas.add(new Library());
		datas.add(new Library());
		datas.add(new Library());
		datas.add(new Library());
		mAdapter = new MainAdapter(getActivity(), datas);
		mLvContent.setAdapter(mAdapter);
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
		
	}

	@Override
	public void onClick(View v) {
		Intent intent=null;
		switch (v.getId()) {
		case R.id.tv_search:
			intent=new Intent(getActivity(), SearchActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

}
