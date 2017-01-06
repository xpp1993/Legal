package com.lxkj.xpp.legal.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import com.lxkj.xpp.legal.app.MyApplication;

import java.io.File;
import java.util.HashMap;

/**
 * Created by 熊萍萍 on 2016/12/19/019.
 * 公共方法类
 */

public class CommonUtils {
    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            //获取手机所有的连接管理对象（包括WiFi，net等连接管理）
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isConnectedOrConnecting();
            }
        }
        return false;
    }

    /**
     * 构建参数map对象的工具方法
     *
     * @param keys
     * @param values
     * @return
     */
    public static HashMap<String, String> getParameterMap(String keys[], String... values) {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], values[i]);
        }
        return map;
    }

    public static Context getContext() {
        return MyApplication.applicationContext;
    }

    /**
     * 获取文件版本
     *
     * @return
     */
    public static int getVerCode() {
        int verCode = -1;
        try {
            verCode = getContext().getPackageManager().getPackageInfo("com.lxkj.xpp.legal", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verCode;
    }
    //打开APK程序代码

    /**
     * 打开 apk
     *
     * @param file
     */
    public static void openFile(File file) {
        Log.e("OpenFile", file.getName());
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        getContext().startActivity(intent);
    }
//
//    /**
//     * 获取processAppName
//     * @param pID
//     * @return
//     */
//    public static String getAppName(int pID) {
//        String processName = null;
//        ActivityManager am = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
//        List l = am.getRunningAppProcesses();
//        Iterator i = l.iterator();
//        PackageManager pm = getContext().getPackageManager();
//        while (i.hasNext()) {
//            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) i.next();
//            try {
//                if (info.pid == pID) {
//                    processName = info.processName;
//                }
//            } catch (Exception e) {
//                Log.d("Process", "Error>>:" + e.toString());
//            }
//        }
//        return processName;
//    }
}