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
