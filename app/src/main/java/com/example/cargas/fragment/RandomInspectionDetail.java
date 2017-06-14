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
import com.example.cargas.adapter.RandomInspectionDetailAdapter;
import com.example.cargas.communication.ComInterface;
import com.example.cargas.communication.ComMsg;
import com.example.cargas.database.DB;
import com.example.cargas.message.MSG;
import com.example.cargas.message.MessageHandlerManager;
import com.example.cargas.model.SamplingRegisterQueryResponse;
import com.example.cargas.view.DoubleDatePickerDialog2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class RandomInspectionDetail extends Fragment {
	private static final String TAG = "RandomInspectionDetail";
	private TextView mRegister;// 登记
	private ListView mList;
	Context context;
	private RandomInspectionDetailAdapter adapter;

	// 数据列表
	List<SamplingRegisterQueryResponse> mRoadData = new ArrayList<SamplingRegisterQueryResponse>();
	private RandomInspectionDetailRegister randomInspectionDetailRegister;
	private int samplingCreateID;

	public RandomInspectionDetail(Context context) {
		this.context = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.check_data_show, null);
		randomInspectionDetailRegister = new RandomInspectionDetailRegister(
				context);

		samplingCreateID = getArguments().getInt("data");

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
		// SamplingRegisterQueryResponse responseData = new
		// SamplingRegisterQueryResponse(
		// "123", "234", "鄂A18022", "蓝牌", "小型越野客车", "非运营",
		// "2017-07-15", "IC4JNND", "无", "28人", "1000", "国IV",
		// "emissionLimits", "ambientTemperature",
		// "environmentalPressure", "relativeHumidity",
		// "smokeOpacity1", "smokeOpacity2", "smokeOpacity3",
		// "smokeOpacityAVG", "checkResult", "registerUser",
		// "checkTime", "checkAddress", "remark");
		//
		// mRoadData.add(responseData);
		// }

		// 查询数据库
		mRoadData.clear();
		// 从数据库中获取数据
		HashMap<String, String> query = DB.instance(context).query(
				"select * from dt_samplingRegister where  SamplingCreateID = "
						+ samplingCreateID + ";");

		if (query != null) {// query.get("_"+i)
			for (int i = 0; i < Integer.parseInt((query.get("records_num"))); i++) {

				// String registerDate = query.get("RegistrationDate_" + i);
				// if (query.get("RegistrationDate_" + i).contains("T")) {
				// registerDate = query.get("RegistrationDate_" + i).replace(
				// "T", " ");
				// } else {
				// registerDate = query.get("RegistrationDate_" + i);
				// }

				String registerDate = query.get("CheckTime_" + i);
				if (query.get("CheckTime_" + i).contains("T")) {
					registerDate = query.get("CheckTime_" + i)
							.replace("T", " ");
				} else {
					registerDate = query.get("CheckTime_" + i);
				}

				SamplingRegisterQueryResponse responseData = new SamplingRegisterQueryResponse(
						query.get("id_" + i), String.valueOf(samplingCreateID),
						query.get("CarNo_" + i), query.get("CarColor_" + i),
						query.get("CarType_" + i), query.get("UseNature_" + i),
						registerDate, query.get("BrandModel_" + i),
						query.get("EngineModel_" + i), query.get("Passengers_"
								+ i), query.get("MaxTotalMass_" + i),
						query.get("EmissionStandards_" + i),
						query.get("EmissionLimits_" + i),
						query.get("AmbientTemperature_" + i),
						query.get("EnvironmentalPressure_" + i),
						query.get("RelativeHumidity_" + i),
						query.get("SmokeOpacity1_" + i),
						query.get("SmokeOpacity2_" + i),
						query.get("SmokeOpacity3_" + i),
						query.get("SmokeOpacityAVG_" + i),
						query.get("CheckResult_" + i),
						query.get("RegisterUser_" + i), query.get("CheckTime_"
								+ i), query.get("CheckAddress_" + i), "",
						query.get("ServerId_" + i) + "", query.get("fueltype_"
								+ i), query.get("Oiltemperature_" + i),
						query.get("Excessairratio_" + i), query.get("Low_co_"
								+ i), query.get("Low_hc_" + i),
						query.get("Low_no_" + i), query.get("Low_o2_" + i),
						query.get("Low_co2_" + i), query.get("High_co_" + i),
						query.get("High_hc_" + i), query.get("High_no_" + i),
						query.get("High_o2_" + i), query.get("High_co2_" + i),
						query.get("PenaltyReceivable_" + i),
						query.get("PenaltyCollected_" + i),
						query.get("UserResult_" + i));

				mRoadData.add(responseData);
			}
		}

		adapter.notifyDataSetChanged();
	}

	// 初始化布局
	private void initViews(View view) {
		mRegister = (TextView) view.findViewById(R.id.check_data_register);
		mList = (ListView) view.findViewById(R.id.check_data_listview);
		final LinearLayout frameLayout = (LinearLayout) view
				.findViewById(R.id.check_show);

		adapter = new RandomInspectionDetailAdapter(context, mRoadData,
				getFragmentManager());
		mList.setAdapter(adapter);

		mRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 登记 跳转到登记页面
				// frameLayout.setVisibility(View.GONE);
				FragmentManager fm = getFragmentManager();
				// 把samplingCreateID传递到登记页面
				Bundle data = new Bundle();
				data.putInt("data", samplingCreateID);
				randomInspectionDetailRegister.setArguments(data);
				fm.beginTransaction()
						.replace(R.id.main_framelayout,
								randomInspectionDetailRegister).commit();
			}
		});

		startTime = (EditText) view.findViewById(R.id.check_starttime_et);
		endTime = (EditText) view.findViewById(R.id.check_endtime_et);

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

									// 抽检车辆列表查询接口
									ComMsg.SamplingRegisterQueryReq samplingRegisterQueryReq = new ComMsg.SamplingRegisterQueryReq();
									samplingRegisterQueryReq.SessionID = MSG.SessionID;
									samplingRegisterQueryReq.StartTime = mstartTime;
									samplingRegisterQueryReq.EndTime = mendTime;

									ComInterface.SamplingRegisterQuery(context,
											samplingRegisterQueryReq);

								}

							}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
									.get(Calendar.DATE), false).show();

				}
				return true;
			}
		});

		endTime.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return true;
			}
		});

	}

	public RandomInspectionDetailRegister getRandomInspectionDetailRegister() {
		return randomInspectionDetailRegister;
	}

	// ////////////////////////////////////////////////////
	// 思想：其实这里的MessageHandler是没什么作用的，如果说要有作用的话，那唯一的作用就是在当前页面退出的时候
	// 将handler置为了null

	public static Handler _message_handler = null;// 界面消息处理句柄
	private EditText startTime;
	private EditText endTime;

	// 注册地图处理消息
	private void register_message() {
		_message_handler = new MainMessageHandler(Looper.getMainLooper());

		// 根据类名和what来标志arraylist中的handler
		// 注册就是将handler添加到ArrayList中，然后可以在其他地方通过handler来发送消息
		MessageHandlerManager.get_instance().register(_message_handler,
				MSG.Random_Inspection_Detail_QUERY_SUCCESS, TAG);
		MessageHandlerManager.get_instance().register(_message_handler,
				MSG.Random_Inspection_Detail_QUERY_FAIL, TAG);
	}

	// 注销地图处理消息
	private void unregister_message() {
		// 将handler从ArrayList中移除
		MessageHandlerManager.get_instance().unregister(
				MSG.Random_Inspection_Detail_QUERY_SUCCESS, TAG);
		MessageHandlerManager.get_instance().unregister(
				MSG.Random_Inspection_Detail_QUERY_FAIL, TAG);
	}

	// 地图消息处理句柄
	private class MainMessageHandler extends Handler {
		MainMessageHandler(Looper looper) {// 与指定的looper关联，而不是默认的looper
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG.Random_Inspection_Detail_QUERY_SUCCESS:

				initData();
				break;

			case MSG.Random_Inspection_Detail_QUERY_FAIL:
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
