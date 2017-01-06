package com.lxkj.xpp.legal.bean;

/**
 * Created by 熊萍萍 on 2016/12/28/028.
 */

public class VersionBean {
    private String versions;
    private String url;
    private String uploadDate;

    public VersionBean() {
    }

    public VersionBean(String versions, String url, String uploadDate) {
        this.versions = versions;
        this.url = url;
        this.uploadDate = uploadDate;
    }

    public String getVersions() {
        return versions;
    }

    public void setVersions(String versions) {
        this.versions = versions;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    @Override
    public String toString() {
        return "VersionBean{" +
                "versions='" + versions + '\'' +
                ", url='" + url + '\'' +
                ", uploadDate='" + uploadDate + '\'' +
                '}';
    }
}
