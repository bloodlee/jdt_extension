package org.yli.eclipse.jdt.extension.dialogs;

import java.io.IOException;

import org.eclipse.jdt.core.IType;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.yli.eclipse.jdt.extension.Activator;
import org.yli.eclipse.jdt.extension.preferences.PreferenceKeys;

public class ClassSelectionDialog extends Dialog {
  private Table table;

  private IType[] inputTypes;

  private IType[] selectedTypes;

  private boolean overwriteExistingFunction = false;

  private CheckboxTableViewer classesTableViewer;

  private boolean beforeGuava18 = true;

  private IPreferenceStore store = Activator.getDefault().getPreferenceStore();

  /**
   * Create the dialog.
   * 
   * @param parentShell
   */
  public ClassSelectionDialog(Shell parentShell, IType[] types) {
    super(parentShell);

    inputTypes = types;

    initPreference();
  }

  private void initPreference() {
    overwriteExistingFunction = store.getBoolean(PreferenceKeys.OVERWITE_FUNCTION);
    beforeGuava18 = store.getBoolean(PreferenceKeys.BEFORE_GUAVA_18);
  }

  /**
   * Create contents of the dialog.
   * 
   * @param parent
   */
  @Override
  protected Control createDialogArea(Composite parent) {
    Composite container = (Composite) super.createDialogArea(parent);
    container.setLayout(new GridLayout(2, false));

    Label lblSelectClasses = new Label(container, SWT.NONE);
    lblSelectClasses.setText(Messages.ClassSelectionDialog_SELECT_CLASSES);
    new Label(container, SWT.NONE);

    classesTableViewer = CheckboxTableViewer.newCheckList(container, SWT.BORDER | SWT.FULL_SELECTION);
    table = classesTableViewer.getTable();
    table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));

    Button btnSelectAllButton = new Button(container, SWT.NONE);
    btnSelectAllButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
    btnSelectAllButton.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        classesTableViewer.setAllChecked(true);
      }
    });
    btnSelectAllButton.setText(Messages.ClassSelectionDialog_SELECT_ALL);

    Button btnSelectNoneButton = new Button(container, SWT.NONE);
    btnSelectNoneButton.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        classesTableViewer.setAllChecked(false);
      }
    });
    btnSelectNoneButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
    btnSelectNoneButton.setText(Messages.ClassSelectionDialog_SELECT_NONE);

    final Button btnOverwriteExistingTostring = new Button(container, SWT.CHECK);
    btnOverwriteExistingTostring.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        overwriteExistingFunction = btnOverwriteExistingTostring.getSelection();
      }
    });
    btnOverwriteExistingTostring.setText(Messages.ClassSelectionDialog_OVERWRITE_EXISTING_FUNCTION);
    btnOverwriteExistingTostring.setSelection(overwriteExistingFunction);

    classesTableViewer.setContentProvider(new ArrayContentProvider());
    classesTableViewer.setLabelProvider(new LabelProvider() {

      @Override
      public String getText(Object element) {
        if (element instanceof IType) {
          return ((IType) element).getElementName();
        } else {
          return null;
        }
      }

    });

    classesTableViewer.setInput(inputTypes);

    new Label(container, SWT.NONE);

    Composite composite = new Composite(container, SWT.NONE);
    GridLayout gl_composite = new GridLayout(2, false);
    gl_composite.verticalSpacing = 0;
    gl_composite.horizontalSpacing = 0;
    composite.setLayout(gl_composite);

    Button btnBeforeGuava = new Button(composite, SWT.RADIO);
    btnBeforeGuava.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        beforeGuava18 = true;
      }
    });
    btnBeforeGuava.setSelection(beforeGuava18);
    btnBeforeGuava.setText(Messages.ClassSelectionDialog_BEFORE_GUAVA_18);

    Button btnGuavaAfter18 = new Button(composite, SWT.RADIO);
    btnGuavaAfter18.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        beforeGuava18 = false;
      }
    });
    btnGuavaAfter18.setText(Messages.ClassSelectionDialog_AFTER_GUAVA_18);
    btnGuavaAfter18.setSelection(!beforeGuava18);
    new Label(container, SWT.NONE);

    return container;
  }

  /**
   * Create contents of the button bar.
   * 
   * @param parent
   */
  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
    createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
  }

  /**
   * Return the initial size of the dialog.
   */
  @Override
  protected Point getInitialSize() {
    return new Point(556, 453);
  }

  @Override
  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);

    newShell.setText(Messages.ClassSelectionDialog_CLASS_SELECTION);
  }

  @Override
  protected void buttonPressed(int buttonId) {
    if (IDialogConstants.OK_ID == buttonId) {
      selectedTypes = convertToIType(classesTableViewer.getCheckedElements());

      store.setValue(PreferenceKeys.OVERWITE_FUNCTION, overwriteExistingFunction);
      store.setValue(PreferenceKeys.BEFORE_GUAVA_18, beforeGuava18);

      if (store instanceof IPersistentPreferenceStore) {
        try {
          ((IPersistentPreferenceStore) store).save();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
    super.buttonPressed(buttonId);
  }

  private IType[] convertToIType(Object[] checkedElements) {
    IType[] types = new IType[checkedElements.length];
    for (int i = 0; i < types.length; ++i) {
      types[i] = (IType) (checkedElements[i]);
    }
    return types;
  }

  public IType[] getSelectedTypes() {
    return selectedTypes;
  }

  public boolean isOverwriteExistingFunction() {
    return overwriteExistingFunction;
  }

  public boolean isBeforeGuava18() {
    return beforeGuava18;
  }

}
