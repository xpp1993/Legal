package com.lxkj.xpp.legal.utils;

import okhttp3.MediaType;

/**
 * Created by 熊萍萍 on 2016/12/16/016.
 */

public final class ParameterUtils {
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");//mediaType，这个需要和服务器保持一致
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");//mediaType，这个需要和服务器保持一致
    public static final MediaType MEDIA_OBJECT_STREAM = MediaType.parse("application/octet-stream");
    public static final int TYPE_GET = 0;//get请求
    public static final int TYPE_POST_JSON = 1;//post请求参数为json
    public static final int TYPE_POST_FORM = 2;//post请求为表单
    //mdiatype 这个需要和服务端保持一致 你需要看下你们服务器设置的ContentType 是不是这个，他们设置的是哪个 我们要和他们保持一致
    public static final String BASE_URL = "http://139.129.217.190:8080";//根地址
}
