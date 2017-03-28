package com.lxkj.xpp.legal.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 熊萍萍 on 2017/3/25/025.
 * 帖书列表详情类
 */

public class CircleListBean implements Serializable {

    /**
     * success : true
     * msg :
     * data : {"dataList":[{"content":"好吃不上火","commentNum":0,"userHeadImg":"http://localhost:80/legal_file/user/info/170322-facd5c6f-c2c9-46cb-90b0-8926a108ee5d.jpg","userNickName":"小法","img":["http://localhost:80/legal_file/article/170323-385f8626-2702-4299-874e-67d53845b0ff.jpg","http://localhost:80/legal_file/article/170323-625c5c02-f48e-414f-8bf8-fc010e5ecbe9.jpg"],"publishTime":"1天前","comments":[{"discussUid":"1010001","discussNickName":"卡乐","replyNickName":"empty","replyUid":"empty","type":1,"content":"真的不上火吗"},{"discussUid":"1010002","discussNickName":"小法","replyNickName":"卡乐","replyUid":"1010001","type":1,"content":"骗你有糖吃"},{"discussUid":"1010001","discussNickName":"卡乐","replyNickName":"小法","replyUid":"1010002","type":1,"content":"给你糖"}],"objId":4},{"content":"生活不止眼前的苟且，还有诗和远方的田野","commentNum":0,"userHeadImg":"http://localhost:80/legal_file/user/real_name/170309-b3b85597-177a-4b1b-b98d-0dcee02a4b65.jpg","userNickName":"卡乐","img":null,"publishTime":"03-22","comments":null,"objId":2},{"content":"拉钩上的H5基本都是汉子,(⊙﹏⊙)b","commentNum":0,"userHeadImg":"http://localhost:80/legal_file/user/real_name/170309-b3b85597-177a-4b1b-b98d-0dcee02a4b65.jpg","userNickName":"卡乐","img":["http://localhost:80/legal_file/article/170323-385f8626-2702-4299-874e-67d53845b0ff.jpg","http://localhost:80/legal_file/article/170323-625c5c02-f48e-414f-8bf8-fc010e5ecbe9.jpg"],"publishTime":"03-22","comments":null,"objId":1}],"pageNo":1,"pageSize":10,"total":3,"totalPage":1}
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
        return "CircleListBean{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", code='" + code + '\'' +
                '}';
    }

    public static class DataBean {
        /**
         * dataList : [{"content":"好吃不上火","commentNum":0,"userHeadImg":"http://localhost:80/legal_file/user/info/170322-facd5c6f-c2c9-46cb-90b0-8926a108ee5d.jpg","userNickName":"小法","img":["http://localhost:80/legal_file/article/170323-385f8626-2702-4299-874e-67d53845b0ff.jpg","http://localhost:80/legal_file/article/170323-625c5c02-f48e-414f-8bf8-fc010e5ecbe9.jpg"],"publishTime":"1天前","comments":[{"discussUid":"1010001","discussNickName":"卡乐","replyNickName":"empty","replyUid":"empty","type":1,"content":"真的不上火吗"},{"discussUid":"1010002","discussNickName":"小法","replyNickName":"卡乐","replyUid":"1010001","type":1,"content":"骗你有糖吃"},{"discussUid":"1010001","discussNickName":"卡乐","replyNickName":"小法","replyUid":"1010002","type":1,"content":"给你糖"}],"objId":4},{"content":"生活不止眼前的苟且，还有诗和远方的田野","commentNum":0,"userHeadImg":"http://localhost:80/legal_file/user/real_name/170309-b3b85597-177a-4b1b-b98d-0dcee02a4b65.jpg","userNickName":"卡乐","img":null,"publishTime":"03-22","comments":null,"objId":2},{"content":"拉钩上的H5基本都是汉子,(⊙﹏⊙)b","commentNum":0,"userHeadImg":"http://localhost:80/legal_file/user/real_name/170309-b3b85597-177a-4b1b-b98d-0dcee02a4b65.jpg","userNickName":"卡乐","img":["http://localhost:80/legal_file/article/170323-385f8626-2702-4299-874e-67d53845b0ff.jpg","http://localhost:80/legal_file/article/170323-625c5c02-f48e-414f-8bf8-fc010e5ecbe9.jpg"],"publishTime":"03-22","comments":null,"objId":1}]
         * pageNo : 1
         * pageSize : 10
         * total : 3
         * totalPage : 1
         */

