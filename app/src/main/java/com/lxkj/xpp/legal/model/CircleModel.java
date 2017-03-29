package com.lxkj.xpp.legal.model;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseMvpModel;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.listener.CirCleCallBackListener;
import com.lxkj.xpp.legal.model.bean.CircleDetailBean;
import com.lxkj.xpp.legal.model.bean.CircleListBean;
import com.lxkj.xpp.legal.model.bean.CommentsBean;
import com.lxkj.xpp.legal.model.bean.UserInfoBean;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.utils.ToastUtlis;
import com.lxkj.xpp.legal.utils.okhttp.OkHttpUtils;
import com.lxkj.xpp.legal.utils.okhttp.callback.GenericsCallback;
import com.lxkj.xpp.legal.utils.okhttp.callback.JsonGenericsSerializator;
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
        final Bundle bundle = new Bundle();
        bundle.putInt("articleId", articleId);
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

    /**
     * 删除评论
     *
     * @param context
     * @param articleId
     * @param commentId
     * @param listener
     */
    public void deleteComment(Context context, int articleId, int commentId, final CirCleCallBackListener listener) {
        String token = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.user_token);
        final Bundle bundle = new Bundle();
        bundle.putInt("articleId", articleId);
        OkHttpUtils
                .get()
                .url(Constant.URL.DELETE_COMMENT)
                .id(Constant.ID.DELETE_COMMENT)
                .addHeader("user_token", token)
                .addParams("commentId", String.valueOf(commentId))
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

    /**
     * 删除帖书
     *
     * @param context
     * @param articleId
     * @param listener
     */
    public void deleteTieshu(Context context, int articleId, final CirCleCallBackListener listener) {
        String token = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.user_token);
        OkHttpUtils
                .get()
                .url(Constant.URL.DELETE_TIESHU)
                .id(Constant.ID.DELETE_TIESHU)
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
     * 获取个人面板信息
     *
     * @param context
     * @param uid
     * @param listener
     */
    public void getUserInfo(Context context, int uid, final CirCleCallBackListener listener) {
        String token = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.user_token);
        OkHttpUtils
                .get()
                .url(Constant.URL.USER_INFO)
                .id(Constant.ID.USERINFO)
                .addHeader("user_token", token)
                .addParams("uid", String.valueOf(uid))
                .build()
                .execute(new GenericsCallback<UserInfoBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onFail(e.getMessage(), id);
                    }

                    @Override
                    public void onResponse(UserInfoBean response, int id) {
                        listener.onSuccess(response, null, id);
                    }

                });
    }

    /**
     * 好友关系
     *
     * @param context
     * @param uid
     * @param friendUid
     * @param relation
     * @param listener
     */
    public void deleteFriends(Context context, String uid, String friendUid, int relation, final CirCleCallBackListener listener) throws JSONException {
        String token = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.user_token);
        String params = CommonUtils.getParameterJsonResult(new String[]{"friendUid", "uid", "relation"}, friendUid, uid, relation);
        OkHttpUtils
                .postString()
                .url(Constant.URL.IM_RELATION)
                .content(params)
                .id(relation)
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

    /**
     * 获取单个用户贴书列表（含用户自己）、后台自动识别
     *
     * @param context
     * @param pageSize
     * @param pageNo
     * @param uid
     * @param listener
     */
    public void getSingleUserArticles(Context context, int pageSize, int pageNo, String uid, final CirCleCallBackListener listener) throws JSONException {
        String token = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.user_token);
        String params = CommonUtils.getParameterJsonResult(new String[]{"pageSize", "pageNo", "uid"}, pageSize, pageNo, uid);
        OkHttpUtils
                .postString()
                .url(Constant.URL.SINGLE_USER_ARTICLE)
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
                        listener.onSuccess(circleListBean, null, id);
                    }
                });
    }
}