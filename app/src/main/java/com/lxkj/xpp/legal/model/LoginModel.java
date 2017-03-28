package com.lxkj.xpp.legal.model;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.google.gson.Gson;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseMvpModel;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.listener.CallBackListener;
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
 * Created by 熊萍萍 on 2017/3/6/006.
 * 密码登录、验证码登录、以及注册的model
 */

public class LoginModel implements BaseMvpModel {

    /**
     * 1：手机号码、密码登录
     *
     * @param context
     * @param username_et
     * @param password_et
     * @param onLoginListener
     */
    public void login(Context context, EditText username_et, EditText password_et, final OnLoginListener onLoginListener) {
        String username = username_et.getText().toString().trim();
        String password = password_et.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {//手机号码为空
            onLoginListener.onError(context.getString(R.string.phone_empty), Constant.REGIST.toast_userName_empty);
        } else if (!FormatCheck.isMobile(username)) {//手机号码格式错误
            onLoginListener.onError(context.getString(R.string.phone_error), Constant.REGIST.toast_userName_error);
        } else if (TextUtils.isEmpty(password)) {//密码为空
            onLoginListener.onError(context.getString(R.string.password_empty), Constant.REGIST.toast_password_empty);
        } else {//联网登录
            HashMap<String, String> params =
                    CommonUtils.getParameterMap(new String[]{"phone", "md5Pwd"}, username, Md5Utils.getVal_UTF8(password));
            final Bundle bundle = new Bundle();
            bundle.putString(Constant.appFinal.LOGIN_NAME_KEY, username);
            bundle.putString(Constant.appFinal.LOGIN_PASSWORD_KEY, password);
            OkHttpUtils
                    .postString()
                    .url(Constant.URL.LOGIN_URL)
                    .content(new Gson().toJson(params))
                    .id(Constant.appFinal.flag_comm_login)
                    .mediaType(Constant.appFinal.MEDIA_TYPE_JSON)
                    .build()
                    .execute(new GenericsCallback<LoginMessage>(new JsonGenericsSerializator()) {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            onLoginListener.onFail(e.getMessage(), id);
                        }

                        @Override
                        public void onResponse(LoginMessage response, int id) {

                            onLoginListener.onSuccess(response, bundle, id);
                        }

                    });

        }
    }

    /**
     * 2：手机号码、验证码登录
     *
     * @param context
     * @param username_et
     * @param code_et
     * @param onLoginListener
     */
    public void fastLogin(Context context, EditText username_et, EditText code_et, final OnLoginListener onLoginListener) {
        String username = username_et.getText().toString().trim();
        String check_code = code_et.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {//手机号码为空
            onLoginListener.onError(context.getString(R.string.phone_empty), Constant.REGIST.toast_userName_empty);
        } else if (!FormatCheck.isMobile(username)) {//手机号码格式错误
            onLoginListener.onError(context.getString(R.string.phone_error), Constant.REGIST.toast_userName_error);
        } else if (TextUtils.isEmpty(check_code)) {//验证码为空
            onLoginListener.onError(context.getString(R.string.code_empty), Constant.REGIST.toast_code);
        } else {//联网登录
            HashMap<String, String> params =
                    CommonUtils.getParameterMap(new String[]{"phone", "code"}, username, check_code);
            OkHttpUtils
                    .postString()
                    .url(Constant.URL.LOGIN_URL)
                    .content(new Gson().toJson(params))
                    .mediaType(Constant.appFinal.MEDIA_TYPE_JSON)
                    .id(Constant.appFinal.flag_code_login)
                    .build()
                    .execute(new GenericsCallback<LoginMessage>(new JsonGenericsSerializator()) {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            onLoginListener.onFail(e.getMessage(), id);
                        }

                        @Override
                        public void onResponse(LoginMessage response, int id) {

                            onLoginListener.onSuccess(response, null, id);
                        }

                    });

        }
    }

    /**
     * 注册
     *
     * @param phone_et    电话号码edit
     * @param code_et     验证码 edit
     * @param password_et 密码 edit
     * @param listener    接口回调用于 model层与view层通信
     */
    public void register(Context context, EditText phone_et, EditText code_et, EditText password_et, final OnLoginListener listener) {
        String phone = phone_et.getText().toString().trim();
        String code = code_et.getText().toString().trim();
        String password = password_et.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {//手机号码为空
            listener.onError(context.getString(R.string.phone_empty), Constant.REGIST.toast_userName_empty);
        } else if (!FormatCheck.isMobile(phone)) {//手机号码格式错误
            listener.onError(context.getString(R.string.phone_error), Constant.REGIST.toast_userName_error);
        } else if (TextUtils.isEmpty(code)) {//验证码为空
            listener.onError(context.getString(R.string.code_empty), Constant.REGIST.toast_code);
        } else if (TextUtils.isEmpty(password)) {//密码为空
            listener.onError(context.getString(R.string.password_empty), Constant.REGIST.toast_password_empty);
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

    /**
     * 重置密码
     *
     * @param context
     * @param resetPassword_et
     * @param confirm_et
     * @param listener
     */

    public void resetPassword(Context context, EditText resetPassword_et, EditText confirm_et, final OnLoginListener listener) {

        final String resetPassword = resetPassword_et.getText().toString().trim();
        String confirm_password = confirm_et.getText().toString().trim();
        if (TextUtils.isEmpty(resetPassword)) {
            listener.onError(context.getString(R.string.password_empty), Constant.REGIST.toast_password_empty);
        } else if (TextUtils.isEmpty(confirm_password)) {
            listener.onError(context.getString(R.string.password_empty), Constant.REGIST.toast_password_confirm);
        } else if (!TextUtils.equals(resetPassword, confirm_password)) {
            listener.onError(context.getString(R.string.confirm_password_error), Constant.REGIST.toast_password_error);
        } else {//提交新密码
            String token = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.user_token);
            OkHttpUtils
                    .get()//
                    .url(Constant.URL.RESET_PASSWORD)
                    .id(Constant.ID.RESET_PASSWORD)
                    .addHeader("user_token", token)
                    .addParams("newMd5Pwd", Md5Utils.getVal_UTF8(resetPassword))
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
     * 修改手机号码第一步
     *
     * @param context
     * @param code_et
     * @param listener
     */
    public void changePhone(Context context, EditText code_et, final OnLoginListener listener) {
        String code = code_et.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {//验证码不能为空
            listener.onError(context.getString(R.string.code_empty), Constant.REGIST.toast_code);
        } else {
            String token = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.user_token);
            OkHttpUtils
                    .get()//
                    .url(Constant.URL.CHCEK_RESET_PHONE_CODE)
                    .id(Constant.ID.CHCEK_RESET_PHONE_CODE)
                    .addHeader("user_token", token)
                    .addParams("code", code)
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
     * 修改手机号第二步，重置号码
     *
     * @param context
     * @param code_et
     * @param newPhone_et
     * @param listener
     */
    public void changePhoneNext(Context context, EditText code_et, EditText newPhone_et, final OnLoginListener listener) {
        String code = code_et.getText().toString().trim();
        String newphone = newPhone_et.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {//验证码不能为空
            listener.onError(context.getString(R.string.code_empty), Constant.REGIST.toast_code);
        } else if (TextUtils.isEmpty(newphone)) {//手机号码不能为空
            listener.onError(context.getString(R.string.phone_empty), Constant.REGIST.toast_userName_empty);
        } else if (!FormatCheck.isMobile(newphone)) {//手机号码格式错误
            listener.onError(context.getString(R.string.phone_error), Constant.REGIST.toast_userName_error);
        } else {
            String token = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.user_token);
            OkHttpUtils
                    .get()//
                    .url(Constant.URL.RESET_PHONE)
                    .id(Constant.ID.RESET_PHONE)
                    .addHeader("user_token", token)
                    .addParams("code", code)
                    .addParams("newPhone", newphone)
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

    public interface OnLoginListener extends CallBackListener<LoginMessage> {

    }
}

