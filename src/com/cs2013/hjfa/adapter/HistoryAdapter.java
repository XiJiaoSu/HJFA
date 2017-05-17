package com.cs2013.hjfa.adapter;

import java.util.List;

import com.cs2013.hjfa.R;
import com.cs2013.hjfa.pojo.Library;
import com.cs2013.hjfa.pojo.Order;
import com.cs2013.hjfa.utils.DateUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HistoryAdapter extends BaseAdapter{

	
	private List<Order> datas = null;
	private LayoutInflater inflater = null;

	public HistoryAdapter(Context context, List<Order> datas) {
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
		HolderHistory holder=null;
		if (convertView==null) {
			holder=new HolderHistory();
			convertView=inflater.inflate(R.layout.item_history, null);
			holder.tvOName=(TextView) convertView.findViewById(R.id.tv_o_id);
			holder.tvSName=(TextView) convertView.findViewById(R.id.tv_s_name);
			holder.tvTime=(TextView) convertView.findViewById(R.id.tv_h_time);
			holder.tvState=(TextView) convertView.findViewById(R.id.tv_order_state);
			holder.tvLName=(TextView) convertView.findViewById(R.id.tv_l_name);
			holder.tvSLevel=(TextView) convertView.findViewById(R.id.tv_s_level);
			convertView.setTag(holder);
		}else{
			holder=(HolderHistory) convertView.getTag();
		}
		holder.tvSLevel.setText(datas.get(position).getSeat().getLevel()+"\t层");
		holder.tvLName.setText(datas.get(position).getSeat().getLibrary().getName());
		holder.tvOName.setText(datas.get(position).getId());
		holder.tvTime.setText(DateUtil.getTime2(datas.get(position).getOrderTime()));
		holder.tvSName.setText(datas.get(position).getSeat().getName());
		int state=datas.get(position).getState();
		String res="完成";
		if(state==0){
			long c=datas.get(position).getConfirmTime();
			long o=datas.get(position).getOrderTime();
			if (c<o) {
				res="待确定";
			}else{
				res="失效";
			}
		}else if(state==1){
			res="使用中";
		}else{
			res="完成";
		}
		holder.tvState.setText(res);
		return convertView;
	}

	
	class HolderHistory{
		TextView tvSName;
		TextView tvSLevel;
		TextView tvLName;
		TextView tvOName;
		TextView tvTime;
		TextView tvState;
		
	}
}
