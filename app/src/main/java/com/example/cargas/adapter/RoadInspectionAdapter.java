package com.example.cargas.adapter;

import java.io.Serializable;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cargas.R;
import com.example.cargas.fragment.BlackSmokeDetail;
import com.example.cargas.fragment.BlackSmokeRegiseter;
import com.example.cargas.fragment.RoadInspectionDetail;
import com.example.cargas.fragment.RoadInspectionRegiseter;
import com.example.cargas.model.RoadCheckQueryResponse;

public class RoadInspectionAdapter extends BaseAdapter {
	LayoutInflater mInflater;
	Context context;
	FragmentManager fm;
	LinearLayout frameLayout;

	// 数据列表
	List<RoadCheckQueryResponse> mRoadData = new ArrayList<RoadCheckQueryResponse>();
	private RoadCheckQueryResponse response;

	public RoadInspectionAdapter(Context context,List<RoadCheckQueryResponse> mRoadData, LinearLayout layout,
			FragmentManager fm) {
		mInflater = LayoutInflater.from(context);
		this.context = context;
		this.fm = fm;
		frameLayout = layout;
		this.mRoadData = mRoadData;
	}

	@Override
	public int getCount() {
//		return 10;
		return mRoadData.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();

			convertView = mInflater.inflate(R.layout.check_item,
					null);
			viewHolder.carNumber = (TextView) convertView
					.findViewById(R.id.check_car_number);
			viewHolder.time = (TextView) convertView
					.findViewById(R.id.check_time_tv);
			viewHolder.status = (TextView) convertView
					.findViewById(R.id.check_status_tv);
			viewHolder.color = (TextView) convertView
					.findViewById(R.id.check_color_tv);
			viewHolder.detail = (Button) convertView
					.findViewById(R.id.check_detail_bt);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		//被选中的数据, 
		response = mRoadData.get(position);
		viewHolder.carNumber.setText(response.getCarNo());
		
		
		if (response.getRegisterTime().contains("T")){
			viewHolder.time.setText(response.getRegisterTime().replace("T", " "));
		} else {
			viewHolder.time.setText(response.getRegisterTime());
		}
		
		viewHolder.color.setText(response.getCarColor());
		
		//检测结果0-合格，1-不合格
		if (response.getCheckResult().equals("0")){
			viewHolder.status.setText("合格");
		} else if (response.getCheckResult().equals("1")) {
			viewHolder.status.setText("不合格");
		}
		

		viewHolder.detail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RoadInspectionDetail roadDetail = new RoadInspectionDetail(context);
				Bundle data  = new Bundle();
				//传递数据
				data.putSerializable("data", (Serializable) mRoadData.get(position));
				roadDetail.setArguments(data);
				
				fm.beginTransaction().replace(R.id.main_framelayout, roadDetail)
						.commit();

			}
		});

		return convertView;
	}

	private class ViewHolder {
		TextView carNumber, time, status;
		TextView color;
		Button detail;
	}


}
