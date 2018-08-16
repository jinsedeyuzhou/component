package com.ebrightmoon.main.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ebrightmoon.common.base.BaseActivity;
import com.ebrightmoon.main.R;
import com.ebrightmoon.retrofitrx.callback.ACallback;
import com.ebrightmoon.retrofitrx.mode.DownProgress;
import com.ebrightmoon.retrofitrx.retrofit.AppClient;

import java.util.HashMap;
import java.util.Map;

import static com.ebrightmoon.main.ui.fragment.NewsMainFragment.readAssetsTxt;

public class SettingActivity extends BaseActivity {
    private ProgressBar mPb;
    private TextView mTvShowPercent;
    private Handler handler=new Handler();
    private String fileName;
    private Map<String, String> params;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main_setting);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mPb = findViewById(R.id.pb);
        mTvShowPercent = findViewById(R.id.tv_show_percent);
        mPb.setMax(100);
        mPb.setProgress(0);
        params = new HashMap<>();
       findViewById(R.id.btn_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String xml = readAssetsTxt(mContext,"XML");
                fileName=System.currentTimeMillis()+".apk";
//                AppClient.getInstance().downloadFile("/IDPBootstrap/IDPServletDataProvider", xml,fileName, mContext, new ACallback<DownProgress>() {
//                    @Override
//                    public void onSuccess(final DownProgress data) {
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (data!=null) {
//                                    mPb.setProgress((int) (data.getDownloadSize()*1.0/data.getTotalSize()*100));
//                                    mTvShowPercent.setText("进度："+data.getPercent());
//                                }
//                            }
//                        });
//
//                    }
//
//                    @Override
//                    public void onFail(int errCode, String errMsg) {
//
//                    }
//                });

                AppClient.getInstance(mContext,"https://raw.githubusercontent.com").downloadFile("/getlantern/lantern-binaries/master/lantern-installer.apk", params, mContext, new ACallback<DownProgress>() {
                            @Override
                            public void onSuccess(DownProgress downProgress) {
                                mPb.setProgress((int) (downProgress.getDownloadSize()*1.0/downProgress.getTotalSize()*100));
                                mTvShowPercent.setText(downProgress.getPercent());
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {

                            }
                        }
                );
            }
        });
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public void processClick(View paramView) {

    }
}
