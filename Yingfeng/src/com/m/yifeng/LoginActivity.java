package com.m.yifeng;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.m.yifeng.tool.DownLoader;
import com.m.yifeng.tool.Global;

public class LoginActivity extends Activity {

	private EditText et_name;
	private EditText et_password;
	private Handler mHandler = new Handler();
	private ProgressDialog pd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		initActivity();
	}
	
	private void initActivity() {
		// TODO Auto-generated method stub
		et_name = (EditText)findViewById(R.id.edit_name);
		et_password = (EditText)findViewById(R.id.edit_password);
		
		et_name.setText("13168880167");
		et_password.setText("123456");
		pd = new ProgressDialog(this);
		pd.setMessage("µ«¬Ω÷–,«Î…‘∫Ú...");
	}

	public void onLogin(View v){
		final String name = et_name.getText().toString();
		if(name.equals("")){
			To("«Î ‰»Î”√ªß√˚");
			return;
		}
		final String password = et_password.getText().toString();
		if(password.equals("")){
			To("«Î ‰»Î√‹¬Î");
			return;
		}
		pd.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String result = DownLoader.donwLoadToString(Global.LOGIN_URL+"?uname="+name+"&password="+password);
				if(result.equals("") || result.equals("-1")){
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							pd.cancel();
							To("µ«¬Ω ß∞‹,«ÎºÏ≤ÈÕ¯¬Á…Ë÷√");
						}
					});
				}
				try{
					JSONObject jsonObject = new JSONObject(result);
					final String error = jsonObject.getString("error");
					final String message = jsonObject.getString("message");
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							pd.cancel();
							if(error.equals("0")){
								//startActivity(new Intent(LoginActivity.this,MainActivity.class));
								LoginActivity.this.finish();
							}else{
								To(message);
							}
						}
					});
				}catch(Exception ex){
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							pd.cancel();
							To("µ«¬Ω ß∞‹,«ÎºÏ≤ÈÕ¯¬Á…Ë÷√");
						}
					});
				}
			}
		}).start();
	}
	private void To(String str){
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
}
