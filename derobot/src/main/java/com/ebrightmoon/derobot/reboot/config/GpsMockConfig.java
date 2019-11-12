package com.ebrightmoon.derobot.reboot.config;

import android.content.Context;

import com.ebrightmoon.derobot.reboot.constant.CachesKey;
import com.ebrightmoon.derobot.reboot.constant.SharedPrefsKey;
import com.ebrightmoon.derobot.reboot.model.LatLng;
import com.ebrightmoon.derobot.reboot.util.CacheUtils;
import com.ebrightmoon.derobot.reboot.util.SharedPrefsUtil;

/**
 *  on 2018/9/20.
 */

public class GpsMockConfig {
    public static boolean isGPSMockOpen(Context context) {
        return SharedPrefsUtil.getBoolean(context, SharedPrefsKey.GPS_MOCK_OPEN, false);
    }

    public static void setGPSMockOpen(Context context, boolean open) {
        SharedPrefsUtil.putBoolean(context, SharedPrefsKey.GPS_MOCK_OPEN, open);
    }

    public static LatLng getMockLocation(Context context) {
        return (LatLng) CacheUtils.readObject(context, CachesKey.MOCK_LOCATION);
    }

    public static void saveMockLocation(Context context, LatLng latLng) {
        CacheUtils.saveObject(context, CachesKey.MOCK_LOCATION, latLng);
    }
}