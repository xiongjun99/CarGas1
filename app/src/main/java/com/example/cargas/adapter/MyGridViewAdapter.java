package com.example.cargas.adapter;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.cargas.R;
import com.example.cargas.activity.PlayVideo;
import com.example.cargas.activity.ShowPicture;
import com.example.cargas.database.DB;
import com.example.cargas.message.MSG;
import com.example.cargas.utils.Utils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class MyGridViewAdapter extends BaseAdapter {

	Context ctx;
	List<String> content;

	public MyGridViewAdapter(Context ctx, List<String> content) {
		this.ctx = ctx;
		this.content = content;
	}

	@Override
	public int getCount() {
		return content.size();
	}

	@Override
	public Object getItem(int position) {
		return content.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(ctx).inflate(
					R.layout.griditem_addpic, null);
			holder.img = (ImageView) convertView.findViewById(R.id.imageView1);
			holder.play = (ImageView) convertView.findViewById(R.id.imageView1_play);
			
			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		File file = new File(content.get(position));
		String name = content.get(position).substring(
				content.get(position).lastIndexOf(File.separator) + 1);
		
		
		
		if (!file.exists()
				|| (file.exists() && (!file.canRead() || !file.canWrite()))) {

			HashMap<String, String> query = DB.instance(ctx).query(
					"select * from dt_pic where Name like '%" + name + "';");
			if (query != null) {

				String name1 = query.get("Name_0");
				String localurl = Utils.Dir + name;
				HttpUtils http = new HttpUtils();
				HttpHandler handler = http.download(MSG.HFS + name1,
						localurl, true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
						true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
						new RequestCallBack<File>() {

							@Override
							public void onStart() {
							}

							@Override
							public void onLoading(long total, long current,
									boolean isUploading) {
							}

							@Override
							public void onSuccess(
									ResponseInfo<File> responseInfo) {
								
								notifyDataSetChanged();
								
							}

							@Override
							public void onFailure(HttpException error,
									String msg) {
							}
						});

			}
		} else {
			
			Glide.with(ctx).load(content.get(position)).into(holder.img);
		}


		if(name.contains("jpg")){
			holder.play.setVisibility(View.GONE);
			
			holder.img.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					//用自定义的图片查看器
					Intent intent = new Intent(ctx, ShowPicture.class);
					intent.putExtra("url", content.get(position));
					ctx.startActivity(intent);
//					Intent intent = new Intent(Intent.ACTION_VIEW);
//					intent.setDataAndType(
//							Uri.fromFile(new File(content.get(position))),
//							"image/*");
//					ctx.startActivity(intent);
				}
			});
			
		} else if (name.contains("mp4")){
			holder.play.setVisibility(View.VISIBLE);
			
			holder.play.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
//					Intent intent = new Intent(Intent.ACTION_VIEW);
//					intent.setDataAndType(
//							Uri.fromFile(new File(content.get(position))),
//							"video/*");
//					ctx.startActivity(intent);
					
					Intent videoIntent = new Intent(ctx,
							PlayVideo.class);
					videoIntent.putExtra("path", content.get(position));
					ctx.startActivity(videoIntent);
				}
			});
		}
		

		return convertView;
	}

	private static class ViewHolder {
		ImageView img, play;
	}

}
