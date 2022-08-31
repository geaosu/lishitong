package com.jn.lst.ui.historyEvent;

import com.google.gson.Gson;
import com.jn.lst.base.BaseView;
import com.jn.lst.base.Constants;
import com.jn.lst.base.DataEvent;
import com.jn.lst.base.UrlManager;
import com.jn.lst.base.net.MyRequest;
import com.jn.lst.base.net.OnMyReuqestListener;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * @Description: 主页
 */
public class HistoryEventListActivityRequest extends MyRequest {
    private final String TAG = "HistoryEventListActivityRequest";
    private BaseView mView;

    public HistoryEventListActivityRequest(BaseView baseView) {
        mView = baseView;
    }

    /**
     * 历史事件列表
     */
    public void getList2(String id, String searchStr, int pageNo) {
        DataEvent.Type succType = DataEvent.Type.HISTORY_EVENT_LIST_SUCC;
        DataEvent.Type errType = DataEvent.Type.HISTORY_EVENT_LIST_ERR;
        String url = UrlManager.RequestUrl.historyEventListUrl;
        String des = "历史事件列表";

        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.PAGE_NUMBER_KEY, pageNo);
        params.put(Constants.PAGE_SIZE_KEY, Constants.PAGE_SIZE_VALUE);
        params.put("eraId", id);
        params.put("search", searchStr);

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
                    HistoryEventListActivityBean bean = new Gson().fromJson(data, HistoryEventListActivityBean.class);
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