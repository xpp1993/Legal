package com.lxkj.xpp.legal.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.exceptions.HyphenateException;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.chat.ChatFragment;

public class ECChatActivity extends AppCompatActivity implements EMContactListener {

    private EaseChatFragment chatFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // 这里直接使用EaseUI封装好的聊天界面
        chatFragment = new ChatFragment();
        // 将参数传递给聊天界面
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.ec_layout_container, chatFragment).commit();
        //监听好友请求状态
        EMClient.getInstance().contactManager().setContactListener(this);
    }

    @Override
    public void onContactAdded(String s) {
        Log.e("ECChatActivity", "onContactAdded:" + s);
    }

    @Override
    public void onContactDeleted(String s) {
        Log.e("ECChatActivity", "onContactAdded:" + s);
    }

    @Override
    public void onContactInvited(String s, String s1) {
        Log.e("ECChatActivity", "onContactInvited:" + s + "," + s1);
    }

    @Override
    public void onFriendRequestAccepted(String s) {
        Log.e("ECChatActivity", "onFriendRequestAccepted" + s);
        try {
            EMClient.getInstance().contactManager().acceptInvitation(s);
        } catch (HyphenateException e) {
            e.printStackTrace();
            Log.e("ECChatActivity", "onFriendRequestAccepted" + e.getMessage());
        }
    }

    @Override
    public void onFriendRequestDeclined(String s) {
        Log.e("ECChatActivity", "onFriendRequestDeclined" + s);

    }
}
