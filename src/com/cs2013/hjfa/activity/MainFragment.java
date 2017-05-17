package com.cs2013.hjfa.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs2013.hjfa.R;
import com.cs2013.hjfa.adapter.MainAdapter;
import com.cs2013.hjfa.pojo.Library;
import com.cs2013.hjfa.pojo.json.JsonList;
import com.cs2013.hjfa.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainFragment extends BaseFragment implements OnItemClickListener,
		OnClickListener {

	private TextView mTVSearch = null;
	private ListView mLvContent = null;
	private MainAdapter mAdapter = null;
	private List<Library> libraryList = null;

	private String msg = null;// 网络获取数据后的信息提示

	@Override
	protected View initLayout(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.fragment_main, container, false);
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

		connServer(Constants.URL_LIBRARY_INFORMATION, "",
				Constants.CODE_LIBRARY_INFORMATION);

		// 初始化ListView相关对象
		libraryList = new ArrayList<Library>();
		mAdapter = new MainAdapter(getActivity(), libraryList);
		mLvContent.setAdapter(mAdapter);
	}

	@Override
	public void onResponse(String res, int code) {
//		Log.e("Log", res);
		Gson gson = new Gson();// 创建Gson对象，用于解析返回的json字符串
		Type objectType = null;
		switch (code) {
		case Constants.CODE_LIBRARY_INFORMATION:// 请求图书馆信息返回的结果
			// 将JSON数据转为对象，可以使用泛型
			objectType = new TypeToken<JsonList<Library>>() {
			}.getType();
			// 开始转型
			JsonList<Library> object = gson.fromJson(res, objectType);
			Log.e("Log", object.getCode() + "code");
			Log.e("Log", object.getMsg() + "meg");

			if (object.getCode() == 200) {// JSON数据中返回的数据正常
				libraryList.clear();
				libraryList.addAll(object.getResult());// 获取对象数据
				msg = null;
			} else {
				msg = object.getMsg();
			}
			break;

		default:
			break;
		}

	}

	@Override
	public void successResult(int code) {
		switch (code) {
		case Constants.CODE_LIBRARY_INFORMATION:
//			Log.e("leeh", "success");
			if (libraryList != null && libraryList.size() != 0 && msg == null) {
				Log.e("leeh", "success");
				mAdapter.notifyDataSetChanged();
			}
			toastShow(msg, Toast.LENGTH_SHORT);
			break;
		}
	}

	@Override
	public void errorResult(int code) {
		switch (code) {
		case Constants.CODE_LIBRARY_INFORMATION:
			if (libraryList != null && libraryList.size() != 0 && msg == null) {
				toastShow(msg, Toast.LENGTH_SHORT);
			}
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(getActivity(), LibrarySeatsActivity.class);
		intent.putExtra("library_id", libraryList.get(position-1).getId());
		intent.putExtra("library_name", libraryList.get(position-1).getName());
		intent.putExtra("library_address", libraryList.get(position-1).getAddress());
		intent.putExtra("library_level", libraryList.get(position-1).getLevel());
		startActivity(intent);
		
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.tv_search:
//			intent = new Intent(getActivity(), SearchActivity.class);
//			startActivity(intent);
			break;

		default:
			break;
		}
	}

}
