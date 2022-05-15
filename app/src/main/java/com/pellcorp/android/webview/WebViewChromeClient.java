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
    public void onReceivedIcon(final android.webkit.WebView view, final Bitmap icon) {
        super.onReceivedIcon(view, icon);
        pageLoadedListener.onReceiveIcon(icon);
    }

    @Override
    public void onProgressChanged(final android.webkit.WebView view, final int progress) {
        pageLoadedListener.onProgressChanged(progress);
    }
}
