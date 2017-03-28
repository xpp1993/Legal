package com.lxkj.xpp.legal.view;
import android.os.Bundle;

import com.lxkj.xpp.legal.base.BaseMvpView;

/**
 * Created by 熊萍萍 on 2017/3/24/024.
 * 帖书
 */

public interface CircleView extends BaseMvpView {
    void  updataUI(Bundle bundle, int id);

    void onLoadingOrRefreshFailed();
    void publishComment(Bundle bundle,int id);
}
