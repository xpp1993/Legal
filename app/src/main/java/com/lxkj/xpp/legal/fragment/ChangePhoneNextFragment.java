package com.lxkj.xpp.legal.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.easeui.widget.EaseTitleBar;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseFragment;
import com.lxkj.xpp.legal.event.NavFragmentEvent;
import com.lxkj.xpp.legal.presenter.LoginPresenter;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.view.LoginView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * Created by 熊萍萍 on 2017/2/27/027.
 * 修改手机号码2页面
 */

public class ChangePhoneNextFragment extends BaseFragment<LoginPresenter> implements LoginView, View.OnClickListener {
    @BindView(R.id.fragment_next_phone)
    EditText editText_phone;
    @BindView(R.id.fragment_next_code)
    EditText editText_code;
    @BindView(R.id.change_next_title_bar)
    EaseTitleBar easeTitleBar;
    @BindView(R.id.fragment_next_commit)
    TextView fragment_next_commit;
    @BindView(R.id.change_phone_next_code)
    TextView get_code;

    @Override
    protected void initWidgets() {

    }

    @Override
    protected void onGetBundle(Bundle bundle) {
        super.onGetBundle(bundle);

    }

    @Override
    protected void initData() {
        editText_phone.requestFocus();
        easeTitleBar.getLeftLayout().setOnClickListener(this);
        get_code.setOnClickListener(this);
        fragment_next_commit.setOnClickListener(this);
    }

    @Override
    protected LoginPresenter setPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_change_phone_next;
    }


    @Override
    public void onShowToast(String message, int i) {

    }

    @Override
    public void navigateToFragment(BaseFragment baseFragment, Bundle bundle) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void animation() {
        getActivity().getSupportFragmentManager().popBackStack(null, 1);
        EventBus.getDefault().post(new NavFragmentEvent(new LoginFragment()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_next_title_bar:
                getActivity().onBackPressed();
                break;
            case R.id.change_phone_next_code:
                mPresenter.getCheckCode(CommonUtils.getContext(), editText_phone, 5001);
                break;
            case R.id.fragment_next_commit:
                mPresenter.resetPhoneNext(CommonUtils.getContext(), editText_code, editText_phone);
                break;
        }
    }
}
