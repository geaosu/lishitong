package com.jn.lst.base.net;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.jn.lst.base.BaseView;
import com.jn.lst.base.DataEvent;
import com.jn.lst.base.UrlManager;
import com.jn.lst.base.bean.UploadFileBean;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

/**
 * @des: 上传文件 - 网络请求
 * @Author:
 * @time: 2022年08月20日
 */
public class UpLoadFileRequest extends MyRequest {
    private final String TAG = "UpLoadFileRequest";
    private BaseView mView;

    public UpLoadFileRequest(BaseView baseView) {
        mView = baseView;
    }

    public void upLoadFile(File file, DataEvent.Type succType, DataEvent.Type errType) {
        // DataEvent.Type succType = DataEvent.Type.FILE_UPLOAD_SUCC;
        // DataEvent.Type errType = DataEvent.Type.FILE_UPLOAD_ERR;
        String url = UrlManager.RequestUrl.uploadFileUrl;
        String des = "上传文件";

        LogUtils.d(TAG, des + " des ------>> " + url);
        LogUtils.d(TAG, des + " url ------>> " + url);
        LogUtils.d(TAG, des + " params ------>> " + file.getAbsolutePath());

        mView.showLoading();
        upLoadFileRequest(url, file, new OnMyReuqestListener() {
            @Override
            public void onFailure(String err) {
                mView.cancelLoading();
                EventBus.getDefault().post(new DataEvent(errType, "网络请求错误，call为空"));
            }

            @Override
            public void onResponse(String data) {
                try {
                    mView.cancelLoading();
                    LogUtils.d(TAG, des + "  data ------>> " + data);
                    UploadFileBean bean = new Gson().fromJson(data, UploadFileBean.class);
                    if (bean.isSuccess()) {
                        EventBus.getDefault().post(new DataEvent(succType, bean));
                    } else {
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
