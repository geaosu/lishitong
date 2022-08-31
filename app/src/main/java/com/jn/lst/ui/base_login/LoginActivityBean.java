package com.jn.lst.ui.base_login;

/**
 * des: 登录
 */
public class LoginActivityBean {

    /**
     * msg : 操作成功
     * code : 200
     * success : true
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NjE1MDUwNDMsInVzZXJuYW1lIjoiMTg4MDk1ODU1MzMifQ.7f8w2Zuv4tV8xE5YkKQ7kXYsoP4P7TMRzk7h6dVHah4
     * refreshToken : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NjE2Nzc4NDMsInVzZXJuYW1lIjoiMTg4MDk1ODU1MzMifQ.Vn9n7PYQlgZbZM1HgaiM5a_4uZWn4zSU8xd7KaqaF1E
     */

    private String msg;
    private int code;
    private boolean success;
    private String token;
    private String refreshToken;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}