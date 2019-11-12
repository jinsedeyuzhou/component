package com.ebrightmoon.derobot.info.memory;

import android.content.Context;

import org.json.JSONObject;

/**
 *
 * @date 2018/1/5
 */
public class MemoryHelper extends MemoryInfo {

    /**
     * info
     *
     * @param context
     * @return
     */
    public static JSONObject getMemoryInfo(Context context) {
        return memoryInfo(context);
    }

}
