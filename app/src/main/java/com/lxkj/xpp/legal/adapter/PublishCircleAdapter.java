package com.lxkj.xpp.legal.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.ccwant.photo.selector.load.CCwantImageLoader;
import com.lxkj.xpp.legal.R;

import java.util.List;

/**
 * Created by 熊萍萍 on 2017/3/26/026.
 */

public class PublishCircleAdapter extends BaseAdapter{
    private Context mContext;
    private LayoutInflater inflater;
    List<String> mImageList;
    //	private int mMaxPosition; // 最后一张图片的下标 ，如果是“+”图片，则返回“+”图片的下标
    private int maxPicNum;

    public void setMaxPicNum(int maxPicNum) {
        this.maxPicNum = maxPicNum;
    }

    public PublishCircleAdapter(Context context, List<String> list) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        mImageList = list;
    }

    public int getCount() {
        if (mImageList.size() == maxPicNum) {
            return mImageList.size();
        } else {
            return mImageList.size() + 1;
        }
    }
//	public int getMaxPosition(){
//		return mMaxPosition;
//	}

    public Object getItem(int arg0) {

        return mImageList.get(arg0);
    }

    public long getItemId(int arg0) {

        return arg0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.circle_item_publish, parent, false);
            holder.image = (ImageView) convertView.findViewById(R.id.item_image_publish_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        if (isAddPic(position)) {
            holder.image.setTag("default");
            holder.image.setImageResource(R.mipmap.zili_tianjia);
        } else {
            final String path = mImageList.get(position);
          CCwantImageLoader.getIntance(mContext).ImageLoader(path, holder.image);
        }
        return convertView;
    }

    public class ViewHolder {
        public ImageView image;
    }

    /**
     * 根据positioon判断按下的图片是否为最后一张的“+”图片
     *
     * @param position
     * @return
     */
    public boolean isAddPic(int position) {
        Log.d("PublishCircleAdapter", "the maxPicNum is " + maxPicNum + "and the position is " + position + " and the mImageList's size is " + mImageList.size());
        if (position == mImageList.size())
            return true;
        return false;
    }
}
