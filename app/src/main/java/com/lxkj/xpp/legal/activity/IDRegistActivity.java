package com.lxkj.xpp.legal.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseActivity;
import com.lxkj.xpp.legal.base.BaseMvpPresenter;
import com.lxkj.xpp.legal.utils.CommonUtils;

import java.io.File;
import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 熊萍萍 on 2017/1/6/006.
 * 身份证实名注册页面
 * 填写的信息保存到sharedpreferences文件
 */

public class IDRegistActivity extends BaseActivity {
    @BindView(R.id.status_bar)
    public LinearLayout id_status;
    @BindView(R.id.bar_ll_left)
    public LinearLayout back_image;
    @BindView(R.id.bar_tv_title_center)
    public TextView title;
    @BindView(R.id.id_name_tv)
    public TextView id_name;
    @BindView(R.id.id_id_tv)
    public TextView id_id;
    @BindView(R.id.id_sex_tv)
    public TextView id_sex;
    @BindView(R.id.id_age_ll)
    public LinearLayout id_age;
    @BindView(R.id.id_age_show_tv)
    public TextView id_age_show;
    @BindView(R.id.id_age_tv)
    public TextView id_age_tv;
    @BindView(R.id.registID_image)
    public ImageView registId_Image;
    private static final int REQUEST_CODE_PICK_IMAGE = 0x1;//相册
    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 0x2;//相机
    private static final int REQUEST_CODE_CAPTURE_CAMEIA_CROP = 0x3;//相机裁剪
    private File tempImageFile;//相机拍摄图片缓存
    private File headImageFile;//剪切图片缓存
    private static final String TAG = IDRegistActivity.class.getSimpleName();

    @Override
    protected void initWidgets(Bundle savedInstanceState) {
        super.initWidgets(savedInstanceState);
        /**
         * 改变状态栏的颜色
         */
        initState(id_status);
        title.setText("实名信息");
        back_image.setVisibility(View.VISIBLE);
    }

    @Override
    protected int setContentViewResId() {
        return R.layout.activity_id_regist;
    }

    @Override
    protected BaseMvpPresenter setPresenter() {
        return null;
    }

