package com.lxkj.xpp.legal.listener;

import com.lxkj.xpp.legal.adapter.ItemViewHolder;
import com.lxkj.xpp.legal.model.bean.CircleListBean;
import com.lxkj.xpp.legal.model.bean.CommentsBean;

/**
 * Created by 熊萍萍 on 2017/3/27/027.
 * 评论或者回复的回调接口
 */

public interface IssueOrReplyDo {
    /**
     *
     * @param viewHolder
     * @param type
     * @param acticleId
     * @param commentsBean
     */
    void issueorReply(ItemViewHolder viewHolder, int type, int acticleId, CommentsBean commentsBean);
}
