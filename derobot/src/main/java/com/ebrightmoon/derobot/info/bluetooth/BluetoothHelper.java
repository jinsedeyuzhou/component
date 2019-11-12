package com.ebrightmoon.derobot.info.bluetooth;


import android.content.Context;

import org.json.JSONObject;


/**
 *
 * @date 2018/3/28
 */

public class BluetoothHelper extends BluetoothInfo {

    /**
     * 获取蓝牙信息
     * @param context
     * @return
     */
    public static JSONObject mobGetMobBluetooth(Context context) {
        return getMobBluetooth(context);
    }


}
