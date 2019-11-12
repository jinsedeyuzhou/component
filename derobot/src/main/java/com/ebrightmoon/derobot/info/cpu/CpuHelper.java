package com.ebrightmoon.derobot.info.cpu;

import org.json.JSONObject;


/**
 *
 * @date 2018/1/5
 */
public class CpuHelper extends CpuInfo {

    /**
     * CPU
     * @return
     */
    public static JSONObject mobGetCpuInfo() {
        return getCpuInfo();
    }


}
