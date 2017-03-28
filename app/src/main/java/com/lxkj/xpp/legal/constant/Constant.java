package com.lxkj.xpp.legal.constant;

import okhttp3.MediaType;

/**
 * Created by 熊萍萍 on 2017/2/21/021.
 */

public interface Constant {
    interface appFinal {
        MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");//mediaType，这个需要和服务器保持一致
        String CHANGE_KEY = "change_key";//修改个人资料的key
        int CHANGE_UINIT = 3;//修改工作单位
        int CHANGE_CAREER = 4;//修改职业
        int CHANGE_DUTY = 5;//修改职务
        int CHANGE_PHONE = 6;//修改手机号码
        String TO_ID = "to_id";
        int REGISTER_ID = 7;//注册页面跳转到实名注册页面标志
        int ME_ID = 8;//我的页面跳转到实名注册页面标志
        String TO_REGIST = "to_regist";
        int flag_getcode = 0x11;//获取验证码标志
        int flag_register = 0x12;//注册标志
        int flag_comm_login = 0x13;//密码登录标志
        int flag_code_login = 0x14;//验证码登录标志
        String LOGIN_NAME_KEY = "login_name_key";
        String LOGIN_PASSWORD_KEY = "login_password_key";
        int dataSource = 1001;//定位数据来源,用户登录后请求
        int TOTAL_ROCK_TIME = 60;//计时器总时长
        int issue = 1;//评论
        int replay = 2;//回复评论
    }

    interface REGIST {
        int toast_userName_error = 1;
        int toast_userName_empty = 2;
        int toast_password_empty = 3;
        int toast_password_error = 4;
        int toast_password_confirm = 5;
        int toast_msg = 6;
        int toast_code = 7;
    }

    interface SHOW_TOAST {
        int not_add_yourself = 1;
        int user_already_in_contactlist = 2;
        int This_user_is_already_your_friend = 3;
        int Is_sending_a_request = 4;
        int unit_empty = 8;
        int duty_empty = 9;
        int real_name_empty = 10;
        int id_empty = 9;
        int publish_content_content = 10;
    }

    interface LOGIN {
        String uid = "uid";
        String user_token = "user_token";
        String phone = "phone";
        String headImg = "headImg";
        String nickName = "nickName";
        String workUnit = "workUnit";
        String workJob = "workJob";
        String realName = "realName";
        String realNameStatusStr = "realNameStatusStr";
    }

    interface ID {
        int ADD_FRIEND = 1001;//添加好友
        int DELETE_FRIEND = 1002;//删除好友
        int GET_SELF_INFO = 0x01;//个人信息获取标致
        int RESET_PASSWORD = 0x02;//重置密码
        int CHCEK_RESET_PHONE_CODE = 0x03;//修改手机号码第一步
        int RESET_PHONE = 0x04;//重置手机号码
        int UPDATA_SELF_INFO = 0x05;//更新个人信息
        int FEEDBACK = 0x06;//用户反馈
        int JOB_COMMIT = 0x07;//资历上传
        int GET_JOB_INFO = 0x08;//资历认证信息获取标致
        int REALNAME = 0x09;//实名认证
        int PULISH_CICLE = 0x10;//发表帖书
        int REAL_PAGE_CONSTANT = 0x11;//平台常量获取
        int LOAD_CIRCLE = 0x12;//加载帖书页
        int PUBLIS_DETAIL = 0x13;//帖书详情
        // int ISSUE_REPLAY = 0x13;//评论，发表评论
        String friend_uuid = "friend_uuid";
    }

    interface URL {
        String BASE_URL = "http://120.76.43.111:8080/";//根地址
        String LOGIN_URL = BASE_URL + "legal/api/open/common/login";//登录
        String CODE_URL = BASE_URL + "legal/api/open/common/sms";//获取验证码
        String REGIST_URL = BASE_URL + "legal/api/open/common/register";//注册
        String LOCATION_URL = BASE_URL + "legal/api/power/common/position";//定位
        String IM_RELATION = BASE_URL + "legal/api/user/im/relation";//维护用户好友关系
        String GET_SELF_INFO = BASE_URL + "legal/api/user/info/personal_info";//个人中心个人信息获取 get 请求
        String UPADATE_SELF_INFO = BASE_URL + "legal/api/user/info/personal_info_update";//更新个人信息
        String RESET_PASSWORD = BASE_URL + "legal/api/user/info/reset_password";//重置登录密码，重置成功后需登录，get 请求
        String CHCEK_RESET_PHONE_CODE = "legal/api/power/common/check_resetPhone_code";//修改手机号的时候，验证原手机号码获得的验证码（4001）get
        String RESET_PHONE = BASE_URL + "legal/api/power/common/resetPhone";//重置手机号 GET
        String FEEDBACK = BASE_URL + "legal/api/power/common/feedback_submit";//用户反馈
        String JOB = BASE_URL + "legal/api/user/info/job_info_submit";//提交资历信息
        String GET_JOB_INFO = BASE_URL + "legal/api/user/info/job_info";//获取资历认证信息
        String REAL_NAME = BASE_URL + "legal/api/user/info/real_name_submit";//实名认证
        String REAL_PAGE_CONSTANS = BASE_URL + "legal/api/open/common/legal_constant";//平台常量
        String PUBLSH_CIRCLE = BASE_URL + "legal/api/article/publish";//发布帖书
        String LOAD_CIRCLE = BASE_URL + "legal/api/article/article_list";//帖书列表
        String ISSUE_REPLAY = BASE_URL + "legal/api/article/discuss";//评论，回复评论
        String PUBLISH_DETAIL = BASE_URL + "legal/api/article/article_detail";//帖书详情
        String DELETE_COMMENT = BASE_URL + "legal/api/article/delete_comment";//删除评论，commentId
        String DELETE_TIESHU = BASE_URL + "legal/api/article/delete_article?articleId=48";//删除帖书，articleId
        String USER_INFO=BASE_URL+"legal/api/power/common/user_info?uid=1010002";
    }
}
