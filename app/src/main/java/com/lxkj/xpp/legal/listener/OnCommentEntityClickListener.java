package com.lxkj.xpp.legal.listener;

import com.lxkj.xpp.legal.model.bean.CommentsBean;

public interface OnCommentEntityClickListener {
    /**
     * 发信人text点击回调
     *
     * @param commentsBean
     */
    void onFromClicked(CommentsBean commentsBean);

    /**
     * 收信人text点击回调
     *
     * @param commentsBean
     */
    void onTargetClicked(CommentsBean commentsBean);

    /**
     * 消息点击回调
     *
     * @param commentsBean
     */
    void onMessageClicked(CommentsBean commentsBean);

    /**
     * 消息长按回调
     *
     * @param commentsBean
     */
    void onMessageLongClicked(CommentsBean commentsBean);
}