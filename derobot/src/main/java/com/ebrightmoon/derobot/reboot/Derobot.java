package com.ebrightmoon.derobot.reboot;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.Toast;

import com.ebrightmoon.derobot.R;
import com.ebrightmoon.derobot.reboot.kit.Category;
import com.ebrightmoon.derobot.reboot.kit.IKit;
import com.ebrightmoon.derobot.reboot.kit.alignruler.AlignRuler;
import com.ebrightmoon.derobot.reboot.kit.blockmonitor.BlockMonitorKit;
import com.ebrightmoon.derobot.reboot.kit.colorpick.ColorPicker;
import com.ebrightmoon.derobot.reboot.kit.crash.CrashCapture;
import com.ebrightmoon.derobot.reboot.kit.custom.Custom;
import com.ebrightmoon.derobot.reboot.kit.dataclean.DataClean;
import com.ebrightmoon.derobot.reboot.kit.fileexplorer.FileExplorer;
import com.ebrightmoon.derobot.reboot.kit.gpsmock.GpsMock;
import com.ebrightmoon.derobot.reboot.kit.gpsmock.GpsMockManager;
import com.ebrightmoon.derobot.reboot.kit.gpsmock.ServiceHookManager;
import com.ebrightmoon.derobot.reboot.kit.layoutborder.LayoutBorder;
import com.ebrightmoon.derobot.reboot.kit.logInfo.LogInfo;
import com.ebrightmoon.derobot.reboot.kit.network.NetworkKit;
import com.ebrightmoon.derobot.reboot.kit.parameter.cpu.Cpu;
import com.ebrightmoon.derobot.reboot.kit.parameter.frameInfo.FrameInfo;
import com.ebrightmoon.derobot.reboot.kit.parameter.ram.Ram;
import com.ebrightmoon.derobot.reboot.kit.sysinfo.SysInfo;
import com.ebrightmoon.derobot.reboot.kit.temporaryclose.TemporaryClose;
import com.ebrightmoon.derobot.reboot.kit.timecounter.TimeCounterKit;
import com.ebrightmoon.derobot.reboot.kit.timecounter.instrumentation.HandlerHooker;
import com.ebrightmoon.derobot.reboot.kit.viewcheck.ViewChecker;
import com.ebrightmoon.derobot.reboot.kit.weaknetwork.WeakNetwork;
import com.ebrightmoon.derobot.reboot.kit.webdoor.WebDoor;
import com.ebrightmoon.derobot.reboot.kit.webdoor.WebDoorManager;
import com.ebrightmoon.derobot.reboot.ui.FloatIconPage;
import com.ebrightmoon.derobot.reboot.ui.UniversalActivity;
import com.ebrightmoon.derobot.reboot.ui.base.FloatPageManager;
import com.ebrightmoon.derobot.reboot.ui.base.PageIntent;
import com.ebrightmoon.derobot.reboot.ui.kit.KitItem;
import com.ebrightmoon.derobot.reboot.util.PermissionUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangweida on 2018/6/22.
 */

public class Derobot {
    private static final String TAG = "Derobot";

    private static SparseArray<List<IKit>> sKitMap = new SparseArray<>();

    private static List<ActivityLifecycleListener> sListeners = new ArrayList<>();

    private static boolean sHasRequestPermission;

    private static boolean sHasInit = false;

    private static WeakReference<Activity> sCurrentResumedActivity;

    private static boolean sShowFloatingIcon = true;


    public static void install(final Application app) {
        install(app, null);
    }

    public static void setWebDoorCallback(WebDoorManager.WebDoorCallback callback) {
        WebDoorManager.getInstance().setWebDoorCallback(callback);
    }

    public static void install(final Application app, List<IKit> selfKits) {
        if (sHasInit) {
            if (selfKits != null) {
                List<IKit> biz = sKitMap.get(Category.BIZ);
                if (biz != null) {
                    biz.clear();
                    biz.addAll(selfKits);
                    for (IKit kit : biz) {
                        kit.onAppInit(app);
                    }
                }
            }
            return;
        }
        sHasInit = true;
        HandlerHooker.doHook(app);
        ServiceHookManager.getInstance().install(app);
        app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            int startedActivityCounts;

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (startedActivityCounts == 0) {
                    FloatPageManager.getInstance().notifyForeground();
                }
                startedActivityCounts++;
            }

