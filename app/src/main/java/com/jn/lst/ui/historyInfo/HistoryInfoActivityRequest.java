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
 * @Description: 详情
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
        DataEvent.Type succType = DataEvent.Type.INFO_SUCC;
        DataEvent.Type errType = DataEvent.Type.INFO_ERR;
        String url = UrlManager.RequestUrl.infoUrl;
        String des = "详情";

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
                    HistoryInfoActivityBean bean = new Gson().fromJson(data, HistoryInfoActivityBean.class);
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