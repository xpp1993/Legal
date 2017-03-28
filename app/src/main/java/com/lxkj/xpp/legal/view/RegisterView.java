package com.lxkj.xpp.legal.view;

import com.lxkj.xpp.legal.base.BaseMvpView;

/**
 * Created by 熊萍萍 on 2017/3/7/007.
 */

public interface RegisterView extends BaseMvpView {

    void onShowProgress();

    void onHideProgress();

    void onShowWarnRealName();
}
