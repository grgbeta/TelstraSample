package com.example.telstrasample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import com.example.telstrasample.data.JsonResponse;
import com.google.gson.Gson;

import android.content.Context;

public class JsonDataLoader extends Thread {
	private Context context = null;
	private OnJsonDataResponse onJsonDataResponse = null;
	
	private JsonResponse jsonResponse = null;
	Gson gson = null;
	
	public JsonDataLoader(Context context,OnJsonDataResponse onJsonResponse) {
		this.context = context;
		this.onJsonDataResponse = onJsonResponse;
	}
	
	private String readUrl(String urlString) {
	    BufferedReader reader = null;
	    try {
	        URL url = new URL(urlString);
	        reader = new BufferedReader(new InputStreamReader(url.openStream()));
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1) {
	            buffer.append(chars, 0, read); 
	        System.out.println(new String(chars)); }

	        return buffer.toString();
	    } catch(Exception e) {
	    	return null;
	    } finally {
	    	try {
	        if (reader != null)
	            reader.close();
	    	} catch(Exception e) {}
	    }
	}
	
	public void loadJsonData() {
		start();
	}
	
	public void run() {
		gson = new Gson();
		String data = readUrl(Global.jsonUrl);
		
		if (data == null) {
			onJsonDataResponse.onError();
			return;
		} else {
		
			jsonResponse = gson.fromJson(data, JsonResponse.class);
			onJsonDataResponse.onJsonDataReceived(jsonResponse);
		}
	}
}
