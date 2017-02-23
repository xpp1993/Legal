package com.lxkj.xpp.legal.activity;

import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseActivity;
import com.lxkj.xpp.legal.base.BaseMvpPresenter;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.utils.CommonUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 熊萍萍 on 2017/2/23/023.
 */

public class ChangeActivity extends BaseActivity {
    @BindView(R.id.status_bar2)
    public LinearLayout status_bar;
    @BindView(R.id.bar2_iv_left)
    public ImageView image_back;
    @BindView(R.id.bar2_tv_title_center)
    public TextView title;
    @BindView(R.id.bar2_tv_title_right)
    public TextView title_rigiht;
    @BindView(R.id.fragment_change_self_info)
    public EditText change_content;

    @Override
    protected int setContentViewResId() {
        return R.layout.fragment_change_geren;
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
        title_rigiht.setVisibility(View.VISIBLE);
        title_rigiht.setText("确定");
        int flag = getIntent().getExtras().getInt(Constant.appFinal.CHANGE_KEY);
        initlayout(flag);
    }

    /**
     * 根据传过来的标志初始化界面内容
     *
     * @param flag
     */
    private void initlayout(int flag) {
        switch (flag) {
            case Constant.appFinal.CHANGE_CAREER://修改职业
                title.setText("修改职业");
                change_content.setHint(new SpannableString("职业：比如 律师"));
                break;
            case Constant.appFinal.CHANGE_DUTY://修改职务
                title.setText("修改职务");
                change_content.setHint(new SpannableString("职务：比如 经理"));
                break;
            case Constant.appFinal.CHANGE_PHONE://修改绑定号码
                title.setText("修改手机号");
                change_content.setHint(new SpannableString("手机号：比如 15112289987"));
                break;
            case Constant.appFinal.CHANGE_UINIT://修改工作单位
                title.setText("修改工作单位");
                change_content.setHint(new SpannableString("工作单位：比如 律师事务所"));
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.bar2_iv_left, R.id.bar2_tv_title_right})
    void click(View view) {
        switch (view.getId()) {
            case R.id.bar2_iv_left:
                finish();
                break;
            case R.id.bar2_tv_title_right:
                break;
        }
    }
}
