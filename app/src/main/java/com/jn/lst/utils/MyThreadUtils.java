package com.jn.lst.utils;

import android.os.Handler;
import android.os.Looper;

public class MyThreadUtils {
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    // 在主线程中运行
    public static void runOnUiThread(Runnable r) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            //主线程
            r.run();
        } else {
            //子线程
            mHandler.post(r);
        }
    }

    // 在子线程中运行
    public static void runOnSubThread(Runnable r) {
        new Thread(r).start();
    }

    // 是否是主线程
    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
}
