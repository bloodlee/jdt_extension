package org.yli.eclipse.jdt.extension.handlers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
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

public class SearchWithGoogleHandlerInTerminal extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
	    // get workbench window
	    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
	    // set selection service
	    ISelectionService service = window.getSelectionService();
	    // set structured selection
	    IStructuredSelection structured = (IStructuredSelection) service.getSelection();
	    
	    if (structured instanceof StructuredSelection) {
	    	StructuredSelection ss = (StructuredSelection) structured;
	    	
	    	CTabItem tabItem = (CTabItem) ss.getFirstElement();
	    	
	    	Control control = tabItem.getControl();
	    	
	    	if (control instanceof Composite) {
	    		Composite parent = (Composite) control;
	    		
	    		if (parent.getChildren()[0] instanceof Composite) {
	    			Composite subParent = (Composite) parent.getChildren()[0];
	    			
	    			Control textCanvas = subParent.getChildren()[0];
	    			
	    			try {
						Method method = textCanvas.getClass().getMethod("getSelectionText");
						
						String selection = (String) method.invoke(textCanvas);
						
						if (selection != null && !selection.isEmpty()) {
							MiscUtil.openBrowser(GoogleSearch.toUrl(selection));
						}
						
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		}
	    	}
	    }
	    
		return null;
	}

 

}
