package com.jn.lst.base.bean;

/**
 * @des: 网络请求 - javaBean
 * @Author:
 * @time: 2022年08月20日
 */
public class BaseBean {
    private boolean success;
    private int code;
    private String msg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
