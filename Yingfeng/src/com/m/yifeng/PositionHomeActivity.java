package com.m.yifeng;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.m.yifeng.tool.Global;
import com.m.yifeng.tool.ToolHelper;

public class PositionHomeActivity extends Activity {
	private String qb = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.position_home);
		qb = getIntent().getStringExtra("qb");
		new Thread(runDownData).start();
	}
	Runnable runDownData = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String url = Global.HOME_AREA_URL;
			if(qb != null){
				url = url+"?qb="+qb;
			}
			String result = ToolHelper.donwLoadToString(url);
			try{
				JSONObject jsonObject = new JSONObject(result);
				String error = jsonObject.getString("error");
				String message = jsonObject.getString("message");
				Log.e("tag", "error = "+error);
				Log.e("tag", "message = "+message);
				Log.e("tag", "data = "+jsonObject.getString("data"));
			}catch(Exception ex){
				Log.e("tag", "Exception = "+ex.getMessage());
			}
		}
	};
	public void onViewStore(View v){
		startActivity(new Intent(this,ViewByStoreActivity.class));
	}
	
	public void onViewClass(View v){
		startActivity(new Intent(this,ViewByClassActivity.class));
	}
}
