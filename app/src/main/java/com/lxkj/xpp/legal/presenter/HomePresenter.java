package com.lxkj.xpp.legal.presenter;

import com.lxkj.xpp.legal.base.BaseMvpPresenter;
import com.lxkj.xpp.legal.model.HomeModel;
import com.lxkj.xpp.legal.view.HomeView;

/**
 * Created by 熊萍萍 on 2016/12/27/027.
 */

public class HomePresenter extends BaseMvpPresenter<HomeView,HomeModel> {
    /**
     * 在构造方法里关联view设置model
     *
     * @param mView
     */
    public HomePresenter(HomeView mView) {
        super(mView);

    }

    @Override
    protected HomeModel setModel() {
        return new HomeModel();
    }
}
