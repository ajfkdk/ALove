package com.example.myapp_ui.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.myapp_ui.R;
import com.hjq.toast.ToastUtils;

public class movie_info extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("movie_name");
        webView= findViewById(R.id.wv_movie_web);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);
                // 加载完成
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                super.onPageStarted(view, url, favicon);
                if (url.contains("pan.baidu.com")) {//判断连接是否是百度云盘的链接
                    Intent intent = new Intent(movie_info.this, movie_show_url.class);
                    intent.putExtra("url", url);
                    startActivity(intent);
                }
            }
        });//解决跑到系统浏览器加载url的问题
        webView.getSettings().setJavaScriptEnabled(true);
//        使用之前记得去主配置文件中申请权限
        String url = "https://v.wxbxkx.com/?s=";
        url += name;
        webView.loadUrl(url);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}