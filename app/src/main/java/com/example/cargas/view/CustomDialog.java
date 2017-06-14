package com.example.cargas.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cargas.R;
import com.example.cargas.communication.ComInterface;
import com.example.cargas.communication.ComMsg;
import com.example.cargas.communication.ComMsg.SamplingCreateUploadReq;
import com.example.cargas.database.DB;
import com.example.cargas.message.MSG;
import com.example.cargas.message.MessageHandlerManager;
import com.example.cargas.utils.LocationUtils;
import com.example.cargas.utils.LocationUtils.LocationListener;
import com.example.cargas.utils.Utils;

import java.util.Calendar;

public class CustomDialog extends Dialog implements
		android.view.View.OnClickListener {

	private static final String TAG = "CustomDialog";

	Context context;

	private LocationUtils location;
	private String mlongitude;
	private String mlatitude;
	boolean flag = false;
	public interface ClickListenerInterface {

		public void doConfirm(String name, String location, String startTime,
				String endTime, String people, String renyuan);

		public void doCancel();
	}

	public CustomDialog(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		location = new LocationUtils(context);
		location.initLocation();

//		location.setLocationListener(new LocationListener() {
//
//			@Override
//			public void onLocationFinished(String longitude, String latitude) {
//				// Utils.showToast(context,
//				// longitude + " " + longitude);
//				mlongitude = longitude;
//				mlatitude = latitude;
//			}
//		});
		
		location.setLocationListener(new LocationListener() {

			@Override
			public void onLocationFinished(String longitude, String latitude,
					String loc) {

				if (location != null) {
					et_loacation.setText(loc);
					mlongitude = longitude;
					mlatitude = latitude;
					location.stopLocation();
				}
			}

		});

//		// 拍照时开始定位
//		location.startLocation();

		// 注册消息
		register_message();

		init();
	}

	private void init() {
		// 设置dialog没有标题
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.dialog_show, null);
		setContentView(view);

		ImageView delete = (ImageView) view.findViewById(R.id.dialog_delete);
		submit = (Button) view.findViewById(R.id.dialog_submit);
		delete.setOnClickListener(this);
		submit.setOnClickListener(this);

		et_name = (EditText) view.findViewById(R.id.dialog_name);
		et_loacation = (EditText) view.findViewById(R.id.dialog_location);
		et_dialog_time_from = (EditText) view
				.findViewById(R.id.dialog_time_from);
		et_dialog_time_to = (EditText) view.findViewById(R.id.dialog_time_to);
		et_people = (EditText) view.findViewById(R.id.dialog_people);
		et_renyuan = (EditText) view.findViewById(R.id.dialog_renyuan);

		Window dialogWindow = getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
		lp.width = (int) (d.widthPixels * 0.9); // 高度设置为屏幕的0.6
		dialogWindow.setAttributes(lp);

		// 设置日期
		et_dialog_time_from.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					Calendar c = Calendar.getInstance();

					// 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
					new DoubleDatePickerDialog(context, 0,
							new DoubleDatePickerDialog.OnDateSetListener() {

								@Override
								public void onDateSet(
										DatePicker startDatePicker,
										int startYear, int startMonthOfYear,
										int startDayOfMonth,
										DatePicker endDatePicker, int endYear,
										int endMonthOfYear, int endDayOfMonth,
										int Hour, int minute) {
									startTime = String.format("%d-%d-%d %d:%d",
											startYear, startMonthOfYear + 1,
											startDayOfMonth, Hour, minute);
									// endTime = String.format("%d-%d-%d",
									// endYear, endMonthOfYear + 1,
									// endDayOfMonth);
									et_dialog_time_from.setText(startTime);
									// et_dialog_time_to.setText(endTime);
								}

							}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
									.get(Calendar.DATE), false).show();
				}
				return true;
			}
		});

		et_dialog_time_to.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					Calendar c = Calendar.getInstance();

					// 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
					new DoubleDatePickerDialog(context, 0,
							new DoubleDatePickerDialog.OnDateSetListener() {

								@Override
								public void onDateSet(
										DatePicker startDatePicker,
										int startYear, int startMonthOfYear,
										int startDayOfMonth,
										DatePicker endDatePicker, int endYear,
										int endMonthOfYear, int endDayOfMonth,
										int Hour, int minute) {
									endTime = String.format("%d-%d-%d %d:%d",
											startYear, startMonthOfYear + 1,
											startDayOfMonth, Hour, minute);
									// = String.format("%d-%d-%d",
									// endYear, endMonthOfYear + 1,
									// endDayOfMonth);
									// et_dialog_time_from.setText(startTime);
									et_dialog_time_to.setText(endTime);
								}

							}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
									.get(Calendar.DATE), false).show();
				}
				return true;
			}
		});
		
		
		et_loacation.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (!flag) {
						location.startLocation();
						flag = true;
					}
				}
				return false;
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_delete:// 点击关闭按钮
			dismiss();
			break;
		case R.id.dialog_submit:// 点击提交按钮
			name = et_name.getText().toString().trim();
			location2 = et_loacation.getText().toString().trim();
			people = et_people.getText().toString().trim();
			renyuan = et_renyuan.getText().toString().trim();

			if (TextUtils.isEmpty(name) && TextUtils.isEmpty(location2)
					&& TextUtils.isEmpty(startTime)
					&& TextUtils.isEmpty(endTime) && TextUtils.isEmpty(people)
					&& TextUtils.isEmpty(renyuan)) {

				Toast.makeText(context, "请输入完整信息", 0).show();
			} else {

				// 将数据提交到服务器
				SamplingCreateUploadReq samplingReq = new ComMsg.SamplingCreateUploadReq();
				samplingReq.SessionID = MSG.SessionID;
				samplingReq.SamplingName = name;
				samplingReq.ChargePerson = people;
				samplingReq.SamplingAddress = location2;
				samplingReq.SamplingPerson = renyuan;
				samplingReq.StartTime = startTime;
				samplingReq.EndTime = endTime;
				samplingReq.Longitude = mlongitude;
				samplingReq.Latitude = mlatitude;

				//TODO
				ComInterface.SamplingCreateUpload(context, samplingReq);
				
				submit.setClickable(false);
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		// Utils.showToast(context, "stop");
		if (location != null) {
			location.stopLocation();
		}
		unregister_message();
	}

	private ClickListenerInterface listener;
	private EditText et_name;
	private EditText et_loacation;
	private EditText et_dialog_time_from;
	private EditText et_dialog_time_to;
	private EditText et_people;
	private EditText et_renyuan;

	// 开始时间和结束时间
	private String startTime;
	private String endTime;

	public void setClickListener(ClickListenerInterface clickListener) {
		this.listener = clickListener;
	}

	// ////////////////////////////////////////////////////
	public static Handler _message_handler = null;// 界面消息处理句柄

	private String name;

	private String location2;

	private String people;

	private String renyuan;

	private Button submit;

	// 注册地图处理消息
	private void register_message() {
		_message_handler = new MainMessageHandler(Looper.getMainLooper());

		MessageHandlerManager.get_instance().register(_message_handler,
				MSG.RandomInspectionCreate_SUCESS, TAG);
		MessageHandlerManager.get_instance().register(_message_handler,
				MSG.RandomInspectionCreate_FAIL, TAG);

	}

	// 注销地图处理消息
	private void unregister_message() {
		MessageHandlerManager.get_instance().unregister(
				MSG.RandomInspectionCreate_SUCESS, TAG);
		MessageHandlerManager.get_instance().unregister(
				MSG.RandomInspectionCreate_FAIL, TAG);

	}

	// 地图消息处理句柄
	private class MainMessageHandler extends Handler {
		MainMessageHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG.RandomInspectionCreate_SUCESS:

				// TODO 登录返回的消息
				ComMsg.SamplingCreateUploadRes res = (ComMsg.SamplingCreateUploadRes) (msg.obj);

				if (res.Success.equals("1")) {// 上传成功
					String result = res.Result;// 成功返回的是上传数据的主键
					// 将数据写进数据库
					StringBuffer sql = new StringBuffer();
					sql.append("insert into dt_samplingCreate (SamplingName,SamplingAddress,ChargePerson,SamplingPerson,StartTime,EndTime,RegisterUser,Longitude,Latitude,ServerId) ");
					sql.append("values ('" + name + "','" + location2 + "','"
							+ people + "','" + renyuan + "','" + startTime
							+ "','" + endTime + "','" + MSG.RegisterUser
							+ "','" + mlongitude + "','" + mlatitude +"','"+result+ "');");
					boolean isSuccess = DB.instance(context).execute(
							sql.toString());

					if (isSuccess) {
						Utils.showToast(context, "数据已存储，正在上传中");
					} else {
						Utils.showToast(context, "数据已存储失败");
					}
					 listener.doConfirm(name, location2, startTime, endTime,
					 people,
					 renyuan);
					dismiss();
				} else {
					Utils.showToast(context, res.Result);
					dismiss();
				}

				break;

			case MSG.RandomInspectionCreate_FAIL:
				Utils.showToast(context, "服务器连接异常");
				break;

			}
		}
	}
	
	

}
