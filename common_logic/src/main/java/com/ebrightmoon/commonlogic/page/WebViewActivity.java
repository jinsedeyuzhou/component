package com.ebrightmoon.commonlogic.page;

import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ebrightmoon.common.base.BaseActivity;
import com.ebrightmoon.common.widget.ProgressWebView;
import com.ebrightmoon.commonlogic.R;

import static android.view.KeyEvent.KEYCODE_BACK;


/**
 * Created by Administrator on 2017/12/11.
 */

public class WebViewActivity extends BaseActivity {

    private ProgressWebView mWebView;
    private String title;
    private String url;
    private FrameLayout mWebViewContent;
    private String html;
    private String phoneNumber;


    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_webview);
    }

    @Override
    public void initView() {
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        html = getIntent().getStringExtra("html");

        initTitle();
        mWebViewContent = (FrameLayout) findViewById(R.id.fl_content);
        mWebView = new ProgressWebView(this, null);
        mWebViewContent.addView(mWebView);

        initWebView(mWebView);

//        mWebView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                super.onProgressChanged(view, newProgress);
//            }
//        });

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
            }
        });

//        mWebView.setOnLongClickListener(new View.OnLongClickListener() {
//
//            @Override
//            public boolean onLongClick(View v) {
//                return true;
//            }
//        });

        if (html != null) {
            mWebView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
        } else {
            mWebView.loadUrl(url);
        }

    }

    /**
     * 设置标题
     */
    private void initTitle() {

        RelativeLayout bar_rl_left = findViewById(R.id.bar_rl_left);
        bar_rl_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ImageView bar_iv_left = findViewById(R.id.bar_iv_left);

        RelativeLayout bar_rl_right = findViewById(R.id.bar_rl_right);
        bar_rl_right.setVisibility(View.VISIBLE);

        ImageView bar_iv_right = findViewById(R.id.bar_iv_right);

    }

    /**
     * 设置WebView参数
     */
    private void initWebView(ProgressWebView webView) {
        WebSettings wSet = webView.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wSet.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        // 是否显示缩放按钮
        wSet.setBuiltInZoomControls(false);
        // 支持缩放
        wSet.setSupportZoom(false);
        wSet.setTextZoom(100);
        // 默认字体大小
        wSet.setDefaultFontSize(12);
        wSet.setAllowFileAccess(false);
        // 设置可以访问文件
        wSet.setAllowFileAccess(true);
        // 设置支持webView JavaScript
        wSet.setJavaScriptEnabled(true);
        // 设置缓冲的模式
        wSet.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 设置字符编码
        wSet.setDefaultTextEncodingName("utf-8");
        //优先使用缓存
//        wSet.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        wSet.setAppCacheEnabled(false);
        wSet.setDomStorageEnabled(true);
        wSet.setDatabaseEnabled(true);
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public void initData() {

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mWebView != null)
            mWebView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWebView != null)
            mWebView.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.setWebViewClient(null);
            mWebView.setWebChromeClient(null);
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();
            if (mWebViewContent != null)
                mWebViewContent.removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    @Override
    public void processClick(View paramView) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
