package com.lxkj.xpp.legal.widget.adaptivedgridview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.utils.DisplayUtils;

import java.util.List;

public class MyGridlayout extends ViewGroup implements View.OnClickListener, View.OnLongClickListener {

    private int columnNumber = 3;

    private int maxItemCount = columnNumber;

    private int itemMarginLeft = DisplayUtils.dip2px(getContext(), 4);
    private int itemMarginTop = DisplayUtils.dip2px(getContext(), 4);
    private int itemMarginRight = DisplayUtils.dip2px(getContext(), 4);
    private int itemMarginBottom = DisplayUtils.dip2px(getContext(), 4);

    private MyGridLayoutCallBack myGridLayoutCallBack;

    private Decorator decorator = new DefaultDecorator();//列数目动态调节器

    public MyGridlayout(Context context) {
        this(context, null);
    }

    public MyGridlayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyGridlayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public int getItemMarginLeft() {
        return itemMarginLeft;
    }

    public void setItemMarginLeft(int itemMarginLeft) {
        this.itemMarginLeft = itemMarginLeft;
    }

    public int getItemMarginTop() {
        return itemMarginTop;
    }

    public void setItemMarginTop(int itemMarginTop) {
        this.itemMarginTop = itemMarginTop;
    }

    public int getItemMarginRight() {
        return itemMarginRight;
    }

    public void setItemMarginRight(int itemMarginRight) {
        this.itemMarginRight = itemMarginRight;
    }

    public int getItemMarginBottom() {
        return itemMarginBottom;
    }

    public void setItemMarginBottom(int itemMarginBottom) {
        this.itemMarginBottom = itemMarginBottom;
    }

    public void setDecorator(Decorator decorator) {
        this.decorator = decorator;
    }

    public MyGridLayoutCallBack getMyGridLayoutCallBack() {
        return myGridLayoutCallBack;
    }

    public int getMaxItemCount() {
        return maxItemCount;
    }

    public void setMaxItemCount(int maxItemCount) {
        this.maxItemCount = maxItemCount;
    }

    public void setMyGridLayoutCallBack(MyGridLayoutCallBack myGridLayoutCallBack) {
        this.myGridLayoutCallBack = myGridLayoutCallBack;
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyGridlayoutAttr, defStyleAttr, 0);

        columnNumber = a.getInt(R.styleable.MyGridlayoutAttr_columnNumber, columnNumber);
        maxItemCount = a.getInt(R.styleable.MyGridlayoutAttr_maxItemCount, maxItemCount);

        itemMarginLeft = a.getDimensionPixelSize(R.styleable.MyGridlayoutAttr_itemMarginLeft, itemMarginLeft);
        itemMarginTop = a.getDimensionPixelSize(R.styleable.MyGridlayoutAttr_itemMarginTop, itemMarginTop);
        itemMarginRight = a.getDimensionPixelSize(R.styleable.MyGridlayoutAttr_itemMarginRight, itemMarginRight);
        itemMarginBottom = a.getDimensionPixelSize(R.styleable.MyGridlayoutAttr_itemMarginBottom, itemMarginBottom);
        a.recycle();
    }

    public void refresh(List<Object> imageRes) {
        removeAllViews();
        columnNumber = decorator.decorate(imageRes.size(), columnNumber);
        for (int i = 0; i < imageRes.size() && i < maxItemCount; i++) {
            Object res = imageRes.get(i);
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (myGridLayoutCallBack != null) {
                myGridLayoutCallBack.inflateItem(imageView, res);
            }
            imageView.setTag(res);
            imageView.setOnClickListener(this);
            imageView.setOnLongClickListener(this);

            addView(imageView);
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 获得它的父容器为它设置的测量模式和大小
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = 0;

        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        if (modeWidth != MeasureSpec.EXACTLY) {
            throw new IllegalArgumentException("layout_width can not be wrap_content");
        }
        if (modeHeight == MeasureSpec.EXACTLY) {
            throw new IllegalArgumentException("layout_height must be wrap_content");
        }

        int lineHeight = 0;

        int eachWidth = (width - getPaddingLeft() - getPaddingRight() - columnNumber * itemMarginLeft - columnNumber * itemMarginRight) / columnNumber;
        int eachHeight;
        if (getChildCount() == 1) {
            eachHeight = eachWidth / 2;
        } else {
            eachHeight = eachWidth;
        }


        int cCount = getChildCount();

        // 遍历每个子元素
        for (int i = 0; i < cCount; i++) {

            View child = getChildAt(i);

            MarginLayoutParams layoutParams = new MarginLayoutParams(eachWidth, eachHeight);
            layoutParams.leftMargin = itemMarginLeft;
            layoutParams.topMargin = itemMarginTop;
            layoutParams.rightMargin = itemMarginRight;
            layoutParams.bottomMargin = itemMarginBottom;
            child.setLayoutParams(layoutParams);


            // 测量每一个child的宽和高
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);

            // 得到child的lp
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();

            if (i % columnNumber == 0) {
                lineHeight = 0;
            }
            if (lineHeight < child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin) {
                height -= lineHeight;
                lineHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
                height += lineHeight;
            }

        }
        height += getPaddingTop() + getPaddingBottom();

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 获取父容器内边距
        int height = getPaddingTop();
        int width = getPaddingLeft();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();
            if (i % columnNumber == 0) {
                width = getPaddingLeft();
                if (i > 0) {
                    height += lp.topMargin + child.getMeasuredHeight() + lp.bottomMargin;
                }

                child.layout(width + lp.leftMargin, height + lp.topMargin,
                        width + lp.leftMargin + child.getMeasuredWidth(), height + lp.topMargin + child.getMeasuredHeight());

                width += lp.leftMargin + child.getMeasuredWidth() + lp.rightMargin;
            } else {
                child.layout(width + lp.leftMargin, height + lp.topMargin,
                        width + lp.leftMargin + child.getMeasuredWidth(), height + lp.topMargin + child.getMeasuredHeight());
                width += lp.leftMargin + child.getMeasuredWidth() + lp.rightMargin;
            }

        }
    }

    @Override
    public void onClick(View v) {
        if (myGridLayoutCallBack != null) {
            myGridLayoutCallBack.onClick(v.getTag());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (myGridLayoutCallBack != null) {
            myGridLayoutCallBack.onLongClick(v.getTag());
        }
        return true;
    }
}