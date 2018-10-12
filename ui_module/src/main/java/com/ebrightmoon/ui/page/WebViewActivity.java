package com.ebrightmoon.ui.page;

import android.Manifest;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.ebrightmoon.common.base.BaseActivity;
import com.ebrightmoon.common.util.Tools;
import com.ebrightmoon.common.widget.ProgressWebView;
import com.ebrightmoon.ui.R;

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
        setContentView(R.layout.ui_activity_webview);
    }

    @Override
    public void initView() {
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        html = getIntent().getStringExtra("html");
        setTitle("设置");
        mWebViewContent = (FrameLayout) findViewById(R.id.fl_content);
        mWebView = new ProgressWebView(this, null);
        mWebViewContent.addView(mWebView);
        // 同步cookies
        // synCookies(this, protocolUrl);
        // 设置webview属性
        Tools.initWebViewSettings(mWebView.getSettings());
//        initWebViewSettings(mWebView.getSettings());
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url=request.getUrl().toString();
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                String url=request.getUrl().toString();
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view,
                                                              String url) {

                WebResourceResponse response = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    response = super.shouldInterceptRequest(view, url);
                    //引用本地文件
//                    if (url.contains("index.css")) {
//                        try {
//                            response = new WebResourceResponse("text/css",
//                                    "UTF-8", getAssets().open("index.css"));
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
                }
                return response;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mWebView.loadUrl("javascript:setHeight()");

            }

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                // super.onReceivedSslError(view, handler, error);
                handler.proceed();
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
            mWebView.loadDataWithBaseURL(null,html,"text/html","UTF-8",null);
        } else {
            mWebView.loadUrl(url);
        }

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
