package com.pellcorp.android.webview;

import android.app.Activity;
import android.os.Bundle;

public class PreferenceActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final PreferenceFragment preferenceFragment = new PreferenceFragment();
        getFragmentManager().beginTransaction().replace(android.R.id.content, preferenceFragment).commit();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}