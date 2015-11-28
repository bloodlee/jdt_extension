package org.yli.eclipse.jdt.extension.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.yli.eclipse.jdt.extension.Activator;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

  @Override
  public void initializeDefaultPreferences() {
    IPreferenceStore store = Activator.getDefault().getPreferenceStore();

    store.setDefault(PreferenceKeys.OVERWITE_FUNCTION, true);
    store.setDefault(PreferenceKeys.BEFORE_GUAVA_18, true);
  }

}
