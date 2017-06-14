package com.example.cargas.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cargas.R;
import com.example.cargas.fragment.RandomInspectionDetail;
import com.example.cargas.model.SamplingCreateQueryResponse;
import com.example.cargas.utils.Utils;
import com.example.cargas.view.CustomDialog;
import com.example.cargas.view.DetailCustomDialog;

public class RandomInspectionAdapter extends BaseAdapter {

	Context context;
	private LayoutInflater mInflater;
	private List<SamplingCreateQueryResponse> responseSampling = new ArrayList<SamplingCreateQueryResponse>();
	private SamplingCreateQueryResponse sampleData;
	FragmentManager fm;
	
	RandomInspectionDetail roadDetail ;

	public RandomInspectionAdapter(Context context,
			List<SamplingCreateQueryResponse> responseSampling,
			FragmentManager fm, RandomInspectionDetail roadDetail) {
		this.context = context;
		mInflater = LayoutInflater.from(context);
		this.responseSampling = responseSampling;
		this.fm = fm;
		
		this.roadDetail = roadDetail;
	}

	@Override
	public int getCount() {
		return responseSampling.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();

			convertView = mInflater.inflate(R.layout.random_inspection_item,
					null);
			viewHolder.time = (TextView) convertView
					.findViewById(R.id.random_time);
			viewHolder.name = (TextView) convertView
					.findViewById(R.id.random_name);
			viewHolder.people = (TextView) convertView
					.findViewById(R.id.random_people);

			viewHolder.eye = (ImageView) convertView
					.findViewById(R.id.random_eye);
			viewHolder.detail = (Button) convertView
					.findViewById(R.id.random_detail_bt);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		sampleData = responseSampling.get(position);

		
		if (sampleData.getStartTime().contains("T")){
			
			viewHolder.time.setText(sampleData.getStartTime().replace("T", " "));
		} else {
			viewHolder.time.setText(sampleData.getStartTime());
		}
		viewHolder.name.setText(sampleData.getSamplingName());
		
		viewHolder.people.setText(sampleData.getChargePerson());

		viewHolder.detail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle data = new Bundle();
//				// 传递数据 传递SamplingCreateID
				data.putInt("data", Integer.parseInt(responseSampling.get(position).getSamplingCreateID()));
				roadDetail.setArguments(data);

				fm.beginTransaction().replace(R.id.main_framelayout, roadDetail)
						.commit();
			}
		});
		
		viewHolder.eye.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final DetailCustomDialog dialog = new DetailCustomDialog(context,responseSampling.get(position));
				dialog.setCanceledOnTouchOutside(false);
				dialog.show();
			}
		});

		return convertView;
	}

	private class ViewHolder {
		TextView name, time, people, location;
		ImageView eye;
		Button detail;
	}

}
