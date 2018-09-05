package com.example.jett.mvp_project.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jett.mvp_project.R;
import com.example.jett.mvp_project.ViewPagerIndicatorActivity;
import com.example.jett.mvp_project.bean.Girl;

import java.util.List;


public class GirlAdapter extends BaseAdapter {
	
	private LayoutInflater inflater;
	private List<Girl> girls;
	private Context mContext;

	public GirlAdapter(Context context, List<Girl> girs) {
		inflater = LayoutInflater.from(context);
		this.mContext=context;
		this.girls = girs;
	}

	@Override
	public int getCount() {
		return girls.size();
	}

	@Override
	public Object getItem(int position) {
		return girls.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = inflater.inflate(R.layout.item, null);
		Girl g = girls.get(position);
		ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
		iv_icon.setImageResource(g.icon);
		
		TextView tv_like = (TextView) view.findViewById(R.id.tv_like);
		tv_like.setText("喜欢程度:"+g.like);
		
		TextView tv_style = (TextView) view.findViewById(R.id.tv_style);
		tv_style.setText(g.style);
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(mContext, ViewPagerIndicatorActivity.class);
				mContext.startActivity(intent);
			}
		});
		return view;
	}

}
