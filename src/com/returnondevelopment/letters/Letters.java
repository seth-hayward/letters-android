package com.returnondevelopment.letters;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class Letters extends FragmentActivity implements ActionBar.TabListener, OnNavigationListener {

	private MenuItem mSpinnerItem1 = null;	
	private Menu _menu;
	private SpinnerAdapter mSpinnerAdapter;
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
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		String colors[] = {"Red","Blue","White","Yellow"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colors);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view		

		
		// Specify a SpinnerAdapter to populate the dropdown list.
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(actionBar.getThemedContext(),
	        android.R.layout.simple_spinner_item, R.id.planets_spinner,
	        colors);

	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

	    // Set up the dropdown list navigation in the action bar.
	    actionBar.setListNavigationCallbacks(adapter, this);
		
	}

	public String getPageTitle(int position) {
		int page = position + 1;		
		return "Page " + page;
	}			
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.letters, menu);
		
		//Spinner s = (Spinner) menu.findItem(R.id.planets_spinner).getActionView(); // find the spinner
		//mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.action_list, android.R.layout.simple_spinner_dropdown_item); //  create the adapter from a StringArray
		//s.setAdapter(mSpinnerAdapter); // set the adapter
		//s.setOnItemSelectedListener(myChangeListener); // (optional) reference to a OnItemSelectedListener, that you can use to perform actions based on user selec

        
        
//		   mSpinnerItem1 = menu.findItem( R.id.planets_spinner);
//		    View view1 = mSpinnerItem1.getActionView();
//		    if (view1 instanceof Spinner)
//		    {
//		        final Spinner spinner = (Spinner) view1;
//		        

//		        spinner.setAdapter(spinnerArrayAdapter);
//		        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//		            @Override
//		            public void onItemSelected(AdapterView<?> arg0, View arg1,
//		                    int arg2, long arg3) {
//		                // TODO Auto-generated method stub
//
//		            }
//
//		            @Override
//		            public void onNothingSelected(AdapterView<?> arg0) {
//		                // TODO Auto-generated method stub
//
//		            }
//		        });
//
//		    }
		
		
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

	@Override
	public boolean onNavigationItemSelected(int arg0, long arg1) {
		// TODO Auto-generated method stub
		return false;
	}	
		
}
