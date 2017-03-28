package com.lxkj.xpp.legal.widget;

/**
 * Created by 熊萍萍 on 2017/3/22/022.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.listener.OnCommentEntityClickListener;
import com.lxkj.xpp.legal.model.bean.CommentsBean;
import com.lxkj.xpp.legal.utils.DisplayUtils;

import java.util.List;

public class MessageGroup extends ViewGroup implements View.OnClickListener, View.OnLongClickListener {

    private static final String TAG = "wyj";
    private static int KEY_SHORT = 4 << 24;
    private static int KEY_LONG = 8 << 24;

    private int maxLineNumber = 3;

    private int itemMarginLeft = DisplayUtils.dip2px(getContext(), 8);
    private int itemMarginTop = DisplayUtils.dip2px(getContext(), 2);
    private int itemMarginRight = DisplayUtils.dip2px(getContext(), 8);
    private int itemMarginBottom = DisplayUtils.dip2px(getContext(), 2);

    private int itemPaddingLeft = DisplayUtils.dip2px(getContext(), 0);
    private int itemPaddingTop = DisplayUtils.dip2px(getContext(), 4);
    private int itemPaddingRight = DisplayUtils.dip2px(getContext(), 4);
    private int itemPaddingBottom = DisplayUtils.dip2px(getContext(), 4);

    private
    @DrawableRes
    int pressedBack = R.drawable.msg_back;

    private
    @ColorInt
    int itemTextColor = ContextCompat.getColor(getContext(), R.color.colorMessageText);
    private
    @ColorInt
    int itemUserColor = ContextCompat.getColor(getContext(), R.color.colorUserText);
    private
    @ColorInt
    int itemHighlightColor = ContextCompat.getColor(getContext(), R.color.colorHighlight);

    private int itemTextSize = DisplayUtils.sp2px(getContext(), 14);

    private OnCommentEntityClickListener mOnEntityClickListener;

    public MessageGroup(Context context) {
        this(context, null);
    }

    public MessageGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * 信息体左边距
     *
     * @param itemMarginLeft
     */
    public void setItemMarginLeft(int itemMarginLeft) {
        this.itemMarginLeft = itemMarginLeft;
    }

    /**
     * 信息体上边距
     *
     * @param itemMarginTop
     */
    public void setItemMarginTop(int itemMarginTop) {
        this.itemMarginTop = itemMarginTop;
    }

    /**
     * 信息体右边距
     *
     * @param itemMarginRight
     */
    public void setItemMarginRight(int itemMarginRight) {
        this.itemMarginRight = itemMarginRight;
    }

    /**
     * 信息体底边距
     *
     * @param itemMarginBottom
     */
    public void setItemMarginBottom(int itemMarginBottom) {
        this.itemMarginBottom = itemMarginBottom;
    }

    public void setItemPaddingTop(int itemPaddingTop) {
        this.itemPaddingTop = itemPaddingTop;
    }

    public void setItemPaddingBottom(int itemPaddingBottom) {
        this.itemPaddingBottom = itemPaddingBottom;
    }

    public void setPressedBack(@DrawableRes int pressedBack) {
        this.pressedBack = pressedBack;
    }

    public void setItemTextColor(@ColorInt int itemTextColor) {
        this.itemTextColor = itemTextColor;
    }

    public void setItemTextSize(int itemTextSize) {
        this.itemTextSize = itemTextSize;
    }

    public void setItemPaddingLeft(int itemPaddingLeft) {
        this.itemPaddingLeft = itemPaddingLeft;
    }

    public void setItemPaddingRight(int itemPaddingRight) {
        this.itemPaddingRight = itemPaddingRight;
    }

    public void setItemUserColor(@ColorInt int itemUserColor) {
        this.itemUserColor = itemUserColor;
    }

    public void setItemHighlightColor(@ColorInt int itemHighlightColor) {
        this.itemHighlightColor = itemHighlightColor;
    }

    public void setMaxLineNumber(int maxLineNumber) {
        this.maxLineNumber = maxLineNumber;
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MessageGroupAttr, defStyleAttr, 0);

        maxLineNumber = a.getInteger(R.styleable.MessageGroupAttr_maxLineNumber, maxLineNumber);

        itemMarginLeft = a.getDimensionPixelSize(R.styleable.MessageGroupAttr_item_margin_left, itemMarginLeft);
        itemMarginTop = a.getDimensionPixelSize(R.styleable.MessageGroupAttr_item_margin_top, itemMarginTop);
        itemMarginRight = a.getDimensionPixelSize(R.styleable.MessageGroupAttr_item_margin_right, itemMarginRight);
        itemMarginBottom = a.getDimensionPixelSize(R.styleable.MessageGroupAttr_item_margin_bottom, itemMarginBottom);

        itemPaddingLeft = a.getDimensionPixelSize(R.styleable.MessageGroupAttr_item_padding_left, itemPaddingLeft);
        itemPaddingTop = a.getDimensionPixelSize(R.styleable.MessageGroupAttr_item_padding_top, itemPaddingTop);
        itemPaddingRight = a.getDimensionPixelSize(R.styleable.MessageGroupAttr_item_padding_right, itemPaddingRight);
        itemPaddingBottom = a.getDimensionPixelSize(R.styleable.MessageGroupAttr_item_padding_bottom, itemPaddingBottom);

        pressedBack = a.getResourceId(R.styleable.MessageGroupAttr_item_background, pressedBack);

        itemTextColor = a.getColor(R.styleable.MessageGroupAttr_item_text_color, itemTextColor);
        itemUserColor = a.getColor(R.styleable.MessageGroupAttr_item_user_color, itemUserColor);
        itemHighlightColor = a.getColor(R.styleable.MessageGroupAttr_item_highlight_color, itemHighlightColor);

        itemTextSize = a.getDimensionPixelSize(R.styleable.MessageGroupAttr_item_text_size, itemTextSize);

        a.recycle();
    }

    public void refresh(List<CommentsBean> commentsBeen) {
        removeAllViews();
        for (int i = 0; i < commentsBeen.size() && i < maxLineNumber; i++) {
            final CommentsBean entity = commentsBeen.get(i);
            int point = 0;
            final TextView textView = new TextView(getContext());

            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(entity.getDiscussNickName());
            builder.setSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setColor(itemUserColor);
                }

                @Override
                public void onClick(View widget) {
                    boolean tag = (boolean) widget.getTag(KEY_SHORT) || (boolean) widget.getTag(KEY_LONG);
                    widget.setTag(KEY_LONG, false);
                    if (!tag) {
                        widget.setTag(KEY_SHORT, true);
                        if (mOnEntityClickListener != null) {
                            mOnEntityClickListener.onFromClicked(entity);
                        }
                    }
                }
            }, 0, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            //  if (entity.getReplyNickName() != null) {
            if ("empty".equals(entity.getReplyNickName()) || entity.getReplyNickName() == null || "".equals(entity.getReplyNickName())) {

            } else {
                point = builder.length();
                builder.append(getResources().getString(R.string.return_text));
                builder.setSpan(new ForegroundColorSpan(itemTextColor), point, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                point = builder.length();
                // builder.append(entity.getDiscussNickName().toString());
                builder.append(entity.getReplyNickName().toString().trim());
                builder.setSpan(new ClickableSpan() {
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(itemUserColor);
                    }

                    @Override
                    public void onClick(View widget) {
                        boolean tag = (boolean) widget.getTag(KEY_SHORT) || (boolean) widget.getTag(KEY_LONG);
                        widget.setTag(KEY_LONG, false);
                        if (!tag) {
                            widget.setTag(KEY_SHORT, true);
                            if (mOnEntityClickListener != null) {
                                mOnEntityClickListener.onTargetClicked(entity);
                            }
                        }

                    }
                }, point, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            }
            point = builder.length();
            builder.append(getResources().getString(R.string.split_text)).append(entity.getContent());
            builder.setSpan(new ForegroundColorSpan(itemTextColor), point, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = itemMarginLeft;
            layoutParams.topMargin = itemMarginTop;
            layoutParams.rightMargin = itemMarginRight;
            layoutParams.bottomMargin = itemMarginBottom;
            textView.setLayoutParams(layoutParams);
            textView.setTextSize(DisplayUtils.px2sp(getContext(), itemTextSize));
            textView.setPadding(itemPaddingLeft, itemPaddingTop, itemPaddingRight, itemPaddingBottom);
            textView.setText(builder);
            textView.setBackgroundResource(pressedBack);
            textView.setOnClickListener(this);
            textView.setOnLongClickListener(this);
            textView.setTag(KEY_SHORT, false);
            textView.setTag(KEY_LONG, false);
            textView.setTag(entity);
            textView.setHighlightColor(itemHighlightColor);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            addView(textView);
        }
        invalidate();
    }

    public void setOnEntityClickListener(OnCommentEntityClickListener onEntityClickListener) {
        this.mOnEntityClickListener = onEntityClickListener;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获得它的父容器为它设置的测量模式和大小
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        // 如果是warp_content情况下，记录宽和高
        int width = 0;
        int height = 0;

        int cCount = getChildCount();

        // 遍历每个子元素
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);


            // 测量每一个child的宽和高
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);

            // 得到child的lp
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();

            width = Math.max(width, child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);
            height += child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

        }
        width += getPaddingLeft() + getPaddingRight();
        height += getPaddingTop() + getPaddingBottom();

        setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth
                : width, (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight
                : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 获取父容器内边距
        int height = getPaddingTop();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();
            child.layout(getPaddingLeft() + lp.leftMargin, height + lp.topMargin,
                    getPaddingLeft() + lp.leftMargin + child.getMeasuredWidth(), height + lp.topMargin + child.getMeasuredHeight());
            height += lp.topMargin + child.getMeasuredHeight() + lp.bottomMargin;
        }
    }

    @Override
    public void onClick(View v) {
        boolean tag = (boolean) v.getTag(KEY_SHORT);
        v.setTag(KEY_SHORT, false);
        v.setTag(KEY_LONG, false);
        if (!tag) {
            if (mOnEntityClickListener != null) {
                mOnEntityClickListener.onMessageClicked((CommentsBean) v.getTag());
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        v.setTag(KEY_LONG, true);
        v.setTag(KEY_SHORT, false);
        if (mOnEntityClickListener != null) {
            mOnEntityClickListener.onMessageLongClicked((CommentsBean) v.getTag());
        }
        return true;
    }


}

