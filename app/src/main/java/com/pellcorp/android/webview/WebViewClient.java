package com.pellcorp.android.webview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

public class WebViewClient extends android.webkit.WebViewClient {
    private static final String TAG = "WebViewClient";

    private final Context context;
    private final Preferences preferences;
    private final PageLoadListener pageLoadedListener;

    public WebViewClient(final Context context, final PageLoadListener pageLoadedListener) {
        this.context = context;
        this.pageLoadedListener = pageLoadedListener;
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

    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);

        if (pageLoadedListener != null) {
            pageLoadedListener.onPageStarted();
        }
    }

    @Override
    public void onPageFinished(android.webkit.WebView view, String url) {
        super.onPageFinished(view, url);

        if (pageLoadedListener != null) {
            pageLoadedListener.onPageFinished(view.getTitle());
        }
    }
}
