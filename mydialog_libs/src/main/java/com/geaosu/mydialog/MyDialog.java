package com.geaosu.mydialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.geaosu.mydialog.adapter.MyButtomDialogAdapter;
import com.geaosu.mydialog.adapter.MyButtonListDialogAdapter;
import com.geaosu.mydialog.utils.PhoneUtils;

import java.util.List;


/**
 * 各种提示框
 */
public class MyDialog {
    public static Dialog showLoading(Context context, String msg, boolean canCancel) {
        try {
            // 得到整个View
            View mDialogView = LayoutInflater.from(context).inflate(R.layout.my_dialog_loading, null);
            // 获取页面中的img
            ImageView ivIcon = mDialogView.findViewById(R.id.ivIcon);
            TextView tvMsg = mDialogView.findViewById(R.id.tvMsg);

            Animation animation = AnimationUtils.loadAnimation(context, R.anim.my_dialog_loading_anim);// 加载动画，动画用户使img图片不停的旋转
            ivIcon.startAnimation(animation);// 显示动画

            if (msg == null || TextUtils.isEmpty(msg)) {
                tvMsg.setVisibility(View.GONE);
            } else {
                tvMsg.setText(msg);
                tvMsg.setVisibility(View.VISIBLE);
            }

            Dialog loadingDialog = new Dialog(context, R.style.my_dialog_loading_style);// 创建自定义样式的Dialog
            if (canCancel) {
                loadingDialog.setCancelable(true);// 需要设置返回键返回，点击空白取消 设置为true
            } else {
                loadingDialog.setCancelable(false);// 需要设置返回键返回，点击空白取消 设置为true
            }
            loadingDialog.setCanceledOnTouchOutside(false);// 点击dialog以外的区域不消失
            loadingDialog.setContentView(mDialogView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            loadingDialog.show();
            return loadingDialog;
        } catch (Exception e) {
            Log.e("MyDialog", "发生异常：------>> " + e.toString());
            return null;
        }
    }

    // 加载动画
    public static Dialog showLoading(Context context, String msg) {
        return showLoading(context, msg, false);
    }

    // 成功提示框
    public static void showSucc(Context context, String msg, final OnMyDialogClickListener listener) {
        try {
            final Dialog dialog = new Dialog(context);
            View dialogView = View.inflate(context, R.layout.my_dialog_succ, null);
            TextView tvMsg = dialogView.findViewById(R.id.tvMsg);
            TextView tvConfirm = dialogView.findViewById(R.id.tvConfirm);

            if (!TextUtils.isEmpty(msg)) {
                tvMsg.setText(msg);
            }

            tvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    if (listener != null) {
                        listener.onConfirmClick();
                    }
                }
            });

            Window window = dialog.getWindow();
            if (window != null) {
                // 一定要设置Background，如果不设置，window属性设置无效
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
                DisplayMetrics dm = new DisplayMetrics();
                if (context != null) {
                    WindowManager.LayoutParams params = window.getAttributes();
                    params.gravity = Gravity.CENTER;
                    // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
                    //设置窗口宽度为充满全屏
                    params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                    //设置窗口高度为包裹内容
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    window.setAttributes(params);
                }
            }

            dialog.setContentView(dialogView);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // 失败提示框
    public static void showErr(String msg) {

    }

