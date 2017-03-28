package com.lxkj.xpp.legal.presenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.lxkj.xpp.legal.base.BaseMvpPresenter;
import com.lxkj.xpp.legal.chat.DemoHelper;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.listener.OnAddFriendsListener;
import com.lxkj.xpp.legal.model.AddFriendsModel;
import com.lxkj.xpp.legal.model.bean.LoginMessage;
import com.lxkj.xpp.legal.view.AddFriendsView;

/**
 * Created by 熊萍萍 on 2017/3/13/013.
 */

public class AddFriendsPresenter extends BaseMvpPresenter<AddFriendsView, AddFriendsModel> implements OnAddFriendsListener {
    private Context mContext;

    /**
     * 在构造方法里关联view设置model
     *
     * @param mView
     */
    public AddFriendsPresenter(AddFriendsView mView) {
        super(mView);
    }

    @Override
    protected AddFriendsModel setModel() {
        return new AddFriendsModel();
    }

    /**
     * 维护IM好友关系
     *
     * @param userName
     * @param relation 关系 1001-添加好友 1002-删除好友
     */
    public void relationFrinds(Context context, String userName, int relation) {
        this.mContext = context;
        mModel.addFriends(context, userName, relation, this);
    }

    @Override
    public void onFail(String error, int i) {
        if (mView != null) {
            mView.onShowToast(error, i);
        }
        Log.e("AddFriendsPresenter", error);
    }

    @Override
    public void onSuccess(LoginMessage loginMessage, Bundle bundle, int id) {
        String msg = loginMessage.getMsg();
        String code = loginMessage.getCode();
        boolean success = loginMessage.isSuccess();
        String friend_uuid = bundle.getString(Constant.ID.friend_uuid);
        Log.e("AddFriendsPresenter", "message:" + msg + ",code:" + code + ",success:" + success);
        switch (id) {
            case Constant.ID.ADD_FRIEND://添加好友
                if (success) {//如果添加成功
                    //环信添加好友
                    //mModel.addfriendstoHuanxin(mContext, friend_uuid);
                }
                break;
            case Constant.ID.DELETE_FRIEND://删除好友
                if (success) {
                    DemoHelper.getInstance().asyncFetchContactsFromServer(null);
                }
                break;
        }
    }

    @Override
    public void onError(String msg, int i) {
        if (mView != null) {
            mView.onShowToast(msg, i);
        }
        Log.e("AddFriendsPresenter", msg);
    }
}
