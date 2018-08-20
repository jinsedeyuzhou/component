package com.ebrightmoon.ui.page.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ebrightmoon.common.base.BaseFragment;
import com.ebrightmoon.retrofitrx.callback.ACallback;
import com.ebrightmoon.retrofitrx.mode.DownProgress;
import com.ebrightmoon.retrofitrx.retrofit.AppClient;
import com.ebrightmoon.ui.R;

import java.util.HashMap;
import java.util.Map;

public class UiAccountFragment extends BaseFragment {

    private ProgressBar mPb;
    private TextView mTvShowPercent;
    private Handler handler = new Handler();
    private String fileName;
    private Map<String, String> params;

    @Override
    protected int getLayoutID() {
        return R.layout.ui_fragment_account;
    }

    @Override
    protected void initView(View view) {
        mPb = view.findViewById(R.id.pb);
        mTvShowPercent = view.findViewById(R.id.tv_show_percent);
        mPb.setMax(100);
        mPb.setProgress(0);
        params = new HashMap<>();
        view.findViewById(R.id.btn_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileName = System.currentTimeMillis() + ".apk";
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

                AppClient.getInstance(mContext, "https://raw.githubusercontent.com").downloadFile("/getlantern/lantern-binaries/master/lantern-installer.apk", params, mContext, new ACallback<DownProgress>() {
                            @Override
                            public void onSuccess(DownProgress downProgress) {
                                mPb.setProgress((int) (downProgress.getDownloadSize() * 1.0 / downProgress.getTotalSize() * 100));
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
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void processClick(View v) {

    }
}
