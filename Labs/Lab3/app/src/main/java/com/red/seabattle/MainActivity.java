package com.red.seabattle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class MainActivity extends Activity {
    @SuppressLint("SetJavaScriptEnabled")
    private void loadHtml() {
        WebView webView = (WebView) findViewById(R.id.webWiew);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Alert")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok,
                                (dialog, which) -> result.confirm()).setCancelable(false).create().show();

                return true;
            }
        });
        webView.loadUrl("file:///android_asset/html/index.html");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadHtml();
    }
}