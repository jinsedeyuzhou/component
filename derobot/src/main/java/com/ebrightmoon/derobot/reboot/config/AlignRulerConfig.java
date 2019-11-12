package com.ebrightmoon.derobot.reboot.config;

import android.content.Context;

import com.ebrightmoon.derobot.reboot.constant.SharedPrefsKey;
import com.ebrightmoon.derobot.reboot.util.SharedPrefsUtil;

/**
 * @author
 */
public class AlignRulerConfig {
    public static boolean isAlignRulerOpen(Context context) {
        return SharedPrefsUtil.getBoolean(context, SharedPrefsKey.ALIGN_RULER_OPEN, false);
    }

    public static void setAlignRulerOpen(Context context, boolean open) {
        SharedPrefsUtil.putBoolean(context, SharedPrefsKey.ALIGN_RULER_OPEN, open);
    }
}