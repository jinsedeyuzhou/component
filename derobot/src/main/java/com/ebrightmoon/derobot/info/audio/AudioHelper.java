package com.ebrightmoon.derobot.info.audio;

import android.content.Context;

import org.json.JSONObject;

/**
 *
 */
public class AudioHelper extends AudioInfo {

    /**
     * 获取电量信息
     *
     * @param context 上下文
     * @return 电量JSON
     */
    public static JSONObject mobGetMobAudio(Context context) {
        return getAudio(context);
    }


}
