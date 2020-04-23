package com.ebrightmoon.webviewlib.page;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.ebrightmoon.webviewlib.BuildConfig;
import com.ebrightmoon.webviewlib.R;
import com.ebrightmoon.webviewlib.permission.OnPermissionCallback;
import com.ebrightmoon.webviewlib.permission.PermissionManager;
import com.ebrightmoon.webviewlib.permission.RxPermissions;
import com.ebrightmoon.webviewlib.utils.WebUtil;
import com.ebrightmoon.webviewlib.widget.CustomDialog;
import com.ebrightmoon.webviewlib.widget.CustomPopWindow;
import com.ebrightmoon.webviewlib.widget.ProgressWebView;
import com.ebrightmoon.webviewlib.widget.ToolsBar;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * Time: 2020-04-13
 * Author:wyy
 * Description:
 */
public class WebViewActivity extends AppCompatActivity {
    private ProgressWebView mWebView;
    private String title;
    private String html;
    private String url;
    private FrameLayout mWebViewContent;
    private ToolsBar toolsBar;
    private String phoneNumber;
    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;
    private Context mContext;
    private Activity mActivity;
    private JavaScriptInterface javaScriptInterface;

    private RxPermissions rxPermissions;


    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        mContext = this;
        mActivity = this;
        rxPermissions = new RxPermissions(mActivity);
        setContentView(R.layout.activity_webview);
        initView();
        bindEvent();
        initData();

    }


    @SuppressLint("JavascriptInterface")
    public void initView() {
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        html = getIntent().getStringExtra("html");

        toolsBar = findViewById(R.id.toolsbar);
        if (title != null) {
            toolsBar.setTitle(title);
        }
        toolsBar.setListener(new ToolsBar.OnTitleBarClickListener() {
            @Override
            public void onRightClick() {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    finish();
                }
            }
        });
        mWebViewContent = findViewById(R.id.fl_content);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new ProgressWebView(this, null);
        mWebView.setHorizontalScrollBarEnabled(false);//水平不显示
        mWebView.setVerticalScrollBarEnabled(false); //垂直不显示
        mWebView.setLayoutParams(params);
        mWebViewContent.addView(mWebView);
        initWebViewSettings(mWebView.getSettings());
        javaScriptInterface = new JavaScriptInterface(mContext);
        mWebView.addJavascriptInterface(javaScriptInterface, "ebrightmoon");
