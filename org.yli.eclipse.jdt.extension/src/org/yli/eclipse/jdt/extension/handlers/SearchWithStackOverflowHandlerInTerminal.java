package org.yli.eclipse.jdt.extension.handlers;

import org.yli.eclipse.jdt.extension.util.SearchEngine;
import org.yli.eclipse.jdt.extension.util.UrlGenerator;

public class SearchWithStackOverflowHandlerInTerminal extends SearchBaseHandlerInTerminal {

	public SearchWithStackOverflowHandlerInTerminal() {
		super(new UrlGenerator() {

			@Override
			public String gen(String keyword) {
				return SearchEngine.toStackOverflowUrl(keyword);
			}
			
		});
	}

}
