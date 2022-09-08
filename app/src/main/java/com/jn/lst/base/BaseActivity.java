package com.jn.lst.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.geaosu.mydialog.MyDialog;
import com.geaosu.mydialog.OnMyDialogClickListener;
import com.jn.lst.base.token.TokenManager;
import com.jn.lst.base.user.UserManager;
import com.jn.lst.ui.base_login.LoginActivity;
import com.jn.lst.utils.MyLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;



/**
 * @des: activity基类
 * @Author:
 * @time: 2022年08月20日
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    protected MyLog mMyLog = new MyLog(this.getClass().getSimpleName());
    protected Activity mActivity;
    protected Context mContext;
    protected String mTAG = this.getClass().getSimpleName();
    public boolean mIsShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);// 透明状态栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);// 透明导航栏
        }

        EventBus.getDefault().register(this);
        mActivity = this;
        mContext = getApplicationContext();
        mMyLog.i("------>> " + this.getClass().getSimpleName());
        init(savedInstanceState);// 程序初始化
        setContentView(attachLayout());// 加载布局
        ButterKnife.bind(this);// 调用注解框架
        initView();// 布局加载完成，初始化
    }

    /**
     * 1. 在setContentView方法之前调用；
     * 2. 恢复保存的数据
     * 3. 解析上个页面传递过来的数据
     */
    protected abstract void init(Bundle savedInstanceState);

    /**
     * 加载布局
     */
    protected abstract int attachLayout();

    /**
     * 初始化view
     */
    protected abstract void initView();

    @Override
    protected void onResume() {
        super.onResume();
        mIsShow = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsShow = false;
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(DataEvent event) {
        switch (event.type) {
            case TOKEN_BE_OVERDUE:
                cancelLoading();
                TokenManager.removeToken();
                UserManager.removeUser();
                if (mIsShow) {
                    MyDialog.showTips(mActivity, event.data.toString(), new OnMyDialogClickListener() {
                        @Override
                        public void onConfirmClick() {
                            LoginActivity.open(mActivity);
                            finish();
                        }
                    });
                } else {
                    finish();
                }
                break;
            case NET_WORK_ERR:
                cancelLoading();
                ToastUtils.showShort(event.data.toString());
                break;
//            default:
//                break;
        }
        eventMsg(event);
    }

    protected abstract void eventMsg(DataEvent event);

    /***************************************** 加载动画 *********************************************/
    /***************************************** 加载动画 *********************************************/

    public void showLoading() {
        showLoading("");
    }

    public void showLoading(String msg) {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GlobalDialogManager.getInstance().show(getSupportFragmentManager(), msg);
            }
        });
    }

    public void cancelLoading() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GlobalDialogManager.getInstance().dismiss();
            }
        });
    }


//    // 加载动画 - 显示
//    public void showAndroidDialog() {
//        AndroidLoadingDialog iosLoadingDialog = new AndroidLoadingDialog().setOnTouchOutside(true);
//        iosLoadingDialog.show(getSupportFragmentManager(), "AndroidLoadingDialog");
//    }
//
//    public void showIOSDialog() {
//        IOSLoadingDialog iosLoadingDialog = new IOSLoadingDialog().setOnTouchOutside(true);
//        iosLoadingDialog.show(getSupportFragmentManager(), "iosLoadingDialog");
//    }

    /***************************************** 加载动画 *********************************************/
    /***************************************** 加载动画 *********************************************/

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        try {
            Resources resources = context.getResources();
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            int height = resources.getDimensionPixelSize(resourceId);
            return height;
        } catch (Exception e) {
            return 0;
        }
    }

}