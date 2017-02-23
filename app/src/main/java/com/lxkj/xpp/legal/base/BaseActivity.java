package com.lxkj.xpp.legal.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.lxkj.xpp.legal.utils.ActivityCollector;
import com.lxkj.xpp.legal.utils.CommonUtils;

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
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        if (mPresenter != null) {
            mPresenter.detach();
        }
        ActivityCollector.removeActivity(this);
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

    /**
     * 动态的设置状态栏  实现沉浸式状态栏
     */
    public void initState(LinearLayout linear_bar) {

        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            linear_bar.setVisibility(View.VISIBLE);
            //获取到状态栏的高度
            int statusHeight = CommonUtils.getStatusBarHeight();
            //动态的设置隐藏布局的高度
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linear_bar.getLayoutParams();
            params.height = statusHeight;
            linear_bar.setLayoutParams(params);
        }
    }
}