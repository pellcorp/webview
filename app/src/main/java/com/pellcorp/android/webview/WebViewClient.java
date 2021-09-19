package com.pellcorp.android.webview;

import android.content.Context;
import android.util.Log;
import android.webkit.WebView;

public class WebViewClient extends android.webkit.WebViewClient {
    private static final String TAG = "WebViewClient";

    private final Context context;

    public WebViewClient(final Context context) {
        this.context = context;
    }

    public void onLoadResource(WebView view, String url) {
        Log.i(TAG, url);
    }
}
