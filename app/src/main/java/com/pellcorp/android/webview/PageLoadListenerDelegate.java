package com.pellcorp.android.webview;

import android.graphics.Bitmap;

public class PageLoadListenerDelegate implements PageLoadListener {
    private PageLoadListener delegate;

    public void setDelegate(final PageLoadListener delegate) {
        this.delegate = delegate;
    }

    @Override
    public void onPageFinished(final String title) {
        if (delegate != null) {
            delegate.onPageFinished(title);
        }
    }

    @Override
    public void onReceiveIcon(Bitmap icon) {
        if (delegate != null) {
            delegate.onReceiveIcon(icon);
        }
    }

    @Override
    public void onProgressChanged(int progress) {
        if (delegate != null) {
            delegate.onProgressChanged(progress);
        }
    }

    @Override
    public void onPageStarted() {
        if (delegate != null) {
            delegate.onPageStarted();
        }
    }
}
