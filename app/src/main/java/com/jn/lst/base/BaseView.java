package com.jn.lst.base;

/**
 * @des: V的基类
 * @Author:
 * @time: 2022年08月20日
 */
public interface BaseView {
    void showLoading();
    void showLoading(String msg);
    void cancelLoading();
}
