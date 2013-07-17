package com.returnondevelopment.letters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

public class MyAdapter extends FragmentStatePagerAdapter {
	
	static final int ITEMS = 100;
	static String current_page = "";
	static String current_letter_message;
	static int current_page_number = -1;
	

	public MyAdapter(FragmentManager fragmentManager, String page, String letter_message) {
		super(fragmentManager);
		current_page = page;
		current_page_number = -1;
		current_letter_message = letter_message;
	}
	
	@Override
	public int getCount() {
		return ITEMS;			
	}
	
	@Override
	public Fragment getItem(int position) {
		Log.d("getItem", Integer.toString(position));
		
		if(current_page_number == -1) {
			// first load
			current_page_number = 1;
		} else {
			current_page_number = position;			
		}
		Fragment fragment;
								
		if(current_page == "send") {
			fragment = new SendFragment(false, "");
		} else if(current_page == "edit") {
			fragment = new SendFragment(true, current_letter_message);
		} else {
			fragment = new WebViewFragment(current_page, current_page_number);					
		}
			
		return fragment;
	}
}

