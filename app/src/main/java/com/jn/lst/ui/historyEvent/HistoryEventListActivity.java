package com.jn.lst.ui.historyEvent;

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
import com.jn.lst.ui.historyInfo.HistoryInfoActivity;
import com.jn.lst.ui.search.SearchActivity;
import com.jn.lst.utils.MyClickUtils;
import com.jn.lst.utils.MySoftKeyboardUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @des: 历史事件列表
 * @Author:
 * @time: 2022年08月20日
 */
public class HistoryEventListActivity extends BaseActivity {

    @BindView(R.id.llSearch)
    LinearLayout llSearch;

    @BindView(R.id.mPullToRefreshLayout)
    PullToRefreshLayout mPullToRefreshLayout;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private HistoryEventListActivityAdapter mAdapter;

    private HistoryEventListActivityRequest mRequest;
    private boolean mCanLoadingMore = true;
    private int mPageNo = 1;
    private String mId;
    private String mSearchStr = "";

    public static void open(Context context, String id) {
        Intent intent = new Intent(context, HistoryEventListActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mId = getIntent().getStringExtra("id");
        mRequest = new HistoryEventListActivityRequest(this);
    }

    @Override
    protected int attachLayout() {
        return R.layout.activity_history_event_list;
    }

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(mAdapter = new HistoryEventListActivityAdapter(mActivity));
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                HistoryEventListActivityBean.DataBean bean = (HistoryEventListActivityBean.DataBean) adapter.getItem(position);
                String id = bean.getId();
                if (TextUtils.isEmpty(id)) {
                    ToastUtils.showShort("id错误");
                    return;
                }
                HistoryInfoActivity.open(mActivity, id);
            }
        });

        mPullToRefreshLayout.canRefresh(true);
        mPullToRefreshLayout.canLoadMore(true);
        mPullToRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCanLoadingMore = true;
                mPageNo = 1;
                mRequest.getList2(mId, mSearchStr, mPageNo);
            }

            @Override
            public void onLoadMore() {
                if (mCanLoadingMore) {
                    mPageNo++;
                    mRequest.getList2(mId, mSearchStr, mPageNo);
                } else {
                    mPullToRefreshLayout.finishLoadMore();
                    ToastUtils.showShort("没有更多数据了");
                }
            }
        });
        mPullToRefreshLayout.autoRefresh();
    }

    @OnClick({R.id.ivBack, R.id.llSearch})
    public void onViewClicked(View view) {
        MySoftKeyboardUtils.closeSoftKeyboard(mActivity);
        if (MyClickUtils.isDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.llSearch:
                SearchActivity.open(mActivity);
                break;
        }
    }

    @Override
    protected void eventMsg(DataEvent event) {
        switch (event.type) {
            case HISTORY_EVENT_LIST_SUCC:
                mPullToRefreshLayout.finishRefresh();
                mPullToRefreshLayout.finishLoadMore();
//                List<HistoryTimeListActivityBean> list = new ArrayList<>();
//                list.add(new HistoryTimeListActivityBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                list.add(new HistoryTimeListActivityBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                list.add(new HistoryTimeListActivityBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                list.add(new HistoryTimeListActivityBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                list.add(new HistoryTimeListActivityBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                list.add(new HistoryTimeListActivityBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                list.add(new HistoryTimeListActivityBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                list.add(new HistoryTimeListActivityBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                list.add(new HistoryTimeListActivityBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                list.add(new HistoryTimeListActivityBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                list.add(new HistoryTimeListActivityBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                list.add(new HistoryTimeListActivityBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                list.add(new HistoryTimeListActivityBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                list.add(new HistoryTimeListActivityBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                list.add(new HistoryTimeListActivityBean("元朝", "时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服时代俊峰乐山大佛连接速度发历史地看杰弗里斯客服"));
//                mAdapter.setNewInstance(list);

                HistoryEventListActivityBean bean = (HistoryEventListActivityBean) event.data;
                List<HistoryEventListActivityBean.DataBean> data = bean.getData();
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
            case HISTORY_EVENT_LIST_ERR:
                mPullToRefreshLayout.finishRefresh();
                mPullToRefreshLayout.finishLoadMore();
                mPullToRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
                ToastUtils.showShort(event.data.toString());
                break;
        }
    }
}