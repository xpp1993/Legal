package com.lxkj.xpp.legal.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.easeui.widget.EaseTitleBar;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseFragment;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.event.NavFragmentEvent;
import com.lxkj.xpp.legal.presenter.LoginPresenter;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.utils.ToastUtlis;
import com.lxkj.xpp.legal.view.LoginView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * Created by 熊萍萍 on 2017/2/27/027.
 * 修改手机号码页面
 */

public class ChangePhoneFragment extends BaseFragment<LoginPresenter> implements LoginView, View.OnClickListener {
    @BindView(R.id.change_title_bar)
    EaseTitleBar easeTitleBar;
    @BindView(R.id.fragment_input_code)
    EditText input_code;
    @BindView(R.id.fragment_next)
    TextView next;
    @BindView(R.id.fragment_original_phone)
    TextView original_phone;
    @BindView(R.id.fragment_send_warn)
    TextView send_warn;
    @BindView(R.id.change_phone_code)
    TextView change_phone_code;
    String phone = null;

    @Override
    protected void initWidgets() {

    }

    @Override
    protected void onGetBundle(Bundle bundle) {
        super.onGetBundle(bundle);

    }

    @Override
    protected void initData() {
        input_code.requestFocus();
        phone = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.phone);
        original_phone.setText(phone);
        easeTitleBar.getLeftLayout().setOnClickListener(this);
        next.setOnClickListener(this);
        change_phone_code.setOnClickListener(this);
        easeTitleBar.getLeftLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    protected LoginPresenter setPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_change_phone;
    }



    @Override
    public void onShowToast(String message, int i) {
        ToastUtlis.showToastInUIThread(message);
    }

    @Override
    public void navigateToFragment(BaseFragment baseFragment, Bundle bundle) {
        EventBus.getDefault().post(new NavFragmentEvent(baseFragment, bundle));
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void animation() {
        send_warn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_title_bar:
                getActivity().onBackPressed();//返回设置页
                break;
            case R.id.fragment_next://下一步
                mPresenter.resetPhone(CommonUtils.getContext(), input_code);
                break;
            case R.id.change_phone_code:
                //发送验证码
                mPresenter.getCode(CommonUtils.getContext(), phone, String.valueOf(4001));
                break;
        }
    }
}
