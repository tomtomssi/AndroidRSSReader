package com.example.AndroidRSSReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

public class DisplaySingleItem extends Activity {
    private TextView textViewTitle;
    private WebView webView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_single_item);
        intent = getIntent();
        initialize();
    }

    private void initialize() {
        initializeComponents();
    }

    private void initializeComponents() {
        textViewTitle = (TextView) this.findViewById(R.id.textViewTitle);
        webView = (WebView) this.findViewById(R.id.webViewSingleItemData);
        populateComponents();
    }

    private void populateComponents() {
        this.setTitle(intent.getStringExtra("PUBLISH_DATE"));
        textViewTitle.setText(intent.getStringExtra("TITLE"));
        webView.loadUrl(intent.getStringExtra("LINK"));
    }
}