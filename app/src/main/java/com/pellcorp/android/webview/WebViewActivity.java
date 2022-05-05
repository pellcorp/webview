package com.pellcorp.android.webview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class WebViewActivity extends Activity implements PageLoadListener {
    private static final String TAG = "WebViewActivity";
    private WebView webView;

    private ProgressBar progressBar;
    private Bitmap pageFavicon = null;
    private String pageTitle = null;
    private boolean currently_reloading = true;

    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);

        final Preferences preferences = new Preferences(this);
		getActionBar().setTitle(preferences.getString(R.string.pref_url));
        setContentView(R.layout.webview);
        progressBar = findViewById(R.id.progressBar);
        webView = findViewById(R.id.webView);
        webView.setOnPageLoadedListener(this);
        hideSystemBars();
	}

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (webView != null) {
            webView.restoreState(savedInstanceState);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (webView != null) {
            webView.destroy();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (webView != null) {
            webView.saveState(outState);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            final Preferences preferences = new Preferences(this);
            final String currentPin = preferences.getString(R.string.pref_pin);

            if (currentPin != null) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Preferences Pin");

                // Set an EditText view to get user input
                final EditText input = new EditText(this);
                input.setFilters(new InputFilter[] { new InputFilter.LengthFilter(4) });
                input.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                alert.setView(input);

                alert.setPositiveButton("Ok", (dialog, whichButton) -> {
                    final String pin = input.getText().toString();
                    if (currentPin.equals(pin)) {
                        startActivity(new Intent(WebViewActivity.this, PreferenceActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Pin is incorrect", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setNegativeButton("Cancel", (dialog, whichButton) -> { });

                alert.show();
            } else {
                startActivity(new Intent(WebViewActivity.this, PreferenceActivity.class));
            }
            return true;
        } else if (item.getItemId() == R.id.reload) {
            webView.reload();
        }
        return false;
    }

    private void hideSystemBars() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            final WindowInsetsController controller = getWindow().getInsetsController();
            if(controller != null) {
                controller.hide(WindowInsets.Type.navigationBars());
                controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
            }
        }  else {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        final Preferences preferences = new Preferences(this);
        final String url = preferences.getString(R.string.pref_url);
        final boolean scrolling = preferences.getBoolean(R.string.pref_scrolling);
        if (url != null) {
            if (!url.equals(webView.getCurrentUrl()) || scrolling != webView.isScrollingEnabled()) {
                webView.enableScrolling(preferences.getBoolean(R.string.pref_scrolling));
                webView.loadUrl(url);
            }
        } else {
            Log.i(TAG, "Url not configured");
        }
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getActionBar().hide();
        } else {
            getActionBar().show();
        }
    }

    @Override
    public void onPageStarted() {
        currently_reloading = true;
        pageTitle = null;
        pageFavicon = null;
        getActionBar().setTitle("");
        getActionBar().setDisplayUseLogoEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(false);
    }

    @Override
    public void onPageFinished(final String title) {
        if (pageTitle == null) {
            pageTitle = title;
        }
    }

    @Override
    public void onProgressChanged(final int progress) {
        if (currently_reloading) {
            if (progressBar.getVisibility() == ProgressBar.GONE && progress < 100) {
                progressBar.setVisibility(ProgressBar.VISIBLE);
            }

            progressBar.setProgress(progress);
            if (progress == 100) {
                progressBar.setVisibility(ProgressBar.GONE);
                currently_reloading = false;
            }
        }
    }

    @Override
    public void onReceiveIcon(Bitmap icon) {
        if (isBetterBitmap(icon)) {
            Log.i(TAG, "Icon received as " + icon.getHeight() + "x" + icon.getWidth());
            this.pageFavicon = icon; // this is the original bit map
            final Bitmap bitmap = icon.getHeight() == 64 ? this.pageFavicon : Bitmap.createScaledBitmap(this.pageFavicon, 64, 64, false);
            final BitmapDrawable theIcon = new BitmapDrawable(getResources(), bitmap);
            getActionBar().setDisplayUseLogoEnabled(true);
            getActionBar().setDisplayShowHomeEnabled(true);
            getActionBar().setIcon(theIcon);
        }

        final String theTitle = " " + pageTitle;
        if (!theTitle.equals(getActionBar().getTitle())) {
            getActionBar().setTitle(" " + pageTitle);
            hideSystemBars();
        }
    }

    private boolean isBetterBitmap(Bitmap icon) {
        if (icon == null) {
            return false;
        }

        if (this.pageFavicon == null) {
            return true;
        }
        return this.pageFavicon.getHeight() < 64 && icon.getHeight() >= 64;
    }
}
