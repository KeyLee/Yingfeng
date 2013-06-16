package com.m.yifeng;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.m.yifeng.tool.DownLoader;
import com.m.yifeng.tool.Global;

public class AddAeraAddressActivity extends Activity {

	private AutoCompleteTextView atv_address;
	private EditText et_signed;
	private EditText et_number;
	private EditText et_name;
	private List<String> aListAddress;
	private MyListAdater adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_area);
		initActivity();
	}

	private void initActivity() {
		// TODO Auto-generated method stub
		TextView tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText("添加收货地址");
		Button btn_back = (Button)findViewById(R.id.btn_top_left);
		btn_back.setText("返回");
		btn_back.setVisibility(View.VISIBLE);
		btn_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AddAeraAddressActivity.this.finish();
			}
		});
		atv_address = (AutoCompleteTextView)findViewById(R.id.et_input_address);
		et_name = (EditText)findViewById(R.id.et_input_name);
		et_number = (EditText)findViewById(R.id.et_input_phonenumber);
		et_signed = (EditText)findViewById(R.id.et_input_sign);
		aListAddress = new ArrayList<String>();
		adapter = new MyListAdater(aListAddress, this);
//		atv_address.setAdapter()
	}
	public void onCommit(View v){
		final String address = atv_address.getText().toString();
		if(address.equals("")){
			To("请输入您的具体收货的地址");
			return;
		}
		final String name = et_name.getText().toString();
		if(name.equals("")){
			To("收货人称呼");
			return;
		}
		final String number = et_number.getText().toString();
		if(number.equals("")){
			To("请输入收货联系电话号码");
			return;
		}
		final String signed_address = et_signed.getText().toString();
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try{
					String na = URLEncoder.encode(name,"utf8");
					String ad = URLEncoder.encode(address,"utf8");
					String si_ad = URLEncoder.encode(signed_address,"utf8");
					String result = DownLoader.donwLoadToString(Global.ADD_AREA_URL+"?");
					
				}catch(Exception ex){
					
				}
			}
		}).start();
	}
	private void To(String str){
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
	
	private class MyListAdater extends BaseAdapter{

		private List<String> mLists;
		private LayoutInflater lay;
		
		public MyListAdater(List<String> mLists,Context context) {
			super();
			this.mLists = mLists;
			lay = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mLists.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return mLists.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int positon, View convertView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder;
			if(convertView == null){
				viewHolder = new ViewHolder();
				convertView = lay.inflate(R.layout.simple_item, null);
				viewHolder.tvaddress = (TextView)convertView.findViewById(R.id.text);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder)convertView.getTag();
			}
			viewHolder.tvaddress.setText(mLists.get(positon));
			return convertView;
		}
		private class ViewHolder{
			private TextView  tvaddress;
		}
	}
}
