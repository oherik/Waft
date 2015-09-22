package com.hjorthjort.commutity.util;

/**
 * @author hjorthjort
 *         Created 22/09/15
 */
public class LogUtils {
    public static String getLogTag(Object caller) {
        return caller.getClass().getSimpleName();
    }
}
