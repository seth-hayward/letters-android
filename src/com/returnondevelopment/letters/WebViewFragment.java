package com.returnondevelopment.letters;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import com.returnondevelopment.letters.SendFragment.OnLetterSentListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.support.v4.app.Fragment;

/**
 * A dummy fragment representing a section of the app, but that simply
 * displays dummy text.
 */

public class WebViewFragment extends Fragment
{
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";
	public static String current_page = "";
	public static int current_page_number;

	public OnLetterSentListener mCallback;
	
	
	public WebViewFragment(String page, int page_number) {
		current_page = page;
		current_page_number = page_number;
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
		View rootView = inflater.inflate(R.layout.fragment_letters_dummy,
				container, false);
		
		WebView dummyWebView = (WebView) rootView.findViewById(R.id.section_webView);
		
		String base_url = "http://www.letterstocrushes.com/mobile/";
		
		base_url = base_url + current_page;

		String extension = "";
		
		if(current_page == "more/" || current_page == "") {
			extension = "page/" + Integer.toString(current_page_number);						
		}
						
		Log.d("Extension", base_url + extension);
		
		dummyWebView.getSettings().setJavaScriptEnabled(true);

		WebSettings dummySettings = dummyWebView.getSettings();

		dummySettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
		
		Callback cb = new Callback(this);
		cb.callback = new WebViewCallback() {

			@Override
			public void callbackCall(String id) {
				// TODO Auto-generated method stub
				Log.d("status", "callbackCall fired with id= " + id);
				mCallback.onLetterPreEdit(new String(id));
			}
			
		};
						
		dummyWebView.setWebViewClient(cb); 
		dummyWebView.loadUrl(base_url + extension);
					
		return rootView;
	}
	
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return getString(R.string.title_home).toUpperCase(l);
		case 1:
			return getString(R.string.title_more).toUpperCase(l);
		case 2:
			return getString(R.string.title_bookmarks).toUpperCase(l);
		case 3:
			return getString(R.string.title_search).toUpperCase(l);
		case 4:
			return getString(R.string.title_send).toUpperCase(l);
			
		}
		return null;
	}		
			

    private class Callback extends WebViewClient { 
    	public WebViewCallback callback;
    	    	
    	WebViewFragment _fragment;
    	public Callback(WebViewFragment frag) {
    		_fragment = frag;    		
    	}
    	
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    	
        	if(url.contains("/edit") == true) {
        		
        		// extract the letter id
        		String id = url.replace("http://www.letterstocrushes.com/edit/", "");
        		
       		Log.d("status", "id, before _fragment.mcallback: " + id);
        		callback.callbackCall(id);
        		
//        		_fragment.mCallback.onLetterPreEdit(id);
        		
        		return true;
        	} else {
                return (false);        		        		
        	}
        	        	
        }

    }

}
	

