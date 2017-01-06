package com.lxkj.xpp.legal.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.githang.statusbar.StatusBarCompat;
import com.lxkj.xpp.legal.base.BaseActivity;
import com.lxkj.xpp.legal.base.BaseMvpPresenter;
import com.lxkj.xpp.legal.R;
/**
 * Created by 熊萍萍 on 2017/1/5/005.
 * 注册
 */

public class RegistActivity extends BaseActivity {
    @Override
    protected void initWidgets(Bundle savedInstanceState) {
        super.initWidgets(savedInstanceState);
    StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this,R.color.color_white));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected int setContentViewResId() {
        return R.layout.activity_register;
    }

    @Override
    protected BaseMvpPresenter setPresenter() {
        return null;
    }
}
