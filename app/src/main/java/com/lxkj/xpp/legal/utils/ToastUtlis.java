package com.lxkj.xpp.legal.utils;

import android.widget.Toast;

/**
 * Created by 熊萍萍 on 2017/02/3/02.
 * Toast 工具类
 */
public class ToastUtlis {
    /**
     * 在UI线程弹出Toast
     * @param content
     */
    public static void showToastInUIThread(String content) {
        Toast.makeText(CommonUtils.getContext(), content, Toast.LENGTH_SHORT).show();
    }

    /**
     * 在在线程中弹出Toast
     *
     * @param content
     */
    public static void showToastInThread(final String content) {
        ThreadUtils.runTaskInUIThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CommonUtils.getContext(), content, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
