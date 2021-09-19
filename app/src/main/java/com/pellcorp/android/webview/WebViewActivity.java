package com.pellcorp.android.webview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class WebViewActivity extends Activity {
    private static final String TAG = "WebViewActivity";
    private WebView webView;

    private Bundle savedInstanceState;

    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        this.savedInstanceState = savedInstanceState;

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
	}

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (webView != null) {
            webView.restoreState(savedInstanceState);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (webView != null) {
            webView.destroy();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (webView != null) {
            webView.saveState(outState);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(this, PreferenceActivity.class));
                return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initWebView();
    }

    private void initWebView() {
        if (webView == null) {
            setContentView(R.layout.webview);

            webView = (WebView) findViewById(R.id.webView);

            if (savedInstanceState != null) {
                webView.restoreState(savedInstanceState);
            }
        }

        Preferences preferences = new Preferences(this);
        String url = preferences.getString(R.string.pref_url);
        if (url != null) {
            webView.loadUrl(url);
        } else {
            Log.i(TAG, "Url not configured");
        }
    }
}
