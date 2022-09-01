package com.jn.lst.ui.base_main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.geaosu.refreshx.OnRefreshListener;
import com.geaosu.refreshx.PullToRefreshLayout;
import com.geaosu.refreshx.ViewStatus;
import com.jn.lst.R;
import com.jn.lst.base.BaseActivity;
import com.jn.lst.base.Constants;
import com.jn.lst.base.DataEvent;
import com.jn.lst.ui.historyEvent.HistoryEventListActivity;
import com.jn.lst.ui.search.SearchActivity;
import com.jn.lst.utils.MyClickUtils;
import com.jn.lst.utils.MySoftKeyboardUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @des: 主界面
 * @Author:
 * @time: 2022年08月20日
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.llSearch)
    LinearLayout llSearch;

    @BindView(R.id.mPullToRefreshLayout)
    PullToRefreshLayout mPullToRefreshLayout;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private MainActivityAdapter mAdapter;

    private MainActivityRequest mRequest;
    private boolean mCanLoadingMore = true;
    private int mPageNo = 1;

    public static void open(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mRequest = new MainActivityRequest(this);
    }

    @Override
    protected int attachLayout() {
        return R.layout.base_main;
    }

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(mAdapter = new MainActivityAdapter(mActivity));
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                MainActivityBean.DataBean bean = (MainActivityBean.DataBean) adapter.getItem(position);
                String id = String.valueOf(bean.getId());
                if (TextUtils.isEmpty(id)) {
                    ToastUtils.showShort("id错误");
                    return;
                }
                HistoryEventListActivity.open(mActivity, id);
            }
        });

        mPullToRefreshLayout.canRefresh(true);
        mPullToRefreshLayout.canLoadMore(true);
        mPullToRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCanLoadingMore = true;
                mPageNo = 1;
                mRequest.getList(mTAG, mPageNo);
            }

            @Override
            public void onLoadMore() {
                if (mCanLoadingMore) {
                    mPageNo++;
                    mRequest.getList(mTAG, mPageNo);
                } else {
                    mPullToRefreshLayout.finishLoadMore();
                    ToastUtils.showShort("没有更多数据了");
                }
            }
        });
        mPullToRefreshLayout.autoRefresh();
    }

    @OnClick({R.id.llSearch})
    public void onViewClicked(View view) {
        MySoftKeyboardUtils.closeSoftKeyboard(mActivity);
        if (MyClickUtils.isDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.llSearch:
                SearchActivity.open(mActivity);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRequest.getGongGao();
    }

    @Override
    protected void eventMsg(DataEvent event) {
        switch (event.type) {
            case MAIN_LIST_SUCC:
                mPullToRefreshLayout.finishRefresh();
                mPullToRefreshLayout.finishLoadMore();


//                List<MainActivityBean.DataBean> list = new ArrayList<>();
//                list.add(new MainActivityBean.DataBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                list.add(new MainActivityBean.DataBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                list.add(new MainActivityBean.DataBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                list.add(new MainActivityBean.DataBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                list.add(new MainActivityBean.DataBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                list.add(new MainActivityBean.DataBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                list.add(new MainActivityBean.DataBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                list.add(new MainActivityBean.DataBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                list.add(new MainActivityBean.DataBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                list.add(new MainActivityBean.DataBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                list.add(new MainActivityBean.DataBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                list.add(new MainActivityBean.DataBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                list.add(new MainActivityBean.DataBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                list.add(new MainActivityBean.DataBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                list.add(new MainActivityBean.DataBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                mAdapter.setNewInstance(list);

                MainActivityBean bean = (MainActivityBean) event.data;
                List<MainActivityBean.DataBean> data = bean.getData();
                if (data != null && data.size() > 0) {
                    mPullToRefreshLayout.showView(ViewStatus.CONTENT_STATUS);
                    if (mPageNo == 1) {
                        mAdapter.setNewInstance(data);
                    } else {
                        mAdapter.addData(data);
                    }
                    if (data.size() >= Constants.PAGE_SIZE_VALUE) {
                        mCanLoadingMore = true;
                    } else {
                        mCanLoadingMore = false;
                    }
                } else {
                    mPullToRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
                    ToastUtils.showShort("没有数据了");
                }
                break;
            case MAIN_LIST_ERR:
                mPullToRefreshLayout.finishRefresh();
                mPullToRefreshLayout.finishLoadMore();
                mPullToRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
                ToastUtils.showShort(event.data.toString());
                break;
            case MAIN_GONG_GAO_SUCC:
                break;
            case MAIN_GONG_GAO_ERR:
                // ToastUtils.showShort(event.data.toString());
                break;

        }
    }
}