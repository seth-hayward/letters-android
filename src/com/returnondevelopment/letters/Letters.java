package com.returnondevelopment.letters;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

public class Letters extends FragmentActivity implements ActionBar.TabListener {

	private Menu _menu;
	MyAdapter mAdapter;
	ViewPager mPager;
	int selected_id = R.id.action_home;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_letters);

		mAdapter = new MyAdapter(getSupportFragmentManager());		
		mPager = (ViewPager)findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
				
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		
	}

	public String getPageTitle(int position) {
		int page = position + 1;		
		return "Page " + page;
	}			
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.letters, menu);
		_menu = menu;
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	
	@Override
	public boolean onPrepareOptionsMenu (Menu menu) {
		super.onPrepareOptionsMenu(menu);
	
		// could change menu items here if i desired, 
		// but since i can't change the icon, not going
		// to go this route
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
				
		View rootView = mPager.getRootView();		
		WebView dummyWebView = (WebView) rootView.findViewById(R.id.section_webView);
				
	    switch (item.getItemId()) {
	        case R.id.action_home:	        	
	        	dummyWebView.loadUrl("http://www.letterstocrushes.com/mobile/page/1");	        		        	
	            return true;
	        case R.id.action_more:
	        	dummyWebView.loadUrl("http://www.letterstocrushes.com/mobile/more/page/1");	        		        	
	            return true;
	        case R.id.action_bookmarks:
	        	dummyWebView.loadUrl("http://www.letterstocrushes.com/mobile/bookmarks");  	
	            return true;
	        case R.id.action_search:
	        	dummyWebView.loadUrl("http://www.letterstocrushes.com/mobile/search");  	
	            return true;	            
	        case R.id.action_send:
	        	// switch to the send screen...
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);	            
	            	            
	    }
	}	
		
}
