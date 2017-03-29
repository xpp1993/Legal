package com.lxkj.xpp.legal.model.bean;

/**
 * Created by 熊萍萍 on 2017/3/29/029.
 * 封装用户信息实体类
 */

public class UserInfoBean {
    /**
     * success : true
     * msg :
     * data : {"uid":"1010002","headImg":"http://localhost:80/legal_file/user/info/170324-177ee7d3-8fd7-46d5-8dec-a6b7834dfdce.jpg","nickName":"我有一个梦想","age":20,"workUnit":"澜星科技有限公司","workJob":"iOS开发工程师","sex":"男","self":false,"see":true,"dynamic":false,"quartersImg":"http://localhost:80/legal_file/user/job_info/170323-a513d1f3-e3c8-4883-8c7a-8d6b9d5974ba.jpg"}
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
         * uid : 1010002
         * headImg : http://localhost:80/legal_file/user/info/170324-177ee7d3-8fd7-46d5-8dec-a6b7834dfdce.jpg
         * nickName : 我有一个梦想
         * age : 20
         * workUnit : 澜星科技有限公司
         * workJob : iOS开发工程师
         * sex : 男
         * self : false
         * see : true
         * dynamic : false
         * quartersImg : http://localhost:80/legal_file/user/job_info/170323-a513d1f3-e3c8-4883-8c7a-8d6b9d5974ba.jpg
         */

        private String uid;
        private String headImg;
        private String nickName;
        private int age;
        private String workUnit;
        private String workJob;
        private String sex;
        private boolean self;
        private boolean see;
        private boolean dynamic;
        private String quartersImg;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getWorkUnit() {
            return workUnit;
        }

        public void setWorkUnit(String workUnit) {
            this.workUnit = workUnit;
        }

        public String getWorkJob() {
            return workJob;
        }

        public void setWorkJob(String workJob) {
            this.workJob = workJob;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public boolean isSelf() {
            return self;
        }

        public void setSelf(boolean self) {
            this.self = self;
        }

        public boolean isSee() {
            return see;
        }

        public void setSee(boolean see) {
            this.see = see;
        }

        public boolean isDynamic() {
            return dynamic;
        }

        public void setDynamic(boolean dynamic) {
            this.dynamic = dynamic;
        }

        public String getQuartersImg() {
            return quartersImg;
        }

        public void setQuartersImg(String quartersImg) {
            this.quartersImg = quartersImg;
        }
    }
}
