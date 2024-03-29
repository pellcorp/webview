package com.pellcorp.android.webview;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;

public class WebView extends android.webkit.WebView {
    private boolean scrolling;
    private String currentUrl;
    private final PageLoadListenerDelegate delegate = new PageLoadListenerDelegate();

    public WebView(final Context context) {
        this(context,null);
    }

    public void enableScrolling(final boolean scrolling) {
        this.scrolling = scrolling;

        this.setVerticalScrollBarEnabled(scrolling);
        this.setHorizontalScrollBarEnabled(scrolling);
    }

    public void setOnPageLoadedListener(final PageLoadListener pageLoadedListener) {
        this.delegate.setDelegate(pageLoadedListener);
    }

    public WebView(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.getSettings().setDomStorageEnabled(true);
        this.getSettings().setAllowFileAccess(true);
        this.getSettings().setDomStorageEnabled(true);
        this.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT );

        this.getSettings().setSupportZoom(true);
        this.getSettings().setBuiltInZoomControls(true);
        this.getSettings().setDisplayZoomControls(false);

        CookieManager.getInstance().setAcceptCookie(true);

        final WebViewClient webClient = new WebViewClient(context, this.delegate);
        this.setWebViewClient(webClient);
        final WebViewChromeClient chromeClient = new WebViewChromeClient(context, this.delegate);
        this.setWebChromeClient(chromeClient);

        // https://stackoverflow.com/a/23844693
        boolean isDebuggable = ( 0 != ( context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE ) );
        if (isDebuggable) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }

    public boolean isScrollingEnabled() {
        return scrolling;
    }

    public String getCurrentUrl() {
        return currentUrl;
    }

    @Override
    public void loadUrl(final String url) {
        this.currentUrl = url;
        super.loadUrl(url);
    }

    // https://newbedev.com/disable-scrolling-in-webview
    @Override
    public boolean overScrollBy(final int deltaX, final int deltaY, final int scrollX, final int scrollY,
                                final int scrollRangeX, final int scrollRangeY, final int maxOverScrollX,
                                final int maxOverScrollY, final boolean isTouchEvent) {
        if (!scrolling) {
            return false;
        } else {
            return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
        }
    }

    @Override
    public void scrollTo(final int x, final int y) {
        if (scrolling) {
            super.scrollTo(x, y);
        }
    }

    @Override
    public void computeScroll() {
        if (scrolling) {
            super.computeScroll();
        }
    }

    // https://stackoverflow.com/questions/47812876/webview-media-content-stops-when-the-screen-is-locked-activitys-onstop-is-cal
    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        if (visibility != View.GONE && visibility != View.INVISIBLE) {
            super.onWindowVisibilityChanged(visibility);
        }
    }
}
