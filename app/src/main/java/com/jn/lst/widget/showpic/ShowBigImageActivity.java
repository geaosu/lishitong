package com.jn.lst.widget.showpic;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ToastUtils;
import com.jn.lst.R;

import java.util.ArrayList;

/*============================ 使用说明 ==============================
============================== 使用说明 ==============================
============================== 使用说明 ==============================

当前页面用到的开源库
    // 使用当前页面需要的依赖
    implementation 'com.github.bumptech.glide:glide:4.9.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.9.0'
    // PhotoView 显示大图
    implementation 'com.github.chrisbanes:PhotoView:2.0.0'
    // SpinKit 加载动画
    implementation 'com.github.ybq:Android-SpinKit:1.0.1'



    // 准备数据
    ArrayList<ImageBean> imgList = new ArrayList<>();
    for (AudioListAdapterBean item : mVideoListAdapterList) {
        String filePath = item.getFilePath();   // 这个是本地图片路径
        String fileUrl = item.getFileUrl();     // 这个是网络图片地址
        String fileName = item.getFileName();   // 图片名称或者标题

        ImageBean imageBean = new ImageBean(filePath, fileUrl, fileName);
        imgList.add(imageBean);
    }
    // 启动activity
    ShowBigImageActivity.open(this, imgList, 0);

============================== 使用说明 ==============================
============================== 使用说明 ==============================
============================== 使用说明 ============================*/

/**
 * Des：显示大图
 * 功能介绍：支持显示本地图片和网络上的图片
 * <p>
 * <p>
 * <p>
 * 注意：这个是已经封装好的，别他妈手长
 * 注意：这个是已经封装好的，别他妈手长
 * 注意：这个是已经封装好的，别他妈手长
 */
public class ShowBigImageActivity extends AppCompatActivity {

    private ShowBigImageActivity mActivity;
    private ShowBigImageActivityViewPager myViewPager;
    private ImageView ivBack;
    private TextView tvHint;

    //集合传递
    private ArrayList<ShowBigImageActivityBean> picUrlList = new ArrayList<>();
    private int mCurrentPosition = 0;
    private int mTotalCount = 0;


    public static void open(Activity activity, ArrayList<ShowBigImageActivityBean> data, int position) {
        Intent intent = new Intent(activity, ShowBigImageActivity.class);
        intent.putParcelableArrayListExtra("data", data);
        intent.putExtra("position", position);
        intent.putExtra("run", true);
        activity.startActivity(intent);
    }

    public static void open(Fragment fragment, ArrayList<ShowBigImageActivityBean> data, int position) {
        Intent intent = new Intent(fragment.getActivity(), ShowBigImageActivity.class);
        intent.putParcelableArrayListExtra("data", data);
        intent.putExtra("position", position);
        intent.putExtra("run", true);
        fragment.startActivity(intent);
    }

    public static void open(Context context, ArrayList<ShowBigImageActivityBean> data, int position) {
        Intent intent = new Intent(context, ShowBigImageActivity.class);
        intent.putParcelableArrayListExtra("data", data);
        intent.putExtra("position", position);
        intent.putExtra("run", true);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_big_image);

        ArrayList<ShowBigImageActivityBean> dataList = getIntent().getParcelableArrayListExtra("data");
        int dataPosition = getIntent().getIntExtra("position", 0);
        boolean run = getIntent().getBooleanExtra("run", false);
        try {
            if (run) {
                if (dataList == null || dataList.size() <= 0) {
                    ToastUtils.showShort("数据不能为空");
                    finish();
                    return;
                }
            } else {
                ToastUtils.showShort("当前页面只能通过调用open方法打开");
                finish();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showShort("当前页面解析数据异常");
            finish();
            return;
        }

        mActivity = ShowBigImageActivity.this;

        picUrlList.clear();
        picUrlList.addAll(dataList);
        mTotalCount = picUrlList.size();
        mCurrentPosition = dataPosition;

        initView();
    }

    private void initView() {
        myViewPager = findViewById(R.id.myViewPager);
        ivBack = findViewById(R.id.ivBack);
        tvHint = findViewById(R.id.tvHint);

        upDateUI();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        myViewPager.setAdapter(new ShowBigImageActivityViewPagerAdapter(picUrlList));
        myViewPager.setCurrentItem(mCurrentPosition);
        myViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
                upDateUI();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 更新UI
     */
    private void upDateUI() {
        tvHint.setText(mCurrentPosition + 1 + "/" + mTotalCount);
    }

}
