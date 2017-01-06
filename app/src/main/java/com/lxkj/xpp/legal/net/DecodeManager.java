package com.lxkj.xpp.legal.net;

import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lxkj.xpp.legal.bean.UserInfo;
import com.lxkj.xpp.legal.bean.VersionBean;
import com.lxkj.xpp.legal.net.parser.ReqJson;
import com.lxkj.xpp.legal.net.parser.TypeInfo;

/**
 * Created by 熊萍萍 on 2016/12/19/019.
 */

public final class DecodeManager {
    final static String TAG = DecodeManager.class.getSimpleName();

    /**
     * 解析 登录
     *
     * @param jsonObject
     * @param requestCode
     * @param handler
     */
    public static void decodeLogin(String jsonObject, int requestCode, Handler handler) {
        JSONObject result = JSON.parseObject(jsonObject);
        String object = result.getJSONObject("json").getJSONObject("versions").toJSONString();
        TypeInfo typeInfo = TypeInfo.createNarmalType(VersionBean.class);
        if (typeInfo != null && object != null) {
            Log.e(TAG, "object" + object);
            ReqJson.parseHttpResult(typeInfo, requestCode, object, handler);
        }
    }
}
