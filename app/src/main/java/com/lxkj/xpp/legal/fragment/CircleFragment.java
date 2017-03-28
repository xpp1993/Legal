package com.lxkj.xpp.legal.fragment;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.hyphenate.easeui.widget.EaseTitleBar;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.adapter.CircleListItemAdapter;
import com.lxkj.xpp.legal.adapter.ItemViewHolder;
import com.lxkj.xpp.legal.app.MyApplication;
import com.lxkj.xpp.legal.base.BaseFragment;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.event.NavFragmentEvent;
import com.lxkj.xpp.legal.listener.CircleListBroadcast;
import com.lxkj.xpp.legal.listener.IssueOrReplyDo;
import com.lxkj.xpp.legal.listener.OnItemClickListener;
import com.lxkj.xpp.legal.model.bean.CircleDetailBean;
import com.lxkj.xpp.legal.model.bean.CircleListBean;
import com.lxkj.xpp.legal.model.bean.CommentsBean;
import com.lxkj.xpp.legal.presenter.CirclePresenter;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.utils.ToastUtlis;
import com.lxkj.xpp.legal.view.CircleView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 熊萍萍 on 2016/12/22/022.
 * 圈子
 */

public class CircleFragment extends BaseFragment<CirclePresenter> implements CircleView, XRecyclerView.LoadingListener, View.OnClickListener {
    @BindView(R.id.tieshu_rv)
    XRecyclerView mRecyclerView;
    @BindView(R.id.tieshu_title_bar)
    EaseTitleBar easeTitleBar;
    CircleListItemAdapter itemAdapter;
    private int page = 1;
    private int totalPage = 1;
    private boolean isLoadingMore;//加载更多标杆，默认false
    private boolean isRefresh;
    private final int REQUIRE_CODE_MESSAGE_NEW = 0X01, REQUIRE_CODE_MESSAGE_OLD = 0X02;
    private ItemViewHolder itemViewHolder;
    private ItemViewHolder viewHolder;
    @BindView(R.id.commit_input_rl)
    RelativeLayout mCommitRelativeLayout;
    @BindView(R.id.commit_et)
    EditText editText_comment;
    private int articleId;
    private CommentsBean commentsBean;
    int comment_type;
    private CircleListBroadcast circleListBroadcast;

    @Override
    protected void initWidgets() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(CommonUtils.getContext()));
        mCommitRelativeLayout.findViewById(R.id.commit_bt).setOnClickListener(this);
        initAdapter();
    }

    @Override
    protected void initData() {
        easeTitleBar.getRightLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//发布帖书
                EventBus.getDefault().post(new NavFragmentEvent(new PublishCircleFragment()));
            }
        });
        isLoadingMore = false;
        registerReceiver();
    }

    @Override
    protected CirclePresenter setPresenter() {
        return new CirclePresenter(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_tieshu;
    }

    @Override
    public void onShowToast(String message, int i) {
        ToastUtlis.showToastInUIThread(message);
    }

    @Override
    public void navigateToFragment(BaseFragment baseFragment, Bundle bundle) {

    }

    /**
     * 获得数据后更新UI界面
     *
     * @param bundle
     * @param id
     */
    @Override
    public void updataUI(Bundle bundle, int id) {
        if (bundle == null)
            return;
        itemViewHolder = viewHolder;
        switch (id) {
            case Constant.ID.PUBLIS_DETAIL://获取帖书详情
                CircleDetailBean circleDetailBean = (CircleDetailBean) bundle.getSerializable("circleDetailBean");
                int commentNumber = circleDetailBean.getData().getCommentNum();
                List<CommentsBean> commentsBeen = circleDetailBean.getData().getComments();
                itemViewHolder.commentCountTextView.setText(commentNumber + "评论");
                itemViewHolder.messageGroup.setVisibility(View.VISIBLE);
                itemViewHolder.messageGroup.setMaxLineNumber(10);//手动设置最多可以显示多少条评论
                itemViewHolder.messageGroup.refresh(commentsBeen);
                CommonUtils.closeKeyMap(getActivity());//评论后更新评论评论列表后，关闭键盘，隐藏评论Relativelayout,清空edittext
                editText_comment.setText(null);
                mCommitRelativeLayout.setVisibility(View.GONE);
                break;
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
                mRecyclerView.refreshComplete();
                mRecyclerView.loadMoreComplete();
                break;
        }
    }


    @Override
    public void onLoadingOrRefreshFailed() {
        isLoadingMore = isRefresh = false;
        mRecyclerView.refreshComplete();
        mRecyclerView.loadMoreComplete();
    }

    @Override
    public void publishComment(Bundle bundle, int id) {

    }

    protected void initAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        itemAdapter = new CircleListItemAdapter(CommonUtils.getContext(), issueOrReplyDo);
        itemAdapter.setOnItemClickListener(new OnItemClickListener<CircleListBean.DataBean.DataListBean>() {
            @Override
            public void onClick(CircleListBean.DataBean.DataListBean item) {
                //跳转到详情页
                Bundle bundle = new Bundle();
                bundle.putInt("articleId", item.getObjId());
                EventBus.getDefault().post(new NavFragmentEvent(new CircleDetailFragment(), bundle));
            }
        });
        // 设置Adapter
        mRecyclerView.setAdapter(itemAdapter);
        mRecyclerView.setLoadingListener(this);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                mCommitRelativeLayout.setVisibility(View.GONE);
                CommonUtils.closeKeyMap(getActivity());
                return false;
            }
        });
        //刷新第一页的数据
        onRefresh();
    }

    /**
     * 评论、回复后需要做的事情
     */
    private IssueOrReplyDo issueOrReplyDo = new IssueOrReplyDo() {
        @Override
        public void issueorReply(ItemViewHolder viewHolder, int type, int acticleId, CommentsBean commentsBean) {
            CircleFragment.this.viewHolder = viewHolder;
            itemViewHolder = CircleFragment.this.viewHolder;
            comment_type = type;
            CircleFragment.this.articleId = acticleId;
            CircleFragment.this.commentsBean = commentsBean;
            //弹出输入框,打开键盘
            editText_comment.requestFocus();
            mCommitRelativeLayout.setVisibility(View.VISIBLE);
            CommonUtils.openKeyboard(getActivity());
        }

    };

    /**
     * 刷新
     */
    @Override
    public void onRefresh() {
        if (!isRefresh) {
            isRefresh = true;
            page = 1;
            mPresenter.loadCircleData(CommonUtils.getContext(), REQUIRE_CODE_MESSAGE_NEW, 5, page);
        }
    }


    /**
     * 加载更多
     */
    @Override
    public void onLoadMore() {

        if (!isLoadingMore) {
            if (page >= totalPage) {
                mRecyclerView.setNoMore(true);
                return;
            }
            page++;
            isLoadingMore = true;
            mPresenter.loadCircleData(CommonUtils.getContext(), REQUIRE_CODE_MESSAGE_OLD, 5, page);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit_bt:
                String content = editText_comment.getText().toString().trim();
                if (content != null)
                    mPresenter.IssueOrReplyDo(CommonUtils.getContext(), comment_type, articleId, commentsBean, content);
                else
                    ToastUtlis.showToastInUIThread("内容不能为空");
                break;
        }
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
        circleListBroadcast = new CircleListBroadcast(mPresenter);
        filter.addAction(MyApplication.CIRCLEFRAGMENT_TAG);
        getActivity().registerReceiver(circleListBroadcast, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (circleListBroadcast != null)
            getActivity().unregisterReceiver(circleListBroadcast);
    }
}
