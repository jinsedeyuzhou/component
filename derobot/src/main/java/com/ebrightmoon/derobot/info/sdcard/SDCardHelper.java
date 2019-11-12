package com.ebrightmoon.derobot.info.sdcard;


import org.json.JSONObject;

/**
 *
 * @date 2018/3/28
 */

public class SDCardHelper extends SDCardInfo {

    /**
     * 获取卡信息
     *
     * @return
     */
    public static JSONObject mobGetSdCard() {
        return getSdCard();
    }


}