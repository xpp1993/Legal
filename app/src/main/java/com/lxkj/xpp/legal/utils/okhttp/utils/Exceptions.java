package com.lxkj.xpp.legal.utils.okhttp.utils;

/**
 * Created by 熊萍萍 on 2017/3/2/002.
 */
public class Exceptions
{
    public static void illegalArgument(String msg, Object... params)
    {
        throw new IllegalArgumentException(String.format(msg, params));
    }


}
