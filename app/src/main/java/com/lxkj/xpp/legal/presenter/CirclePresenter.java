package com.lxkj.xpp.legal.presenter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lxkj.xpp.legal.base.BaseMvpPresenter;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.listener.CirCleCallBackListener;
import com.lxkj.xpp.legal.listener.OnCommentEntityClickListener;
import com.lxkj.xpp.legal.model.CircleModel;
import com.lxkj.xpp.legal.model.bean.CircleDetailBean;
import com.lxkj.xpp.legal.model.bean.CircleListBean;
import com.lxkj.xpp.legal.model.bean.CommentsBean;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.utils.ToastUtlis;
import com.lxkj.xpp.legal.view.CircleView;
import com.lxkj.xpp.legal.widget.MessageGroup;
import com.lxkj.xpp.legal.widget.adaptivedgridview.MyGridLayoutCallBack;
import com.lxkj.xpp.legal.widget.adaptivedgridview.MyGridlayout;
import com.lxkj.xpp.legal.widget.adaptivedgridview.SmartDecorator;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 熊萍萍 on 2017/3/24/024.
 */

public class CirclePresenter extends BaseMvpPresenter<CircleView, CircleModel> implements CirCleCallBackListener<Object> {
    private Context context;

    /**
     * 在构造方法里关联view设置model
     *
     * @param mView
     */
    public CirclePresenter(CircleView mView) {
        super(mView);
    }

    @Override
    protected CircleModel setModel() {
        return new CircleModel();
    }

    @Override
    public void onFail(String error, int i) {

    }

    @Override
    public void onSuccess(Object response, Bundle bundle, int id) {
        if (response == null)
            return;
        String msg = null;
        String code = null;
        boolean isSuccessFull = false;
        CircleListBean circleListBean = null;
        CircleDetailBean circleDetailBean = null;
        //1.弹出Toast
        if (response instanceof CircleListBean) {
            circleListBean = (CircleListBean) response;
            msg = circleListBean.getMsg();
            code = circleListBean.getCode();
            isSuccessFull = circleListBean.isSuccess();
            Log.e("CirclePresenter", circleListBean.toString());
        } else if (response instanceof CircleDetailBean) {
            circleDetailBean = (CircleDetailBean) response;
            msg = circleDetailBean.getMsg();
            code = circleDetailBean.getCode();
            isSuccessFull = circleDetailBean.isSuccess();
            Log.e("CirclePresenter", circleDetailBean.toString());
        }

        Log.e("iPresenter", "msg=" + msg + ",code=" + code + ",isSuccessFull=" + isSuccessFull);
        // mView.onShowToast("msg=" + msg + ",code=" + code, id);
        switch (id) {
            case Constant.ID.PULISH_CICLE://用户反馈
                if (isSuccessFull) {
                    if (mView != null) {
                        mView.onShowToast("发布成功", Constant.ID.PULISH_CICLE);
                        mView.updataUI(null, Constant.ID.PULISH_CICLE);
                    }
                }
                break;
            case Constant.ID.LOAD_CIRCLE://加载帖书列表
                if (isSuccessFull) {
                    if (mView != null) {
                        mView.onShowToast("加载完成", Constant.ID.LOAD_CIRCLE);
                        Bundle circleListData = new Bundle();
                        circleListData.putSerializable("circleListBean", circleListBean);
                        if (bundle != null) {
                            circleListData.putInt("code", bundle.getInt("code"));
                            Log.e("iPresenter", "code=" + bundle.getInt("code"));
                        }
                        mView.updataUI(circleListData, Constant.ID.LOAD_CIRCLE);
                    }
                } else {
                    mView.onLoadingOrRefreshFailed();
                }
                break;
            case Constant.appFinal.issue://评论
                if (isSuccessFull) {
                    ToastUtlis.showToastInUIThread("评论成功");
                    //在请求一遍帖数详情
                    int articleId = bundle.getInt("articleId");
                    loadPublishDetail(context, articleId);
                } else {
                    ToastUtlis.showToastInUIThread(msg);
                }
                break;
            case Constant.appFinal.replay://回复
                if (isSuccessFull) {
                    ToastUtlis.showToastInUIThread("回复成功");
                    //在请求一遍帖数详情
                    int articleId = bundle.getInt("articleId");
                    loadPublishDetail(context, articleId);
                } else {
                    ToastUtlis.showToastInUIThread(msg);
                }
                break;
            case Constant.ID.PUBLIS_DETAIL://获取帖书详情页
                if (isSuccessFull) {
                    Bundle circleDetailBean_data = new Bundle();
                    circleDetailBean_data.putSerializable("circleDetailBean", circleDetailBean);
                    mView.updataUI(circleDetailBean_data, Constant.ID.PUBLIS_DETAIL);
                }
                break;
        }
    }

