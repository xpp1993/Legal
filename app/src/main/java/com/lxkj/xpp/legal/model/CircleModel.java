package com.lxkj.xpp.legal.model;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.adapter.CircleListItemAdapter;
import com.lxkj.xpp.legal.adapter.ItemViewHolder;
import com.lxkj.xpp.legal.base.BaseMvpModel;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.listener.CirCleCallBackListener;
import com.lxkj.xpp.legal.model.bean.CircleDetailBean;
import com.lxkj.xpp.legal.model.bean.CircleListBean;
import com.lxkj.xpp.legal.model.bean.CommentsBean;
import com.lxkj.xpp.legal.model.bean.LoginMessage;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.utils.ToastUtlis;
import com.lxkj.xpp.legal.utils.okhttp.OkHttpUtils;
import com.lxkj.xpp.legal.utils.okhttp.callback.GenericsCallback;
import com.lxkj.xpp.legal.utils.okhttp.callback.JsonGenericsSerializator;
import com.lxkj.xpp.legal.utils.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import okhttp3.Call;

/**
 * Created by 熊萍萍 on 2017/3/24/024.
 * 帖书
 */

public class CircleModel implements BaseMvpModel {
    /**
     * 发布帖书
     *
     * @param context
     * @param editText_content
     * @param empower
     * @param img
     * @param listener
     */
    public void publishCircle(Context context, EditText editText_content, int empower, JSONArray img, final CirCleCallBackListener listener) throws JSONException {
        String content = editText_content.getText().toString().trim();
        String token = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.user_token);
        if (TextUtils.isEmpty(content)) {
            listener.onError(context.getString(R.string.publish_content_empty), Constant.SHOW_TOAST.publish_content_content);
        } else {
            String params = CommonUtils.getParameterJsonResult(new String[]{
                    "content", "empowerWay", "img"}, content, empower, img);
            OkHttpUtils
                    .postString()
                    .url(Constant.URL.PUBLSH_CIRCLE)
                    .content(params)
                    .id(Constant.ID.PULISH_CICLE)
                    .mediaType(Constant.appFinal.MEDIA_TYPE_JSON)
                    .addHeader("user_token", token)
                    .build()
                    .execute(new GenericsCallback<CircleListBean>(new JsonGenericsSerializator()) {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            listener.onFail(e.getMessage(), id);
                        }

                        @Override
                        public void onResponse(CircleListBean circleListBean, int id) {
                            listener.onSuccess(circleListBean, null, id);
                        }
                    });
        }
    }

    /**
     * 加载帖书页面动态
     *
     * @param context
     * @param pageSize 分页大小
     * @param pageNo   页码
     * @param listener
     */
    public void loadCircleData(Context context, int code, int pageSize, int pageNo, final CirCleCallBackListener listener) throws JSONException {
        String token = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.user_token);
        String params = CommonUtils.getParameterJsonResult(new String[]{"pageSize", "pageNo"}, pageSize, pageNo);
        final Bundle bundle = new Bundle();
        bundle.putInt("code", code);
        OkHttpUtils
                .postString()
                .url(Constant.URL.LOAD_CIRCLE)
                .content(params)
                .id(Constant.ID.LOAD_CIRCLE)
                .mediaType(Constant.appFinal.MEDIA_TYPE_JSON)
                .addHeader("user_token", token)
                .build()
                .execute(new GenericsCallback<CircleListBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onFail(e.getMessage(), id);
                    }

                    @Override
                    public void onResponse(CircleListBean circleListBean, int id) {
                        listener.onSuccess(circleListBean, bundle, id);
                    }
                });
    }

    /**
     * 获取帖数详情
     *
     * @param context
     * @param articleId
     * @param listener
     * @throws JSONException
     */
    public void getDetailPublish(Context context, int articleId, final CirCleCallBackListener listener) throws JSONException {
        String token = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.user_token);
        OkHttpUtils
                .get()
                .url(Constant.URL.PUBLISH_DETAIL)
                .id(Constant.ID.PUBLIS_DETAIL)
                .addHeader("user_token", token)
                .addParams("articleId", String.valueOf(articleId))
                .build()
                .execute(new GenericsCallback<CircleDetailBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onFail(e.getMessage(), id);
                    }

                    @Override
                    public void onResponse(CircleDetailBean response, int id) {
                        listener.onSuccess(response, null, id);
                    }

                });
    }

    /**
     * 发表评论,回复评论
     *
     * @param context
     * @param type         1评论 2 回复评论
     * @param articleId
     * @param commentsBean
     * @param content
     * @param listener
     * @throws JSONException
     */
    public void IssueOrReplay(Context context, int type, int articleId, CommentsBean commentsBean, String content, final CirCleCallBackListener listener) throws JSONException {
        String token = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.user_token);
        String params = null;
        final Bundle bundle=new Bundle();
        bundle.putInt("articleId",articleId);
        if (type == Constant.appFinal.issue) {//评论
            params = CommonUtils.getParameterJsonResult(new String[]{"type", "articleId", "content"},
                    Constant.appFinal.issue, articleId, content);
        } else if (type == Constant.appFinal.replay) {//回复评论
            params = CommonUtils.getParameterJsonResult(new String[]{"type", "articleId", "content", "replyUid", "replyNickName"},
                    Constant.appFinal.replay, articleId, content, commentsBean.getDiscussUid(), commentsBean.getDiscussNickName());
            if ("empty".equals(commentsBean.getDiscussUid()) && "empty".equals(commentsBean.getDiscussNickName())) {
                ToastUtlis.showToastInUIThread("回复失败");
                return;
            }
        }
        OkHttpUtils
                .postString()
                .url(Constant.URL.ISSUE_REPLAY)
                .content(params)
                .id(type)
                .mediaType(Constant.appFinal.MEDIA_TYPE_JSON)
                .addHeader("user_token", token)
                .build()
                .execute(new GenericsCallback<CircleDetailBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onFail(e.getMessage(), id);
                    }

                    @Override
                    public void onResponse(CircleDetailBean response, int id) {
                        listener.onSuccess(response, bundle, id);
                    }

                });
    }
}
