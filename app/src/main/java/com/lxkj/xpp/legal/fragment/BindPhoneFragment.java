package com.lxkj.xpp.legal.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseFragment;
import com.lxkj.xpp.legal.base.BaseMvpPresenter;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.event.NavFragmentEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 熊萍萍 on 2017/2/27/027.
 * 修改绑定的密码页面
 */

public class BindPhoneFragment extends BaseFragment {
    @BindView(R.id.fragment_phone_tv)
    public TextView change_bind_phone;

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
        return R.layout.fragment_phone;
    }

    @OnClick({R.id.fragment_phone_tv})
    void click(View view) {
        switch (view.getId()) {
//            case R.id.bar_iv_left:
//                getActivity().onBackPressed();
//                break;
            case R.id.fragment_phone_tv:
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.appFinal.CHANGE_KEY, Constant.appFinal.CHANGE_PHONE);
                EventBus.getDefault().post(new NavFragmentEvent(new ChangePhoneFragment(), bundle));
                break;
        }
    }

    @Override
    public void onShowToast(String message, int i) {

    }

    @Override
    public void navigateToFragment(BaseFragment baseFragment, Bundle bundle) {

    }
}