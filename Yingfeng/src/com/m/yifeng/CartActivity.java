package com.m.yifeng;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.m.yifeng.tool.DownLoader;
import com.m.yifeng.tool.Global;

public class CartActivity extends Activity {

	private RelativeLayout rl_login;
	private RelativeLayout rl_info;
	private boolean isShowed = false;
	private ProgressBar pb;
	private TextView tv_empty;
	private Handler mHandler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cart);
		initActivity();
		LoadData();
	}

	private void initActivity() {
		// TODO Auto-generated method stub
		TextView tvTitle = (TextView)findViewById(R.id.tv_title);
		tvTitle.setText("我的购物车");
		rl_login = (RelativeLayout)findViewById(R.id.rl_login);
		rl_info = (RelativeLayout)findViewById(R.id.rl_in);
		Button btn_refresh = (Button)findViewById(R.id.btn_top_right);
		btn_refresh.setVisibility(View.VISIBLE);
		btn_refresh.setText("刷新");
		btn_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		pb = (ProgressBar)findViewById(R.id.pb);
		tv_empty = (TextView)findViewById(R.id.tv_empty);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		LoadData();
		super.onResume();
	}

	public void onLogin(View v){
		startActivityForResult(new Intent(this,LoginActivity.class),100);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode == 100){
			LoadData();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	private void LoadData(){
		if(Global.strCookies.equals("")){
			rl_login.setVisibility(View.VISIBLE);
			rl_info.setVisibility(View.GONE);
		}else{
			rl_login.setVisibility(View.GONE);
			rl_info.setVisibility(View.VISIBLE);
			if(!isShowed){
				new Thread(runDownData).start();
				isShowed = true;
			}
		}
	}
	
	Runnable runDownData = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String result = DownLoader.donwLoadToString(Global.CART_URL);
			Log.e("tag", "result111 = "+result);
			try{
				JSONObject jsonObject = new JSONObject(result);
				final String message = jsonObject.getString("message");
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						pb.setVisibility(View.GONE);
						tv_empty.setText(message);
					}
				});
			}catch(Exception ex){
				Log.e("tag", "error = "+ex.getMessage());
			}
		}
	};
	
}
