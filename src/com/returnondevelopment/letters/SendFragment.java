package com.returnondevelopment.letters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.returnondevelopment.letters.HeadlinesFragment.OnHeadlineSelectedListener;

import android.R.bool;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
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
		public void onLetterSend(int id);	
	}
	
	Button button_Send;
	EditText text_Letter;
	
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
		text_Letter = (EditText)sendView.findViewById(R.id.text_Letter);
		button_Send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				DownloadWebPageTask task = new DownloadWebPageTask();
				ServerMessage msg = new ServerMessage();
				try {
					msg = task.execute(new String[] { text_Letter.getText().toString() }).get();
					
					if(msg.l_response == 1) {
						mCallback.onLetterSend(msg.l_message);						
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
