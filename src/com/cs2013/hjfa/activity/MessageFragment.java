package com.cs2013.hjfa.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.cs2013.hjfa.R;
import com.cs2013.hjfa.adapter.MessgeAdapter;
import com.cs2013.hjfa.pojo.Message;
import com.cs2013.hjfa.pojo.User;
import com.cs2013.hjfa.pojo.json.JsonList;
import com.cs2013.hjfa.pojo.json.JsonObject;
import com.cs2013.hjfa.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MessageFragment extends BaseFragment implements OnItemClickListener{

	private ListView mLvMsg=null;
	private List<Message> messages,netMessage;
	private MessgeAdapter messgeadapter;
	
	private Message msgs = null;
	private String msg = null;
	
	@Override
	public void onResponse(String res, int code) {
//		Log.e("Log", res);
		Gson gson = new Gson();// 创建Gson对象，用于解析返回的json字符串
		Type objectType = null;
		switch (code) {
		case Constants.CODE_MESSAGE:// 登陆请求返回的数据
			// 将JSON数据转为对象，可以使用泛型
			objectType = new TypeToken<JsonList<Message>>() {
			}.getType();
			// 开始转型
			JsonList<Message> object = gson.fromJson(res, objectType);
			Log.e("Log", object.getCode() + "code");
			Log.e("Log", object.getMsg() + "meg");
			
			if (object.getCode() == 200) {// JSON数据中返回的数据正常
				netMessage =(ArrayList<Message>) object.getResult();// 获取对象数据
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
	protected View initLayout(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.fragment_msg,container, false);
	}

	@Override
	protected void initEvents(View view) {
		mLvMsg.setAdapter(messgeadapter);
		mLvMsg.setOnItemClickListener(this);
	}

	@Override
	protected void initViews(View view) {
		mLvMsg=(ListView) view.findViewById(R.id.lv_msg);
		messages = new ArrayList<Message>();
		messgeadapter = new MessgeAdapter(messages, getActivity());
		
	}
	@Override
	public void onResume() {
		super.onResume();
		connServer(Constants.URL_MESSAGE, null, Constants.CODE_MESSAGE);
		//connServer(Constants.URL_MESSAGE, null, Constants.CODE_MESSAGE);
	}

	@Override
	public void successResult(int code) {
		switch (code) {
		case Constants.CODE_MESSAGE:
			Log.e("Log", "success");
			if (netMessage.size() != 0 && msg == null) {
				messages.clear();
				messages.addAll(netMessage);
				messgeadapter.notifyDataSetChanged();
			}
			toastShow(msg, Toast.LENGTH_SHORT);
			break;
		}

	}

	@Override
	public void errorResult(int code) {
		switch (code) {
		case Constants.CODE_MESSAGE:
			
			if (messages.size() == 0 && msg != null) {
				toastShow(msg, Toast.LENGTH_SHORT);
			}
			break;
		}

	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getActivity(),MegDetailsActivity.class);
		Message message = (Message)messgeadapter.getItem(position);
		String title = message.getTitle();
		String content = message.getMessage();
		String date = message.getTime();
		intent.putExtra("title",title);
		intent.putExtra("content", content);
		intent.putExtra("date", date);
		startActivity(intent);
	}

}
