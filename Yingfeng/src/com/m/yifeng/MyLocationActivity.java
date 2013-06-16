package com.m.yifeng;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.m.yifeng.tool.DownLoader;
import com.m.yifeng.tool.Global;

public class MyLocationActivity extends Activity {
	private double latitude=0.0;
	private double longitude =0.0;
	private Handler mHandler = new Handler();
	private Map<String,String> map;
	private RelativeLayout rl_info;
	private ProgressBar pb;
	private TextView tv_position;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mylocation);
		map = new HashMap<String, String>();
		rl_info = (RelativeLayout)findViewById(R.id.rl_info);
		pb = (ProgressBar)findViewById(R.id.pb);
		tv_position = (TextView)findViewById(R.id.tv_position);
		Log.e("tag", "--------------1---------------");
		LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			Log.e("tag", "-------------------2------------");
			 Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			 if(location != null){
				 Log.e("tag", "-------------------3------------");
					latitude = location.getLatitude();
					longitude = location.getLongitude();
					Log.e("tag", "---7---"+latitude+"  "+longitude);
					new Thread(runnable).start();
			}else{
				Location location1 = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);   
				 if(location1 != null){   
					 Log.e("tag", "-------------------5------------");
					latitude = location1.getLatitude(); //经度   
					longitude = location1.getLongitude(); //纬度
					Log.e("tag", "---5---"+latitude+"   "+longitude);
					new Thread(runnable).start();
				}else{
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							pb.setVisibility(View.GONE);
							MyLocationActivity.this.finish();
							Toast.makeText(MyLocationActivity.this, "定位失败", Toast.LENGTH_SHORT).show();
						}
					});
				}
			}
		 }else{
			 Log.e("tag", "-------------------4------------");
			 Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);   
			 if(location != null){   
				 Log.e("tag", "-------------------5------------");
				latitude = location.getLatitude(); //经度   
				longitude = location.getLongitude(); //纬度
				Log.e("tag", "---5---"+latitude+"   "+longitude);
				new Thread(runnable).start();
			}   
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			MyLocationActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{
				String result = DownLoader.donwLoadToString(Global.LOCATION_URL+"?lat="+latitude+"&lng="+longitude);
				JSONObject jsonObject = new JSONObject(result);
				String error = jsonObject.getString("error");
				final String message = jsonObject.getString("message");
				if(error.equals("1")){
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							pb.setVisibility(View.GONE);
							MyLocationActivity.this.finish();
							Toast.makeText(MyLocationActivity.this, message, Toast.LENGTH_SHORT).show();
						}
					});
				}else{
					String data = jsonObject.getString("data");
					Log.e("tag", "data = "+data);
					Log.e("tag", "error = "+error);
					Log.e("tag", "message = "+message);
					JSONObject datas = new JSONObject(data);
					String id = datas.getString("id");
					String address = datas.getString("address");
					map.put("id", id);
					map.put("address", address);
					mHandler.post(runGUI);
					Log.e("tag", "id = "+id+"   address = "+address);
				}
			}catch(Exception ex){
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						pb.setVisibility(View.GONE);
						MyLocationActivity.this.finish();
						Toast.makeText(MyLocationActivity.this, "定位失败", Toast.LENGTH_SHORT).show();
					}
				});
			}
		}
	};
	
	Runnable runGUI = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			pb.setVisibility(View.GONE);
			rl_info.setVisibility(View.VISIBLE);
			tv_position.setText(map.get("address"));
		}
	};
	
	
	public void onLocationRight(View v){
		
	}
	
	public void onLocationError(View v){
		MyLocationActivity.this.finish();
	}
}
