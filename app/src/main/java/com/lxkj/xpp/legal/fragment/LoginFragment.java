package com.lxkj.xpp.legal.fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.adapter.MyViewPagerAdapter;
import com.lxkj.xpp.legal.base.BaseFragment;
import com.lxkj.xpp.legal.base.BaseMvpPresenter;

import butterknife.BindView;

/**
 * Created by 熊萍萍 on 2017/2/27/027.
 * 登录页面
 */

public class LoginFragment extends BaseFragment {
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.login_vp)
    ViewPager mViewPager;
    private MyViewPagerAdapter myViewPagerAdapter;

    @Override
    protected void initWidgets() {
        myViewPagerAdapter = new MyViewPagerAdapter(getChildFragmentManager());
        CommonLoginFragment commonLoginFragment = new CommonLoginFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", "密码登录");
        commonLoginFragment.setArguments(bundle);
        FastFragment fastFragment = new FastFragment();
        bundle = new Bundle();
        bundle.putString("title", "验证码登录");
        fastFragment.setArguments(bundle);
        myViewPagerAdapter.addFragment(commonLoginFragment);
        myViewPagerAdapter.addFragment(fastFragment);
        mViewPager.setAdapter(myViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initData() {

    }


    @Override
    protected int setContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    public boolean finish() {
        return true;
    }

    @Override
    protected BaseMvpPresenter setPresenter() {
        return null;
    }

    @Override
    public void onShowToast(String message, int i) {

    }

    @Override
    public void navigateToFragment(BaseFragment baseFragment, Bundle bundle) {

    }
}
