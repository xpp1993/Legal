package com.lxkj.xpp.legal.model;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseMvpModel;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.listener.MeCallBackListener;
import com.lxkj.xpp.legal.model.bean.LoginMessage;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.utils.okhttp.OkHttpUtils;
import com.lxkj.xpp.legal.utils.okhttp.callback.GenericsCallback;
import com.lxkj.xpp.legal.utils.okhttp.callback.JsonGenericsSerializator;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

import okhttp3.Call;

/**
 * Created by 熊萍萍 on 2016/12/23/023.
 */

public class IModel implements BaseMvpModel {

    /**
     * 获取个人信息
     */
    public void getUserInfo(final MeCallBackListener listener) {
        String token = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.user_token);
        OkHttpUtils
                .get()//
                .url(Constant.URL.GET_SELF_INFO)
                .id(Constant.ID.GET_SELF_INFO)
                .addHeader("user_token", token)
                .build()
                .execute(new GenericsCallback<LoginMessage>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onFail(e.getMessage(), id);
                    }

                    @Override
                    public void onResponse(LoginMessage loginMessage, int id) {
                        listener.onSuccess(loginMessage, null, id);
                    }
                });
    }

    /**
     * 更新个人信息
     */
    public void upataUserInfo(Context context, String age, String nickName, int sex, String headImg, final MeCallBackListener listener) {
        if (context.getString(R.string.age_warn).equals(age) || "".equals(age) || age == null) {
            netToSelfInfo(age, nickName, sex, headImg, listener);
        } else {
            try {
                int numberAge = Integer.parseInt(age);
                if (numberAge < 0 || numberAge > 100) {
                    listener.onError(context.getString(R.string.age_bound_error), Constant.ID.UPDATA_SELF_INFO);
                    return;
                }
                netToSelfInfo(age, nickName, sex, headImg, listener);
            } catch (Exception e) {
                listener.onError(context.getString(R.string.age_error), Constant.ID.UPDATA_SELF_INFO);
            }
        }

    }

    /**
     * 网络上传个人数据
     *
     * @param age
     * @param nickName
     * @param sex
     * @param headImg
     * @param listener
     */
    private void netToSelfInfo(String age, String nickName, int sex, String headImg, final MeCallBackListener listener) {
        HashMap<String, String> params = CommonUtils.getParameterMap(new String[]{
                "age", "nickName", "sex", "headImg"}, age, nickName, String.valueOf(sex), headImg);
        String token = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.user_token);
        OkHttpUtils
                .postString()
                .url(Constant.URL.UPADATE_SELF_INFO)
                .content(new Gson().toJson(params))
                .id(Constant.ID.UPDATA_SELF_INFO)
                .mediaType(Constant.appFinal.MEDIA_TYPE_JSON)
                .addHeader("user_token", token)
                .build()
                .execute(new GenericsCallback<LoginMessage>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onFail(e.getMessage(), id);
                    }

                    @Override
                    public void onResponse(LoginMessage loginMessage, int id) {
                        listener.onSuccess(loginMessage, null, id);
                    }
                });
    }

    /**
     * 提交用户反馈数据
     *
     * @param context
     * @param editText_phone
     * @param editText_content
     * @param headImgs
     * @param listener
     */
    public void commitFeedBack(Context context, EditText editText_phone, EditText editText_content,
                               JSONArray headImgs, final MeCallBackListener listener) throws JSONException {
        String phone = editText_phone.getText().toString().trim();
        String content = editText_content.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            listener.onError(context.getString(R.string.age_warn), Constant.ID.FEEDBACK);
        } else {
            Log.d("wyj", "commitFeedBack: headImgs are " + headImgs);
            String params = CommonUtils.getParameterJsonResult(new String[]{
                    "content", "phone", "img"}, content, phone, headImgs);
            String token = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.user_token);
            OkHttpUtils
                    .postString()
                    .url(Constant.URL.FEEDBACK)
                    .content(params)
                    .id(Constant.ID.FEEDBACK)
                    .mediaType(Constant.appFinal.MEDIA_TYPE_JSON)
                    .addHeader("user_token", token)
                    .build()
                    .execute(new GenericsCallback<LoginMessage>(new JsonGenericsSerializator()) {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            listener.onFail(e.getMessage(), id);
                        }

                        @Override
                        public void onResponse(LoginMessage loginMessage, int id) {
                            listener.onSuccess(loginMessage, null, id);
                        }
                    });
        }
    }

    /**
     * 提交资历认证信息
     *
     * @param context
     * @param editText_unit
     * @param editText_duty
     * @param headImage
     * @param empowerWay
     * @param listener
     */
    public void CommitJobInfo(Context context, EditText editText_unit, EditText editText_duty, String headImage, int empowerWay, final MeCallBackListener listener) throws JSONException {
        String unit = editText_unit.getText().toString().trim();
        String duty = editText_duty.getText().toString().trim();
        String token = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.user_token);
        if (TextUtils.isEmpty(unit)) {
            listener.onError(context.getString(R.string.unit_empty), Constant.SHOW_TOAST.unit_empty);
        }
        if (TextUtils.isEmpty(duty)) {
            listener.onError(context.getString(R.string.duty_empty), Constant.SHOW_TOAST.duty_empty);
        } else {
            String params = CommonUtils.getParameterJsonResult(new String[]{"workUnit", "workJob", "empowerWay", "gwImg"},
                    unit, duty, empowerWay, headImage);
            OkHttpUtils
                    .postString()
                    .url(Constant.URL.JOB)
                    .content(params)
                    .id(Constant.ID.JOB_COMMIT)
                    .mediaType(Constant.appFinal.MEDIA_TYPE_JSON)
                    .addHeader("user_token", token)
                    .build()
                    .execute(new GenericsCallback<LoginMessage>(new JsonGenericsSerializator()) {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            listener.onFail(e.getMessage(), id);
                        }

                        @Override
                        public void onResponse(LoginMessage loginMessage, int id) {
                            listener.onSuccess(loginMessage, null, id);
                        }
                    });
        }
    }

    /**
     * 获取资历认证信息
     *
     * @param listener
     */
    public void getJobInfo(final MeCallBackListener listener) {
        String token = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.user_token);
        OkHttpUtils
                .get()//
                .url(Constant.URL.GET_JOB_INFO)
                .id(Constant.ID.GET_JOB_INFO)
                .addHeader("user_token", token)
                .build()
                .execute(new GenericsCallback<LoginMessage>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onFail(e.getMessage(), id);
                    }

                    @Override
                    public void onResponse(LoginMessage loginMessage, int id) {
                        listener.onSuccess(loginMessage, null, id);
                    }
                });
    }



