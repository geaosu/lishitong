package com.jn.lst.base;



import androidx.fragment.app.FragmentManager;

import com.loading.dialog.AndroidLoadingDialog;

/**
 * @des: dialog
 * @Author:
 * @time: 2022年08月20日
 */
public class GlobalDialogManager {

    private AndroidLoadingDialog mLoadingDialog;
    private boolean mIsShow;//是否显示

    private GlobalDialogManager() {
        init();
    }

    public static GlobalDialogManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static GlobalDialogManager INSTANCE = new GlobalDialogManager();
    }

    public void init() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new AndroidLoadingDialog();
        }
    }

    /**
     * 展示加载框
     */
    public synchronized void show(FragmentManager manager, String msg) {
        if (manager != null && mLoadingDialog != null && !mIsShow) {
            mLoadingDialog.showAllowingStateLoss(manager, "loadingDialog", msg);
            mIsShow = true;
        }
    }

    /**
     * 隐藏加载框
     */
    public synchronized void dismiss() {
        if (mLoadingDialog != null && mIsShow) {
            mLoadingDialog.dismissAllowingStateLoss();
            mIsShow = false;
        }

    }
}
