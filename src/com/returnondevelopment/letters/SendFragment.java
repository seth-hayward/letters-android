package com.returnondevelopment.letters;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SendFragment extends Fragment {
	OnLetterSentListener mCallback;

	// Container activity must implement this interface
	public interface OnLetterSentListener {
		public void onLetterSend(int id, String l_guid);
		public void onLetterEdit(String letter_message);	
	}
	
	Button button_Send;
	EditText text_Letter;
	boolean _is_edit;
	String _letter_message;
	
	public SendFragment(boolean is_edit, String letter_message) {
		_is_edit = is_edit;
		_letter_message = letter_message;
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
		text_Letter = (EditText)sendView.findViewById(R.id.text_Letter);
		
		if(_is_edit) {
			text_Letter.setText(_letter_message);						
			button_Send.setText("Edit");
		} else {
			button_Send.setText("Send");			
		}
			
		button_Send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				DownloadWebPageTask task = new DownloadWebPageTask();
				ServerMessage msg = new ServerMessage();
				try {
					msg = task.execute(new String[] { text_Letter.getText().toString() }).get();
					
					if(msg.l_response == 1) {
						Log.d("status", "onClick: " + Integer.toString(msg.l_message));
						mCallback.onLetterSend(msg.l_message, msg.l_guid);						
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					msg.l_response = -1;
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					msg.l_response = -1;					
				}		    
				
				
				 
			}
 
		});
 
    	
    	
    	return sendView;
    }    

    
    
}
