package com.ebrightmoon.ui.page.test;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ebrightmoon.common.base.mvc.BaseActivity;
import com.ebrightmoon.common.permission.OnPermissionCallback;
import com.ebrightmoon.common.permission.PermissionManager;
import com.ebrightmoon.common.permission.RxPermissions;
import com.ebrightmoon.ui.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Time: 2019/6/11
 * Author:wyy
 * Description:
 */
public class UiHttpActivity extends BaseActivity {
    private ProgressBar mPb;
    private TextView mTvShowPercent;
    private int i = 1;
    private TextView mTvShowCount;
    private String fileName;
    private HashMap<String, String> params;
    private RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.ui_activity_http);
        rxPermissions = new RxPermissions(this);
    }

    @SuppressLint("CheckResult")
    @Override
    public void initData() {
        PermissionManager.instance().request(mActivity, new OnPermissionCallback() {
            @Override
            public void onRequestAllow(String permissionName) {

            }

            @Override
            public void onRequestRefuse(String permissionName) {
            }

            @Override
            public void onRequestNoAsk(String permissionName) {
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);


//        rxPermissions
//                .request(Manifest.permission.CAMERA)
//                .subscribe(granted -> {
//                    if (granted) { // Always true pre-M
//                        // I can control the camera now
//                    } else {
//                        // Oups permission denied
//                    }
//                });
    }
    @Override
    public void initView() {
        mPb = findViewById(R.id.pb);
        mPb.setMax(100);
        mPb.setProgress(0);
        mTvShowPercent = findViewById(R.id.tv_show_percent);
        mTvShowCount = findViewById(R.id.tv_show_count);
        // xml
//        findViewById(R.id.btn_download).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                i = 1;
//                fileName = System.currentTimeMillis() + ".zip";
//                String xml = readAssetsTxt(mContext, "XML");
//                AppClient.getInstance(mContext, "http://10.10.68.180:8001").downloadFile("/IDPBootstrap/IDPServletDataProvider", xml, "PZFG201844010000021546.zip", mContext, new ACallback<DownProgress>() {
//                    @Override
//                    public void onSuccess(DownProgress data) {
//                        downProgress = data;
//                        i++;
//                        mTvShowCount.setText(i + "");
//                        if (downProgress != null) {
//                            mPb.setProgress((int) (downProgress.getDownloadSize() * 1.0 / downProgress.getTotalSize() * 100));
//                            mTvShowPercent.setText("进度：" + downProgress.getPercent());
//                        }
//                    }
//
//                    @Override
//                    public void onFail(int errCode, String errMsg) {
//
//                    }
//                });
//            }
//        });

        params = new HashMap<>();
        findViewById(R.id.btn_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileName = System.currentTimeMillis() + ".apk";

            }
        });
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public void processClick(View paramView) {

    }

    /*

     **
     * 读取assets下的txt文件，返回utf-8 String
     * @param context
     * @param fileName 不包括后缀
     * @return
     */
    public static String readAssetsTxt(Context context, String fileName) {
        try {
            //Return an AssetManager instance for your application's package
            InputStream is = context.getAssets().open(fileName + ".txt");
            int size = is.available();
            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the buffer into a string.
            String text = new String(buffer, "utf-8");
            // Finally stick the string into the text view.
            return text;
        } catch (IOException e) {
            // Should never happen!
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        return "读取错误，请检查文件名";
    }

}
