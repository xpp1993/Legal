package com.lxkj.xpp.legal.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.easeui.widget.EaseTitleBar;
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
 * 注册页面
 */

public class RegistFragment extends BaseFragment<LoginPresenter> implements LoginView {
    @BindView(R.id.register_code_tv)
    TextView check_code;
    @BindView(R.id.register_tv)
    TextView regist;
    @BindView(R.id.register_phone_et)
    EditText regist_phone_et;
    @BindView(R.id.register_code_et)
    EditText regist_code_et;
    @BindView(R.id.register_pw_et)
    EditText regist_pw_et;
    @BindView(R.id.register_title_bar)
    EaseTitleBar easeTitleBar;
    CustomDialog progressDialog;
    private int remainRockTime;//倒计时剩余时长
    @Override
    protected void initWidgets() {

    }

    @Override
    protected void initData() {
        remainRockTime = Constant.appFinal.TOTAL_ROCK_TIME;
        progressDialog = new CustomDialog(getActivity(),R.style.CustomDialog);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setText(getString(R.string.register_warn));
        easeTitleBar.getLeftLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回密码登录页面
                mPresenter.toCommLogin();
            }
        });
    }

    @Override
    protected void onGetBundle(Bundle bundle) {
        super.onGetBundle(bundle);
    }

    @Override
    protected LoginPresenter setPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_register;
    }


    @OnClick({R.id.register_code_tv, R.id.register_tv})
    void click(View view) {
        switch (view.getId()) {
            case R.id.register_code_tv://获取验证码
                // 1001 表示验证码类型为用户注册
                mPresenter.getCheckCode(CommonUtils.getContext(), regist_phone_et, 1001);
                break;
            case R.id.register_tv://注册
                mPresenter.doRegister(CommonUtils.getContext(), regist_phone_et, regist_code_et, regist_pw_et);
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
                regist_phone_et.requestFocus();
                break;
            case Constant.REGIST.toast_userName_error://手机号码格式错误
                regist_phone_et.setText("");
                regist_phone_et.requestFocus();
                break;
            case Constant.REGIST.toast_code://验证码为空
                regist_code_et.requestFocus();
                break;
            case Constant.REGIST.toast_password_empty://密码为空
                regist_pw_et.requestFocus();
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
        handler.post(new RockRunnable(remainRockTime, handler,check_code ));
    }
}
