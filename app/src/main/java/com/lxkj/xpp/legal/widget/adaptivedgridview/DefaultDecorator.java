package com.lxkj.xpp.legal.widget.adaptivedgridview;

/**
 *默认每一列的数量恒等于columnNumber
 */
public class DefaultDecorator extends Decorator {
    @Override
    public int decorate(int resCount, int columnNumber) {
        return columnNumber;
    }
}
