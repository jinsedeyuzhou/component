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


public class SettingMainActivity extends BaseActivity {


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
        setTitle("设置");

    }


    @Override
    protected void bindEvent() {

    }

    @Override
    public void processClick(View paramView) {

    }
}
