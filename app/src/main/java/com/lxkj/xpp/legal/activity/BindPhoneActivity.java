package com.lxkj.xpp.legal.activity;

import android.content.Intent;
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

public class BindPhoneActivity extends BaseActivity {
    @BindView(R.id.status_bar)
    public LinearLayout status_bar;
    @BindView(R.id.bar_iv_left)
    public ImageView image_back;
    @BindView(R.id.bar_tv_title_center)
    public TextView title;
    @BindView(R.id.fragment_phone_tv)
    public TextView change_bind_phone;

    @Override
    protected int setContentViewResId() {
        return R.layout.fragment_phone;
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
        title.setText("绑定手机号");
    }

    @OnClick({R.id.bar_iv_left, R.id.fragment_phone_tv})
    void click(View view) {
        switch (view.getId()) {
            case R.id.bar_iv_left:
                finish();
                break;
            case R.id.fragment_phone_tv:
                Intent intent = new Intent(this, ChangeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.appFinal.CHANGE_KEY, Constant.appFinal.CHANGE_PHONE);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }
}
