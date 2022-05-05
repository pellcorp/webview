package com.pellcorp.android.webview;

import android.content.Context;
import android.graphics.Bitmap;

public class WebViewChromeClient extends android.webkit.WebChromeClient {
    private final Context context;
    private final PageLoadListener pageLoadedListener;

    public WebViewChromeClient(final Context context, final PageLoadListener pageLoadedListener) {
        this.context = context;
        this.pageLoadedListener = pageLoadedListener;
    }

    @Override
    public void onReceivedIcon(android.webkit.WebView view, Bitmap icon) {
        super.onReceivedIcon(view, icon);
        pageLoadedListener.onReceiveIcon(icon);
    }

    @Override
    public void onProgressChanged(android.webkit.WebView view, int progress) {
        pageLoadedListener.onProgressChanged(progress);
    }
}
