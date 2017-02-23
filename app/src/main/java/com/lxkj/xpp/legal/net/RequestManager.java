package com.lxkj.xpp.legal.net;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.net.callback.ReCallBack;
import com.lxkj.xpp.legal.net.callback.ReqProgressCallBack;
import com.lxkj.xpp.legal.utils.CommonUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * Created by 熊萍萍 on 2016/12/12/012.
 */

public class RequestManager {
    private static final String TAG = RequestManager.class.getSimpleName();
    private static volatile RequestManager instance;//单例引用
    private static OkHttpClient mOkHttpClient;//okhttpClient实例
    private Handler okHttpHandler;//全局处理子线程和M主线程通信。
    private Context context;

    /**
     * 初始化RequestMananger
     *
     * @param context
     */
    public RequestManager(final Context context) {
        this.context = context;
        //初始化OKHttpClient
        mOkHttpClient = new OkHttpClient().newBuilder().
                connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        OkHttpClient.Builder newBuilder = mOkHttpClient.newBuilder();//添加拦截
        newBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                boolean connected = CommonUtils.isNetworkConnected(context);//是否有网络
                if (!connected) {
                    request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                }
                Response response = chain.proceed(request);
                return response;
            }
        });
        //初始化Handler
        okHttpHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 实现单例引用
     *
     * @param context
     * @return
     */
    public static RequestManager getInstance(Context context) {
        RequestManager inst = instance;
        if (inst == null) {
            synchronized (RequestManager.class) {
                inst = instance;
                if (inst == null) {
                    inst = new RequestManager(context.getApplicationContext());
                    instance = inst;
                }
            }
        }
        return inst;
    }
