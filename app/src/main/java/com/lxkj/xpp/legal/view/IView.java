package com.lxkj.xpp.legal.view;

import android.os.Bundle;

import com.lxkj.xpp.legal.base.BaseMvpView;

/**
 * Created by 熊萍萍 on 2016/12/23/023.
 */

public interface IView extends BaseMvpView {
    void changeDataForUi(Bundle bundle, int id);
}
