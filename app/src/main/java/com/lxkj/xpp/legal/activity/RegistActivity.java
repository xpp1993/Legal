package com.lxkj.xpp.legal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.lxkj.xpp.legal.base.BaseActivity;
import com.lxkj.xpp.legal.base.BaseMvpPresenter;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.utils.CommonUtils;

import butterknife.BindView;

/**
 * Created by 熊萍萍 on 2017/1/5/005.
 * 注册
 */

public class RegistActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.status_bar2)
    public LinearLayout regist_status;
    @BindView(R.id.bar2_iv_left)
    public ImageView image_back;
    @BindView(R.id.bar2_tv_title_center)
    public TextView bar_title;
    private Button ok_button;//确认对话框按钮
    private Button cancel_button;//取消对话框按钮
    private NiftyDialogBuilder dialog;

    @Override
    protected void initWidgets(Bundle savedInstanceState) {
        super.initWidgets(savedInstanceState);
        //改变状态栏的颜色为白色
        initState(regist_status);
        /**
         * 白底黑字，改变状态栏图标为深色
         */
        CommonUtils.setMystatusBar(this);
        bar_title.setText("注册");
        image_back.setVisibility(View.VISIBLE);
        image_back.setOnClickListener(this);
        dialog = NiftyDialogBuilder.getInstance(this);
        showDialog();
    }

    /**
     * 弹出是否实名注册的对话框
     */
    private void showDialog() {
        //获得dialog的view
        LayoutInflater inflater = LayoutInflater.from(this);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.dialog_regist1, null);
        ok_button = (Button) layout.findViewById(R.id.regist_dialog_ok);
        ok_button.setOnClickListener(this);
        cancel_button = (Button) layout.findViewById(R.id.regist_dialog_cencel);
        cancel_button.setOnClickListener(this);
        dialog.withTitle("是否实名验证")
                .withTitleColor(ContextCompat.getColor(this, R.color.color_white))
                .withDividerColor(ContextCompat.getColor(this, R.color.color_white))
                .withMessage(null)
                .isCancelableOnTouchOutside(false)
                .withDuration(1000)
                .withEffect(Effectstype.Fliph)
                .withDialogColor(ContextCompat.getColor(this, R.color.bar2))
                .setCustomView(layout, this)
                .show();
    }

    @Override
    protected int setContentViewResId() {
        return R.layout.activity_register;
    }

    @Override
    protected BaseMvpPresenter setPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar2_iv_left:
                finish();
                break;
            case R.id.regist_dialog_cencel:
                dialog.dismiss();
                break;
            case R.id.regist_dialog_ok:
                dialog.dismiss();
                //跳转到实名注册页面
                Intent intent = new Intent(RegistActivity.this, IDRegistActivity.class);
                startActivity(intent);
                break;
        }
    }
}