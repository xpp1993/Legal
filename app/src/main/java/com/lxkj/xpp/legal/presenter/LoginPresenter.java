package com.lxkj.xpp.legal.presenter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseMvpPresenter;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.fragment.ChangePhoneNextFragment;
import com.lxkj.xpp.legal.fragment.LoginFragment;
import com.lxkj.xpp.legal.fragment.MainTabFragment;
import com.lxkj.xpp.legal.fragment.RegistFragment;
import com.lxkj.xpp.legal.fragment.ShezhiFragment;
import com.lxkj.xpp.legal.model.LoginModel;
import com.lxkj.xpp.legal.model.bean.DataEntity;
import com.lxkj.xpp.legal.model.bean.LoginMessage;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.utils.ThreadUtils;
import com.lxkj.xpp.legal.utils.okhttp.Md5Utils;
import com.lxkj.xpp.legal.view.LoginView;

/**
 * Created by 熊萍萍 on 2017/3/6/006.
 */

public class LoginPresenter extends BaseMvpPresenter<LoginView, LoginModel> implements LoginModel.OnLoginListener {
    private Context context;

    /**
     * 在构造方法里关联view设置model
     *
     * @param mView
     */
    public LoginPresenter(LoginView mView) {
        super(mView);
    }

    @Override
    protected LoginModel setModel() {
        return new LoginModel();
    }

    /**
     * 密码登录
     *
     * @param context
     * @param un_et
     * @param pw_et
     */
    public void doLogin(Context context, EditText un_et, EditText pw_et) {
        this.context = context;
        if (mView != null) {
            mView.showProgress();
        }
        mModel.login(context, un_et, pw_et, this);
    }

    /**
     * 验证码登录
     *
     * @param context
     * @param phone_et
     * @param code_et
     */
    public void fastLogin(Context context, EditText phone_et, EditText code_et) {
        this.context = context;
        if (mView != null) {
            mView.showProgress();
        }
        mModel.fastLogin(context, phone_et, code_et, this);
    }

    /**
     * 获取验证码
     *
     * @param phone_et
     * @param type
     */
    public void getCheckCode(Context context, EditText phone_et, int type) {
        if (mView != null) {
            mView.showProgress();
        }
        // mModel.getCheckCode(context, phone_et, type, this);
        CommonUtils.getCheckCode(context, phone_et, type, this);
    }

    /**
     * 直接通过手机号获取验证码
     *
     * @param context
     * @param phone
     * @param type
     */
    public void getCode(Context context, String phone, String type) {
        CommonUtils.netForCheckCode(this, phone, type);
    }

    /**
     * 注册
     *
     * @param phone_et
     * @param code_et
     * @param password_et
     */
    public void doRegister(Context context, EditText phone_et, EditText code_et, EditText password_et) {
        if (mView != null) {
            mView.showProgress();
        }
        mModel.register(context, phone_et, code_et, password_et, this);
    }

    /**
     * 跳转到注册页面
     */
    public void toRegister() {
        if (mView != null) {
            mView.navigateToFragment(new RegistFragment(), null);
        }
    }

    /**
     * 跳转到密码登录页面
     */
    public void toCommLogin() {
        if (mView != null) {
            mView.navigateToFragment(new LoginFragment(), null);
        }
    }

    /**
     * 返回设置页
     */
    public void backSet() {
        if (mView != null) {
            mView.navigateToFragment(new ShezhiFragment(), null);
        }
    }

    /**
     * 重置密码
     *
     * @param context
     * @param resetPassword
     * @param confirm_password
     */
    public void resetPassword(Context context, EditText resetPassword, EditText confirm_password) {
        mModel.resetPassword(context, resetPassword, confirm_password, this);
    }

    /**
     * 修改手机号码第一步
     *
     * @param context
     * @param code_et
     */
    public void resetPhone(Context context, EditText code_et) {
        mModel.changePhone(context, code_et, this);
    }

