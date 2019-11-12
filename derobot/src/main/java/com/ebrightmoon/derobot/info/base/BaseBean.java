package com.ebrightmoon.derobot.info.base;

import android.text.TextUtils;

import org.json.JSONObject;

import java.io.Serializable;

/**
 *
 */
public class BaseBean implements Serializable {

    protected JSONObject jsonObject = new JSONObject();


    protected JSONObject toJSONObject() {
        return jsonObject;
    }

    protected BaseBean() {

    }

    protected String isEmpty(String value) {
        if (TextUtils.isEmpty(value)) {
            return BaseData.UNKNOWN_PARAM;
        }
        return value;
    }

    protected String isEmpty(boolean value) {
        return value+"";
    }
}
