package com.ebrightmoon.derobot.reboot.kit.layoutborder;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.ebrightmoon.derobot.reboot.Derobot;
import com.ebrightmoon.derobot.reboot.ui.UniversalActivity;
import com.ebrightmoon.derobot.reboot.ui.layoutborder.ViewBorderFrameLayout;

/**
 *  on 2019/1/9
 */
public class LayoutBorderManager {
    private boolean isRunning;

    private ViewBorderFrameLayout mViewBorderFrameLayout;

    private Derobot.ActivityLifecycleListener mLifecycleListener = new Derobot.ActivityLifecycleListener() {
        @Override
        public void onActivityResumed(Activity activity) {
            resolveActivity(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }
    };

    private void resolveActivity(Activity activity) {
        if (activity == null || (activity instanceof UniversalActivity)) {
            return;
        }
        Window window = activity.getWindow();
        if (window == null) {
            return;
        }
        final ViewGroup root = (ViewGroup) window.getDecorView();
        if (root == null) {
            return;
        }
        mViewBorderFrameLayout = new ViewBorderFrameLayout(root.getContext());
        while (root.getChildCount() != 0) {
            View child = root.getChildAt(0);
            if (child instanceof ViewBorderFrameLayout) {
                mViewBorderFrameLayout = (ViewBorderFrameLayout) child;
                return;
            }
            root.removeView(child);
            mViewBorderFrameLayout.addView(child);
        }
        root.addView(mViewBorderFrameLayout);
    }

    private static class Holder {
        private static LayoutBorderManager INSTANCE = new LayoutBorderManager();
    }

    private LayoutBorderManager() {
    }

    public static LayoutBorderManager getInstance() {
        return Holder.INSTANCE;
    }

    public void start() {
        isRunning = true;
        resolveActivity(Derobot.getCurrentResumedActivity());
        Derobot.registerListener(mLifecycleListener);
    }

    public void stop() {
        isRunning = false;
        if (mViewBorderFrameLayout != null) {
            mViewBorderFrameLayout.requestLayout();
        }
        mViewBorderFrameLayout = null;
        Derobot.unRegisterListener(mLifecycleListener);
    }

    public boolean isRunning() {
        return isRunning;
    }
}