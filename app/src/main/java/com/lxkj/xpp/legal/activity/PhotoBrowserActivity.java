package com.lxkj.xpp.legal.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ccwant.photo.selector.load.CCwantImageLoader;
import com.lxkj.xpp.legal.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 熊萍萍 on 2017/3/26/026.
 * 图片浏览
 */

public class PhotoBrowserActivity extends Activity {

    private ArrayList<View> listViews = null;
    private ViewPager pager;
    private MyPageAdapter adapter;


    private List<String> mData = new ArrayList<String>();
    private int mPosition = 0;

    private TextView mTxtCount;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setTranslucentStatus();
        setContentView(R.layout.publish_circle_activity_photo);

        listViews = new ArrayList<>();


        Button photo_bt_exit = (Button) findViewById(R.id.photo_bt_exit);
        photo_bt_exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                finish();
            }
        });
        Button photo_bt_del = (Button) findViewById(R.id.photo_bt_del);
        photo_bt_del.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (listViews.size() == 1) {

                    Intent intent = new Intent();
                    intent.putExtra("result", (Serializable) mData);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {


                    mData.remove(mData.get(mPosition));
                    pager.removeAllViews();
                    listViews.remove(mPosition);
                    adapter.notifyDataSetChanged();
                    mTxtCount.setText((mPosition + 1) + "/" + mData.size());
                }
            }
        });
        Button photo_bt_enter = (Button) findViewById(R.id.photo_bt_enter);
        photo_bt_enter.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                Intent intent = new Intent();
                intent.putExtra("result", (Serializable) mData);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        mTxtCount = (TextView) findViewById(R.id.photo_count);

        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setOnPageChangeListener(pageChangeListener);

        steupPhotoToListView();

        adapter = new MyPageAdapter(listViews);// 构造adapter
        pager.setAdapter(adapter);// 设置适配器
        pager.setCurrentItem(mPosition);
        mTxtCount.setText((mPosition + 1) + "/" + mData.size());

    }

    //    // 需要setContentView之前调用
//    private void setTranslucentStatus() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            // 透明状态栏
//            getWindow().addFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            // 透明导航栏
//            getWindow().addFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            SystemStatusManager tintManager = new SystemStatusManager(this);
//            tintManager.setStatusBarTintEnabled(true);
//            // 设置状态栏的颜色
//            tintManager.setStatusBarTintResource(R.color.mainTheme);
//            getWindow().getDecorView().setFitsSystemWindows(true);
//        }
//    }
    private void steupPhotoToListView() {
        Intent intent = getIntent();
        mData = (List<String>) intent.getSerializableExtra("CCwantPhotoList");
        mPosition = (int) intent.getSerializableExtra("CCwantPhotoPosition");
        mData.remove("default");
        for (int i = 0; i < mData.size(); i++) {
            initListViews(mData.get(i));
        }
    }

    private void initListViews(String path) {

        ImageView img = new ImageView(this);// 构造textView对象
        img.setBackgroundColor(0xff000000);
        img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        listViews.add(img);// 添加view
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        public void onPageSelected(int position) {// 页面选择响应函数
            mPosition = position;
            mTxtCount.setText((mPosition + 1) + "/" + mData.size());
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {// 滑动中。。。

        }

        public void onPageScrollStateChanged(int arg0) {// 滑动状态改变

        }
    };

    class MyPageAdapter extends PagerAdapter {

        private ArrayList<View> listViews;// content

        public MyPageAdapter(ArrayList<View> listViews) {// 构造函数
            // 初始化viewpager的时候给的一个页面
            this.listViews = listViews;
        }

        @Override
        public int getCount() {// 返回数量
            return listViews == null ? 0 : listViews.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {// 销毁view对象
            ((ViewPager) arg0).removeView(listViews.get(arg1 % getCount()));
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {// 返回view对象
            try {
                CCwantImageLoader.getIntance(PhotoBrowserActivity.this).ImageLoader(mData.get(arg1), (ImageView) listViews.get(arg1), true);
                ((ViewPager) arg0).addView(listViews.get(arg1 % getCount()), 0);

            } catch (Exception e) {
            }
            return listViews.get(arg1 % getCount());
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }
}

