package com.lxkj.xpp.legal.base;

/**
 * Created by 熊萍萍 on 2016/12/23/023.
 * 基础业务实现层presenter
 *
 * @param <V> 需要植入的view实现，通常为activity、Fragment
 */

public abstract class BaseMvpPresenter<V extends BaseMvpView, M extends BaseMvpModel> {
    protected V mView;
    protected M mModel;

    /**
     * 在构造方法里关联view设置model
     */
    public BaseMvpPresenter(V mView) {
        this.mView = mView;
        this.mModel = setModel();
    }

    protected abstract M setModel();

    public void detach() {
        mView = null;
        mModel = null;
    }
}
