package com.lxkj.xpp.legal.utils.okhttp.callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by 熊萍萍 on 2017/3/2/002.
 * 结果返回String形式
 */
public abstract class StringCallback extends Callback<String>
{
    @Override
    public String parseNetworkResponse(Response response, int id) throws IOException
    {
        return response.body().string();
    }
}
