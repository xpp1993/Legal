package com.lxkj.xpp.legal.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 熊萍萍 on 2016/12/27/027.
 */

public abstract class BaseFragment<P extends BaseMvpPresenter> extends Fragment implements BaseMvpView {
    protected View view;

    private int fId;

    private Unbinder mUnbinder;
    protected P mPresenter;

    public BaseFragment() {
        fId++;
    }

    public String getMTag() {
        return this.getClass().getName() + fId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(setContentViewId(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mPresenter = setPresenter();
        initWidgets();
        onGetBundle(getArguments());
        Log.e("BaseFragment", "----onCreateView----");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initMState();
        Log.e("BaseFragment", "----onViewCreated----");
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        Log.e("BaseFragment", "----onActivityCreated----");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("BaseFragment", "----onResume()----");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("BaseFragment", "----onStart()----");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null)
            mUnbinder.unbind();
        if (mPresenter != null)
            mPresenter.detach();
    }

    protected abstract void initWidgets();

    protected abstract void initData();

    // 子类初始化一些状态
    protected void initMState() {

    }

    // 可以获取上一个Fragment传过来的数据
    protected void onGetBundle(Bundle bundle) {

    }

    //把控制返回键的权利交给了Fragment
    public boolean onBack() {
        return false;
    }

    // 子类可以控制生命
    public boolean finish() {
        return false;
    }

    public View getRootView() {
        return view;
    }

    /**
     * 设置presenter层对象
     *
     * @return
     */
    protected abstract P setPresenter();

    /**
     * 获取Fragment的布局ID
     *
     * @return
     */
    protected abstract int setContentViewId();

}
