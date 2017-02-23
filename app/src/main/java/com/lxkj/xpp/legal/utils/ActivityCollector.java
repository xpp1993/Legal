package com.lxkj.xpp.legal.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.lxkj.xpp.legal.activity.LoginActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 熊萍萍 on 2017/2/23/023.
 * 此类用作收集和销毁activity的公共类.
 */

public class ActivityCollector {
    public static List<FragmentActivity> activities = new ArrayList<FragmentActivity>();

    /**
     * 添加activity到管理器
     *
     * @param activity
     */
    public static void addActivity(FragmentActivity activity) {
        activities.add(activity);
    }

    /**
     * 移除activity
     *
     * @param activity
     */
    public static void removeActivity(FragmentActivity activity) {
        activities.remove(activity);
    }

    /**
     * 销毁所有的activity
     */
    public static void finishAll() {
        for (FragmentActivity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
