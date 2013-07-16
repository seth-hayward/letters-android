package com.returnondevelopment.letters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SendFragment extends Fragment {
		
	public SendFragment() {
		
		
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    	
    	View sendView = inflater.inflate(R.layout.fragment_send, container, false);
    	
    	return sendView;
    }

}
