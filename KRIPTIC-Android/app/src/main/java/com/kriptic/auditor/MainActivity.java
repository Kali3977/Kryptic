package com.kriptic.auditor;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import java.io.OutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MainActivity extends Activity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        webView.setBackgroundColor(0xFF0B1020);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new ReportBridge(this), "KRIPTIC");
        setContentView(webView);
        webView.loadUrl("file:///android_asset/index.html");
    }

    public class ReportBridge {
        private final Context context;
        ReportBridge(Context context) { this.context = context; }

        @JavascriptInterface
        public void saveReport(String reportText) {
            try {
                ContentValues values = new ContentValues();
                values.put(MediaStore.MediaColumns.DISPLAY_NAME, "kriptic-demo-report.txt");
                values.put(MediaStore.MediaColumns.MIME_TYPE, "text/plain");
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);
                Uri uri = getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
                if (uri == null) throw new IOException("Unable to create report file");
                try (OutputStream out = getContentResolver().openOutputStream(uri)) {
                    if (out == null) throw new IOException("Unable to open report file");
                    out.write(reportText.getBytes(StandardCharsets.UTF_8));
                }
                runOnUiThread(() -> Toast.makeText(MainActivity.this,
                    "Report saved to Downloads", Toast.LENGTH_LONG).show());
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this,
                    "Could not save report: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }
}
