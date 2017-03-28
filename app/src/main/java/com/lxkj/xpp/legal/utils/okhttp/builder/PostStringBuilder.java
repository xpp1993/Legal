package com.lxkj.xpp.legal.utils.okhttp.builder;

import com.lxkj.xpp.legal.utils.okhttp.request.PostStringRequest;
import com.lxkj.xpp.legal.utils.okhttp.request.RequestCall;

import okhttp3.MediaType;

/**
 * Created by 熊萍萍 on 2017/3/2/002.
 */
public class PostStringBuilder extends OkHttpRequestBuilder<PostStringBuilder>
{
    private String content;
    private MediaType mediaType;


    public PostStringBuilder content(String content)
    {
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build()
    {
        return new PostStringRequest(url, tag, params, headers, content, mediaType,id).build();
    }


}
