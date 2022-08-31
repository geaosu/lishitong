package com.jn.lst.widget.showpic;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jn.lst.R;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

/**
 * Des：显示大图
 * 功能介绍：数据适配器
 * <p>
 * <p>
 * <p>
 * 注意：这个是已经封装好的，别他妈手长
 * 注意：这个是已经封装好的，别他妈手长
 * 注意：这个是已经封装好的，别他妈手长
 */
public class ShowBigImageActivityViewPagerAdapter extends PagerAdapter {
    private List<ShowBigImageActivityBean> sList;

    public ShowBigImageActivityViewPagerAdapter(List<ShowBigImageActivityBean> list) {
        this.sList = list;
    }

    @Override
    public int getCount() {
        return sList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.show_big_image_viewpager_item_layout, null, false);

        PhotoView mPhotoView = itemView.findViewById(R.id.mPhotoView);
        RelativeLayout mLoading = itemView.findViewById(R.id.mLoading);
        LinearLayout mLoadingErr = itemView.findViewById(R.id.mLoadingErr);

        mLoading.setVisibility(View.VISIBLE);
        mLoadingErr.setVisibility(View.GONE);

        try {
            ShowBigImageActivityBean imageBean = sList.get(position);
            String imgPath = imageBean.getImgPath();
            String imgUrl = imageBean.getImgUrl();

            Log.d("------>>", "图片地址：" + imgPath);
            Log.d("------>>", "图片url：" + imgUrl);

            if (imgPath != null && !TextUtils.isEmpty(imgPath)) {
                mLoading.setVisibility(View.GONE);
                mLoadingErr.setVisibility(View.GONE);
                Glide
                        .with(container.getContext())
                        .load(imgPath)
                        .into(mPhotoView);
            } else if (imgUrl != null && !TextUtils.isEmpty(imgUrl)) {
                Glide.with(container.getContext())
                        .load(imgUrl)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                // 加载失败
                                mLoading.setVisibility(View.GONE);
                                mLoadingErr.setVisibility(View.VISIBLE);
                                return true;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                // 加载成功
                                mLoading.setVisibility(View.GONE);
                                mLoadingErr.setVisibility(View.GONE);
                                mPhotoView.setImageDrawable(resource);
                                return true;
                            }
                        })
                        .into(mPhotoView);
            } else {
                mLoading.setVisibility(View.GONE);
                mLoadingErr.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            mLoading.setVisibility(View.GONE);
            mLoadingErr.setVisibility(View.VISIBLE);
        }
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}