package com.jn.lst.ui.historyEvent;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.itheima.roundedimageview.RoundedImageView;
import com.jn.lst.R;
import com.jn.lst.base.UrlManager;


/**
 * @Description: 主页
 */
public class HistoryEventListActivityAdapter extends BaseQuickAdapter<HistoryEventListActivityBean.DataBean, BaseViewHolder> {
    private Context mContext;
    private final String TAG = "HistoryEventListActivityAdapter";

    public HistoryEventListActivityAdapter(Context context) {
        super(R.layout.activity_history_event_list_item, null);
        this.mContext = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, HistoryEventListActivityBean.DataBean item) {
        try {
            RoundedImageView mRoundedImageView = (RoundedImageView) holder.getView(R.id.mRoundedImageView);
            TextView tvShiJianMingCheng = (TextView) holder.getView(R.id.tvShiJianMingCheng);
            TextView tvDes = (TextView) holder.getView(R.id.tvDes);
            TextView tvRecommend = (TextView) holder.getView(R.id.tvRecommend);
            tvRecommend.setVisibility(View.GONE);

            String title = item.getTitle();
            String contents = item.getContents();
            String description = item.getDescription();
            String url = item.getImage();
            String isRecommend = item.getIsRecommend();

            tvShiJianMingCheng.setText(title);
            tvDes.setText(description);

            if (!TextUtils.isEmpty(url)) {
                url = UrlManager.RequestUrl.baseUrl + url;
            }

            Log.d(TAG, "------>> url = " + url);
            RequestOptions options = new RequestOptions()
                    .error(R.mipmap.default_img_1)
                    .placeholder(R.mipmap.default_img_1);
            Glide.with(mContext)
                    .load(url)
                    .thumbnail(0.5f)
                    .apply(options)
                    .into(mRoundedImageView);

            if (!TextUtils.isEmpty(isRecommend)) {
                if (isRecommend.equals("Y")) {
                    tvRecommend.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            ToastUtils.showShort("数据异常");
        }
    }

}
