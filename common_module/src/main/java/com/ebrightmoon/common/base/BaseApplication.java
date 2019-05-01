package com.ebrightmoon.common.base;

import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.ebrightmoon.common.common.CommonApplication;
import com.ebrightmoon.common.service.InitializeService;
import com.ebrightmoon.common.util.LogUtils;
import com.ebrightmoon.common.widget.imageloader.LoaderFactory;
import com.ebrightmoon.data.RouterConfig;
import com.facebook.stetho.Stetho;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by wyy on 2016/9/11.
 */
public class BaseApplication extends Application {
    private static final String TAG=BaseFragment.class.getSimpleName();
    private static BaseApplication app;

    public static BaseApplication getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        initLogger();
        init();
        InitializeService.start(this);
//        CommonApplication.initQDApp(this);
//        LoaderFactory.getLoader().init(this);
//        //路由初始化
//        RouterConfig.init(this, true);
//        //Stetho调试工具初始化
//        Stetho.initializeWithDefaults(this);
//        initCrashReport();
//        LogUtils.setShowLog(true);
//        Logger.addLogAdapter(new AndroidLogAdapter() {
//            @Override public boolean isLoggable(int priority, String tag) {
//                return BuildConfig.DEBUG;
//            }
//        });

    }
    private void init()
    {
        new Thread()
        {
            @Override
            public void run() {
                super.run();
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                CommonApplication.initQDApp(app);
                LoaderFactory.getLoader().init(app);
                //路由初始化
                RouterConfig.init(app, true);
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

    private void initCrashReport() {
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly isDebug 如果为true,开启调试模式，会即使上传错误消息，如果上线，给为false
        CrashReport.initCrashReport(context, "39a78f56da", true, strategy);
        // 如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
        // CrashReport.initCrashReport(context, strategy);
    }


    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        //支持超过65535
        MultiDex.install(this);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

}
