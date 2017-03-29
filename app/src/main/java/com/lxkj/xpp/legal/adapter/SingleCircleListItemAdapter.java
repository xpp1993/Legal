package com.lxkj.xpp.legal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.model.bean.CircleListBean;
import com.lxkj.xpp.legal.widget.adaptivedgridview.MyGridLayoutCallBack;
import com.lxkj.xpp.legal.widget.adaptivedgridview.MyGridlayout;
import com.lxkj.xpp.legal.widget.adaptivedgridview.SmartDecorator;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 熊萍萍 on 2017/3/22/022.
 * 单个用户帖书列表的Adapter
 */

public class SingleCircleListItemAdapter extends RecyclerBaseAdapter<CircleListBean.DataBean.DataListBean, SingleCircleListItemAdapter.SingleItemViewHolder> {
    private Context context;

    @Override
    public SingleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflateItemView(parent, R.layout.single_circle_item);
        return new SingleItemViewHolder(itemView);
    }

    public SingleCircleListItemAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void addItems(List<CircleListBean.DataBean.DataListBean> items) {
        super.addItems(items);
    }

    @Override
    protected void bindDataToItemView(SingleItemViewHolder holder, CircleListBean.DataBean.DataListBean item) {
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
    }

    class SingleItemViewHolder extends RecyclerView.ViewHolder {
        public TextView whatAgoTextView;
        public TextView messageTextView;
        public MyGridlayout imageGridView;
        public TextView commentCountTextView;

        public SingleItemViewHolder(View itemView) {
            super(itemView);
            whatAgoTextView = (TextView) itemView.findViewById(R.id.single_item_what_ago);
            messageTextView = (TextView) itemView.findViewById(R.id.single_item_message);
            imageGridView = (MyGridlayout) itemView.findViewById(R.id.single_item_image_gv);
            commentCountTextView = (TextView) itemView.findViewById(R.id.single_item_comment_count);
        }
    }
}


