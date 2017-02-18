package org.yli.eclipse.jdt.extension.actions;

import org.eclipse.swt.widgets.Shell;
import org.yli.eclipse.jdt.extension.util.SearchEngine;
import org.yli.eclipse.jdt.extension.util.UrlGenerator;

public class SearchWithGoogleAction extends SearchBaseAction {
	
	public SearchWithGoogleAction() {
		super(new UrlGenerator() {

			@Override
			public String gen(String keyword) {
				return SearchEngine.toGoogleUrl(keyword);
			}
			
		});
	}
	
}
