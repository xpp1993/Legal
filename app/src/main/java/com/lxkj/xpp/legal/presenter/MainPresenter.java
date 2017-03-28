package com.lxkj.xpp.legal.presenter;
import com.lxkj.xpp.legal.base.BaseMvpPresenter;
import com.lxkj.xpp.legal.model.MainModel;
import com.lxkj.xpp.legal.view.MainView;

/**
 * Created by 熊萍萍 on 2016/12/23/023.
 */

public class MainPresenter extends BaseMvpPresenter<MainView,MainModel> {
    /**
     * 在构造方法里关联view设置model
     *
     * @param mView
     */
    public MainPresenter(MainView mView) {
        super(mView);
    }

    @Override
    protected MainModel setModel() {
        return new MainModel();
    }
}
