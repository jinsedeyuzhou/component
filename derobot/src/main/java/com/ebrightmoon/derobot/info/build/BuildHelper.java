package com.ebrightmoon.derobot.info.build;


import org.json.JSONObject;

/**
 *
 * @date 2018/3/28
 */

public class BuildHelper extends BuildInfo {

    /**
     * build信息
     *
     * @return
     */
    public static JSONObject mobGetBuildInfo() {
        return getBuildInfo();
    }
}
