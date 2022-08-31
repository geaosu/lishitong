package com.jn.lst;

import android.app.Application;
import android.content.Context;

/**
 * Created by suancai on 2016/11/22.
 */

public class MyApplication extends Application {
    private static MyApplication instance;

    public Application getApplication() {
        return getInstance();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public synchronized static MyApplication getInstance() {
        while (instance == null) {
            try {
                MyApplication.class.wait();
            } catch (InterruptedException e) {
                break;
            }
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

}










