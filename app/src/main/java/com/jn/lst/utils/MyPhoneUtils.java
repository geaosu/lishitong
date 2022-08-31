package com.jn.lst.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 电话号码工具类
 */
public class MyPhoneUtils {
    // 是不是手机号码
    public static boolean isPhone(String phone) {
        String regExp = "^((1[3,4,5,6,7,8,9][0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phone);
        return m.matches();
    }
}
