package com.lxkj.xpp.legal.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseMvpPresenter;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.fragment.IDRegistFragment;
import com.lxkj.xpp.legal.fragment.LoginFragment;
import com.lxkj.xpp.legal.listener.OnRegisterListener;
import com.lxkj.xpp.legal.model.RegisterModel;
import com.lxkj.xpp.legal.model.bean.LoginMessage;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.view.RegisterView;

/**
 * Created by 熊萍萍 on 2017/3/7/007.
 */

public class RegisterPresenter extends BaseMvpPresenter<RegisterView, RegisterModel> implements OnRegisterListener {
    /**
     * 在构造方法里关联view设置model
     *
     * @param mView
     */
    public RegisterPresenter(RegisterView mView) {
        super(mView);
    }

    @Override
    protected RegisterModel setModel() {
        return new RegisterModel();
    }

    /**
     * 获取验证码
     *
     * @param phone_et
     * @param type
     */
    public void getCheckCode(Context context, EditText phone_et, int type) {
        if (mView != null) {
            mView.onShowProgress();
        }
        mModel.getCheckCode(context, phone_et, type, this);
    }

    /**
     * 注册
     *
     * @param phone_et
     * @param code_et
     * @param password_et
     * @param confirm_et
     */
    public void doRegister(Context context, EditText phone_et, EditText code_et, EditText password_et, EditText confirm_et) {
        if (mView != null) {
            mView.onShowProgress();
        }
        mModel.register(context, phone_et, code_et, password_et, confirm_et, this);
    }

    /**
     * 跳转到实名注册页面
     */
    public void toIdRegister() {
        if (mView != null) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constant.appFinal.TO_ID, Constant.appFinal.REGISTER_ID);
            mView.navigateToFragment(new IDRegistFragment(), bundle);
        }
    }

    /**
     * 跳转到实名注册页面
     */
    public void toLogin() {
        if (mView != null) {
            mView.navigateToFragment(new LoginFragment(), null);
        }
    }

    /**
     * 弹出是否实名注册的对话框
     *
     * @param builder
     * @param viewGroup
     */
    public void showDialog(NiftyDialogBuilder builder, ViewGroup viewGroup) {
        builder.withTitle("是否实名验证")
                .withTitleColor(ContextCompat.getColor(CommonUtils.getContext(), R.color.color_white))
                .withDividerColor(ContextCompat.getColor(CommonUtils.getContext(), R.color.color_white))
                .withMessage(null)
                .isCancelableOnTouchOutside(false)
                .withDuration(1000)
                .withEffect(Effectstype.Fliph)
                .withDialogColor(ContextCompat.getColor(CommonUtils.getContext(), R.color.bar2))
                .setCustomView(viewGroup, CommonUtils.getContext())
                .show();
    }


    @Override
    public void onFail(String error, int i) {
        if (mView != null) {
            mView.onHideProgress();
            mView.onShowToast(error, i);
        }
        Log.e("RegisterPresenter", error);
    }

    @Override
    public void onSuccess(LoginMessage loginMessage, Bundle bundle, int id) {
        if (mView != null) {
            mView.onHideProgress();
        }
        String msg = loginMessage.getMsg();
        String code = loginMessage.getCode();
        boolean success = loginMessage.isSuccess();
        String phone = null;
        String password = null;
        if (bundle != null) {
            phone = bundle.getString(Constant.appFinal.LOGIN_NAME_KEY);
            password = bundle.getString(Constant.appFinal.LOGIN_PASSWORD_KEY);
        }
        Log.e("RegisterPresenter", "message:" + msg + ",code:" + code);
        mView.onShowToast(msg, Constant.REGIST.toast_msg);
        switch (id) {
            case Constant.appFinal.flag_register://注册
                if (success) {
                    //1.将phone和密码持久化
                    if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password)) {
                        CommonUtils.getPreference().putString(CommonUtils.getContext(), Constant.appFinal.LOGIN_NAME_KEY, phone);
                        CommonUtils.getPreference().putString(CommonUtils.getContext(), Constant.appFinal.LOGIN_PASSWORD_KEY, password);
                    }
                    //2.跳转到登录页面
                    mView.navigateToFragment(new LoginFragment(), null);
                }
                break;
            case Constant.appFinal.flag_getcode://获取验证码

                break;
        }
    }

    @Override
    public void onError(String msg, int i) {
        if (mView != null) {
            mView.onHideProgress();
            mView.onShowToast(msg, i);
        }
    }
}
