package com.lxkj.xpp.legal.fragment;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseFragment;
import com.lxkj.xpp.legal.base.BaseMvpPresenter;

import butterknife.BindView;

/**
 * Created by 熊萍萍 on 2017/2/21/021.
 */

public class ChatConversationListFragment extends BaseFragment {
    @BindView(R.id.chat_status_bar)
    public LinearLayout linear_status;

//    @Override
//    protected void initListener() {
//
//    }

    @Override
    protected BaseMvpPresenter setPresenter() {
        return null;
    }

    @Override
    protected void initWidgets(Bundle savedInstanceState) {
        initState(linear_status);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_conversation_list;
    }
}
