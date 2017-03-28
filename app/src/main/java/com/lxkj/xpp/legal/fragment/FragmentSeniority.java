package com.lxkj.xpp.legal.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseFragment;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.model.bean.DataEntity;
import com.lxkj.xpp.legal.presenter.IPresenter;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.utils.ToastUtlis;
import com.lxkj.xpp.legal.view.IView;

import java.io.File;

import butterknife.BindView;
import qiu.niorgai.StatusBarCompat;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 熊萍萍 on 2017/3/22/022.
 * 资历认证页
 */

public class FragmentSeniority extends BaseFragment<IPresenter> implements IView, View.OnClickListener {
    @BindView(R.id.seniority_unit)
    EditText editText_unit;
    @BindView(R.id.seniority_duty)
    EditText editText_job;
    @BindView(R.id.seniority_title_bar)
    EaseTitleBar easeTitleBar;
    @BindView(R.id.seniority_image)
    ImageView imageView;
    @BindView(R.id.fragment_seniority_commit)
    TextView comit;
    @BindView(R.id.seniority_empowerWay)
    TextView seniority_empowerWay;
    @BindView(R.id.seniority_warn_text)
    TextView seniority_warn_text;
    private static final int REQUEST_CODE_PICK_IMAGE = 0x1;//相册
    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 0x2;//相机
    private static final int REQUEST_CODE_CAPTURE_CAMEIA_CROP = 0x3;//相机裁剪
    private File tempImageFile;//相机拍摄图片缓存
    private File headImageFile;//剪切图片缓存
    private static final String TAG = FragmentSeniority.class.getSimpleName();

    @Override
    protected void initWidgets() {
        StatusBarCompat.setStatusBarColor(getActivity(), ContextCompat.getColor(CommonUtils.getContext(), R.color.colorPrimaryDark));
        easeTitleBar.getLeftLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        imageView.setOnClickListener(this);
        comit.setOnClickListener(this);
        seniority_empowerWay.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mPresenter.getSeniority();
    }

    @Override
    protected IPresenter setPresenter() {
        return new IPresenter(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_seniority;
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
        if (bundle == null)
            return;
        switch (id) {
            case Constant.ID.GET_JOB_INFO://获取资历信息
                DataEntity dataEntity = (DataEntity) bundle.getSerializable("job_data");
                String unit = dataEntity.getWorkUnit();
                String job = dataEntity.getWorkJob();
                String quartersImg = dataEntity.getQuartersImg();
                int empowerWay = dataEntity.getEmpowerWay();
                String explain = dataEntity.getExplain();
                boolean isSubmit = dataEntity.isSubmit();
                editText_unit.setText(unit);
                editText_job.setText(job);
                if (quartersImg != null)
                    Glide.with(CommonUtils.getContext()).load(quartersImg).into(imageView);
                if (empowerWay == 1102) {
                    seniority_empowerWay.setText("好友可见");
                } else if (empowerWay == 1101) {
                    seniority_empowerWay.setText("公开可见");
                }
                if (isSubmit) {
                    comit.setText("重新上传");
                }
                seniority_warn_text.setText(explain);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.seniority_empowerWay://对谁公开
                //弹出对话框
                showDialog(getActivity());
                break;
            case R.id.seniority_image://提交照片
                changeHeadIcon();
                break;
            case R.id.fragment_seniority_commit://提交
                mPresenter.commitJobInfo(CommonUtils.getContext(), editText_unit, editText_job, headImageFile, seniority_empowerWay.getText().toString().trim());
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
        radioButton1.setChecked(TextUtils.equals(seniority_empowerWay.getText(), "公开可见"));
        radioButton2.setChecked(TextUtils.equals(seniority_empowerWay.getText(), "好友可见"));
        builder.setView(inflate);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String newGender = radioButton1.isChecked() ? "公开可见" : "好友可见";
                seniority_empowerWay.setText(newGender);
            }
        });
        builder.setNegativeButton("放弃", null);
        builder.setCancelable(false).create().show();
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
