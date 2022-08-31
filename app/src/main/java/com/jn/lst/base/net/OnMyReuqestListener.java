package com.jn.lst.base.net;

/**
 * @Description:
 */
public interface OnMyReuqestListener {

    void onFailure(String err);

    void onResponse(String data);

}
