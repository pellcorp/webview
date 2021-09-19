package com.pellcorp.android.webview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class PreferenceActivity extends Activity {
    private PreferenceFragment preferenceFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceFragment = new PreferenceFragment();
        getFragmentManager().beginTransaction().replace(android.R.id.content, preferenceFragment).commit();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}