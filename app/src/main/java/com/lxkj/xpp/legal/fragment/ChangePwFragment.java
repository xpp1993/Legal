package com.lxkj.xpp.legal.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.hyphenate.easeui.widget.EaseTitleBar;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseFragment;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.event.NavFragmentEvent;
import com.lxkj.xpp.legal.presenter.LoginPresenter;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.utils.ToastUtlis;
import com.lxkj.xpp.legal.view.LoginView;

import android.support.v4.app.FragmentManager;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * Created by 熊萍萍 on 2017/2/27/027.
 * 修改密码页面
 */

public class ChangePwFragment extends BaseFragment<LoginPresenter> implements LoginView, View.OnClickListener {

    @BindView(R.id.fragment_cpw_change)
    EditText fragment_cpw;
    @BindView(R.id.fragment_cpw_change_confirm)
    EditText confirm_password;
    @BindView(R.id.change_password_title_bar)
    EaseTitleBar easeTitleBar;
    @BindView(R.id.fragment_commit_change_pw)
    TextView fragment_commit_change_pw;

    @Override
    protected void initWidgets() {
    }

    @Override
    protected void initData() {
        fragment_cpw.requestFocus();
        easeTitleBar.getLeftLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        fragment_commit_change_pw.setOnClickListener(this);
    }

    @Override
    protected LoginPresenter setPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_password_change;
    }


    @Override
    public void onShowToast(String message, int i) {
        ToastUtlis.showToastInUIThread(message);
        switch (i) {
            case Constant.REGIST.toast_password_empty://新密码为空
                fragment_cpw.requestFocus();
                break;
            case Constant.REGIST.toast_password_error://两次密码不相同
                fragment_cpw.requestFocus();
                break;
            case Constant.REGIST.toast_password_confirm://确认密码为空
                confirm_password.requestFocus();
                break;
        }
    }

    @Override
    public void navigateToFragment(BaseFragment baseFragment, Bundle bundle) {
        EventBus.getDefault().post(new NavFragmentEvent(baseFragment, bundle));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_commit_change_pw://重置密码
                mPresenter.resetPassword(CommonUtils.getContext(), fragment_cpw, confirm_password);
                break;
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void animation() {
        //重置密码成功后，退出登录
        int num = getActivity().getSupportFragmentManager().getBackStackEntryCount();
        for (int i = 0; i < num; i++) {
            FragmentManager.BackStackEntry backStackEntry = getActivity().getSupportFragmentManager().getBackStackEntryAt(i);
            Log.d("ChangePwFragment", backStackEntry.getName());
        }
        CommonUtils.getPreference().putString(CommonUtils.getContext(), Constant.appFinal.LOGIN_PASSWORD_KEY, "");
        getActivity().getSupportFragmentManager().popBackStack(null, 1);
        EventBus.getDefault().post(new NavFragmentEvent(new LoginFragment()));
    }
}
