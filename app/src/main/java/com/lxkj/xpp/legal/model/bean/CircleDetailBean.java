package com.lxkj.xpp.legal.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 熊萍萍 on 2017/3/25/025.
 * 帖书详情实体类
 */

public class CircleDetailBean implements Serializable{

    /**
     * success : true
     * msg :
     * data : {"content":"好吃不上火","commentNum":3,"userHeadImg":"http://localhost:80/legal_file/user/info/170322-facd5c6f-c2c9-46cb-90b0-8926a108ee5d.jpg","userNickName":"小法","img":["http://localhost:80/legal_file/article/170323-385f8626-2702-4299-874e-67d53845b0ff.jpg","http://localhost:80/legal_file/article/170323-625c5c02-f48e-414f-8bf8-fc010e5ecbe9.jpg"],"publishTime":"1天前","comments":[{"discussUid":"1010001","discussNickName":"卡乐","replyNickName":"empty","replyUid":"empty","type":1,"content":"真的不上火吗"},{"discussUid":"1010002","discussNickName":"小法","replyNickName":"卡乐","replyUid":"1010001","type":1,"content":"骗你有糖吃"},{"discussUid":"1010001","discussNickName":"卡乐","replyNickName":"小法","replyUid":"1010002","type":1,"content":"给你糖"}],"objId":4}
     * code : 0000
     */

    private boolean success;
    private String msg;
    private DataBean data;
    private String code;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "CircleDetailBean{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", code='" + code + '\'' +
                '}';
    }

    public static class DataBean {
        /**
         * content : 好吃不上火
         * commentNum : 3
         * userHeadImg : http://localhost:80/legal_file/user/info/170322-facd5c6f-c2c9-46cb-90b0-8926a108ee5d.jpg
         * userNickName : 小法
         * img : ["http://localhost:80/legal_file/article/170323-385f8626-2702-4299-874e-67d53845b0ff.jpg","http://localhost:80/legal_file/article/170323-625c5c02-f48e-414f-8bf8-fc010e5ecbe9.jpg"]
         * publishTime : 1天前
         * comments : [{"discussUid":"1010001","discussNickName":"卡乐","replyNickName":"empty","replyUid":"empty","type":1,"content":"真的不上火吗"},{"discussUid":"1010002","discussNickName":"小法","replyNickName":"卡乐","replyUid":"1010001","type":1,"content":"骗你有糖吃"},{"discussUid":"1010001","discussNickName":"卡乐","replyNickName":"小法","replyUid":"1010002","type":1,"content":"给你糖"}]
         * objId : 4
         */

        private String content;
        private int commentNum;
        private String userHeadImg;
        private String userNickName;
        private String publishTime;
        private int objId;
        private List<String> img;
        private List<CommentsBean> comments;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public String getUserHeadImg() {
            return userHeadImg;
        }

        public void setUserHeadImg(String userHeadImg) {
            this.userHeadImg = userHeadImg;
        }

        public String getUserNickName() {
            return userNickName;
        }

        public void setUserNickName(String userNickName) {
            this.userNickName = userNickName;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public int getObjId() {
            return objId;
        }

        public void setObjId(int objId) {
            this.objId = objId;
        }

        public List<String> getImg() {
            return img;
        }

        public void setImg(List<String> img) {
            this.img = img;
        }

        public List<CommentsBean> getComments() {
            return comments;
        }

        public void setComments(List<CommentsBean> comments) {
            this.comments = comments;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "content='" + content + '\'' +
                    ", commentNum=" + commentNum +
                    ", userHeadImg='" + userHeadImg + '\'' +
                    ", userNickName='" + userNickName + '\'' +
                    ", publishTime='" + publishTime + '\'' +
                    ", objId=" + objId +
                    ", img=" + img +
                    ", comments=" + comments +
                    '}';
        }

//        public static class CommentsBean {
//            /**
//             * discussUid : 1010001
//             * discussNickName : 卡乐
//             * replyNickName : empty
//             * replyUid : empty
//             * type : 1
//             * content : 真的不上火吗
//             */
//
//            private String discussUid;
//            private String discussNickName;
//            private String replyNickName;
//            private String replyUid;
//            private int type;
//            private String content;
//
//            public String getDiscussUid() {
//                return discussUid;
//            }
//
//            public void setDiscussUid(String discussUid) {
//                this.discussUid = discussUid;
//            }
//
//            public String getDiscussNickName() {
//                return discussNickName;
//            }
//
//            public void setDiscussNickName(String discussNickName) {
//                this.discussNickName = discussNickName;
//            }
//
//            public String getReplyNickName() {
//                return replyNickName;
//            }
//
//            public void setReplyNickName(String replyNickName) {
//                this.replyNickName = replyNickName;
//            }
//
//            public String getReplyUid() {
//                return replyUid;
//            }
//
//            public void setReplyUid(String replyUid) {
//                this.replyUid = replyUid;
//            }
//
//            public int getType() {
//                return type;
//            }
//
//            public void setType(int type) {
//                this.type = type;
//            }
//
//            public String getContent() {
//                return content;
//            }
//
//            public void setContent(String content) {
//                this.content = content;
//            }
//
//            @Override
//            public String toString() {
//                return "CommentsBean{" +
//                        "discussUid='" + discussUid + '\'' +
//                        ", discussNickName='" + discussNickName + '\'' +
//                        ", replyNickName='" + replyNickName + '\'' +
//                        ", replyUid='" + replyUid + '\'' +
//                        ", type=" + type +
//                        ", content='" + content + '\'' +
//                        '}';
//            }
//        }
    }
}
