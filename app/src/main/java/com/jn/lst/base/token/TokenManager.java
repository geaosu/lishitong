package com.jn.lst.base.token;

import com.blankj.utilcode.util.SPUtils;
import com.jn.lst.base.Constants;

/**
 * @Description:
 * @Author: geaosu
 * @CreateDate: 8/25/22 9:56 AM
 */
public class TokenManager {
    /**
     * 保存Token
     */
    public static void saveToken(String token) {
        SPUtils.getInstance().put(Constants.TOKEN, token, true);
    }

    /**
     * 获取Token
     */
    public static String getToken() {
        String token = SPUtils.getInstance().getString(Constants.TOKEN);
        return token;
    }

    /**
     * 清空Token
     */
    public static void removeToken() {
        SPUtils.getInstance().remove(Constants.TOKEN, true);
    }
}
