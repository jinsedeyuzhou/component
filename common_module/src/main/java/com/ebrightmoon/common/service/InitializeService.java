package com.ebrightmoon.common.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Process;

import com.ebrightmoon.common.common.CommonApplication;
import com.ebrightmoon.common.util.LogUtils;
import com.ebrightmoon.common.widget.imageloader.LoaderFactory;
import com.facebook.stetho.Stetho;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * 作者：create by  Administrator on 2018/9/6
 * 需要在清单文件里面注册
 */
public class InitializeService extends IntentService {
    private static final String TAG = InitializeService.class.getSimpleName();
    private static final String ACTION_INIT_WHEN_APP_CREATE = "com.ebrightmoon.util.action.INIT";
    public InitializeService() {
        super("InitializeService");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT_WHEN_APP_CREATE.equals(action)) {
                performInit();
            }
        }
    }

    private void performInit() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        CommonApplication.initQDApp(this);
        LoaderFactory.getLoader().init(this);
        //路由初始化
//        RouterConfig.init(this.getApplication(), true);
        //Stetho调试工具初始化
        Stetho.initializeWithDefaults(this);
        LogUtils.setShowLog(true);
//        initLogger();
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

}
