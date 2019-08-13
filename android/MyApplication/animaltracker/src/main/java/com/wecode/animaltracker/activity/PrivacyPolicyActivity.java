package com.wecode.animaltracker.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.wecode.animaltracker.R;

import java.util.Locale;

public class PrivacyPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        WebView mWebView = (WebView) findViewById(R.id.privacyPolicyWebview);

        if ("sk".equalsIgnoreCase(Locale.getDefault().getLanguage())) {
            mWebView.loadUrl("file:///android_asset/privacy_policy_sk.html");
        } else {
            mWebView.loadUrl("file:///android_asset/privacy_policy_en.html");
        }

    }

}
