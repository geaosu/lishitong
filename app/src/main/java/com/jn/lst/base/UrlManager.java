package com.jn.lst.base;

public class UrlManager {

    /*******************************************************************  baseUrl  */
    public static final String[] urlArr = {
            "https://405690ox08.zicp.fun/jeeplus",   // 0
            "http://121.36.64.63:8081/jeeplus",      // 1
    };

    public interface RequestUrl {
        String baseUrl = urlArr[1];

        /*******************************************************************  版本更新  */
        // 版本更新 - 检测更新
        String uploadApk = baseUrl + "";

        /*******************************************************************  登录、注册  */
        // 登录 - 获取RSA公钥
        String getAccountKey = baseUrl + "";
        // 登录 - 获取验证吗
        String getValidaCode = baseUrl + "";
        // 注册 - 检测账号是否被注册
        String registerCheckUrl = baseUrl + "/app/user/checkLoginName";
        // 注册
        String registerUrl = baseUrl + "/app/user/register";
        // 登录
        String loginUrl = baseUrl + "/app/user/login";
        // 登录 - 刷新token
        String refreshToken = baseUrl + "";
        // 登录 - 获取用户信息
        String getUserInfoUrl = baseUrl + "";
        /*******************************************************************  文件  */
        // 文件 - 上传
        String uploadFileUrl = baseUrl + "";
        // 文件 - 下载
        String downloadFileUrl = baseUrl + "";

        /*******************************************************************  字典  */
        // 字典 - 从服务器获取全部字典数据
        String getDictDataAll = baseUrl + "";
        // 字典 - 获取单个字典
        String getDictDataOne = baseUrl + "";
        // 字典 - 根据key获取value
        String getDictValueByKey = baseUrl + "";

        /*******************************************************************  主界面  */
        // 主页 - 列表数据
        String mainListUrl = baseUrl + "/app/api/homeList";
        // 主页 - 公告
        String gongGaoUrl = baseUrl + "/app/api/notice";
        // 细化 - 列表数据
        String historyEventListUrl = baseUrl + "/app/api/refinementList";
        // 详情
        String infoUrl = baseUrl + "/app/api/checkDetails";
        // 添加足迹
        String addZuJiUrl = baseUrl + "/app/api/instFootprint";
        // 搜索
        String searchUrl = baseUrl + "/app/api/search";

    }
}