    /**
     * 重置手机号码
     *
     * @param context
     * @param code_et
     * @param newPhone_et
     */
    public void resetPhoneNext(Context context, EditText code_et, EditText newPhone_et) {
        mModel.changePhoneNext(context, code_et, newPhone_et, this
        );
    }

    /**
     * 获取网络请求失败
     *
     * @param error
     * @param i
     */
    @Override
    public void onFail(String error, int i) {
        if (mView != null) {
            mView.dismissProgress();
            if (error != null) {
                mView.onShowToast(error, i);
                Log.e("LoginPresenter", error);
            }

        }
    }

    /**
     * 获取网络请求成功
     *
     * @param loginMessage 返回的信息
     * @param bundle       请求的参数bundle
     * @param id
     */
    @Override
    public void onSuccess(LoginMessage loginMessage, Bundle bundle, int id) {
        //1.弹出Toast
        String msg = loginMessage.getMsg();
        String code = loginMessage.getCode();
        boolean isSuccessFull = loginMessage.isSuccess();
        DataEntity dataEntity = loginMessage.getData();
        Log.e("LoginPresenter", "msg:" + msg + ",code:" + code + ",success:" + isSuccessFull + ",dataEntity:" + dataEntity);
        String phone = null;
        String password = null;
        if (bundle != null) {
            phone = bundle.getString(Constant.appFinal.LOGIN_NAME_KEY);
            password = bundle.getString(Constant.appFinal.LOGIN_PASSWORD_KEY);
        }
        switch (id) {
            case Constant.appFinal.flag_comm_login://密码登录
                //用户名和密码持久化
                CommonUtils.getPreference().putString(CommonUtils.getContext(), Constant.appFinal.LOGIN_NAME_KEY, phone);
                CommonUtils.getPreference().putString(CommonUtils.getContext(), Constant.appFinal.LOGIN_PASSWORD_KEY, password);
                getDateEntityEMLogin(msg, dataEntity);
                break;
            case Constant.appFinal.flag_getcode://获取验证码
                if (mView != null) {
                    mView.dismissProgress();
                    mView.onShowToast(msg, Constant.REGIST.toast_msg);
                    mView.animation();
                }
                break;
            case Constant.appFinal.flag_code_login://验证码登录
                getDateEntityEMLogin(msg, dataEntity);
                break;
            case Constant.appFinal.flag_register://注册
                if (isSuccessFull) {
                    //1.将phone和密码持久化
                    if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password)) {
                        CommonUtils.getPreference().putString(CommonUtils.getContext(), Constant.appFinal.LOGIN_NAME_KEY, phone);
                        CommonUtils.getPreference().putString(CommonUtils.getContext(), Constant.appFinal.LOGIN_PASSWORD_KEY, password);
                    }
                    //2.跳转到密码登录页面
                    if (mView != null) {
                        mView.onShowToast(msg, Constant.REGIST.toast_msg);
                        mView.navigateToFragment(new LoginFragment(), null);
                    }
                }
                break;
            case Constant.ID.RESET_PASSWORD://重置密码
                if (isSuccessFull) {
                    //2.退出登录,别忘了环信也要退出登录
                    if (mView != null) {
                        mView.onShowToast(msg, Constant.REGIST.toast_msg);
                        mView.animation();
                    }
                }
                break;
            case Constant.ID.CHCEK_RESET_PHONE_CODE://修改手机号码第一步
                if (mView != null) {
                    mView.onShowToast(msg, Constant.REGIST.toast_msg);
                }
                if (isSuccessFull) {
                    //跳转到重置手机号页
                    if (mView != null) {
                        mView.navigateToFragment(new ChangePhoneNextFragment(), null);
                    }
                }
                break;
            case Constant.ID.RESET_PHONE://重置号码
                if (mView != null) {
                    mView.onShowToast(msg, Constant.REGIST.toast_msg);
                }
                if (isSuccessFull) {
                    //2.退出登录,别忘了环信也要退出登录
                    if (mView != null) {
                        mView.onShowToast(msg, Constant.REGIST.toast_msg);
                        mView.animation();
                    }
                }
                break;
        }

    }

    /**
     * 将登录成功返回的数据持久化，并登录环信服务器
     *
     * @param msg
     * @param dataEntity
     */
    private void getDateEntityEMLogin(String msg, DataEntity dataEntity) {
        //1.dataEntity 持久化
        if (dataEntity != null) {
            putPreference(dataEntity);
            //2.普通的登录完成，进行环信登录
            String uuidname = dataEntity.getUid();
            String uuidpassword = Md5Utils.getVal_UTF8(uuidname).toLowerCase();
            //3.跳转到主页
            ThreadUtils.runTaskInUIThread(new Runnable() {
                @Override
                public void run() {
                    if (mView != null) {
                        mView.dismissProgress();
                        mView.onShowToast(context.getResources().getString(R.string.login_success), Constant.REGIST.toast_msg);
                        mView.navigateToFragment(new MainTabFragment(), null);
                    }
                }
            });
            //暂时不做聊天相关
            // onLoginEMClient(uuidname, uuidpassword);

        } else {
            if (mView != null) {
                mView.dismissProgress();
                mView.onShowToast(msg, Constant.REGIST.toast_msg);
            }
        }
    }

    /**
     * 环信登录
     *
     * @param userName
     * @param password
     */

    private void onLoginEMClient(final String userName, final String password) {

        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password))
            EMClient.getInstance().login(userName, password, new EMCallBack() {
                @Override
                public void onSuccess() {
                    Log.e("EMClientLogin", ",环信登录成功！" + userName + "," + password);
                    //3.跳转到主页
                    ThreadUtils.runTaskInUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mView != null) {
                                mView.dismissProgress();
                                mView.onShowToast(context.getResources().getString(R.string.login_success), Constant.REGIST.toast_msg);
                                mView.navigateToFragment(new MainTabFragment(), null);
                            }
                        }
                    });
                }

                @Override
                public void onError(int i, final String s) {
                    Log.e("EMClientLogin", Thread.currentThread().getName() + s);
                    ThreadUtils.runTaskInUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mView != null) {
                                mView.dismissProgress();
                                mView.onShowToast(s, Constant.REGIST.toast_msg);
                            }
                        }
                    });

                }

                @Override
                public void onProgress(int i, String s) {

                }
            });
    }


    /**
     * 数据持久化
     *
     * @param dataEntity
     */
    private void putPreference(DataEntity dataEntity) {
        //uuid
        CommonUtils.getPreference().putString(context, Constant.LOGIN.uid, dataEntity.getUid());
        //token
        CommonUtils.getPreference().putString(context, Constant.LOGIN.user_token, dataEntity.getToken());
        //phone
        CommonUtils.getPreference().putString(context, Constant.LOGIN.phone, dataEntity.getPhone());
        //headImg
        CommonUtils.getPreference().putString(context, Constant.LOGIN.headImg, dataEntity.getHeadImg());
        //nickName
        CommonUtils.getPreference().putString(context, Constant.LOGIN.nickName, dataEntity.getNickName());
        //workUint
        CommonUtils.getPreference().putString(context, Constant.LOGIN.workUnit, dataEntity.getWorkUnit());
        //workJob
        CommonUtils.getPreference().putString(context, Constant.LOGIN.workJob, dataEntity.getWorkJob());
        //realName
        CommonUtils.getPreference().putBoolean(context, Constant.LOGIN.realName, dataEntity.isRealName());
        // realNameStatusStr
        CommonUtils.getPreference().putString(context,Constant.LOGIN.realNameStatusStr,dataEntity.getRealNameStatusStr());

    }

    /**
     * 弹出 Toast
     *
     * @param msg Toast的提示
     * @param id
     */
    @Override
    public void onError(String msg, int id) {
        if (mView != null) {
            mView.dismissProgress();
            mView.onShowToast(msg, id);
        }
    }

}
