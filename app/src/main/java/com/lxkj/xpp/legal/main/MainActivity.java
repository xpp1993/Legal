package com.lxkj.xpp.legal.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.activity.ChatActivity;
import com.lxkj.xpp.legal.base.BaseActivity;
import com.lxkj.xpp.legal.bean.VersionBean;
import com.lxkj.xpp.legal.homepage.HomeFragment;
import com.lxkj.xpp.legal.main.mvpview.MainView;
import com.lxkj.xpp.legal.main.presenter.MainPresenter;
import com.lxkj.xpp.legal.my.MeFragment;
import com.lxkj.xpp.legal.net.DecodeManager;
import com.lxkj.xpp.legal.net.RequestManager;
import com.lxkj.xpp.legal.net.callback.ReqProgressCallBack;
import com.lxkj.xpp.legal.utils.CommonUtils;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;


public class MainActivity extends BaseActivity<MainPresenter> implements MainView, ReqProgressCallBack<Object> {
    @BindView(R.id.unread_msg_number)
    public TextView unreadLablel;
    public Button[] mTabs;
    private EaseConversationListFragment conversationListFragment;//会话列表页面
    private MeFragment meFragment;//我的页面
    private HomeFragment mainFragment;//主页
    private Fragment[] fragments;
    private int index;
    private int currtentTabIndex;
    public static final int recodeCode_version = 0x01;
    private int vesionCode;
    private Handler handler = new MyHandler();
    private File file;
    private ProgressDialog m_progressDlg;//更新软件进度条

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                case 1:
                    VersionBean versionBean = (VersionBean) msg.obj;
                    if (versionBean.getVersions().equals("1")) {
                        m_progressDlg.show();
                        //下载文件
                        file = new File(Environment.getExternalStorageDirectory(), "dexin.love.band.apk");
                        RequestManager.getInstance(CommonUtils.getContext()).downLoadFile(versionBean.getUrl(), file, MainActivity.this, 2);
                    }
                    break;
            }
        }
    }

    @Override
    protected void initWidgets(Bundle savedInstanceState) {
        super.initWidgets(savedInstanceState);
        mTabs = new Button[3];
        mTabs[0] = (Button) findViewById(R.id.btn_main);
        mTabs[1] = (Button) findViewById(R.id.btn_message);
        mTabs[2] = (Button) findViewById(R.id.btn_me);
        conversationListFragment = new EaseConversationListFragment();
        mainFragment = new HomeFragment();
        meFragment = new MeFragment();
        fragments = new Fragment[]{mainFragment, conversationListFragment, meFragment};
        vesionCode = CommonUtils.getVerCode();
        m_progressDlg = new ProgressDialog(this);
        m_progressDlg.setTitle("请稍后...");
        m_progressDlg.setMessage("正在下载数据，请稍等...");
        m_progressDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确
        m_progressDlg.setIndeterminate(false);
        m_progressDlg.setCancelable(false);
        m_progressDlg.setMax(100);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void initListener() {
        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                startActivity(new Intent(MainActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.getUserName()));
            }
        });
    }

    @Override
    protected void initData() {
        mTabs[0].setSelected(true);
        // unreadLablel.setVisibility(View.VISIBLE);
        // add and show first fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mainFragment)
                .add(R.id.fragment_container, conversationListFragment)
                .add(R.id.fragment_container, meFragment)
                .hide(conversationListFragment)
                .hide(meFragment)
                .show(mainFragment)
                .commit();
        //获取apk版本
//       HashMap<String, String> params = CommonUtils.getParameterMap(new String[]{"mobile", "password"}, "17186172024", "xpp");
//        RequestManager.getInstance(CommonUtils.getContext()).requestAysn("cuffapi/user/sign_up_submint", 1, 1, params, this);
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
     * onTabClicked 点击下面的按钮，跳转事件
     */
    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_main:
                index = 0;
                break;
            case R.id.btn_message:
                index = 1;
                break;
            case R.id.btn_me:
                index = 2;
                break;
        }
//        if (currtentTabIndex != index) {//如果当前的索引不等于index
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            for(int i=0;i<fragments.length;i++){
                if(i!=index){
                    mTabs[i].setSelected(false);
                    trx.hide(fragments[i]);
                }else{
                    mTabs[i].setSelected(true);
                    trx.show(fragments[i]);
                }
            }
        trx.commitAllowingStateLoss();
//            trx.hide(fragments[currtentTabIndex]);//隐藏当前的
            //显示
//            if (!fragments[index].isAdded()) {
//                trx.add(R.id.fragment_container, fragments[index]);
//            }
//            trx.show(fragments[index]).commit();

//        }
//        mTabs[currtentTabIndex].setSelected(false);
//        mTabs[index].setSelected(true);
        currtentTabIndex = index;
    }

    @Override
    public void onReqSuccess(int requestCode, Object result) {
        switch (requestCode) {
            case 1:
                DecodeManager.decodeLogin((String) result, requestCode, handler);
                break;
            case 2:
                //安装文件
                CommonUtils.openFile(file);
                break;
        }
    }

    @Override
    public void onReqFailed(int requestCode, String errorMsg) {

    }

    @Override
    public void onProgress(int requestCode,long total, long current) {
        switch (requestCode){
            case 2:
                int a = (int) Math.floor((current * 1.0 / total * 100));
                m_progressDlg.setProgress(a);
                if (total == current) {
                    m_progressDlg.dismiss();
                }
                break;
        }
    }
}