            @Override
            public void onActivityResumed(Activity activity) {
                if (PermissionUtil.canDrawOverlays(activity)) {
                    if (sShowFloatingIcon) {
                        showFloatIcon(activity);
                    }
                } else {
                    requestPermission(activity);
                }
                for (ActivityLifecycleListener listener : sListeners) {
                    listener.onActivityResumed(activity);
                }
                sCurrentResumedActivity = new WeakReference<>(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                for (ActivityLifecycleListener listener : sListeners) {
                    listener.onActivityPaused(activity);
                }
                sCurrentResumedActivity = null;
            }

            @Override
            public void onActivityStopped(Activity activity) {
                startedActivityCounts--;
                if (startedActivityCounts == 0) {
                    FloatPageManager.getInstance().notifyBackground();
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
        sKitMap.clear();
        List<IKit> tool = new ArrayList<>();
        List<IKit> biz = new ArrayList<>();
        List<IKit> ui = new ArrayList<>();
        List<IKit> performance = new ArrayList<>();
        List<IKit> exit = new ArrayList<>();

        tool.add(new SysInfo());
        tool.add(new FileExplorer());
        if (GpsMockManager.getInstance().isMockEnable()) {
            tool.add(new GpsMock());
        }
        tool.add(new WebDoor());
        tool.add(new CrashCapture());
        tool.add(new LogInfo());
        tool.add(new DataClean());
        tool.add(new WeakNetwork());

        performance.add(new FrameInfo());
        performance.add(new Cpu());
        performance.add(new Ram());
        performance.add(new NetworkKit());
        performance.add(new BlockMonitorKit());
        performance.add(new TimeCounterKit());
        performance.add(new Custom());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ui.add(new ColorPicker());
        }

        ui.add(new AlignRuler());
        ui.add(new ViewChecker());
        ui.add(new LayoutBorder());

        exit.add(new TemporaryClose());

        if (selfKits != null && !selfKits.isEmpty()) {
            biz.addAll(selfKits);
        }

        for (IKit kit : biz) {
            kit.onAppInit(app);
        }
        for (IKit kit : performance) {
            kit.onAppInit(app);
        }
        for (IKit kit : tool) {
            kit.onAppInit(app);
        }
        for (IKit kit : ui) {
            kit.onAppInit(app);
        }

        sKitMap.put(Category.BIZ, biz);
        sKitMap.put(Category.PERFORMANCE, performance);
        sKitMap.put(Category.TOOLS, tool);
        sKitMap.put(Category.UI, ui);
        sKitMap.put(Category.CLOSE, exit);

        FloatPageManager.getInstance().init(app);
    }

    private static void requestPermission(Context context) {
        if (!PermissionUtil.canDrawOverlays(context) && !sHasRequestPermission) {
            Toast.makeText(context, context.getText(R.string.dk_float_permission_toast), Toast.LENGTH_SHORT).show();
            PermissionUtil.requestDrawOverlays(context);
            sHasRequestPermission = true;
        }
    }

    private static void showFloatIcon(Context context) {
        if (context instanceof UniversalActivity) {
            return;
        }
        PageIntent intent = new PageIntent(FloatIconPage.class);
        intent.mode = PageIntent.MODE_SINGLE_INSTANCE;
        FloatPageManager.getInstance().add(intent);
    }

    public static List<IKit> getKitList(int catgory) {
        if (sKitMap.get(catgory) != null) {
            return new ArrayList<>(sKitMap.get(catgory));
        } else {
            return null;
        }
    }

    public static List<KitItem> getKitItems(int catgory) {
        if (sKitMap.get(catgory) != null) {
            List<KitItem> kitItems = new ArrayList<>();
            for (IKit kit : sKitMap.get(catgory)) {
                kitItems.add(new KitItem(kit));
            }
            return kitItems;
        } else {
            return null;
        }
    }

    public interface ActivityLifecycleListener {
        void onActivityResumed(Activity activity);

        void onActivityPaused(Activity activity);
    }

    public static void registerListener(ActivityLifecycleListener listener) {
        sListeners.add(listener);
    }

    public static void unRegisterListener(ActivityLifecycleListener listener) {
        sListeners.remove(listener);
    }

    public static void show() {
        if (!isShow()) {
            showFloatIcon(null);
        }
        sShowFloatingIcon = true;
    }

    public static void hide() {
        FloatPageManager.getInstance().removeAll();
        sShowFloatingIcon = false;
    }



    public static boolean isShow() {
        return sShowFloatingIcon;
    }

    public static Activity getCurrentResumedActivity() {
        if (sCurrentResumedActivity != null && sCurrentResumedActivity.get() != null) {
            return sCurrentResumedActivity.get();
        }
        return null;
    }

    public static void enableRequestPermissionSelf() {
        sHasRequestPermission = true;
    }
}
