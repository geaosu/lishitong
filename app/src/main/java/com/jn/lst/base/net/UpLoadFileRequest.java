package com.jn.lst.base.net;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.jn.lst.base.BaseView;
import com.jn.lst.base.DataEvent;
import com.jn.lst.base.bean.UploadFileBean;
import com.jn.lst.base.UrlManager;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UpLoadFileRequest extends BaseModule {
    private final String TAG = "UpLoadFileRequest";
    private BaseView mView;

    public UpLoadFileRequest(BaseView baseView) {
        mView = baseView;
    }

    public void upLoadFile(File file) {
        DataEvent.Type succType = DataEvent.Type.FILE_UPLOAD_SUCC;
        DataEvent.Type errType = DataEvent.Type.FILE_UPLOAD_ERR;
        String url = UrlManager.RequestUrl.uploadFileUrl;
        String des = "主页 - 列表数据";

        LogUtils.d(TAG, des + " des ------>> " + url);
        LogUtils.d(TAG, des + " url ------>> " + url);
        LogUtils.d(TAG, des + " params ------>> " + file.getAbsolutePath());

        mView.showLoading();
        Call call = sendUpLoadFileRequest(url, file);
        if (call == null) {
            mView.cancelLoading();
            EventBus.getDefault().post(new DataEvent(errType, "网络请求错误，call为空"));
            return;
        }

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mView.cancelLoading();
                LogUtils.d(TAG, des + " err ------>> " + e.toString());
                EventBus.getDefault().post(new DataEvent(errType, e.toString()));
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String data = response.body().string();
                    LogUtils.d(TAG, des + "  data ------>> " + data);
                    UploadFileBean bean = new Gson().fromJson(data, UploadFileBean.class);
                    if (bean.isSuccess()) {
                        EventBus.getDefault().post(new DataEvent(succType, bean));
                    } else {
                        mView.cancelLoading();
                        EventBus.getDefault().post(new DataEvent(errType, bean.getMsg()));
                    }
                } catch (Exception e) {
                    mView.cancelLoading();
                    LogUtils.d(TAG, des + " err ------>> " + e.toString());
                    EventBus.getDefault().post(new DataEvent(errType, e.toString()));
                }
            }
        });

    }

}
