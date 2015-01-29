package com.example.demo.ui;

//import com.example.web_android.R;

import com.example.demo.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class ClubDetail extends Activity implements OnClickListener{
	
	private WebView webview;
	private Button button1, button2, button3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.club_detail_layout);
		getActionBar().setDisplayShowHomeEnabled(false); 
		setTitle(getString(R.string.club));
		
		initView();
	}
	
	public void initView(){
		setWebView();
		
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
	}
	
	public void setWebView(){
		webview = (WebView)this.findViewById(R.id.webview);
		WebSettings mWebSet = (WebSettings) webview.getSettings();
		mWebSet.setJavaScriptEnabled(true);
		mWebSet.setBuiltInZoomControls(true);
		webview.requestFocus();
		mWebSet.setSupportZoom(false);
		
		webview.loadUrl("file:///android_asset/club-detail.html");
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.button1:
				Log.v("Button","Click");
		        Toast.makeText(this, "Join̬", Toast.LENGTH_SHORT).show();
				break;
			case R.id.button2:
				Log.v("Button","Click");
		        Toast.makeText(this, "Details", Toast.LENGTH_SHORT).show();
				break;
			default:
				Log.v("Button","Click");
		        Toast.makeText(this, "Quit", Toast.LENGTH_SHORT).show();
				break;
		}	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.club, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
}
