package com.m.yifeng;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.m.yifeng.tool.DownLoader;
import com.m.yifeng.tool.Global;

public class ViewByStoreActivity extends Activity {

	private ListView mListView;
	private TextView tv_empty;
	private ProgressBar pb;
	private Handler mHandler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewbystore);
		initActivity();
		new Thread(runnable).start();
	}
	private void initActivity() {
		// TODO Auto-generated method stub
		TextView tv_titleTextView = (TextView)findViewById(R.id.tv_title);
		tv_titleTextView.setText("÷‹±ﬂ…Ã∆Ã");
		Button btn_backButton = (Button)findViewById(R.id.btn_top_left);
		btn_backButton.setVisibility(View.VISIBLE);
		btn_backButton.setText("∑µªÿ");
		btn_backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ViewByStoreActivity.this.finish();
			}
		});
		pb = (ProgressBar)findViewById(R.id.pb);
		tv_empty = (TextView)findViewById(R.id.tv_empty);
		mListView = (ListView)findViewById(R.id.mlist);
	}
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{
				String result = DownLoader.donwLoadToString(Global.CUSTOMER_URL);
				Log.e("tag", "result = "+result);
				JSONObject jsonObject = new JSONObject(result);
				String error = jsonObject.getString("error");
				final String message = jsonObject.getString("message");
				if(error.equals("1")){
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							pb.setVisibility(View.GONE);
							tv_empty.setText(message);
						}
					});
				}else{
					mHandler.post(runGui);
				}
				Log.e("tag", "message = "+message);
			}catch(Exception ex){
				
			}
		}
	};
	
	Runnable runGui = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			pb.setVisibility(View.GONE);
		}
	};
}
