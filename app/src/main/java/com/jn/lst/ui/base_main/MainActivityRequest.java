package com.jn.lst.ui.base_main;

import com.google.gson.Gson;
import com.jn.lst.base.BaseView;
import com.jn.lst.base.Constants;
import com.jn.lst.base.DataEvent;
import com.jn.lst.base.UrlManager;
import com.jn.lst.base.bean.BaseBean;
import com.jn.lst.base.net.MyRequest;
import com.jn.lst.base.net.OnMyReuqestListener;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import okhttp3.Call;

/**
 * @Description: 主页
 */
public class MainActivityRequest extends MyRequest {
    private final String TAG = "MainActivityRequest";
    private BaseView mView;

    public MainActivityRequest(BaseView baseView) {
        mView = baseView;
    }

    /**
     * 主页 - 列表数据
     */
    public void getList(String clazz, int pageNo) {
        DataEvent.Type succType = DataEvent.Type.MAIN_LIST_SUCC;
        DataEvent.Type errType = DataEvent.Type.MAIN_LIST_ERR;
        String url = UrlManager.RequestUrl.mainListUrl;
        String des = "主页 - 列表数据";

        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.PAGE_NUMBER_KEY, pageNo);
        params.put(Constants.PAGE_SIZE_KEY, Constants.PAGE_SIZE_VALUE);

        mMyLog.d(TAG, des + " clazz  ------>> " + clazz);
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
                    MainActivityBean bean = new Gson().fromJson(data, MainActivityBean.class);
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
     * 主页 - 公告
     */
    public void getGongGao() {
        DataEvent.Type succType = DataEvent.Type.MAIN_GONG_GAO_SUCC;
        DataEvent.Type errType = DataEvent.Type.MAIN_GONG_GAO_ERR;
        String url = UrlManager.RequestUrl.gongGaoUrl;
        String des = "主页 - 公告";

        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.PAGE_SIZE_KEY, Constants.PAGE_SIZE_VALUE);

        mMyLog.d(TAG, des + " des    ------>> " + des);
        mMyLog.d(TAG, des + " url    ------>> " + url);
        mMyLog.d(TAG, des + " params ------>> " + new Gson().toJson(null));

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