package com.returnondevelopment.letters;

import java.util.Locale;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

	public WebViewFragment(String page, int page_number) {
		current_page = page;
		current_page_number = page_number;
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
		dummyWebView.setWebViewClient(new Callback()); 
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
			

    private class Callback extends WebViewClient{ 

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
        	
        	if(url.contains("/edit") == true) {
        		
        		// now download the letter info
        		
        		
        		
        		
        		
        		
        		
        		// and show the send view
        		        		
        		return true;
        	} else {
                return (false);        		        		
        	}
        	        	
        }

    }

}
	

