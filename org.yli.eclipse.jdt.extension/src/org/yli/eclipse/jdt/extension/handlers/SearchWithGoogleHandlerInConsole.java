package org.yli.eclipse.jdt.extension.handlers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.yli.eclipse.jdt.extension.util.GoogleSearch;
import org.yli.eclipse.jdt.extension.util.MiscUtil;

public class SearchWithGoogleHandlerInConsole extends AbstractHandler {

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
				MiscUtil.openBrowser(GoogleSearch.toUrl(selection));
			}
	    }
	    
		return null;
	}

 

}
