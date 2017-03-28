package com.lxkj.xpp.legal.presenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.lxkj.xpp.legal.base.BaseMvpPresenter;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.listener.OnIdListener;
import com.lxkj.xpp.legal.model.IDModel;
import com.lxkj.xpp.legal.view.IDView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 熊萍萍 on 2016/12/23/023.
 * 实名认证
 */

public class IDPresenter extends BaseMvpPresenter<IDView, IDModel> implements OnIdListener {
    /**
     * 在构造方法里关联view设置model
     *
     * @param mView
     */
    public IDPresenter(IDView mView) {
        super(mView);
    }

    @Override
    protected IDModel setModel() {
        return new IDModel();
    }


    @Override
    public void onFail(String error, int i) {

    }

    @Override
    public void onSuccess(String message, Bundle bundle, int id) {
        if (message != null)
            Log.e("IdPresenter", message);
        try {
            JSONObject jsonObject = new JSONObject(message);
            boolean success = jsonObject.optBoolean("success");
            String msg = jsonObject.optString("msg");
            String data = jsonObject.optString("data");
            String code = jsonObject.optString("code");
            switch (id) {
                case Constant.ID.REALNAME://实名认证提交结果
                    if (mView != null)
                        mView.onShowToast(message + "," + code, id);
                    break;
                case Constant.ID.REAL_PAGE_CONSTANT://平台常量获取
                    if (success) {
                        if (mView != null) {
                            Bundle warn_data = new Bundle();
                            warn_data.putString("warn_data", data);
                            mView.changeDataForUi(warn_data, Constant.ID.REAL_PAGE_CONSTANT);
                        }
                    }
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onError(String msg, int i) {
        if (mView != null) {
            mView.onShowToast(msg, i);
        }
    }

    /**
     * 实名认证
     *
     * @param context
     * @param editText_name
     * @param editText_id
     */
    public void toReal(Context context, EditText editText_name, EditText editText_id) {
        try {
            mModel.realName(context, editText_name, editText_id, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 平台常量获取
     */
    public void getConstant() {
        mModel.getRealPageConstant(this);
    }
}
