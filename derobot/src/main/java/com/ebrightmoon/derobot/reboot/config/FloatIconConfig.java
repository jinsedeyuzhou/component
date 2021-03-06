package com.ebrightmoon.derobot.reboot.config;

import android.content.Context;

import com.ebrightmoon.derobot.reboot.constant.SharedPrefsKey;
import com.ebrightmoon.derobot.reboot.util.SharedPrefsUtil;

/**
 *  on 2018/12/14.
 */

public class FloatIconConfig {

    public static int getLastPosX(Context context) {
        return SharedPrefsUtil.getInt(context, SharedPrefsKey.FLOAT_ICON_POS_X, 0);
    }

    public static int getLastPosY(Context context) {
        return SharedPrefsUtil.getInt(context, SharedPrefsKey.FLOAT_ICON_POS_Y, 0);
    }

    public static void saveLastPosY(Context context, int val) {
        SharedPrefsUtil.putInt(context, SharedPrefsKey.FLOAT_ICON_POS_Y, val);
    }

    public static void saveLastPosX(Context context, int val) {
        SharedPrefsUtil.putInt(context, SharedPrefsKey.FLOAT_ICON_POS_X, val);
    }
}
