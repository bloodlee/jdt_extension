package org.yli.eclipse.jdt.extension.util;

import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.internal.core.CompilationUnit;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.google.common.collect.Lists;

/**
 * {@link MiscUtil} will take a lot of dirty work.
 * 
 * @author yli
 *
 */
public class MiscUtil {

  private MiscUtil() {
    // do nothing
  }

  public static ICompilationUnit getCompilationUnit(IStructuredSelection structuredSelection) {
    if (structuredSelection != null && structuredSelection.getFirstElement() instanceof CompilationUnit) {
      return ((CompilationUnit) structuredSelection.getFirstElement()).getCompilationUnit();
    } else {
      return null;
    }
  }

  public static void deleteMethod(IType type, String functionName) throws JavaModelException {
    if (type == null) {
      return;
    }

    IMethod[] methods = type.getMethods();
    for (IMethod method : methods) {
      if (method.getElementName().equals(functionName)) {
        method.delete(true, null);
        break;
      }
    }
  }

  public static IField[] getNonStaticMemberFields(IType type) throws JavaModelException {
    if (type == null) {
      return new IField[] {};
    }

    ASTParser parser = ASTParser.newParser(AST.JLS8);
    parser.setResolveBindings(true);
    parser.setSource(type.getCompilationUnit());

    ASTNode unitNode = parser.createAST(new NullProgressMonitor());

    final IField[] fields = type.getFields();
    final List<IField> nonStaticMemberFields = Lists.newArrayList();

    unitNode.accept(new ASTVisitor() {

      @Override
      public boolean visit(VariableDeclarationFragment node) {
        IJavaElement element = node.resolveBinding().getJavaElement();

        for (int i = 0; i < fields.length; ++i) {
          if (fields[i].equals(element)) {
            FieldDeclaration fieldDeclaration = (FieldDeclaration) node.getParent();

            int modifier = fieldDeclaration.getModifiers();
            if (!Modifier.isStatic(modifier)) {
              nonStaticMemberFields.add(fields[i]);
            }
          }
        }

        return true;
      }

    });

    return nonStaticMemberFields.toArray(new IField[] {});
  }
}
