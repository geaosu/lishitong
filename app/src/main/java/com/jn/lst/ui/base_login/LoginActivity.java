package com.jn.lst.ui.base_login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.geaosu.mydialog.MyDialog;
import com.geaosu.mydialog.OnMyDialogClickListener;
import com.jn.lst.R;
import com.jn.lst.base.BaseActivity;
import com.jn.lst.base.Constants;
import com.jn.lst.base.DataEvent;
import com.jn.lst.base.token.TokenManager;
import com.jn.lst.ui.base_main.MainActivity;
import com.jn.lst.utils.MyClickUtils;
import com.jn.lst.utils.MySoftKeyboardUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @des: 登录
 * @Author:
 * @time: 2022年08月20日
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.llSwitchLogin)
    LinearLayout llSwitchLogin;
    @BindView(R.id.tvSwitchLogin)
    TextView tvSwitchLogin;
    @BindView(R.id.tvSwitchLoginLine)
    View tvSwitchLoginLine;
    @BindView(R.id.llSwitchRegister)
    LinearLayout llSwitchRegister;
    @BindView(R.id.tvSwitchRegister)
    TextView tvSwitchRegister;
    @BindView(R.id.tvSwitchRegisterLine)
    View tvSwitchRegisterLine;
    @BindView(R.id.llLoginBox)
    LinearLayout llLoginBox;
    @BindView(R.id.etLoginName)
    EditText etLoginName;
    @BindView(R.id.etLoginPwd)
    EditText etLoginPwd;
    @BindView(R.id.tvLogin)
    TextView tvLogin;
    @BindView(R.id.llRegisterBox)
    LinearLayout llRegisterBox;
    @BindView(R.id.etRegisterName)
    EditText etRegisterName;
    @BindView(R.id.etRegisterPhone)
    EditText etRegisterPhone;
    @BindView(R.id.etRegisterPwd)
    EditText etRegisterPwd;
    @BindView(R.id.etRegisterPwd2)
    EditText etRegisterPwd2;
    @BindView(R.id.tvRegister)
    TextView tvRegister;

    private LoginActivityRequest mRequest;

    public static void open(Context context) {
        // 通过Intent启动当前activity
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        // 初始化网络请求
        mRequest = new LoginActivityRequest(this);
    }

    @Override
    protected int attachLayout() {
        return R.layout.base_login;
    }

    @Override
    protected void initView() {
        // 登录文字的样式
        tvSwitchLogin.setTextColor(Color.parseColor("#5064EB"));
        tvSwitchLoginLine.setVisibility(View.VISIBLE);

        // 注册文字的样式
        tvSwitchRegister.setTextColor(Color.parseColor("#000000"));
        tvSwitchRegisterLine.setVisibility(View.INVISIBLE);

        // 用户名和密码的输入框
        llLoginBox.setVisibility(View.VISIBLE);
        // 注册时的那些输入框
        llRegisterBox.setVisibility(View.INVISIBLE);

        String loginName = SPUtils.getInstance().getString(Constants.LOGIN_NAME);
        String loginPwd = SPUtils.getInstance().getString(Constants.LOGIN_PWD);

        if (!TextUtils.isEmpty(loginName)) {
            etLoginName.setText(loginName);
        }
        if (!TextUtils.isEmpty(loginPwd)) {
            etLoginPwd.setText(loginPwd);
        }
    }

    @OnClick({R.id.llSwitchLogin, R.id.llSwitchRegister, R.id.tvLogin, R.id.tvRegister})
    public void onViewClicked(View view) {
        MySoftKeyboardUtils.closeSoftKeyboard(mActivity);
        if (MyClickUtils.isDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.llSwitchLogin:// 点击登录
                // 登录的那一部分显示出来
                tvSwitchLogin.setTextColor(Color.parseColor("#5064EB"));
                tvSwitchLoginLine.setVisibility(View.VISIBLE);
                llLoginBox.setVisibility(View.VISIBLE);

                // 注册的那一部分内容隐藏掉
                tvSwitchRegister.setTextColor(Color.parseColor("#000000"));
                tvSwitchRegisterLine.setVisibility(View.INVISIBLE);
                llRegisterBox.setVisibility(View.INVISIBLE);
                break;
            case R.id.llSwitchRegister:// 点击注册
                // 注册的那一部分内容显示出来
                tvSwitchRegister.setTextColor(Color.parseColor("#5064EB"));
                tvSwitchRegisterLine.setVisibility(View.VISIBLE);
                llRegisterBox.setVisibility(View.VISIBLE);

                // 登录的那一部分隐藏掉
                tvSwitchLogin.setTextColor(Color.parseColor("#000000"));
                tvSwitchLoginLine.setVisibility(View.INVISIBLE);
                llLoginBox.setVisibility(View.INVISIBLE);
                break;
            case R.id.tvLogin:
                login();
                break;
            case R.id.tvRegister:
                register(false);
                break;
        }
    }

    @Override
    protected void eventMsg(DataEvent event) {
        switch (event.type) {
            case REGISTER_CHECK_SUCC:
                register(true);
                break;
            case REGISTER_CHECK_ERR:
                ToastUtils.showShort(event.data.toString());
                break;
            case REGISTER_SUCC:
                MyDialog.showTips(mActivity, "注册成功，请登录", new OnMyDialogClickListener() {
                    @Override
                    public void onConfirmClick() {
                        tvSwitchLogin.setTextColor(Color.parseColor("#5064EB"));
                        tvSwitchLoginLine.setVisibility(View.VISIBLE);
                        llLoginBox.setVisibility(View.VISIBLE);

                        tvSwitchRegister.setTextColor(Color.parseColor("#000000"));
                        tvSwitchRegisterLine.setVisibility(View.INVISIBLE);
                        llRegisterBox.setVisibility(View.INVISIBLE);

                        etLoginName.setText(etRegisterPhone.getText().toString().trim());
                    }
                });
                break;
            case REGISTER_ERR:
                ToastUtils.showShort(event.data.toString());
                break;
            case LOGIN_SUCC:// 请求成功
                loginSucc(event);
                break;
            case LOGIN_ERR:// 请求失败
                ToastUtils.showShort(event.data.toString());
                break;
        }
    }

    private void loginSucc(DataEvent event) {
        SPUtils.getInstance().put(Constants.LOGIN_NAME, etLoginName.getText().toString().trim());
        SPUtils.getInstance().put(Constants.LOGIN_PWD, etLoginPwd.getText().toString().trim());

        LoginActivityBean bean = (LoginActivityBean) event.data;

        // 获取token
        String token = bean.getToken();
        // 判断token是否为空
        if (!TextUtils.isEmpty(token)) {
            // token不为空，保存起来
            TokenManager.saveToken(token);
            // 跳转到主界面
            MainActivity.open(mActivity);
            // 销毁掉当前界面
            finish();
        }
    }

    private void login() {
        String loginName = etLoginName.getText().toString().trim();
        String loginPwd = etLoginPwd.getText().toString().trim();

        if (TextUtils.isEmpty(loginName)) {
            ToastUtils.showShort("请输入您的手机号码");
            return;
        }
        if (TextUtils.isEmpty(loginPwd)) {
            ToastUtils.showShort("请输入登录密码");
            return;
        }

        mRequest.login(loginName, loginPwd);
    }

    private void register(boolean isRegister) {
        String registerName = etRegisterName.getText().toString().trim();
        String registerPhone = etRegisterPhone.getText().toString().trim();
        String registerPwd = etRegisterPwd.getText().toString().trim();
        String registerPwd2 = etRegisterPwd2.getText().toString().trim();

        if (TextUtils.isEmpty(registerName)) {
            ToastUtils.showShort("请输入您的昵称");
            return;
        }
        if (TextUtils.isEmpty(registerPhone)) {
            ToastUtils.showShort("请输入您的手机号");
            return;
        }
        if (TextUtils.isEmpty(registerName)) {
            ToastUtils.showShort("请输入登录密码");
            return;
        }
        if (TextUtils.isEmpty(registerName)) {
            ToastUtils.showShort("请输入确认密码");
            return;
        }
        if (registerPwd.length() >= 6 && registerPwd.length() <= 20) {
            if (!registerPwd.equals(registerPwd2)) {
                ToastUtils.showShort("两次输入的密码不一致");
                return;
            }
        } else {
            ToastUtils.showShort("密码长度不正确（6-20位）");
            return;
        }
        if (isRegister) {
            mRequest.register(registerName, registerPhone, registerPwd, registerPwd2);
        } else {
            mRequest.check(registerPhone);
        }
    }

}