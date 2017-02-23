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
 * Created by 熊萍萍 on 2017/2/23/023.
 */

public class FeedBackActivity extends BaseActivity {
    @BindView(R.id.status_bar2)
    public LinearLayout status_bar;
    @BindView(R.id.bar2_tv_title_center)
    public TextView title;
    @BindView(R.id.bar2_iv_left)
    public ImageView image_back;

    @Override
    protected int setContentViewResId() {
        return R.layout.fragment_feedback;
    }

    @Override
    protected BaseMvpPresenter setPresenter() {
        return null;
    }

    @Override
    protected void initWidgets(Bundle savedInstanceState) {
        super.initWidgets(savedInstanceState);
        initState(status_bar);
        CommonUtils.setMystatusBar(this);
        image_back.setVisibility(View.VISIBLE);
        title.setText("意见反馈");
    }

    @OnClick({R.id.bar2_iv_left})
    void click(View view) {
        switch (view.getId()) {
            case R.id.bar2_iv_left:
                finish();
                break;
        }
    }
}
