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
			holder.tvName=(TextView) convertView.findViewById(R.id.tv_h_name);
			holder.tvTime=(TextView) convertView.findViewById(R.id.tv_h_time);
			holder.tvState=(TextView) convertView.findViewById(R.id.tv_order_state);
			convertView.setTag(holder);
		}else{
			holder=(HolderHistory) convertView.getTag();
		}
		holder.tvName.setText("订单编号:"+datas.get(position).getName());
		holder.tvTime.setText("预定时间:"+DateUtil.getTime2(datas.get(position).getOrderTime()));
		int state=datas.get(position).getState();
		String res="完成";
		if(state!=1){
			long c=datas.get(position).getConfirmTime();
			long o=datas.get(position).getOrderTime();
			if (c<o) {
				res="失效";
			}else{
				res="未确定";
			}
			
		}
		holder.tvState.setText(res);
		return convertView;
	}

	
	class HolderHistory{
		TextView tvName;
		TextView tvTime;
		TextView tvState;
		
	}
}
