package com.lxkj.xpp.legal.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.activity.LoginActivity;
import com.lxkj.xpp.legal.base.BaseFragment;
import com.lxkj.xpp.legal.base.BaseMvpPresenter;
import com.lxkj.xpp.legal.my.mvpview.IView;
import com.lxkj.xpp.legal.my.presenter.IPresenter;

import butterknife.BindView;

/**
 * Created by 熊萍萍 on 2016/12/22/022.
 */

public class MeFragment extends BaseFragment<IPresenter> implements IView, View.OnClickListener {
    @BindView(R.id.easeTitleBar1)
    public EaseTitleBar easeTitleBar;
    @BindView(R.id.btn_logout)
    public Button logoutButton;

    @Override
    protected IPresenter setPresenter() {
        return new IPresenter(this);
    }

    @Override
    protected void initWidgets(Bundle savedInstanceState) {
        easeTitleBar.setTitle("我的");
    }

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initListener() {
        logoutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout:
                EMClient.getInstance().logout(false, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        getActivity().finish();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    }

                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
                break;
        }
    }
}
