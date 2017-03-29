package com.lxkj.xpp.legal.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.listener.IssueOrReplyDo;
import com.lxkj.xpp.legal.listener.OnCommentEntityClickListener;
import com.lxkj.xpp.legal.model.bean.CircleListBean;
import com.lxkj.xpp.legal.model.bean.CommentsBean;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.widget.adaptivedgridview.MyGridLayoutCallBack;
import com.lxkj.xpp.legal.widget.adaptivedgridview.SmartDecorator;


import java.util.Arrays;
import java.util.List;

/**
 * Created by 熊萍萍 on 2017/3/22/022.
 */

public class CircleListItemAdapter extends RecyclerBaseAdapter<CircleListBean.DataBean.DataListBean, ItemViewHolder> {
    private Context context;
    private IssueOrReplyDo issueOrReplyDo;
    private ItemViewHolder viewHolder;

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflateItemView(parent, R.layout.comment_item);
        return new ItemViewHolder(itemView);
    }

    public CircleListItemAdapter(Context context, IssueOrReplyDo issueOrReplyDo) {
        this.context = context;
        this.issueOrReplyDo = issueOrReplyDo;
    }

    @Override
    public void addItems(List<CircleListBean.DataBean.DataListBean> items) {
        super.addItems(items);
    }

    public ItemViewHolder getViewHolder() {
        return viewHolder;
    }

    @Override
    protected void bindDataToItemView(final ItemViewHolder holder, final CircleListBean.DataBean.DataListBean item) {
        viewHolder = holder;
        Glide.with(context).load(item.getUserHeadImg()).into(holder.headCircleImageView);
        holder.nicknameTextView.setText(item.getUserNickName());
        holder.whatAgoTextView.setText(item.getPublishTime());
        holder.messageTextView.setText(item.getContent());
        if (item.getImg() != null && item.getImg().size() > 0) {
            holder.imageGridView.setMyGridLayoutCallBack(new MyGridLayoutCallBack() {
                @Override
                public void onClick(Object res) {
                    Toast.makeText(context, "image onClick on ", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onLongClick(Object res) {
                    Toast.makeText(context, "image onLongClick on ", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void inflateItem(ImageView imageView, Object res) {
                    Glide.with(context).load(res.toString()).thumbnail(0.1f).into(imageView);
                }
            });
            holder.imageGridView.setVisibility(View.VISIBLE);
            holder.imageGridView.setMaxItemCount(3);//手动设置最多可以显示多少张图片
            holder.imageGridView.setColumnNumber(3);
            holder.imageGridView.setDecorator(new SmartDecorator());
            holder.imageGridView.refresh(Arrays.asList(item.getImg().toArray()));
        } else {
            holder.imageGridView.setVisibility(View.GONE);
        }
        holder.commentCountTextView.setText(item.getCommentNum() + " 评论");
        holder.commentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//发表评论
                if (issueOrReplyDo != null) {
                    issueOrReplyDo.issueorReply(holder, Constant.appFinal.issue, item.getObjId(), null);
                }
            }
        });
        if (item.getComments() != null && item.getComments().size() > 0 || item == null) {
            holder.messageGroup.setOnEntityClickListener(new OnCommentEntityClickListener() {

                @Override
                public void onFromClicked(CommentsBean commentsBean) {
                    Toast.makeText(context, "onFromClicked:" + commentsBean, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onTargetClicked(CommentsBean commentsBean) {
                    Toast.makeText(context, "onTargetClicked:" + commentsBean, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onMessageClicked(CommentsBean commentsBean) {//回复评论
                    if (!commentsBean.getDiscussUid().equals(CommonUtils.getPreference().getString(context, Constant.LOGIN.uid))) {
                        if (issueOrReplyDo != null) {
                            issueOrReplyDo.issueorReply(holder, Constant.appFinal.replay, item.getObjId(), commentsBean);
                        }
                    } else {
                        if (issueOrReplyDo != null) {//删除评论
                            issueOrReplyDo.deleteCommentDo(item.getObjId(), commentsBean.getObjId(), commentsBean.getContent().trim());
                        }
                    }

                }

                @Override
                public void onMessageLongClicked(CommentsBean commentsBean) {
                    String uid = CommonUtils.getPreference().getString(context, Constant.LOGIN.uid);
                    if (commentsBean.getDiscussUid().equals(uid) || item.getUid().equals(uid))//长按删除自己的评论、或者是自己说说里的说有评论
                        if (issueOrReplyDo != null) {//删除评论
                            issueOrReplyDo.deleteCommentDo(item.getObjId(), commentsBean.getObjId(), commentsBean.getContent().trim());
                        }
                }
            });
            holder.messageGroup.setVisibility(View.VISIBLE);
            holder.messageGroup.setMaxLineNumber(10);//手动设置最多可以显示多少条评论
            holder.messageGroup.refresh(item.getComments());
        } else {
            holder.messageGroup.setVisibility(View.GONE);
        }
    }

}
