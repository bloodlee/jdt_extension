package org.yli.eclipse.jdt.extension.dialogs;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
  private static final String BUNDLE_NAME = "org.yli.eclipse.jdt.extension.dialogs.messages"; //$NON-NLS-1$
  public static String ClassSelectionDialog_AFTER_GUAVA_18;
  public static String ClassSelectionDialog_BEFORE_GUAVA_18;
  public static String ClassSelectionDialog_CLASS_SELECTION;
  public static String ClassSelectionDialog_OVERWRITE_EXISTING_FUNCTION;
  public static String ClassSelectionDialog_SELECT_ALL;
  public static String ClassSelectionDialog_SELECT_CLASSES;
  public static String ClassSelectionDialog_SELECT_NONE;

  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
  }
}
