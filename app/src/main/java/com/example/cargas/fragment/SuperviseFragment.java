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
import com.example.cargas.adapter.SuperviseAdapter;
import com.example.cargas.communication.ComInterface;
import com.example.cargas.communication.ComMsg;
import com.example.cargas.database.DB;
import com.example.cargas.message.MSG;
import com.example.cargas.message.MessageHandlerManager;
import com.example.cargas.model.VaporRecoveryQueryResponse;
import com.example.cargas.view.DoubleDatePickerDialog2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class SuperviseFragment extends Fragment implements OnClickListener {

	private static final String TAG = "SuperviseFragment";
	Context context;
	private TextView register;
	private LinearLayout frameLayout;
	private ListView listview;
	private SuperviseAdapter adapter;
	List<VaporRecoveryQueryResponse> mVaporData = new ArrayList<VaporRecoveryQueryResponse>();
	private SuperviseRegiseter superviseRegister;

	public SuperviseFragment() {

	}

	public SuperviseFragment(Context context) {
		this.context = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.supervise_list_show, null);

		superviseRegister = new SuperviseRegiseter(context);
		initView(view);
		initData();

		register_message();
		return view;
	}

	private void initData() {

		// mVaporData.clear();
		// // 伪造数据
		// for (int i = 0; i < 5; i++) {
		// VaporRecoveryQueryResponse data = new VaporRecoveryQueryResponse(
		// "", "2016-07-27", "武汉卓刀泉", "xxxx单位", "0", "加油", "dddd",
		// "remark","");
		// mVaporData.add(data);
		// }
		//
		mVaporData.clear();
		// 查询数据库
		HashMap<String, String> query = DB.instance(context).query(
				"SELECT * FROM dt_vaporRecovery;");

		if (query != null) {

			for (int i = 0; i < Integer.parseInt((query.get("records_num"))); i++) {

				// 每次只返回一条数据
				HashMap<String, String> orgquery = DB.instance(context).query(
						"select OrganizationName from mi_organization where id = "
								+ query.get("OrganizationID_" + i) + ";");
				String orgName = "";
				if (orgquery != null) {
					// 每次只有一条数据，所以orgquery中的数据都是_0
					orgName = orgquery.get("OrganizationName_" + 0);
				}
				VaporRecoveryQueryResponse blackData1 = new VaporRecoveryQueryResponse(
						query.get("id_" + i), query.get("ExecutionTime_" + i),
						query.get("Address_" + i), orgName,
						query.get("CheckResult_" + i),
						query.get("Opinion_" + i), query.get("RegisterUser_"
								+ i), "", query.get("ServerId_" + i));

				mVaporData.add(blackData1);
			}
		}

		adapter.notifyDataSetChanged();

	}

	private void initView(View view) {
		register = (TextView) view.findViewById(R.id.supervise_data_register);
		frameLayout = (LinearLayout) view
				.findViewById(R.id.supervise_list_show);
		listview = (ListView) view.findViewById(R.id.supervise_list_listview);
		adapter = new SuperviseAdapter(context, mVaporData, frameLayout,
				getFragmentManager());

		listview.setAdapter(adapter);
		register.setOnClickListener(this);

		startTime = (EditText) view.findViewById(R.id.supervise_starttime_et);
		endTime = (EditText) view.findViewById(R.id.supervise_endtime_et);

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
									// 监管查询
									ComMsg.VaporRecoveryQueryReq vaporReq = new ComMsg.VaporRecoveryQueryReq();
									vaporReq.SessionID = MSG.SessionID;
									vaporReq.StartTime = mstartTime;
									vaporReq.EndTime = mendTime;
									ComInterface.VaporRecoveryQuery(context, vaporReq);

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
									// 监管查询
									ComMsg.VaporRecoveryQueryReq vaporReq = new ComMsg.VaporRecoveryQueryReq();
									vaporReq.SessionID = MSG.SessionID;
									vaporReq.StartTime = mstartTime;
									vaporReq.EndTime = mendTime;
									ComInterface.VaporRecoveryQuery(context, vaporReq);

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
		case R.id.supervise_data_register:
			// 登记
			FragmentManager fm = getFragmentManager();
			fm.beginTransaction()
					.replace(R.id.main_framelayout, superviseRegister).commit();
			break;

		default:
			break;
		}
	}

	public SuperviseRegiseter getSuperviseRegiseter() {
		return superviseRegister;
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
				MSG.Supervise_Detail_QUERY_SUCCESS, TAG);
		MessageHandlerManager.get_instance().register(_message_handler,
				MSG.Supervise_Detail_QUERY_FAIL, TAG);
	}

	// 注销地图处理消息
	private void unregister_message() {
		// 将handler从ArrayList中移除
		MessageHandlerManager.get_instance().unregister(
				MSG.Supervise_Detail_QUERY_SUCCESS, TAG);
		MessageHandlerManager.get_instance().unregister(
				MSG.Supervise_Detail_QUERY_FAIL, TAG);
	}

	// 地图消息处理句柄
	private class MainMessageHandler extends Handler {
		MainMessageHandler(Looper looper) {// 与指定的looper关联，而不是默认的looper
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG.Supervise_Detail_QUERY_SUCCESS:
				
				initData();
				break;

			case MSG.Supervise_Detail_QUERY_FAIL:
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
