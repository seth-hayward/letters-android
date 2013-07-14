package com.returnondevelopment.letters;

import java.util.Locale;

import android.os.Bundle;
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
public class DummySectionFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";

	public DummySectionFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_letters_dummy,
				container, false);
		
		WebView dummyWebView = (WebView) rootView.findViewById(R.id.section_webView);
		
		String base_url = "http://www.letterstocrushes.com/mobile/";
		String extension = "";
		
		switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
		case 1:
			// home
			extension = "";
			break;
		case 2:
			// more
			extension = "more";
			break;
		case 3:
			extension = "bookmarks";
			break;
		case 4:
			extension = "search";
			break;
		case 5:
			extension = "more";
			break;
		
		}
		
		extension = "page/" + getArguments().getInt(ARG_SECTION_NUMBER);
		
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
            return (false);
        }

    }		
	
}
	

