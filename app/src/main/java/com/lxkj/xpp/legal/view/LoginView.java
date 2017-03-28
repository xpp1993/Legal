package com.lxkj.xpp.legal.view;

import com.lxkj.xpp.legal.base.BaseMvpView;

/**
 * Created by 熊萍萍 on 2017/3/6/006.
 */

public interface LoginView extends BaseMvpView {
    void showProgress();

    void dismissProgress();

    /**
     * 动画
     */
    void animation();
}
