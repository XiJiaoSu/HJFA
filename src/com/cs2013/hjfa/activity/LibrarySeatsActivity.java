package com.cs2013.hjfa.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs2013.hjfa.R;
import com.cs2013.hjfa.adapter.FloorSeatAdapter;
import com.cs2013.hjfa.pojo.Library;
import com.cs2013.hjfa.pojo.Seat;
import com.cs2013.hjfa.pojo.json.JsonList;
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

	private ArrayAdapter<String> mAdapterFloor;
	private List<String> mListFloor;

	private TextView mTvFloorTitle;// ͼ���¥�����
	private GridView mGvFloorSeats; // ¥����λ��Ϣ
	private FloorSeatAdapter mSeatAdapter; //
	private List<Seat> mListSeat;

	@Override
	protected void initViews() {
		setContentView(R.layout.activity_library_seats);

		mDlLibrary = (DrawerLayout) findViewById(R.id.dl_library_floor_seat);

		mTvLibraryName = (TextView) findViewById(R.id.tv_library_name);
		mTvLibraryAddress = (TextView) findViewById(R.id.tv_library_address);
		mLvLibraryFloor = (ListView) findViewById(R.id.lv_libray_floor);

		mTvFloorTitle = (TextView) findViewById(R.id.tv_floor_title);
		mGvFloorSeats = (GridView) findViewById(R.id.gv_floor_seat);

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

		// û��¥�� ��û��λ��
		if (mListFloor.size() == 0) {
			// return;
		}
		// ���ϻ�ȡ��Ϣ
		mTvFloorTitle.setText(libraryInfo.getName() + "1 ¥");
		Seat seat = new Seat();
		seat.setPid(libraryInfo.getId());
		seat.setLevel("" + 1);
		String json = new Gson().toJson(seat);
		connServer(Constants.URL_LIBRARY_FLOOR_SEAT_INFORMATION, json,
				Constants.CODE_LIBRARY_FLOOR_SEAT_INFORMATION);

	}

	@Override
	protected void initEvents() {
		mLvLibraryFloor.setOnItemClickListener(this);
		mGvFloorSeats.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long arg3) {
		Seat seat = null;
		switch (view.getId()) {
		case R.id.lv_libray_floor:
			mTvFloorTitle
					.setText(libraryInfo.getName() + (position + 1) + " ¥");
			seat = new Seat();
			seat.setPid(libraryInfo.getId());
			seat.setLevel("" + (position + 1));
			String json = new Gson().toJson(seat);
			connServer(Constants.URL_LIBRARY_FLOOR_SEAT_INFORMATION, json,
					Constants.CODE_LIBRARY_FLOOR_SEAT_INFORMATION);
			mDlLibrary.closeDrawer(GravityCompat.END);
			break;
		case R.id.gv_floor_seat:
			seat = mListSeat.get(position);
			if (!seat.getState().equals("0")) {
				Toast.makeText(this, "��λ���ѱ�Ԥ������ѡ��������λ", Toast.LENGTH_LONG).show() ;
			}else{
				Dialog d = new Dialog(this);
				//������λԤ������
			}
			break;
		default:
			break;
		}

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

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

		default:
			break;
		}

	}

	@Override
	public void successResult(int code) {
		switch (code) {
		case Constants.CODE_LIBRARY_FLOOR_SEAT_INFORMATION:
			Log.e("leeh", "success");
			if (mListSeat != null && mListSeat.size() != 0 && msg == null) {
				Log.e("leeh", "success");
				mSeatAdapter.notifyDataSetChanged();
			}
			toastShow(msg, Toast.LENGTH_SHORT);
			break;
		}
	}

	@Override
	public void errorResult(int code) {
		switch (code) {
		case Constants.CODE_LIBRARY_FLOOR_SEAT_INFORMATION:
			if (mListSeat != null && mListSeat.size() != 0 && msg == null) {
				toastShow(msg, Toast.LENGTH_SHORT);
			}
			break;
		}
	}

}
