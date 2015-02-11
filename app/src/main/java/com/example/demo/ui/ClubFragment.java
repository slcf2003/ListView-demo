package com.example.demo.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.R;
import com.example.demo.util.ImageCache;
import com.example.demo.util.ImageWorker;

/**
 * Created by Yupeng Hua on 1/29/2015.
 */

public class ClubFragment extends ListFragment implements OnScrollListener{

    private static final String TAG = "ClubFragment";


    private Bundle bundle;
    private View mFooterView;

    private ImageWorker mImageWorker;
    private MyAdapter mAdapter;


    List<HashMap<String, String>> data;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
//        data = getData();
    }
/*
    public static ActivityFragment newInstance(Bundle bundle){
        ActivityFragment activity = new ActivityFragment();
        this.bundle = bundle;

        return activity;
    }
*/
//	private String[] title = {"�ൺơ��","���ؼ�","�����ơ��","Gin","Ice wine","��ʿ��","����Һ","ę́","��ơ"};
//	private String[] desc = {"yummy 1","yummy 2","yummy 3","yummy 4","yummy 5","yummy 6","yummy 7","yummy 8","yummy 9"};

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
        data = getData(bundle);
        Toast.makeText(getActivity(), "size of data is " + data.size(), Toast.LENGTH_SHORT).show();

        mAdapter = new MyAdapter(getActivity(), data);
        mImageWorker = new ImageWorker(getActivity());

        ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams("club_thumb");
        mImageWorker.setImageCache(ImageCache.findOrCreateCache(getActivity(), cacheParams));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        container.removeAllViewsInLayout();
        View v = inflater.inflate(R.layout.main_club, container, false);
        Log.d(TAG, "OnCreateView is called" );
//		Toast.makeText(getActivity(), "OnCreateView in ActivityFragment", Toast.LENGTH_SHORT).show();

        setListAdapter(mAdapter);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFooterView = LayoutInflater.from(getActivity()).inflate(R.layout.load_more, null);

        getListView().addFooterView(mFooterView, null, false);
        getListView().setOnScrollListener(this);
    }

    private List<HashMap<String, String>> getData(Bundle bundle){
        String[] title, desc, img;
        List<HashMap<String, String>> mHashmaps = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;

        title = bundle.getStringArray("title");
        desc = bundle.getStringArray("desc");
        img = bundle.getStringArray("img");

        for(int i = 0; i < title.length; i++){
            map = new HashMap<String, String>();
            map.put("title", title[i]);
            map.put("desc", desc[i]);
            map.put("img", img[i]);
            mHashmaps.add(map);
        }
        return mHashmaps;
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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        Log.v(TAG,"Click");
        Toast.makeText(getActivity(), "You have selected " + position, Toast.LENGTH_SHORT).show();
    }

    /*
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        Log.v(TAG,"Click");
        Toast.makeText(getActivity(), "You have selected " + position, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent();
        intent.setClass(getActivity(), Club.class);
        startActivity(intent);
    }
    */
    private class MyAdapter extends BaseAdapter {

        LayoutInflater inflater;
        Context context;
        List<HashMap<String, String>> data;

        MyAdapter(Context context, List<HashMap<String, String>> data){
            this.context = context;
            this.data = data;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            Log.d(TAG, "getCount() is called");
        //    return (data.size()<10)?data.size():10;
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            inflater = LayoutInflater.from(context);
            ViewHolder holder;

            if(convertView == null){
                holder = new ViewHolder();

                convertView = inflater.inflate(R.layout.club_list_layout, parent, false);
                holder.img = (ImageView)convertView.findViewById(R.id.thumbnail);
                holder.title = (TextView)convertView.findViewById(R.id.title);
                holder.desc = (TextView)convertView.findViewById(R.id.desc);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }

            holder.title.setText(data.get(position).get("title"));
            holder.desc.setText(data.get(position).get("desc"));
            //		holder.img.setImageResource((Integer) data.get(position).get("img"));
//            Log.d(TAG,data.get(position).get("img"));
            mImageWorker.loadBitmap(data.get(position).get("img"), holder.img);

            convertView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Log.v(TAG,"Click");
                    Toast.makeText(getActivity(), "You have selected " + position, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), ClubDetail.class);
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }

    public final class ViewHolder{
        ImageView img;
        TextView title;
        TextView desc;
    }

}