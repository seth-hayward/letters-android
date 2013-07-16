package com.returnondevelopment.letters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class SendFragment extends Fragment {
	
	Button button_Send;
	
	public SendFragment() {
		
		
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    	
    	View sendView = inflater.inflate(R.layout.fragment_send, container, false);

		button_Send = (Button)sendView.findViewById(R.id.btn_send);
		 
		button_Send.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {

				// read the response from the server
				// display the letter page... 
 
			}
 
		});
 
    	
    	
    	return sendView;
    }    
		 
}
