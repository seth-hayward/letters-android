package com.returnondevelopment.letters;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

public class MyAdapter extends FragmentStatePagerAdapter {
	
	static final int ITEMS = 100;

	public MyAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
	}
	
	@Override
	public int getCount() {
		return ITEMS;			
	}
	
	@Override
	public Fragment getItem(int position) {
						
		Fragment fragment = new DummySectionFragment("", position);
		Bundle args = new Bundle();
		
		//
		// hok, can i somehow get the action bar from here?
		//
					
		//args.putInt(DummySectionFragment.current_page, position + 1);
		//fragment.setArguments(args);									
		return fragment;
	}
}

