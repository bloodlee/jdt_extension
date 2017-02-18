package org.yli.eclipse.jdt.extension.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SearchEngine {

	private static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";
	
	private static final String STACKOVERFLOW_SEARCH_URL = "http://stackoverflow.com/search";
	
	public static String toGoogleUrl(String keyword) {
		return doToUrl(GOOGLE_SEARCH_URL, keyword);
	}
	
	public static String toStackOverflowUrl(String keyword) {
		return doToUrl(STACKOVERFLOW_SEARCH_URL, keyword);
	}

	private static String doToUrl(String baseUrl, String keyword) {
		String searchURL = "";
		try {
			searchURL = baseUrl + "?q="+ URLEncoder.encode(keyword, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		 return searchURL;
	}
	
}
