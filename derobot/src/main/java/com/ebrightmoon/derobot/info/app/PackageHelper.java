package com.ebrightmoon.derobot.info.app;

import android.content.Context;

import org.json.JSONObject;

/**
 *
 * @date 2018/1/12
 */
public class PackageHelper extends PackageInfo {


    /**
     * 获取包类信息
     *
     * @param context
     * @return
     */
    public static JSONObject getPackageInfo(Context context) {
        return packageInfo(context);
    }


}
