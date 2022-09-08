package com.jn.lst.ui.base_login;

import com.google.gson.Gson;
import com.jn.lst.base.BaseView;
import com.jn.lst.base.DataEvent;
import com.jn.lst.base.UrlManager;
import com.jn.lst.base.bean.BaseBean;
import com.jn.lst.base.net.MyRequest;
import com.jn.lst.base.net.OnMyReuqestListener;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * @des: 登录 - 网络请求
 * @Author:
 * @time: 2022年08月20日
 */
public class LoginActivityRequest extends MyRequest {
    private final String TAG = "LoginActivityRequest";
    private BaseView mView;

    public LoginActivityRequest(BaseView baseView) {
        mView = baseView;
    }

    /**
     * 登录
     */
    public void login(String loginName, String password) {
        DataEvent.Type succType = DataEvent.Type.LOGIN_SUCC;// 请求成功时的数据分发标识
        DataEvent.Type errType = DataEvent.Type.LOGIN_ERR;// 请求失败时的数据分发标识
        String url = UrlManager.RequestUrl.loginUrl;// 请求地址
        String des = "登录";

        HashMap<String, Object> params = new HashMap<>();
        params.put("loginName", loginName);
        params.put("password", password);

        mMyLog.d(TAG, des + " des    ------>> " + des);
        mMyLog.d(TAG, des + " url    ------>> " + url);
        mMyLog.d(TAG, des + " params ------>> " + new Gson().toJson(params));

        mView.showLoading();
        sendMyPostRequest(url, params, new OnMyReuqestListener() {
            @Override
            public void onFailure(String err) {
                // 请求失败
                mView.cancelLoading();// 隐藏加载动画
                mMyLog.d(TAG, des + " err    ------>> " + err);// 打印日志
                EventBus.getDefault().post(new DataEvent(errType, err));// 通知activity更新UI
            }

            @Override
            public void onResponse(String data) {
                try {
                    // 请求成功
                    mView.cancelLoading();// 隐藏加载动画
                    mMyLog.d(TAG, des + " data   ------>> " + data);// 打印日志
                    // 将服务器返回的json数据转成javaBean
                    LoginActivityBean bean = new Gson().fromJson(data, LoginActivityBean.class);
                    if (bean.isSuccess()) {
                        EventBus.getDefault().post(new DataEvent(succType, bean));// 通知activity更新UI
                    } else {
                        EventBus.getDefault().post(new DataEvent(errType, bean.getMsg()));// 通知activity更新UI
                    }
                } catch (Exception e) {
                    mMyLog.d(TAG, des + " err    ------>> " + e.toString());// 打印日志
                    EventBus.getDefault().post(new DataEvent(errType, e.toString()));// 通知activity更新UI
                }
            }
        });
    }

    /**
     * 检测账号是否被注册
     */
    public void check(String loginName) {
        DataEvent.Type succType = DataEvent.Type.REGISTER_CHECK_SUCC;
        DataEvent.Type errType = DataEvent.Type.REGISTER_CHECK_ERR;
        String url = UrlManager.RequestUrl.registerCheckUrl;
        String des = "注册 - 检测账号是否被注册";

        HashMap<String, Object> params = new HashMap<>();
        params.put("loginName", loginName);

        mMyLog.d(TAG, des + " des    ------>> " + des);
        mMyLog.d(TAG, des + " url    ------>> " + url);
        mMyLog.d(TAG, des + " params ------>> " + new Gson().toJson(params));

        mView.showLoading();
        sendMyPostRequest(url, params, new OnMyReuqestListener() {
            @Override
            public void onFailure(String err) {
                mView.cancelLoading();
                mMyLog.d(TAG, des + " err    ------>> " + err);
                EventBus.getDefault().post(new DataEvent(errType, err));
            }

            @Override
            public void onResponse(String data) {
                try {
                    mView.cancelLoading();
                    mMyLog.d(TAG, des + " data   ------>> " + data);
                    BaseBean bean = new Gson().fromJson(data, BaseBean.class);
                    if (bean.isSuccess()) {
                        EventBus.getDefault().post(new DataEvent(succType, bean));
                    } else {
                        EventBus.getDefault().post(new DataEvent(errType, bean.getMsg()));
                    }
                } catch (Exception e) {
                    mMyLog.d(TAG, des + " err    ------>> " + e.toString());
                    EventBus.getDefault().post(new DataEvent(errType, e.toString()));
                }
            }
        });
    }

    /**
     * 注册
     */
    public void register(String name, String loginName, String password, String checkPassword) {
        DataEvent.Type succType = DataEvent.Type.REGISTER_SUCC;
        DataEvent.Type errType = DataEvent.Type.REGISTER_ERR;
        String url = UrlManager.RequestUrl.registerUrl;
        String des = "注册";

        HashMap<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("loginName", loginName);
        params.put("password", password);
        params.put("checkPassword", checkPassword);

        mMyLog.d(TAG, des + " des    ------>> " + des);
        mMyLog.d(TAG, des + " url    ------>> " + url);
        mMyLog.d(TAG, des + " params ------>> " + new Gson().toJson(params));

        mView.showLoading();
        sendMyPostRequest(url, params, new OnMyReuqestListener() {
            @Override
            public void onFailure(String err) {
                mView.cancelLoading();
                mMyLog.d(TAG, des + " err    ------>> " + err);
                EventBus.getDefault().post(new DataEvent(errType, err));
            }

            @Override
            public void onResponse(String data) {
                try {
                    mView.cancelLoading();
                    mMyLog.d(TAG, des + " data   ------>> " + data);
                    BaseBean bean = new Gson().fromJson(data, BaseBean.class);
                    if (bean.isSuccess()) {
                        EventBus.getDefault().post(new DataEvent(succType, bean));
                    } else {
                        EventBus.getDefault().post(new DataEvent(errType, bean.getMsg()));
                    }
                } catch (Exception e) {
                    mMyLog.d(TAG, des + " err    ------>> " + e.toString());
                    EventBus.getDefault().post(new DataEvent(errType, e.toString()));
                }
            }
        });
    }

}