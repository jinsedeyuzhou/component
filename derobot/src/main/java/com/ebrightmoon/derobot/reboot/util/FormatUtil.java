package com.ebrightmoon.derobot.reboot.util;

import java.util.Date;

/**
 *  on 2019-06-12
 */
public class FormatUtil {
    private FormatUtil() {
    }

    public static String format(long time) {
        return new Date(time).toString();
    }
}