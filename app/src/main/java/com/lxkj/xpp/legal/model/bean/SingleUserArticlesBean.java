package com.lxkj.xpp.legal.model.bean;

import java.util.List;

/**
 * Created by 熊萍萍 on 2017/3/29/029.
 * 获取单个用户贴书列表（含用户自己）、后台自动识别
 */

public class SingleUserArticlesBean {

    /**
     * success : true
     * msg :
     * data : {"dataList":[{"content":"今天，玄米茶","commentNum":1,"userHeadImg":"http://localhost:80/legal_file/user/info/170324-177ee7d3-8fd7-46d5-8dec-a6b7834dfdce.jpg","userNickName":"我有一个梦想","img":["http://localhost:80/legal_file/article/170327-b220d0ba-9b0e-43ad-8529-3e384e2ea744.jpg"],"publishTime":"03-27","uid":"1010002","comments":null,"objId":44},{"content":"😇😇😇","commentNum":7,"userHeadImg":"http://localhost:80/legal_file/user/info/170324-177ee7d3-8fd7-46d5-8dec-a6b7834dfdce.jpg","userNickName":"我有一个梦想","img":null,"publishTime":"03-27","uid":"1010002","comments":null,"objId":38},{"content":"😪😪😪😪","commentNum":1,"userHeadImg":"http://localhost:80/legal_file/user/info/170324-177ee7d3-8fd7-46d5-8dec-a6b7834dfdce.jpg","userNickName":"我有一个梦想","img":null,"publishTime":"03-27","uid":"1010002","comments":null,"objId":36},{"content":"在《春天》中，作者描述了春天来临后的瓦尔登湖。瓦尔登湖终于开冻了，\u201c春光来临之前的一切琐碎事\u201d，都在春光的照耀下变得微不足道，新的生命开始了自已的生活。","commentNum":1,"userHeadImg":"http://localhost:80/legal_file/user/info/170324-177ee7d3-8fd7-46d5-8dec-a6b7834dfdce.jpg","userNickName":"我有一个梦想","img":["http://localhost:80/legal_file/article/170324-b737b0e2-69b3-4ab0-b6f9-6912a49eb88e.jpg","http://localhost:80/legal_file/article/170324-fa35d524-16ab-49dc-94eb-fcf71b930538.jpg","http://localhost:80/legal_file/article/170324-af44b693-62f5-4f56-b652-e2a99bac156f.jpg"],"publishTime":"03-24","uid":"1010002","comments":null,"objId":12},{"content":"深圳市聚力科技有限公司位于深圳宝安，主营法律以及周边服务。公司秉承＂为顾客服务，勇攀高峰的经营理念，坚持诚信原则＂，官网www，客服@163.com\u2026","commentNum":1,"userHeadImg":"http://localhost:80/legal_file/user/info/170322-facd5c6f-c2c9-46cb-90b0-8926a108ee5d.jpg","userNickName":"小法","img":["http://localhost:80/legal_file/article/170324-486a8b1c-ec86-4ca8-ba7b-fb7aec5c3797.jpg","http://localhost:80/legal_file/article/170324-47f3cd65-a56e-4271-b066-3f4494e2a5f4.jpg","http://localhost:80/legal_file/article/170324-c44adf27-0aae-4e28-ae04-d88ea170b488.jpg"],"publishTime":"03-24","uid":"1010002","comments":null,"objId":11},{"content":"好吃不上火","commentNum":17,"userHeadImg":"http://localhost:80/legal_file/user/info/170322-facd5c6f-c2c9-46cb-90b0-8926a108ee5d.jpg","userNickName":"小法","img":["http://localhost:80/legal_file/article/170323-385f8626-2702-4299-874e-67d53845b0ff.jpg","http://localhost:80/legal_file/article/170323-625c5c02-f48e-414f-8bf8-fc010e5ecbe9.jpg"],"publishTime":"03-23","uid":"1010002","comments":null,"objId":4}],"pageNo":1,"pageSize":10,"total":6,"totalPage":1}
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

    public static class DataBean {
        /**
         * dataList : [{"content":"今天，玄米茶","commentNum":1,"userHeadImg":"http://localhost:80/legal_file/user/info/170324-177ee7d3-8fd7-46d5-8dec-a6b7834dfdce.jpg","userNickName":"我有一个梦想","img":["http://localhost:80/legal_file/article/170327-b220d0ba-9b0e-43ad-8529-3e384e2ea744.jpg"],"publishTime":"03-27","uid":"1010002","comments":null,"objId":44},{"content":"😇😇😇","commentNum":7,"userHeadImg":"http://localhost:80/legal_file/user/info/170324-177ee7d3-8fd7-46d5-8dec-a6b7834dfdce.jpg","userNickName":"我有一个梦想","img":null,"publishTime":"03-27","uid":"1010002","comments":null,"objId":38},{"content":"😪😪😪😪","commentNum":1,"userHeadImg":"http://localhost:80/legal_file/user/info/170324-177ee7d3-8fd7-46d5-8dec-a6b7834dfdce.jpg","userNickName":"我有一个梦想","img":null,"publishTime":"03-27","uid":"1010002","comments":null,"objId":36},{"content":"在《春天》中，作者描述了春天来临后的瓦尔登湖。瓦尔登湖终于开冻了，\u201c春光来临之前的一切琐碎事\u201d，都在春光的照耀下变得微不足道，新的生命开始了自已的生活。","commentNum":1,"userHeadImg":"http://localhost:80/legal_file/user/info/170324-177ee7d3-8fd7-46d5-8dec-a6b7834dfdce.jpg","userNickName":"我有一个梦想","img":["http://localhost:80/legal_file/article/170324-b737b0e2-69b3-4ab0-b6f9-6912a49eb88e.jpg","http://localhost:80/legal_file/article/170324-fa35d524-16ab-49dc-94eb-fcf71b930538.jpg","http://localhost:80/legal_file/article/170324-af44b693-62f5-4f56-b652-e2a99bac156f.jpg"],"publishTime":"03-24","uid":"1010002","comments":null,"objId":12},{"content":"深圳市聚力科技有限公司位于深圳宝安，主营法律以及周边服务。公司秉承＂为顾客服务，勇攀高峰的经营理念，坚持诚信原则＂，官网www，客服@163.com\u2026","commentNum":1,"userHeadImg":"http://localhost:80/legal_file/user/info/170322-facd5c6f-c2c9-46cb-90b0-8926a108ee5d.jpg","userNickName":"小法","img":["http://localhost:80/legal_file/article/170324-486a8b1c-ec86-4ca8-ba7b-fb7aec5c3797.jpg","http://localhost:80/legal_file/article/170324-47f3cd65-a56e-4271-b066-3f4494e2a5f4.jpg","http://localhost:80/legal_file/article/170324-c44adf27-0aae-4e28-ae04-d88ea170b488.jpg"],"publishTime":"03-24","uid":"1010002","comments":null,"objId":11},{"content":"好吃不上火","commentNum":17,"userHeadImg":"http://localhost:80/legal_file/user/info/170322-facd5c6f-c2c9-46cb-90b0-8926a108ee5d.jpg","userNickName":"小法","img":["http://localhost:80/legal_file/article/170323-385f8626-2702-4299-874e-67d53845b0ff.jpg","http://localhost:80/legal_file/article/170323-625c5c02-f48e-414f-8bf8-fc010e5ecbe9.jpg"],"publishTime":"03-23","uid":"1010002","comments":null,"objId":4}]
         * pageNo : 1
         * pageSize : 10
         * total : 6
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

        public static class DataListBean {
            /**
             * content : 今天，玄米茶
             * commentNum : 1
             * userHeadImg : http://localhost:80/legal_file/user/info/170324-177ee7d3-8fd7-46d5-8dec-a6b7834dfdce.jpg
             * userNickName : 我有一个梦想
             * img : ["http://localhost:80/legal_file/article/170327-b220d0ba-9b0e-43ad-8529-3e384e2ea744.jpg"]
             * publishTime : 03-27
             * uid : 1010002
             * comments : null
             * objId : 44
             */

            private String content;
            private int commentNum;
            private String userHeadImg;
            private String userNickName;
            private String publishTime;
            private String uid;
            private Object comments;
            private int objId;
            private List<String> img;

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

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public Object getComments() {
                return comments;
            }

            public void setComments(Object comments) {
                this.comments = comments;
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
        }
    }
}
