package com.m.yifeng;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.m.yifeng.tool.Global;
import com.m.yifeng.tool.ToolHelper;

public class MyPositionActivity extends Activity implements OnItemClickListener {

	private ListView mListView;
	private ProgressBar pb;
	private RelativeLayout rl;
	private SimpleAdapter adapter;
	private List<Map<String,String>> adrresses;
	private String q = "";
	private Handler mHandler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myposition);
		q = getIntent().getStringExtra("q");
		initActivity();
		new Thread(rundownData).start();
	}

	private void initActivity() {
		// TODO Auto-generated method stub
		TextView tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText("ËÑË÷½á¹û");
		Button btn = (Button)findViewById(R.id.btn_top_left);
		btn.setVisibility(View.VISIBLE);
		btn.setText("·µ»Ø");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				MyPositionActivity.this.finish();
			}
		});
		mListView = (ListView)findViewById(R.id.list);
		pb = (ProgressBar)findViewById(R.id.pb);
		rl = (RelativeLayout)findViewById(R.id.rl);
		rl.setVisibility(View.GONE);
		adrresses = new ArrayList<Map<String,String>>();
		adapter = new SimpleAdapter(this, adrresses, R.layout.position_item, new String[]{"address"}, new int[]{R.id.tv_position});
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			MyPositionActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	Runnable  rundownData = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{
				String area = URLEncoder.encode(q,"utf8");
				String result = ToolHelper.donwLoadToString(Global.SEARCH_RESULT_RUL+"?q="+area);
				Log.e("tag", "url = "+Global.SEARCH_RESULT_RUL+"?q="+area);
				JSONObject jsonObject = new JSONObject(result);
				String error = jsonObject.getString("error");
				final String message = jsonObject.getString("message");
				Log.e("tag", "message = "+message);
				if(!error.equals("0")){
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText(MyPositionActivity.this, message, Toast.LENGTH_SHORT).show();
							MyPositionActivity.this.finish();
						}
					});
				}else{
					String datas = jsonObject.getString("data");
					JSONObject jsonObjectdatas = new JSONObject(datas);
					String list_datas = jsonObjectdatas.getString("list_data");
					JSONObject jsonDetails = new JSONObject(list_datas);
					Iterator<?> it = jsonDetails.keys(); 
		            String id = ""; 
		            String address = "";
		            Log.e("tag", "data = "+list_datas);
		            while(it.hasNext()){
		            	Map<String,String> map = new HashMap<String, String>();
		                id = (String) it.next().toString(); 
		                address = jsonDetails.getString(id);
		                map.put("id", id);
		                map.put("address", address);
		                Log.e("tag", "address = "+address);
		                adrresses.add(map);
		            }
		            mHandler.post(ruGUI);
				}
			}catch(Exception ex){
				Log.e("tag", "error = "+ex.getMessage());
			}
		}
	};
	Runnable ruGUI = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			rl.setVisibility(View.VISIBLE);
			pb.setVisibility(View.GONE);
			adapter.notifyDataSetChanged();
		}
	};
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this,PositionHomeActivity.class);
		intent.putExtra("qb", adrresses.get(arg2).get("id"));
		startActivity(intent);
	}
}
