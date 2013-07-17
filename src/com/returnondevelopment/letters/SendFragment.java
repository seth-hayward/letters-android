package com.returnondevelopment.letters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
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

				// read the response from the server
				// display the letter page... 
			    // Create a new HttpClient and Post Header
			    HttpClient httpclient = new DefaultHttpClient();
			    HttpPost httppost = new HttpPost("http://ww.letterstocrushes.com/home/mail");
		    	AlertDialog errorDialog = new AlertDialog.Builder(getActivity()).create();
		    	AlertDialog successDialog = new AlertDialog.Builder(getActivity()).create();

		    	Log.d("status", "onClick: " + text_Letter.getText().toString());
			    
			    try {
			        // Add your data
			    	String letter_message = text_Letter.getText().toString();
			    	
			        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
			        nameValuePairs.add(new BasicNameValuePair("letterText", letter_message));
			        nameValuePairs.add(new BasicNameValuePair("letterCountry", "US"));
			        nameValuePairs.add(new BasicNameValuePair("mobile", "1"));
			        
			        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			        Log.d("status", "before execute");
			        
			        // Execute HTTP Post Request
			        HttpResponse response = httpclient.execute(httppost);

			        Log.d("status", "before reader");

			        
			        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			        StringBuilder builder = new StringBuilder();
			        for (String line = null; (line = reader.readLine()) != null;) {
			            builder.append(line).append("\n");
			        }
			        JSONTokener tokener = new JSONTokener(builder.toString());
			        JSONArray finalResult = new JSONArray(tokener);

			        // this is awful
			        int new_letter_id = 0;
			        boolean success = false;

			        Log.d("status", "before call");
			        
			        for(int i = 0; i < finalResult.length(); i++) {
			        	
			        	JSONObject row = finalResult.getJSONObject(i);
				        Log.d("status", "get object");
		        	
			        	int server_response = row.getInt("response");
			        	final String server_message = row.getString("message");
			        	String server_guid = row.getString("guid");
			        	
			        	if(server_response == 1) {
					        Log.d("status", "success");

			        		successDialog.setTitle("error");
			            	successDialog.setMessage(server_message);
			            	successDialog.setButton("Close", new DialogInterface.OnClickListener() {
			        	    	public void onClick(DialogInterface dialog, int which) {
			    					mCallback.onLetterSend(Integer.parseInt(server_message));			        	    		
			        	    	}
			            	});
			        		
			        	} else {
			        		
			            	errorDialog.setTitle("error");
			            	errorDialog.setMessage(server_message);
			            	errorDialog.setButton("Close", new DialogInterface.OnClickListener() {
			        	    	public void onClick(DialogInterface dialog, int which) {
			        	    	// here you can add functions
			        	    		Log.d("ok", "OK");
			        	    	}
			            	});
			            	errorDialog.show();
			            	
			        		
			        	}
			        	
			        	
			        }
			        			        				        			        
			    } catch (ClientProtocolException e) {
			        // TODO Auto-generated catch block			    	
			    } catch (IOException e) {
			        // TODO Auto-generated catch block
			    } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				 
			}
 
		});
 
    	
    	
    	return sendView;
    }    
		 
}
