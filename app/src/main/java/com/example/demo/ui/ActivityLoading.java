package com.example.demo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.demo.R;

import java.util.logging.LogRecord;

/**
 * Created by yhua on 1/27/2015.
 */
public class ActivityLoading extends Fragment {
    private Thread thread;
    private Handler mHandler = new Handler();
    Bundle bundle = null;
    ProgressBar progressBar;
    Boolean stop = false;
    long time;
    OnLoadingListener mCallback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (OnLoadingListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        time = System.currentTimeMillis() + 5 * 1000;

        thread = new Thread(new Runnable() {
            String json = null;
            String[] title, desc = null;

            @Override
            public void run() {
                Log.d("ActivityLoading", "In the new thread");
                if (stop) return;
                JsonParser jsonParser = new JsonParser();
                //           String json = jsonParser.getJSONFromUrl("http://api.androidhive.info/game/game_stats.json");
//                json = jsonParser.getJSONFromUrl("http://10.205.144.248:8080/club/partake/list.do?act=history");
                json = jsonParser.getJSONFromUrl("http://www.eng.uwaterloo.ca/~y2hua/json.json");

//              Log.e("Response: ", "> " + json);

                if (json != null) {
                    try {
             /*         JSONObject jObj = new JSONObject(json).getJSONObject("game_stat");
             //         now_playing = jObj.getString("now_playing");
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

                    bundle = new Bundle();
             //     bundle.putString("earned", earned);
                    bundle.putStringArray("title", title);
                    bundle.putStringArray("desc", desc);
                    bundle.putString("json", json);
                    while(System.currentTimeMillis() < time){}

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            replace(bundle);
                        }
                    });
                }
//                return null;
            }
        });
        thread.start();
//        new FetchJson(getActivity()).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.loading_activity, container, false);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        super.onStop();
        stop = true;
        thread.interrupt();
    }

    public void replace(Bundle bundle){
        mCallback.onFinished(1, bundle);
//        ((OnLoadingListener) getActivity()).onFinished(1, bundle);
        Log.d("ActivityLoading", "replace is called");
    }

}

/*
public class FetchJson extends AsyncTask<Void, Void, Void> {
    String json = null;
    String[] title, desc = null;
    ActivityFragment activity;

    public FetchJson(ActivityFragment activity){
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... params) {
        // TODO Auto-generated method stub
        JsonParser jsonParser = new JsonParser();
        //           String json = jsonParser.getJSONFromUrl("http://api.androidhive.info/game/game_stats.json");
        json = jsonParser.getJSONFromUrl("http://10.205.144.248:8080/club/partake/list.do?act=history");

//        Log.e("Response: ", "> " + json);

        if (json != null) {
            try {
             /*       JSONObject jObj = new JSONObject(json)
                            .getJSONObject("game_stat");
             //       now_playing = jObj.getString("now_playing");
                    earned = jObj.getString("earned");

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


//        intent.setClass(SplashActivity.this, TabHostActivity.class);
//        intent.putExtras(bundle);

//        startActivity(intent);
//        SplashActivity.this.finish();
    }
}

*/