package com.lxkj.xpp.legal.listener;

import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.utils.CommonUtils;
import com.lxkj.xpp.legal.utils.okhttp.OkHttpUtils;
import com.lxkj.xpp.legal.utils.okhttp.callback.StringCallback;

import java.util.HashMap;

import okhttp3.Call;

/**
 * Created by 熊萍萍 on 2017/3/8/008.
 * 定位监听器
 */

public class MyLoctionListener implements AMapLocationListener {
    /**
     * 1001	用户登录后请求
     * 1002	附近的人（用户在点击附近的人时请求、同时请求附近的人接口）
     */
    private int dataSource;//定位类型

    public MyLoctionListener() {
    }

    public MyLoctionListener(int dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {//解析amapLocation获取相应内容。
                String street = amapLocation.getStreet();//街道信息
                String city = amapLocation.getCity();//城市信息
                String province = amapLocation.getProvince();//省信息
                double lat = amapLocation.getLatitude();//获取纬度
                String adCode = amapLocation.getAdCode();//地区编码
                double lon = amapLocation.getLongitude();//获取经度
                String desc = amapLocation.getDistrict();//城区信息
                String AoiName = amapLocation.getAoiName();//获取当前定位点的AOI信息
                Log.e("MyLoctionListener", street + "," + city + "," + province + "," + lat
                        + "," + adCode + "," + lon + "," + desc + "," + AoiName);
                //上传定位数据到服务器
                postLoctionInfo(street, dataSource,
                        city, province, lat, adCode, lon, desc, AoiName);
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        } else {
            Log.e("AmapError", "amapLocation 为空！");
        }

    }

    /**
     * 上传定位数据到服务器
     *
     * @param street
     * @param dataSource
     * @param city
     * @param province
     * @param lat
     * @param adCode
     * @param lon
     * @param desc
     * @param aoiName
     */
    private void postLoctionInfo(String street, int dataSource, String city, String province, double lat, String adCode, double lon, String desc, String aoiName) {
        HashMap<String, String> params = CommonUtils.getParameterMap(new String[]{
                        "street", "sourceType", "dataSource", "city", "province",
                        "latitude", "adCode", "longitude", "district", "poiName"},
                street, "Android", String.valueOf(dataSource), city, province,
                lat + "", adCode, lon + "", desc, aoiName);
        String token = CommonUtils.getPreference().getString(CommonUtils.getContext(), Constant.LOGIN.user_token);
        OkHttpUtils
                .postString()
                .url(Constant.URL.LOCATION_URL)
                .content(new Gson().toJson(params))
                .mediaType(Constant.appFinal.MEDIA_TYPE_JSON)
                .addHeader("user_token", token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (e.getMessage() != null)
                            Log.e("MyLocationListener", e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("MyLocationListener", response);
                    }
                });
    }
}
