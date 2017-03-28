package com.lxkj.xpp.legal.utils.okhttp.builder;

import com.lxkj.xpp.legal.utils.okhttp.request.PostFileRequest;
import com.lxkj.xpp.legal.utils.okhttp.request.RequestCall;

import java.io.File;

import okhttp3.MediaType;

/**
 * Created by 熊萍萍 on 2017/3/2/002.
 */
public class PostFileBuilder extends OkHttpRequestBuilder<PostFileBuilder>
{
    private File file;
    private MediaType mediaType;


    public OkHttpRequestBuilder file(File file)
    {
        this.file = file;
        return this;
    }

    public OkHttpRequestBuilder mediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build()
    {
        return new PostFileRequest(url, tag, params, headers, file, mediaType,id).build();
    }


}
