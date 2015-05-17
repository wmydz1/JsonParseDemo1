package com.logoocc.jsonparsedemo1.Adapter;

import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.logoocc.jsonparsedemo1.R;
import com.logoocc.jsonparsedemo1.bean.User;
import com.logoocc.jsonparsedemo1.util.BitMapChace;

import android.R.raw;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	private List<User> mList;
	private Context context;
	private RequestQueue mQueue;

	public MyAdapter (Context context, List<User> mList,RequestQueue mQueue) {

		this.mList = mList;
		this.context = context;
		this.mQueue = mQueue;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item,
					null);
			viewHolder.mId = (TextView) convertView
					.findViewById(R.id.item_tv_id);
			viewHolder.mName = (TextView) convertView
					.findViewById(R.id.item_tv_name);
			viewHolder.mTime = (TextView) convertView
					.findViewById(R.id.item_tv_time);

			viewHolder.mImg=(NetworkImageView) convertView.findViewById(R.id.item_iv);
			
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.mId.setText(mList.get(position).getId()+"");
		viewHolder.mName.setText(mList.get(position).getName());
		viewHolder.mTime.setText(mList.get(position).getTime());
		ImageLoader imageLoader=new ImageLoader(mQueue, new BitMapChace());
		
		viewHolder.mImg.setDefaultImageResId(R.drawable.ic_launcher);
		viewHolder.mImg.setErrorImageResId(R.drawable.mmd);
		viewHolder.mImg.setImageUrl(mList.get(position).getImg(), imageLoader);
		

		return convertView;
	}

	private class ViewHolder {
		TextView mId;
		TextView mName;
		TextView mTime;
		NetworkImageView mImg;
	}

}
