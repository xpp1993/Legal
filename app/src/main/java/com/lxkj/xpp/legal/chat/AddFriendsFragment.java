package com.lxkj.xpp.legal.chat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hyphenate.easeui.widget.EaseTitleBar;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseFragment;
import com.lxkj.xpp.legal.presenter.AddFriendsPresenter;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.utils.ToastUtlis;
import com.lxkj.xpp.legal.view.AddFriendsView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 熊萍萍 on 2017/3/13/013.
 * 添加好友、或者群
 */

public class AddFriendsFragment extends BaseFragment<AddFriendsPresenter> implements AddFriendsView {
    //    @BindView(R.id.status_bar2)
//    LinearLayout status_bar;
    @BindView(R.id.addfriends_title_bar)
    EaseTitleBar easeTitleBar;
    //    @BindView(R.id.bar2_tv_title_center)
//    TextView title;
//    @BindView(R.id.bar2_tv_title_right)
//    TextView title_rigiht;
    @BindView(R.id.fragment_add_friends)
    EditText add_friends;
    @BindView(R.id.btn_add_friends)
    Button btn_add_friends;
    @BindView(R.id.btn_delete_friends)
    Button btn_delete_friends;

    @Override
    protected void initWidgets() {
        //initState(status_bar);
        // CommonUtils.setMystatusBar(getActivity());
//        image_back.setVisibility(View.VISIBLE);
//        title_rigiht.setVisibility(View.VISIBLE);
//        title_rigiht.setText("确定");
//        title.setText("添加");
    }

    @OnClick({R.id.btn_add_friends, R.id.btn_delete_friends})
    void click(View view) {
        String userName = add_friends.getText().toString().trim();
        switch (view.getId()) {
//            case R.id.bar2_iv_left:
//                getActivity().onBackPressed();
//                break;
            case R.id.btn_add_friends://添加好友
                mPresenter.relationFrinds(CommonUtils.getContext(), userName, 1001);
                break;
            case R.id.btn_delete_friends://删除好友
                mPresenter.relationFrinds(CommonUtils.getContext(), userName, 1002);
                break;
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected AddFriendsPresenter setPresenter() {
        return new AddFriendsPresenter(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_addfriends;
    }

    @Override
    public void onShowToast(String message, int i) {
        ToastUtlis.showToastInUIThread(message);
    }

    @Override
    public void navigateToFragment(BaseFragment baseFragment, Bundle bundle) {

    }
}
