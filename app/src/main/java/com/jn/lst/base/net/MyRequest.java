package com.jn.lst.base.net;

import com.google.gson.Gson;
import com.jn.lst.base.DataEvent;
import com.jn.lst.base.bean.BaseBean;
import com.jn.lst.utils.MyLog;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @des: 网络请求 - 封装中间层
 * @Author:
 * @time: 2022年08月20日
 */
public abstract class MyRequest {
    // 日志工具
    protected MyLog mMyLog = new MyLog(this.getClass().getSimpleName());
    private BaseModule mBaseModule;

    public MyRequest() {
        this.mBaseModule = new BaseModule();
    }

    /**
     * POST - 不带参数
     *
     * @param url      请求地址
     * @param listener 监听器
     */
    public void sendMyPostRequest(String url, OnMyReuqestListener listener) {
        sendMyPostRequest(url, null, listener);
    }

    /**
     * POST - 带参数
     *
     * @param url      请求地址
     * @param params   参数
     * @param listener 监听器
     */
    public void sendMyPostRequest(String url, HashMap<String, Object> params, OnMyReuqestListener listener) {
        Call call = mBaseModule.sendPostRequest(url, params);
        if (call == null) {
            if (listener != null) {
                listener.onFailure("网络请求错误，请重试");
            }
            return;
        }

        // 异步发送新的网络请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (listener != null) {
                    listener.onFailure(e.toString());
                }
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String data = response.body().string();
                    if (data == null) {
                        if (listener != null) {
                            listener.onFailure("数据错误");
                        }
                    } else {
                        BaseBean bean = new Gson().fromJson(data, BaseBean.class);
                        if (bean != null) {
                            if (bean.getCode() == 401) {// token 过期
//                            if (bean.getCode() == 200) {// 成功
                                // token过期
                                EventBus.getDefault().post(new DataEvent(DataEvent.Type.TOKEN_BE_OVERDUE, bean.getMsg()));
                                return;
                            }
                            if (listener != null) {
                                listener.onResponse(data);
                            }
                        } else {
                            if (listener != null) {
                                listener.onFailure("数据错误");
                            }
                        }
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onFailure(e.toString());
                    }
                }
            }
        });

    }

    /**
     * 上传文件
     *
     * @param url      上传地址
     * @param file     文件
     * @param listener 监听器
     */
    public void upLoadFileRequest(String url, File file, OnMyReuqestListener listener) {
        Call call = mBaseModule.sendUpLoadFileRequest(url, file);
        if (call == null) {
            if (listener != null) {
                listener.onFailure("网络请求错误，请重试");
            }
            return;
        }

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (listener != null) {
                    listener.onFailure(e.toString());
                }
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String data = response.body().string();
                    if (data == null) {
                        if (listener != null) {
                            listener.onFailure("数据错误");
                        }
                    } else {
                        BaseBean bean = new Gson().fromJson(data, BaseBean.class);
                        if (bean != null) {
                            if (bean.getCode() == 401) {// token 过期
//                            if (bean.getCode() == 200) {// 成功
                                // token过期
                                EventBus.getDefault().post(new DataEvent(DataEvent.Type.TOKEN_BE_OVERDUE, bean.getMsg()));
                                return;
                            }
                            if (listener != null) {
                                listener.onResponse(data);
                            }
                        } else {
                            if (listener != null) {
                                listener.onFailure("数据错误");
                            }
                        }
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onFailure(e.toString());
                    }
                }
            }
        });

    }

}