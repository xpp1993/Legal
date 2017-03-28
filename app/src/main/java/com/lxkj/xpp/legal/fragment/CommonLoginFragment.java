package com.lxkj.xpp.legal.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseFragment;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.event.NavFragmentEvent;
import com.lxkj.xpp.legal.presenter.LoginPresenter;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.utils.ToastUtlis;
import com.lxkj.xpp.legal.view.LoginView;
import com.lxkj.xpp.legal.widget.CustomDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 熊萍萍 on 2017/3/20/020.
 * 普通登录页面,用户名密码登录
 */

public class CommonLoginFragment extends BaseFragment<LoginPresenter> implements LoginView {
    @BindView(R.id.comm_login_tv)
    TextView login_tv;
    @BindView(R.id.login_wechat)
    TextView wx_login;//微信登录
    @BindView(R.id.login_QQ)
    TextView QQ_login;//QQ登录
    @BindView(R.id.comm_login_phone_et)
    EditText phone_et;
    @BindView(R.id.comm_login_pw_et)
    EditText password_et;
    CustomDialog progressBar;

    @Override
    protected void initWidgets() {
        String phone = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.appFinal.LOGIN_NAME_KEY, "");
        String password = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.appFinal.LOGIN_PASSWORD_KEY, "");
        if (!TextUtils.isEmpty(phone)) {
            phone_et.setText(phone);
        }
        if (!TextUtils.isEmpty(password)) {
            password_et.setText(password);
        }
    }

    @OnClick({R.id.comm_login_tv, R.id.login_wechat, R.id.login_QQ})
    void click(View v) {
        switch (v.getId()) {
            case R.id.comm_login_tv://普通登录
                mPresenter.doLogin(CommonUtils.getContext(), phone_et, password_et);
                break;
            case R.id.login_wechat://微信登录
                break;
            case R.id.login_QQ://QQ登录
                break;
            default:
                break;
        }
    }

    @Override
    protected void onGetBundle(Bundle bundle) {
        super.onGetBundle(bundle);

    }

    @Override
    protected void initData() {
        progressBar = new CustomDialog(getActivity(), R.style.CustomDialog);
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setText(getString(R.string.login_warn));
    }

    @Override
    protected LoginPresenter setPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.common_login;
    }

    @Override
    public void onShowToast(String message, int i) {
        ToastUtlis.showToastInUIThread(message);
        switch (i) {
            case Constant.REGIST.toast_userName_empty://用户名为空
                password_et.requestFocus();
                break;
            case Constant.REGIST.toast_password_empty://密码为空
                password_et.requestFocus();
                break;
            case Constant.REGIST.toast_msg:
                break;
        }
    }

    @Override
    public void navigateToFragment(BaseFragment baseFragment, Bundle bundle) {
        EventBus.getDefault().post(new NavFragmentEvent(baseFragment, bundle));
    }

    @Override
    public void showProgress() {
        progressBar.show();
    }

    @Override
    public void dismissProgress() {
        progressBar.dismiss();
    }

    @Override
    public void animation() {

    }
}
