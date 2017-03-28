package com.lxkj.xpp.legal.model;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseMvpModel;
import com.lxkj.xpp.legal.chat.DemoHelper;
import com.lxkj.xpp.legal.chat.UserDao;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.listener.OnAddFriendsListener;
import com.lxkj.xpp.legal.model.bean.LoginMessage;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.utils.ThreadUtils;
import com.lxkj.xpp.legal.utils.ToastUtlis;
import com.lxkj.xpp.legal.utils.okhttp.OkHttpUtils;
import com.lxkj.xpp.legal.utils.okhttp.callback.GenericsCallback;
import com.lxkj.xpp.legal.utils.okhttp.callback.JsonGenericsSerializator;

import java.util.HashMap;

import okhttp3.Call;

/**
 * Created by 熊萍萍 on 2017/3/13/013.
 */

public class AddFriendsModel implements BaseMvpModel {


    /**
     * 添加、删除好友
     *
     * @param userName
     * @param relation
     * @param listener
     */
    public void addFriends(final Context context, String userName, int relation, final OnAddFriendsListener listener) {
        String uuid = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.uid);
        //1.从APP服务器查询用户，获得UUID，即环信的用户名
        //假设UUID等于userName(测试),模拟点击获得的friends_uuid
        final String friends_uuid = userName;
        if (relation == Constant.ID.ADD_FRIEND) {//添加好友
            if (TextUtils.equals(friends_uuid, uuid)) {//不能自己添加自己
                listener.onError(context.getString(R.string.not_add_myself), Constant.SHOW_TOAST.not_add_yourself);
                return;
            }
            if (DemoHelper.getInstance().getContactList().containsKey(friends_uuid)) {
                //让用户知道您的联系人列表中已有的联系人您希望在用户单击通知时显示的活动
                if (EMClient.getInstance().contactManager().getBlackListUsernames().contains(friends_uuid)) {
                    listener.onError(context.getString(R.string.user_already_in_contactlist),
                            Constant.SHOW_TOAST.user_already_in_contactlist);
                    return;
                }
                listener.onError(context.getString(R.string.This_user_is_already_your_friend),
                        Constant.SHOW_TOAST.This_user_is_already_your_friend);
                return;
            }
            listener.onError(context.getString(R.string.Is_sending_a_request), Constant.SHOW_TOAST.Is_sending_a_request);
            addfriendstoHuanxin(context, friends_uuid);
        } else if (relation == Constant.ID.DELETE_FRIEND) {//删除好友
            String st1 = context.getString(R.string.deleting);
            listener.onError(st1, Constant.ID.DELETE_FRIEND);
            deleteContactfromHuanxin(context, friends_uuid);
        }
//        // 2.在自己的app上添加/删除好友
//        addFriendtoService(friends_uuid, relation, listener, uuid);
    }

    /**
     * 在环信服务器上请求添加好友
     *
     * @param context
     * @param friends_uuid
     */
    public void addfriendstoHuanxin(final Context context, final String friends_uuid) {
        ThreadUtils.runTaskInThread(new Runnable() {
            @Override
            public void run() {
                try {
                    //demo use a hardcode reason here, you need let user to input if you like
                    String s = context.getString(R.string.Add_a_friend);
                    EMClient.getInstance().contactManager().addContact(friends_uuid, s);
                    ToastUtlis.showToastInThread(context.getString(R.string.send_successful));
                } catch (final Exception e) {
                    ToastUtlis.showToastInThread(context.getString(R.string.Request_add_buddy_failure));
                }
            }
        });
    }

    /**
     * 自己的app上添加/删除好友
     *
     * @param friends_uuid
     * @param relation
     * @param listener
     * @param uuid
     */
    private void addFriendtoService(String friends_uuid, int relation, final OnAddFriendsListener listener, String uuid) {
        String add_delete = String.valueOf(relation);
        HashMap<String, String> params = CommonUtils.getParameterMap(
                new String[]{"friendUid", "uid", "relation"}, friends_uuid, uuid, add_delete);
        String token = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.user_token);
        final Bundle bundle = new Bundle();
        bundle.putString(Constant.ID.friend_uuid, friends_uuid);
        OkHttpUtils
                .postString()
                .url(Constant.URL.IM_RELATION)
                .content(new Gson().toJson(params))
                .id(relation)
                .mediaType(Constant.appFinal.MEDIA_TYPE_JSON)
                .addHeader("user_token", token)
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

    /**
     * 从环信服务器上删除好友
     *
     * @param userName
     */
    private void deleteContactfromHuanxin(final Context context, final String userName) {
        ThreadUtils.runTaskInThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().deleteContact(userName);
                    // remove user from memory and database
                    UserDao dao = new UserDao(context);
                    dao.deleteContact(userName);
                    DemoHelper.getInstance().getContactList().remove(userName);
                } catch (final Exception e) {
                    Log.e("AddFriendsModel", e.getMessage());
                    ToastUtlis.showToastInUIThread(context.getString(R.string.Delete_failed));
                }
            }
        });
    }
}