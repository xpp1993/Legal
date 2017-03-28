package com.lxkj.xpp.legal.fragment;

import android.os.Bundle;

import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseFragment;
import com.lxkj.xpp.legal.presenter.HomePresenter;
import com.lxkj.xpp.legal.view.HomeView;

/**
 * Created by 熊萍萍 on 2016/12/22/022.
 * 首页
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeView {

    @Override
    protected HomePresenter setPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected void initWidgets() {
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onShowToast(String message, int i) {

    }

    @Override
    public void navigateToFragment(BaseFragment baseFragment, Bundle bundle) {

    }

//    @OnClick({R.id.homepage_chat_iv})
//    void click(View view) {
//        switch (view.getId()) {
//            case R.id.homepage_chat_iv://
//                EventBus.getDefault().post(new NavFragmentEvent(new ChatConversationListFragment()));
//                break;
//        }
//    }
}