        private int pageNo;
        private int pageSize;
        private int total;
        private int totalPage;
        private List<DataListBean> dataList;

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<DataListBean> getDataList() {
            return dataList;
        }

        public void setDataList(List<DataListBean> dataList) {
            this.dataList = dataList;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "pageNo=" + pageNo +
                    ", pageSize=" + pageSize +
                    ", total=" + total +
                    ", totalPage=" + totalPage +
                    ", dataList=" + dataList +
                    '}';
        }

        public static class DataListBean {
            /**
             * content : 好吃不上火
             * commentNum : 0
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
            private  String uid;
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

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            @Override
            public String toString() {
                return "DataListBean{" +
                        "content='" + content + '\'' +
                        ", commentNum=" + commentNum +
                        ", userHeadImg='" + userHeadImg + '\'' +
                        ", userNickName='" + userNickName + '\'' +
                        ", publishTime='" + publishTime + '\'' +
                        ", objId=" + objId +
                        ", uid='" + uid + '\'' +
                        ", img=" + img +
                        ", comments=" + comments +
                        '}';
            }

            @Override
            public boolean equals(Object o) {
                if (o instanceof DataListBean) {
                    if (objId == ((DataListBean) o).objId) {
                        return true;
                    }
                }
                return false;
            }

//            public static class CommentsBean {
//                /**
//                 * discussUid : 1010001
//                 * discussNickName : 卡乐
//                 * replyNickName : empty
//                 * replyUid : empty
//                 * type : 1
//                 * content : 真的不上火吗
//                 */
//
//                private String discussUid;
//                private String discussNickName;
//                private String replyNickName;
//                private String replyUid;
//                private int type;
//                private String content;
//
//                public CommentsBean() {
//                }
//
//                public CommentsBean(String discussUid, String discussNickName, String replyNickName, String replyUid, int type, String content) {
//                    this.discussUid = discussUid;
//                    this.discussNickName = discussNickName;
//                    this.replyNickName = replyNickName;
//                    this.replyUid = replyUid;
//                    this.type = type;
//                    this.content = content;
//                }
//
//                public String getDiscussUid() {
//                    return discussUid;
//                }
//
//                public void setDiscussUid(String discussUid) {
//                    this.discussUid = discussUid;
//                }
//
//                public String getDiscussNickName() {
//                    return discussNickName;
//                }
//
//                public void setDiscussNickName(String discussNickName) {
//                    this.discussNickName = discussNickName;
//                }
//
//                public String getReplyNickName() {
//                    return replyNickName;
//                }
//
//                public void setReplyNickName(String replyNickName) {
//                    this.replyNickName = replyNickName;
//                }
//
//                public String getReplyUid() {
//                    return replyUid;
//                }
//
//                public void setReplyUid(String replyUid) {
//                    this.replyUid = replyUid;
//                }
//
//                public int getType() {
//                    return type;
//                }
//
//                public void setType(int type) {
//                    this.type = type;
//                }
//
//                public String getContent() {
//                    return content;
//                }
//
//                public void setContent(String content) {
//                    this.content = content;
//                }
//
//                @Override
//                public String toString() {
//                    return "CommentsBean{" +
//                            "discussUid='" + discussUid + '\'' +
//                            ", discussNickName='" + discussNickName + '\'' +
//                            ", replyNickName='" + replyNickName + '\'' +
//                            ", replyUid='" + replyUid + '\'' +
//                            ", type=" + type +
//                            ", content='" + content + '\'' +
//                            '}';
//                }
//            }
        }
    }
}
