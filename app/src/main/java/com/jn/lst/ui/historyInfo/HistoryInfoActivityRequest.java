package com.jn.lst.ui.historyInfo;

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
 * @des: 文章详情 - 网络请求
 * @Author:
 * @time: 2022年08月20日
 */
public class HistoryInfoActivityRequest extends MyRequest {
    private final String TAG = "HistoryInfoActivityRequest";
    private BaseView mView;

    public HistoryInfoActivityRequest(BaseView baseView) {
        mView = baseView;
    }

    /**
     * 详情
     */
    public void getInfo(String id) {
        DataEvent.Type succType = DataEvent.Type.INFO_SUCC;// 请求成功的type
        DataEvent.Type errType = DataEvent.Type.INFO_ERR;// 请求失败的type
        String url = UrlManager.RequestUrl.infoUrl;// 请求地址
        String des = "详情";// 请求说明

        HashMap<String, Object> params = new HashMap<>();// 参数
        params.put("contentsId", id);// 添加参数名称和参数值

        // 日志输出
        mMyLog.d(TAG, des + " des    ------>> " + des);
        mMyLog.d(TAG, des + " url    ------>> " + url);
        mMyLog.d(TAG, des + " params ------>> " + new Gson().toJson(params));

        // 发送post请求
        sendMyPostRequest(url, params, new OnMyReuqestListener() {
            @Override
            public void onFailure(String err) {// 请求失败
                // 日志打印失败信息
                mMyLog.d(TAG, des + " err    ------>> " + err);
                // EventBus发送失败信息
                EventBus.getDefault().post(new DataEvent(errType, err));
            }

            @Override
            public void onResponse(String data) {
                // 请求成功
                try {
                    // 日志打印成功信息
                    mMyLog.d(TAG, des + " data   ------>> " + data);
                    // 用Gson框架将json转化为javaBean
                    HistoryInfoActivityBean bean = new Gson().fromJson(data, HistoryInfoActivityBean.class);
                    // 判断是否请求成功
                    if (bean.isSuccess()) {
                        // 请求成功 - EventBus发送成功信息
                        EventBus.getDefault().post(new DataEvent(succType, bean));
                    } else {
                        // 请求失败 - EventBus发送失败信息
                        EventBus.getDefault().post(new DataEvent(errType, bean.getMsg()));
                    }
                } catch (Exception e) {// 异常信息捕获
                    // 日志打印异常信息
                    mMyLog.d(TAG, des + " err    ------>> " + e.toString());
                    // EventBus发送异常信息
                    EventBus.getDefault().post(new DataEvent(errType, e.toString()));
                }
            }
        });
    }

    /**
     * 添加足迹
     */
    public void addZuJi(String id) {
        DataEvent.Type succType = DataEvent.Type.ADD_ZU_JI_SUCC;
        DataEvent.Type errType = DataEvent.Type.ADD_ZU_JI_ERR;
        String url = UrlManager.RequestUrl.addZuJiUrl;
        String des = "添加足迹";

        HashMap<String, Object> params = new HashMap<>();
        params.put("contentsId", id);

        mMyLog.d(TAG, des + " des    ------>> " + des);
        mMyLog.d(TAG, des + " url    ------>> " + url);
        mMyLog.d(TAG, des + " params ------>> " + new Gson().toJson(params));

        sendMyPostRequest(url, params, new OnMyReuqestListener() {
            @Override
            public void onFailure(String err) {
                mMyLog.d(TAG, des + " err    ------>> " + err);
                EventBus.getDefault().post(new DataEvent(errType, err));
            }

            @Override
            public void onResponse(String data) {
                try {
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