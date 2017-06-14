package com.example.cargas.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cargas.R;
import com.example.cargas.activity.MainActivity;
import com.example.cargas.adapter.MyGridViewAdapter;
import com.example.cargas.database.DB;
import com.example.cargas.model.VaporRecoveryQueryResponse;

public class SuperviseDetail extends Fragment implements OnClickListener {

	private TextView back;
	private VaporRecoveryQueryResponse vaporResponse;
	private TextView time;
	private TextView org;
	private TextView loc;
	private TextView result;
	private TextView opnion;

	List<String> content = new ArrayList<String>();// 图片数据
	Context context;

	public SuperviseDetail(Context context) {
		this.context = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.supervise_list_detail, null);
		vaporResponse = (VaporRecoveryQueryResponse) getArguments().get("data");
		initView(view);

		initData();
		return view;
	}

	private void initData() {

		if (vaporResponse != null) {
			time.setText(vaporResponse.getExecutionTime());
			org.setText(vaporResponse.getOrganizationID());
			loc.setText(vaporResponse.getAddress());

			opnion.setText(vaporResponse.getOpinion());

			if (vaporResponse.getCheckResult().equals("0")) {
				result.setText("合格");
				result.setTextColor(getResources().getColor(R.color.myblue1));
			} else {
				result.setText("不合格");
				result.setTextColor(getResources().getColor(R.color.red));
			}

		}
	}

	private void initView(View view) {
		back = (TextView) view.findViewById(R.id.bt_detail_back);

		time = (TextView) view.findViewById(R.id.tv_supervise_list_carnumber);
		org = (TextView) view.findViewById(R.id.tv_supervise_list_color);
		loc = (TextView) view.findViewById(R.id.tv_supervise_list_location);
		result = (TextView) view.findViewById(R.id.tv_supervise_list_status);
		opnion = (TextView) view.findViewById(R.id.tv_supervise_list_comment);

		back.setOnClickListener(this);

		// 显示图片
		String path = Environment.getExternalStorageDirectory().getPath()
				+ File.separator + "TestPhoto" + File.separator;

		HashMap<String, String> query = DB.instance(context).query(
				"select * from dt_pic where DataType = 4 AND DataID = "
						+ vaporResponse.getServerId() + ";");

		if (query != null) {

			for (int i = 0; i < Integer.parseInt((query.get("records_num"))); i++) {
				// 数据库中的图片名带有日期
				String name = query.get("Name_" + i).substring(
						query.get("Name_" + i).indexOf(File.separator) + 1);
				content.add(path + name);
			}
		}

		GridView gridview = (GridView) view
				.findViewById(R.id.supervise_gridview);
		MyGridViewAdapter adapter = new MyGridViewAdapter(context, content);
		gridview.setAdapter(adapter);
		// //////////////////////////////////////////////////////////////////
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_detail_back:
			getFragmentManager()
					.beginTransaction()
					.replace(R.id.main_framelayout,
							((MainActivity) getActivity()).getmSuper())
					.commit();

			break;

		default:
			break;
		}
	}
}
