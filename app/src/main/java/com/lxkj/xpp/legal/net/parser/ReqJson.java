package com.lxkj.xpp.legal.net.parser;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 熊萍萍 on 2016/12/15/015.
 * 声明一个json解析工具类ReqJsonUtils。java 主要用于通过TypeInfo相关属性进行不同类型的json解析
 */

public class ReqJson {
    //基本类型关系Map
    private static final Map primitiveWrapperTypeMap = new HashMap(8);
    private static final String TAG = ReqJson.class.getSimpleName();

    static {
        //添加基本类型
        primitiveWrapperTypeMap.put(Boolean.class, boolean.class);
        primitiveWrapperTypeMap.put(Byte.class, byte.class);
        primitiveWrapperTypeMap.put(Character.class, char.class);
        primitiveWrapperTypeMap.put(Double.class, double.class);
        primitiveWrapperTypeMap.put(Float.class, float.class);
        primitiveWrapperTypeMap.put(Integer.class, int.class);
        primitiveWrapperTypeMap.put(Long.class, long.class);
        primitiveWrapperTypeMap.put(Short.class, short.class);
    }

    public static <T> void parseHttpResult(TypeInfo type, int messageWhat, String jsonData, Handler handler) {
        //处理Void类型的返回值
        if (Void.class.isAssignableFrom(type.getComponentType())) {
            Log.e(TAG, "Void.class.isAssignableFrom(type.getComponentType()" + type.getComponentType());
            // return null;
        }
        //获取当前type所属对象的数据类型
        Class<?> rawType = type.getRawType();
        //是否是array
        boolean isArray = rawType != null && Array.class.isAssignableFrom(rawType);
        //是否是Collection
        boolean isCollection = rawType != null && Collection.class.isAssignableFrom(rawType);
        //是否是Map
        boolean isMap = rawType != null && Map.class.isAssignableFrom(rawType);
        //获取泛型类型
        Class<?> componentType = type.getComponentType();
        //声明结果类型
        T result = null;
        if (isArray) {
            result = (T) JSON.parseArray(jsonData, componentType).toArray();
        } else if (isCollection) {
            result = (T) JSON.parseArray(jsonData, componentType);
        } else if (isMap) {
            result = JSON.parseObject(jsonData, type.getType());
        } else if (componentType.isAssignableFrom(String.class)) {//处理字符串返回类型
            // return (T) jsonData;
            Message message = handler.obtainMessage();
            message.what = messageWhat;
            message.obj = jsonData;
            handler.sendMessage(message);
        } else {
            //如果接口的返回类型是简单类型，则会封装成一个json对象，真正的对象存在value的属性上
            if (isPrimitiveOrWrapper(componentType)) {
                result = (T) JSON.parseObject(jsonData);
            } else {
                //处理自定义的对象
                result = (T) JSON.parseObject(jsonData, componentType);
            }
        }
        Log.e(TAG, "isArray:" + isArray + " isCollection:" + isCollection + " isMAp:" + isMap);
        Log.e(TAG, "rawType:" + rawType + " componentType:" + componentType + " result:" + result);
        Message message = handler.obtainMessage();
        message.what = messageWhat;
        message.obj = result;
        handler.sendMessage(message);
        // return result;
    }

    /**
     * 判读是否是基本的数据类型
     *
     * @param clazz
     * @return
     */
    public static boolean isPrimitiveOrWrapper(Class clazz) {
        return (clazz.isPrimitive() || isPrimitiveWrapper(clazz));
    }

    /**
     * 判断是否是基本数据类型
     *
     * @param clazz
     * @return
     */
    public static boolean isPrimitiveWrapper(Class clazz) {
        return primitiveWrapperTypeMap.containsKey(clazz);
    }

}
