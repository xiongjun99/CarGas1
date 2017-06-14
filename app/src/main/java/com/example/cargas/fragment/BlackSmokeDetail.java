package com.example.cargas.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cargas.R;
import com.example.cargas.activity.MainActivity;
import com.example.cargas.adapter.MyGridViewAdapter;
import com.example.cargas.database.DB;
import com.example.cargas.message.MSG;
import com.example.cargas.message.MessageHandlerManager;
import com.example.cargas.model.BlackSmokeQueryResponse;

public class BlackSmokeDetail extends Fragment implements OnClickListener {

	private static final String TAG = "BlackSmokeDetail";
	private TextView back;
	private TextView carNumber;
	private TextView carColor;
	private TextView location;
	private TextView status;
	private TextView comment;
	private BlackSmokeQueryResponse showData;

	List<String> content = new ArrayList<String>();// 图片数据
	Context context;

	public BlackSmokeDetail(Context context) {
		this.context = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.black_smoke_detail, null);

		// 获取详情数据
		showData = (BlackSmokeQueryResponse) getArguments().getSerializable(
				"data");
		initView(view);
		return view;
	}

	// 初始化控件
	private void initView(View view) {
		back = (TextView) view.findViewById(R.id.bt_detail_back);
		carNumber = (TextView) view.findViewById(R.id.tv_register_carnumber);
		carColor = (TextView) view.findViewById(R.id.tv_register_color);
		location = (TextView) view.findViewById(R.id.tv_register_location);
		status = (TextView) view.findViewById(R.id.tv_register_status);
		comment = (TextView) view.findViewById(R.id.tv_register_comment);

		// 显示图片
		String path = Environment.getExternalStorageDirectory().getPath()
				+ File.separator + "TestPhoto" + File.separator;

		HashMap<String, String> query = DB.instance(context).query(
				"select * from dt_pic where DataType = 1 AND DataID = "
						+ showData.getServerId() + ";");

		if (query != null) {

			for (int i = 0; i < Integer.parseInt((query.get("records_num"))); i++) {
				// 数据库中的图片名带有日期
				String name = query.get("Name_" + i).substring(
						query.get("Name_" + i).indexOf(File.separator) + 1);
				content.add(path + name);
			}
		}

		GridView gridview = (GridView) view.findViewById(R.id.gridView1);
		adapter = new MyGridViewAdapter(context, content);
		gridview.setAdapter(adapter);
		// //////////////////////////////////////////////////////////////////
		if (showData != null) {
			carNumber.setText(showData.getCarNo());
			carColor.setText(showData.getCarColor());
			location.setText(showData.getAddress());
			// status.setText(showData.getPunishStatus());

			if (showData.getRegisterStatus().equals("0")) {
				status.setText("合格");

			} else if (showData.getRegisterStatus().equals("1")) {
				status.setText("不合格");
				status.setTextColor(context.getResources()
						.getColor(R.color.red));
			}

			comment.setText(showData.getFieldEvaluation());

		}

		back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.bt_detail_back:
			// TODO 点击返回
			getFragmentManager()
					.beginTransaction()
					.replace(R.id.main_framelayout,
							((MainActivity) getActivity()).getmBlack())
					.commit();
			break;

		default:
			break;
		}
	}
	
	private MyGridViewAdapter adapter;
}
