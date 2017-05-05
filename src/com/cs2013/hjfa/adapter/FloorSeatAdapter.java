package com.cs2013.hjfa.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cs2013.hjfa.R;
import com.cs2013.hjfa.pojo.Seat;

public class FloorSeatAdapter extends BaseAdapter{

	private List<Seat> seatList = null;
	private LayoutInflater inflater = null;
	
	public FloorSeatAdapter(Context context, List<Seat> seatList) {
		this.seatList = seatList;
		this.inflater = LayoutInflater.from(context);
	}

	
	@Override
	public int getCount() {
		
		return seatList == null ? 0 : seatList.size();
	}

	@Override
	public Object getItem(int position) {
		
		return seatList == null ? null : seatList.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		HoldView holdView = null;
		if (convertView == null) {
			holdView = new HoldView();
			convertView = inflater.inflate(R.layout.item_library_seats, null);
			holdView.mTvSeat = (TextView) convertView.findViewById(R.id.tv_item_seat);
			convertView.setTag(holdView);
		}else{
			holdView = (HoldView) convertView.getTag();
		}
		Seat seat = seatList.get(position);
		if (!seat.getState().equals("0")) {
			holdView.mTvSeat.setBackgroundColor(Color.RED);
		}
		holdView.mTvSeat.setText(seat.getName());
		
		return convertView;
	}
	class HoldView {
		TextView mTvSeat;
	}
}
