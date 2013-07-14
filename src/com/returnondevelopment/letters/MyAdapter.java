package com.returnondevelopment.letters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MyAdapter extends FragmentStatePagerAdapter {
	
	static final int ITEMS = 3;

	public MyAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
	}
	
	@Override
	public int getCount() {
		return ITEMS;			
	}
	
	@Override
	public Fragment getItem(int position) {
		Fragment fragment = new DummySectionFragment();
		Bundle args = new Bundle();
		args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
		fragment.setArguments(args);									
		return fragment;
	}
					
}

