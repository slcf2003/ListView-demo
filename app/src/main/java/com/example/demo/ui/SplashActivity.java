package com.example.demo.ui;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.demo.R;

public class SplashActivity extends Activity {

	public String earned;
	private String[] title;
	private String[] desc;
	String json;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
				, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        long num = System.currentTimeMillis() + 5*1000;
        while (System.currentTimeMillis() <= num){
        }

        Intent intent = new Intent();
        intent.setClass(this, TabHostActivity.class);
        startActivity(intent);
        SplashActivity.this.finish();
/*		
		new Handler().postDelayed(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SplashActivity.this, TabHostActivity.class);
				startActivity(intent);
				SplashActivity.this.finish();
			}
			
		}, 3000);
*/		
//		new FetchJson().execute();
	}
	
	private class FetchJson extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
            JsonParser jsonParser = new JsonParser();
 //           String json = jsonParser.getJSONFromUrl("http://api.androidhive.info/game/game_stats.json");
            json = jsonParser.getJSONFromUrl("http://10.205.144.248:8080/club/partake/list.do?act=history");
 
            Log.e("Response: ", "> " + json);
 
            if (json != null) {
                try {
             /*       JSONObject jObj = new JSONObject(json)
                            .getJSONObject("game_stat");
             //       now_playing = jObj.getString("now_playing");
                    earned = jObj.getString("earned");
 				*/
              //      Log.e("JSON", "> " + now_playing + earned);
                	JSONObject jObj = new JSONObject(json);
                	JSONArray jsonArray = jObj.getJSONArray("historyActivityList");
                	int length = jsonArray.length();
                	title = new String[length];
                	desc = new String[length];
                	
                	for(int i = 0; i < length; i++){
                		JSONObject item = jsonArray.getJSONObject(i);
                		String name = item.getString("activityName");
                		String loc = item.getString("address");
                		title[i] = name;
                		desc[i] = loc;
                	}
                    	
 
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
 
            }
 
            return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
	//		bundle.putString("earned", earned);
			bundle.putStringArray("title", title);
			bundle.putStringArray("desc", desc);
			bundle.putString("json", json);
			intent.setClass(SplashActivity.this, TabHostActivity.class);
			intent.putExtras(bundle);
			
			startActivity(intent);
			SplashActivity.this.finish();
		}
		
	}

}
