package com.m.yifeng;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SettingActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		initActivity();
	}

	private void initActivity() {
		// TODO Auto-generated method stub
		TextView tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText("…Ë÷√");
	}
}
