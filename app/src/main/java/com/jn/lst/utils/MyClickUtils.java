package com.jn.lst.utils;

import java.util.Calendar;

/**
 * @Description:
 */
public class MyClickUtils {
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime = 0;

    public static boolean isDoubleClick() {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            return false;
        } else {
            return true;
        }
    }
}
