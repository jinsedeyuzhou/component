package com.ebrightmoon.derobot.reboot.config;

import android.content.Context;

import com.ebrightmoon.derobot.reboot.constant.SharedPrefsKey;
import com.ebrightmoon.derobot.reboot.util.SharedPrefsUtil;

/**
 * @author
 */
public class ColorPickConfig {
    public static boolean isColorPickOpen(Context context) {
        return SharedPrefsUtil.getBoolean(context, SharedPrefsKey.COLOR_PICK_OPEN, false);
    }

    public static void setColorPickOpen(Context context, boolean open) {
        SharedPrefsUtil.putBoolean(context, SharedPrefsKey.COLOR_PICK_OPEN, open);
    }
}