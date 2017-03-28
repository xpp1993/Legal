package com.lxkj.xpp.legal.base;

import android.os.Bundle;

/**
 * Created by 熊萍萍 on 2016/12/23/023.
 * 基础View层接口
 */

public interface BaseMvpView {
    /**
     * @param message
     * @param i
     */
    void onShowToast(String message, int i);

    /**
     * 跳转到某个页面
     *
     * @param baseFragment
     * @param bundle
     */
    void navigateToFragment(BaseFragment baseFragment, Bundle bundle);
}
