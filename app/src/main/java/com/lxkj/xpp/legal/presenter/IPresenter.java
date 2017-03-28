package com.lxkj.xpp.legal.presenter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.lxkj.xpp.legal.base.BaseMvpPresenter;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.listener.MeCallBackListener;
import com.lxkj.xpp.legal.model.IModel;
import com.lxkj.xpp.legal.model.bean.DataEntity;
import com.lxkj.xpp.legal.model.bean.LoginMessage;
import com.lxkj.xpp.legal.view.IView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by 熊萍萍 on 2016/12/23/023.
 */

public class IPresenter extends BaseMvpPresenter<IView, IModel> implements MeCallBackListener {
    /**
     * 在构造方法里关联view设置model
     *
     * @param mView
     */
    public IPresenter(IView mView) {
        super(mView);
    }

    @Override
    protected IModel setModel() {
        return new IModel();
    }

    /**
     * 获得个人信息
     */
    public void getUserInfo() {
        mModel.getUserInfo(this);
    }

    /**
     * 更新个人信息
     *
     * @param editText_age
     * @param editText_nickName
     * @param textView_sex
     * @param headFile
     */
    public void updateUserInfo(Context contex, final EditText editText_age, EditText editText_nickName, TextView textView_sex, File headFile) {
        //年龄
        String ageStr = editText_age.getText().toString().trim();
        //昵称
        String nickName = editText_nickName.getText().toString().trim();
        //将图片文件转化为数组后转为字符串
        String photo = null;
        if (headFile != null) {
            byte[] buffer = changeFileToByte(headFile);
            byte[] encode = Base64.encode(buffer, Base64.DEFAULT);
            photo = new String(encode);
        }
        //性别数据
        String sexStr = textView_sex.getText().toString().trim();
        int sex = 0;
        if ("女".equals(sexStr)) {
            sex = 0;
        }
        if ("男".equals(sexStr)) {
            sex = 1;
        }
        mModel.upataUserInfo(contex, ageStr, nickName, sex, photo, this);
    }

    /**
     * 用户反馈
     *
     * @param context
     * @param editText_content
     * @param editText_phone
     * @param headFiles
     */
    public void feedBack(Context context, EditText editText_content, EditText editText_phone, File... headFiles) {
        //将图片文件转化为数组后转为字符串
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < headFiles.length; i++) {
            if (headFiles[i] == null)
                continue;
            jsonArray.put(new String(Base64.encodeToString(changeFileToByte(headFiles[i]), Base64.DEFAULT)));
        }
        try {
            mModel.commitFeedBack(context, editText_phone, editText_content, jsonArray, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getBase64(Bitmap file) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        file.compress(Bitmap.CompressFormat.PNG, 40, byteArrayOutputStream);
        //第二步:利用Base64将字节数组输出流中的数据转换成字符串String
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        try {
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(Base64.encodeToString(byteArray, Base64.DEFAULT));

    }

    /**
     * 获取资历信息
     */
    public void getSeniority() {
        mModel.getJobInfo(this);
    }

    @Override
    public void onFail(String error, int i) {

    }

    @Override
    public void onSuccess(LoginMessage loginMessage, Bundle bundle, int id) {
        if (loginMessage == null)
            return;
        Log.e("iPresenter", loginMessage.toString());
        //1.弹出Toast
        String msg = loginMessage.getMsg();
        String code = loginMessage.getCode();
        boolean isSuccessFull = loginMessage.isSuccess();
        Log.e("iPresenter", "msg=" + msg + ",code=" + code + ",isSuccessFull=" + isSuccessFull);
        DataEntity dataEntity = loginMessage.getData();
        switch (id) {
            case Constant.ID.GET_SELF_INFO://获得个人信息
                if (mView != null) {
                    mView.onShowToast(msg, Constant.REGIST.toast_msg);
                }
                if (isSuccessFull) {
                    if (dataEntity != null) {
                        Bundle data = new Bundle();
                        data.putSerializable("dataEntity", dataEntity);
                        if (mView != null) {
                            //显示编辑资料界面信息
                            mView.changeDataForUi(data, Constant.ID.GET_SELF_INFO);
                        }
                    }
                }
                break;
            case Constant.ID.UPDATA_SELF_INFO://编辑个人信息
                if (isSuccessFull) {
                    if (mView != null) {
                        mView.onShowToast("编辑个人信息成功", Constant.ID.UPDATA_SELF_INFO);
                    }
                }
                break;
            case Constant.ID.FEEDBACK://用户反馈
                if (isSuccessFull) {
                    if (mView != null) {
                        mView.onShowToast("反馈成功", Constant.ID.UPDATA_SELF_INFO);
                    }
                }
            case Constant.ID.GET_JOB_INFO://获得资历信息
                if (isSuccessFull) {
                    if (dataEntity != null) {
                        Bundle job_data = new Bundle();
                        job_data.putSerializable("job_data", dataEntity);
                        if (mView != null) {
                            //显示编辑资料界面信息
                            mView.changeDataForUi(job_data, Constant.ID.GET_JOB_INFO);
                        }
                    }
                }
                break;
            case Constant.ID.JOB_COMMIT://提交资历信息
                if (isSuccessFull) {
                    if (mView != null) {
                        mView.onShowToast("提交成功", Constant.ID.JOB_COMMIT);
                    }
                }else {
                    if (mView != null) {
                        mView.onShowToast("提交失败", Constant.ID.JOB_COMMIT);
                    }
                }
                break;
        }
    }

    /**
     * 资历认证
     *
     * @param context
     * @param editText_unit
     * @param editText_job
     * @param headFile
     * @param empwerWayStr  1101	公开可见
     *                      1102	好友可见
     */
    public void commitJobInfo(Context context, EditText editText_unit, EditText editText_job, File headFile, String empwerWayStr) {
        try {
            String headImg = null;
            if (headFile != null) {
                headImg = getBase64(BitmapFactory.decodeFile(headFile.getPath()));
            }
            int empworWay = 1101;
            if ("公开可见".equals(empwerWayStr)) {
                empworWay = 1101;
            } else if ("好友可见".equals(empwerWayStr)) {
                empworWay = 1102;
            }
            mModel.CommitJobInfo(context, editText_unit, editText_job, headImg, empworWay, this);
        } catch (JSONException e) {
            e.printStackTrace();
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
        if (mView != null) {
            mView.onShowToast(msg, i);
        }
    }
}
