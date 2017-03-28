package com.lxkj.xpp.legal.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.widget.EaseTitleBar;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseFragment;
import com.lxkj.xpp.legal.base.BaseMvpPresenter;
import com.lxkj.xpp.legal.event.NavFragmentEvent;
import com.lxkj.xpp.legal.utils.CommonUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by 熊萍萍 on 2017/2/27/027.
 * 设置页面
 */

public class ShezhiFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.set_change_pw)
    ImageView reset_password;//重置密码
    @BindView(R.id.set_change_phone)
    ImageView reset_phone;//修改绑定手机号
    @BindView(R.id.set_title_bar)
    EaseTitleBar easeTitleBar;
    @BindView(R.id.set_login_out)
    TextView login_out;

    @Override
    protected void initWidgets() {
    }

    @Override
    protected void initData() {
        StatusBarCompat.setStatusBarColor(getActivity(), ContextCompat.getColor(CommonUtils.getContext(), R.color.colorPrimaryDark));
        easeTitleBar.getLeftLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        reset_password.setOnClickListener(this);
        reset_phone.setOnClickListener(this);
        login_out.setOnClickListener(this);
    }

    @Override
    protected BaseMvpPresenter setPresenter() {
        return null;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_shezhi;
    }

    @Override
    public void onShowToast(String message, int i) {

    }

    @Override
    public void navigateToFragment(BaseFragment baseFragment, Bundle bundle) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_change_pw://跳转到修改密码页面
                EventBus.getDefault().post(new NavFragmentEvent(new ChangePwFragment()));
                break;
            case R.id.set_change_phone://跳转到修改手机号页面
                EventBus.getDefault().post(new NavFragmentEvent(new ChangePhoneFragment()));
                break;
            case R.id.set_login_out://退出登录
                getActivity().getSupportFragmentManager().popBackStack(null, 1);
                EventBus.getDefault().post(new NavFragmentEvent(new LoginFragment()));
                break;
        }
    }
}
