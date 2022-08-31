package com.jn.lst.base.net;

/**
 * @Description:
 * @Author: geaosu
 * @CreateDate: 8/27/22 8:51 PM
 */
public interface OnMyReuqestListener {

    void onFailure(String err);

    void onResponse(String data);

}
