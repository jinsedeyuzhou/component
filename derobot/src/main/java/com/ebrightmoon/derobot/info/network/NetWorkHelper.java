package com.ebrightmoon.derobot.info.network;

import android.content.Context;

import org.json.JSONObject;

/**
 *
 * @date 2018/3/28
 */

public class NetWorkHelper extends NetWorkInfo {

    public static JSONObject mobGetMobNetWork(Context context) {
        return getMobNetWork(context);
    }

}
