package com.m.yifeng;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ViewByClassActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewbyclass);
		initActivity();
	}

	private void initActivity() {
		// TODO Auto-generated method stub
		TextView tv_titleTextView = (TextView)findViewById(R.id.tv_title);
		tv_titleTextView.setText("全部分类");
		Button btn_backButton = (Button)findViewById(R.id.btn_top_left);
		btn_backButton.setVisibility(View.VISIBLE);
		btn_backButton.setText("返回");
		btn_backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ViewByClassActivity.this.finish();
			}
		});
	}
}
