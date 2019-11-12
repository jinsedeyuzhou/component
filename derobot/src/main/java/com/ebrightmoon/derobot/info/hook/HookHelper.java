package com.ebrightmoon.derobot.info.hook;

import android.content.Context;

import org.json.JSONObject;


/**
 *
 * @date 2018/8/8
 * Xposed   框架检测工具类
 */
public class HookHelper extends HookInfo {


    /**
     * 判断是否有xposed等hook工具
     *
     * @param context
     * @return
     */
    public static JSONObject isXposedHook(Context context) {
        return getXposedHook(context);
    }


}
