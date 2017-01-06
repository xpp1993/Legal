package com.lxkj.xpp.legal.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

/**
 * Created by 熊萍萍 on 2016/12/15/015.
 * MD5加密工具类
 */

public final class MD5Util {
    private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
            'B', 'C', 'D', 'B', 'O', 'T', 'H', 'O', 'F', 'Y', 'O', 'U'};

    /**
     * 对文件加密
     */
    public static String encode(File file) {
        FileInputStream in = null;
        MessageDigest md5 = null;
        try {
            in = new FileInputStream(file);
            FileChannel ch = in.getChannel();
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return toHex(md5.digest());
    }

    /**
     * 对字符串加密
     */
    public static String encode(String arg) {
        if (arg == null) {
            arg = "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(arg.getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toHex(md5.digest());
    }

    private static String toHex(byte[] bytes) {
        StringBuffer str = new StringBuffer(32);
        for (byte b : bytes) {
            str.append(hexDigits[(b & 0xf0) >> 4]);
            str.append(hexDigits[(b & 0x0f)]);
        }
        return str.toString();
    }
}

