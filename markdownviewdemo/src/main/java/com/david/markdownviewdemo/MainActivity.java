package com.david.markdownviewdemo;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import us.feras.mdv.MarkdownView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  MarkdownView markdownView = new MarkdownView(this);
        setContentView(markdownView);
        markdownView.loadMarkdownFile("file:///android_asset/okhhtp_easy_access.md");*/

        final WebView webview = new WebView(this);
        setContentView(webview);
        webview.getSettings().setDefaultTextEncodingName("UTF-8");
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("file:///android_asset/markdown_parent.html");

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                //HTML加载完成
                super.onPageFinished(view, url);
                String call = "javascript:marked(\'" + getData() + "\')";
                webview.loadUrl(call);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });


    }

    private String getData() {
        File filename = new File("file:///android_asset/okhhtp_easy_access.md");
        AssetManager asset = this.getAssets();
        InputStream input = null;
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            input = asset.open("okhhtp_easy_access.md");
  
            br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\\n");//注意这一行，通常应该是sb.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Log.e("info", sb.toString());
            return sb.toString();

        }
    }

}
