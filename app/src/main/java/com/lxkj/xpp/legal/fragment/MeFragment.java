package com.lxkj.xpp.legal.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseFragment;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.event.NavFragmentEvent;
import com.lxkj.xpp.legal.presenter.IPresenter;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.view.IView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 熊萍萍 on 2016/12/22/022.
 * 我的页面
 */

public class MeFragment extends BaseFragment<IPresenter> implements IView {
    @BindView(R.id.fragment_me_editor)
    ImageView fragment_me_editor;//编辑个人资料
    @BindView(R.id.fragment_me_set)
    ImageView fragment_me_set;//设置
    @BindView(R.id.fragment_me_user_feedback)
    ImageView fragment_me_user_feedback;//用户反馈
    @BindView(R.id.fragment_me_to_real)
    TextView fragment_me_to_real;//实名认证
    @BindView(R.id.fragment_me_to_seniority)
    ImageView fragment_me_to_seniority;//资历认证
    @BindView(R.id.fragment_me_username)
    TextView textView_name;
    @BindView(R.id.fragment_me_duty)
    TextView textView_duty;
    @BindView(R.id.fragment_me_company)
    TextView textView_company;
    @BindView(R.id.fragment_me_version)
    TextView textView_verion;

    @Override
    protected IPresenter setPresenter() {
        return new IPresenter(this);
    }

    @Override
    protected void initWidgets() {

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("meFragement", "---onStart");
    }

    @Override
    public void onResume() {
        super.onStart();
        Log.e("meFragement", "---onResume");
    }

    @Override
    protected void initData() {
        String nickName = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.nickName);
        String phone = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.phone);
        String unit = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.workUnit);
        String duty = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.workJob);
        String uuid = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.uid);
        String realNameStatusStr = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.realNameStatusStr);
        boolean realName = CommonUtils.getPreference().getBoolean(CommonUtils.getContext(), Constant.LOGIN.realName);
        textView_name.setText(nickName == null ? phone : nickName);
        if (realName) {
            fragment_me_to_real.setCompoundDrawables(null, null, null, null);
            fragment_me_to_real.setClickable(false);
        }
        fragment_me_to_real.setText(realNameStatusStr);
        textView_verion.setText("聚力号:" + uuid);
        if (!TextUtils.isEmpty(unit)) {
            textView_company.setText(unit);
            textView_company.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(duty)) {
            textView_duty.setText(duty);
            textView_duty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_me;
    }

    @OnClick({R.id.fragment_me_editor, R.id.my_state, R.id.fragment_me_set, R.id.fragment_me_user_feedback, R.id.fragment_me_to_real, R.id.fragment_me_to_seniority})
    void click(View view) {
        switch (view.getId()) {
            case R.id.fragment_me_editor://跳转到编辑个人资料页面
                EventBus.getDefault().post(new NavFragmentEvent(new SelfFragment()));
                break;
            case R.id.my_state://我的动态
                EventBus.getDefault().post(new NavFragmentEvent(new SingleCircleFragment()));
                break;
            case R.id.fragment_me_set://跳转到设置页面
                EventBus.getDefault().post(new NavFragmentEvent(new ShezhiFragment()));
                break;
            case R.id.fragment_me_user_feedback://跳转到用户反馈页面
                EventBus.getDefault().post(new NavFragmentEvent(new FeedBackFragment()));
                break;
            case R.id.fragment_me_to_real://实名认证页面
                EventBus.getDefault().post(new NavFragmentEvent(new IDRegistFragment()));
                break;
            case R.id.fragment_me_to_seniority://跳转到资历认证页面
                EventBus.getDefault().post(new NavFragmentEvent(new FragmentSeniority()));
                break;

            default:
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("MeFragment", "---onPause----");
    }


    @Override
    public void onShowToast(String message, int i) {

    }

    @Override
    public void navigateToFragment(BaseFragment baseFragment, Bundle bundle) {

    }

    @Override
    public void changeDataForUi(Bundle bundle, int id) {

    }
}
