package com.ebrightmoon.derobot.info.useragent;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.WebSettings;

import com.ebrightmoon.derobot.info.base.BaseData;

import java.lang.reflect.Method;

/**
 *
 */
public class UserAgentHelper {

    public static String getDefaultUserAgent(Context paramContext) {
        String userAgent = null;
        try {
            Method localMethod = WebSettings.class.getDeclaredMethod("getDefaultUserAgent", new Class[]{Context.class});
            if (localMethod != null) {
                userAgent = (String) localMethod.invoke(WebSettings.class, new Object[]{paramContext});
            }
            if (userAgent == null) {
                userAgent = System.getProperty("http.agent");
            }
        } catch (Exception localException) {

        }
        return TextUtils.isEmpty(userAgent) ? BaseData.UNKNOWN_PARAM : userAgent;
    }
}
