package com.jn.lst.utils;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Color;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 动画工具类
 */
public class MyAnimationUtils {
    /**
     * View 动画
     */
    public static void startAnim(View view) {
        Animation animation = new TranslateAnimation(0, 0, 0, 10);
        animation.setInterpolator(new CycleInterpolator(5));
        animation.setDuration(600);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);
    }

    /**
     * TextView 动画
     */
    public static void startEmptyAnim(TextView view) {
        Animation animation = new TranslateAnimation(0, 10, 0, 0);
        animation.setInterpolator(new CycleInterpolator(5));
        animation.setDuration(600);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setHintTextColor(Color.parseColor("#FF0000"));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setHintTextColor(Color.parseColor("#737373"));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);
    }

    /**
     * TextView 动画
     */
    public static void startEmptyAnim(EditText view) {
        Animation animation = new TranslateAnimation(0, 10, 0, 0);
        animation.setInterpolator(new CycleInterpolator(5));
        animation.setDuration(600);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setHintTextColor(Color.parseColor("#FF0000"));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setHintTextColor(Color.parseColor("#737373"));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);
    }

    /**
     * TextView 动画
     */
    public static void startErrAnim(TextView view) {
        Animation animation = new TranslateAnimation(0, 10, 0, 0);
        animation.setInterpolator(new CycleInterpolator(5));
        animation.setDuration(600);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setTextColor(Color.parseColor("#FF0000"));

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setTextColor(Color.parseColor("#737373"));

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);
    }

    /**
     * 气泡漂浮动画
     *
     * @param view
     * @param duration    动画运行时间
     * @param offset      动画运行幅度
     * @param repeatCount 动画运行次数
     * @return
     */
    public static ObjectAnimator bubbleFloat(View view, int duration, int offset, int repeatCount) {
        float path = (float) (Math.sqrt(3) / 2 * offset);
        PropertyValuesHolder translateX = PropertyValuesHolder.ofKeyframe(
                View.TRANSLATION_X,
                Keyframe.ofFloat(0f, 0),
                Keyframe.ofFloat(1 / 12f, offset / 2),
                Keyframe.ofFloat(2 / 12f, path),
                Keyframe.ofFloat(3 / 12f, offset),
                Keyframe.ofFloat(4 / 12f, path),
                Keyframe.ofFloat(5 / 12f, offset / 2),
                Keyframe.ofFloat(6 / 12f, 0),
                Keyframe.ofFloat(7 / 12f, -offset / 2),
                Keyframe.ofFloat(8 / 12f, -path),
                Keyframe.ofFloat(9 / 12f, -offset),
                Keyframe.ofFloat(10 / 12f, -path),
                Keyframe.ofFloat(11 / 12f, -offset / 2),
                Keyframe.ofFloat(1f, 0)
        );

        PropertyValuesHolder translateY = PropertyValuesHolder.ofKeyframe(
                View.TRANSLATION_Y,
                Keyframe.ofFloat(0f, 0),
                Keyframe.ofFloat(1 / 12f, offset - path),
                Keyframe.ofFloat(2 / 12f, offset / 2),
                Keyframe.ofFloat(3 / 12f, offset),
                Keyframe.ofFloat(4 / 12f, offset * 3 / 2),
                Keyframe.ofFloat(5 / 12f, offset + path),
                Keyframe.ofFloat(6 / 12f, offset * 2),
                Keyframe.ofFloat(7 / 12f, offset + path),
                Keyframe.ofFloat(8 / 12f, offset * 3 / 2),
                Keyframe.ofFloat(9 / 12f, offset),
                Keyframe.ofFloat(10 / 12f, offset / 2),
                Keyframe.ofFloat(11 / 12f, offset - path),
                Keyframe.ofFloat(1f, 0)
        );

        PropertyValuesHolder rotateX = PropertyValuesHolder.ofKeyframe(
                View.ROTATION_X,
                Keyframe.ofFloat(0f, 0),
                Keyframe.ofFloat(1 / 12f, offset / 2),
                Keyframe.ofFloat(2 / 12f, path),
                Keyframe.ofFloat(3 / 12f, offset),
                Keyframe.ofFloat(4 / 12f, path),
                Keyframe.ofFloat(5 / 12f, offset / 2),
                Keyframe.ofFloat(6 / 12f, 0),
                Keyframe.ofFloat(7 / 12f, -offset / 2),
                Keyframe.ofFloat(8 / 12f, -path),
                Keyframe.ofFloat(9 / 12f, -offset),
                Keyframe.ofFloat(10 / 12f, -path),
                Keyframe.ofFloat(11 / 12f, -offset / 2),
                Keyframe.ofFloat(1f, 0)
        );

        PropertyValuesHolder rotateY = PropertyValuesHolder.ofKeyframe(
                View.ROTATION_Y,
                Keyframe.ofFloat(0f, 0),
                Keyframe.ofFloat(1 / 12f, offset / 2),
                Keyframe.ofFloat(2 / 12f, path),
                Keyframe.ofFloat(3 / 12f, offset),
                Keyframe.ofFloat(4 / 12f, path),
                Keyframe.ofFloat(5 / 12f, offset / 2),
                Keyframe.ofFloat(6 / 12f, 0),
                Keyframe.ofFloat(7 / 12f, -offset / 2),
                Keyframe.ofFloat(8 / 12f, -path),
                Keyframe.ofFloat(9 / 12f, -offset),
                Keyframe.ofFloat(10 / 12f, -path),
                Keyframe.ofFloat(11 / 12f, -offset / 2),
                Keyframe.ofFloat(1f, 0)
        );

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, translateX, translateY, rotateX, rotateY).setDuration(duration);
        animator.setRepeatCount(repeatCount);
        animator.setInterpolator(new LinearInterpolator());
        return animator;
    }
}
