package com.lxkj.xpp.legal.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 熊萍萍 on 2017/3/1/001.
 * 格式校验工具类
 */

public class FormatCheck {
    /**
     * 手机号码格式校验
     * @param phone
     * @return true:合法 false:非法
     */
    public static boolean isMobile(String phone){
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8,7][0-9]{9}$"); // 验证手机号
        m = p.matcher(phone);
        b = m.matches();
        return b;
    }
}