/****************************************get和post请求***************************************************/
    /**
     * okhttp 请求统一入口
     *
     * @param actionUrl   请求接口
     * @param requestType 请求码
     * @param requestType 请求类型
     * @param params      请求参数
     * @param reqCallBack 请求返回数据回调 if null 表示同步请求 else 表示异步请求
     * @param <T>         数据泛型
     * @return
     */
    public <T> T requestAysn(String actionUrl, int requestCode, int requestType, HashMap<String, String> params, ReCallBack<T> reqCallBack) {
        T call = null;
        switch (requestType) {
            case Constant.appFinal.TYPE_GET:
                call = requestGet(actionUrl, requestCode, params, reqCallBack);
                break;
            case Constant.appFinal.TYPE_POST_JSON:
                call = requestPost(actionUrl, requestCode, params, reqCallBack);
                break;
            case Constant.appFinal.TYPE_POST_FORM:
                call = requestPostWitnForm(actionUrl, requestCode, params, reqCallBack);
                break;
        }
        return call;
    }

    /**
     * okhttp get 请求
     *
     * @param actionUrl 请求接口
     * @param parmsMap  请求参数
     * @param callBack  请求返回数据回调 if callBacl==null 表示同步请求 else 表示异步请求
     * @param <T>       数据泛型
     */
    private <T> T requestGet(String actionUrl, int requestCode, HashMap<String, String> parmsMap, final ReCallBack<T> callBack) {
        try {
            //处理参数
            StringBuilder tempParams = handlerParams(parmsMap);
            //补全请求的参数
            String requestUrl = String.format("%s/%s?%s", Constant.URL.BASE_URL, actionUrl, tempParams.toString());
            //使用,设置一个有效期为十秒的缓存
            final CacheControl cache = new CacheControl.Builder().maxAge(10, TimeUnit.MILLISECONDS).build();
            //创建一个请求
            Request request = addHeaders().cacheControl(cache).url(requestUrl).build();
            //创建一个Call
            final Call call = mOkHttpClient.newCall(request);
            //执行请求
            T responseStr = doRequst(callBack, call, requestCode);
            if (responseStr != null) return responseStr;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "requestGet---->" + e.toString());
        }
        return null;
    }

    /**
     * okhttp post 请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调 if callBacl==null 表示同步请求 else 表示异步请求
     * @param <T>       数据泛型
     * @return
     */
    private <T> T requestPost(String actionUrl, int requestCode, HashMap<String, String> paramsMap, final ReCallBack<T> callBack) {
        try {
            //处理请求参数
            StringBuilder tempParams = handlerParams(paramsMap);
            //补全请求地址
          // String requestUrl = String.format("%s/%s", Constant.URL.BASE_URL, actionUrl);
            //创建请求实体对象vrequestBody
            RequestBody requestBody = RequestBody.create(Constant.appFinal.MEDIA_TYPE_JSON, tempParams.toString());
            //设置一个有效期为十秒的缓存
            final CacheControl cache = new CacheControl.Builder().maxAge(10, TimeUnit.SECONDS).build();
            //创建请求
            Request request = addHeaders().cacheControl(cache).url(actionUrl).post(requestBody).build();
            //创建一个call
            final Call call = mOkHttpClient.newCall(request);
            T responseStr = doRequst(callBack, call, requestCode);
            if (responseStr != null) return responseStr;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "requestPost------>" + e.toString());
        }
        return null;
    }

    /**
     * okhttp post 提交表单
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调 if callBacl==null 表示同步请求 else 表示异步请求
     * @param <T>       数据泛型
     * @return
     */
    private <T> T requestPostWitnForm(String actionUrl, int requestCode, HashMap<String, String> paramsMap, final ReCallBack<T> callBack) {
        try {
            //创建一个FormBody.Builder
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : paramsMap.keySet()) {
                //追加表单信息
                builder.add(key, paramsMap.get(key));
            }
            //生成表单实体对象
            FormBody formBody = builder.build();
            //补全请求地址
            String requestUrl = String.format("%s/%s", Constant.URL.BASE_URL, actionUrl);
            //创建一个请求
            final Request request = addHeaders().url(requestUrl).put(formBody).build();
            //创建一个Call
            Call call = mOkHttpClient.newCall(request);
            //执行请求
            T responseStr = doRequst(callBack, call, requestCode);
            if (responseStr != null) return responseStr;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "requestPostByAsynWitnForm" + e.toString());
        }
        return null;
    }

    /**
     * 执行请求
     *
     * @param callBack
     * @param call
     * @param <T>
     * @return
     * @throws IOException
     */
    @Nullable
    private <T> T doRequst(ReCallBack<T> callBack, Call call, int requestCode) throws IOException {
        if (callBack == null) {//执行同步请求
            T responseStr = doSyncQuest(call);
            if (responseStr != null) return responseStr;
        } else {
            //执行异步请求
            enqueueCall(callBack, call, requestCode, context.getString(R.string.getpost));
            return (T) call;
        }
        return null;
    }

    /**
     * 执行同步请求
     *
     * @param call
     * @param <T>
     * @return
     * @throws IOException
     */
    @Nullable
    private <T> T doSyncQuest(Call call) throws IOException {
        //执行请求
        Response response = call.execute();
        //请求成功返回数据
        if (response.isSuccessful()) {
            String responseStr = response.body().string();
            Log.e(TAG, "response------>" + responseStr);
            return (T) responseStr;
        }
        return null;
    }

    /**
     * 执行异步请求
     *
     * @param callBack
     * @param call
     * @param <T>
     */
    private <T> void enqueueCall(final ReCallBack<T> callBack, Call call, final int requestCode, final String errorMsg) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                failedCallBack(errorMsg, callBack, requestCode);
                Log.e(TAG, "enqueueCall----->" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseString = response.body().string();
                    Log.e(TAG, "enqueueCall----->" + responseString);
                    successCallBack((T) responseString, callBack, requestCode);
                } else {
                    failedCallBack(errorMsg, callBack, requestCode);
                    Log.e(TAG,"enqueueCall----->"+errorMsg+response.headers()+","+response.code());
                }
            }
        });
    }

    /**
     * get post请求为json处理参数
     *
     * @param parmsMap
     * @return
     */
    @NonNull
    private StringBuilder handlerParams(HashMap<String, String> parmsMap) throws UnsupportedEncodingException {
        StringBuilder tempParams = new StringBuilder();
        //处理参数
        int pos = 0;
        for (String key : parmsMap.keySet()) {
            if (pos > 0) {
                tempParams.append("&");
            }
            //对参数进行URLEncoder
            tempParams.append(String.format("%s=%s", key, URLEncoder.encode(parmsMap.get(key), "utf-8")));
            pos++;
        }
        return tempParams;
    }

    /**
     * 如何请求添加头信息
     *
     * @return
     */
    private Request.Builder addHeaders() {
        Request.Builder builder = new Request.Builder()
                .addHeader("Connection", "keep-alive")
                .addHeader("platfrom", "2")
                .addHeader("phoneModel", Build.MODEL)
                .addHeader("systemVersion", Build.VERSION.RELEASE)
                .addHeader("appVersion", "3.5.0");
        return builder;
    }
