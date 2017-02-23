package com.lxkj.xpp.legal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseActivity;
import com.lxkj.xpp.legal.base.BaseMvpPresenter;
import com.lxkj.xpp.legal.utils.ActivityCollector;
import com.lxkj.xpp.legal.utils.CommonUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 熊萍萍 on 2017/2/23/023.
 */

public class SheZhiActivity extends BaseActivity {
    @BindView(R.id.status_bar2)
    public LinearLayout status_bar2;
    @BindView(R.id.bar2_iv_left)
    public ImageView image_back;
    @BindView(R.id.bar2_tv_title_center)
    public TextView title;
    @BindView(R.id.shezhi_xinxi_tv)
    public TextView to_self_info;
    @BindView(R.id.shezhi_cpw)
    public TextView cpw;
    @BindView(R.id.shezhi_loginout)
    public Button loginout;

    @Override
    protected int setContentViewResId() {
        return R.layout.fragment_shezhi;
    }

    @Override
    protected void initWidgets(Bundle savedInstanceState) {
        super.initWidgets(savedInstanceState);
        initState(status_bar2);
        CommonUtils.setMystatusBar(this);
        image_back.setVisibility(View.VISIBLE);
        title.setText("设置");
    }

    @Override
    protected BaseMvpPresenter setPresenter() {
        return null;
    }

    @OnClick({R.id.bar2_iv_left, R.id.shezhi_xinxi_tv, R.id.shezhi_cpw, R.id.shezhi_loginout})
    void click(View view) {
        switch (view.getId()) {
            case R.id.bar2_iv_left:
                finish();
                break;
            case R.id.shezhi_xinxi_tv://跳转到修改个人信息页面
                Intent intent_self = new Intent(this, SelfActivity.class);
                startActivity(intent_self);
                break;
            case R.id.shezhi_cpw://跳转到修改密码页面
                startActivity(new Intent(this,ChangePwActivity.class));
                break;
            case R.id.shezhi_loginout://退出账号，回到登陆页面
                ActivityCollector.finishAll();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}
