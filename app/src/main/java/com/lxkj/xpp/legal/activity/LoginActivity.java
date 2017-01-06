package com.lxkj.xpp.legal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.main.MainActivity;

/**
 * Created by 熊萍萍 on 2016/12/22/022.
 */

public class LoginActivity extends EaseBaseActivity {
    private EditText usernameView;
    private EditText pwdView;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
//        if (EMClient.getInstance().isLoggedInBefore()) {
//            //如果之前登过陆，直接进入MainActivity
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//        }
        setContentView(R.layout.activity_login);
        usernameView = (EditText) findViewById(R.id.et_username);
        pwdView = (EditText) findViewById(R.id.et_password);
        Button loginButton = (Button) findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //login
                EMClient.getInstance().login(usernameView.getText().toString(), pwdView.getText().toString(), new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onError(int i, String s) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "login failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            }
        });
        Button registButton = (Button) findViewById(R.id.btn_regist);
        registButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }
}
