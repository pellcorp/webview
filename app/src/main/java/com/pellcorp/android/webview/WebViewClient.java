package com.pellcorp.android.webview;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

public class WebViewClient extends android.webkit.WebViewClient {
    private static final String TAG = "WebViewClient";

    private final Context context;
    private final Preferences preferences;

    public WebViewClient(final Context context) {
        this.context = context;
        preferences = new Preferences(context);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest url) {
        final boolean externalLinks = preferences.getBoolean(R.string.pref_external_links);
        if (externalLinks) {
            final Intent intent = new Intent(Intent.ACTION_VIEW, url.getUrl());
            context.startActivity(intent);
            return true;
        }
        return false;
    }
}
