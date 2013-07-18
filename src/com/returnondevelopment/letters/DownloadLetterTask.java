package com.returnondevelopment.letters;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class DownloadLetterTask extends AsyncTask<String, Void, ServerMessage> {

	@Override
	protected ServerMessage doInBackground(String... url) {

        ServerMessage msg = new ServerMessage();          
		
		for(String z_url : url) {
	        Log.d("status", "hello from downloadlettertask: " + z_url);

	        String response = "";
	        DefaultHttpClient client = new DefaultHttpClient();
	        HttpGet httpget = new HttpGet("http://www.letterstocrushes.com/home/getletter/" + z_url);            
	        
	        try {
	            HttpResponse execute = client.execute(httpget);
	            InputStream content = execute.getEntity().getContent();

	            BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
	            String s = "";
	            while ((s = buffer.readLine()) != null) {
	              response += s;
	            }
	            
	            Log.d("status", "response: " + response);
	                          
	            try {
	              JSONObject jsonObject = new JSONObject(response);
	              
	              String letter_message = jsonObject.getString("letterMessage");              
	              msg.l_letter_text  = letter_message;
	              msg.l_response = 1;
	              
	            } catch (Exception e) {
	              msg.l_error_message += e.getMessage().toString();
	              msg.l_response = 0;              
	            }

	          } catch (Exception e) {
	             msg.l_error_message += e.getMessage().toString();
	             msg.l_response = 0;                           
	          }
			
		}
        return msg;
		
			
	}

}
