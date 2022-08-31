package com.jn.lst.utils;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 软键盘工具类
 */
public class MySoftKeyboardUtils {
    private static final String TAG = "MySoftKeyboardUtils";

    /**
     * 清除输入状态
     */
    public static void requestFocus(View view) {
        try {
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
        } catch (Exception e) {
            Log.d(TAG, "------>> 发生异常: " + e.toString());
        }
    }

    /**
     * 清除输入状态
     */
    public static void closeFocus(EditText editText) {
        try {
            editText.clearFocus();// 取消焦点
        } catch (Exception e) {
            Log.d(TAG, "------>> 发生异常: " + e.toString());
        }
    }

    /**
     * 清除输入状态, 并关闭软键盘
     */
    public static void closeFocusAndSoftKeyboard(Activity activity, EditText editText) {
        try {
            editText.clearFocus();// 取消焦点
            InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);// 关闭输入法
        } catch (Exception e) {
            Log.d(TAG, "------>> 发生异常: " + e.toString());
        }
    }

    /**
     * 关闭软键盘
     */
    public static void closeSoftKeyboard(Activity activity) {
        try {
            InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);// 关闭输入法
        } catch (Exception e) {
            Log.d(TAG, "------>> 发生异常: " + e.toString());
        }
    }
}
