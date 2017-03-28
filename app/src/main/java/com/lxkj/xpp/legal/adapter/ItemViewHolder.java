package com.lxkj.xpp.legal.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.widget.MessageGroup;
import com.lxkj.xpp.legal.widget.adaptivedgridview.MyGridlayout;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 熊萍萍 on 2017/3/27/027.
 */


public class ItemViewHolder extends RecyclerView.ViewHolder {
    public CircleImageView headCircleImageView;
    public TextView nicknameTextView;
    public TextView whatAgoTextView;
    public TextView messageTextView;
    public MyGridlayout imageGridView;
    public TextView commentCountTextView;
    public ImageView commentImageView;
    public MessageGroup messageGroup;

    public ItemViewHolder(View itemView) {
        super(itemView);
        headCircleImageView = (CircleImageView) itemView.findViewById(R.id.item_head);
        nicknameTextView = (TextView) itemView.findViewById(R.id.item_nickname);
        whatAgoTextView = (TextView) itemView.findViewById(R.id.item_what_ago);
        messageTextView = (TextView) itemView.findViewById(R.id.item_message);
        imageGridView = (MyGridlayout) itemView.findViewById(R.id.item_image_gv);
        commentCountTextView = (TextView) itemView.findViewById(R.id.item_comment_count);
        commentImageView = (ImageView) itemView.findViewById(R.id.item_comment_iv);
        messageGroup = (MessageGroup) itemView.findViewById(R.id.item_mg);
    }
}