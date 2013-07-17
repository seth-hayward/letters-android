package com.returnondevelopment.letters;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.analytics.tracking.android.EasyTracker;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ActionBar.OnNavigationListener;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ArrayAdapter;

public class Letters extends FragmentActivity implements ActionBar.TabListener, OnNavigationListener,
	SendFragment.OnLetterSentListener {
	
	MyAdapter mAdapter;
	ViewPager mPager;
	int selected_id = 0;
	String current_page = "";
	int current_page_number = 1;
	private DefaultHttpClient httpClient;
	public static Cookie cookie = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_letters);
		
		mAdapter = new MyAdapter(getSupportFragmentManager(), current_page, "");		
		
		mPager = (ViewPager)findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
				
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);    
	    	    
	    ArrayAdapter<CharSequence> dropDownAdapter = ArrayAdapter.createFromResource(this, R.array.action_list, android.R.layout.simple_list_item_1);
	    
		actionBar.setListNavigationCallbacks(dropDownAdapter, 
				new OnNavigationListener() {

					@Override
					public boolean onNavigationItemSelected(int spinner_position, long arg1) {
												
						
						// RECREATE
						// THE 
						// FRAGMENT

					    switch (spinner_position) {
				        case 0:	        	
				        	current_page = "";
				        	break;
				        case 1:
				        	current_page = "more/";					        	
				        	break;
				        case 2:
				        	current_page = "bookmarks";					        	
				        	break;
				        case 3:
				        	current_page = "search";
				        	break;
				        case 4:
				        	// switch to the send screen...
				        	current_page = "send";
				        	break;
				    }
				    
					    						
						mAdapter = new MyAdapter(getSupportFragmentManager(), current_page, "");		
						mPager = (ViewPager)findViewById(R.id.pager);
						mPager.setAdapter(mAdapter);
						
						mPager.setCurrentItem(1);
																																
					    Log.d("CurrentPage", current_page + " - " + Integer.toString(spinner_position) + " - /" + mAdapter.getCount());

					    return true;
												
					}
			
				
			});
			    
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.letters, menu);		
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
	public boolean onNavigationItemSelected(int arg0, long arg1) {
		return false;
	}

	@Override
	public void onLetterSend(int id, String guid) {
				
		if(id > 1) {

    		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
    		alertDialog.setTitle("Send successful");
    		alertDialog.setMessage("Your letter was sent.");

    		alertDialog.setButton("Yay", new DialogInterface.OnClickListener() {
    		      public void onClick(DialogInterface dialog, int which) {
    		 
    		       //here you can add functions
    		 
    		    } });
    		
    		alertDialog.show();
						
			httpClient = new DefaultHttpClient();
		    List<Cookie> cookies = httpClient.getCookieStore().getCookies();
		    for (int i = 0; i < cookies.size(); i++) {
		        cookie = cookies.get(i);
		    }		
			
			//------- Web Browser activity
			CookieSyncManager.createInstance(this);
			CookieManager cookieManager = CookieManager.getInstance();
			
		    cookieManager.setCookie("www.letterstocrushes.com", guid + "=1; domain=www.letterstocrushes.com" );
		    CookieSyncManager.getInstance().sync();
			
			
			mAdapter = new MyAdapter(getSupportFragmentManager(), "letter/" + Integer.toString(id), "");		
			mPager = (ViewPager)findViewById(R.id.pager);
			mPager.setAdapter(mAdapter);
			
			mPager.setCurrentItem(1);
					
		}
		
	}
	
	@Override
	public void onLetterEdit(String letter_message) {

		mAdapter = new MyAdapter(getSupportFragmentManager(), "edit", letter_message);		
		mPager = (ViewPager)findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		
		mPager.setCurrentItem(1);		
		
	}

	  @Override
	  public void onStart() {
	    super.onStart();
	    EasyTracker.getInstance().activityStart(this); // Add this method.	    
	  }

	  @Override
	  public void onStop() {
	    super.onStop();
	    EasyTracker.getInstance().activityStop(this); // Add this method.
	  }

	@Override
	public void onLetterPreEdit(String id) {
		
		// now download the letter info        		
		DownloadLetterTask task = new DownloadLetterTask();
		ServerMessage msg = new ServerMessage();
		try {
			msg = task.execute(new String[] { id }).get();					
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			msg.l_response = -1;
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg.l_response = -1;					
		}		    
		        		
		// and show the send view
		

		if(msg.l_response == 1) {
			
			// now, show send screen with this stuff...
			Log.d("status", "getLetter: " + msg.l_letter_text );					
		}					
		
	}
	
}
