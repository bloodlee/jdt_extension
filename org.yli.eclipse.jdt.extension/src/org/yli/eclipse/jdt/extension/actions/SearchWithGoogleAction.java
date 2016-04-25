package org.yli.eclipse.jdt.extension.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.yli.eclipse.jdt.extension.Activator;
import org.yli.eclipse.jdt.extension.util.GoogleSearch;
import org.yli.eclipse.jdt.extension.util.MiscUtil;

public class SearchWithGoogleAction implements IObjectActionDelegate {

	private Shell shell;
	
	@Override
	public void run(IAction action) {
		try {
			//get editor
			IEditorPart editorPart = Activator.getDefault().getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.getActiveEditor();
 
			if (editorPart instanceof AbstractTextEditor) {
				int offset = 0;
				int length = 0;
				String selectedText = null;
				IEditorSite iEditorSite = editorPart.getEditorSite();
				if (iEditorSite != null) {
					//get selection provider
					ISelectionProvider selectionProvider = iEditorSite
							.getSelectionProvider();
					if (selectionProvider != null) {
						ISelection iSelection = selectionProvider
								.getSelection();
						//offset
						ITextSelection textSelection = (ITextSelection) iSelection;
						offset = textSelection.getOffset();
						if (!textSelection.isEmpty()) {
							MiscUtil.openBrowser(GoogleSearch.toUrl(textSelection.getText()));
						} else {
							MessageDialog.openWarning(
							         shell,
							         "Warning",
							         "Nothing selected.");
						}
					}
				}
 
			}
		} catch (Exception e) {		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}


}
