package com.lxkj.xpp.legal.listener;

import android.os.Bundle;

/**
 * Created by 熊萍萍 on 2017/3/7/007.
 */

public interface CallBackListener<T> {
    /**
     * 获取数据失败
     *
     * @param error
     * @param i
     */
    void onFail(String error, int i);

    /**
     * 获取数据成功
     *
     * @param response 返回的信息
     * @param bundle       请求的参数bundle
     * @param id
     */

    void onSuccess(T response, Bundle bundle, int id);

    /**
     * 输入错误
     *
     * @param msg Toast的提示
     * @param i
     */

    void onError(String msg, int i);
}
