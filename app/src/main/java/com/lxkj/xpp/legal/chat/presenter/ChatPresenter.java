package com.lxkj.xpp.legal.chat.presenter;

import com.lxkj.xpp.legal.base.BaseMvpPresenter;
import com.lxkj.xpp.legal.chat.model.ChatModel;
import com.lxkj.xpp.legal.chat.mvpview.ChatView;

/**
 * Created by 熊萍萍 on 2016/12/23/023.
 */

public class ChatPresenter extends BaseMvpPresenter<ChatView,ChatModel> {
    /**
     * 在构造方法里关联view设置model
     *
     * @param mView
     */
    public ChatPresenter(ChatView mView) {
        super(mView);
    }

    @Override
    protected ChatModel setModel() {
        return new ChatModel();
    }
}
