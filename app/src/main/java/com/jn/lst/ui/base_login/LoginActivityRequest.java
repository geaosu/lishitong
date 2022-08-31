package com.jn.lst.ui.base_login;

import com.google.gson.Gson;
import com.jn.lst.base.BaseView;
import com.jn.lst.base.DataEvent;
import com.jn.lst.base.UrlManager;
import com.jn.lst.base.bean.BaseBean;
import com.jn.lst.base.net.BaseModule;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * des: 登录
 */
public class LoginActivityRequest extends BaseModule {
    private final String TAG = "LoginActivityRequest";
    private BaseView mView;

    public LoginActivityRequest(BaseView baseView) {
        mView = baseView;
    }

    /**
     * 登录
     */
    public void login(String loginName, String password) {
        DataEvent.Type succType = DataEvent.Type.LOGIN_SUCC;
        DataEvent.Type errType = DataEvent.Type.LOGIN_ERR;
        String url = UrlManager.RequestUrl.loginUrl;
        String des = "登录";

        HashMap<String, Object> params = new HashMap<>();
        params.put("loginName", loginName);
        params.put("password", password);

        mMyLog.d(TAG, des + " des    ------>> " + des);
        mMyLog.d(TAG, des + " url    ------>> " + url);
        mMyLog.d(TAG, des + " params ------>> " + new Gson().toJson(params));

        mView.showLoading();
        Call call = sendPostRequest(url, params);
        if (call == null) {
            mView.cancelLoading();
            EventBus.getDefault().post(new DataEvent(errType, "网络请求错误，call为空"));
            return;
        }

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mView.cancelLoading();
                mMyLog.d(TAG, des + " err    ------>> " + e.toString());
                EventBus.getDefault().post(new DataEvent(errType, e.toString()));
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    mView.cancelLoading();
                    String data = response.body().string();
                    mMyLog.d(TAG, des + " data   ------>> " + data);
                    LoginActivityBean bean = new Gson().fromJson(data, LoginActivityBean.class);
                    if (bean.isSuccess()) {
                        EventBus.getDefault().post(new DataEvent(succType, bean));
                    } else {
                        EventBus.getDefault().post(new DataEvent(errType, bean.getMsg()));
                    }
                } catch (Exception e) {
                    mView.cancelLoading();
                    mMyLog.d(TAG, des + " err    ------>> " + e.toString());
                    EventBus.getDefault().post(new DataEvent(errType, e.toString()));
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
        Call call = sendPostRequest(url, params);
        if (call == null) {
            mView.cancelLoading();
            EventBus.getDefault().post(new DataEvent(errType, "网络请求错误，call为空"));
            return;
        }

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mView.cancelLoading();
                mMyLog.d(TAG, des + " err    ------>> " + e.toString());
                EventBus.getDefault().post(new DataEvent(errType, e.toString()));
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    mView.cancelLoading();
                    String data = response.body().string();
                    mMyLog.d(TAG, des + " data   ------>> " + data);
                    BaseBean bean = new Gson().fromJson(data, BaseBean.class);
                    if (bean.isSuccess()) {
                        EventBus.getDefault().post(new DataEvent(succType, bean));
                    } else {
                        EventBus.getDefault().post(new DataEvent(errType, bean.getMsg()));
                    }
                } catch (Exception e) {
                    mView.cancelLoading();
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
        Call call = sendPostRequest(url, params);
        if (call == null) {
            mView.cancelLoading();
            EventBus.getDefault().post(new DataEvent(errType, "网络请求错误，call为空"));
            return;
        }

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mView.cancelLoading();
                mMyLog.d(TAG, des + " err    ------>> " + e.toString());
                EventBus.getDefault().post(new DataEvent(errType, e.toString()));
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    mView.cancelLoading();
                    String data = response.body().string();
                    mMyLog.d(TAG, des + " data   ------>> " + data);
                    BaseBean bean = new Gson().fromJson(data, BaseBean.class);
                    if (bean.isSuccess()) {
                        EventBus.getDefault().post(new DataEvent(succType, bean));
                    } else {
                        EventBus.getDefault().post(new DataEvent(errType, bean.getMsg()));
                    }
                } catch (Exception e) {
                    mView.cancelLoading();
                    mMyLog.d(TAG, des + " err    ------>> " + e.toString());
                    EventBus.getDefault().post(new DataEvent(errType, e.toString()));
                }
            }
        });

    }

}