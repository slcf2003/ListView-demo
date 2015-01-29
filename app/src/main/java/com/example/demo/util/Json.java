package com.example.demo.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Json {
	private String jsonStr;
	
	Json(String jsonStr){
		this.jsonStr = jsonStr;
	}
	
	public void getJsonArray(String jsonStr) throws Exception{
		JSONArray jsonArray = new JSONArray(jsonStr);
		ArrayList<String> tit = new ArrayList<String>(10);
		ArrayList<String> de = new ArrayList<String>(10);
		
		
		for(int i = 0; i < jsonArray.length(); i++){
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			String title = jsonObj.getString("title");
			String desc = jsonObj.getString("desc");
			tit.add(title);
			de.add(desc);
		}
	}
}
