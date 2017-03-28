package com.lxkj.xpp.legal.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.app.MyApplication;
import com.lxkj.xpp.legal.base.BaseFragment;
import com.lxkj.xpp.legal.base.BaseMvpPresenter;
import com.lxkj.xpp.legal.chat.DemoHelper;
import com.lxkj.xpp.legal.constant.ParamterManager;
import com.lxkj.xpp.legal.listener.MyLoctionListener;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.utils.DisplayUtils;
import com.lxkj.xpp.legal.utils.LocationUtils;
import com.lxkj.xpp.legal.widget.QuickFragmentTabHost;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by 熊萍萍 on 2017/2/24/024.
 */

public class MainTabFragment extends BaseFragment implements TabHost.OnTabChangeListener {
    private final String[] TITLES = {"首页", "通讯录", "帖书", "我的"};
    private final String[] TAGS = {"homepage", "address_book", "circle", "me"};
    private int[] ICONS = {R.drawable.tab_homepage, R.drawable.tab_chat, R.drawable.tab_circle, R.drawable.tab_me};
    private final Class[] fragments = {HomeFragment.class, AddressBookFragment.class, CircleFragment.class, MeFragment.class};
    @BindView(android.R.id.tabhost)
    public QuickFragmentTabHost mTabHost;
    private List<ViewHolder> mTabHolder;
    private LocationUtils locationUtils;
    private MyLoctionListener loctionListener = new MyLoctionListener(ParamterManager.LOCATION_TYPE_LOGIN);

    @Override
    protected BaseMvpPresenter setPresenter() {
        return null;
    }

    @Override
    protected void initWidgets() {
        mTabHolder = new ArrayList<>();
        mTabHost.setup(getContext(), getChildFragmentManager(), R.id.real_tabcontent);
        mTabHost.setOnTabChangedListener(this);
        mTabHost.getTabWidget().setDividerDrawable(null);
        EMClient.getInstance().contactManager().setContactListener(new MyContactListener());
        initTabs();
    }

    private void initTabs() {
        for (int i = 0; i < fragments.length; i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.view_tab_icon, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tabTv = (TextView) view.findViewById(R.id.icon_tab_tv);
            viewHolder.redPoint = (ImageView) view.findViewById(R.id.icon_red_point);
            viewHolder.tag = TAGS[i];
            mTabHolder.add(viewHolder);
            // 动态设置TextView 的图片
            Drawable drawable = ContextCompat.getDrawable(getContext(), ICONS[i]);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//int left, int top, int right, int bottom
            viewHolder.tabTv.setCompoundDrawables(null, drawable, null, null);
            viewHolder.tabTv.setText(TITLES[i]);
            viewHolder.tabTv.setTextSize(DisplayUtils.px2sp(CommonUtils.getContext(), 20));
            mTabHost.addTab(mTabHost.newTabSpec(viewHolder.tag).setIndicator(view), fragments[i], null);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //停止定位
        locationUtils.unregisterListener(loctionListener);
        locationUtils.stop();
        locationUtils.onDestroy();
    }

    @Override
    protected void initData() {
        /**
         * 开始定位
         */
        startLocation();
    }

    private void startLocation() {
        locationUtils = ((MyApplication) CommonUtils.getContext()).locationUtils;
        //注册监听
        locationUtils.registerListener(loctionListener);
        //开始定位
        locationUtils.start();
    }

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_main_tab;
    }

    @Override
    public void onTabChanged(String tabId) {
        if (!tabId.equals("me")) {
            StatusBarCompat.setStatusBarColor(getActivity(), ContextCompat.getColor(CommonUtils.getContext(), R.color.colorPrimaryDark));
        } else {
            StatusBarCompat.translucentStatusBar(getActivity());
        }
        Log.e("tabHost", tabId);
    }

    @Override
    public void onShowToast(String message, int i) {

    }

    @Override
    public void navigateToFragment(BaseFragment baseFragment, Bundle bundle) {

    }

    private class ViewHolder {
        TextView tabTv;
        ImageView redPoint;
        String tag;
    }

    @Override
    public boolean finish() {
        return false;
    }

    /**
     * 监听好友状态事件
     */
    public class MyContactListener implements EMContactListener {

        @Override
        public void onContactAdded(String s) {
            //增加了联系人时回调此方法
        }

        @Override
        public void onContactDeleted(String s) {
            //被删除时回调此方法，更新联系人列表
        }

        @Override
        public void onContactInvited(String s, String s1) {
            Log.e("MainTabFragment", s + "," + s1);
            //收到好友邀请,点击接受、或者拒绝，测试直接能接受
            try {
                EMClient.getInstance().contactManager().acceptInvitation(s);
                Log.e("MainTabFragment", "同意添加好友！");
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFriendRequestAccepted(String s) {
            //好友请求被同意,从服务器获取好友列表,并保存到本地数据库
            Log.e("MainTabFragment", "好友请求被接受！");
            DemoHelper.getInstance().asyncFetchContactsFromServer(null);
        }

        @Override
        public void onFriendRequestDeclined(String s) {
            //好友请求被拒绝
        }
    }

}