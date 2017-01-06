package com.lxkj.xpp.legal.net.callback;

/**
 * Created by 熊萍萍 on 2016/12/13/013.
 */

public interface ReqProgressCallBack<T> extends ReCallBack<T> {
    /**
     * 响应进度更新
     */
    void onProgress(int requestCode,long total, long current);
}
