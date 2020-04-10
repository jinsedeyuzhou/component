package com.ebrightmoon.common.util;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Time: 2020-01-08
 * Author:wyy
 * Description:
 */
public final class TimeConstant {

    /**
     * 毫秒与毫秒的倍数
     */
    static final int MSEC = 1;
    /**
     * 秒与毫秒的倍数
     */
    static final int SEC = 1000;
    /**
     * 分与毫秒的倍数
     */
    static final int MIN = 60000;
    /**
     * 时与毫秒的倍数
     */
    static final int HOUR = 3600000;
    /**
     * 天与毫秒的倍数
     */
    static final int DAY = 86400000;

    @IntDef({MSEC, SEC, MIN, HOUR, DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
}
