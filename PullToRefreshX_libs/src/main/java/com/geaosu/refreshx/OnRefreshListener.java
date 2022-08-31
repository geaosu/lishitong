package com.geaosu.refreshx;

/**
 * 刷新回调监听器
 */
public interface OnRefreshListener {
    /**
     * 刷新
     */
    void onRefresh();

    /**
     * 加载更多
     */
    void onLoadMore();
}
