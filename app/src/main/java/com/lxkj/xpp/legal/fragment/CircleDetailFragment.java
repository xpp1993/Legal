package com.lxkj.xpp.legal.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hyphenate.easeui.widget.EaseTitleBar;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseFragment;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.model.bean.CommentsBean;
import com.lxkj.xpp.legal.presenter.CirclePresenter;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.utils.ToastUtlis;
import com.lxkj.xpp.legal.view.CircleView;
import com.lxkj.xpp.legal.widget.MessageGroup;
import com.lxkj.xpp.legal.widget.adaptivedgridview.MyGridlayout;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 熊萍萍 on 2017/3/27/027.
 * 帖书详情页
 */

public class CircleDetailFragment extends BaseFragment<CirclePresenter> implements CircleView, View.OnClickListener {
    @BindView(R.id.item_head)
    CircleImageView headCircleImageView;
    @BindView(R.id.item_nickname)
    TextView nicknameTextView;
    @BindView(R.id.item_what_ago)
    TextView whatAgoTextView;
    @BindView(R.id.item_message)
    TextView messageTextView;
    @BindView(R.id.item_image_gv)
    MyGridlayout imageGridView;
    @BindView(R.id.item_comment_count)
    TextView commentCountTextView;
    @BindView(R.id.item_comment_iv)
    ImageView commentImageView;
    @BindView(R.id.item_mg)
    MessageGroup messageGroup;
    @BindView(R.id.all_comment_warn)
    TextView all_comment_warn;
    @BindView(R.id.detail_scroll_view)
    ScrollView scrollView;
    @BindView(R.id.commit_input_rl)
    RelativeLayout mCommitRelativeLayout;
    @BindView(R.id.commit_et)
    EditText editText_comment;
    @BindView(R.id.circle_detail_title_bar)
    EaseTitleBar easeTitleBar;
    private int articleId;
    private int type;
    private CommentsBean commentsBean;
    private Handler mHandler;

    @Override
    protected void initWidgets() {
        all_comment_warn.setVisibility(View.VISIBLE);
        this.type = Constant.appFinal.issue;
        easeTitleBar.getLeftLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    protected void onGetBundle(Bundle bundle) {
        super.onGetBundle(bundle);
        if (bundle == null)
            return;
        articleId = bundle.getInt("articleId");
        type = Constant.appFinal.issue;
        mPresenter.loadPublishDetail(CommonUtils.getContext(), articleId);
    }

    @Override
    protected void initData() {
        mHandler=new Handler();
        mCommitRelativeLayout.findViewById(R.id.commit_bt).setOnClickListener(this);
    }

    @Override
    protected CirclePresenter setPresenter() {
        return new CirclePresenter(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_circle_detail;
    }

    /**
     * 获得数据后更新Ui界面
     *
     * @param bundle
     * @param id
     */
    @Override
    public void updataUI(Bundle bundle, int id) {
        if (bundle == null)
            return;
        if (id == Constant.ID.PUBLIS_DETAIL) {//帖书详情标志
            mPresenter.setCircleDetailUI(bundle, headCircleImageView, imageGridView, nicknameTextView, whatAgoTextView, messageTextView,
                    commentImageView, commentCountTextView, messageGroup, 6);
            CommonUtils.closeKeyMap(getActivity());//评论后更新评论列表后，关闭键盘,清空edittext
            editText_comment.setText(null);
            mHandler.postDelayed(srcollRunnable, 500);
        }
        //发送一个广播更新当前Item
        EventBus.getDefault().post(articleId);
    }


    @Override
    public void onLoadingOrRefreshFailed() {

    }

    /**
     * 发布评论\回复评论
     *
     * @param bundle
     * @param id
     */
    @Override
    public void publishComment(Bundle bundle, int id) {
        //弹出输入框,打开键盘
        editText_comment.requestFocus();
        CommonUtils.openKeyboard(getActivity());
        //articleId = bundle.getInt("ObjId");
        this.type = id;
        if (id == Constant.appFinal.replay) {
            commentsBean = (CommentsBean) bundle.getSerializable("commentsBean");
        }
    }

    /**
     * 滚动到最底部
     */
    private Runnable srcollRunnable = new Runnable() {
        @Override
        public void run() {
            scrollView.smoothScrollTo(0, scrollView.getChildAt(0).getHeight());
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(srcollRunnable);
    }

    @Override
    public void onShowToast(String message, int i) {

    }

    @Override
    public void navigateToFragment(BaseFragment baseFragment, Bundle bundle) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit_bt:
                String content = editText_comment.getText().toString().trim();
                if (content != null)
                    mPresenter.IssueOrReplyDo(CommonUtils.getContext(), type, articleId, commentsBean, content);
                else
                    ToastUtlis.showToastInUIThread("内容不能为空");
                break;
        }
    }
}
