package com.ebrightmoon.derobot.info.signal;

import android.content.Context;

import org.json.JSONObject;



public class SignalHelper extends SignalInfo {


    /**
     * 信号强度获取
     *
     * @param context
     * @return
     */
    public static JSONObject mobGetNetRssi(Context context) {
        return getNetRssi(context);
    }


}