    // 删除提示框
    public static void showDelete(Context context, String msg, final OnMyDialogClickListener listener) {
        try {
            final Dialog dialog = new Dialog(context);
            View dialogView = View.inflate(context, R.layout.my_dialog_delete, null);
            TextView tvMsg = dialogView.findViewById(R.id.tvMsg);
            TextView tvCancel = dialogView.findViewById(R.id.tvCancel);
            TextView tvConfirm = dialogView.findViewById(R.id.tvConfirm);

            if (!TextUtils.isEmpty(msg)) {
                tvMsg.setText(msg);
            }

            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            });
            tvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    if (listener != null) {
                        listener.onConfirmClick();
                    }
                }
            });

            Window window = dialog.getWindow();
            if (window != null) {
                // 一定要设置Background，如果不设置，window属性设置无效
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
                if (context != null) {
                    WindowManager.LayoutParams params = window.getAttributes();
                    params.gravity = Gravity.CENTER;
                    // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
                    //设置窗口宽度为充满全屏
                    params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                    //设置窗口高度为包裹内容
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    window.setAttributes(params);
                }
            }

            dialog.setContentView(dialogView);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {
        }
    }

    // 删除提示框
    public static void showDeleteWithTitle(Context context, String title, String msg, final OnMyDialogClickListener listener) {
        try {
            final Dialog dialog = new Dialog(context);
            View dialogView = View.inflate(context, R.layout.my_dialog_delete_with_title, null);
            TextView tvTitle = dialogView.findViewById(R.id.tvTitle);
            TextView tvMsg = dialogView.findViewById(R.id.tvMsg);
            TextView tvCancel = dialogView.findViewById(R.id.tvCancel);
            TextView tvConfirm = dialogView.findViewById(R.id.tvConfirm);

            if (!TextUtils.isEmpty(title)) {
                tvTitle.setText(title);
            }
            if (!TextUtils.isEmpty(msg)) {
                tvMsg.setText(Html.fromHtml(msg));
            }

            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            });
            tvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    if (listener != null) {
                        listener.onConfirmClick();
                    }
                }
            });

            Window window = dialog.getWindow();
            if (window != null) {
                // 一定要设置Background，如果不设置，window属性设置无效
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
                DisplayMetrics dm = new DisplayMetrics();
                if (context != null) {
                    WindowManager.LayoutParams params = window.getAttributes();
                    params.gravity = Gravity.CENTER;
                    // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
                    //设置窗口宽度为充满全屏
                    params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                    //设置窗口高度为包裹内容
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    window.setAttributes(params);
                }
            }

            dialog.setContentView(dialogView);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {
        }
    }

    // 信息提示框
    public static void showTips(Context context, String msg, final OnMyDialogClickListener listener) {
        try {
            final Dialog dialog = new Dialog(context);
            View dialogView = View.inflate(context, R.layout.my_dialog_tips, null);
            TextView tvMsg = dialogView.findViewById(R.id.tvMsg);
            TextView tvConfirm = dialogView.findViewById(R.id.tvConfirm);

            if (!TextUtils.isEmpty(msg)) {
                tvMsg.setText(msg);
            }

            tvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    if (listener != null) {
                        listener.onConfirmClick();
                    }
                }
            });

            Window window = dialog.getWindow();
            if (window != null) {
                // 一定要设置Background，如果不设置，window属性设置无效
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
                DisplayMetrics dm = new DisplayMetrics();
                if (context != null) {
                    WindowManager.LayoutParams params = window.getAttributes();
                    params.gravity = Gravity.CENTER;
                    // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
                    //设置窗口宽度为充满全屏
                    params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                    //设置窗口高度为包裹内容
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    window.setAttributes(params);
                }
            }

            dialog.setContentView(dialogView);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {

        }
    }

    // 信息提示框
    public static void showTips2(Context context, String msg, String confirmBtnLabel, String cancelBtnLabel, final OnMyDialogTips2ClickListener listener) {
        try {
            final Dialog dialog = new Dialog(context);
            View dialogView = View.inflate(context, R.layout.my_dialog_tips_2, null);
            TextView tvMsg = dialogView.findViewById(R.id.tvMsg);
            TextView tvCancel = dialogView.findViewById(R.id.tvCancel);
            TextView tvConfirm = dialogView.findViewById(R.id.tvConfirm);

            if (TextUtils.isEmpty(msg)) {
                tvMsg.setText("暂无提示信息");
            }else {
                tvMsg.setText(msg);
            }

            if (TextUtils.isEmpty(confirmBtnLabel)) {
                tvConfirm.setText("确认");
            }else {
                tvConfirm.setText(confirmBtnLabel);
            }

            if (TextUtils.isEmpty(cancelBtnLabel)) {
                tvCancel.setText("取消");
            }else {
                tvCancel.setText(cancelBtnLabel);
            }

            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    if (listener != null) {
                        listener.onCancelClick();
                    }
                }
            });

            tvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    if (listener != null) {
                        listener.onConfirmClick();
                    }
                }
            });

            Window window = dialog.getWindow();
            if (window != null) {
                // 一定要设置Background，如果不设置，window属性设置无效
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
                DisplayMetrics dm = new DisplayMetrics();
                if (context != null) {
                    WindowManager.LayoutParams params = window.getAttributes();
                    params.gravity = Gravity.CENTER;
                    // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
                    //设置窗口宽度为充满全屏
                    params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                    //设置窗口高度为包裹内容
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    window.setAttributes(params);
                }
            }

            dialog.setContentView(dialogView);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {

        }
    }

    // 信息提示框
    public static void showTitleTips(Context context, String title, String msg, final OnMyDialogClickListener listener) {
        try {
            final Dialog dialog = new Dialog(context);
            View dialogView = View.inflate(context, R.layout.my_dialog_tips_with_title, null);
            TextView tvTitle = dialogView.findViewById(R.id.tvTitle);
            TextView tvMsg = dialogView.findViewById(R.id.tvMsg);
            TextView tvCancel = dialogView.findViewById(R.id.tvCancel);
            TextView tvConfirm = dialogView.findViewById(R.id.tvConfirm);

            if (!TextUtils.isEmpty(title)) {
                tvTitle.setText(title);
            }

            if (!TextUtils.isEmpty(msg)) {
                tvMsg.setText(msg);
            }

            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            });

            tvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    if (listener != null) {
                        listener.onConfirmClick();
                    }
                }
            });

            Window window = dialog.getWindow();
            if (window != null) {
                // 一定要设置Background，如果不设置，window属性设置无效
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
                DisplayMetrics dm = new DisplayMetrics();
                if (context != null) {
                    WindowManager.LayoutParams params = window.getAttributes();
                    params.gravity = Gravity.CENTER;
                    // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
                    //设置窗口宽度为充满全屏
                    params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                    //设置窗口高度为包裹内容
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    window.setAttributes(params);
                }
            }

            dialog.setContentView(dialogView);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {
        }
    }

    /**
     * 带标题，可以自定义保存和取消按钮文字的弹框提示, 如果取消按钮没有操作cancelListener传null即可
     *
     * @param context
     * @param title
     * @param msg
     * @param cancel
     * @param confirm
     * @param cancelListener
     * @param listener
     */
    public static void showTitleDiyTips(Context context, String title, String msg, String cancel, String confirm, final OnMyDialogClickListener cancelListener, final OnMyDialogClickListener listener) {
        try {
            final Dialog dialog = new Dialog(context);
            View dialogView = View.inflate(context, R.layout.my_dialog_tips_with_title, null);
            TextView tvTitle = dialogView.findViewById(R.id.tvTitle);
            TextView tvMsg = dialogView.findViewById(R.id.tvMsg);
            TextView tvCancel = dialogView.findViewById(R.id.tvCancel);
            TextView tvConfirm = dialogView.findViewById(R.id.tvConfirm);

            if (!TextUtils.isEmpty(title)) {
                tvTitle.setText(title);
            } else {
                tvTitle.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(msg)) {
                tvMsg.setText(Html.fromHtml(msg));
            } else {
                tvMsg.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(cancel)) {
                tvCancel.setText(cancel);
            }
            if (!TextUtils.isEmpty(confirm)) {
                tvConfirm.setText(confirm);
            }
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    if (cancelListener != null) {
                        cancelListener.onConfirmClick();
                    }
                }
            });

            tvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    if (listener != null) {
                        listener.onConfirmClick();
                    }
                }
            });

            Window window = dialog.getWindow();
            if (window != null) {
                // 一定要设置Background，如果不设置，window属性设置无效
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
                DisplayMetrics dm = new DisplayMetrics();
                if (context != null) {
                    WindowManager.LayoutParams params = window.getAttributes();
                    params.gravity = Gravity.CENTER;
                    // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
                    //设置窗口宽度为充满全屏
                    params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                    //设置窗口高度为包裹内容
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    window.setAttributes(params);
                }
            }

            dialog.setContentView(dialogView);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);
            dialog.show();
        } catch (Exception e) {
        }
    }

    // 全屏显示
    private Dialog showFullScreenDialog(Context context, View view) {
        //1、使用Dialog、设置style
//        Dialog dialog = new Dialog(context, R.style.DialogTheme);
//        //2、设置布局
//        dialog.setContentView(view);
//
//        Window window = dialog.getWindow();
//        //设置弹出位置
//        window.setGravity(Gravity.BOTTOM);
//        //设置弹出动画
//        window.setWindowAnimations(R.style.main_menu_animStyle);
//        //设置对话框大小
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.show();
//        return dialog;
        return null;
    }

    // 信息提示框
    public static void showAddPeople(Context context, final OnMyDialogAddPeopleClickListener listener) {
        try {
            final Dialog dialog = new Dialog(context);
            View dialogView = View.inflate(context, R.layout.my_dialog_add_people, null);
            TextView tvConfirm = dialogView.findViewById(R.id.tvConfirm);
            TextView tvCancel = dialogView.findViewById(R.id.tvCancel);
            final EditText etName = dialogView.findViewById(R.id.etName);
            final EditText etPhone = dialogView.findViewById(R.id.etPhone);

            etPhone.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().length() > 0) {
                        if (PhoneUtils.isPhone(s.toString())) {
                            etPhone.setTextColor(Color.parseColor("#243047"));
                        } else {
                            etPhone.setTextColor(Color.parseColor("#FF0000"));
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            tvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = etName.getText().toString().trim();
                    String phone = etPhone.getText().toString().trim();
                    if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone)) {
                        if (PhoneUtils.isPhone(phone)) {
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            if (listener != null) {
                                listener.onConfirmClick(name, phone);
                            }
                        }
                    } else {
                        etName.setHintTextColor(Color.parseColor("#C2C2C2"));
                        etPhone.setHintTextColor(Color.parseColor("#C2C2C2"));
                        if (TextUtils.isEmpty(name)) {
                            etName.setHintTextColor(Color.parseColor("#60FF0000"));
                        }
                        if (TextUtils.isEmpty(phone)) {
                            etPhone.setHintTextColor(Color.parseColor("#60FF0000"));
                        }
                    }
                }
            });

            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            });

            Window window = dialog.getWindow();
            if (window != null) {
                // 一定要设置Background，如果不设置，window属性设置无效
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
                DisplayMetrics dm = new DisplayMetrics();
                if (context != null) {
                    WindowManager.LayoutParams params = window.getAttributes();
                    params.gravity = Gravity.CENTER;
                    // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
                    //设置窗口宽度为充满全屏
                    params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                    //设置窗口高度为包裹内容
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    window.setAttributes(params);
                }
            }

            dialog.setContentView(dialogView);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {
        }
    }

    // 显示底部弹窗 - 带确认和取消按钮 版本
    public static void showButtonListSeletorWhitBtn(final Context context, String title, final List<ButtonBean> butList, final OnMyButtonListDialogClickListener listener) {
        try {
            final Dialog dialog = new Dialog(context);
            View dialogView = View.inflate(context, R.layout.my_dialog_buttom_selector, null);
            TextView tvCancel = (TextView) dialogView.findViewById(R.id.tvCancel);
            TextView tvTitle = (TextView) dialogView.findViewById(R.id.tvTitle);
            TextView tvConfirm = (TextView) dialogView.findViewById(R.id.tvConfirm);
            ListView mListView = (ListView) dialogView.findViewById(R.id.mListView);

            if (butList == null || butList.size() <= 0) {
                Toast.makeText(context, "按钮数据异常", Toast.LENGTH_SHORT).show();
            } else {
                final String[] id = {""};
                final String[] name = {""};

                if (!TextUtils.isEmpty(title)) {
                    tvTitle.setText(title);
                }

                final MyButtomDialogAdapter adapter;
                mListView.setAdapter(adapter = new MyButtomDialogAdapter());
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long positionId) {
                        for (int i = 0; i < butList.size(); i++) {
                            if (i == position) {
                                butList.get(i).setChecked(true);
                                id[0] = butList.get(i).getId();
                                name[0] = butList.get(i).getName();
                            } else {
                                butList.get(i).setChecked(false);
                            }
                        }
                        adapter.replaceAll(butList);
                    }
                });

                adapter.replaceAll(butList);

                tvConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(id[0])) {
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            if (listener != null) {
                                listener.onConfirmClick(id[0], name[0]);
                            }
                        } else {
                            Toast.makeText(context, "请选择", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });

                Window window = dialog.getWindow();
                if (window != null) {
                    // 一定要设置Background，如果不设置，window属性设置无效
                    window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
                    DisplayMetrics dm = new DisplayMetrics();
                    if (context != null) {
                        WindowManager.LayoutParams params = window.getAttributes();
                        params.gravity = Gravity.CENTER;
                        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
                        //设置窗口宽度为充满全屏
                        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                        //设置窗口高度为包裹内容
                        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        window.setAttributes(params);
                    }
                }

                dialog.setContentView(dialogView);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                dialog.show();
            }
        } catch (Exception e) {
        }
    }

    // 显示底部弹窗 - 点击item即可确认 版本
    public static void showButtonListSeletor(final Context context, String title, final List<ButtonBean> btnList, final OnMyButtonListDialogClickListener listener) {
        try {
            final Dialog dialog = new Dialog(context);
            View dialogView = View.inflate(context, R.layout.my_dialog_button_list_selector, null);
            TextView tvTitle = (TextView) dialogView.findViewById(R.id.tvTitle);
            ListView mListView = (ListView) dialogView.findViewById(R.id.mListView);

            if (btnList == null || btnList.size() <= 0) {
                Toast.makeText(context, "按钮数据异常", Toast.LENGTH_SHORT).show();
            } else {
                if (!TextUtils.isEmpty(title)) {
                    tvTitle.setText(title);
                }
                MyButtonListDialogAdapter adapter;
                // mListView.setDivider(new ColorDrawable(Color.TRANSPARENT));// 设置分割线的颜色
                // mListView.setDividerHeight((int) getResources().getDimension(R.dimen.dp_10));// 设置分割线的高度
                mListView.setAdapter(adapter = new MyButtonListDialogAdapter());
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        String btnID = btnList.get(position).getId();
                        String btnName = btnList.get(position).getName();
                        if (listener != null) {
                            listener.onConfirmClick(btnID, btnName);
                        }
                    }
                });
                adapter.replaceAll(btnList);

                Window window = dialog.getWindow();
                if (window != null) {
                    window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));// 一定要设置Background，如果不设置，window属性设置无效
                    if (context != null) {
                        WindowManager.LayoutParams params = window.getAttributes();
                        params.gravity = Gravity.CENTER;
                        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
                        params.width = WindowManager.LayoutParams.WRAP_CONTENT;//设置窗口宽度为充满全屏
                        params.height = WindowManager.LayoutParams.WRAP_CONTENT;//设置窗口高度为包裹内容
                        window.setAttributes(params);
                    }
                }
                dialog.setContentView(dialogView);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setCancelable(true);
                dialog.show();
            }
        } catch (Exception e) {
        }
    }

    // 显示底部弹窗 -
    public static void showButtonListSeletor2(final Context context, String title, final List<ButtonBean> btnList, final OnMyButtonListDialogClickListener listener) {
        try {
            final Dialog dialog = new Dialog(context);
            View dialogView = View.inflate(context, R.layout.my_dialog_button_list_selector, null);
            TextView tvTitle = (TextView) dialogView.findViewById(R.id.tvTitle);
            ListView mListView = (ListView) dialogView.findViewById(R.id.mListView);
            if (!TextUtils.isEmpty(title)) {
                tvTitle.setText(title);
            }
            if (btnList == null || btnList.size() <= 0) {
                Toast.makeText(context, "按钮数据异常", Toast.LENGTH_SHORT).show();
            } else {
                MyButtonListDialogAdapter adapter;
                mListView.setAdapter(adapter = new MyButtonListDialogAdapter());
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        String btnID = btnList.get(position).getId();
                        String btnName = btnList.get(position).getName();
                        if (listener != null) {
                            listener.onConfirmClick(btnID, btnName);
                        }
                    }
                });
                adapter.replaceAll(btnList);

                Window window = dialog.getWindow();
                if (window != null) {
                    window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));// 一定要设置Background，如果不设置，window属性设置无效
                    window.setGravity(Gravity.BOTTOM);
                    window.setContentView(dialogView);
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
                }
                dialog.setCanceledOnTouchOutside(true);
                dialog.setCancelable(true);
                dialog.show();
            }
        } catch (Exception e) {
        }
    }

    // 显示底部弹窗 - 点击item即可确认 版本
    public static void showEverydaySinginSucc(Context context, String title, String msg, final OnMyDialogClickListener listener) {
        try {
            final Dialog dialog = new Dialog(context);
            View dialogView = View.inflate(context, R.layout.my_dialog_everyday_login, null);
            TextView tvTitle = dialogView.findViewById(R.id.tvTitle);
            TextView tvMsg = dialogView.findViewById(R.id.tvMsg);
            TextView tvConfirm = dialogView.findViewById(R.id.tvConfirm);
            TextView tvGo = dialogView.findViewById(R.id.tvGo);

            if (!TextUtils.isEmpty(title)) {
                tvTitle.setText(title);
            } else {
                tvTitle.setVisibility(View.INVISIBLE);
                tvMsg.setTextSize(20);
                tvMsg.setTextColor(Color.parseColor("#3364D3"));
            }
            if (!TextUtils.isEmpty(msg)) {
                tvMsg.setText(msg);
            }

            tvGo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    if (listener != null) {
                        listener.onConfirmClick();
                    }
                }
            });
            tvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            });

            Window window = dialog.getWindow();
            if (window != null) {
                // 一定要设置Background，如果不设置，window属性设置无效
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
                DisplayMetrics dm = new DisplayMetrics();
                if (context != null) {
                    WindowManager.LayoutParams params = window.getAttributes();
                    params.gravity = Gravity.CENTER;
                    // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
                    //设置窗口宽度为充满全屏
                    params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                    //设置窗口高度为包裹内容
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    window.setAttributes(params);
                }
            }

            dialog.setContentView(dialogView);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {

        }
    }

    // 现场打卡
    public static void showPunchOlockSucc(Context context, String msg, final OnMyDialogClickListener listener) {
        try {
            final Dialog dialog = new Dialog(context);
            View dialogView = View.inflate(context, R.layout.my_dialog_punch_olock_succ, null);
            TextView tvMsg = dialogView.findViewById(R.id.tvMsg);
            TextView tvConfirm = dialogView.findViewById(R.id.tvConfirm);

            if (!TextUtils.isEmpty(msg)) {
                tvMsg.setText(msg);
            }

            tvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    if (listener != null) {
                        listener.onConfirmClick();
                    }
                }
            });

            Window window = dialog.getWindow();
            if (window != null) {
                // 一定要设置Background，如果不设置，window属性设置无效
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
                DisplayMetrics dm = new DisplayMetrics();
                if (context != null) {
                    WindowManager.LayoutParams params = window.getAttributes();
                    params.gravity = Gravity.CENTER;
                    // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
                    //设置窗口宽度为充满全屏
                    params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                    //设置窗口高度为包裹内容
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    window.setAttributes(params);
                }
            }

            dialog.setContentView(dialogView);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {

        }
    }

    // 信息提示框
    public static void showCode(final Context context, final OnMyDialogGetCodeListener listener) {
        try {
            final Dialog dialog = new Dialog(context);
            View dialogView = View.inflate(context, R.layout.my_dialog_code, null);
            final EditText edCode = (EditText) dialogView.findViewById(R.id.edCode);
            TextView tvConfirm = (TextView) dialogView.findViewById(R.id.tvConfirm);

            tvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String code = edCode.getText().toString().trim();
                    if (TextUtils.isEmpty(code)) {
                        Toast.makeText(context, "请输入验证码", Toast.LENGTH_SHORT).show();
                    } else {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        if (listener != null) {
                            listener.onConfirmClick(code);
                        }
                    }
                }
            });

            Window window = dialog.getWindow();
            if (window != null) {
                // 一定要设置Background，如果不设置，window属性设置无效
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
                DisplayMetrics dm = new DisplayMetrics();
                if (context != null) {
                    WindowManager.LayoutParams params = window.getAttributes();
                    params.gravity = Gravity.CENTER;
                    // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
                    //设置窗口宽度为充满全屏
                    params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                    //设置窗口高度为包裹内容
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    window.setAttributes(params);
                }
            }

            dialog.setContentView(dialogView);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {

        }
    }

    // 信息提示框
    public static void showEditDialog(final Context context, String msg, String name, String btn1Title, final OnMyDialogGetCodeListener listener, String btn2Title, final OnMyDialogGetCodeListener listener2) {
        try {
            final Dialog dialog = new Dialog(context);
            View wiewRoot = View.inflate(context, R.layout.dialog_edit_layout, null);

            TextView tvMsg = wiewRoot.findViewById(R.id.tvMsg);
            final EditText editText = wiewRoot.findViewById(R.id.edit_text);
            TextView tvBtn1 = wiewRoot.findViewById(R.id.tvBtn1);
            TextView tvBtn2 = wiewRoot.findViewById(R.id.tvBtn2);

//        ivIcon.setImageDrawable(window.getContext().getDrawable(iconRes));
            editText.setText(name);
            tvMsg.setText(msg);
            tvBtn1.setText(btn1Title);
            tvBtn2.setText(btn2Title);

            tvBtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (listener != null) {
                        listener.onConfirmClick("");
                    }
                }
            });

            tvBtn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (listener2 != null) {
                        listener2.onConfirmClick(editText.getText().toString().trim());
                    }
                }
            });

            Window window = dialog.getWindow();
            if (window != null) {
                // 一定要设置Background，如果不设置，window属性设置无效
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
                DisplayMetrics dm = new DisplayMetrics();
                if (context != null) {
                    WindowManager.LayoutParams params = window.getAttributes();
                    params.gravity = Gravity.CENTER;
                    // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
                    //设置窗口宽度为充满全屏
                    params.width = WindowManager.LayoutParams.MATCH_PARENT;
                    //设置窗口高度为包裹内容
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    window.setAttributes(params);
                }
            }

            dialog.setContentView(wiewRoot);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {

        }
    }

    // 显示系统信息
    public static void showSystemInfo(Context context, String msg, String oldBaseUrl, final OnMyDialogShowSystemInfoClickListener listener) {
        try {
            final Dialog dialog = new Dialog(context);
            View dialogView = View.inflate(context, R.layout.my_dialog_show_system_info, null);
            TextView tvMsg = (TextView) dialogView.findViewById(R.id.tvMsg);
            final EditText etBaseUrl = (EditText) dialogView.findViewById(R.id.etBaseUrl);
            TextView tvCancel = (TextView) dialogView.findViewById(R.id.tvCancel);
            TextView tvConfirm = (TextView) dialogView.findViewById(R.id.tvConfirm);


            if (!TextUtils.isEmpty(msg)) {
                tvMsg.setText(msg);
            }
            if (!TextUtils.isEmpty(oldBaseUrl)) {
                etBaseUrl.setText(oldBaseUrl);
            }

            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            });

            tvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }

                    String baseUrl = etBaseUrl.getText().toString().trim();
                    if (listener != null) {
                        listener.onConfirmClick(baseUrl);
                    }
                }
            });

            Window window = dialog.getWindow();
            if (window != null) {
                // 一定要设置Background，如果不设置，window属性设置无效
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
                DisplayMetrics dm = new DisplayMetrics();
                if (context != null) {
                    WindowManager.LayoutParams params = window.getAttributes();
                    params.gravity = Gravity.CENTER;
                    // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
                    //设置窗口宽度为充满全屏
                    params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                    //设置窗口高度为包裹内容
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    window.setAttributes(params);
                }
            }

            dialog.setContentView(dialogView);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {

        }
    }

    /**
     * 显示网格列表
     */
    public static void showGridList(Context context, String title, final List<ButtonBean> btnList, final OnMyButtonListDialogClickListener listener) {
        showGridList(context, title, btnList, true, listener);
    }


    /**
     * 显示网格列表
     */
    public static void showGridList(final Context context, String title, final List<ButtonBean> btnList, boolean cancelAble, final OnMyButtonListDialogClickListener listener) {
        try {
            final Dialog dialog = new Dialog(context);
            View dialogView = View.inflate(context, R.layout.my_dialog_grid_selector, null);
            TextView tvTitle = (TextView) dialogView.findViewById(R.id.tvTitle);
            ListView mListView = (ListView) dialogView.findViewById(R.id.mListView);
            TextView tvCancel = (TextView) dialogView.findViewById(R.id.tvCancel);
            if (TextUtils.isEmpty(title)) {
                tvTitle.setText("请选择网格");
            } else {
                tvTitle.setText(title);
            }
            if (cancelAble) {
                tvCancel.setVisibility(View.VISIBLE);
            }else {
                tvCancel.setVisibility(View.GONE);
            }
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            });
            if (btnList == null || btnList.size() <= 0) {
                Toast.makeText(context, "按钮数据异常", Toast.LENGTH_SHORT).show();
            } else {
                MyButtonListDialogAdapter adapter;
                mListView.setAdapter(adapter = new MyButtonListDialogAdapter());
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        String btnID = btnList.get(position).getId();
                        String btnName = btnList.get(position).getName();
                        if (listener != null) {
                            listener.onConfirmClick(btnID, btnName);
                        }
                    }
                });
                adapter.replaceAll(btnList);

                Window window = dialog.getWindow();
                if (window != null) {
                    window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));// 一定要设置Background，如果不设置，window属性设置无效
                    window.setGravity(Gravity.BOTTOM);
                    window.setContentView(dialogView);
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
                }
                dialog.setCanceledOnTouchOutside(cancelAble);
                dialog.setCancelable(cancelAble);
                dialog.show();
            }
        } catch (Exception e) {
        }
    }

    /**
     * 场所分类说明
     *
     * @param context
     */
    public static void showTipsChangSuoFenLei(final Context context) {
        try {
            final Dialog dialog = new Dialog(context);
            View dialogView = View.inflate(context, R.layout.my_dialog_chang_suo_fen_lei_book, null);
            Window window = dialog.getWindow();
            if (window != null) {
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));// 一定要设置Background，如果不设置，window属性设置无效
                window.setGravity(Gravity.BOTTOM);
                window.setContentView(dialogView);
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
            }
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);
            dialog.show();
        } catch (Exception e) {
        }
    }

    /**
     * 社会组织性质说明
     *
     * @param context
     */
    public static void showTipsSheHuiZuZhiXingZhi(final Context context) {
        try {
            final Dialog dialog = new Dialog(context);
            View dialogView = View.inflate(context, R.layout.my_dialog_she_hui_zu_zhi_xing_zhi_book, null);
            Window window = dialog.getWindow();
            if (window != null) {
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));// 一定要设置Background，如果不设置，window属性设置无效
                window.setGravity(Gravity.BOTTOM);
                window.setContentView(dialogView);
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
            }
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);
            dialog.show();
        } catch (Exception e) {
        }
    }

    /**
     * 社会组织类型说明
     *
     * @param context
     */
    public static void showTipsSheHuiZuZhiLeiXing(final Context context) {
        try {
            final Dialog dialog = new Dialog(context);
            View dialogView = View.inflate(context, R.layout.my_dialog_she_hui_zu_zhi_type_book, null);
            Window window = dialog.getWindow();
            if (window != null) {
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));// 一定要设置Background，如果不设置，window属性设置无效
                window.setGravity(Gravity.BOTTOM);
                window.setContentView(dialogView);
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
            }
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);
            dialog.show();
        } catch (Exception e) {
        }
    }

    /**
     * 小区等级说明
     *
     * @param context
     */
    public static void showTipsXiaoQuDengJi(final Context context) {
        try {
            final Dialog dialog = new Dialog(context);
            View dialogView = View.inflate(context, R.layout.my_dialog_xiao_qu_deng_ji_book, null);
            Window window = dialog.getWindow();
            if (window != null) {
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));// 一定要设置Background，如果不设置，window属性设置无效
                window.setGravity(Gravity.BOTTOM);
                window.setContentView(dialogView);
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
            }
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);
            dialog.show();
        } catch (Exception e) {
        }
    }

}
