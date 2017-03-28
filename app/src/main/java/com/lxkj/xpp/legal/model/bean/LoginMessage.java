package com.lxkj.xpp.legal.model.bean;


/**
 * Created by 熊萍萍 on 2017/3/7/007.
 {
 "success": true,
 "msg": "",
 "data": {
 "uid": "1010001",
 "phone": "18319893061",
 "headImg": "http://localhost:80/legal_file/user/real_name/170309-b3b85597-177a-4b1b-b98d-0dcee02a4b65.jpg",
 "nickName": "卡乐",
 "sex": 1,
 "age": 28,
 "workUnit": "澜星科技",
 "workJob": "Java",
 "realName": false,
 "realNameStatusStr": "待认证",
 "sexStr": "男"
 },
 "code": "0000"
 }
 */

public class LoginMessage {
    private boolean success;
    private String code;
    private String msg;
    private DataEntity data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LoginMessage{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
