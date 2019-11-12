package com.ebrightmoon.derobot.info.simcard;

import android.content.Context;

import org.json.JSONObject;


/**
 *
 * @date 2018/8/6
 * 运营商获取类
 */
public class SimCardHelper extends SimCardInfo {

    /**
     * 获取网络卡信息
     *
     * @return 运营商返回
     */
    public static JSONObject mobileSimInfo(Context context) {
        return getMobSimInfo(context);
    }
}
