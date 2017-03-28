package com.lxkj.xpp.legal.model.bean;

import java.io.Serializable;

/**
 * Created by 熊萍萍 on 2017/3/22/022.
 */

public class DataEntity implements Serializable{
    private String uid;
    private String phone;
    private String token;
    private String headImg;
    private String nickName;
    private int sex;
    private int age;
    private String workUnit;
    private String workJob;
    private boolean realName;
    private String realNameStatusStr;
    private String sexStr;
    private boolean submit;
    private String quartersImg;
    private int empowerWay;
    private String explain;
    private String empowerWayStr;

    public boolean isSubmit() {
        return submit;
    }

    public void setSubmit(boolean submit) {
        this.submit = submit;
    }

    public String getQuartersImg() {
        return quartersImg;
    }

    public void setQuartersImg(String quartersImg) {
        this.quartersImg = quartersImg;
    }

    public int getEmpowerWay() {
        return empowerWay;
    }

    public void setEmpowerWay(int empowerWay) {
        this.empowerWay = empowerWay;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getEmpowerWayStr() {
        return empowerWayStr;
    }

    public void setEmpowerWayStr(String empowerWayStr) {
        this.empowerWayStr = empowerWayStr;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isRealName() {
        return realName;
    }

    public void setRealName(boolean realName) {
        this.realName = realName;
    }

    public String getSexStr() {
        return sexStr;
    }

    public void setSexStr(String sexStr) {
        this.sexStr = sexStr;
    }

    public DataEntity() {
    }

    public DataEntity(String uid, String phone, String token, String headImg, String nickName, int sex, int age, String workUnit, String workJob, boolean realName, String realNameStatusStr, String sexStr) {
        this.uid = uid;
        this.phone = phone;
        this.token = token;
        this.headImg = headImg;
        this.nickName = nickName;
        this.sex = sex;
        this.age = age;
        this.workUnit = workUnit;
        this.workJob = workJob;
        this.realName = realName;
        this.realNameStatusStr = realNameStatusStr;
        this.sexStr = sexStr;
    }

    public DataEntity(String uid, String phone, String token, String headImg, String nickName, int sex, int age, String workUnit, String workJob, boolean realName, String realNameStatusStr, String sexStr, boolean submit, String quartersImg, int empowerWay, String explain, String empowerWayStr) {
        this.uid = uid;
        this.phone = phone;
        this.token = token;
        this.headImg = headImg;
        this.nickName = nickName;
        this.sex = sex;
        this.age = age;
        this.workUnit = workUnit;
        this.workJob = workJob;
        this.realName = realName;
        this.realNameStatusStr = realNameStatusStr;
        this.sexStr = sexStr;
        this.submit = submit;
        this.quartersImg = quartersImg;
        this.empowerWay = empowerWay;
        this.explain = explain;
        this.empowerWayStr = empowerWayStr;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getRealNameStatusStr() {
        return realNameStatusStr;
    }

    public void setRealNameStatusStr(String realNameStatusStr) {
        this.realNameStatusStr = realNameStatusStr;
    }

    @Override
    public String toString() {
        return "DataEntity{" +
                "uid='" + uid + '\'' +
                ", phone='" + phone + '\'' +
                ", token='" + token + '\'' +
                ", headImg='" + headImg + '\'' +
                ", nickName='" + nickName + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", workUnit='" + workUnit + '\'' +
                ", workJob='" + workJob + '\'' +
                ", realName=" + realName +
                ", realNameStatusStr='" + realNameStatusStr + '\'' +
                ", sexStr='" + sexStr + '\'' +
                ", submit=" + submit +
                ", quartersImg='" + quartersImg + '\'' +
                ", empowerWay=" + empowerWay +
                ", explain='" + explain + '\'' +
                ", empowerWayStr='" + empowerWayStr + '\'' +
                '}';
    }
}
