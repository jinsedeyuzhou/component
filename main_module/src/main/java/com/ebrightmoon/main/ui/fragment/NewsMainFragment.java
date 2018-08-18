package com.ebrightmoon.main.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ebrightmoon.common.base.BaseFragment;
import com.ebrightmoon.main.R;
import com.ebrightmoon.main.entity.Channel;
import com.ebrightmoon.retrofitrx.callback.ACallback;
import com.ebrightmoon.retrofitrx.mode.DownProgress;
import com.ebrightmoon.retrofitrx.retrofit.AppClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NewsMainFragment extends BaseFragment {


    private ProgressBar mPb;
    private TextView mTvShowPercent;
    private Handler handler=new Handler();
    private int i=1;
    private TextView mTvShowCount;
    private DownProgress downProgress;
    private String fileName;

    public static NewsMainFragment newInstance(Channel courseType) {
        NewsMainFragment newsMainFragment = new NewsMainFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Channel.KEY_COURSE_TYPE, courseType);
        newsMainFragment.setRegisterEvent(true);
        newsMainFragment.setArguments(bundle);
        return newsMainFragment;
    }


    @Override
    protected int getLayoutID() {
        return R.layout.fragment_main_news;
    }

    @Override
    protected void initView(View view) {

        mPb = view.findViewById(R.id.pb);
        mPb.setMax(100);
        mPb.setProgress(0);
        mTvShowPercent = view.findViewById(R.id.tv_show_percent);
        mTvShowCount = view.findViewById(R.id.tv_show_count);
        view.findViewById(R.id.btn_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i=1;
                fileName=System.currentTimeMillis()+".zip";
                String xml = readAssetsTxt(mContext,"XML");
                AppClient.getInstance(mContext,"http://10.10.68.180:8001").downloadFile("/IDPBootstrap/IDPServletDataProvider", xml,"PZFG201844010000021546.zip", mContext, new ACallback<DownProgress>() {
                    @Override
                    public void onSuccess( DownProgress data) {
                        downProgress=data;
                        i++;
                        mTvShowCount.setText(i+"");
                        if (downProgress!=null) {
                            mPb.setProgress((int) (downProgress.getDownloadSize()*1.0/downProgress.getTotalSize()*100));
                            mTvShowPercent.setText("进度："+downProgress.getPercent());

                        }


                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });
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

    /*

   **
           * 读取assets下的txt文件，返回utf-8 String
     * @param context
     * @param fileName 不包括后缀
     * @return
             */
    public static String readAssetsTxt(Context context, String fileName){
        try {
            //Return an AssetManager instance for your application's package
            InputStream is = context.getAssets().open(fileName+".txt");
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
