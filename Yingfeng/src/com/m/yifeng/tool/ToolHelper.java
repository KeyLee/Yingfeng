package com.m.yifeng.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

public class ToolHelper {

	public static String donwLoadToString(String urlStr){
		StringBuffer sb = new StringBuffer();
		BufferedReader bReader = null;
		String line = null;
		try {
			InputStream inputStream = getIsFromUrl(urlStr);
			if(inputStream == null){
				return "";
			}
			bReader = new BufferedReader(new InputStreamReader(inputStream));
			while ((line = bReader.readLine()) != null) {
				sb.append(line);
			}
			bReader.close();
			return sb.toString();
		} catch (Exception e) {
			return "";
		}
	}
	/* 
	　　* 获取当前程序的版本号 
	　　*/ 
	public static String getVersionName(Context context) {
		try{
			PackageManager packageManager = context.getPackageManager(); 
			PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			return packInfo.versionName; 
		}catch (Exception e) {
			// TODO: handle exception
			return "";
		}
	}
	public static InputStream getIsFromUrl(String urlStr){
		try{
			URL url = new URL(urlStr);
			HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
			urlConnection.setConnectTimeout(7000);
			urlConnection.setRequestMethod("GET");
			return urlConnection.getInputStream();
		}catch(Exception ex){
			return null;
		}
	}
	
	public static Uri getImageURI(String path, File cache) throws Exception {
		String name = MD5.getMD5(path) + path.substring(path.lastIndexOf("."));
		File file = new File(cache, name);
		if (file.exists()) {
			return Uri.fromFile(file);
		} else {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			if (conn.getResponseCode() == 200) {
				InputStream is = conn.getInputStream();
				FileOutputStream fos = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}
				is.close();
				fos.close();
				return Uri.fromFile(file);
			}
		}
		return null;
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String time2Str(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMDDHHmmss");
		Date curDate = new Date(System.currentTimeMillis());
		return formatter.format(curDate);
	}
	
	public static boolean netIsAvail(Context context){   
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);   
       if (manager == null) {   
           return false;   
       }   
       NetworkInfo networkinfo = manager.getActiveNetworkInfo();   
       if (networkinfo == null || !networkinfo.isAvailable()) {   
           return false;   
       }   
       return true;   
 	}
}
