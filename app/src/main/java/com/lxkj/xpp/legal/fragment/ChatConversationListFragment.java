package com.lxkj.xpp.legal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.activity.ECChatActivity;
import com.lxkj.xpp.legal.base.BaseFragment;
import com.lxkj.xpp.legal.base.BaseMvpPresenter;
import com.lxkj.xpp.legal.chat.AddFriendsFragment;
import com.lxkj.xpp.legal.event.NavFragmentEvent;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.utils.ToastUtlis;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 熊萍萍 on 2017/2/21/021.
 * 聊天页面
 */

public class ChatConversationListFragment extends BaseFragment implements TextWatcher {
    @BindView(R.id.conversation_query)
    EditText query;
    @BindView(R.id.conversation_search_clear)
    ImageButton query_clear;
    // 发起聊天 username 输入框
    @BindView(R.id.ec_edit_chat_id)
    EditText mChatIdEdit;
    // 发起聊天
    @BindView(R.id.ec_btn_start_chat)
    Button mStartChatBtn;
    // 退出登录
    @BindView(R.id.ec_btn_sign_out)
    Button mSignOutBtn;

    @Override
    protected BaseMvpPresenter setPresenter() {
        return null;
    }

    @Override
    protected void initWidgets() {
//        query.addTextChangedListener(this);
//        query_clear.setOnClickListener(this);
    }

    @OnClick({R.id.ec_btn_start_chat, R.id.ec_btn_sign_out})
    void click(View view) {
        switch (view.getId()) {
            case R.id.ec_btn_start_chat://发起聊天
                // 获取我们发起聊天的者的username
                String chatId = mChatIdEdit.getText().toString().trim();
                if (!TextUtils.isEmpty(chatId)) {
                    // 获取当前登录用户的 username
                    String currUsername = EMClient.getInstance().getCurrentUser();
                    if (chatId.equals(currUsername)) {
                        ToastUtlis.showToastInUIThread("不能和自己聊天");
                        return;
                    }
                    // 跳转到聊天界面，开始聊天
                    Intent intent = new Intent(getActivity(), ECChatActivity.class);
                    // EaseUI封装的聊天界面需要这两个参数，聊天者的username，以及聊天类型，单聊还是群聊
                    intent.putExtra("userId", chatId);
                    intent.putExtra("chatType", EMMessage.ChatType.Chat);
                    startActivity(intent);
                } else {
                    ToastUtlis.showToastInUIThread("Username 不能为空");
                }
                break;
            case R.id.ec_btn_sign_out://退出登录
                signOut();
                break;
//            case R.id.bar_image_right://弹出popwindow
//                showPopWindow(view);
//                break;
        }
    }

    private void showPopWindow(View view) {

        View contentView = LayoutInflater.from(CommonUtils.getContext()).inflate(R.layout.pop_dialog, null);
        final PopupWindow popupWindow = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(false);//点击外面消失
        popupWindow.showAsDropDown(view, 100, 20);
        contentView.findViewById(R.id.add_group_friend).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null)
                            popupWindow.dismiss();
                        //跳转到添加好友/群页面
                        EventBus.getDefault().post(new NavFragmentEvent(new AddFriendsFragment()));
                    }
                });

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initData() {

    }


    @Override
    protected int setContentViewId() {
        return R.layout.fragment_conversation_list;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //过滤操作
        if (s.length() > 0) {
            query_clear.setVisibility(View.VISIBLE);
        } else {
            query_clear.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

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
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.conversation_search_clear://清空文本框
//                query.getText().clear();
//                break;
//        }
//    }

    @Override
    public void onShowToast(String message, int i) {

    }

    @Override
    public void navigateToFragment(BaseFragment baseFragment, Bundle bundle) {

    }


}
