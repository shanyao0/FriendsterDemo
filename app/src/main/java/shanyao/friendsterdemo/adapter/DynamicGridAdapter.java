package shanyao.friendsterdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import shanyao.friendsterdemo.R;


public class DynamicGridAdapter extends BaseAdapter {
	private int[] files;
	private Context mContext;
	private LayoutInflater mLayoutInflater;

	public DynamicGridAdapter(int[] files, Context context) {
		this.files = files;
		this.mContext = context;
		mLayoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return files == null ? 0 : files.length;
	}

	@Override
	public Object getItem(int position) {
		return files[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyGridViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new MyGridViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.item_dynamic_gridview,
					parent, false);
			
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.album_image);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (MyGridViewHolder) convertView.getTag();
		}
		int url = (int) getItem(position);
		if (getCount() == 1) {// 一张图片时的宽高
			viewHolder.imageView.setLayoutParams(new android.widget.AbsListView.LayoutParams(dip2px(150), dip2px(100)));
		}else if (getCount() == 2 ||getCount() == 4){// 2张或者4张
			viewHolder.imageView.setLayoutParams(new android.widget.AbsListView.LayoutParams(dip2px(65), dip2px(65)));
		}else{// 其他
			viewHolder.imageView.setLayoutParams(new android.widget.AbsListView.LayoutParams(dip2px( 65), dip2px(65)));
		}

		// 从网络获取图片
//		ImageLoader.getInstance().displayImage(url, viewHolder.imageView);
		viewHolder.imageView.setImageResource(url);
		return convertView;
	}

	private static class MyGridViewHolder {
		ImageView imageView;
	}
	/**
	 * dip转化成px
	 */
	public int dip2px(float dpValue) {
		final float scale = mContext.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}
