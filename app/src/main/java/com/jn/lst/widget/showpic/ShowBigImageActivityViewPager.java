package com.jn.lst.widget.showpic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * Des：显示大图 - MyViewPager
 * 功能介绍：解决与PhotoView一起使用时的滑动冲突
 * <p>
 * <p>
 * <p>
 * 注意：这个是已经封装好的，别他妈手长
 * 注意：这个是已经封装好的，别他妈手长
 * 注意：这个是已经封装好的，别他妈手长
 */
public class ShowBigImageActivityViewPager extends ViewPager {
    public ShowBigImageActivityViewPager(Context context) {
        super(context);
    }

    public ShowBigImageActivityViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
