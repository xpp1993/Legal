package com.lxkj.xpp.legal.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.widget.EaseTitleBar;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseFragment;
import com.lxkj.xpp.legal.presenter.IPresenter;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.utils.ToastUtlis;
import com.lxkj.xpp.legal.view.IView;

import java.io.File;

import butterknife.BindView;
import qiu.niorgai.StatusBarCompat;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 熊萍萍 on 2017/2/27/027.
 * 意见反馈页面
 */

public class FeedBackFragment extends BaseFragment<IPresenter> implements IView, View.OnClickListener {
    @BindView(R.id.feedback_title_bar)
    EaseTitleBar easeTitleBar;
    @BindView(R.id.feedback_phone)
    EditText editText_phone;
    @BindView(R.id.feedback_et)
    EditText editText_content;
    @BindView(R.id.feedback_image)
    ImageView imageView;
    @BindView(R.id.fragment_feedback_commit)
    TextView commit;
    private static final int REQUEST_CODE_PICK_IMAGE = 0x1;//相册
    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 0x2;//相机
    private static final int REQUEST_CODE_CAPTURE_CAMEIA_CROP = 0x3;//相机裁剪
    private File tempImageFile;//相机拍摄图片缓存
    private File headImageFile;//剪切图片缓存
    private static final String TAG = FeedBackFragment.class.getSimpleName();

    @Override
    protected void initWidgets() {

    }

    @Override
    protected void initData() {
        StatusBarCompat.setStatusBarColor(getActivity(), ContextCompat.getColor(CommonUtils.getContext(), R.color.colorPrimaryDark));
        editText_phone.requestFocus();
        easeTitleBar.getLeftLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        commit.setOnClickListener(this);
        imageView.setOnClickListener(this);
    }

    @Override
    protected IPresenter setPresenter() {
        return new IPresenter(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_feedback;
    }


    @Override
    public void onShowToast(String message, int i) {
        ToastUtlis.showToastInUIThread(message);
    }

    @Override
    public void navigateToFragment(BaseFragment baseFragment, Bundle bundle) {

    }

    @Override
    public void changeDataForUi(Bundle bundle, int id) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.feedback_image://相片
                changeHeadIcon();
                break;
            case R.id.fragment_feedback_commit://提交
                mPresenter.feedBack(CommonUtils.getContext(), editText_content, editText_phone, headImageFile);
                break;
        }
    }

    /**
     * 设置头像
     */
    private void changeHeadIcon() {
        final CharSequence[] items = {"相册", "拍照"};
        AlertDialog dlg = new AlertDialog.Builder(getActivity())
                .setTitle("选择图片")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // 这里item是根据选择的方式，
                        if (item == 0) {
                            getImageFromAlbum();
                        } else {
                            getImageFromCamera();
                        }
                    }
                }).create();
        dlg.show();
    }

    /**
     * 从相册获取图片
     */
    private void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    /**
     * 从相机获取图片
     */
    private void getImageFromCamera() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Intent getImageByCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            tempImageFile = CommonUtils.getTempFile(".jpg");
            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempImageFile));//将相机拍摄的照片缓存人指定的路径
            startActivityForResult(getImageByCamera, REQUEST_CODE_CAPTURE_CAMEIA);
        } else {
            Log.v(getClass().getName(), "getImageFromCamera:不存在SD卡");
            ToastUtlis.showToastInUIThread("请确认已经插入SD卡");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_IMAGE) {//相册
            if (resultCode == RESULT_OK) {
                Intent photoZoomIntent = CommonUtils.getPhotoZoomIntent(data.getData());
                headImageFile = CommonUtils.getTempFile(".jpg");
                photoZoomIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(headImageFile));//将截取器截取的图片缓存人指定的路径
                startActivityForResult(photoZoomIntent, REQUEST_CODE_CAPTURE_CAMEIA_CROP);
            } else {
                Log.v(TAG, "onActivityResult:请求图片从相册没有返回");
            }
        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
            if (resultCode == RESULT_OK) {
                Intent photoZoomIntent = CommonUtils.getPhotoZoomIntent(Uri.fromFile(tempImageFile));
                headImageFile = CommonUtils.getTempFile(".jpg");
                photoZoomIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(headImageFile));//将截取器截取的图片缓存人指定的路径
                startActivityForResult(photoZoomIntent, REQUEST_CODE_CAPTURE_CAMEIA_CROP);
            } else {
                Log.v(TAG, "onActivityResult:请求图片从相机没有返回");
            }
        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA_CROP) {
            if (resultCode == RESULT_OK) {
                Log.v(TAG, "onActivityResult:请求图片从从剪切器返回成功");
                try {
                    imageView.setImageURI(Uri.fromFile(headImageFile));
                } catch (Exception e) {
                    Log.e(TAG, "onActivityResult:" + e.getMessage());
                }
            } else {
                Log.v(TAG, "onActivityResult:请求图片从剪切器没有返回");
            }
            if (tempImageFile != null && tempImageFile.exists()) {
                tempImageFile.delete();
            }
        }
    }
}
