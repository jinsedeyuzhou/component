package com.ebrightmoon.webviewlib.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.TypedValue;
import android.view.Display;
import android.webkit.WebSettings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Time: 2020-04-13
 * Author:wyy
 * Description:
 */
public class WebUtil {

    /**
     * 检查 URL 是否合法
     * @param url
     * @return true 合法，false 非法
     */
    public static boolean isNetworkUrl(String url) {
        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern patt = Pattern.compile(regex);
        Matcher matcher = patt.matcher(url);
        return matcher.matches();
    }

    public static void initWebViewSettings(WebSettings wSet) {
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
     * dp转px
     *
     * @param context
     * @param dpVal
     * @return
     */
    public static int dp2px(Context context, float dpVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    //跳转到打电话的界面
    public static Intent getCallIntent(String telNumber) {
        Uri uri = Uri.parse("tel:"+telNumber);
        Intent it = new Intent(Intent.ACTION_DIAL, uri);
        return it;
    }

    //获取跳转到权限列表的intent
    public static Intent getAppDetailSettingIntent(Context mContext) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", mContext.getPackageName());
        }
        return localIntent;
    }

    /**
     * 获取拍照的图片
     *
     * @param context
     * @param data
     * @return
     */
    public static String getCameraUriCompress(Activity context, Intent data) {
        Display display = context.getWindowManager().getDefaultDisplay();
        float ScreenW = display.getWidth();
        float ScreenH = display.getHeight();
        String sdState = Environment.getExternalStorageState();
        if (!sdState.equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }
        new DateFormat();
        String photoName = DateFormat.format("yyyyMMdd_hhmmss",
                Calendar.getInstance(Locale.CHINA))
                + ".jpg";
//        Bundle bundle = data.getExtras();

        // 获取相机返回的数据，并转换为图片格式
//        data.getParcelableExtra("data");
        Bitmap bitmap=null;
        if (data.hasExtra("data")) {
            bitmap = (Bitmap) data.getParcelableExtra("data");
        }else
        {
            return null;
        }


        FileOutputStream fout = null;
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(path + "/vm");
        file.mkdirs();
        String filename = file.getPath() + "/" + photoName;
        try {
            fout = new FileOutputStream(filename);
            bitmap.compress(Bitmap.CompressFormat.JPEG
                    , 100, fout);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return filename;
    }

    public static Bitmap getGalleryPicture(Activity context, Intent data) throws Exception {
        Display display = context.getWindowManager().getDefaultDisplay();
        float ScreenW = display.getWidth();
        float ScreenH = display.getHeight();
        ContentResolver resolver = context.getContentResolver();
        // 照片的原始资源地址
        Uri originalUri = data.getData();
        System.out.println("uri=" + originalUri);

        String photoName = DateFormat.format("yyyyMMdd_hhmmss",
                Calendar.getInstance(Locale.CHINA))
                + ".jpg";

        File file = new File("/sdcard/pintu/");
        file.mkdirs();
        String filename = file.getPath() + "/" + photoName;

        FileOutputStream fout = null;

        try {
            fout = new FileOutputStream(filename);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        Bitmap bitmap = MediaStore.Images.Media.getBitmap(resolver, originalUri);
        try {
            // 使用ContentProvider通过URI获取原始图片


            float oldBitW = bitmap.getWidth();
            float oldBitH = bitmap.getHeight();

            float x = ((ScreenH * oldBitW) / oldBitH) / oldBitW;
            float y = ScreenH / oldBitH;

            Matrix matrix = new Matrix();
            matrix.postScale(x, y);

            bitmap = Bitmap.createBitmap(bitmap, 0, 0, (int) oldBitW,
                    (int) oldBitH, matrix, true);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 1, fout);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fout.flush();
                fout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }


}
