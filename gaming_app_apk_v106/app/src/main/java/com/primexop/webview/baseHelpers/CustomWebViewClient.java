package com.primexop.webview.baseHelpers;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class CustomWebViewClient extends WebViewClient {
    private AppCompatActivity activity;

    public CustomWebViewClient(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();

        Log.d(TAG, "shouldOverrideUrlLoading:  "+url);

        if (url.startsWith("whatsapp://send?")) {
            // Handle WhatsApp sharing here
            try {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                activity.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        return super.shouldOverrideUrlLoading(view, request);
    }
}

