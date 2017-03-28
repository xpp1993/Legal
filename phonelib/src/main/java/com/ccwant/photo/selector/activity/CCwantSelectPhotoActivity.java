/*******************************************************************************
 * Copyright 2015 CCwant
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.ccwant.photo.selector.activity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.ccwant.photo.selector.R;
import com.ccwant.photo.selector.SystemStatusManager;
import com.ccwant.photo.selector.adapter.CCwantSelectPhotoAdapter;
import com.ccwant.photo.selector.adapter.CCwantSelectPhotoAdapter.SelectCallback;
import com.ccwant.photo.selector.bean.CCwantAlbum;
import com.ccwant.photo.selector.bean.CCwantPhoto;
import com.ccwant.photo.selector.util.CCwantActivityManager;
import com.ccwant.photo.selector.util.CCwantPhotoSortHelper;
import com.ccwant.photo.selector.util.CCwantPhotoSortHelper.PhotoSortMethod;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 选择图片
 * @author Administrator
 *
 */
public class CCwantSelectPhotoActivity extends Activity implements SelectCallback{

	List<CCwantPhoto> mData=new ArrayList<CCwantPhoto>();
	List<String> mPathList=new ArrayList<String>();

	String mAlbumName;
	int remainNum ;
	private CCwantSelectPhotoAdapter mAdapter;
	private GridView mGrvContent;
	private Button mBtnSubmit;
	private TextView mTxtTitle;
	private ImageView mImgBack;
	private Toast mToast ;

	CCwantPhotoSortHelper mPhotoSortHelper;
	private TextView mTxtCancel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTranslucentStatus();
		setContentView(R.layout.ccwant_activity_select_photo);
		mPhotoSortHelper=new CCwantPhotoSortHelper();
		mPhotoSortHelper.setSortMethog(PhotoSortMethod.dateOrder);

		remainNum = getIntent().getIntExtra("remainNum",0);

		mImgBack=(ImageView)findViewById(R.id.ccwnat_img_back_select_photo);
		mImgBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mTxtCancel=(TextView)findViewById(R.id.ccwant_txt_cancel_select);
		mTxtCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CCwantActivityManager.getInstance().exit();
				Intent intent = new Intent();
				setResult(RESULT_CANCELED, intent);
				finish();
			}
		});
		
		
		mTxtTitle=(TextView)findViewById(R.id.ccwant_txt_title_select_photo);
		mGrvContent=(GridView)findViewById(R.id.ccwant_grv_content_select);
		mAdapter=new CCwantSelectPhotoAdapter(this, mData);
		mAdapter.setSelectCallback(this);
		mAdapter.setRemainNum(remainNum);
		mGrvContent.setAdapter(mAdapter);
		mBtnSubmit=(Button)findViewById(R.id.ccwant_btn_submit_select);
		mBtnSubmit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("result",(Serializable)mPathList);
				setResult(RESULT_OK, intent);
				finish();
			}

		});
		steupSortPhotoToListView();
	}
	// 需要setContentView之前调用
	private void setTranslucentStatus() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			// 透明状态栏
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 透明导航栏
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			SystemStatusManager tintManager = new SystemStatusManager(this);
			tintManager.setStatusBarTintEnabled(true);
			// 设置状态栏的颜色
			tintManager.setStatusBarTintResource(R.color.mainTheme);
			getWindow().getDecorView().setFitsSystemWindows(true);
		}
	}
	private void steupSortPhotoToListView(){
		Intent intent =getIntent();
		mAlbumName=intent.getStringExtra("CCwantAlbumName");
		List<CCwantPhoto> list=(List<CCwantPhoto>)intent.getSerializableExtra("CCwantPhotoList");
		Collections.sort(list, mPhotoSortHelper.getComparator());
		mData.clear();
		mData.addAll(list);
		mAdapter.notifyDataSetChanged();
		mTxtTitle.setText(mAlbumName);
	}
	@Override
	public void onSelectListen(String path,boolean isCancel) {
		if(isCancel){
			mPathList.remove(path);
		}else{
			mPathList.add(path);
		}
		mBtnSubmit.setText("确定("+mPathList.size()+")");
	}

	@Override
	public void onOverSelectListen() {
		if(mToast == null){
			mToast = Toast.makeText(this,"达到图片选择上限",Toast.LENGTH_SHORT);
		}
		mToast.show();
	}


}
