package com.example.cargas.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cargas.R;
import com.example.cargas.adapter.RandomInspectionAdapter;
import com.example.cargas.communication.ComInterface;
import com.example.cargas.communication.ComMsg;
import com.example.cargas.database.DB;
import com.example.cargas.message.MSG;
import com.example.cargas.message.MessageHandlerManager;
import com.example.cargas.model.SamplingCreateQueryResponse;
import com.example.cargas.view.CustomDialog;
import com.example.cargas.view.CustomDialog.ClickListenerInterface;
import com.example.cargas.view.DoubleDatePickerDialog2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class RandomInspectionFragment extends Fragment implements
		OnClickListener {

	private static final String TAG = "RandomInspectionFragment";

	private Context context;

	// 查询接口返回的数据
	private List<SamplingCreateQueryResponse> responseSampling = new ArrayList<SamplingCreateQueryResponse>();

	private RandomInspectionDetail roadDetail;

	private RandomInspectionAdapter adapter;

	public RandomInspectionFragment(Context context) {
		this.context = context;
	}

	public RandomInspectionFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.random_inspection_show, null);
		roadDetail = new RandomInspectionDetail(context);
		
		initView(view);
		
		register_message();
		return view;
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		initData();
//		Utils.showToast(context, "resume");
	}
	
	

	private void initData() {

		responseSampling.clear();
		for (int i = 0; i < 10; i++) {
			SamplingCreateQueryResponse data = new SamplingCreateQueryResponse(
					"123", "727公交场站车辆抽检", "建设大道110号", "罗师傅", "小李",
					"2016-07-27", "2016-08-28", "ddd", "hello register");
			responseSampling.add(data);
		}

		// 从数据库中读取数据
		responseSampling.clear();

		// 查询数据库
		HashMap<String, String> query = DB.instance(context).query(
				"SELECT * FROM dt_samplingCreate;");

		if (query != null) {// query.get("_" + i)

			for (int i = 0; i < Integer.parseInt((query.get("records_num"))); i++) {

				SamplingCreateQueryResponse data = new SamplingCreateQueryResponse(
						query.get("ServerId_" + i), query.get("SamplingName_" + i),
						query.get("SamplingAddress_" + i),
						query.get("ChargePerson_" + i),
						query.get("SamplingPerson_" + i),
						query.get("StartTime_" + i), query.get("EndTime_" + i),
						query.get("RegisterUser_" + i), "");
				responseSampling.add(data);

			}
		}

		adapter.notifyDataSetChanged();
	}

	private void initView(View view) {
		TextView register = (TextView) view.findViewById(R.id.random_register);
		register.setOnClickListener(this);

		ListView listview = (ListView) view.findViewById(R.id.random_listview);
		adapter = new RandomInspectionAdapter(context,
				responseSampling, getFragmentManager(), roadDetail);
		listview.setAdapter(adapter);
		
		
		startTime = (EditText) view.findViewById(R.id.random_starttime_et);
		endTime = (EditText) view.findViewById(R.id.random_endtime_et);

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
									
									// 抽检查询接口
									 ComMsg.SamplingCreateQueryReq samplingReq = new
									 ComMsg.SamplingCreateQueryReq();
									 samplingReq.SessionID = MSG.SessionID;
									 samplingReq.StartTime =mstartTime;
									 samplingReq.EndTime =mendTime;
									
									 ComInterface.SamplingCreateQuery(context,
									 samplingReq);

								}

							}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
									.get(Calendar.DATE), false).show();

				}
				return true;
			}
		});

		endTime.setOnTouchListener(new OnTouchListener() {

//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				// TODO Auto-generated method stub
//				return true;
//			}
			
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
									
									// 抽检查询接口
									 ComMsg.SamplingCreateQueryReq samplingReq = new
									 ComMsg.SamplingCreateQueryReq();
									 samplingReq.SessionID = MSG.SessionID;
									 samplingReq.StartTime =mstartTime;
									 samplingReq.EndTime =mendTime;
									
									 ComInterface.SamplingCreateQuery(context,
									 samplingReq);

								}

							}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
									.get(Calendar.DATE), false).show();

				}
				return true;
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.random_register:// 登记
			final CustomDialog dialog = new CustomDialog(context);
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();

			dialog.setClickListener(new ClickListenerInterface() {

				/*
				 * (non-Javadoc) name:名称 location:地点 startTime和endTime是起止时间
				 * people负责人 renyuan:人员
				 */
				@Override
				public void doConfirm(String name, String location,
						String startTime, String endTime, String people,
						String renyuan) {
					// Toast.makeText(
					// context,
					// "name: " + name + "location: " + location
					// + "startTime: " + startTime + "endTime: "
					// + endTime + "people: " + people
					// + "renyuan: " + renyuan, Toast.LENGTH_LONG)
					// .show();
					
					initData();
					adapter.notifyDataSetChanged();

				}

				@Override
				public void doCancel() {

				}
			});
			break;

		default:
			break;
		}

	}

	public RandomInspectionDetail getRandomInspectionDetail() {
		return roadDetail;
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
					MSG.Random_Inspection_QUERY_SUCCESS, TAG);
			MessageHandlerManager.get_instance().register(_message_handler,
					MSG.Random_Inspection_QUERY_FAIL, TAG);
		}

		// 注销地图处理消息
		private void unregister_message() {
			// 将handler从ArrayList中移除
			MessageHandlerManager.get_instance().unregister(
					MSG.Random_Inspection_QUERY_SUCCESS, TAG);
			MessageHandlerManager.get_instance().unregister(
					MSG.Random_Inspection_QUERY_FAIL, TAG);
		}

		// 地图消息处理句柄
		private class MainMessageHandler extends Handler {
			MainMessageHandler(Looper looper) {// 与指定的looper关联，而不是默认的looper
				super(looper);
			}

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case MSG.Random_Inspection_QUERY_SUCCESS:
					
					initData();
					break;

				case MSG.Random_Inspection_QUERY_FAIL:
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
