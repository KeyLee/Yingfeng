package com.m.yifeng.tool;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import android.util.Log;

public class DownLoader {
	
	private static int count = 0;
	public static String donwLoadToString(String urlStr){
		StringBuffer sb = new StringBuffer();
		BufferedReader bReader = null;
		HttpURLConnection urlConnection = null;
		String line = null;
		try {
			URL url = new URL(urlStr);
			urlConnection = (HttpURLConnection)url.openConnection();
			if( !Global.strCookies.equals("")){
				urlConnection.setRequestProperty("Cookie", Global.strCookies);
			}
			urlConnection.setConnectTimeout(8000);
			urlConnection.setRequestMethod("GET");
			urlConnection.setReadTimeout(8000);
			urlConnection.setDoInput(true);
			InputStream inputStream = urlConnection.getInputStream();
			if(inputStream == null){
				return "-1";
			}
			bReader = new BufferedReader(new InputStreamReader(inputStream,"GBK"));
			while ((line = bReader.readLine()) != null) {
				sb.append(line);
			}
			bReader.close();
		} catch (Exception e) {
			Log.e("tag", "error = "+e.getMessage());
			if(count == 3){
				return "-1";
			}
			count ++;
			return donwLoadToString(urlStr);
		}
		if(Global.strCookies.equals("") && urlConnection!= null ){
			setCookies(urlConnection);
		}
		count = 0;
		return sb.toString();
	}
	public static InputStream getIsFromUrl(String urlStr){
		try{
			URL url = new URL(urlStr);
			HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
			if( !Global.strCookies.equals("")){
				urlConnection.setRequestProperty("Cookie", Global.strCookies);
			}
			urlConnection.setConnectTimeout(8000);
			urlConnection.setRequestMethod("GET");
			urlConnection.setReadTimeout(8000);
			urlConnection.setDoInput(true);
			if(Global.strCookies.equals("")){
				setCookies(urlConnection);
			}
			return urlConnection.getInputStream();
		}catch(Exception ex){
			return null;
		}
	}
	private static void setCookies(HttpURLConnection urlConnection) {
		// TODO Auto-generated method stub
		StringBuilder sBuilder = new StringBuilder();
		Map<String, List<String>> map = urlConnection.getHeaderFields();
		List<String> list = (List<String>) map.get("Set-Cookie");
		 if(list.size() == 0||list == null)
		 {
			 return;
		 }
		 int len = list.size();
		 for(int i = 0; i < len - 1; i++ ){
			 String[] strs = list.get(i).split(";");
			 sBuilder.append(strs[0]+"; ");
		 }
		 String[] strs1 = list.get(len - 1).split(";");
		 sBuilder.append(strs1[0]);
		 Global.strCookies = sBuilder.toString();
	}
	
}
