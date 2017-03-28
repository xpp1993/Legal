package com.lxkj.xpp.legal.model;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseMvpModel;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.listener.OnIdListener;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.utils.okhttp.OkHttpUtils;
import com.lxkj.xpp.legal.utils.okhttp.callback.StringCallback;

import org.json.JSONException;

import okhttp3.Call;

/**
 * Created by 熊萍萍 on 2016/12/23/023.
 * 实名认证
 */

public class IDModel implements BaseMvpModel {
    /**
     * 实名认证
     *
     * @param context
     * @param editText_name
     * @param editText_id
     * @param listener
     */
    public void realName(Context context, EditText editText_name, EditText editText_id, final OnIdListener listener) throws JSONException {
        String name = editText_name.getText().toString().trim();
        String id = editText_id.getText().toString().trim();
        String token = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.user_token);
        if (TextUtils.isEmpty(name)) {
            listener.onError(context.getString(R.string.realName_warn), Constant.SHOW_TOAST.real_name_empty);
        } else if (TextUtils.isEmpty(id)) {
            listener.onError(context.getString(R.string.id_warn), Constant.SHOW_TOAST.id_empty);
        } else {
            String params = CommonUtils.getParameterJsonResult(new String[]{"realIdNo", "realName"}, id, name);
            OkHttpUtils
                    .postString()
                    .url(Constant.URL.REAL_NAME)
                    .content(params)
                    .id(Constant.ID.REALNAME)
                    .mediaType(Constant.appFinal.MEDIA_TYPE_JSON)
                    .addHeader("user_token", token)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            listener.onFail(e.getMessage(), id);
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            listener.onSuccess(response, null, id);
                        }
                    });
        }
    }

    /**
     * 平台常量获取
     * @param listener
     */
    public void getRealPageConstant(final OnIdListener listener) {
        String token = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.user_token);
        OkHttpUtils
                .get()//
                .url(Constant.URL.REAL_PAGE_CONSTANS)
                .id(Constant.ID.REAL_PAGE_CONSTANT)
                .addHeader("user_token", token)
                .addParams("key", "user_real_name_explain")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onFail(e.getMessage(), id);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        listener.onSuccess(response, null, id);
                    }
                });

    }
}
