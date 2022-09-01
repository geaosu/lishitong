package com.jn.lst.ui.base_main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.itheima.roundedimageview.RoundedImageView;
import com.jn.lst.R;
import com.jn.lst.base.UrlManager;


/**
 * @des: 主界面 - 数据适配器
 * @Author:
 * @time: 2022年08月20日
 */
public class MainActivityAdapter extends BaseQuickAdapter<MainActivityBean.DataBean, BaseViewHolder> {
    private Context mContext;
    private final String TAG = "HistoryEventListActivityAdapter";

    public MainActivityAdapter(Context context) {
        super(R.layout.base_main_list_item, null);
        this.mContext = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, MainActivityBean.DataBean item) {
        try {
            RoundedImageView mRoundedImageView = (RoundedImageView) holder.getView(R.id.mRoundedImageView);
            TextView tvChaoDai = (TextView) holder.getView(R.id.tvChaoDai);
            TextView tvDes = (TextView) holder.getView(R.id.tvDes);

            String name = item.getName();
            String des = item.getDescription();
            String url = item.getRecomImage();

            tvChaoDai.setText(name);

            if (!TextUtils.isEmpty(url)) {
                url = UrlManager.RequestUrl.baseUrl + url;
            }

            tvDes.setText(des); Log.d(TAG, "------>> url = " + url);
            RequestOptions options = new RequestOptions()
                    .error(R.mipmap.default_img_1)
                    .placeholder(R.mipmap.default_img_1);
            Glide.with(mContext)
                    .load(url)
                    .thumbnail(0.5f)
                    .apply(options)
                    .into(mRoundedImageView);
        } catch (Exception e) {
            ToastUtils.showShort("数据异常");
        }
    }

}
