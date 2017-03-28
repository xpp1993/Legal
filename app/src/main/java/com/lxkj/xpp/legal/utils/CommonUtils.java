package com.lxkj.xpp.legal.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.app.MyApplication;
import com.lxkj.xpp.legal.constant.Constant;
import com.lxkj.xpp.legal.model.LoginModel;
import com.lxkj.xpp.legal.model.bean.LoginMessage;
import com.lxkj.xpp.legal.utils.okhttp.OkHttpUtils;
import com.lxkj.xpp.legal.utils.okhttp.PreferencesUtils;
import com.lxkj.xpp.legal.utils.okhttp.callback.GenericsCallback;
import com.lxkj.xpp.legal.utils.okhttp.callback.JsonGenericsSerializator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import okhttp3.Call;

/**
 * Created by 熊萍萍 on 2016/12/19/019.
 * 公共方法类
 */

public class CommonUtils {


    /**
     * 构建参数map对象的工具方法
     *
     * @param keys
     * @param values
     * @return
     */
    public static HashMap<String, String> getParameterMap(String keys[], String... values) {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], values[i]);
        }
        return map;
    }

    public static String getParameterJsonResult(String keys[], Object... values) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < keys.length; i++) {
            jsonObject.put(keys[i], values[i]);
        }
        return jsonObject.toString();

    }


    /**
     * 获取验证码
     *
     * @param phone_et 手机号码 editText
     * @param type     获取验证码类型 ：1001	用户注册  2001验证码登 3001第三方账号绑定
     * @param listener 接口回调，用于和view层通信
     */

    public static void getCheckCode(Context context, EditText phone_et, int type, final LoginModel.OnLoginListener listener) {
        String phone = phone_et.getText().toString().trim();
        String typeStr = String.valueOf(type);
        if (TextUtils.isEmpty(phone)) {//手机号码为空
            listener.onError(context.getString(R.string.phone_empty), Constant.REGIST.toast_userName_empty);
        } else if (!FormatCheck.isMobile(phone)) {//手机号码格式错误
            listener.onError(context.getString(R.string.phone_error), Constant.REGIST.toast_userName_error);
        } else {//联网获取手机验证码
            netForCheckCode(listener, phone, typeStr);
        }
    }

    /**
     * 联网获取手机验证码
     *
     * @param listener
     * @param phone
     * @param typeStr
     */
    public static void netForCheckCode(final LoginModel.OnLoginListener listener, String phone, String typeStr) {
        HashMap<String, String> params =
                CommonUtils.getParameterMap(new String[]{"phone", "type"}, phone, typeStr);
        OkHttpUtils
                .postString()
                .url(Constant.URL.CODE_URL)
                .id(Constant.appFinal.flag_getcode)
                .content(new Gson().toJson(params))
                .mediaType(Constant.appFinal.MEDIA_TYPE_JSON)
                .build()
                .execute(new GenericsCallback<LoginMessage>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onFail(e.getMessage(), id);
                    }

                    @Override
                    public void onResponse(LoginMessage response, int id) {
                        listener.onSuccess(response, null, id);
                    }

                });
    }

    public static Context getContext() {
        return MyApplication.applicationContext;
    }

    public static PreferencesUtils getPreference() {
        return MyApplication.preferencesUtils;
    }

    /**
     * 调度剪裁intent
     *
     * @param dataUri 选中图片的uri路径
     * @return 返回意图
     */
    public static Intent getPhotoZoomIntent(Uri dataUri) {
        Intent intent = new Intent();
        //系统裁剪活动
        intent.setAction("com.android.camera.action.CROP");
        //设置裁剪的源图片和类型
        intent.setDataAndType(dataUri, "image/*");
        //打开裁剪
        intent.putExtra("crop", "true");
        // 裁剪框比例，宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 输出图片大小，剪裁图片的宽高
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        //返回结果,但是大图片很有可能直接内存溢出
//		intent.putExtra("return-data", true);
        //黑边【可以缩放】
        intent.putExtra("scale", true);
        //黑边【可以缩放】
        intent.putExtra("scaleUpIfNeeded", true);
        return intent;
    }

    /**
     * 使用系统当前时间，产生一个临时文件，用户缓存
     *
     * @param fileType 缓存文件的格式  如：jpg png
     * @return
     */
    public static File getTempFile(String fileType) {
        File file = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("'TEMP_FILE'_yyyy-MM-dd-HH-mm-ss");
            String tempFileName = dateFormat.format(new Date());
            try {
                file = File.createTempFile(tempFileName, fileType, Environment.getExternalStorageDirectory());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static File scal(Uri fileUri) {
        String path = fileUri.getPath();
        File outputFile = new File(path);
        long fileSize = outputFile.length();
        final long fileMaxSize = 100 * 1024;
        if (fileSize >= fileMaxSize) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            int height = options.outHeight;
            int width = options.outWidth;

            double scale = Math.sqrt((float) fileSize / fileMaxSize);
            options.outHeight = (int) (height / scale);
            options.outWidth = (int) (width / scale);
            options.inSampleSize = (int) (scale + 0.5);
            options.inJustDecodeBounds = false;

            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            outputFile = getTempFile(".jpg");
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(outputFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            } else {
                File tempFile = outputFile;
                outputFile = getTempFile(".jpg");
                copyFileUsingFileChannels(tempFile, outputFile);
            }

        }
        return outputFile;

    }

    public static void copyFileUsingFileChannels(File source, File dest) {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            try {
                inputChannel = new FileInputStream(source).getChannel();
                outputChannel = new FileOutputStream(dest).getChannel();
                outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } finally {
            try {
                inputChannel.close();
                outputChannel.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭键盘
     */
    public static void closeKeyMap(FragmentActivity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null&&activity.getCurrentFocus()!=null&&activity.getCurrentFocus().getWindowToken()!=null)
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 打开键盘
     */
    public static void openKeyboard(FragmentActivity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 删除ArrayList中重复元素，保持顺序
     */
    public static List removeDuplicateWithOrder(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        list.clear();
        list.addAll(newList);
        return list;
        // System.out.println( " remove duplicate "   +  list);
    }

    //打开APK程序代码

    /**
     * 打开 apk
     *
     * @param file
     */
    public static void openFile(File file) {
        Log.e("OpenFile", file.getName());
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        getContext().startActivity(intent);
    }

}