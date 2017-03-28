package com.lxkj.xpp.legal.utils;


import android.os.Handler;
import android.os.Looper;

/**
 * Created by 熊萍萍 on 2017/02/3/02.
 * 线程工具类
 */
public class ThreadUtils {
    // 在非UI线程中执行
    public static void runTaskInThread(Runnable task) {
        new Thread(task).start();
    }

    // 在UI线程中执行
    public static Handler handler = new Handler(Looper.getMainLooper());

    public static void runTaskInUIThread(Runnable task) {
        handler.post(task);
    }
}

