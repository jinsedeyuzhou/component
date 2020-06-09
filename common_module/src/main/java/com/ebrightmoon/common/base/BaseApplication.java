package com.ebrightmoon.common.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Process;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

import com.ebrightmoon.common.common.CommonApplication;
import com.ebrightmoon.common.util.LogUtils;
import com.ebrightmoon.common.util.ScreenAdapter;
import com.ebrightmoon.common.widget.imageloader.LoaderFactory;
import com.facebook.stetho.Stetho;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;


/**
 * Created by wyy on 2016/9/11.
 */
public class BaseApplication extends Application {
    private static final String TAG=BaseApplication.class.getSimpleName();
    private static BaseApplication app;

    public static BaseApplication getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        ScreenAdapter.setCustomDensity(this);
        init();
        initLogger();

    }
    private void init()
    {
        new Thread()
        {
            @Override
            public void run() {
                super.run();
                //路由初始化

                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                CommonApplication.initQDApp(app);
                LoaderFactory.getLoader().init(app);
                //Stetho调试工具初始化
                Stetho.initializeWithDefaults(app);
                LogUtils.setShowLog(true);
            }
        }.start();

    }

    private static  void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag(TAG)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return true;
            }
        });
    }



    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        //支持超过65535
        MultiDex.install(this);
    }


    /**
     * 全局限制改变字体
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        if (newConfig.fontScale != 1f) {
            getResources();
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        if (resources.getConfiguration().fontScale != 1f) {
            Configuration conf = new Configuration();
            conf.setToDefaults();
            resources.updateConfiguration(conf, resources.getDisplayMetrics());
        }
        return resources;
    }

}
