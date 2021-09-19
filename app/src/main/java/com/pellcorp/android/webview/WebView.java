package com.pellcorp.android.webview;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.AttributeSet;

public class WebView extends android.webkit.WebView {
    public WebView(final Context context) {
        this(context,null);
    }

    public WebView(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.getSettings().setDomStorageEnabled(true);
        this.getSettings().setAllowFileAccess(false);

        WebViewClient webClient = new WebViewClient(context);
        this.setWebViewClient(webClient);
        WebViewChromeClient chromeClient = new WebViewChromeClient();
        this.setWebChromeClient(chromeClient);

        // https://stackoverflow.com/a/23844693
        boolean isDebuggable = ( 0 != ( context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE ) );
        if (isDebuggable) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }
}
