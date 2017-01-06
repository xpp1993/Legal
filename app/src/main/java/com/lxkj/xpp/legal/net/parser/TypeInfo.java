package com.lxkj.xpp.legal.net.parser;

import android.util.Log;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by 熊萍萍 on 2016/12/15/015.
 * 用来封装泛型相关属性
 */

public class TypeInfo {
    // componentType extends rawType (T extends ?)用来限制泛型
    //Type 泛型对象类型
    private Class<?> componentType;
    //Type所属对象类型
    private Class<?> rawType;
    //type
    private Type type;//泛型类型
    private static final String TAG = TypeInfo.class.getSimpleName();

    private TypeInfo(Class<?> rawType, Class<?> componentType) {
        this.componentType = componentType;
        this.rawType = rawType;
    }

    public static TypeInfo createArrayType(Class<?> componentType) {
        return new TypeInfo(Array.class, componentType);
    }

    public static TypeInfo createNarmalType(Class<?> componentType) {
        return new TypeInfo(null, componentType);
    }

    public static TypeInfo createParameterizedType(Class<?> rawType, Class<?> componentType) {
        return new TypeInfo(rawType, componentType);
    }

    public TypeInfo(Type type) {
        this.type = type;
        if (type instanceof ParameterizedType) {
            //向下转型
            ParameterizedType parameterizedType = (ParameterizedType) type;
            this.rawType = (Class<?>) parameterizedType.getRawType();
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            this.componentType = (Class<?>) actualTypeArguments[0];
        } else if (type instanceof GenericArrayType) {
            this.rawType = Array.class;
            this.componentType = (Class<?>) ((GenericArrayType) type).getGenericComponentType();
        } else {
            this.componentType = (Class<?>) type;
        }
        Log.e(TAG, "type--->" + type.toString() + " rawType--->" + rawType.toString() + " componentType--->" + componentType.toString());
    }

    public Type getType() {
        return type;
    }

    public Class<?> getComponentType() {
        return componentType;
    }

    public Class<?> getRawType() {
        return rawType;
    }
}
