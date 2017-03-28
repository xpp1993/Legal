package com.lxkj.xpp.legal.model.bean;

/**
 * Created by 熊萍萍 on 2017/3/22/022.
 * 评论信息实体类
 * 成员变量以toString信息显示在textView中
 */

public class CommentEntity {
    /**
     * 信息标志位
     */
    public Object key;
    /**
     * 发信人
     */
    public Object form;
    /**
     * 收信人
     */
    public Object target;
    /**
     * 信息体
     */
    public Object message;

    public CommentEntity(Object key, Object form, Object message) {
        this(key, form, null, message);
    }

    public CommentEntity(Object key, Object form, Object target, Object message) {
        this.key = key;
        this.form = form;
        this.target = target;
        this.message = message;
    }

    @Override
    public String toString() {
        return "key:" + key + " from:" + form + " target:" + target + " message:" + message;
    }
}
