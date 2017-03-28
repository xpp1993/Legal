package com.lxkj.xpp.legal.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 熊萍萍 on 2016/12/23/023.
 * activity 基类
 */

public abstract class BaseActivity<P extends BaseMvpPresenter> extends AppCompatActivity implements BaseMvpView {
    private Unbinder mUnbinder;
    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentViewResId());
        mUnbinder = ButterKnife.bind(this);
        mPresenter = setPresenter();
        initWidgets(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        if (mPresenter != null) {
            mPresenter.detach();
        }
        //退出登录
        signOut();
    }

    /**
     * 退出登录
     */
    private void signOut() {
        // 调用sdk的退出登录方法，第一个参数表示是否解绑推送的token，没有使用推送或者被踢都要传false
        EMClient.getInstance().logout(false, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.i("lzan13", "logout success");
                // 调用退出成功，结束app
                //finish();
            }

            @Override
            public void onError(int i, String s) {
                Log.i("lzan13", "logout error " + i + " - " + s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    /**
     * 获取activity的布局资源
     */
    protected abstract int setContentViewResId();

    /**
     * 设置presenter层对象
     *
     * @return presenter层对象
     */
    protected abstract P setPresenter();

    /**
     * 初始化工作
     *
     * @param savedInstanceState
     */
    protected void initWidgets(Bundle savedInstanceState) {
    }

}