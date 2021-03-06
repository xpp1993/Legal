package com.lxkj.xpp.legal.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lxkj.xpp.legal.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 熊萍萍 on 2017/2/28/028.
 * 适用于RecyclerView的抽象Adapter，封装了数据集，ViewHolder的创建与绑定的过程，简化子类的操作
 *
 * @param <D> 数据集中的类型
 * @param <V> ViewHolder类型
 */
public abstract class RecyclerBaseAdapter<D, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {
    private int position;
    /**
     * RecyclerView中的数据集
     */
    protected final List<D> mDataSet = new ArrayList<>();

    /**
     * 点击事件处理回调
     */
    private OnItemClickListener<D> mItemClickListener;

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    protected D getItem(int position) {
        this.position = position;
        //Log.e("xpp3-29", position + "");
        return mDataSet.get(position);
    }

    public void addItems(List<D> items) {
        //移除已经存在的的数据避免重复
        items.removeAll(mDataSet);
        //添加新数据
        mDataSet.addAll(items);
        notifyDataSetChanged();
    }

    public void clearDataSet() {
        mDataSet.clear();
    }

    public void refresh(int position) {
        mDataSet.remove(getItem(position));
        notifyDataSetChanged();
    }

    /**
     * 绑定数据，主要分为两步,绑定数据与设置每项的点击事件chuli
     */
    @Override
    public final void onBindViewHolder(V viewHolder, int position) {
        final D item = getItem(position);
        bindDataToItemView(viewHolder, item);
        setupItemViewClickListener(viewHolder, item);
    }

    protected View inflateItemView(ViewGroup viewGroup, int layoutId) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);
    }

    public void setOnItemClickListener(OnItemClickListener<D> mItemClickListener) {

        this.mItemClickListener = mItemClickListener;
    }

    /**
     * 点击事件
     *
     * @param viewHolder
     * @param item
     */
    protected void setupItemViewClickListener(final V viewHolder, final D item) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    if (viewHolder.getLayoutPosition() > 0)
                        mItemClickListener.onClick(item, viewHolder.getLayoutPosition() - 1);
                }
            }
        });
    }

    /**
     * 将数据绑定到ITemView上
     */
    protected abstract void bindDataToItemView(V viewHolder, D item);
}
