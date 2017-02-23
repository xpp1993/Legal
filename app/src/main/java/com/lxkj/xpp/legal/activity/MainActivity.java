package com.lxkj.xpp.legal.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseActivity;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.fragment.ChatConversationListFragment;
import com.lxkj.xpp.legal.fragment.HomeFragment;
import com.lxkj.xpp.legal.fragment.MeFragment;
import com.lxkj.xpp.legal.mvppresenter.MainPresenter;
import com.lxkj.xpp.legal.mvpview.MainView;

import butterknife.BindView;


public class MainActivity extends BaseActivity<MainPresenter> implements MainView {
    @BindView(R.id.tab_host)
    public RelativeLayout relativeLayout_bottom;
    public Button[] mTabs;
    private long mExitTime = 0;
    private ChatConversationListFragment conversationListFragment;//会话列表界面
    private MeFragment meFragment;//我的页面
    private HomeFragment mainFragment;//主页
    private Fragment[] fragments;
    private int index;

    @Override
    protected void initWidgets(Bundle savedInstanceState) {
        super.initWidgets(savedInstanceState);
        mTabs = new Button[3];
        mTabs[0] = (Button) findViewById(R.id.btn_main);
        mTabs[1] = (Button) findViewById(R.id.btn_message);
        mTabs[2] = (Button) findViewById(R.id.btn_me);
        conversationListFragment = new ChatConversationListFragment();
        mainFragment = new HomeFragment();
        meFragment = new MeFragment();
        fragments = new Fragment[]{mainFragment, conversationListFragment, meFragment};
        mTabs[0].setSelected(true);
        // add and show first fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mainFragment)
                .add(R.id.fragment_container, conversationListFragment)
                .add(R.id.fragment_container, meFragment)
                .hide(conversationListFragment)
                .hide(meFragment)
                .show(mainFragment)
                .commitAllowingStateLoss();
    }

    //
//    @Override
//    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.v("MainAcitity", "onSaveInstanceState" + outState);
        //super.onSaveInstanceState(outState);   //将这一行注释掉，阻止activity保存fragment的状态
    }

    @Override
    protected int setContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter setPresenter() {
        return new MainPresenter(this);
    }

    /**
     * 重写返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (SystemClock.uptimeMillis() - mExitTime > Constant.appFinal.EXIT_TIME_GAP) {
                Toast.makeText(this, "再按一次退出法律", Toast.LENGTH_SHORT).show();
                mExitTime = SystemClock.uptimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * onTabClicked 点击下面的按钮，跳转事件
     */
    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_main:
                index = 0;
                //改变底部的菜单栏
                //1.改变布局的颜色
                relativeLayout_bottom.setBackgroundColor(ContextCompat.getColor(this, R.color.color_transparent));
                // 2.动态设置按钮 的图片
                setDrawable(mTabs[0], R.drawable.zhuye2);
                setDrawable(mTabs[1], R.drawable.liaotian1);
                setDrawable(mTabs[2], R.drawable.wode1);
                //3.动态设置按钮的文字颜色
                setButtonTextColor(mTabs[0], R.color.colorPrimary);
                setButtonTextColor(mTabs[1], R.color.color_white);
                setButtonTextColor(mTabs[2], R.color.color_white);
                break;
            case R.id.btn_message:
                index = 1;
                //改变底部的菜单栏
                //1.改变布局的颜色
                relativeLayout_bottom.setBackgroundColor(ContextCompat.getColor(this, R.color.color_white));
                // 2.动态设置按钮 的图片
                setDrawable(mTabs[0], R.drawable.zhuye3);
                setDrawable(mTabs[1], R.drawable.liaotian2);
                setDrawable(mTabs[2], R.drawable.wode3);
                //3.动态设置按钮的文字颜色
                setButtonTextColor(mTabs[0], R.color.main_bottom_unselect);
                setButtonTextColor(mTabs[1], R.color.colorPrimary);
                setButtonTextColor(mTabs[2], R.color.main_bottom_unselect);
                break;
            case R.id.btn_me:
                index = 2;
                relativeLayout_bottom.setBackgroundColor(ContextCompat.getColor(this, R.color.color_white));
                // 2.动态设置按钮 的图片
                setDrawable(mTabs[0], R.drawable.zhuye3);
                setDrawable(mTabs[1], R.drawable.liaotian3);
                setDrawable(mTabs[2], R.drawable.wode2);
                //3.动态设置按钮的文字颜色
                setButtonTextColor(mTabs[0], R.color.main_bottom_unselect);
                setButtonTextColor(mTabs[1], R.color.main_bottom_unselect);
                setButtonTextColor(mTabs[2], R.color.colorPrimary);
                break;
        }
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            if (i != index) {
                trx.hide(fragments[i]);
            } else {
                trx.show(fragments[i]);
            }
        }
        trx.commitAllowingStateLoss();
    }

    /**
     * 动态设置图片
     */
    private void setDrawable(Button button, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(this, drawableId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//int left, int top, int right, int bottom
        button.setCompoundDrawables(null, drawable, null, null);
    }

    /**
     * 动态设置按钮文字颜色
     */
    private void setButtonTextColor(Button button, int color) {
        button.setTextColor(ContextCompat.getColor(this, color));
    }

}
