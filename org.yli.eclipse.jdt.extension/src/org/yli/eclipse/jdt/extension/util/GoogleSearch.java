package org.yli.eclipse.jdt.extension.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class GoogleSearch {

	public static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";
	
	public static String toUrl(String keyword) {
		String searchURL = "";
		try {
			searchURL = GOOGLE_SEARCH_URL + "?q="+ URLEncoder.encode(keyword, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		 return searchURL;
	}
	
}
