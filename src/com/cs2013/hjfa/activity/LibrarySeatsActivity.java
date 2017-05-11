package com.cs2013.hjfa.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs2013.hjfa.R;
import com.cs2013.hjfa.adapter.FloorSeatAdapter;
import com.cs2013.hjfa.pojo.Library;
import com.cs2013.hjfa.pojo.Order;
import com.cs2013.hjfa.pojo.Seat;
import com.cs2013.hjfa.pojo.json.JsonList;
import com.cs2013.hjfa.pojo.json.JsonObject;
import com.cs2013.hjfa.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LibrarySeatsActivity extends BaseActivity implements
		OnItemClickListener {

	private DrawerLayout mDlLibrary;

	private TextView mTvLibraryName; // ͼ�������
	private TextView mTvLibraryAddress; // ͼ��ݵ�ַ
	private ListView mLvLibraryFloor; // ͼ���¥��
	private Library libraryInfo;
	private ImageView mIvBack;
	private ImageView mIvMnue;

	private ArrayAdapter<String> mAdapterFloor;
	private List<String> mListFloor;

	
	private TextView mTvFloorTitle;// ͼ���¥�����
	private GridView mGvFloorSeats; // ¥����λ��Ϣ
	private FloorSeatAdapter mSeatAdapter; //
	private List<Seat> mListSeat;
	
	private int position;
	private Order order;

	@Override
	protected void initViews() {
		setContentView(R.layout.activity_library_seats);

		mDlLibrary = (DrawerLayout) findViewById(R.id.dl_library_floor_seat);

		mTvLibraryName = (TextView) findViewById(R.id.tv_library_name);
		mTvLibraryAddress = (TextView) findViewById(R.id.tv_library_address);
		mLvLibraryFloor = (ListView) findViewById(R.id.lv_libray_floor);

		mTvFloorTitle = (TextView) findViewById(R.id.tv_floor_title);
		mGvFloorSeats = (GridView) findViewById(R.id.gv_floor_seat);
		mIvBack =(ImageView) findViewById(R.id.iv_top_back2);
		mIvMnue =(ImageView) findViewById(R.id.iv_top_menu);
		
		Intent intent = getIntent();
		libraryInfo = new Library();
		libraryInfo.setId(intent.getStringExtra("library_id"));
		libraryInfo.setName(intent.getStringExtra("library_name"));
		libraryInfo.setAddress(intent.getStringExtra("library_address"));
		libraryInfo.setLevel(intent.getStringExtra("library_level"));

		mTvLibraryName.setText(libraryInfo.getName());

		mTvLibraryAddress.setText(libraryInfo.getAddress());

		Log.i("LibrarySeatsActivity", Integer.parseInt(libraryInfo.getLevel())
				+ "");

		mListFloor = new ArrayList<String>();
		for (int i = 1; i <= Integer.parseInt(libraryInfo.getLevel()); i++) {
			mListFloor.add(i + " ¥");
		}

		mAdapterFloor = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mListFloor);
		mLvLibraryFloor.setAdapter(mAdapterFloor);

		mListSeat = new ArrayList<Seat>();
		mSeatAdapter = new FloorSeatAdapter(this, mListSeat);
		mGvFloorSeats.setAdapter(mSeatAdapter);

		mTvFloorTitle.setText(libraryInfo.getName());
		
		// û��¥�� ��û��λ��
		if (mListFloor.size() == 0) {
			 return;
		}
		
		// ���ϻ�ȡ��Ϣ
		getFloorSeats(1);

	}

	@Override
	protected void initEvents() {
		mIvBack.setOnClickListener(this);
		mIvMnue.setOnClickListener(this);
		mLvLibraryFloor.setOnItemClickListener(this);
		mGvFloorSeats.setOnItemClickListener(this);
	}
	
	/**
	 * Ԥ����ʾ�Ի���
	 */
	private void orderDialog(final Seat s) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("��ʾ");
		builder.setMessage("ȷ��Ԥ����");
		builder.setPositiveButton("ȷ��", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				com.google.gson.JsonObject jsonObj=new com.google.gson.JsonObject();
				jsonObj.addProperty("uid", getHApplication().getUser().getId());
				jsonObj.addProperty("sid", s.getId());
//				Order o = new Order();
//				o.setSid(s.getId());
//				o.setUid(getHApplication().getUser().getStuId());
//				String json = new Gson().toJson(o);
				connServer(Constants.URL_LIBRARY_FLOOR_SEAT_ORDER, jsonObj.toString(),
						Constants.CODE_LIBRARY_FLOOR_SEAT_ORDER);
				dialog.dismiss();
			}
		});

		builder.setNegativeButton("ȡ��", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private void getFloorSeats(int localtion) {
		
		mTvFloorTitle.setText(libraryInfo.getName() + localtion + " ¥");
		Seat s = new Seat();
		s.setPid(libraryInfo.getId());
		s.setLevel("" + localtion);
		String json = new Gson().toJson(s);
		connServer(Constants.URL_LIBRARY_FLOOR_SEAT_INFORMATION, json,
				Constants.CODE_LIBRARY_FLOOR_SEAT_INFORMATION);
	}

	private void orderFloorSeat(int localtion) {
		if (!getHApplication().isUserLogin()) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		} else {
			Seat s = mListSeat.get(localtion);
			if (!s.getState().equals("0")) {
				toastShow("��λ���ѱ�Ԥ������ѡ��������λ��", Toast.LENGTH_SHORT);
			} else {
				this.position = localtion;
				orderDialog(s);
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long arg3) {
		Log.e("Log", "Item:"+view.getId());
		switch (parent.getId()) {
		case R.id.lv_libray_floor:
			getFloorSeats(position + 1);
			mDlLibrary.closeDrawer(GravityCompat.END);
			break;
		case R.id.gv_floor_seat:
			orderFloorSeat(position);
			break;
		default:
			break;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_top_back2:
			finish();
			break;
		case R.id.iv_top_menu:
			mDlLibrary.openDrawer(GravityCompat.END);
			break;
		default:
			break;
		}

	}

	@Override
	public void onResponse(String res, int code) {
		Log.e("Log", res);
		Gson gson = new Gson();// ����Gson�������ڽ������ص�json�ַ���
		Type objectType = null;
		switch (code) {
		case Constants.CODE_LIBRARY_FLOOR_SEAT_INFORMATION:// ����ͼ�����Ϣ���صĽ��
			// ��JSON����תΪ���󣬿���ʹ�÷���
			objectType = new TypeToken<JsonList<Seat>>() {
			}.getType();
			// ��ʼת��
			JsonList<Seat> object = gson.fromJson(res, objectType);
			Log.e("Log", object.getCode() + "code");
			Log.e("Log", object.getMsg() + "meg");

			if (object.getCode() == 200) {// JSON�����з��ص���������
				mListSeat.clear();
				mListSeat.addAll(object.getResult());// ��ȡ��������
				msg = null;
			} else {
				msg = object.getMsg();
			}
			break;
		case Constants.CODE_LIBRARY_FLOOR_SEAT_ORDER:
			// ��JSON����תΪ���󣬿���ʹ�÷���
			objectType = new TypeToken<JsonObject<Order>>() {
			}.getType();
			// ��ʼת��
			JsonObject<Order> objectOrder = gson.fromJson(res, objectType);
			Log.e("Log", objectOrder.getCode() + "");
			Log.e("Log", objectOrder.getMsg() + "");
			if (objectOrder.getCode() == Constants.JSON_OK) {// JSON�����з��ص���������
				order = objectOrder.getResult();// ��ȡ��������
				msg = null;
			} else {
				msg = objectOrder.getMsg();
				order = null;
			}
			break;
		default:
			break;
		}

	}

	@Override
	public void successResult(int code) {
		switch (code) {
		case Constants.CODE_LIBRARY_FLOOR_SEAT_INFORMATION:
			if (mListSeat != null && msg == null) {
				Log.e("leeh", "success");
				mSeatAdapter.notifyDataSetChanged();
			}
			toastShow(msg, Toast.LENGTH_SHORT);
			break;
		case Constants.CODE_LIBRARY_FLOOR_SEAT_ORDER:
			Log.e("leeh", "success");
			if (order != null && msg == null) {
				Log.e("leeh", "success");
				mListSeat.get(position).setState("1");
				mSeatAdapter.notifyDataSetChanged();
				Date d = new Date(order.getOrderTime() + 1000 * 10);
					
				toastShow("Ԥ���ɹ�!" + d.toString() + "֮ǰɨ��ȷ����" , Toast.LENGTH_SHORT);
			} else {
				toastShow(msg, Toast.LENGTH_SHORT);
			}
			break;
		}
	}

	@Override
	public void errorResult(int code) {
		switch (code) {
		case Constants.CODE_LIBRARY_FLOOR_SEAT_INFORMATION:
			if (mListSeat != null) {
				toastShow(msg, Toast.LENGTH_SHORT);
			}
			break;
		case Constants.CODE_LIBRARY_FLOOR_SEAT_ORDER:
			if (order != null) {
				toastShow(msg, Toast.LENGTH_SHORT);
			}
			break;
		}
	}

}
