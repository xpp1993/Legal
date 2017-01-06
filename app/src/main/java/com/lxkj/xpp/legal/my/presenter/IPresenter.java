package com.lxkj.xpp.legal.my.presenter;

import com.lxkj.xpp.legal.base.BaseMvpPresenter;
import com.lxkj.xpp.legal.my.model.IModel;
import com.lxkj.xpp.legal.my.mvpview.IView;

/**
 * Created by 熊萍萍 on 2016/12/23/023.
 */

public class IPresenter extends BaseMvpPresenter<IView,IModel> {
    /**
     * 在构造方法里关联view设置model
     *
     * @param mView
     */
    public IPresenter(IView mView) {
        super(mView);
    }

    @Override
    protected IModel setModel() {
        return new IModel();
    }

}
