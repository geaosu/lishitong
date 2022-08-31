package com.jn.lst.utils;

import android.util.Log;

import com.jn.lst.BuildConfig;

/**
 * 日志工具类
 */
public class MyLog {
    private static boolean debug = BuildConfig.DEBUG;
    private static String tag;

    public MyLog(String className) {
        tag = "------>> " + className;
    }

    public void i(String msg) {
        if (debug) {
            Log.i(tag, msg);
        }
    }

    public void i(String tag, String msg) {
        if (debug) {
            Log.i(tag, msg);
        }
    }

    public void d(String msg) {
        if (debug) {
            Log.d(tag, msg);
        }
    }

    public void d(String tag, String msg) {
        if (debug) {
            Log.d(tag, msg);
        }
    }

    public void e(String msg) {
        if (debug) {
            Log.e(tag, msg);
        }
    }

    public void e(String tag, String msg) {
        if (debug) {
            Log.e(tag, msg);
        }
    }

    public void w(String msg) {
        if (debug) {
            Log.w(tag, msg);
        }
    }

    public void w(String tag, String msg) {
        if (debug) {
            Log.w(tag, msg);
        }
    }
} 