package com.returnondevelopment.letters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
			    task.execute(new String[] { text_Letter.getText().toString() });		    
				
				
				 
			}
 
		});
 
    	
    	
    	return sendView;
    }    

    
    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
          String response = "";
          String letter_id = "-1";
          for (String url : urls) {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.letterstocrushes.com/home/mail");            

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
		    nameValuePairs.add(new BasicNameValuePair("letterText", url));
			nameValuePairs.add(new BasicNameValuePair("letterCountry", "US"));
			nameValuePairs.add(new BasicNameValuePair("mobile", "1"));
			     
			try {
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				response = e1.getMessage().toString();
			}            
            
            try {
              HttpResponse execute = client.execute(httppost);
              InputStream content = execute.getEntity().getContent();

              BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
              String s = "";
              while ((s = buffer.readLine()) != null) {
                response += s;
              }
                            
              try {
                JSONArray jsonArray = new JSONArray(response);
                Log.d("status",
                    "Number of entries " + jsonArray.length());
                
                response += "entries: " + jsonArray.length();
                for (int i = 0; i < jsonArray.length(); i++) {
                  JSONObject jsonObject = jsonArray.getJSONObject(i);
                  Log.d("status", jsonObject.getString("message"));
                  letter_id = jsonObject.getString("message");
                  response += " - message:" + letter_id;
                }
              } catch (Exception e) {
                response += e.getMessage().toString();
              }

            } catch (Exception e) {
				response += e.getMessage().toString();
            }
          }
          return response;
        }

        @Override
        protected void onPostExecute(String result) {
          text_Letter.setText(result);
        }
      }
    
    
}
