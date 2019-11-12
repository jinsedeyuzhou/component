package com.ebrightmoon.derobot.info.camera;

import android.content.Context;

import org.json.JSONObject;


/**
 *
 * @date 2018/1/5
 */
public class CameraHelper extends CameraInfo {

    /**
     * 摄像头信息
     *
     * @param context
     * @return
     */
    public static JSONObject getCameraInfo(Context context) {
        return cameraInfo(context);
    }

}
