package com.ebrightmoon.common.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ebrightmoon.common.ebus.RxBus;
import com.ebrightmoon.common.util.AppManager;
import com.ebrightmoon.common.util.LogUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;


/**
 * Created by wyy on 2016/9/11.
 * 所有模块通用的可以在这里面设置
 */
public abstract  class CommonBaseActivity extends RxAppCompatActivity implements View.OnClickListener{
    private static final String TAG = "CommonBaseActivity";
    private static final int ACTIVITY_DESTROY = 4;
    private static final int ACTIVITY_PAUSE = 3;
    private static final int ACTIVITY_RESUME = 0;
    private static final int ACTIVITY_START = 2;
    private static final int ACTIVITY_STOP = 1;
    public int activityState;


    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
    {
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
        LogUtils.i(TAG, "onActivityResult");
        RxBus.get().toFlowable(String.class).subscribe(str ->{

        });
    }

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        LogUtils.i(TAG, "onCreate");
        AppManager.getAppManager().addActivity(this);

    }




    protected void onDestroy()
    {
        super.onDestroy();
        this.activityState = ACTIVITY_DESTROY;
        AppManager.getAppManager().finishActivity(this);

        LogUtils.i(TAG, "onDestroy");

    }

    protected void onPause()
    {
        super.onPause();
        this.activityState = ACTIVITY_PAUSE;
        LogUtils.i(TAG, "onPause");
    }

    protected void onRestart()
    {
        super.onRestart();
        LogUtils.i(TAG, "onRestart");
    }

    protected void onResume()
    {
        super.onResume();
        this.activityState = ACTIVITY_RESUME;
        LogUtils.i(TAG, "onResume");
    }

    protected void onStart()
    {
        super.onStart();
        this.activityState = ACTIVITY_START;
        LogUtils.i(TAG, "onStart");
    }

    protected void onStop()
    {
        super.onStop();
        this.activityState = ACTIVITY_STOP;
        LogUtils.i(TAG, "onStop");
    }



}
