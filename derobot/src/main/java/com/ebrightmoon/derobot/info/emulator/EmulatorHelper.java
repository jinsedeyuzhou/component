package com.ebrightmoon.derobot.info.emulator;

import android.content.Context;

import org.json.JSONObject;


/**
 *
 * @date 2018/3/12
 */

public class EmulatorHelper extends EmulatorInfo {


    /**
     * 判断是否是模拟器
     * 通过静态资源，设备特征参数来判断
     *
     * @param context 上下文
     * @return true为模拟器
     */

    public static JSONObject mobCheckEmulator(Context context) {
        return checkEmulator(context);

    }

}
