package com.lxkj.xpp.legal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.activity.AboutActivity;
import com.lxkj.xpp.legal.activity.BindPhoneActivity;
import com.lxkj.xpp.legal.activity.FeedBackActivity;
import com.lxkj.xpp.legal.activity.IDRegistActivity;
import com.lxkj.xpp.legal.activity.SelfActivity;
import com.lxkj.xpp.legal.activity.SheZhiActivity;
import com.lxkj.xpp.legal.base.BaseFragment;
import com.lxkj.xpp.legal.mvppresenter.IPresenter;
import com.lxkj.xpp.legal.mvpview.IView;
import com.lxkj.xpp.legal.utils.CommonUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 熊萍萍 on 2016/12/22/022.
 */

public class MeFragment extends BaseFragment<IPresenter> implements IView {
    @BindView(R.id.me_status_bar)
    public LinearLayout linear_status;
    @BindView(R.id.fragment_me_shezhi)
    public ImageView fragment_me_shezhi;
    @BindView(R.id.me_geren)
    public ImageView fragment_me_geren;
    @BindView(R.id.me_shiming)
    public ImageView fragment_me_shiming;
    @BindView(R.id.me_haoma)
    public ImageView fragment_me_haoma;
    @BindView(R.id.me_kefu)
    public ImageView fragment_me_kefu;
    @BindView(R.id.me_wome)
    public ImageView fragment_me_women;
    @BindView(R.id.me_yijian)
    public ImageView fragment_me_yijian;

    @Override
    protected IPresenter setPresenter() {
        return new IPresenter(this);
    }

    @Override
    protected void initWidgets(Bundle savedInstanceState) {
        initState(linear_status);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_me;
    }

    @OnClick({R.id.fragment_me_shezhi, R.id.me_geren, R.id.me_shiming, R.id.me_haoma, R.id.me_kefu, R.id.me_wome, R.id.me_yijian})
    void click(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.fragment_me_shezhi://跳转到设置页面
                intent = new Intent(getActivity(), SheZhiActivity.class);
                startActivity(intent);
                break;
            case R.id.me_geren://跳转到个人页面
                intent = new Intent(getActivity(), SelfActivity.class);
                startActivity(intent);
                break;
            case R.id.me_shiming://跳转到实名页面
                intent = new Intent(getActivity(), IDRegistActivity.class);
                startActivity(intent);
                break;
            case R.id.me_haoma://跳转到修改绑定号码页面
                intent = new Intent(getActivity(), BindPhoneActivity.class);
                startActivity(intent);
                break;
            case R.id.me_kefu://跳转到客服中心页面
                Toast.makeText(CommonUtils.getContext(),"Ui未出来！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.me_wome://跳转到关于我们页面
                intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.me_yijian://跳转到意见反馈页面
                intent = new Intent(getActivity(), FeedBackActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}
