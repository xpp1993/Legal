package com.lxkj.xpp.legal.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseFragment;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.event.NavFragmentEvent;
import com.lxkj.xpp.legal.model.RockRunnable;
import com.lxkj.xpp.legal.presenter.LoginPresenter;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.utils.ToastUtlis;
import com.lxkj.xpp.legal.view.LoginView;
import com.lxkj.xpp.legal.widget.CustomDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 熊萍萍 on 2017/2/27/027.
 * 验证码登录页面
 */

public class FastFragment extends BaseFragment<LoginPresenter> implements LoginView {
    @BindView(R.id.fast_login_tv)
    TextView login_tv;
    @BindView(R.id.login_wechat)
    TextView wx_login;//微信登录
    @BindView(R.id.login_QQ)
    TextView QQ_login;//QQ登录
    @BindView(R.id.to_register_tv)
    TextView to_register;
    @BindView(R.id.fast_code_tv)
    TextView get_check_code;
    @BindView(R.id.fast_login_phone_et)
    EditText phone_et;
    @BindView(R.id.fast_login_code_et)
    EditText code_et;
    CustomDialog progressDialog;
    private int remainRockTime;//倒计时剩余时长
    @Override
    protected void initWidgets() {

    }

    @Override
    protected void initData() {
        progressDialog = new CustomDialog(getActivity(), R.style.CustomDialog);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setText(getString(R.string.login_warn));
        remainRockTime = Constant.appFinal.TOTAL_ROCK_TIME;
    }

    @Override
    protected LoginPresenter setPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_fast_login;
    }

    @OnClick({R.id.to_register_tv, R.id.fast_login_tv, R.id.login_wechat, R.id.login_QQ, R.id.fast_code_tv})
    void click(View view) {
        switch (view.getId()) {
            case R.id.to_register_tv://跳转到注册页面
                mPresenter.toRegister();
                break;
            case R.id.fast_login_tv://验证码登录
                mPresenter.fastLogin(CommonUtils.getContext(), phone_et, code_et);
                break;
            case R.id.login_wechat://微信登录
                break;
            case R.id.login_QQ://QQ登录
                break;
            case R.id.fast_code_tv://获取验证码
                // 2001 表示验证码登录
                mPresenter.getCheckCode(CommonUtils.getContext(), phone_et, 2001);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean finish() {
        return true;
    }

    @Override
    public void onShowToast(String message, int i) {
        ToastUtlis.showToastInUIThread(message);
        switch (i) {
            case Constant.REGIST.toast_userName_empty://手机号码为空
                phone_et.requestFocus();
                break;
            case Constant.REGIST.toast_userName_error://手机号码格式错误
                phone_et.setText("");
                phone_et.requestFocus();
                break;
            case Constant.REGIST.toast_code://验证码为空
                code_et.requestFocus();
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
        progressDialog.show();
    }

    @Override
    public void dismissProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void animation() {
        //开始倒计时
        final Handler handler = new Handler();
        handler.post(new RockRunnable(remainRockTime, handler, get_check_code));
    }


}
