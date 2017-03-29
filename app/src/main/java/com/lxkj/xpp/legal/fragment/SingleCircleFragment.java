package com.lxkj.xpp.legal.fragment;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.hyphenate.easeui.widget.EaseTitleBar;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.adapter.SingleCircleListItemAdapter;
import com.lxkj.xpp.legal.app.MyApplication;
import com.lxkj.xpp.legal.base.BaseFragment;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.event.NavFragmentEvent;
import com.lxkj.xpp.legal.listener.CircleListBroadcast;
import com.lxkj.xpp.legal.listener.OnItemClickListener;
import com.lxkj.xpp.legal.model.bean.CircleListBean;
import com.lxkj.xpp.legal.presenter.CirclePresenter;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.view.CircleView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by 熊萍萍 on 2017/3/29/029.
 * 单个用户的帖书列表页
 */

public class SingleCircleFragment extends BaseFragment<CirclePresenter> implements CircleView, XRecyclerView.LoadingListener {
    @BindView(R.id.single_circle_rv)
    XRecyclerView xRecyclerView;
    @BindView(R.id.single_circle_title_bar)
    EaseTitleBar easeTitleBar;
    SingleCircleListItemAdapter itemAdapter;
    private int page = 1;
    private int totalPage = 1;
    private boolean isLoadingMore;//加载更多标杆，默认false
    private boolean isRefresh;
    private final int REQUIRE_CODE_MESSAGE_NEW = 0X01, REQUIRE_CODE_MESSAGE_OLD = 0X02;
    private String uid;
    private CircleListBroadcast circleListBroadcast;
    int position;

    /**
     * 上拉刷新
     */
    @Override
    public void onRefresh() {
        if (!isRefresh) {
            isRefresh = true;
            page = 1;
            mPresenter.getSingleUserArticles(CommonUtils.getContext(), 10, page, uid);
        }
    }

    /**
     * 下拉加载更多
     */
    @Override
    public void onLoadMore() {

        if (!isLoadingMore) {
            if (page >= totalPage) {
                xRecyclerView.setNoMore(true);
                return;
            }
            page++;
            isLoadingMore = true;
            mPresenter.getSingleUserArticles(CommonUtils.getContext(), 10, page, uid);
        }
    }

    /**
     * 初始化布局
     */
    @Override
    protected void initWidgets() {
        StatusBarCompat.setStatusBarColor(getActivity(), ContextCompat.getColor(CommonUtils.getContext(), R.color.colorPrimaryDark));
        easeTitleBar.getLeftLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        uid = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.uid);
        initAdapter();
        registerReceiver();
    }

    /**
     * 设置Presenter
     *
     * @return
     */
    @Override
    protected CirclePresenter setPresenter() {
        return new CirclePresenter(this);
    }

    /**
     * 设置view
     *
     * @return
     */
    @Override
    protected int setContentViewId() {
        return R.layout.fragment_single_circle;
    }

    /**
     * 更新界面
     *
     * @param bundle
     * @param id
     */
    @Override
    public void updataUI(Bundle bundle, int id) {
        if (bundle == null)
            return;
        switch (id) {
            case Constant.ID.LOAD_CIRCLE://加载朋友圈
                CircleListBean circleListBean = (CircleListBean) bundle.getSerializable("circleListBean");
                int code = bundle.getInt("code");
                totalPage = circleListBean.getData().getTotalPage();
                ArrayList<CircleListBean.DataBean.DataListBean> dataListBeen = (ArrayList<CircleListBean.DataBean.DataListBean>) circleListBean.getData().getDataList();
                if (code == REQUIRE_CODE_MESSAGE_NEW) {
                    itemAdapter.clearDataSet();
                }
                itemAdapter.addItems(dataListBeen);
                isLoadingMore = false;
                isRefresh = false;
                xRecyclerView.refreshComplete();
                xRecyclerView.loadMoreComplete();
                break;
        }
    }

    /**
     * show Toast
     *
     * @param message
     * @param i
     */
    @Override
    public void onShowToast(String message, int i) {

    }

    /**
     * 跳转页面
     *
     * @param baseFragment
     * @param bundle
     */
    @Override
    public void navigateToFragment(BaseFragment baseFragment, Bundle bundle) {

    }

    /**
     * 上拉或者下拉时用到，否则headView以及footView一直在
     */
    @Override
    public void onLoadingOrRefreshFailed() {
        isLoadingMore = isRefresh = false;
        xRecyclerView.refreshComplete();
        xRecyclerView.loadMoreComplete();
    }

    /**
     * 发布评论时用到，弹出输入框和键盘
     *
     * @param bundle
     * @param id
     */
    @Override
    public void publishComment(Bundle bundle, int id) {

    }

    protected void initAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        itemAdapter = new SingleCircleListItemAdapter(CommonUtils.getContext());
        itemAdapter.setOnItemClickListener(new OnItemClickListener<CircleListBean.DataBean.DataListBean>() {
            @Override
            public void onClick(CircleListBean.DataBean.DataListBean item, int position) {
                //跳转到详情页
                //跳转到详情页
                Bundle bundle = new Bundle();
                bundle.putString("uid", item.getUid());
                bundle.putInt("articleId", item.getObjId());
                SingleCircleFragment.this.position = position;
                EventBus.getDefault().post(new NavFragmentEvent(new CircleDetailFragment(), bundle));
            }
        });
        // 设置Adapter
        xRecyclerView.setAdapter(itemAdapter);
        xRecyclerView.setLoadingListener(this);
        xRecyclerView.setLoadingMoreEnabled(true);
        //刷新第一页的数据
        onRefresh();
    }

    //注册广播的方法
    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        try {
            if (circleListBroadcast != null) {
                getActivity().unregisterReceiver(circleListBroadcast);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        circleListBroadcast = new CircleListBroadcast(mPresenter, new CircleListBroadcast.RefreshAfetrDelete() {
            @Override
            public void refresh() {
                if (itemAdapter != null) {
                    itemAdapter.refresh(position);
                }
            }
        });
        filter.addAction(MyApplication.CIRCLEFRAGMENT_DELETE);
        getActivity().registerReceiver(circleListBroadcast, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (circleListBroadcast != null)
            getActivity().unregisterReceiver(circleListBroadcast);
    }
}
