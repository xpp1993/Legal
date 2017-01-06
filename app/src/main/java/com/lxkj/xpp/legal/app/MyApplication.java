package com.lxkj.xpp.legal.app;

import android.app.Application;
import android.content.Context;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;

/**
 * Created by 熊萍萍 on 2016/12/19/019.
 */

public class MyApplication extends Application {
    public static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        EMOptions options=new EMOptions();
        options.setAcceptInvitationAlways(false);
        EaseUI.getInstance().init(this,options);
    }
}
