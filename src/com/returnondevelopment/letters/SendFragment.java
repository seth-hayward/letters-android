package com.returnondevelopment.letters;

import com.returnondevelopment.letters.HeadlinesFragment.OnHeadlineSelectedListener;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class SendFragment extends Fragment {
	
	OnLetterSentListener mCallback;
	
	// Container activity must implement this interface
	public interface OnLetterSentListener {
		public void onLetterSend(int id);	
	}
	
	Button button_Send;
	
	public SendFragment() {
		
		
	}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnLetterSentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
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
				mCallback.onLetterSend(10000);
 
			}
 
		});
 
    	
    	
    	return sendView;
    }    
		 
}
