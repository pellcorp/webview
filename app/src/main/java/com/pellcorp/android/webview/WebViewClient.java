package com.pellcorp.android.webview;

import static android.util.Log.DEBUG;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
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
    public boolean shouldOverrideUrlLoading(final WebView view, final WebResourceRequest url) {
        final boolean externalLinks = preferences.getBoolean(R.string.pref_external_links);
        if (externalLinks) {
            final Intent intent = new Intent(Intent.ACTION_VIEW, url.getUrl());
            context.startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(final WebView view, final WebResourceRequest request) {
        final Uri url = request.getUrl() == null ? null : request.getUrl();
        if (url != null) {
            if (Log.isLoggable(TAG, DEBUG)) {
                Log.d(TAG, "shouldInterceptRequest: URL: " + url);
            }
        }
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public void onPageStarted(final WebView view, final String url, final Bitmap favicon) {
        super.onPageStarted(view, url, favicon);

        if (pageLoadedListener != null) {
            pageLoadedListener.onPageStarted(url);
        }
    }

    @Override
    public void onPageFinished(final android.webkit.WebView view, final String url) {
        super.onPageFinished(view, url);

        if (pageLoadedListener != null) {
            pageLoadedListener.onPageFinished(view.getTitle());
        }
    }
}
