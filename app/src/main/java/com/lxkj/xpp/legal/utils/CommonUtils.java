package com.lxkj.xpp.legal.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lxkj.xpp.legal.app.MyApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    /** 调度剪裁intent
    * @param dataUri 选中图片的uri路径
    * @return 返回意图
    */
    public static Intent getPhotoZoomIntent(Uri dataUri) {
        Intent intent = new Intent();
        //系统裁剪活动
        intent.setAction("com.android.camera.action.CROP");
        //设置裁剪的源图片和类型
        intent.setDataAndType(dataUri, "image/*");
        //打开裁剪
        intent.putExtra("crop", "true");
        // 裁剪框比例，宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 输出图片大小，剪裁图片的宽高
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        //返回结果,但是大图片很有可能直接内存溢出
//		intent.putExtra("return-data", true);
        //黑边【可以缩放】
        intent.putExtra("scale", true);
        //黑边【可以缩放】
        intent.putExtra("scaleUpIfNeeded", true);
        return intent;
    }
    /**
     * 将file转为数组
     *
     * @param file
     * @return
     */
    public static byte[] changeFileToByte(File file) {
        byte[] buffer = null;
        try {

            if (file == null || !file.exists()) {
                return null;
            }
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            byte[] b = new byte[1024];
            int n;

            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
                bos.flush();
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 使用系统当前时间，产生一个临时文件，用户缓存
     *
     * @param fileType 缓存文件的格式  如：jpg png
     * @return
     */
    public static File getTempFile(String fileType) {
        File file = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("'TEMP_FILE'_yyyy-MM-dd-HH-mm-ss");
            String tempFileName = dateFormat.format(new Date());
            try {
                file = File.createTempFile(tempFileName, fileType, Environment.getExternalStorageDirectory());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
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

    /**
     * 通过反射的方式获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getContext().getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
/**
 * Android系统更改状态栏字体颜色
 */
    /**
     * MIUI6+
     */
    public static boolean setMiuiStatusBarDarkMode(Activity activity, boolean darkmode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Flyme4+ 魅族
     */
    public static boolean setMeizuStatusBarDarkIcon(Activity activity, boolean dark) {
        boolean result = false;
        if (activity != null) {
            try {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                activity.getWindow().setAttributes(lp);
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }

    /**
     * 白底黑字，改变状态栏的颜色为深色
     */
    public static void setMystatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setMiuiStatusBarDarkMode(activity, true);
            setMeizuStatusBarDarkIcon(activity, true);
            // 在Android6.0才开始支持SYSTEM_UI_FLAG_LIGHT_STATUS_BAR可以将状态栏图标改为灰色。
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }
}