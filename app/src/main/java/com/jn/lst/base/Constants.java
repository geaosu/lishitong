package com.jn.lst.base;

import android.os.Environment;

/**
 * @des: 常量
 * @Author:
 * @time: 2022年08月20日
 */
public class Constants {
    public static final String FILE_PROVIDER = "com.elon.yczh.provider";// 选择全部文件
    //登录时用的加密秘钥，其他地方不用，不要轻易改动
    public static String loginSecretKey;
    /*******************************************************************  登录 */
    public static final String TOKEN = "TOKEN";// token
    public static final String LOGIN_NAME = "LOGIN_NAME";// 登录名
    public static final String LOGIN_PWD = "LOGIN_PWD";// 登录密码
    public static final String LOGIN_REMEMBER_NAME_PWD = "LOGIN_REMEMBER_NAME_PWD";// 记住账号密码

    /*******************************************************************  分页加载 */
    public static final String PAGE_NUMBER_KEY = "pageNo";
    public static final String PAGE_SIZE_KEY = "pageSize";
    public static final int PAGE_SIZE_VALUE = 20;// 每页加载20条数据

    /*******************************************************************  日期信息 */
    public static final String DATE_ALL = "yyyy-MM-dd";
    public static final String DATE_YEAR_MOTH = "yyyy-MM";
    public static final String DATE_YEAR = "yyyy";
    public static final String DATE_TIME_ALL = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME = "yyyy-MM-dd HH:mm";

    /*******************************************************************  日志信息 */
    public static final String CACHE_DIRECTORY = "YinChuanZongZhi";
    public static final String CACHE_PATH = Environment.getExternalStorageDirectory() + "/" + CACHE_DIRECTORY + "/";
    public static final String CACHE_IMAGE = CACHE_PATH + "image/";    // 下载OFD到本地的路径
    public static final String CACHE_FILE = CACHE_PATH + "file/";    // 下载OFD到本地的路径
    public static final String CRASH_DIRECTORY = CACHE_PATH + "crashLog/";

    /*******************************************************************  用户信息 */
    public static final String USER_INFO = "USER_INFO";// 用户信息



}
