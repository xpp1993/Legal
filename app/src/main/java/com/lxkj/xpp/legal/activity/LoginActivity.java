package com.lxkj.xpp.legal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseActivity;
import com.lxkj.xpp.legal.base.BaseMvpPresenter;
import com.lxkj.xpp.legal.utils.CommonUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 熊萍萍 on 2016/12/22/022.
 * 登录页面
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.status_bar2)
    public LinearLayout login_status;
    @BindView(R.id.bar2_tv_title_center)
    public TextView title_center;
    @BindView(R.id.bar2_tv_title_right)
    public TextView title_right;
    @BindView(R.id.login_phone_tv)
    public TextView fast_login;
    @BindView(R.id.login_pw_tv)
    public TextView pw_tv;
    @BindView(R.id.login_btn)
    public Button login_btn;
    @BindView(R.id.login_phone_et)
    public EditText login_username_et;
    @BindView(R.id.login_password_et)
    public EditText login_password_et;

    @Override
    protected int setContentViewResId() {
        return R.layout.activity_login;
    }

    @Override
    protected BaseMvpPresenter setPresenter() {
        return null;
    }

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
        initState(login_status);
        title_center.setText("登录");
        title_right.setVisibility(View.VISIBLE);
        title_right.setText("注册");
    }


    @OnClick({R.id.login_phone_tv, R.id.login_pw_tv, R.id.bar2_tv_title_right, R.id.login_btn})
    void click(View v) {
        switch (v.getId()) {
            case R.id.login_phone_tv://跳转手机快捷登陆界面
                Intent intent_fast = new Intent(this, FastActivity.class);
                startActivity(intent_fast);
                break;
            case R.id.login_pw_tv://跳转到忘记密码页面
                Intent intent_forget = new Intent(this, ForgetPassWordActivity.class);
                startActivity(intent_forget);
                break;
            case R.id.bar2_tv_title_right://跳转到注册页面
                Intent intent_regist = new Intent(this, RegistActivity.class);
                startActivity(intent_regist);
                break;
            case R.id.login_btn://登录
                String username = login_username_et.getText().toString().trim();
                String password = login_password_et.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(LoginActivity.this, "输入的用户名不能为空！", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "输入的密码不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    //登录操作，联网
                    Intent intent_main = new Intent(this, MainActivity.class);
                    startActivity(intent_main);
                    finish();
                }
                break;
            default:
                break;
        }
    }
}