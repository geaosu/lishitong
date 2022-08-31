package com.jn.lst.ui.historyInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.geaosu.refreshx.OnRefreshListener;
import com.geaosu.refreshx.PullToRefreshLayout;
import com.geaosu.refreshx.ViewStatus;
import com.jn.lst.R;
import com.jn.lst.base.BaseActivity;
import com.jn.lst.base.DataEvent;
import com.jn.lst.utils.MyClickUtils;
import com.jn.lst.utils.MySoftKeyboardUtils;
import com.zzhoujay.richtext.RichText;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * des: 详情
 */
public class HistoryInfoActivity extends BaseActivity {
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.mPullToRefreshLayout)
    PullToRefreshLayout mPullToRefreshLayout;

    @BindView(R.id.tvDes)
    TextView tvDes;

    private String mId;
    private HistoryInfoActivityRequest mRequest;

    public static void open(Context context, String id) {
        Intent intent = new Intent(context, HistoryInfoActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mId = getIntent().getStringExtra("id");
        mRequest = new HistoryInfoActivityRequest(this);
    }

    @Override
    protected int attachLayout() {
        return R.layout.activity_history_info;
    }

    @Override
    protected void initView() {
        tvTitle.setText("详情");
        mRequest.addZuJi(mId);

        mPullToRefreshLayout.canRefresh(true);
        mPullToRefreshLayout.canLoadMore(false);
        mPullToRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRequest.getInfo(mId);
            }

            @Override
            public void onLoadMore() {

            }
        });
        mPullToRefreshLayout.autoRefresh();
    }

    @OnClick({R.id.ivBack})
    public void onViewClicked(View view) {
        MySoftKeyboardUtils.closeSoftKeyboard(mActivity);
        if (MyClickUtils.isDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
        }
    }

    @Override
    protected void eventMsg(DataEvent event) {
        switch (event.type) {
            case INFO_SUCC:
                mPullToRefreshLayout.finishRefresh();
                getInfoSucc(event);
                break;
            case INFO_ERR:
                mPullToRefreshLayout.finishRefresh();
                mPullToRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
                ToastUtils.showShort(event.data.toString());
                break;
            case ADD_ZU_JI_SUCC:
                break;
            case ADD_ZU_JI_ERR:
                break;
        }
    }

    private void getInfoSucc(DataEvent event) {
        HistoryInfoActivityBean bean = (HistoryInfoActivityBean) event.data;
        HistoryInfoActivityBean.DataBean data = bean.getData();
        if (data == null) {
            ToastUtils.showShort("暂无数据");
            mPullToRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
            return;
        }

        String contents = data.getContents();
        if (!TextUtils.isEmpty(contents)) {
            mPullToRefreshLayout.showView(ViewStatus.CONTENT_STATUS);
            MovementMethod instance = LinkMovementMethod.getInstance();
            RichText.initCacheDir(mActivity);
            RichText.fromHtml(contents).into(tvDes);
        } else {
            ToastUtils.showShort("暂无数据");
            mPullToRefreshLayout.showView(ViewStatus.EMPTY_STATUS);
        }
    }


}