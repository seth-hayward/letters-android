package com.returnondevelopment.letters;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadWebPageTask extends AsyncTask<String, Void, ServerMessage> {
    	
        protected ServerMessage doInBackground(String... urls) {
        	
          ServerMessage msg = new ServerMessage();          
          String response = "";
          int letter_id = -1;
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
                JSONObject jsonObject = new JSONObject(response);
                
                Log.d("status", jsonObject.getString("message"));
                int response_value = jsonObject.getInt("response");
                
                msg.l_response = response_value;
               
                if(response_value == 1) {
                    letter_id = jsonObject.getInt("message");
                    // show success view and navigate to letter
                    msg.l_message = letter_id;                    
                } else {
                	// show error alert view
                	msg.l_response = 0;
                	msg.l_error_message = jsonObject.getString("message");                	
                }
                
              } catch (Exception e) {
                msg.l_error_message += e.getMessage().toString();
              }

            } catch (Exception e) {
               msg.l_error_message += e.getMessage().toString();
            }
          }
          return msg;
        }

        @Override
        protected void onPostExecute(ServerMessage result) {
          //if(result.l_response == 1) {        	  
          //	  mCallback.onLetterSend(result.l_message);        	  
          //}
        }
      }

	
