package com.lxkj.xpp.legal.utils.okhttp.builder;

import com.lxkj.xpp.legal.utils.okhttp.request.OtherRequest;
import com.lxkj.xpp.legal.utils.okhttp.request.RequestCall;

import okhttp3.RequestBody;

/**
 * Created by 熊萍萍 on 2017/3/2/002.
 * DELETE、PUT、PATCH等其他方法
 */
public class OtherRequestBuilder extends OkHttpRequestBuilder<OtherRequestBuilder>
{
    private RequestBody requestBody;
    private String method;
    private String content;

    public OtherRequestBuilder(String method)
    {
        this.method = method;
    }

    @Override
    public RequestCall build()
    {
        return new OtherRequest(requestBody, content, method, url, tag, params, headers,id).build();
    }

    public OtherRequestBuilder requestBody(RequestBody requestBody)
    {
        this.requestBody = requestBody;
        return this;
    }

    public OtherRequestBuilder requestBody(String content)
    {
        this.content = content;
        return this;
    }


}
