package org.yli.eclipse.jdt.extension.handlers;

import java.util.Arrays;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.yli.eclipse.jdt.extension.dialogs.ClassSelectionDialog;
import org.yli.eclipse.jdt.extension.util.MiscUtil;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;

public class GenerateGuavaToStringHandler extends AbstractHandler {

  private static final String TO_STRING_FUNC = "toString";

  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    // get workbench window
    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
    // set selection service
    ISelectionService service = window.getSelectionService();
    // set structured selection
    IStructuredSelection structured = (IStructuredSelection) service.getSelection();

    ICompilationUnit cu = MiscUtil.getCompilationUnit(structured);

    if (cu != null) {

      IType[] allTypes = null;
      try {
        allTypes = cu.getAllTypes();
      } catch (JavaModelException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      ClassSelectionDialog dialog = new ClassSelectionDialog(Display.getDefault().getActiveShell(), allTypes);
      if (IDialogConstants.OK_ID == dialog.open()) {
        IType[] selectedTypes = dialog.getSelectedTypes();
        generateToStringFunction(selectedTypes, dialog.isOverwriteExistingFunction(), dialog.isBeforeGuava18());
      }
    }

    return null;
  }

  private void generateToStringFunction(IType[] allTypes, boolean b, boolean isBeforeGuava18) {
    if (allTypes != null) {
      for (IType type : allTypes) {
        try {
          generateFunction(type, b, isBeforeGuava18);
        } catch (JavaModelException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
  }

  private void generateFunction(IType type, boolean overwrite, boolean isBeforeGuava18) throws JavaModelException {
    if (type != null) {
      if (overwrite) {
        MiscUtil.deleteMethod(type, TO_STRING_FUNC);
      }

      IField[] fields = MiscUtil.getNonStaticMemberFields(type);

      String finalContent = createFunctionBody(type, fields, isBeforeGuava18);
      type.createMethod(finalContent, null, false, null);
    }
  }

  private String createFunctionBody(IType type, IField[] fields, boolean isBeforeGuava18) {
    String toStringContent = "\n" + "@Override\n" + "public String toString() {\n"
        + "  return %s.toStringHelper(%s.class)\n" + "%s" + "                .toString();\n" + "}\n";

    String elementContent = "";
    for (IField field : fields) {
      elementContent += String.format("                .add(\"%s\", %s)\n", field.getElementName(),
          field.getElementName());

    }

    String finalContent = String.format(toStringContent, isBeforeGuava18 ? "Objects" : "MoreObjects",
        type.getElementName(), elementContent);
    return finalContent;
  }

}
