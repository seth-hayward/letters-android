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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class DownloadLetterTask extends AsyncTask<String, Void, ServerMessage> {

	@Override
	protected ServerMessage doInBackground(String... url) {

        ServerMessage msg = new ServerMessage();          
        String response = "";
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("http://www.letterstocrushes.com/home/getletter/" + url);            
			               
        try {
            HttpResponse execute = client.execute(httpget);
            InputStream content = execute.getEntity().getContent();

            BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
            String s = "";
            while ((s = buffer.readLine()) != null) {
              response += s;
            }
                          
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
        return msg;
		
			
	}

}
