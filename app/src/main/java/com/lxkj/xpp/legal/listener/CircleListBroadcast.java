package com.lxkj.xpp.legal.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lxkj.xpp.legal.app.MyApplication;
import com.lxkj.xpp.legal.presenter.CirclePresenter;

/**
 * Created by 熊萍萍 on 2017/3/28/028.
 * 帖书列表的接收广播
 */

public class CircleListBroadcast extends BroadcastReceiver {
    private CirclePresenter circlePresenter;

    public CircleListBroadcast(CirclePresenter circlePresenter) {
        this.circlePresenter = circlePresenter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(MyApplication.CIRCLEFRAGMENT_TAG)) {
            int articleId = intent.getIntExtra("articleId", 1);
            circlePresenter.loadPublishDetail(context, articleId);
        }
    }
}