    /**
     * 发布帖书
     *
     * @param context
     * @param editText_content
     * @param empwerWayStr
     * @param issueImages
     */
    public void publishCircle(Context context, EditText editText_content, String empwerWayStr, List<String> issueImages) {
        int empworWay = 1101;
        if ("公开可见".equals(empwerWayStr)) {
            empworWay = 1101;
        } else if ("好友可见".equals(empwerWayStr)) {
            empworWay = 1102;
        } else if ("仅自己可见".equals(empwerWayStr)) {
            empworWay = 1103;
        }
        //将图片文件转化为数组后转为字符串
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < issueImages.size(); i++) {
            if (issueImages.get(i) == null)
                continue;
            File file = CommonUtils.scal(Uri.parse(issueImages.get(i)));
            jsonArray.put(new String(Base64.encodeToString(changeFileToByte(file), Base64.DEFAULT)));
        }
//        for (int i = 0; i < headFiles.length; i++) {
//            if (headFiles[i] == null)
//                continue;
//            jsonArray.put(new String(Base64.encodeToString(changeFileToByte(headFiles[i]), Base64.DEFAULT)));
//        }
        try {
            mModel.publishCircle(context, editText_content, empworWay, jsonArray, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载帖书数据
     *
     * @param context
     * @param pageSize
     * @param code     0x01:上拉刷新，0x02下拉加载更多
     * @param pageNo
     */
    public void loadCircleData(Context context, int code, int pageSize, int pageNo) {
        this.context = context;
        try {
            mModel.loadCircleData(context, code, pageSize, pageNo, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取帖书详情页
     *
     * @param context
     * @param articleId
     */
    public void loadPublishDetail(Context context, int articleId) {
        try {
            mModel.getDetailPublish(context, articleId, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 评论、回复评论
     *
     * @param context
     * @param type
     * @param article
     * @param commentsBean
     * @param content
     */
    public void IssueOrReplyDo(Context context, int type, int article, CommentsBean commentsBean, String content) {
        try {
            mModel.IssueOrReplay(context, type, article, commentsBean, content, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置Circleitem的UI
     *
     * @param bundle
     * @param headCircleImageView
     * @param imageGridView
     * @param nicknameTextView
     * @param whatAgoTextView
     * @param messageTextView
     * @param commentCountTextView
     * @param messageGroup
     * @param maxImag
     */
    public void setCircleDetailUI(Bundle bundle, CircleImageView headCircleImageView, MyGridlayout imageGridView, TextView nicknameTextView,
                                  TextView whatAgoTextView, TextView messageTextView, ImageView commentImageView, TextView commentCountTextView,
                                  MessageGroup messageGroup, int maxImag) {
        final CircleDetailBean circleDetailBean = (CircleDetailBean) bundle.getSerializable("circleDetailBean");
        if (circleDetailBean.getData().getUserHeadImg() == null || "empty".equals(circleDetailBean.getData().getUserHeadImg())) {
        } else {
            Glide.with(CommonUtils.getContext()).load(circleDetailBean.getData().getUserHeadImg()).into(headCircleImageView);//头像
        }
        if (circleDetailBean.getData().getImg() != null && circleDetailBean.getData().getImg().size() > 0) {//发表的图片
            imageGridView.setMyGridLayoutCallBack(new MyGridLayoutCallBack() {
                @Override
                public void onClick(Object res) {
                    ToastUtlis.showToastInUIThread("image onClick on ");
                }

                @Override
                public void onLongClick(Object res) {
                    ToastUtlis.showToastInUIThread("image onLongClick on ");
                }

                @Override
                public void inflateItem(ImageView imageView, Object res) {
                    Glide.with(CommonUtils.getContext()).load(res.toString()).thumbnail(0.1f).into(imageView);
                }
            });
            imageGridView.setVisibility(View.VISIBLE);
            imageGridView.setMaxItemCount(maxImag);//手动设置最多可以显示多少张图片
            imageGridView.setColumnNumber(3);
            imageGridView.setDecorator(new SmartDecorator());
            imageGridView.refresh(Arrays.asList(circleDetailBean.getData().getImg().toArray()));
        } else {
            imageGridView.setVisibility(View.GONE);
        }
        nicknameTextView.setText(circleDetailBean.getData().getUserNickName());//昵称
        whatAgoTextView.setText(circleDetailBean.getData().getPublishTime());//发布时间
        messageTextView.setText(circleDetailBean.getData().getContent());//内容
        List<CommentsBean> commentsBeen = circleDetailBean.getData().getComments();//评论
        commentCountTextView.setText((commentsBeen == null ? 0 : commentsBeen.size()) + " 评论");
        commentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//发表评论
                Bundle issue_bundle = new Bundle();
                issue_bundle.putInt("ObjId", circleDetailBean.getData().getObjId());
                mView.publishComment(issue_bundle, Constant.appFinal.issue);
                // IssueOrReplyDo(context, Constant.appFinal.issue, circleDetailBean.getData().getObjId(), null, "");
            }
        });
        if (commentsBeen != null && commentsBeen.size() > 0) {
            messageGroup.setOnEntityClickListener(new OnCommentEntityClickListener() {

                @Override
                public void onFromClicked(CommentsBean commentsBean) {
                   // Toast.makeText(context, "onFromClicked:" + commentsBean, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onTargetClicked(CommentsBean commentsBean) {
                    //Toast.makeText(context, "onTargetClicked:" + commentsBean, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onMessageClicked(CommentsBean commentsBean) {//回复评论
                    Bundle reply_bundle = new Bundle();
                    reply_bundle.putInt("ObjId", circleDetailBean.getData().getObjId());
                    reply_bundle.putSerializable("commentsBean", commentsBean);
                    mView.publishComment(reply_bundle, Constant.appFinal.replay);
                    //IssueOrReplyDo(context, Constant.appFinal.issue, circleDetailBean.getData().getObjId(), commentsBean, "");
                }

                @Override
                public void onMessageLongClicked(CommentsBean commentsBean) {
                    Toast.makeText(context, "onMessageLongClicked:" + commentsBean, Toast.LENGTH_SHORT).show();
                }
            });
            messageGroup.setVisibility(View.VISIBLE);
            messageGroup.setMaxLineNumber(circleDetailBean.getData().getCommentNum());//手动设置最多可以显示多少条评论
            messageGroup.refresh(commentsBeen);
        } else {
            messageGroup.setVisibility(View.GONE);
        }
    }

    /**
     * 将file转为数组
     *
     * @param file
     * @return
     */
    private byte[] changeFileToByte(File file) {
        byte[] buffer = null;
        try {

            if (file == null || !file.exists()) {
                return null;
            }
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;

            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
                bos.flush();
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    @Override
    public void onError(String msg, int i) {

    }
}
