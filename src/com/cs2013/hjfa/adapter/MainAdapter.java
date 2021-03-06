package com.cs2013.hjfa.adapter;

import java.util.List;

import com.cs2013.hjfa.R;
import com.cs2013.hjfa.pojo.Library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 主界面的适配器，用于显示主界面的图书馆的信息
 * 
 */
public class MainAdapter extends BaseAdapter {
	private List<Library> datas = null;
	private LayoutInflater inflater = null;

	public MainAdapter(Context context, List<Library> datas) {
		this.datas = datas;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return this.datas == null ? 0 : this.datas.size();
	}

	@Override
	public Object getItem(int position) {
		return this.datas == null ? null : this.datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return this.datas == null ? 0 : position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		HoldView holdView = null;

		if (convertView == null) {
			holdView = new HoldView();

			convertView = inflater.inflate(R.layout.item_ativity_main, null);
//			holdView.mIvHead = (ImageView) convertView
//					.findViewById(R.id.iv_mItem_icon);
			holdView.mTvName = (TextView) convertView
					.findViewById(R.id.tv_mItem_name);
			holdView.mTvContent = (TextView) convertView
					.findViewById(R.id.tv_mList_content);
			convertView.setTag(holdView);
		} else {
			holdView = (HoldView) convertView.getTag();
		}
		
		holdView.mTvName.setText(datas.get(position).getName());
		holdView.mTvContent.setText(datas.get(position).getAddress());
		
		return convertView;
	}

	class HoldView {
//		ImageView mIvHead;
		TextView mTvName;
		TextView mTvContent;
	}
}
