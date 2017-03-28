package com.lxkj.xpp.legal.model;

import android.os.Handler;
import android.widget.TextView;

import com.lxkj.xpp.legal.constant.Constant;

/**
 * Created by 熊萍萍 on 2017/3/21/021.
 * 验证码计时类
 */

public class RockRunnable implements Runnable {
    private int remainRockTime;//倒计时剩余时长
    private Handler handler;
    private TextView get_check_code;

    public RockRunnable(int remainRockTime, Handler handler, TextView get_check_code) {
        this.remainRockTime = remainRockTime;
        this.handler = handler;
        this.get_check_code = get_check_code;
    }

    @Override
    public void run() {
        remainRockTime--;
        if (remainRockTime > 0) {
            get_check_code.setClickable(false);
            get_check_code.setText(remainRockTime + "秒");
            handler.postDelayed(this, 1000);
        } else {
            get_check_code.setClickable(true);
            get_check_code.setText("获取验证码");
            remainRockTime = Constant.appFinal.TOTAL_ROCK_TIME;
        }
    }
}
