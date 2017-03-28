package com.lxkj.xpp.legal.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseActivity;
import com.lxkj.xpp.legal.base.BaseFragment;
import com.lxkj.xpp.legal.event.NavFragmentEvent;
import com.lxkj.xpp.legal.fragment.FeedBackFragment;
import com.lxkj.xpp.legal.fragment.FragmentSeniority;
import com.lxkj.xpp.legal.fragment.IDRegistFragment;
import com.lxkj.xpp.legal.fragment.LoginFragment;
import com.lxkj.xpp.legal.fragment.MainTabFragment;
import com.lxkj.xpp.legal.fragment.MeFragment;
import com.lxkj.xpp.legal.fragment.SelfFragment;
import com.lxkj.xpp.legal.fragment.ShezhiFragment;
import com.lxkj.xpp.legal.presenter.MainPresenter;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.view.MainView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.LinkedList;

import qiu.niorgai.StatusBarCompat;


public class MainActivity extends BaseActivity<MainPresenter> implements MainView {
    private FragmentManager fm;
    private LinkedList<String> mFragments = new LinkedList<String>();

    public static final int LAST_CLICK_GAP = 600;

    public long lastClickTime = 0;

    private long mExitTime = 0;

    public static final int EXIT_TIME_GAP = 2000;

    @Override
    protected void initWidgets(Bundle savedInstanceState) {
        //super.initWidgets(savedInstanceState);
        EventBus.getDefault().register(this);
        fm = getSupportFragmentManager();
        BaseFragment baseFragment;
        String tag;
//        if (AppUtils.isFirstLogin()) {
//            baseFragment = new WelcomeFragment();
//            tag = baseFragment.getMTag();
//            AppUtils.firstLogin();
//        } else {
//            baseFragment = new SplashFragment();
//            tag = baseFragment.getMTag();
//        }
        baseFragment = new LoginFragment();
        //  baseFragment = new MainTabFragment();
        tag = baseFragment.getMTag();
        mFragments.add(tag);
        fm.beginTransaction().add(R.id.main_container, baseFragment, tag).addToBackStack(tag).commitAllowingStateLoss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 防止fragment重叠
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //如果用以下这种做法则不保存状态，再次进来的话会显示默认tab
        //总是执行这句代码来调用父类去保存视图层的状态
        //super.onSaveInstanceState(outState);
    }

    @Override
    protected int setContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter setPresenter() {
        return new MainPresenter(this);
    }

    //监听返回键
    //按返回键，Fragment要不要禁用返回键
    //假如Fragment不控制返回键--->MainActivity 管理东西---->mFragments.pollLast();fm.popBackStack
    //                                                      只有一个Fragment情况下，
    //                                                      MainActivty.finish();
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {// 返回键处理
            if (!backCurrentFragment()) {
                goBack();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 处理默认返回的
    private void goBack() {
        if (getCurrentFrament() instanceof ShezhiFragment) {
            Log.e("MainActivity", "返回键退回到我的页面");
            StatusBarCompat.translucentStatusBar(this);
        } else if (getCurrentFrament() instanceof SelfFragment) {
            Log.e("MainActivity", "返回键退回到我的页面");
            StatusBarCompat.translucentStatusBar(this);
        } else if (getCurrentFrament() instanceof FeedBackFragment) {
            Log.e("MainActivity", "返回键退回到我的页面");
            StatusBarCompat.translucentStatusBar(this);
        } else if (getCurrentFrament() instanceof IDRegistFragment) {
            Log.e("MainActivity", "返回键退回到我的页面");
            StatusBarCompat.translucentStatusBar(this);
        } else if (getCurrentFrament() instanceof FragmentSeniority) {
            Log.e("MainActivity", "返回键退回到我的页面");
            StatusBarCompat.translucentStatusBar(this);
        }
        int count = fm.getBackStackEntryCount();
        if (count == 1) {
            // 只存在一个Fragment时候，进行二次提醒
            if (SystemClock.uptimeMillis() - mExitTime > EXIT_TIME_GAP) {
                Toast.makeText(this, "再按一次，退出本程序", Toast.LENGTH_SHORT).show();
                mExitTime = SystemClock.uptimeMillis();
            } else {
                // 假如多个Activity，清除多个Activity
                MainActivity.this.finish();
            }
        } else {
            if (mFragments.size() > 0) {
                mFragments.pollLast();
            }
            fm.popBackStack();// 把Framgent移除出去
        }
    }

    //Fragment控制返回键
    public boolean backCurrentFragment() {
        BaseFragment currFragment = getCurrentFrament();
        if (currFragment != null) {
            return currFragment.onBack();
        }
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(NavFragmentEvent event) {
        BaseFragment fragment = event.fragment;
        Bundle bundle = event.bundle;
        startFragment(fragment, bundle);
    }

    public void startFragment(BaseFragment fragment, Bundle bundle) {
        if (fragment == null) {
            throw new IllegalArgumentException("fragment is null");
        }
        if (lastClickTime + LAST_CLICK_GAP < SystemClock.uptimeMillis()) {
            // 1 获取tag
            String tag = fragment.getMTag();
            // 2 获取事务
            FragmentTransaction ft = fm.beginTransaction();
            // 3 控制Fragment 的动画
//          ft.setCustomAnimations(R.anim.slide_left_enter, 0, 0,R.anim.slide_right_exit);
            // 4 添加Fragment
            ft.add(R.id.main_container, fragment, tag);
            if (bundle != null) {
                fragment.setArguments(bundle);
            }
            // 5 隐藏当前或者finish的Fragment
            BaseFragment currFragment = getCurrentFrament();
            if (currFragment != null) {
                if (currFragment.finish()) {
                    mFragments.pollLast();
                   // fm.popBackStack();//finish
                    fm.popBackStackImmediate();
//                    //由于当前的Fragment，被弹出去，需要当前的Fragment已经变化角色，需要重新隐藏
                    currFragment = (BaseFragment) getCurrentFrament();
                    if (currFragment != null) {
                        ft.hide(currFragment);
                    }
                } else {
                    ft.hide(currFragment);// hide
                }
            }
            // 6 把tag 添加到mFragments
            mFragments.add(tag);
            // 7 添加到返回栈
            ft.addToBackStack(tag);
            // 8 添加事务
            ft.commit();
            lastClickTime = SystemClock.uptimeMillis();
        }
    }

    // 怎么获取当前的Fragment
    public BaseFragment getCurrentFrament() {
        return mFragments.size() > 0 ? (BaseFragment) fm.findFragmentByTag(mFragments.peekLast()) : null;
    }

    @Override
    public void onShowToast(String message, int i) {

    }

    @Override
    public void navigateToFragment(BaseFragment baseFragment, Bundle bundle) {

    }
}
