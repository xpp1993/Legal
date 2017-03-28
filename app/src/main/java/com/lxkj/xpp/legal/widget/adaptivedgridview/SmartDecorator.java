package com.lxkj.xpp.legal.widget.adaptivedgridview;

/**
 *
 */
public class SmartDecorator extends Decorator {
    @Override
    public int decorate(int resCount, int columnNumber) {
        if (resCount < columnNumber) {
            columnNumber = resCount;
        } else if (resCount == 2 * (columnNumber - 1) && resCount > (columnNumber)) {
            columnNumber -= 1;
        } else if (resCount % columnNumber == 0 && resCount / 2 < columnNumber) {
            columnNumber = resCount / 2;
        }
        return columnNumber;
    }
}
