package com.lxkj.xpp.legal.model;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.google.gson.Gson;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseMvpModel;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.listener.OnRegisterListener;
import com.lxkj.xpp.legal.model.bean.LoginMessage;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.utils.FormatCheck;
import com.lxkj.xpp.legal.utils.okhttp.Md5Utils;
import com.lxkj.xpp.legal.utils.okhttp.OkHttpUtils;
import com.lxkj.xpp.legal.utils.okhttp.callback.GenericsCallback;
import com.lxkj.xpp.legal.utils.okhttp.callback.JsonGenericsSerializator;

import java.util.HashMap;

import okhttp3.Call;

/**
 * Created by 熊萍萍 on 2017/3/7/007.
 */

public class RegisterModel implements BaseMvpModel {
    /**
     * 获取验证码
     *
     * @param phone_et 手机号码 editText
     * @param type     获取验证码类型 ：1001	用户注册  2001验证码登
     * @param listener 接口回调，用于和view层通信
     */

    public void getCheckCode(Context context, EditText phone_et, int type, final OnRegisterListener listener) {
        String phone = phone_et.getText().toString().trim();
        String typeStr = String.valueOf(type);
        if (TextUtils.isEmpty(phone)) {//手机号码为空
            listener.onError(context.getString(R.string.phone_empty), Constant.REGIST.toast_userName_empty);
        } else if (!FormatCheck.isMobile(phone)) {//手机号码格式错误
            listener.onError(context.getString(R.string.phone_error), Constant.REGIST.toast_userName_error);
        } else {//联网获取手机验证码
            HashMap<String, String> params =
                    CommonUtils.getParameterMap(new String[]{"phone", "type"}, phone, typeStr);
            OkHttpUtils
                    .postString()
                    .url(Constant.URL.CODE_URL)
                    .id(Constant.appFinal.flag_getcode)
                    .content(new Gson().toJson(params))
                    .mediaType(Constant.appFinal.MEDIA_TYPE_JSON)
                    .build()
                    .execute(new GenericsCallback<LoginMessage>(new JsonGenericsSerializator()) {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            listener.onFail(e.getMessage(), id);
                        }

                        @Override
                        public void onResponse(LoginMessage response, int id) {
                            listener.onSuccess(response, null, id);
                        }

                    });
        }

    }

    /**
     * 注册
     *
     * @param phone_et         电话号码edit
     * @param code_et          验证码 edit
     * @param password_et      密码 edit
     * @param confirm_password 确认密码 edit
     * @param listener         接口回调用于 model层与view层通信
     */
    public void register(Context context, EditText phone_et, EditText code_et, EditText password_et, EditText confirm_password, final OnRegisterListener listener) {
        String phone = phone_et.getText().toString().trim();
        String code = code_et.getText().toString().trim();
        String password = password_et.getText().toString().trim();
        String confirm_pw = confirm_password.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {//手机号码为空
            listener.onError(context.getString(R.string.phone_empty), Constant.REGIST.toast_userName_empty);
        } else if (!FormatCheck.isMobile(phone)) {//手机号码格式错误
            listener.onError(context.getString(R.string.phone_error), Constant.REGIST.toast_userName_error);
        } else if (TextUtils.isEmpty(code)) {//验证码为空
            listener.onError(context.getString(R.string.code_empty), Constant.REGIST.toast_code);
        } else if (TextUtils.isEmpty(password)) {//密码为空
            listener.onError(context.getString(R.string.password_empty), Constant.REGIST.toast_password_empty);
        } else if (TextUtils.isEmpty(confirm_pw)) {//确认的密码为空
            listener.onError(context.getString(R.string.password_empty), Constant.REGIST.toast_password_confirm);
        } else if (!TextUtils.equals(password, confirm_pw)) {//密码不相同
            listener.onError(context.getString(R.string.confirm_password_error), Constant.REGIST.toast_password_error);
        } else {//联网注册
            HashMap<String, String> params =
                    CommonUtils.getParameterMap(new String[]{"phone", "code", "md5Pwd"}, phone, code, Md5Utils.getVal_UTF8(password));
            final Bundle bundle = new Bundle();
            bundle.putString(Constant.appFinal.LOGIN_NAME_KEY, phone);
            bundle.putString(Constant.appFinal.LOGIN_PASSWORD_KEY, password);
            OkHttpUtils
                    .postString()
                    .url(Constant.URL.REGIST_URL)
                    .id(Constant.appFinal.flag_register)
                    .content(new Gson().toJson(params))
                    .mediaType(Constant.appFinal.MEDIA_TYPE_JSON)
                    .build()
                    .execute(new GenericsCallback<LoginMessage>(new JsonGenericsSerializator()) {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            listener.onFail(e.getMessage(), id);
                        }

                        @Override
                        public void onResponse(LoginMessage response, int id) {
                            listener.onSuccess(response, bundle, id);
                        }

                    });
        }
    }
}
