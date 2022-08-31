package com.jn.lst.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.ivClear)
    ImageView ivClear;
    @BindView(R.id.tvSearch)
    TextView tvSearch;

    @BindView(R.id.mPullToRefreshLayout)
    PullToRefreshLayout mPullToRefreshLayout;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private SearchActivityAdapter mAdapter;

    private SearchActivityRequest mRequest;
    private boolean mCanLoadingMore = true;
    private int mPageNo = 1;
    private String mSearchStr = "";

    public static void open(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mRequest = new SearchActivityRequest(this);
    }

    @Override
    protected int attachLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        ivClear.setVisibility(View.GONE);
        tvSearch.setVisibility(View.GONE);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSearchStr = s.toString();
                if (TextUtils.isEmpty(mSearchStr)) {
                    ivClear.setVisibility(View.GONE);
                    tvSearch.setVisibility(View.GONE);
                    mSearchStr = "";
                } else {
                    ivClear.setVisibility(View.VISIBLE);
                    tvSearch.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(mAdapter = new SearchActivityAdapter(mActivity));
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                SearchActivityBean.DataBean bean = (SearchActivityBean.DataBean) adapter.getItem(position);
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
                mRequest.search2(mSearchStr, mPageNo);
            }

            @Override
            public void onLoadMore() {
                if (mCanLoadingMore) {
                    mPageNo++;
                    mRequest.search2(mSearchStr, mPageNo);
                } else {
                    mPullToRefreshLayout.finishLoadMore();
                    ToastUtils.showShort("没有更多数据了");
                }
            }
        });
    }

    @OnClick({R.id.ivBack, R.id.ivClear, R.id.tvSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivClear:
                etSearch.setText("");
                break;
            case R.id.tvSearch:
                mPullToRefreshLayout.autoRefresh();
                break;
        }
    }

    @Override
    protected void eventMsg(DataEvent event) {
        switch (event.type) {
            case SEARCH_SUCC:
                mPullToRefreshLayout.finishRefresh();
                mPullToRefreshLayout.finishLoadMore();

                SearchActivityBean bean = (SearchActivityBean) event.data;
                List<SearchActivityBean.DataBean> data = bean.getData();
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
            case SEARCH_ERR:
                mPullToRefreshLayout.finishRefresh();
                mPullToRefreshLayout.finishLoadMore();
                mPullToRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
                ToastUtils.showShort(event.data.toString());
                break;
        }
    }
}