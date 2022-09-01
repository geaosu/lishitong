package com.jn.lst.base.user;

import com.blankj.utilcode.util.SPUtils;
import com.jn.lst.base.Constants;
import com.google.gson.Gson;

/**
 * @des: 用户信息
 * @Author:
 * @time: 2022年08月20日
 */
public class UserManager {
    /**
     * 保存用户信息
     */
    public static void saveUser(UserInfoBean bean) {
        SPUtils.getInstance().put(Constants.USER_INFO, new Gson().toJson(bean), true);
    }

    /**
     * 获取用户信息
     */
    public static UserInfoBean getUser() {
        String userJson = SPUtils.getInstance().getString(Constants.USER_INFO);
        UserInfoBean userInfoBean = new Gson().fromJson(userJson, UserInfoBean.class);
        return userInfoBean;
    }

    /**
     * 清空用户信息
     */
    public static void removeUser() {
        // SPUtils.getInstance().put(Constants.USER_INFO, "", true);
        SPUtils.getInstance().remove(Constants.USER_INFO, true);
    }
}
