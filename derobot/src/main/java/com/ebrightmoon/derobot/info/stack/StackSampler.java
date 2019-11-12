package com.ebrightmoon.derobot.info.stack;


/**
 * Dumps thread stack.
 *
 */
public class StackSampler {

    public static String getStackInfo(Thread thread) {
        StringBuilder stringBuilder = new StringBuilder();

        for (StackTraceElement stackTraceElement : thread.getStackTrace()) {
            stringBuilder
                    .append(stackTraceElement.toString());
        }
        return stringBuilder.toString();

    }
}