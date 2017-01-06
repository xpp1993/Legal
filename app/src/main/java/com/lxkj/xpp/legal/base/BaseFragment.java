package com.lxkj.xpp.legal.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 熊萍萍 on 2016/12/27/027.
 */

public abstract class BaseFragment<P extends BaseMvpPresenter> extends Fragment implements BaseMvpView {
    //是否可见
    protected boolean isVisible = false;
    //标志位，标志Fragment已经初始化完成。
    public boolean isPrepared = false;
    private Unbinder mUnbinder;
    protected P mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setContentViewId(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mPresenter = setPresenter();
        initWidgets(savedInstanceState);
        isVisible = savedInstanceState != null && savedInstanceState.getBoolean("isVisible", false);
        onGetBunndle(getArguments());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListener();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepared = true;
        if (!isVisible && getUserVisibleHint())
            onFragmentVisibleChange(true);
        if (isVisible && !getUserVisibleHint()) {
            onFragmentVisibleChange(true);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isPrepared)
            return;
        if (isVisible && !getUserVisibleHint()) {
            onFragmentVisibleChange(false);
        } else if (!isVisible && getUserVisibleHint()) {
            onFragmentVisibleChange(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isVisible", isVisible);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mPresenter.detach();
    }

    protected void onFragmentVisibleChange(boolean isVisible) {
        this.isVisible = isVisible;
    }

    // Fragment 直接bundle 的传递
    public void onGetBunndle(Bundle arguments) {

    }
    /**
     * 监听事件
     */
    protected abstract void initListener();

    /**
     * 设置presenter层对象
     *
     * @return
     */
    protected abstract P setPresenter();

    /**
     * 初始化组件工作
     *
     * @param savedInstanceState
     */
    protected abstract void initWidgets(Bundle savedInstanceState);

    /**
     * 获取Fragment的布局ID
     *
     * @return
     */
    protected abstract int setContentViewId();
}
