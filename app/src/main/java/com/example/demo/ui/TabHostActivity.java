package com.example.demo.ui;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.example.demo.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class TabHostActivity extends FragmentActivity implements OnLoadingListener, TabHost.OnTabChangeListener{

    private static final String TAG = "TabHostActivity";

    private LayoutInflater layoutInflater;
	private FragmentTabHost mtabHost;
	Bundle mbundle;
    FragmentManager manager;
	
	//Tabѡ�������  
    private String mTabs[] = {"Activity", "Club", "Settings"};
//    private Class fragmentArray[] = {ActivityLoading.class,Club.class,Mine.class};
    private Class fragmentArray[] = {ActivityLoading.class,ClubLoading.class,Mine.class};
    private int mBtnArray[] = {R.drawable.tab_activity_btn,R.drawable.tab_club_btn,R.drawable.tab_mine_btn};

    @Override
    public void onLoadingFinished(int index, Bundle bundle) {
        FragmentTransaction fragmentTransaction;
        switch (index){
            case 1:
                mbundle = bundle;
                fragmentTransaction = manager.beginTransaction();
                ActivityFragment activity = new ActivityFragment();
                activity.setArguments(mbundle);
                fragmentTransaction.replace(R.id.realtabcontent, activity);
                fragmentTransaction.commitAllowingStateLoss();
                break;
            case 2:
                mbundle = bundle;
                fragmentTransaction = manager.beginTransaction();
                ClubFragment club = new ClubFragment();
                club.setArguments(mbundle);
                fragmentTransaction.replace(R.id.realtabcontent, club);
                fragmentTransaction.commitAllowingStateLoss();
                break;
            case 3:
                break;
            default:
                break;
        }
    }

    @Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		/*
		Club club = new Club();
		club.setArguments(bundle);
		*/
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setTitle("Club");
		forceShowOverflowMenu();

/*
		bundle = new Bundle();
		bundle = this.getIntent().getExtras();
		if(bundle == null){
			Log.d("Bundle", "bundle is null");
		} else{
			Log.d("Bundle", "bundle is not null");
		} 
*/
        manager = getSupportFragmentManager();
		initView();
	}
	
	private void initView(){
		layoutInflater = LayoutInflater.from(this);
		
		mtabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
		mtabHost.setup(this, manager, R.id.realtabcontent);
//		mtabHost.setup(this, manager);

		int count = fragmentArray.length;
		
//		Log.d("TabHost", "fragmentArray.length = "+ count);
		
		for(int i = 0; i < count; i++){
			TabSpec tabspec = mtabHost.newTabSpec(mTabs[i]).setIndicator(getTabItemView(i));
//			if(i == 0){
//				mtabHost.addTab(tabspec, fragmentArray[0], bundle);
//			}else{
				mtabHost.addTab(tabspec, fragmentArray[i], null);
//			}
			mtabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
		}
        mtabHost.setOnTabChangedListener(this);

	}
	
	private View getTabItemView(int index){
		View view = layoutInflater.inflate(R.layout.main_tab_item, null);
		
		ImageView imageView = (ImageView) view.findViewById(R.id.tabimage);
		imageView.setImageResource(mBtnArray[index]);
		
		TextView textView = (TextView) view.findViewById(R.id.textview);
		textView.setText(mTabs[index]);
		
		return view;
	}

    @Override
    public void onTabChanged(String tabId) {
        Log.d(TAG, "onTabChanged is called");

        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        switch (tabId){
            case "Activity":
                ActivityLoading activityLoading = new ActivityLoading();
                fragmentTransaction.replace(R.id.realtabcontent, activityLoading);
                fragmentTransaction.commit();
                break;
            case "Club":
                ClubLoading clubLoading = new ClubLoading();
                fragmentTransaction.replace(R.id.realtabcontent, clubLoading);
                fragmentTransaction.commit();
                break;
            case "Settings":
                break;
            default:
                break;
        }

    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch(item.getItemId()){
			case R.id.action_search:
				return true;
			case R.id.action_share:
				return true;
			case R.id.action_settings:
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
			
	//	if (id == R.id.action_settings) {
	//		return true;
	//	}
	//	return super.onOptionsItemSelected(item);
	}
	
	private void forceShowOverflowMenu() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onMenuOpened(int featureId, Menu menu){
		if(featureId == Window.FEATURE_ACTION_BAR && menu != null){
	        if(menu.getClass().getSimpleName().equals("MenuBuilder")){
	            try{
	                Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
	                m.setAccessible(true);
	                m.invoke(menu, true);
	            }
	            catch(NoSuchMethodException e){
	                Log.e("My Menu", "onMenuOpened", e);
	            }
	            catch(Exception e){
	                throw new RuntimeException(e);
	            }
	        }
	    }
	    return super.onMenuOpened(featureId, menu);
	}
}
