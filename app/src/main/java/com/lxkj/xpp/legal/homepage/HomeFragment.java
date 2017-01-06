package com.lxkj.xpp.legal.homepage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.hyphenate.easeui.widget.EaseTitleBar;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.activity.PDFDetailActivity;
import com.lxkj.xpp.legal.base.BaseFragment;
import com.lxkj.xpp.legal.homepage.mvpview.HomeView;
import com.lxkj.xpp.legal.homepage.presenter.HomePresenter;
import com.lxkj.xpp.legal.net.RequestManager;
import com.lxkj.xpp.legal.net.callback.ReqProgressCallBack;
import com.lxkj.xpp.legal.utils.CommonUtils;

import java.io.File;

import butterknife.BindView;

/**
 * Created by 熊萍萍 on 2016/12/22/022.
 * 首页
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeView, View.OnClickListener, ReqProgressCallBack<Object> {
    @BindView(R.id.title_bar_main)
    public EaseTitleBar titleBar;
    @BindView(R.id.btn_test)
    public Button test_pdf;
    private String urlpath = "http://oa8iajf2m.bkt.clouddn.com/Redis%E5%85%A5%E9%97%A8%E6%8C%87%E5%8D%97%EF%BC%88%E7%AC%AC2%E7%89%88%EF%BC%89.pdf";
    private ProgressDialog m_progressDlg;//更新软件进度条
    private File file;

    @Override
    protected void initListener() {

    }

    @Override
    protected HomePresenter setPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected void initWidgets(Bundle savedInstanceState) {
        titleBar.setTitle("首页");
        test_pdf.setOnClickListener(this);
        m_progressDlg = new ProgressDialog(getActivity());
        m_progressDlg.setTitle("请稍后...");
        m_progressDlg.setMessage("正在下载数据，请稍等...");
        m_progressDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确
        m_progressDlg.setIndeterminate(false);
        m_progressDlg.setCancelable(false);
        m_progressDlg.setMax(100);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
                m_progressDlg.show();
                String fileName = urlpath.substring(urlpath.lastIndexOf('/') + 1);
                file = new File(Environment.getExternalStorageDirectory(), fileName);
                RequestManager.getInstance(CommonUtils.getContext()).downLoadFile(urlpath, file, this, 0x26);
                break;
        }
    }

    @Override
    public void onProgress(int requestCode, long total, long current) {
        switch (requestCode) {
            case 0x26:
                int a = (int) Math.floor((current * 1.0 / total * 100));
                m_progressDlg.setProgress(a);
                if (total == current) {
                    m_progressDlg.dismiss();
                }
                break;
        }
    }

    @Override
    public void onReqSuccess(int requestCode, Object result) {
        switch (requestCode) {
            case 0x26:
                Intent intent = new Intent(getActivity(), PDFDetailActivity.class);
                intent.setAction(Intent.ACTION_VIEW);
                if (file == null)
                    return;
                intent.setData(Uri.fromFile(file));
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onReqFailed(int requestCode, String errorMsg) {

    }
}
