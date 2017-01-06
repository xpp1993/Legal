package com.lxkj.xpp.legal.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 熊萍萍 on 2016/12/23/023.
 * activity 基类
 */

public abstract class BaseActivity<P extends BaseMvpPresenter> extends AppCompatActivity implements BaseMvpView {
    private Unbinder mUnbinder;
    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentViewResId());
        mUnbinder = ButterKnife.bind(this);
        mPresenter = setPresenter();
        initWidgets(savedInstanceState);
        initListener();
        initData();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }
    /**
     * 监听事件
     */
    protected abstract void initListener();
    /**
     * 初始化数据
     */
    protected abstract void initData();
    /**
     * 获取activity的布局资源
     */
    protected abstract int setContentViewResId();
    /**
     * 设置presenter层对象
     *
     * @return presenter层对象
     */
    protected abstract P setPresenter();

    /**
     * 初始化组件工作
     *
     * @param savedInstanceState
     */
    protected void initWidgets(Bundle savedInstanceState) {
    }
}