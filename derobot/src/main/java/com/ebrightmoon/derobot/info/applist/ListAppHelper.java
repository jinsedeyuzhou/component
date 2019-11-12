package com.ebrightmoon.derobot.info.applist;

import android.content.Context;

import org.json.JSONObject;

import java.util.List;

/**
 *
 * @date 2018/5/10
 */

public class ListAppHelper extends ListAppInfo {

    /**
     * 获取安装软件列表
     *
     * @param context
     * @return
     */
    public static List<JSONObject> mobListApp(Context context) {
        return getMobListApp(context);
    }
}
