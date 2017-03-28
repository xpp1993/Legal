package com.lxkj.xpp.legal.utils.okhttp.utils;

import android.util.Log;

/**
 * Created by 熊萍萍 on 2017/3/2/002.
 */
public class L
{
    private static boolean debug = false;

    public static void e(String msg)
    {
        if (debug)
        {
            Log.e("OkHttp", msg);
        }
    }

}

