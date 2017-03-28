package com.lxkj.xpp.legal.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.easeui.widget.EaseTitleBar;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseFragment;
import com.lxkj.xpp.legal.event.NavFragmentEvent;
import com.lxkj.xpp.legal.presenter.IDPresenter;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.utils.ToastUtlis;
import com.lxkj.xpp.legal.view.IDView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by 熊萍萍 on 2017/2/27/027.
 * 身份证实名注册页面
 * 填写的信息保存到sharedpreferences文件
 */
public class IDRegistFragment extends BaseFragment<IDPresenter> implements View.OnClickListener, IDView {
    @BindView(R.id.id_register_title_bar)
    EaseTitleBar easeTitleBar;
    @BindView(R.id.id_real_name)
    EditText id_name;
    @BindView(R.id.id_real_id)
    EditText id_id;
    @BindView(R.id.fragment_id_commit)
    TextView id_commit;
    @BindView(R.id.id_text_warn)
    TextView warn_text;

    @Override
    protected void initWidgets() {

    }

    @Override
    protected void onGetBundle(Bundle bundle) {
        super.onGetBundle(bundle);
    }

    @Override
    protected void initData() {
        StatusBarCompat.setStatusBarColor(getActivity(), ContextCompat.getColor(CommonUtils.getContext(), R.color.colorPrimaryDark));
        easeTitleBar.getLeftLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        id_commit.setOnClickListener(this);
        mPresenter.getConstant();
    }

    @Override
    protected IDPresenter setPresenter() {
        return new IDPresenter(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_id_regist;
    }


    @Override
    public void onShowToast(String message, int i) {
        ToastUtlis.showToastInUIThread(message);
    }

    @Override
    public void navigateToFragment(BaseFragment baseFragment, Bundle bundle) {
        EventBus.getDefault().post(new NavFragmentEvent(baseFragment, bundle));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_id_commit://实名认证
                mPresenter.toReal(CommonUtils.getContext(), id_name, id_id);
                break;
        }
    }

    @Override
    public void changeDataForUi(Bundle bundle, int id) {
        if (bundle == null)
            return;
        String warn_data = bundle.getString("warn_data");
        warn_text.setText(warn_data);
    }
}