    @OnClick({R.id.bar_ll_left, R.id.id_name_tv, R.id.id_id_tv, R.id.id_age_ll, R.id.id_sex_tv, R.id.registID_image})
    void click(View view) {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(IDRegistActivity.this);
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.dialog_textview_name, null);
        final EditText editText = (EditText) layout.findViewById(R.id.dialog_username_tv);
        View.OnClickListener listener;
        switch (view.getId()) {
            case R.id.bar_ll_left:  //返回注册页面
                finish();
                break;
            case R.id.id_name_tv://填写姓名
                editText.setText(id_name.getText().toString().trim());
                editText.requestFocus();//获得焦点
                editText.setSelection(id_name.getText().toString().trim().length());//将光标移到末尾
                listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userName = editText.getText().toString().trim();
                        if (TextUtils.isEmpty(userName)) {
                            Toast.makeText(IDRegistActivity.this, "内容不能为空！", Toast.LENGTH_SHORT).show();
                        } else {
                            id_name.setText(userName);
                            dialogBuilder.dismiss();
                            //把姓名本地保存
                        }
                    }
                };
                setDialogBuilder(dialogBuilder, "请输入姓名", Effectstype.Slideright, layout, listener);
                break;
            case R.id.id_id_tv://填写身份证号
                editText.setText(id_id.getText().toString().trim());
                editText.requestFocus();//获得焦点
                editText.setSelection(id_id.getText().toString().trim().length());//将光标移到末尾
                listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id = editText.getText().toString().trim();
                        if (TextUtils.isEmpty(id)) {
                            Toast.makeText(IDRegistActivity.this, "内容不能为空！", Toast.LENGTH_SHORT).show();
                        } else {
                            dialogBuilder.dismiss();
                            id_id.setText(id);
                            //身份证号本地保存
                        }
                    }
                };
                setDialogBuilder(dialogBuilder, "请输入身份证号码", Effectstype.Slideright, layout, listener);
                break;
            case R.id.id_age_ll://填写年龄
                LinearLayout picker_view = (LinearLayout) inflater.inflate(R.layout.dialog_numberpicker, null);
                final NumberPicker numberPicker = (NumberPicker) picker_view.findViewById(R.id.number_picker);
                numberPicker.setMaxValue(100);
                numberPicker.setMinValue(0);
                String age = id_age_show.getText().toString().trim();
                if (!TextUtils.isEmpty(age)) {
                    numberPicker.setValue(Integer.parseInt(age));
                }
                numberPicker.setFocusable(true);
                numberPicker.setFocusableInTouchMode(true);
                setNumberPickerDividerColor(numberPicker);//设置分割线的颜色
                listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(IDRegistActivity.this, "" + numberPicker.getValue(), Toast.LENGTH_SHORT).show();
                        dialogBuilder.dismiss();
                        id_age_show.setText(numberPicker.getValue() + "");
                        id_age_tv.setText("岁");
                        //年龄本地保存到sp文件
                    }
                };
                setDialogBuilder(dialogBuilder, null, Effectstype.Slideright, picker_view, listener);
                break;
            case R.id.id_sex_tv://填写性别
                LinearLayout layout_sex = (LinearLayout) inflater.inflate(R.layout.dialog_radiogroup_sex, null);
                RadioButton radioButton_women = (RadioButton) layout_sex.findViewById(R.id.dialog_radiogroup_sex_woman);
                final RadioButton radioButton_men = (RadioButton) layout_sex.findViewById(R.id.dialog_radiogroup_sex_man);
                radioButton_women.setChecked(TextUtils.equals(id_sex.getText(), "女"));
                radioButton_men.setChecked(!TextUtils.equals(id_sex.getText(), "女"));
                listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                        String newGender = radioButton_men.isChecked() ? "男" : "女";
                        if (!TextUtils.equals(newGender, id_sex.getText().toString().trim())) {
                            id_sex.setText(newGender);
                            //性别保存到sp文件
                        }

                    }
                };
                setDialogBuilder(dialogBuilder, "请选择性别", Effectstype.Slideright, layout_sex, listener);
                break;
            case R.id.registID_image://上传身份证照片
                LinearLayout layout_image = (LinearLayout) inflater.inflate(R.layout.dialog_radiogroup_image, null);
                final RadioButton radioButton_carema = (RadioButton) layout_image.findViewById(R.id.dialog_radiogroup_camera);
                listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                        if (radioButton_carema.isChecked()) {//相机
                            IDRegistActivity.this.getImageFromCamera();
                        } else {//相册
                            IDRegistActivity.this.getImageFromAlbum();
                        }

                    }
                };
                setDialogBuilder(dialogBuilder, "请选择图片", Effectstype.SlideBottom, layout_image, listener);
                break;
            default:
                break;
        }
    }

    /**
     * 自定义滚动框分隔线颜色
     */
    private void setNumberPickerDividerColor(NumberPicker number) {
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    //设置分割线的颜色值
                    pf.set(number, new ColorDrawable(ContextCompat.getColor(this, R.color.color_white)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    /**
     * 获得经过设置的NiftyDialogBuilder
     *
     * @param dialogBuilder
     * @param msg           内容msg
     * @param effectstype   弹出style
     * @param view          view
     * @param listener      确定按钮的listener
     * @return
     */
    private void setDialogBuilder(final NiftyDialogBuilder dialogBuilder, String msg, Effectstype effectstype, View view, View.OnClickListener listener) {
        dialogBuilder.withIcon(ContextCompat.getDrawable(this, R.drawable.tishi))
                .withTitle("完善个人资料")
                .withTitleColor(ContextCompat.getColor(this, R.color.color_white))
                .withDividerColor(ContextCompat.getColor(this, R.color.color_white))
                .withMessage(msg)
                .withMessageColor(ContextCompat.getColor(this, R.color.color_white))
                .withDialogColor(ContextCompat.getColor(this, R.color.bar2))
                .withDuration(500)
                .withEffect(effectstype)
                .isCancelableOnTouchOutside(false)
                .setCustomView(view, this)
                .withButton1Text("确定")
                .withButton2Text("取消")
                .setButton1Click(listener)
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
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
            Toast.makeText(this, "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
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
                    registId_Image.setImageURI(Uri.fromFile(headImageFile));
                } catch (Exception e) {
                    Log.e(TAG, "onActivityResult:" + e.getMessage());
                }
            } else {
                Log.v(TAG, "onActivityResult:请求图片从剪切器没有返回");
            }
            if (tempImageFile != null && tempImageFile.exists()) {
                tempImageFile.delete();
            }
            //上传base64字串（头像）
        }
    }
}
