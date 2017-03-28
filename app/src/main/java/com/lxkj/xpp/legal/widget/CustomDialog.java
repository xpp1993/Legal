package com.lxkj.xpp.legal.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.lxkj.xpp.legal.R;

/**
 * Created by 熊萍萍 on 2017/3/21/021.
 * 加载提醒对话框
 */
public class CustomDialog extends ProgressDialog {
    private Context context;

    public CustomDialog(Context context) {
        super(context);
        this.context = context;
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init(context);
    }

    private void init(Context context) {
//        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
//        setCancelable(false);
//        setCanceledOnTouchOutside(false);
        setContentView(R.layout.loading);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
    }

    /**
     * 暴露一个方法给progressDialog设置提醒文本
     *
     * @param warnText
     */
    public void setText(String warnText) {
        View view = View.inflate(context, R.layout.loading, null);
        if (view != null)
            ((TextView) view.findViewById(R.id.tv_load_dialog)).setText(warnText);
    }
}