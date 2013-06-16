package com.m.yifeng.adapter;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.m.yifeng.R;
import com.m.yifeng.util.AreaInfo;

public class AreaAdapter extends BaseAdapter {

	private List<AreaInfo> areaInfos;
	private LayoutInflater lay;
	
	public AreaAdapter(List<AreaInfo> areaInfos,Context context) {
		super();
		this.areaInfos = areaInfos;
		lay = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return areaInfos.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return areaInfos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if(convertView == null){
			convertView = lay.inflate(R.layout.default_area_item, null);
			viewHolder = new ViewHolder();
			viewHolder.btn_isdefault = (Button)convertView.findViewById(R.id.btn_isdefalut);
			viewHolder.tv_address = (TextView)convertView.findViewById(R.id.tv_address);
			viewHolder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
			viewHolder.tv_phonenum = (TextView)convertView.findViewById(R.id.tv_phonenumber);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		String isDefault = areaInfos.get(position).getIs_default();
		if(isDefault.equals("1")){
			viewHolder.btn_isdefault.setBackgroundResource(R.drawable.default_icon_pressed);
		}else{
			viewHolder.btn_isdefault.setBackgroundResource(R.drawable.default_icon_normal);
		}
		viewHolder.btn_isdefault.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		viewHolder.tv_address.setText(Html.fromHtml("<font color=#FF0000>寄送至:</font>")+areaInfos.get(position).getText());
		viewHolder.tv_name.setText("收件人:"+areaInfos.get(position).getConsignee());
		viewHolder.tv_phonenum.setText("联系电话:"+areaInfos.get(position).getTel());
		return convertView;
	}
	private class ViewHolder{
		private Button	btn_isdefault;
		private TextView tv_address;
		private TextView tv_name;
		private TextView tv_phonenum;
	}
}
