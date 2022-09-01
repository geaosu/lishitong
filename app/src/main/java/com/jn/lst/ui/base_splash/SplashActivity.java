package com.jn.lst.ui.base_splash;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;

import com.jn.lst.R;
import com.jn.lst.base.BaseActivity;
import com.jn.lst.base.DataEvent;
import com.jn.lst.base.token.TokenManager;
import com.jn.lst.ui.base_login.LoginActivity;
import com.jn.lst.ui.base_main.MainActivity;

/**
 * @des: 启动界面
 * @Author:
 * @time: 2022年08月20日
 */
public class SplashActivity extends BaseActivity {

    private CountDownTimer mTimer = new CountDownTimer(800, 800) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            String token = TokenManager.getToken();
            // String token = SPUtils.getInstance().getString(Constants.TOKEN);
            if (TextUtils.isEmpty(token)) {
                LoginActivity.open(mActivity);
            } else {
                MainActivity.open(mActivity);
            }
            finish();
        }
    };

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected int attachLayout() {
        return R.layout.base_splash;
    }

    @Override
    protected void initView() {
        mTimer.start();
    }

    @Override
    protected void eventMsg(DataEvent event) {

    }
}