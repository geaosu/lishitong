package com.jn.lst.base.net;

/**
 * @des: 网络请求 - 封装中间层 - 监听器
 * @Author:
 * @time: 2022年08月20日
 */
public interface OnMyReuqestListener {
    /**
     * 请求失败 - 回调
     *
     * @param err 错误信息
     */
    void onFailure(String err);

    /**
     * 请求成功 - 回调
     *
     * @param data json数据
     */
    void onResponse(String data);
}
