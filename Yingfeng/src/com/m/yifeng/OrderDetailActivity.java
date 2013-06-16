package com.m.yifeng;

import com.m.yifeng.tool.DownLoader;
import com.m.yifeng.tool.Global;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class OrderDetailActivity extends Activity {

	private String order_sn = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_detail);
		order_sn = getIntent().getStringExtra("order_sn");
		initActivity();
		new Thread(runnable).start();
	}
	private void initActivity() {
		// TODO Auto-generated method stub
		
	}
	
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String result = DownLoader.donwLoadToString(Global.ORDER_DETAIL_URL+"?order_sn="+order_sn);
			Log.e("tag", "result = "+result);
		}
	};
}
