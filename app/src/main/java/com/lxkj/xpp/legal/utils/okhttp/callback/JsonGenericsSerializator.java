package com.lxkj.xpp.legal.utils.okhttp.callback;

import com.google.gson.Gson;

/**
 * Created by 熊萍萍 on 2017/3/7/007.
 * 解析器
 */

public class JsonGenericsSerializator implements IGenericsSerializator {
    Gson mGson = new Gson();

    @Override
    public <T> T transform(String response, Class<T> classOfT) {
        return mGson.fromJson(response, classOfT);
    }
}
