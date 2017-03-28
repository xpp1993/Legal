package com.lxkj.xpp.legal.utils.okhttp.callback;

/**
 * Created by 熊萍萍 on 2017/3/2/002.
 * 解析器接口
 */
public interface IGenericsSerializator {
    <T> T transform(String response, Class<T> classOfT);
}
