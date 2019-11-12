package com.ebrightmoon.derobot.info.debug;

import android.content.Context;

import org.json.JSONObject;


/**
 *
 * @date 2018/11/20
 */
public class DebugHelper extends DebugInfo {

    /**
     * 调试模式判断
     *
     * @return
     */
    public static JSONObject getDebuggingData(Context context) {
        return getDebugData(context);
    }

}


