package com.jn.lst.base;

/**
 * @des: EventBus的事件集合
 * @Author:
 * @time: 2022年08月20日
 */
public class DataEvent {

    public Type type;
    public String tag;
    public Object data;
    public Code code;
    public int codes;
    public static int DEVICE_FIRM = -1;

    // view事件专用(点击事件+长按事件)
    public DataEvent(Type type) {
        this.type = type;
    }

    public DataEvent(Type type, Object data) {
        this.type = type;
        this.data = data;
    }

    public DataEvent(Type type, String tag, Object data) {
        this.type = type;
        this.tag = tag;
        this.data = data;
    }

    public DataEvent(Type type, Object data, Code code) {
        this.type = type;
        this.data = data;
        this.code = code;
    }

    public DataEvent(Type type, Object data, int codes) {
        this.type = type;
        this.data = data;
        this.codes = codes;
    }

    public enum Code {
        NO_NET_WORLK_CONTENT,               // 没有网路连接
    }

    public enum Type {
        /*******************************************************************  base */
        // 网络请求发生异常
        NET_WORK_ERR,
        TOKEN_BE_OVERDUE,// token过期了

        /*******************************************************************  文件 */
        // 文件 - 上传
        FILE_UPLOAD_SUCC,
        FILE_UPLOAD_ERR,

        // 文件 - 下载
        FILE_DOWNLOAD_SUCC,
        FILE_DOWNLOAD_ERR,

        /*******************************************************************  登录、注册  */

        // 获取加密公钥
        SECRET_KEY_SUCC,
        SECRET_KEY_ERR,

        // 注册 - 检测账号是否被注册
        REGISTER_CHECK_SUCC,
        REGISTER_CHECK_ERR,

        // 注册
        REGISTER_SUCC,
        REGISTER_ERR,

        // 登录
        LOGIN_SUCC,
        LOGIN_ERR,

        // 获取用户信息
        GET_USER_INFO_SUCC,
        GET_USER_INFO_ERR,

        REFRESH_TOKEN_SUCC,
        REFRESH_TOKEN_ERR,
        // 版本更新
        UPLOA_AP_SUCC,
        UPLOA_AP_ERRO,

        /*******************************************************************  主页面 */

        MAIN_LIST_SUCC,
        MAIN_LIST_ERR,

        MAIN_GONG_GAO_SUCC,
        MAIN_GONG_GAO_ERR,

        HISTORY_EVENT_LIST_SUCC,
        HISTORY_EVENT_LIST_ERR,

        INFO_SUCC,
        INFO_ERR,

        ADD_ZU_JI_SUCC,
        ADD_ZU_JI_ERR,

        SEARCH_SUCC,
        SEARCH_ERR,

    }

}
