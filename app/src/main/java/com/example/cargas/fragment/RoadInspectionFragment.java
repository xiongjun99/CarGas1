package com.example.cargas.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cargas.R;
import com.example.cargas.adapter.RoadInspectionAdapter;
import com.example.cargas.communication.ComInterface;
import com.example.cargas.communication.ComMsg;
import com.example.cargas.database.DB;
import com.example.cargas.message.MSG;
import com.example.cargas.message.MessageHandlerManager;
import com.example.cargas.model.RoadCheckQueryResponse;
import com.example.cargas.view.DoubleDatePickerDialog2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class RoadInspectionFragment extends Fragment {

	private static final String TAG = "RoadInspectionFragment";
	private TextView mRegister;// 登记
	private ListView mList;
	Context context;
	private RoadInspectionAdapter adapter;

	// 数据列表
	List<RoadCheckQueryResponse> mRoadData = new ArrayList<RoadCheckQueryResponse>();
	private RoadInspectionRegiseter roadRegister;
	private EditText startTime;
	private EditText endTime;

	public RoadInspectionFragment(Context context) {
		this.context = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.road_data_show, null);
		roadRegister = new RoadInspectionRegiseter(context);
		initViews(view);
		initData();

		register_message();
		return view;
	}

	// 初始化数据
	private void initData() {

		// // 清除数据
		// mRoadData.clear();
		// // 伪造的数据
		// for (int i = 0; i < 10; i++) {
		// RoadCheckQueryResponse responseData = new RoadCheckQueryResponse(
		// "", "鄂A78208", "蓝色", "小型越野车", "非运营", "2017-07-15",
		// "IC4JNND", "无", "28人", "100", "国IV", "123", "11.4", "99.6",
		// "64.9", "50", "0.99", "0.2", "34", "2", "21", "0", "0.18",
		// "24", "11", "21", "0", "0", "武汉市卓刀泉南路", "2222", "999",
		// "2015-07-18", "2015-07-18", "评价");
		//
		// mRoadData.add(responseData);
		// }
		//
		mRoadData.clear();
		// 从数据库中获取数据
		HashMap<String, String> query = DB.instance(context).query(
				"select * from dt_roadCheckRegister;");

		if (query != null) {// query.get("_"+i)
			for (int i = 0; i < Integer.parseInt((query.get("records_num"))); i++) {
				RoadCheckQueryResponse blackData1 = new RoadCheckQueryResponse(
						query.get("id_" + i), query.get("CarNo_" + i),
						query.get("CarColor_" + i), query.get("CarType_" + i),
						query.get("UseNature_" + i),
						query.get("RegistrationDate_" + i),
						query.get("BrandModel_" + i), query.get("EngineModel_"
								+ i), query.get("Passengers_" + i),
						query.get("MaxTotalMass_" + i),
						query.get("EmissionStandards_" + i),
						query.get("EmissionLimits_" + i),
						query.get("AmbientTemperature_" + i),
						query.get("EnvironmentalPressure_" + i),
						query.get("RelativeHumidity_" + i),
						query.get("OilTemperature_" + i),
						query.get("ExcessAirRatio_" + i), query.get("Low_co_"
								+ i), query.get("Low_hc_" + i),
						query.get("Low_no_" + i), query.get("Low_o2_" + i),
						query.get("Low_co2_" + i), query.get("High_co_" + i),
						query.get("High_hc_" + i), query.get("High_no_" + i),
						query.get("High_o2_" + i), query.get("High_co2_" + i),
						query.get("CheckResult_" + i),
						query.get("CheckAddress_" + i), query.get("Longitude_"
								+ i), query.get("Latitude_" + i),
						query.get("RegisterUser_" + i),
						query.get("RegisterTime_" + i),
						query.get("Remark_" + i), query.get("ServerId_" + i),
						query.get("fueltype_" + i), query.get("Smokeopacity1_"
								+ i), query.get("Smokeopacity2_" + i),
						query.get("Smokeopacity3_" + i),
						query.get("Smokeopacityavg_" + i),
						query.get("PenaltyReceivable_" + i),
						query.get("PenaltyCollected_" + i),
						query.get("UserResult_" + i));

				mRoadData.add(blackData1);
			}
		}

		adapter.notifyDataSetChanged();
	}

	// 初始化布局
	private void initViews(View view) {
		mRegister = (TextView) view.findViewById(R.id.road_data_register);
		mList = (ListView) view.findViewById(R.id.road_data_listview);
		final LinearLayout frameLayout = (LinearLayout) view
				.findViewById(R.id.road_show);

		adapter = new RoadInspectionAdapter(context, mRoadData, frameLayout,
				getFragmentManager());
		mList.setAdapter(adapter);

		mRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 登记 跳转到登记页面
				// frameLayout.setVisibility(View.GONE);
				FragmentManager fm = getFragmentManager();
				fm.beginTransaction()
						.replace(R.id.main_framelayout, roadRegister).commit();
			}
		});

		startTime = (EditText) view.findViewById(R.id.road_starttime_et);
		endTime = (EditText) view.findViewById(R.id.road_endtime_et);

		startTime.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					Calendar c = Calendar.getInstance();

					// 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
					new DoubleDatePickerDialog2(context, 0,
							new DoubleDatePickerDialog2.OnDateSetListener() {

								@Override
								public void onDateSet(
										DatePicker startDatePicker,
										int startYear, int startMonthOfYear,
										int startDayOfMonth,
										DatePicker endDatePicker, int endYear,
										int endMonthOfYear, int endDayOfMonth) {
									String mstartTime = String.format(
											"%d-%d-%d", startYear,
											startMonthOfYear + 1,
											startDayOfMonth);
									String mendTime = String.format("%d-%d-%d",
											endYear, endMonthOfYear + 1,
											endDayOfMonth);
									startTime.setText(mstartTime);
									endTime.setText(mendTime);

									// 然后从服务器上拉去数据
									// 路检查询接口
									ComMsg.RoadCheckQueryReq roadReq = new ComMsg.RoadCheckQueryReq();
									roadReq.SessionID = MSG.SessionID;
									roadReq.StartTime = mstartTime;
									roadReq.EndTime = mendTime;

									ComInterface.RoadCheckQuery(context,
											roadReq);

								}

							}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
									.get(Calendar.DATE), false).show();

				}
				return true;
			}
		});

		endTime.setOnTouchListener(new OnTouchListener() {

			// @Override
			// public boolean onTouch(View v, MotionEvent event) {
			// // TODO Auto-generated method stub
			// return true;
			// }

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					Calendar c = Calendar.getInstance();

					// 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
					new DoubleDatePickerDialog2(context, 0,
							new DoubleDatePickerDialog2.OnDateSetListener() {

								@Override
								public void onDateSet(
										DatePicker startDatePicker,
										int startYear, int startMonthOfYear,
										int startDayOfMonth,
										DatePicker endDatePicker, int endYear,
										int endMonthOfYear, int endDayOfMonth) {
									String mstartTime = String.format(
											"%d-%d-%d", startYear,
											startMonthOfYear + 1,
											startDayOfMonth);
									String mendTime = String.format("%d-%d-%d",
											endYear, endMonthOfYear + 1,
											endDayOfMonth);
									startTime.setText(mstartTime);
									endTime.setText(mendTime);

									// 然后从服务器上拉去数据
									// 路检查询接口
									ComMsg.RoadCheckQueryReq roadReq = new ComMsg.RoadCheckQueryReq();
									roadReq.SessionID = MSG.SessionID;
									roadReq.StartTime = mstartTime;
									roadReq.EndTime = mendTime;

									ComInterface.RoadCheckQuery(context,
											roadReq);

								}

							}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
									.get(Calendar.DATE), false).show();

				}
				return true;
			}
		});

	}

	public RoadInspectionRegiseter getRoadRegister() {

		return roadRegister;
	}

	// ////////////////////////////////////////////////////
	// 思想：其实这里的MessageHandler是没什么作用的，如果说要有作用的话，那唯一的作用就是在当前页面退出的时候
	// 将handler置为了null

	public static Handler _message_handler = null;// 界面消息处理句柄

	// 注册地图处理消息
	private void register_message() {
		_message_handler = new MainMessageHandler(Looper.getMainLooper());

		// 根据类名和what来标志arraylist中的handler
		// 注册就是将handler添加到ArrayList中，然后可以在其他地方通过handler来发送消息
		MessageHandlerManager.get_instance().register(_message_handler,
				MSG.Road_Inspection_QUERY_SUCCESS, TAG);
		MessageHandlerManager.get_instance().register(_message_handler,
				MSG.Road_Inspection_QUERY_FAIL, TAG);
	}

	// 注销地图处理消息
	private void unregister_message() {
		// 将handler从ArrayList中移除
		MessageHandlerManager.get_instance().unregister(
				MSG.Road_Inspection_QUERY_SUCCESS, TAG);
		MessageHandlerManager.get_instance().unregister(
				MSG.Road_Inspection_QUERY_FAIL, TAG);
	}

	// 地图消息处理句柄
	private class MainMessageHandler extends Handler {
		MainMessageHandler(Looper looper) {// 与指定的looper关联，而不是默认的looper
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG.Road_Inspection_QUERY_SUCCESS:

				initData();
				break;

			case MSG.Road_Inspection_QUERY_FAIL:
				Toast.makeText(context, "服务器连接异常", Toast.LENGTH_SHORT).show();

				break;
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregister_message();
	}
}
