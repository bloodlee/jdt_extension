package org.yli.eclipse.jdt.extension.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Action for making a note.
 * 
 * @author yli
 *
 */
public class MakeNoteAction implements IObjectActionDelegate {

	private Shell shell;
	
	@Override
	public void run(IAction action) {
		System.out.println("Going to make a note.");
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// do nothing.
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

}
