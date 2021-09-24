package com.pellcorp.android.webview;

import android.content.Context;

public class WebViewChromeClient extends android.webkit.WebChromeClient {
    private final Context context;

    public WebViewChromeClient(final Context context) {
        this.context = context;
    }
}
