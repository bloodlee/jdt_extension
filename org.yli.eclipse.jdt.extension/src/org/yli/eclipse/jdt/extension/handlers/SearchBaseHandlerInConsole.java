package org.yli.eclipse.jdt.extension.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.yli.eclipse.jdt.extension.util.MiscUtil;
import org.yli.eclipse.jdt.extension.util.UrlGenerator;

public class SearchBaseHandlerInConsole extends AbstractHandler {

	private final UrlGenerator generator;
	
	public SearchBaseHandlerInConsole(UrlGenerator generator) {
		if (generator == null) {
			throw new IllegalArgumentException("Generator cound't be null.");
		}
		this.generator = generator;
	}
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
	    // get workbench window
	    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
	    // set selection service
	    ISelectionService service = window.getSelectionService();
	    // set structured selection
	    ITextSelection structured = (ITextSelection) service.getSelection();
	    
	    if (structured instanceof TextSelection) {
	    	TextSelection ss = (TextSelection) structured;
	    	
	    	String selection = ss.getText();
	    	if (selection != null && !selection.isEmpty()) {
				MiscUtil.openBrowser(generator.gen(selection));
			}
	    }
	    
		return null;
	}

 

}
