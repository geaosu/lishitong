package com.jn.lst.ui.search;

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
 * @Description: 搜索
 */
public class SearchActivityRequest extends MyRequest {
    private final String TAG = "SearchActivityRequest";
    private BaseView mView;

    public SearchActivityRequest(BaseView baseView) {
        mView = baseView;
    }

    /**
     * 搜索
     */
    public void search2(String searchStr, int pageNo) {
        DataEvent.Type succType = DataEvent.Type.SEARCH_SUCC;
        DataEvent.Type errType = DataEvent.Type.SEARCH_ERR;
        String url = UrlManager.RequestUrl.searchUrl;
        String des = "搜索";

        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.PAGE_NUMBER_KEY, pageNo);
        params.put(Constants.PAGE_SIZE_KEY, Constants.PAGE_SIZE_VALUE);
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
                    SearchActivityBean bean = new Gson().fromJson(data, SearchActivityBean.class);
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