package com.lxkj.xpp.legal.fragment;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseFragment;
import com.lxkj.xpp.legal.mvppresenter.HomePresenter;
import com.lxkj.xpp.legal.mvpview.HomeView;

import butterknife.BindView;

/**
 * Created by 熊萍萍 on 2016/12/22/022.
 * 首页
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeView {
    @BindView(R.id.main_status_bar)
    public LinearLayout linear_bar;

//    @Override
//    protected void initListener() {
//
//    }

    @Override
    protected HomePresenter setPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected void initWidgets(Bundle savedInstanceState) {
        /**
         * 动态的设置状态栏  实现沉浸式状态栏
         */
        initState(linear_bar);
    }
    @Override
    protected int setContentViewId() {
        return R.layout.fragment_main;
    }

}
