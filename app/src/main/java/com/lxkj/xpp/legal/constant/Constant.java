package com.lxkj.xpp.legal.constant;

import okhttp3.MediaType;

/**
 * Created by 熊萍萍 on 2017/2/21/021.
 */

public interface Constant {
    interface appFinal {
        int EXIT_TIME_GAP = 3000;
        MediaType MEDIA_TYPE_JSON = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");//mediaType，这个需要和服务器保持一致
        MediaType MEDIA_OBJECT_JSON = MediaType.parse("application/octet-stream");
        int TYPE_GET = 0;//get请求
        int TYPE_POST_JSON = 1;//post请求参数为json
        int TYPE_POST_FORM = 2;//post请求为表单
        String CHANGE_KEY = "change_key";//修改个人资料的key
        int CHANGE_UINIT = 3;//修改工作单位
        int CHANGE_CAREER = 4;//修改职业
        int CHANGE_DUTY = 5;//修改职务
        int CHANGE_PHONE = 6;//修改手机号码
    }

    interface URL {
        String BASE_URL = "http://139.129.217.190:8080";//根地址
    }
}