//    /**
//     * 实名认证
//     *
//     * @param context
//     * @param editText_name
//     * @param editText_id
//     * @param listener
//     */
//    public void realName(Context context, EditText editText_name, EditText editText_id, final MeCallBackListener listener) throws JSONException {
//        String name = editText_name.getText().toString().trim();
//        String id = editText_id.getText().toString().trim();
//        String token = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.user_token);
//        if (TextUtils.isEmpty(name)) {
//            listener.onError(context.getString(R.string.realName_warn), Constant.SHOW_TOAST.real_name_empty);
//        } else if (TextUtils.isEmpty(id)) {
//            listener.onError(context.getString(R.string.id_warn), Constant.SHOW_TOAST.id_empty);
//        } else {
//            String params = CommonUtils.getParameterJsonResult(new String[]{"realIdNo", "realName"}, id, name);
//            OkHttpUtils
//                    .postString()
//                    .url(Constant.URL.REAL_NAME)
//                    .content(params)
//                    .id(Constant.ID.REALNAME)
//                    .mediaType(Constant.appFinal.MEDIA_TYPE_JSON)
//                    .addHeader("user_token", token)
//                    .build()
//                    .execute(new GenericsCallback<LoginMessage>(new JsonGenericsSerializator()) {
//                        @Override
//                        public void onError(Call call, Exception e, int id) {
//                            listener.onFail(e.getMessage(), id);
//                        }
//
//                        @Override
//                        public void onResponse(LoginMessage loginMessage, int id) {
//                            listener.onSuccess(loginMessage, null, id);
//                        }
//                    });
//        }
//    }
}
