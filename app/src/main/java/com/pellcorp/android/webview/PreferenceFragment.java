package com.pellcorp.android.webview;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import android.text.method.PasswordTransformationMethod;

public class PreferenceFragment extends android.preference.PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    public PreferenceFragment() {
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        initSummary(getPreferenceScreen());
    }

    @Override
    public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, final String key) {
        updatePreference(findPreference(key));
    }

    private void initSummary(final Preference p) {
        if (p instanceof PreferenceGroup) {
            PreferenceGroup pGrp = (PreferenceGroup) p;
            for (int i = 0; i < pGrp.getPreferenceCount(); i++) {
                initSummary(pGrp.getPreference(i));
            }
        } else {
            updatePreference(p);
        }
    }

    private void updatePreference(final Preference preference) {
        if (preference instanceof EditTextPreference) {
            final EditTextPreference listPreference = (EditTextPreference) preference;

            if(listPreference.getEditText().getTransformationMethod() != PasswordTransformationMethod.getInstance() ) {
                listPreference.setSummary(listPreference.getText());
            }
        }
    }
}
