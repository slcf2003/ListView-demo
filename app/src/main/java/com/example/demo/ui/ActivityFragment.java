package com.example.demo.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.R;

public class ActivityFragment extends ListFragment implements OnScrollListener{
	
	private static final String TAG = "ActivityFragment";
	
	private SimpleAdapter adapter;
	private List<HashMap<String, Object>> mHashMaps;
	private HashMap<String, Object> map;
	private Bundle bundle;
	private View mFooterView;
/*
    public static ActivityFragment newInstance(Bundle bundle){
        ActivityFragment activity = new ActivityFragment();
        this.bundle = bundle;

        return activity;
    }
*/
//	private String[] title = {"�ൺơ��","���ؼ�","�����ơ��","Gin","Ice wine","��ʿ��","����Һ","ę́","��ơ"};
//	private String[] desc = {"yummy 1","yummy 2","yummy 3","yummy 4","yummy 5","yummy 6","yummy 7","yummy 8","yummy 9"};
	
	private String[] title;
	private String[] desc;
/*
	@Override
	public void onAttach(android.app.ActivityFragment activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
        /*
		if(this.getArguments() == null){
			Log.d("Bundle", "getArguments() is null");
		} else{
			Log.d("Bundle", "getArguments() is not null");
		}
		*\/
		bundle = this.getArguments();
		title = bundle.getStringArray("title");
		desc = bundle.getStringArray("desc");
	}
*/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onCreate in Fragment1");
		super.onCreate(savedInstanceState);
//		adapter = new SimpleAdapter(getActivity(), getData(), R.layout.activity_list_layout, new String[] {"img", "title", "desc"}
//		, new int[] {R.id.thumbnail, R.id.title, R.id.desc});

        bundle = this.getArguments();
        title = bundle.getStringArray("title");
        desc = bundle.getStringArray("desc");

		adapter = new SimpleAdapter(getActivity(), getData(), R.layout.activity_list_layout, new String[] {"title", "desc"}
		, new int[] {R.id.title, R.id.desc});
		setListAdapter(adapter);

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.main_activity, container, false);
//		TextView text = (TextView)v.findViewById(R.id.json);
//		String str = bundle.getString("json");
		
//		text.setText(str);
        Log.d("ActivityFragment", "OnCreateView is called" );
//		Toast.makeText(getActivity(), "OnCreateView in ActivityFragment", Toast.LENGTH_SHORT).show();

		return v;
	}

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFooterView = LayoutInflater.from(getActivity()).inflate(R.layout.load_more, null);

        getListView().addFooterView(mFooterView, null, false);
        getListView().setOnScrollListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		
	}
	  
	
	private List<HashMap<String, Object>> getData() {
		mHashMaps = new ArrayList<HashMap<String, Object>>();
		
		for(int i = 0; i < title.length; i++){
			map = new HashMap<String, Object>();
			map.put("title", title[i]);
			map.put("desc", desc[i]);
			mHashMaps.add(map);
		}
		return mHashMaps;
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Log.v(TAG,"Click");
        Toast.makeText(getActivity(), "You have selected " + position, Toast.LENGTH_SHORT).show();  
	}

}
