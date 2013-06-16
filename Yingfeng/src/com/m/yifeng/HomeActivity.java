package com.m.yifeng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.m.yifeng.tool.Global;
import com.m.yifeng.tool.ToolHelper;

@SuppressLint("HandlerLeak")
public class HomeActivity extends Activity {

	
	private ListView mList;
	private List<Map<String,String>> articlesMapLists;
	private Handler mHandler = new Handler();
	private SimpleAdapter adapter;
	private ProgressBar pb;
	private EditText et_address;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		initActivity();
		new Thread(downDatas).start();
	}

	private void initActivity() {
		// TODO Auto-generated method stub
		pb = (ProgressBar)findViewById(R.id.pb);
		mList = (ListView)findViewById(R.id.list);
		articlesMapLists = new ArrayList<Map<String,String>>();
		adapter = new SimpleAdapter(this, articlesMapLists, R.layout.article_item, new String[]{"title","description"}, new int[]{R.id.title,R.id.description});
		mList.setAdapter(adapter);
		et_address = (EditText)findViewById(R.id.et_input_address);
	}

	public void onMyLocation(View v){
		startActivity(new Intent(this,MyLocationActivity.class));
	}
	
	public void onMyDefaultLocation(View v){
		if(Global.strCookies.equals("")){
			startActivity(new Intent(this,LoginActivity.class));
		}else{
			startActivity(new Intent(this,PositionHomeActivity.class));
		}
	}
	
	public void onSearch(View v){
		String address = et_address.getText().toString();
		if(address.equals("")){
			Toast.makeText(this, "«Î ‰»Îµÿ÷∑", Toast.LENGTH_SHORT).show();
			return;
		}
		Intent intent = new Intent(this,MyPositionActivity.class);
		intent.putExtra("q", address);
		startActivity(intent);
	}
	
	public void onCode(View v){
		
	}
	
	Runnable downDatas = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String result = ToolHelper.donwLoadToString(Global.HOME_URL);
			if(result.equals("") || result.equals("-1")){
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
				Log.e("tag", "home_result = "+result);
				final String error = jsonObject.getString("error");
		//		final String message = jsonObject.getString("message");
				final String datas = jsonObject.getString("data");
				Log.e("tag", "error = "+error);
				JSONObject jsonObjectDatas = new JSONObject(datas);
				final String articlesDatas = jsonObjectDatas.getString("articles");
			//	final String sides = jsonObjectDatas.getString("slide");
				JSONArray jsonArray = new JSONArray(articlesDatas);
				int len = jsonArray.length();
				int i = 0;
				Log.e("tag", "len = "+len);
				for(;i<len;i++){
					JSONObject jsoObjectDe = jsonArray.getJSONObject(i);
					Map<String, String> map = new HashMap<String, String>();
					map.put("title", jsoObjectDe.getString("title"));
					map.put("description", jsoObjectDe.getString("description"));
					Log.e("tag", "-->"+jsoObjectDe.getString("description"));
					map.put("id", jsoObjectDe.getString("id"));
					map.put("add_time", jsoObjectDe.getString("add_time"));
					articlesMapLists.add(map);
				}
				mHandler.post(uiRun);
			}catch(Exception ex){
				Log.e("tag", "error = "+ex.getMessage());
			}
		}
	};
	
	Runnable uiRun = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			pb.setVisibility(View.GONE);
			adapter.notifyDataSetChanged();
		}
	};
}
