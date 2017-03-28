package com.lxkj.xpp.legal.widget.adaptivedgridview;

import android.widget.ImageView;

/**
 * Created by Carl on 2017-03-20 020.
 */

public interface MyGridLayoutCallBack {
    /**
     * 单击事件
     * @param res
     */
    void onClick(Object res);

    /**
     * 长按事件
     * @param res
     */
    void onLongClick(Object res);

    /**
     * 图片资源自定义展示回调,你可以使用自己的图片加载库进行图片渲染
     * @param imageView
     * @param res
     */
    void inflateItem(ImageView imageView, Object res);
}
