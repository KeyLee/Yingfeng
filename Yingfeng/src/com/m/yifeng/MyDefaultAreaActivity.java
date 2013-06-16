package com.m.yifeng;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.m.yifeng.adapter.AreaAdapter;
import com.m.yifeng.tool.DownLoader;
import com.m.yifeng.tool.Global;
import com.m.yifeng.util.AreaInfo;

public class MyDefaultAreaActivity extends Activity implements OnItemClickListener {

	private ListView mListView;
	private ProgressBar pb;
	private List<AreaInfo> areaInfos;
	private AreaAdapter adapter;
	private Handler mHandler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.default_area);
		initActivity();
		new Thread(runDownData).start();
	}

	private void initActivity() {
		// TODO Auto-generated method stub
		mListView = (ListView)findViewById(R.id.list);
		pb = (ProgressBar)findViewById(R.id.pb);
		TextView tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText("收货地址列表");
		Button btn_back = (Button)findViewById(R.id.btn_top_left);
		btn_back.setText("返回");
		btn_back.setVisibility(View.VISIBLE);
		btn_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				MyDefaultAreaActivity.this.finish();
			}
		});
		Button btn_add = (Button)findViewById(R.id.btn_top_right);
		btn_add.setText("添加");
		btn_add.setVisibility(View.VISIBLE);
		btn_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MyDefaultAreaActivity.this,AddAeraAddressActivity.class));
			}
		});
		areaInfos = new ArrayList<AreaInfo>();
		adapter = new AreaAdapter(areaInfos, this);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(this);
	}
	
	Runnable runDownData = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String result = DownLoader.donwLoadToString(Global.DEFAULT_AREA_URL);
			Log.e("tag", "result = "+result);
			try{
				JSONObject jsonObject = new JSONObject(result);
				String error = jsonObject.getString("error");
				final String message = jsonObject.getString("message");
				String data = jsonObject.getString("data");
				if(error.equals("1")){
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							pb.setVisibility(View.GONE);
							Toast.makeText(MyDefaultAreaActivity.this, message, Toast.LENGTH_SHORT).show();
						}
					});
				}else{
					JSONArray jsonArray = new JSONArray(data);
					int len = jsonArray.length();
					int i = 0;
					for(;i<len;i++){
						JSONObject jsonObject2 = jsonArray.getJSONObject(i);
						AreaInfo areaInfo = new AreaInfo();
						areaInfo.setAddress_id(jsonObject2.getString("address_id"));
						areaInfo.setConsignee(jsonObject2.getString("consignee"));
						areaInfo.setIs_default(jsonObject2.getString("is_default"));
						areaInfo.setTel(jsonObject2.getString("tel"));
						areaInfo.setText(jsonObject2.getString("text"));
						areaInfos.add(areaInfo);
					}
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							pb.setVisibility(View.GONE);
							adapter.notifyDataSetChanged();
						}
					});
				}
			}catch(Exception ex){
				
			}
		}
	};
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "arg2 = "+arg2, Toast.LENGTH_SHORT).show();
	}
	
}