//        WebUtil.initWebViewSettings(mWebView.getSettings());

    }

    public void initData() {
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    //加载完网页进度条消失
                    mWebView.progressbar.setVisibility(View.GONE);

                } else {
                    //开始加载网页时显示进度条
                    mWebView.progressbar.setVisibility(View.VISIBLE);
                    //设置进度值
                    mWebView.progressbar.setProgress(newProgress);
                }
            }

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> valueCallback) {
                uploadMessage = valueCallback;
                getICCarD();
            }

            // For Android  >= 3.0
            public void openFileChooser(ValueCallback valueCallback, String acceptType) {
                uploadMessage = valueCallback;
                getICCarD();
            }

            //For Android  >= 4.1
            public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
                uploadMessage = valueCallback;
                getICCarD();
            }

            // For Android >= 5.0
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                uploadMessageAboveL = filePathCallback;
                getICCarD();
                return true;
            }


            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            /**
             * 截取console 获取调整信息
             * @param consoleMessage
             * @return
             */
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                return super.onConsoleMessage(consoleMessage);
            }

        });
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.equals("tel:")) {
                    Toast.makeText(WebViewActivity.this, "等待网页加载完毕再试", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (url.contains("tel")) {
                    phoneNumber = url.split("tel:")[1];
                    if (phoneNumber != null && phoneNumber != "") {
                        callPhone(phoneNumber);
                    } else {
                        Toast.makeText(WebViewActivity.this, "等待网页加载完毕再试", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }

                if (!WebUtil.isNetworkUrl(url)) {
                    return true;
                }

                WebView.HitTestResult hitTestResult = view.getHitTestResult();
                if (!TextUtils.isEmpty(url) && hitTestResult == null) {
                    view.loadUrl(url);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                if (url.equals("tel:")) {
                    Toast.makeText(WebViewActivity.this, "等待网页加载完毕再试", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (url.contains("tel")) {
                    phoneNumber = url.split("tel:")[1];
                    if (phoneNumber != null && phoneNumber != "") {
                        callPhone(phoneNumber);
                    } else {
                        Toast.makeText(WebViewActivity.this, "等待网页加载完毕再试", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                //解决不是URL不能跳转
                if (!WebUtil.isNetworkUrl(url)) {
                    return true;
                }
                // 解决重定向问题
                WebView.HitTestResult hitTestResult = view.getHitTestResult();
                if (!TextUtils.isEmpty(url) && hitTestResult == null) {
                    view.loadUrl(url);
                    return true;
                }
                return true;
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

            }

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                // super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }
        });

        mWebView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                        mWebView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
        if (html != null) {
            mWebView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
        } else {
            mWebView.loadUrl(url);
        }
    }

    public void initWebViewSettings(WebSettings wSet) {
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


    /**
     * 调用电话
     *
     * @param phone
     */
    private void callPhone(String phone) {
        PermissionManager.instance().request(mActivity, new OnPermissionCallback() {
            @Override
            public void onRequestAllow(String permissionName) {
                if (phone != null) {
                    CustomDialog customDialogView = new CustomDialog(mContext);
                    customDialogView.setCanceledOnTouchOutside(false);
                    customDialogView.setButton(CustomDialog.BUTTON_POSITIVE, "呼叫", (dialog, which) -> {
                        startActivity(WebUtil.getCallIntent(phoneNumber));
                        dialog.dismiss();
                    });
                    customDialogView.setButton(CustomDialog.BUTTON_NEGATIVE, "取消", (dialog, which) -> {
                        dialog.dismiss();
                    });
                    customDialogView.setTsingDescrition(phone);
                    customDialogView.show();
                }
            }

            @Override
            public void onRequestRefuse(String permissionName) {
                CustomDialog customDialogView = new CustomDialog(mActivity);
                customDialogView.setTsingTitle("提示");
                customDialogView.setTsingDescrition("请到应用权限中心打开拨号权限，否则不能使用此功能。");
                customDialogView.setButton(CustomDialog.BUTTON_NEGATIVE, "取消", null);
                customDialogView.setButton(CustomDialog.BUTTON_POSITIVE, "去打开", (dialog, which) -> {
                    startActivity(WebUtil.getAppDetailSettingIntent(mActivity));
                });
                customDialogView.show();
            }

            @Override
            public void onRequestNoAsk(String permissionName) {

            }
        }, Manifest.permission.CALL_PHONE);
    }


    /**
     * 选择拍照还是选择照片
     */
    @SuppressLint("CheckResult")
    private void getICCarD() {

        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (granted) {
                        // All requested permissions are granted
                        new CustomPopWindow(mContext)
                                .setTitle("请选择图片")
                                .setData(new ArrayList<String>() {{
                                    add("拍照");
                                    add("从手机相册选择");
                                }})
                                .setPopupWindow()
                                .setOnItemClickListener((title, position) -> {
                                    if (position == 0) {
                                        //拍照
                                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivityForResult(intent, 1);
                                    } else if (position == 1) {
                                        //从手机相册选择
                                        Intent intent_gallsy = new Intent(Intent.ACTION_GET_CONTENT);
                                        intent_gallsy.setType("image/*");
                                        startActivityForResult(intent_gallsy, 2);
                                    }
                                }).openPopWindow();
                    } else {
                        cancelCallback();
                        CustomDialog customDialogView = new CustomDialog(mActivity);
                        customDialogView.setTsingTitle("提示");
                        customDialogView.setTsingDescrition("请到应用权限中心存储权限，否则不能使用此功能。");
                        customDialogView.setButton(CustomDialog.BUTTON_NEGATIVE, "取消", null);
                        customDialogView.setButton(CustomDialog.BUTTON_POSITIVE, "去打开", (dialog, which) -> {
                            startActivity(WebUtil.getAppDetailSettingIntent(mActivity));
                        });
                        customDialogView.show();
                        // At least one permission is denied

                    }
                });


    }

    /**
     * 打开图库回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && null != data) {
            if (uploadMessageAboveL == null)
                return;
            String imageFilePath = WebUtil.getCameraUriCompress(mActivity, data);
            if (imageFilePath == null) {
                cancelCallback();
                return;
            }
            Uri imageFileUri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //针对Android7.0，需要通过FileProvider封装过的路径，提供给外部调用
                imageFileUri = FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()), mContext.getPackageName() + ".provider", new File(imageFilePath));
            } else {
                imageFileUri = Uri.fromFile(new File(imageFilePath));
            }
            uploadMessageAboveL.onReceiveValue(new Uri[]{imageFileUri});
            uploadMessageAboveL = null;
        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK && null != data) {
            if (uploadMessageAboveL == null) {
                cancelCallback();
                return;
            }
            Bitmap galleryPicture = null;
            try {
                galleryPicture = WebUtil.getGalleryPicture(this, data);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //转成url
            Uri imageUri = Uri.parse(MediaStore.Images.Media.insertImage(
                    getContentResolver(), galleryPicture, null, null));
            //上传图片
            uploadMessageAboveL.onReceiveValue(new Uri[]{imageUri});
            uploadMessageAboveL = null;
            // 显示图片
//            Drawable drawable = new BitmapDrawable(galleryPicture);

        }
        cancelCallback();
    }


    /**
     * 防止点击一次第二次不执行
     */
    public void cancelCallback() {
        if (uploadMessageAboveL != null) {
            uploadMessageAboveL.onReceiveValue(null);
            uploadMessageAboveL = null;
        }

        if (uploadMessage != null) {
            uploadMessage.onReceiveValue(null);
            uploadMessage = null;
        }
    }

    protected void bindEvent() {

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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
