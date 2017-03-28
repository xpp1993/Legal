package com.lxkj.xpp.legal.utils.okhttp.builder;

import java.util.Map;

/**
 * Created by 熊萍萍 on 2017/3/2/002.
 */
public interface HasParamsable
{
    OkHttpRequestBuilder params(Map<String, String> params);
    OkHttpRequestBuilder addParams(String key, String val);
}
