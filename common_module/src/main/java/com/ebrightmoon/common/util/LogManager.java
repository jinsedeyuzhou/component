package com.ebrightmoon.common.util;

import uk.co.senab.photoview.log.Logger;
import uk.co.senab.photoview.log.LoggerDefault;

/**
 * Time: 2019/7/8
 * Author:wyy
 * Description:
 */
public final class LogManager {

    private static Logger logger = new LoggerDefault();

    public static void setLogger(Logger newLogger) {
        logger = newLogger;
    }

    public static Logger getLogger() {
        return logger;
    }

}