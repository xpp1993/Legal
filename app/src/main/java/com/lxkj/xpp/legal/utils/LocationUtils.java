package com.lxkj.xpp.legal.utils;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * Created by 熊萍萍 on 2017/3/8/008.
 * 定位
 */

public class LocationUtils {
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    private Object objLock = new Object();
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption, DIYoption;

    public LocationUtils(Context context) {
        synchronized (objLock) {
            if (mLocationClient == null) {
                mLocationClient = new AMapLocationClient(context);
                setLocationOption(getDefaultLocationClientOption());
                Log.e("LocationUtils", "初始化定位sdk");
            }
        }
    }

    /**
     * 定位参数配置
     */
    public AMapLocationClientOption getDefaultLocationClientOption() {
        if (mLocationOption == null) {
            mLocationOption = new AMapLocationClientOption();
            //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
            //不会使用GPS和其他传感器，只会使用网络定位（Wi-Fi和基站定位）；
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
            //获取一次定位结果：该方法默认为false。
            mLocationOption.setOnceLocation(true);
            mLocationOption.setOnceLocationLatest(true);
            //设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);
            //关闭缓存机制
            mLocationOption.setLocationCacheEnable(false);
        }
        return mLocationOption;
    }

    /***
     * 设置定位参数
     *
     * @param option
     * @return isSuccessSetOption
     */
    public boolean setLocationOption(AMapLocationClientOption option) {
        boolean isSuccess = false;
        if (option != null) {
            if (mLocationClient.isStarted())
                mLocationClient.stopLocation();
            DIYoption = option;
            mLocationClient.setLocationOption(option);
        }
        return isSuccess;
    }

    /**
     * 获得定位参数对象
     *
     * @return
     */
    public AMapLocationClientOption getOption() {
        return DIYoption;
    }

    /***
     * 注册监听
     *
     * @param listener
     * @return
     */

    public boolean registerListener(AMapLocationListener listener) {
        boolean isSuccess = false;
        if (listener != null) {
            mLocationClient.setLocationListener(listener);
            Log.e("LocationUtils", "注册定位监听");
            isSuccess = true;
        }
        return isSuccess;
    }

    /**
     * 注销监听
     *
     * @param listener
     */
    public void unregisterListener(AMapLocationListener listener) {
        if (listener != null) {
            mLocationClient.unRegisterLocationListener(listener);
            Log.e("LocationUtils", "注销定位监听");
        }
    }

    /**
     * 开始定位
     */
    public void start() {
        synchronized (objLock) {
            if (mLocationClient != null ) {
                mLocationClient.startLocation();
                Log.e("LocationUtils", "开始定位");
            }
        }
    }

    /**
     * 停止定位
     */
    public void stop() {
        synchronized (objLock) {
            if (mLocationClient != null && mLocationClient.isStarted()) {
                mLocationClient.stopLocation();
                Log.e("LocationUtils", "停止定位");
            }
        }
    }

    /**
     * 销毁定位客户端
     */
    public void onDestroy() {
        synchronized (objLock) {
            if (mLocationClient != null && !mLocationClient.isStarted()) {
                mLocationClient.onDestroy();
                Log.e("LocationUtils", "销毁定位客户端");
            }
        }
    }
}
