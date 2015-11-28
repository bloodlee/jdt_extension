package org.yli.eclipse.jdt.extension.handlers;

import java.util.Arrays;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
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

public class GenerateGuavaHashcodeAndEqualsHandler extends AbstractHandler implements IHandler {

  private static final String HASH_CODE_FUNC = "hashCode";
  
  private static final String EQUALS_FUNC = "equals";

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
        MiscUtil.deleteMethod(type, HASH_CODE_FUNC);
        MiscUtil.deleteMethod(type, EQUALS_FUNC);
      }

      IField[] fields = MiscUtil.getNonStaticMemberFields(type);

      String hashCodeFuncContent = createHashCodeFunctionBody(type, fields, isBeforeGuava18);
      type.createMethod(hashCodeFuncContent, null, false, null);
      
      String equalsFunctionContent = createEqualsFunctionBody(type, fields, isBeforeGuava18);
      type.createMethod(equalsFunctionContent, null, false, null);
    }
  }

  private String createEqualsFunctionBody(IType type, IField[] fields, boolean isBeforeGuava18) {
    String functionTemplate = "\n" + 
        "@Override\n" + 
        "public boolean equals(Object obj) {\n" +
        "  if (obj == this) {\n" + 
        "    return true;\n" + 
        "  }\n" + 
        "  \n" + 
        "  if (!(obj instanceof %s)) {\n" + 
        "      return false;\r\n" + 
        "  }\n" + 
        "  \n" +
        "%s" +
        "}\n";

    String coreContent = "";
    int length = fields.length;
    if (length > 0) {
      coreContent = String.format("  %s anotherInstance = (%s) obj;\n", type.getElementName(), type.getElementName());
      
      int index = 0;
      coreContent += String.format("  return Objects.equal(anotherInstance.%s, this.%s)", fields[0].getElementName(), fields[0].getElementName());
      coreContent += ending(length, index);
      
      for (index = 1; index < length; ++index) {
        coreContent += String.format("      && Objects.equal(anotherInstance.%s, this.%s)", fields[index].getElementName(), fields[index].getElementName());
        coreContent += ending(length, index);
      }
      
    } else {
      coreContent = "  return true;\n";
    }

    
    String finalContent = String.format(functionTemplate, type.getElementName(), coreContent);
    return finalContent;
  }

  private String ending(int length, int index) {
    if (index == length - 1) {
      return ";\n";
    } else {
      return "\n";
    }
  }

  private String createHashCodeFunctionBody(IType type, IField[] fields, boolean isBeforeGuava18) {
    if (fields.length == 0) {
      return "\n" 
        + "@Override\n" 
        + "public int hashCode() {\n"
        + "  return -1;\n" 
        + "}\n";
    }

    String toStringContent = "\n" 
        + "@Override\n" 
        + "public int hashCode() {\n"
        + "  return Objects.hashCode(%s);\n" 
        + "}\n";

    String elementContent = Joiner.on(", ").skipNulls().join(Collections2.transform(Arrays.asList(fields), new Function<IField, String>() {

      @Override
      public String apply(IField arg0) {
        return arg0.getElementName();
      }
      
    }));

    String finalContent = String.format(toStringContent, elementContent);
    return finalContent;
  }
}
