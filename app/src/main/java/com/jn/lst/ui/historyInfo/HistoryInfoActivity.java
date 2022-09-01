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
 * @des: 文章详情
 * @Author:
 * @time: 2022年08月20日
 */
public class HistoryInfoActivity extends BaseActivity {
    // butterknife注解框架（ 替代findViewById(R.id.xxx) ）
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.mPullToRefreshLayout)
    PullToRefreshLayout mPullToRefreshLayout;

    @BindView(R.id.tvDes)
    TextView tvDes;

    private String mId;
    // 网络请求
    private HistoryInfoActivityRequest mRequest;

    /**
     * 当前界面启动函数
     * @param context 上下文
     * @param id 文章id
     */
    public static void open(Context context, String id) {
        Intent intent = new Intent(context, HistoryInfoActivity.class);
        // 打开新页面的时候传递参数
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        // 接收参数
        mId = getIntent().getStringExtra("id");
        // 实例化网络请求
        mRequest = new HistoryInfoActivityRequest(this);
    }

    @Override
    protected int attachLayout() {
        // 加载布局
        return R.layout.activity_history_info;
    }

    @Override
    protected void initView() {// 布局已经加载完成
        tvTitle.setText("详情");// 设置当前页面的标题
        mRequest.addZuJi(mId);// 网络请求 - 添加足迹，用于数据分析和算法推荐

        mPullToRefreshLayout.canRefresh(true);// 能否下拉刷新
        mPullToRefreshLayout.canLoadMore(false);// 能否上拉加载更多数据
        mPullToRefreshLayout.setOnRefreshListener(new OnRefreshListener() {// 下拉上拉监听器
            @Override
            public void onRefresh() {// 下拉的回调函数
                mRequest.getInfo(mId);// 网络请求 - 加载文章详情数据
            }

            @Override
            public void onLoadMore() {// 上拉的回调函数

            }
        });
        mPullToRefreshLayout.autoRefresh();// 自动刷新函数
    }

    // butterknife注解框架（ 替代setOnClicklistener（）函数 ）
    @OnClick({R.id.ivBack})
    public void onViewClicked(View view) {
        MySoftKeyboardUtils.closeSoftKeyboard(mActivity);// 关闭软键盘
        if (MyClickUtils.isDoubleClick()) {// 防止双击
            return;
        }
        switch (view.getId()) {
            case R.id.ivBack:// 返回按钮的点击事件
                finish();// 结束当前页面
                break;
        }
    }

    @Override
    protected void eventMsg(DataEvent event) {// EventBus的数据接收函数
        switch (event.type) {// 根据type类型接收对应的事件信息
            case INFO_SUCC:// 加载文章详情成功
                mPullToRefreshLayout.finishRefresh();// 结束刷新动画
                getInfoSucc(event);// 处理数据
                break;
            case INFO_ERR:// 加载文章详情失败
                mPullToRefreshLayout.finishRefresh();// 结束刷新动画
                mPullToRefreshLayout.showView(ViewStatus.EMPTY_STATUS);// 显示空布局
                ToastUtils.showShort(event.data.toString());// 显示提示信息
                break;
            case ADD_ZU_JI_SUCC:
                break;
            case ADD_ZU_JI_ERR:
                break;
        }
    }

    private void getInfoSucc(DataEvent event) {
        HistoryInfoActivityBean bean = (HistoryInfoActivityBean) event.data;// 将数据转成对应的javaBean
        HistoryInfoActivityBean.DataBean data = bean.getData();
        if (data == null) {// 判断数据是否为空
            ToastUtils.showShort("暂无数据");
            mPullToRefreshLayout.showView(ViewStatus.EMPTY_STATUS);// 显示空布局
            return;
        }

        String contents = data.getContents();// 获取富文本
        if (!TextUtils.isEmpty(contents)) {// 判断富文本是否为空
            // 富文本内容不为空
            mPullToRefreshLayout.showView(ViewStatus.CONTENT_STATUS);// 显示内容布局
            MovementMethod instance = LinkMovementMethod.getInstance();
            RichText.initCacheDir(mActivity);// 初始化富文本显示框架
            RichText.fromHtml(contents).into(tvDes);// 显示富文本
        } else {
            // 富文本内容为空
            ToastUtils.showShort("暂无数据");// 提示
            mPullToRefreshLayout.showView(ViewStatus.EMPTY_STATUS);// 显示空布局
        }
    }


}