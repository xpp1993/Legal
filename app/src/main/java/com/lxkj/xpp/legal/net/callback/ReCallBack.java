package com.lxkj.xpp.legal.net.callback;

/**
 * Created by 熊萍萍 on 2016/12/12/012.
 */

/**
 * 请求响应
 * @param <T>
 */
public interface ReCallBack<T> {
    /**
     * 响应成功
     */
    void onReqSuccess(int requestCode, T result);

    /**
     * 响应失败
     */
    void onReqFailed(int requestCode, String errorMsg);
}
