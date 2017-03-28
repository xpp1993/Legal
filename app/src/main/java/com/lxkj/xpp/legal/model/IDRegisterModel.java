package com.lxkj.xpp.legal.model;

import com.lxkj.xpp.legal.base.BaseMvpModel;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.utils.okhttp.OkHttpUtils;
import com.lxkj.xpp.legal.utils.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by 熊萍萍 on 2017/3/9/009.
 */

public class IDRegisterModel implements BaseMvpModel {
    /**
     * 获取实名信息
     */
    public void getRealInfo() {
        String token = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.user_token);
        OkHttpUtils
                .get()
                .addHeader(Constant.LOGIN.user_token, token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }
                });

    }
}
