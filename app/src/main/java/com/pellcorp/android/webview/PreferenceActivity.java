package com.pellcorp.android.webview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class PreferenceActivity extends AppCompatActivity {
    private PreferenceFragment preferenceFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceFragment = new PreferenceFragment();
        getFragmentManager().beginTransaction().replace(android.R.id.content, preferenceFragment).commit();

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}