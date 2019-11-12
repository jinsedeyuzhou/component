package com.ebrightmoon.derobot.reboot.config;

import android.content.Context;

import com.ebrightmoon.derobot.reboot.constant.SharedPrefsKey;
import com.ebrightmoon.derobot.reboot.util.SharedPrefsUtil;

public class CrashCaptureConfig {
    public static boolean isCrashCaptureOpen(Context context) {
        return SharedPrefsUtil.getBoolean(context, SharedPrefsKey.CRASH_CAPTURE_OPEN, false);
    }

    public static void setCrashCaptureOpen(Context context, boolean open) {
        SharedPrefsUtil.putBoolean(context, SharedPrefsKey.CRASH_CAPTURE_OPEN, open);
    }
}