/****************************************get和post请求***************************************************/
/****************************************文件上传和下载***************************************************/
    /**
     * 不带参数上传文件
     *
     * @param actionUrl 接口地址
     * @param filePath  本地文件地址
     * @param callBack
     * @param <T>
     */
    public <T> void upLoadFile(String actionUrl, String filePath, final ReCallBack<T> callBack, int requestCode) {
        //请求补全地址
        String requestUrl = String.format("%s/%s", Constant.URL.BASE_URL, actionUrl);
        //创建file
        File file = new File(filePath);
        //创建requestbody
        RequestBody requestBody = RequestBody.create(Constant.appFinal.MEDIA_OBJECT_JSON, file);
        //创建request
        final Request request = new Request.Builder().url(requestUrl).post(requestBody).build();
        //创建一个call
        final Call call = mOkHttpClient.newBuilder().writeTimeout(50, TimeUnit.SECONDS).build().newCall(request);
        //执行异步请求
        enqueueCall(callBack, call, requestCode, context.getString(R.string.getpost));
    }

    /**
     * 带参数上传文件(post 提交分块请求)
     *
     * @param actionUrl 接口
     * @param paramsMap 参数
     * @param callBack  回调
     * @param <T>       数据泛型
     */
    public <T> void upLoadFile(String actionUrl, HashMap<String, Object> paramsMap, final ReCallBack<T> callBack, int requestCode) {
        try {
            //补全请求地址
            String requestUrl = String.format("%s/%s", Constant.URL.BASE_URL, actionUrl);
            //分块
            MultipartBody.Builder builder = new MultipartBody.Builder();
            //设置类型
            builder.setType(MultipartBody.FORM);
            //追加参数
            for (String key : paramsMap.keySet()) {
                Object object = paramsMap.get(key);
                if (!(object instanceof File)) {//如果value 不是文件类型
                    builder.addFormDataPart(key, object.toString());
                } else {
                    File file = (File) object;
                    if (callBack instanceof ReqProgressCallBack) {//带参数带进度上传文件
                        //向下转型
                        ReqProgressCallBack reqProgressCallBack = (ReqProgressCallBack) callBack;
                        builder.addFormDataPart(key, file.getName(), createProgressRequestBody(requestCode, Constant.appFinal.MEDIA_OBJECT_JSON, file, reqProgressCallBack));
                    } else if (!(callBack instanceof ReqProgressCallBack)) {
                        builder.addFormDataPart(key, file.getName(), RequestBody.create(null, file));
                    }
                }
            }
            //创建requestBody
            RequestBody requestBody = builder.build();
            //创建request
            Request request = new Request.Builder().url(requestUrl).post(requestBody).build();
            //为call配置，读取超时时间
            OkHttpClient copyClient = mOkHttpClient.newBuilder().writeTimeout(50, TimeUnit.SECONDS).build();
            //创建一个call
            final Call call = copyClient.newCall(request);
            //执行异步请求
            enqueueCall(callBack, call, requestCode, context.getString(R.string.loadFile));
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * 下载文件
     *
     * @param fileUrl  文件url
     * @param file     存储目标
     * @param callBack
     * @param <T>
     */
    public <T> void downLoadFile(String fileUrl, final File file, final ReCallBack<T> callBack, final int requestCode) {
//        final String fileName = MD5Util.encode(fileUrl);//一加密后的文件路径作为文件名
//        final File file = new File(destFileDir, fileName);
        //补全请求地址
        String requestUrl=null;
        if (requestCode != 0x26) {
            requestUrl = String.format("%s/%s", Constant.URL.BASE_URL, fileUrl);
        }
        if (requestCode==0x26){
            requestUrl=fileUrl;
        }
//        if (file.exists()) {
//            successCallBack((T) file, callBack, requestCode);
//            return;
//        }
        final Request request = new Request.Builder().url(requestUrl).build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                failedCallBack("下载失败", callBack, requestCode);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream in = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    long total = response.body().contentLength();
                    Log.e(TAG, "tatal----->" + total);
                    long current = 0;
                    in = response.body().byteStream();
                    fos = new FileOutputStream(file);
                    while ((len = in.read(buf)) != -1) {
                        current += len;
                        fos.write(buf, 0, len);
                        Log.e(TAG, "current---->" + current);
                        //带进度下载文件
                        if (callBack instanceof ReqProgressCallBack) {//判断父类对象是否是ReqProgressCallBack子类的一个实例
                            ReqProgressCallBack<T> reqProgressCallBack = (ReqProgressCallBack<T>) callBack;//向下转型操作
                            progressCallBack(requestCode, total, current, reqProgressCallBack);
                        }
                    }
                    fos.flush();
                    successCallBack((T) file, callBack, requestCode);
                } catch (IOException e) {
                    Log.e(TAG, e.toString());
                    failedCallBack("下载失败", callBack, requestCode);
                    e.printStackTrace();
                } finally {
                    if (in != null) {
                        in.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                }

            }
        });
    }

    /**
     * 创建带进度的RequestBody
     *
     * @param contentType mediaType
     * @param file        准备上传的文件
     * @param callBack    响应进度回调
     * @param <T>         数据泛型
     * @return
     */
    public <T> RequestBody createProgressRequestBody(final int requestCode, final MediaType contentType, final File file, final ReqProgressCallBack<T> callBack) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() throws IOException {
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source;
                try {
                    source = Okio.source(file);
                    Buffer buffer = new Buffer();
                    long remaining = contentLength();
                    long current = 0;
                    for (long readCount; (readCount = source.read(buffer, 2048)) != -1; ) {
                        sink.write(buffer, readCount);
                        current += readCount;
                        Log.e(TAG, "current---->" + current);
                        progressCallBack(requestCode, remaining, current, callBack);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * 响应进度实现
     *
     * @param total               总计大小
     * @param current             当前进度
     * @param reqProgressCallBack 进度回调
     * @param <T>
     */
    private <T> void progressCallBack(final int requestCode, final long total, final long current, final ReqProgressCallBack<T> reqProgressCallBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (reqProgressCallBack != null) {
                    reqProgressCallBack.onProgress(requestCode, total, current);
                }
            }
        });
    }
/****************************************文件上传和下载***************************************************/
    /**
     * 成功回调处理
     *
     * @param result
     * @param reqCallBack
     * @param <T>
     */
    private <T> void successCallBack(final T result, final ReCallBack<T> reqCallBack, final int requestCode) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (reqCallBack != null) {
                    reqCallBack.onReqSuccess(requestCode, result);
                }
            }
        });
    }

    /**
     * 失败回调处理
     *
     * @param errorMsg
     * @param reqCallBack
     * @param <T>
     */
    private <T> void failedCallBack(final String errorMsg, final ReCallBack<T> reqCallBack, final int requestCode) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (reqCallBack != null) {
                    reqCallBack.onReqFailed(requestCode, errorMsg);
                }
            }
        });
    }
}
