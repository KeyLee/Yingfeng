package com.m.yifeng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

import com.m.yifeng.tool.DownLoader;
import com.m.yifeng.tool.Global;

public class OrderActivity extends Activity implements OnItemClickListener {
	
	private RelativeLayout rl_login;
	private RelativeLayout rl_order;
	private ProgressBar pb;
	private boolean isShowed = false;
	private List<Map<String,String>> orderLists;
	private SimpleAdapter adapter;
	private ListView mOrderList;
	private Handler mHandler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order);
		initActivity();
	}
	private void initActivity() {
		// TODO Auto-generated method stub
		TextView tvTitle = (TextView)findViewById(R.id.tv_title);
		tvTitle.setText("订单列表");
		rl_login = (RelativeLayout)findViewById(R.id.rl_login);
		rl_order = (RelativeLayout)findViewById(R.id.rl_order);
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
		mOrderList = (ListView)findViewById(R.id.list);
		orderLists = new ArrayList<Map<String,String>>();
		adapter = new SimpleAdapter(this, orderLists, R.layout.order_list_item, new String[]{"order_sn","shipping_fee","total_fee","short_order_time","address"},
				new int[]{R.id.tv_order_sn,R.id.tv_shipping_fee,R.id.tv_total_fee,R.id.tv_short_order_time,R.id.tv_address});
		mOrderList.setAdapter(adapter);
		mOrderList.setOnItemClickListener(this);
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
			rl_order.setVisibility(View.GONE);
		}else{
			rl_login.setVisibility(View.GONE);
			rl_order.setVisibility(View.VISIBLE);
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
			String result = DownLoader.donwLoadToString(Global.ORDER_RUL);
			Log.e("tag", "result = "+result);
			try{
				JSONObject jsonObject = new JSONObject(result);
				String datas = jsonObject.getString("data");
				JSONObject jsonObject1 = new JSONObject(datas);
				String orders_list = jsonObject1.getString("orders_list");
				JSONArray jsonArray = new JSONArray(orders_list);
				int len = jsonArray.length();
				int i = 0;
				Log.e("tag", "len = "+len);
				for(;i<len;i++){
					JSONObject jsonObjectdatass = jsonArray.getJSONObject(i);
					Map<String,String> map = new HashMap<String, String>();
					map.put("order_sn", jsonObjectdatass.getString("order_sn"));
					map.put("order_status", jsonObjectdatass.getString("order_status"));
					map.put("goods_amount", jsonObjectdatass.getString("goods_amount"));
					map.put("shipping_fee", "配送运费:￥"+jsonObjectdatass.getString("shipping_fee"));
					map.put("sign_building", jsonObjectdatass.getString("sign_building"));
					map.put("address", "地址："+jsonObjectdatass.getString("address"));
					map.put("total_fee", "订单总价:￥"+jsonObjectdatass.getString("total_fee"));
					map.put("short_order_time", jsonObjectdatass.getString("short_order_time"));
					Log.e("tag", "data = "+map.toString());
					orderLists.add(map);
				}
				mHandler.post(runUI);
			}catch(Exception ex){
				Log.e("tag", "error = "+ex.getMessage());
			}
		}
	};
	Runnable runUI = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			pb.setVisibility(View.GONE);
			adapter.notifyDataSetChanged();
		}
	};
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this,OrderDetailActivity.class);
		intent.putExtra("order_sn", orderLists.get(arg2).get("order_sn"));
		startActivity(intent);
	}
}
