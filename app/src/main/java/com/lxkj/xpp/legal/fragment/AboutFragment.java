package com.lxkj.xpp.legal.fragment;

import android.os.Bundle;

import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseFragment;
import com.lxkj.xpp.legal.base.BaseMvpPresenter;

/**
 * Created by 熊萍萍 on 2017/2/27/027.
 * 关于我们页面
 */

public class AboutFragment extends BaseFragment {


    @Override
    protected void initWidgets() {

    }

    @Override
    protected void initData() {
    }

    @Override
    protected BaseMvpPresenter setPresenter() {
        return null;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_about;
    }

//    @OnClick({R.id.bar2_iv_left})
//    void click(View view) {
//        switch (view.getId()) {
//            case R.id.bar2_iv_left:
//                getActivity().onBackPressed();
//                break;
//        }
//    }

    @Override
    public void onShowToast(String message, int i) {

    }

    @Override
    public void navigateToFragment(BaseFragment baseFragment, Bundle bundle) {

    }
}
