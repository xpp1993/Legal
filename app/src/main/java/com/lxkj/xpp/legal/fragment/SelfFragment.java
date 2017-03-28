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
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import qiu.niorgai.StatusBarCompat;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 熊萍萍 on 2017/2/27/027.
 * 个人信息页面
 */

public class SelfFragment extends BaseFragment<IPresenter> implements IView {
    @BindView(R.id.geren_nickname)
    EditText editText_nick;
    @BindView(R.id.geren_age)
    EditText editText_age;
    @BindView(R.id.geren_sex)
    TextView textView_sex;
    @BindView(R.id.geren_version)
    TextView textView_version;
    @BindView(R.id.geren_phone)
    TextView textView_phone;
    @BindView(R.id.geren_head_iv)
    public CircleImageView circleImageView;
    @BindView(R.id.fragment_self_save_change)
    TextView textView_save;
    @BindView(R.id.geren_title_bar)
    EaseTitleBar easeTitleBar;
    private static final int REQUEST_CODE_PICK_IMAGE = 0x1;//相册
    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 0x2;//相机
    private static final int REQUEST_CODE_CAPTURE_CAMEIA_CROP = 0x3;//相机裁剪
    private File tempImageFile;//相机拍摄图片缓存
    private File headImageFile;//剪切图片缓存
    private static final String TAG = SelfFragment.class.getSimpleName();

    @Override
    protected void initWidgets() {

    }

    @Override
    protected void initData() {
        StatusBarCompat.setStatusBarColor(getActivity(), ContextCompat.getColor(CommonUtils.getContext(), R.color.colorPrimaryDark));
        mPresenter.getUserInfo();//初始化界面数据
        editText_nick.requestFocus();
        easeTitleBar.getLeftLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    protected IPresenter setPresenter() {
        return new IPresenter(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_geren;
    }

    @OnClick({R.id.geren_head_iv, R.id.fragment_self_save_change, R.id.geren_sex})
    void click(View view) {
        switch (view.getId()) {
            case R.id.geren_head_iv://修改头像
                changeHeadIcon();
                break;
            case R.id.fragment_self_save_change://保存修改
                mPresenter.updateUserInfo(CommonUtils.getContext(), editText_age, editText_nick, textView_sex, headImageFile);
                break;
            case R.id.geren_sex://修改性别
                showSetSexDialog(getActivity());
                break;
            default:
                break;
        }
    }

    /**
     * 显示性别选择框
     *
     * @param activity
     */
    private void showSetSexDialog(FragmentActivity activity) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("选择性别");
        View inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_radiogroup_sex, null);
        final RadioButton womanRadioButton = (RadioButton) inflate.findViewById(R.id.dialog_radiogroup_sex_woman);
        RadioButton manRadioButton = (RadioButton) inflate.findViewById(R.id.dialog_radiogroup_sex_man);
        womanRadioButton.setChecked(TextUtils.equals(textView_sex.getText(), "女"));
        manRadioButton.setChecked(TextUtils.equals(textView_sex.getText(), "男"));
        builder.setView(inflate);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String newGender = womanRadioButton.isChecked() ? "女" : "男";
                textView_sex.setText(newGender);
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
                    circleImageView.setImageURI(Uri.fromFile(headImageFile));
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

    @Override
    public void onShowToast(String message, int i) {
        ToastUtlis.showToastInUIThread(message);
    }

    @Override
    public void navigateToFragment(BaseFragment baseFragment, Bundle bundle) {

    }

    /**
     * 初始化页面的结果
     *
     * @param bundle
     * @param id
     */
    @Override
    public void changeDataForUi(Bundle bundle, int id) {
        if (id == Constant.ID.GET_SELF_INFO) {//获取个人信息
            if (bundle == null)
                return;
            DataEntity dataEntity = (DataEntity) bundle.getSerializable("dataEntity");
            String headImgurl = dataEntity.getHeadImg();
            String nickName = dataEntity.getNickName();
            String sexstr = dataEntity.getSexStr();
            String age = String.valueOf(dataEntity.getAge());
            String phone = dataEntity.getPhone();
            String version = dataEntity.getUid();
            if (headImgurl != null) {
                Glide.with(CommonUtils.getContext()).load(headImgurl).into(circleImageView);
            }
            if (nickName != null) {
                editText_nick.setHint(new SpannableString(nickName));
            }
            if (sexstr != null) {
                textView_sex.setText(sexstr);
            }
            if (age != null && !"0".equals(age)) {
                editText_age.setHint(new SpannableString(age));
            }
            if (phone != null) {
                textView_phone.setText(phone);
            }
            if (version != null) {
                textView_version.setText(version);
            }
        }
    }
}
