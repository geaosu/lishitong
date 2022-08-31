package com.jn.lst.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jn.lst.utils.MyLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Fragment基类
 */
public abstract class BaseFragment extends Fragment implements BaseView {
    protected MyLog mMyLog = new MyLog(this.getClass().getSimpleName());
    protected Fragment mFragment;
    protected Activity mActivity;
    protected Context mContext;
    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        mFragment = this;
        mActivity = getActivity();
        mContext = getContext();
        init(savedInstanceState);
        mMyLog.i("------>> " + this.getClass().getSimpleName());
        View view = inflater.inflate(attachLayout(), container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        initView(view);
    }

    @Override
    public void onDetach() {
        EventBus.getDefault().unregister(this);
        mUnbinder.unbind();
        super.onDetach();
    }

    /**
     * 加载布局
     */
    protected abstract int attachLayout();

    /**
     * 数据初始化，在attachLayout方法之前调用
     */
    protected abstract void init(Bundle savedInstanceState);

    /**
     * 初始化控件
     */
    protected abstract void initView(View view);


    protected abstract void eventMsg(DataEvent event);

    /**
     * EventBus 数据和事件分发
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(DataEvent event) {
        switch (event.type) {
            case NET_WORK_ERR:
                cancelLoading();
                ToastUtils.showShort(event.data.toString());
                break;
            default:
                eventMsg(event);
                break;
        }
    }

    /***************************************** 加载动画 *********************************************/
    /***************************************** 加载动画 *********************************************/

    public void showLoading() {
        showLoading("");
    }

    public void showLoading(String msg) {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GlobalDialogManager.getInstance().show(getFragmentManager(), msg);
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
//        iosLoadingDialog.show(getFragmentManager(), "AndroidLoadingDialog");
//    }
//
//    public void showIOSDialog() {
//        IOSLoadingDialog iosLoadingDialog = new IOSLoadingDialog().setOnTouchOutside(true);
//        iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");
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
