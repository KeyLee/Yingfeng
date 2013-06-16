package com.m.yifeng;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends TabActivity {

	private static TabHost mTabHost;
	static TextView tab_home;
    static TextView tab_cart;
    static TextView tab_person;
    static TextView tab_order;
    static TextView tab_setting;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initActivity();
	}
	private void initActivity() {
//		 TODO Auto-generated method stub
		mTabHost = getTabHost();
		mTabHost.addTab(mTabHost.newTabSpec("main_home").setIndicator(this.getString(R.string.main_home)).setContent(new Intent(MainActivity.this, HomeActivity.class)));
        mTabHost.addTab(mTabHost.newTabSpec("main_cart").setIndicator(this.getString(R.string.main_cart)).setContent(new Intent(MainActivity.this, CartActivity.class)));
        mTabHost.addTab(mTabHost.newTabSpec("main_person").setIndicator(this.getString(R.string.main_person)).setContent(new Intent(MainActivity.this, PersonActivity.class)));
        mTabHost.addTab(mTabHost.newTabSpec("main_order").setIndicator(this.getString(R.string.main_order)).setContent(new Intent(MainActivity.this, OrderActivity.class)));
        mTabHost.addTab(mTabHost.newTabSpec("main_setting").setIndicator(this.getString(R.string.main_setting)).setContent(new Intent(MainActivity.this, SettingActivity.class)));
        
        tab_home = (TextView)findViewById(R.id.tab_home);
        tab_cart = (TextView)findViewById(R.id.tab_cart);
        tab_person = (TextView)findViewById(R.id.tab_person);
        tab_order = (TextView)findViewById(R.id.tab_order);
        tab_setting = (TextView)findViewById(R.id.tab_setting);
        tab_home.setSelected(true);
		mTabHost.setCurrentTab(0);
		tab_home.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				tab_home.setSelected(true);
				tab_cart.setSelected(false);
				tab_person.setSelected(false);
				tab_order.setSelected(false);
				tab_setting.setSelected(false);
				mTabHost.setCurrentTab(0);
			}
		});
		tab_cart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tab_home.setSelected(false);
				tab_person.setSelected(false);
				tab_cart.setSelected(true);
				tab_order.setSelected(false);
				tab_setting.setSelected(false);
				mTabHost.setCurrentTab(1);
			}
		});
		tab_person.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				tab_home.setSelected(false);
				tab_cart.setSelected(false);
				tab_person.setSelected(true);
				tab_order.setSelected(false);
				tab_setting.setSelected(false);
				mTabHost.setCurrentTab(2);
			}
		});
		
		tab_order.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				tab_home.setSelected(false);
				tab_cart.setSelected(false);
				tab_person.setSelected(false);
				tab_order.setSelected(true);
				tab_setting.setSelected(false);
				mTabHost.setCurrentTab(3);
			}
		});

		tab_setting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				tab_home.setSelected(false);
				tab_cart.setSelected(false);
				tab_person.setSelected(false);
				tab_order.setSelected(false);
				tab_setting.setSelected(true);
				mTabHost.setCurrentTab(4);
			}
		});
	}
	
	public static void onTabOrder(){
		tab_home.setSelected(false);
		tab_cart.setSelected(false);
		tab_person.setSelected(false);
		tab_order.setSelected(true);
		tab_setting.setSelected(false);
		mTabHost.setCurrentTab(3);
	}
}
