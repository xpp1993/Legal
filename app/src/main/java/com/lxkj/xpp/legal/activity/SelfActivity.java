package com.lxkj.xpp.legal.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseActivity;
import com.lxkj.xpp.legal.base.BaseMvpPresenter;
import com.lxkj.xpp.legal.constant.Constant;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 熊萍萍 on 2017/2/23/023.
 */

public class SelfActivity extends BaseActivity {
    @BindView(R.id.status_bar)
    public LinearLayout status_bar;
    @BindView(R.id.bar_tv_title_center)
    public TextView title;
    @BindView(R.id.bar_iv_left)
    public ImageView image_back;
    @BindView(R.id.geren_head_ll)
    public LinearLayout self_head;
    @BindView(R.id.geren_zhiye_tv)
    public TextView self_career;
    @BindView(R.id.geren_danwei_tv)
    public TextView self_unit;
    @BindView(R.id.geren_zhiwu_tv)
    public TextView self_duty;

    @Override
    protected int setContentViewResId() {
        return R.layout.fragment_geren;
    }

    @Override
    protected BaseMvpPresenter setPresenter() {
        return null;
    }

    @Override
    protected void initWidgets(Bundle savedInstanceState) {
        super.initWidgets(savedInstanceState);
        initState(status_bar);
        image_back.setVisibility(View.VISIBLE);
        title.setText("个人信息");
    }

    @OnClick({R.id.bar_iv_left, R.id.geren_head_ll, R.id.geren_zhiye_tv, R.id.geren_danwei_tv, R.id.geren_zhiwu_tv})
    void click(View view) {
        Intent intent;
        Bundle bundle;
        switch (view.getId()) {
            case R.id.bar_iv_left:
                finish();
                break;
            case R.id.geren_head_ll://修改头像
                break;
            case R.id.geren_zhiye_tv://修改职业
                intent = new Intent(this, ChangeActivity.class);
                bundle = new Bundle();
                bundle.putInt(Constant.appFinal.CHANGE_KEY, Constant.appFinal.CHANGE_CAREER);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.geren_danwei_tv://修改工作单位
                intent = new Intent(this, ChangeActivity.class);
                bundle = new Bundle();
                bundle.putInt(Constant.appFinal.CHANGE_KEY, Constant.appFinal.CHANGE_UINIT);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.geren_zhiwu_tv://修改职务
                intent = new Intent(this, ChangeActivity.class);
                bundle = new Bundle();
                bundle.putInt(Constant.appFinal.CHANGE_KEY, Constant.appFinal.CHANGE_DUTY);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
