package com.lxkj.xpp.legal.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ccwant.photo.selector.activity.CCwantPhotoBrowserActivity;
import com.ccwant.photo.selector.activity.CCwantSelectAlbumActivity;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.adapter.PublishCircleAdapter;
import com.lxkj.xpp.legal.base.BaseFragment;
import com.lxkj.xpp.legal.presenter.CirclePresenter;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.utils.DisplayUtils;
import com.lxkj.xpp.legal.utils.ToastUtlis;
import com.lxkj.xpp.legal.view.CircleView;
import com.lxkj.xpp.legal.widget.MyGrideView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 熊萍萍 on 2017/3/24/024.
 */

public class PublishCircleFragment extends BaseFragment<CirclePresenter> implements CircleView, AdapterView.OnItemClickListener {
    @BindView(R.id.publish_img_back)
    ImageView imageView_back;
    @BindView(R.id.publish_circle_commit)
    TextView textView_commit;
    @BindView(R.id.publish_content_et)
    EditText editText_content;
    @BindView(R.id.publish_circle_empowerWay)
    LinearLayout linearLayout_empower;
    @BindView(R.id.publish_circle_empowerWay_tv)
    TextView textView_empower;
    private static final int OPEN_SELECT_ALBUM_ACTIVITY = 0x1; // 打开相册选择activity
    private static final int OPEN_CAMERA_ACTIVITY = 0x2; //打开相机activity
    private static final int OPEN_PHOTO_BROWSER_ACTIVITY = 0x4; //打开图片浏览器activity
    private File tempImageFile;//相机拍摄图片缓存
    private File headImageFile;//剪切图片缓存
    private static final String TAG = CircleFragment.class.getSimpleName();
    private PublishCircleAdapter mAdapter;
    private List<String> issueImages = new ArrayList<>();
    @BindView(R.id.publish_circle_gv)
    MyGrideView mGrvContent;

    @Override
    protected void initWidgets() {

    }

    @Override
    protected void initData() {
        mAdapter = new PublishCircleAdapter(CommonUtils.getContext(), issueImages);
        mAdapter.setMaxPicNum(6);//最多发布6张图片

        mGrvContent.setAdapter(mAdapter);
        mGrvContent.setOnItemClickListener(this);
    }

    @Override
    protected CirclePresenter setPresenter() {
        return new CirclePresenter(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_publish_circle;
    }

    @Override
    public void onShowToast(String message, int i) {
        ToastUtlis.showToastInUIThread(message);
    }

    @Override
    public void navigateToFragment(BaseFragment baseFragment, Bundle bundle) {

    }

    @OnClick({R.id.publish_img_back, R.id.publish_circle_commit, R.id.publish_circle_empowerWay})
    void click(View view) {
        switch (view.getId()) {
            case R.id.publish_img_back:
                getActivity().onBackPressed();
                break;
            case R.id.publish_circle_commit:
                String empowerStr = textView_empower.getText().toString().trim();

                mPresenter.publishCircle(CommonUtils.getContext(), editText_content, empowerStr, issueImages);
                break;
            case R.id.publish_circle_empowerWay:
                showDialog(getActivity());
                break;
        }
    }

    /**
     * 显示对谁公开选择框
     *
     * @param activity
     */
    private void showDialog(FragmentActivity activity) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("对谁公开");
        View inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_radiogroup_sex, null);
        final RadioButton radioButton1 = (RadioButton) inflate.findViewById(R.id.dialog_radiogroup_sex_woman);
        RadioButton radioButton2 = (RadioButton) inflate.findViewById(R.id.dialog_radiogroup_sex_man);
        radioButton1.setText("公开可见");
        radioButton2.setText("好友可见");
        radioButton1.setChecked(TextUtils.equals(textView_empower.getText(), "公开可见"));
        radioButton2.setChecked(TextUtils.equals(textView_empower.getText(), "好友可见"));
        builder.setView(inflate);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String newGender = radioButton1.isChecked() ? "公开可见" : "好友可见";
                textView_empower.setText(newGender);
            }
        });
        builder.setNegativeButton("放弃", null);
        builder.setCancelable(false).create().show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OPEN_SELECT_ALBUM_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                List<String> list = new ArrayList<String>();
                list.addAll(issueImages);
                list.addAll(bundle.getStringArrayList("result"));
                issueImages.clear();
                issueImages.addAll(list);
                mAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == OPEN_CAMERA_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                List<String> list = new ArrayList<String>();
                list.addAll(issueImages);
                list.add(tempImageFile.getAbsolutePath());
                issueImages.clear();
                issueImages.addAll(list);
                mAdapter.notifyDataSetChanged();
            } else {
                Log.v("RegistActivity", "onActivityResult:请求图片从相机没有返回");
            }
        } else if (requestCode == OPEN_PHOTO_BROWSER_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                List<String> list = bundle.getStringArrayList("result");
                issueImages.clear();
                issueImages.addAll(list);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void updataUI(Bundle bundle, int id) {
        getActivity().onBackPressed();
    }

    @Override
    public void onLoadingOrRefreshFailed() {

    }

    @Override
    public void publishComment(Bundle bundle, int id) {

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        {
            //如果点击了+则打开菜单，询问进入相机还是相册
            if (mAdapter.isAddPic(position)) {
                View menuView = LayoutInflater.from(getActivity()).inflate(R.layout.pub_choose_pic_menu, null);
                final PopupWindow popupWindow = new PopupWindow(menuView, FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {

                    }
                });
                popupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, DisplayUtils.dip2px(CommonUtils.getContext(), 8));
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                            ToastUtlis.showToastInUIThread("请确认已经插入SD卡");
                        } else {
                            switch (v.getId()) {
                                case R.id.pub_choose_pic_menu_cancel:

                                    break;
                                case R.id.pub_choose_pic_menu_camera:
                                    Intent getImageByCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    tempImageFile = CommonUtils.getTempFile(".jpg");
                                    getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempImageFile));//将相机拍摄的照片缓存人指定的路径
                                    startActivityForResult(getImageByCamera, OPEN_CAMERA_ACTIVITY);
                                    break;
                                case R.id.pub_choose_pic_menu_album:
                                    Intent intent = new Intent(getActivity(), CCwantSelectAlbumActivity.class);
                                    intent.putExtra("remainNum", 6 - issueImages.size());
                                    startActivityForResult(intent, OPEN_SELECT_ALBUM_ACTIVITY);
                                    break;
                            }
                        }
                        popupWindow.dismiss();
                    }
                };

                menuView.findViewById(R.id.pub_choose_pic_menu_camera).setOnClickListener(listener);
                menuView.findViewById(R.id.pub_choose_pic_menu_album).setOnClickListener(listener);
                menuView.findViewById(R.id.pub_choose_pic_menu_cancel).setOnClickListener(listener);

            } else {//点击了图片，进入图片浏览界面
                Intent intent = new Intent(getActivity(), CCwantPhotoBrowserActivity.class);
                intent.putExtra("CCwantPhotoList", (Serializable) issueImages);
                intent.putExtra("CCwantPhotoPosition", position);
                startActivityForResult(intent, OPEN_PHOTO_BROWSER_ACTIVITY);
            }
        }
    }

    @Override
    public void onDestroy() {
        issueImages.clear();
        super.onDestroy();
    }

}
