package com.example.demo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Yupeng Hua on 1/29/2015.
 */

public class ClubLoading extends Fragment {

    private static final String TAG = "ClubLoading";

    private Thread thread;
    private Handler mHandler = new Handler();
    Bundle bundle = null;
    Boolean stop = false;
    OnLoadingListener mCallback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (OnLoadingListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        thread = new Thread(new Runnable() {
            String json = null;
            String[] title, desc, img = null;

            @Override
            public void run() {
                Log.d(TAG, "In the new thread");
                if (stop) return;
                JsonParser jsonParser = new JsonParser();
//              String json = jsonParser.getJSONFromUrl("http://api.androidhive.info/game/game_stats.json");
//              json = jsonParser.getJSONFromUrl("http://10.205.144.248:8080/club/partake/list.do?act=history");
                json = jsonParser.getJSONFromUrl("http://www.eng.uwaterloo.ca/~y2hua/club.json");

//              Log.e("Response: ", "> " + json);

                if (json != null) {
                    try {
             /*         JSONObject jObj = new JSONObject(json).getJSONObject("game_stat");
             //         now_playing = jObj.getString("now_playing");
                        earned = jObj.getString("earned");
 				*/
                    //      Log.e("JSON", "> " + now_playing + earned);
                        JSONObject jObj = new JSONObject(json);
                        JSONArray jsonArray = jObj.getJSONArray("ContactList");
                        int length = jsonArray.length();
                        title = new String[length];
                        desc = new String[length];
                        img = new String[length];

                        for(int i = 0; i < length; i++){
                            JSONObject item = jsonArray.getJSONObject(i);
                            title[i] = item.getString("company");
                            img[i] = item.getString("link");
                            desc[i] = item.getString("registered");
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    bundle = new Bundle();
                    bundle.putStringArray("title", title);
                    bundle.putStringArray("desc", desc);
                    bundle.putStringArray("img", img);
//                    bundle.putString("json", json);

                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            replace(bundle);
                        }
                    }, 2000);
                }
            }
        });
        thread.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.loading, container, false);
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
        mCallback.onLoadingFinished(2, bundle);
        Log.d(TAG, "replace is called");
    }
}
