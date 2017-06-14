package com.king.photo.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.cargas.R;
import com.king.photo.util.Bimp;
import com.king.photo.util.ImageItem;

public class GridAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private int selectedPosition = -1;
	private boolean shape;
	Context context;
	public  ArrayList<ImageItem> tempSelectBitmap = new ArrayList<ImageItem>();

	public boolean isShape() {
		return shape;
	}

	public void setShape(boolean shape) {
		this.shape = shape;
	}

	public GridAdapter(Context context, ArrayList<ImageItem> tempSelectBitmap) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.tempSelectBitmap = tempSelectBitmap;
	}
	
	public void setcontent(ArrayList<ImageItem> tempSelectBitmap){
		this.tempSelectBitmap = tempSelectBitmap;

	}

	
	public int getCount() {
		if (tempSelectBitmap.size() == 9) {
			return 9;
		}
		return (tempSelectBitmap.size() + 1);
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int arg0) {
		return 0;
	}

	public void setSelectedPosition(int position) {
		selectedPosition = position;
	}

	public int getSelectedPosition() {
		return selectedPosition;
	}

	public View getView(final int position, View convertView,
			ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_published_grida,
					parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.item_grida_image);

			// 删除按钮
			holder.delete = (ImageView) convertView
					.findViewById(R.id.item_grida_delete);
			
			holder.play = (ImageView) convertView
					.findViewById(R.id.item_grida_play);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (position == tempSelectBitmap.size()) {
			holder.image.setImageBitmap(BitmapFactory.decodeResource(
					context.getResources(), R.drawable.addpic));

			// 加号不能删除,没有播放按钮
			holder.delete.setVisibility(View.GONE);
			holder.play.setVisibility(View.GONE);
			if (position == 9) {
				holder.image.setVisibility(View.GONE);
			}
		} else {
			holder.image.setImageBitmap(tempSelectBitmap.get(position)
					.getBitmap());
			holder.delete.setVisibility(View.VISIBLE);
			holder.play.setVisibility(View.GONE);
			
			//视频才有播放按钮
			if (tempSelectBitmap.get(position).getImagePath().contains("mp4")){
				holder.play.setVisibility(View.VISIBLE);
			}
		}

		holder.delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				listener.notityUpdate(position);
			}
		});

		return convertView;
	}

	public class ViewHolder {
		public ImageView image, delete,play;
	}
	
	private onPicClicked listener;
	public void setDeleteListener(onPicClicked listener){
		this.listener = listener;
	}
	
	public interface onPicClicked{
		public void notityUpdate(int position);
	}
}
