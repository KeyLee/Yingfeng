package com.m.yifeng;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.m.yifeng.tool.DownLoader;
import com.m.yifeng.tool.Global;

public class PersonActivity extends Activity {
	private RelativeLayout rl_login;
	private RelativeLayout rl_info;
	private Handler mHandler = new Handler();
	private ProgressBar pb;
	private Map<String,String> map;
	private RelativeLayout rl_my_order;
	private RelativeLayout rl_my_address;
	
	private TextView tv_name;
	private TextView tv_score;
	
	private boolean isShowed = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person);
		initActivity();
	}
	private void initActivity() {
		// TODO Auto-generated method stub
		TextView tvTitle = (TextView)findViewById(R.id.tv_title);
		tvTitle.setText("个人中心");
		rl_login = (RelativeLayout)findViewById(R.id.rl_login);
		rl_info = (RelativeLayout)findViewById(R.id.rl_info);
		pb = (ProgressBar)findViewById(R.id.pb);
		pb.setVisibility(View.GONE);
		map = new HashMap<String, String>();
		rl_my_order = (RelativeLayout)findViewById(R.id.rl1);
		rl_my_order.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				MainActivity.onTabOrder();
			}
		});
		rl_my_address = (RelativeLayout)findViewById(R.id.rl2);
		rl_my_address.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(PersonActivity.this,MyDefaultAreaActivity.class));
			}
		});
		tv_name = (TextView)findViewById(R.id.tv_name);
		tv_score = (TextView)findViewById(R.id.tv_score);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		LoadData();
		super.onResume();
	}
	public void onLogin(View v){
		startActivityForResult(new Intent(this,LoginActivity.class),200);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode == 200){
			LoadData();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	private void LoadData(){
		if(Global.strCookies.equals("")){
			rl_login.setVisibility(View.VISIBLE);
			rl_info.setVisibility(View.GONE);
			pb.setVisibility(View.GONE);
		}else{
			rl_login.setVisibility(View.GONE);
			if(!isShowed){
				pb.setVisibility(View.VISIBLE);
				new Thread(runDownData).start();
				isShowed = true;
			}
		}
	}
	
	Runnable runDownData = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String result = DownLoader.donwLoadToString(Global.USER_URL);
			Log.e("tag", "result = "+result);
			if(result.equals("-1")){
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						pb.setVisibility(View.GONE);
					}
				});
			}
			try{
				JSONObject jsonObject = new JSONObject(result);
				String error = jsonObject.getString("error");
				if(error.equals("0")){
					String data = jsonObject.getString("data");
					JSONObject jsonObjectDatas = new JSONObject(data);
					map.put("nickname", jsonObjectDatas.getString("nickname"));
					map.put("pay_points", jsonObjectDatas.getString("pay_points"));
					mHandler.post(runUI);
				}else{
					
				}
			}catch(Exception ex){
				Log.e("tag", "error = "+ex.getMessage());
			}
		}
	};
	Runnable  runUI = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			pb.setVisibility(View.GONE);
			tv_name.setText(map.get("nickname")+" 你好");
			tv_score.setText(map.get("pay_points")+"分");
			rl_info.setVisibility(View.VISIBLE);
		}
	};
}
