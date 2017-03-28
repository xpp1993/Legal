package com.lxkj.xpp.legal.utils.okhttp.builder;

import com.lxkj.xpp.legal.utils.okhttp.OkHttpUtils;
import com.lxkj.xpp.legal.utils.okhttp.request.OtherRequest;
import com.lxkj.xpp.legal.utils.okhttp.request.RequestCall;

/**
 *Created by 熊萍萍 on 2017/3/2/002.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
