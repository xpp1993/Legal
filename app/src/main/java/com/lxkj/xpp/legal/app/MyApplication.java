package com.lxkj.xpp.legal.app;

import android.app.Application;
import android.content.Context;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.lxkj.xpp.legal.chat.DemoHelper;
import com.lxkj.xpp.legal.model.bean.CircleListBean;
import com.lxkj.xpp.legal.utils.LocationUtils;
import com.lxkj.xpp.legal.utils.okhttp.OkHttpUtils;
import com.lxkj.xpp.legal.utils.okhttp.PreferencesUtils;
import com.lxkj.xpp.legal.utils.okhttp.https.HttpsUtils;
import com.lxkj.xpp.legal.utils.okhttp.log.LoggerInterceptor;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;


/**
 * Created by 熊萍萍 on 2016/12/19/019.
 */

public class MyApplication extends Application {
    public static Context applicationContext;
    public static PreferencesUtils preferencesUtils;
    private static final String TAG = "MyApplication";
    public LocationUtils locationUtils;


    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        preferencesUtils = PreferencesUtils.getInstance();
        //初始化环信sdk
        DemoHelper.getInstance().init(applicationContext);
        //初始化okhttpClient
        initOKHttpClient();
        //初始化定位sdk
        locationUtils = new LocationUtils(applicationContext);
    }

    private void initOKHttpClient() {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        ClearableCookieJar cookieJar1 = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(20000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor(TAG))//设置拦截器，打印LOG
                .cookieJar(cookieJar1)//cookie持久化
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)//设置能访问所有的https网站
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }


}
