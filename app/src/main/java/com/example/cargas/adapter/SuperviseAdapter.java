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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cargas.R;
import com.example.cargas.database.DB;
import com.example.cargas.fragment.SuperviseDetail;
import com.example.cargas.model.VaporRecoveryQueryResponse;

public class SuperviseAdapter extends BaseAdapter  {

	Context context;
	private LayoutInflater inflater;
	FragmentManager fm;
	LinearLayout frameLayout;

	List<VaporRecoveryQueryResponse> mVaporData;
	private VaporRecoveryQueryResponse vapor;

	public SuperviseAdapter(Context context,
			List<VaporRecoveryQueryResponse> mVaporData,
			LinearLayout frameLayout, FragmentManager fm) {
		inflater = LayoutInflater.from(context);
		this.fm = fm;
		this.frameLayout = frameLayout;
		this.mVaporData = mVaporData;
		this.context = context;
	}

	@Override
	public int getCount() {
		return mVaporData.size();
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
			convertView = inflater.inflate(
					R.layout.supervise_show_listview_item, null);

			viewHolder.time = (TextView) convertView
					.findViewById(R.id.supervise_time);
			viewHolder.constructor = (TextView) convertView
					.findViewById(R.id.supervise_constructor);
			viewHolder.isOk = (TextView) convertView
					.findViewById(R.id.supervise_ok);
			viewHolder.detail = (Button) convertView
					.findViewById(R.id.supervise_detail_bt);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		vapor = mVaporData.get(position);
		
		
		if (vapor.getExecutionTime().contains("T")){
			viewHolder.time.setText(vapor.getExecutionTime().replace("T", " "));
		} else {
			viewHolder.time.setText(vapor.getExecutionTime());
		}
		
		viewHolder.constructor.setText(vapor.getOrganizationID());
		

		if (vapor.getCheckResult().equals("0")) {
			viewHolder.isOk.setText("合格");
		} else {
			viewHolder.isOk.setText("不合格");
		}

		viewHolder.detail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SuperviseDetail detail = new SuperviseDetail(context);
				Bundle data = new Bundle();
				data.putSerializable("data", (Serializable) mVaporData.get(position));
				detail.setArguments(data);

				fm.beginTransaction().replace(R.id.main_framelayout, detail)
						.commit();
			}
		});

		return convertView;
	}

	private class ViewHolder {
		TextView time, constructor, isOk;// 合格
		Button detail;
	}


}
