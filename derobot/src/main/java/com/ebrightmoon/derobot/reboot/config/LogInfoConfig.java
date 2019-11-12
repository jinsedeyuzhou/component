package com.ebrightmoon.derobot.reboot.config;

import android.content.Context;

import com.ebrightmoon.derobot.reboot.constant.SharedPrefsKey;
import com.ebrightmoon.derobot.reboot.util.SharedPrefsUtil;

/**
 * @author
 */
public class LogInfoConfig {
    public static boolean isLogInfoOpen(Context context) {
        return SharedPrefsUtil.getBoolean(context, SharedPrefsKey.LOG_INFO_OPEN, false);
    }

    public static void setLogInfoOpen(Context context, boolean open) {
        SharedPrefsUtil.putBoolean(context, SharedPrefsKey.LOG_INFO_OPEN, open);
    }
}