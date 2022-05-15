package com.pellcorp.android.webview;

import android.graphics.Bitmap;

public interface PageLoadListener {
    void onPageFinished(final String title);
    void onReceiveIcon(final Bitmap icon);
    void onProgressChanged(final int newProgress);
    void onPageStarted(final String url);
}
