package com.ebrightmoon.commonlogic.base;

import android.os.Process;
import android.text.TextUtils;

import com.ebrightmoon.common.base.BaseApplication;
import com.ebrightmoon.common.common.CommonApplication;
import com.ebrightmoon.common.util.LogUtils;
import com.ebrightmoon.common.widget.imageloader.LoaderFactory;
import com.ebrightmoon.data.dao.DbHelper;
import com.ebrightmoon.data.router.RouterConfig;
import com.facebook.stetho.Stetho;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Time: 2019-08-15
 * Author:wyy
 * Description:
 */
public class ConApp extends BaseApplication {

    private static ConApp conApp;

    public static ConApp getApp() {
        return conApp;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        conApp=this;
        init();
    }

    //    private void initCrashReport() {
//        Context context = getApplicationContext();
//        // 获取当前包名
//        String packageName = context.getPackageName();
//        // 获取当前进程名
//        String processName = getProcessName(android.os.Process.myPid());
//        // 设置是否为上报进程
//        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
//        strategy.setUploadProcess(processName == null || processName.equals(packageName));
//        // 初始化Bugly isDebug 如果为true,开启调试模式，会即使上传错误消息，如果上线，给为false
//        CrashReport.initCrashReport(context, "39a78f56da", true, strategy);
//        // 如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
//        // CrashReport.initCrashReport(context, strategy);
//    }

    private void init()
    {
        new Thread()
        {
            @Override
            public void run() {
                super.run();
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                //路由初始化
                RouterConfig.init(conApp, true);
                DbHelper.getInstance().init(conApp.getApplicationContext());
            }
        }.start();

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
