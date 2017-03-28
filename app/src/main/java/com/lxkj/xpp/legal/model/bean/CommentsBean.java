package com.lxkj.xpp.legal.model.bean;

import java.io.Serializable;

/**
 * Created by 熊萍萍 on 2017/3/27/027.
 * 帖书评论
 */

public class CommentsBean implements Serializable{

    /**
     * discussUid : 1010001
     * discussNickName : 卡乐
     * replyNickName : empty
     * replyUid : empty
     * type : 1
     * content : 真的不上火吗
     */

    private String discussUid;
    private String discussNickName;
    private String replyNickName;
    private String replyUid;
    private int type;
    private String content;
    private int objId ;
    public CommentsBean() {
    }

    public CommentsBean(String discussUid, String discussNickName, String replyNickName, String replyUid, int type, String content, int objId) {
        this.discussUid = discussUid;
        this.discussNickName = discussNickName;
        this.replyNickName = replyNickName;
        this.replyUid = replyUid;
        this.type = type;
        this.content = content;
        this.objId = objId;
    }

    public int getObjId() {
        return objId;
    }

    public void setObjId(int objId) {
        this.objId = objId;
    }

    public String getDiscussUid() {
        return discussUid;
    }

    public void setDiscussUid(String discussUid) {
        this.discussUid = discussUid;
    }

    public String getDiscussNickName() {
        return discussNickName;
    }

    public void setDiscussNickName(String discussNickName) {
        this.discussNickName = discussNickName;
    }

    public String getReplyNickName() {
        return replyNickName;
    }

    public void setReplyNickName(String replyNickName) {
        this.replyNickName = replyNickName;
    }

    public String getReplyUid() {
        return replyUid;
    }

    public void setReplyUid(String replyUid) {
        this.replyUid = replyUid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CommentsBean{" +
                "discussUid='" + discussUid + '\'' +
                ", discussNickName='" + discussNickName + '\'' +
                ", replyNickName='" + replyNickName + '\'' +
                ", replyUid='" + replyUid + '\'' +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", objId=" + objId +
                '}';
    }
}
