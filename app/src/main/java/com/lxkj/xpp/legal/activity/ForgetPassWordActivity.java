package com.lxkj.xpp.legal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseActivity;
import com.lxkj.xpp.legal.base.BaseMvpPresenter;
import com.lxkj.xpp.legal.utils.CommonUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 熊萍萍 on 2017/2/21/021.
 */

public class ForgetPassWordActivity extends BaseActivity  {
    @BindView(R.id.status_bar2)
    public LinearLayout forget_password_status;
    @BindView(R.id.bar2_tv_title_center)
    public TextView title_center;
    @BindView(R.id.bar2_iv_left)
    public ImageView image_back;

    @Override
    protected void initWidgets(Bundle savedInstanceState) {
        super.initWidgets(savedInstanceState);
        /**
         * 白底黑字，改变状态栏图标为深色
         */
        CommonUtils.setMystatusBar(this);
        /**
         * 改变状态栏的颜色
         */
        initState(forget_password_status);
        title_center.setText("找回密码");
        image_back.setVisibility(View.VISIBLE);
    }

    @Override
    protected int setContentViewResId() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected BaseMvpPresenter setPresenter() {
        return null;
    }

    @OnClick({R.id.bar2_iv_left})
    void click(View view) {
        switch (view.getId()) {
            case R.id.bar2_iv_left://跳转到登录页面
                finish();
                break;
            default:
                break;
        }
    }
}
