package com.cs2013.hjfa.adapter;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cs2013.hjfa.R;
import com.cs2013.hjfa.pojo.Message;
import com.cs2013.hjfa.utils.DateUtil;

public class MessgeAdapter extends BaseAdapter{
	private List<Message> messages;
	private Context context;
	private LayoutInflater inflater;
	public MessgeAdapter(List<Message> messages,Context context)
	{
		this.messages = messages;
		this.context = context;
		inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return messages.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return messages.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Viewholder holder = null;
		if(convertView == null)
		{
			convertView = inflater.inflate(R.layout.item_fragment_msg, null);
			holder = new Viewholder();
			holder.mTv_title = (TextView) convertView.findViewById(R.id.tv_message_title);
			holder.mTv_date = (TextView) convertView.findViewById(R.id.tv_message_date);
			holder.mTv_content = (TextView) convertView.findViewById(R.id.tv_message_content);
			convertView.setTag(holder);
		}else
		{
			holder = (Viewholder) convertView.getTag();
		}
		Message message = messages.get(position);
		holder.mTv_title.setText(message.getTitle());
		holder.mTv_content.setText(message.getMessage());
		holder.mTv_date.setText(DateUtil.getTime(message.getTime()));
		return convertView;
	}
	public class Viewholder{
		private TextView mTv_title;
		private TextView mTv_date;
		private TextView mTv_content;
	}

}
