package com.lxkj.xpp.legal.model;

import com.lxkj.xpp.legal.base.BaseMvpModel;

/**
 * Created by 熊萍萍 on 2016/12/23/023.
 */

public class MainModel implements BaseMvpModel {
//    /**
//     * 检查软件是否更新
//     *
//     * @param context
//     * @param actionUrl
//     * @param requestCode
//     * @param requestType
//     * @param params
//     */
//    public void checkVesion(Context context, String actionUrl, int requestCode, int requestType, HashMap<String, String> params, final Handler handler) {
//        RequestManager.getInstance(context).requestAysn(actionUrl, requestCode, requestType, params, new ReCallBack<String>() {
//
//            @Override
//            public void onReqSuccess(int requestCode, String result) {
//                DecodeManager.decodeLogin(result,requestCode,handler);
//            }
//
//            @Override
//            public void onReqFailed(int requestCode, String errorMsg) {
//
//            }
//
//        });
//
//    }
//
//    /**
//     * 下载文件 apk
//     *
//     * @param context
//     * @param actionUrl
//     * @param dirFile
//     * @param requestCode
//     * @param params
//     */
//    public void downFile(final Context context, String actionUrl, final File dirFile, int requestCode, HashMap<String, String> params) {
//        RequestManager.getInstance(context).downLoadFile(actionUrl, dirFile, new ReqProgressCallBack<File>() {
//            @Override
//            public void onProgress(int requestCode, long total, long current) {
//
//            }
//
//            @Override
//            public void onReqSuccess(int requestCode, File result) {
//                //文件下载成功，安装apk 文件
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setDataAndType(Uri.fromFile(dirFile), "application/vnd.android.package-archive");
//                context.startActivity(intent);
//            }
//
//            @Override
//            public void onReqFailed(int requestCode, String errorMsg) {
//
//            }
//        }, requestCode);
//    }
}
