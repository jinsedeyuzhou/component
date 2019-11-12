package com.ebrightmoon.derobot.info.setting;

import android.util.Log;

import com.ebrightmoon.derobot.info.base.BaseBean;
import com.ebrightmoon.derobot.info.base.BaseData;

import org.json.JSONObject;

/**
 *
 */
public class SettingsBean extends BaseBean {
    private static final String TAG = SettingsBean.class.getSimpleName();

    /**
     * android id
     */
    private String androidId;

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    @Override
    protected JSONObject toJSONObject() {
        try {
            jsonObject.put(BaseData.Settings.ANDROID_ID, isEmpty(androidId));
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return super.toJSONObject();
    }
}
