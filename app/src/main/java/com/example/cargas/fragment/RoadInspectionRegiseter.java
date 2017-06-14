package com.example.cargas.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cargas.R;
import com.example.cargas.activity.MainActivity;
import com.example.cargas.activity.PlayVideo;
import com.example.cargas.communication.ComInterface;
import com.example.cargas.communication.ComMsg;
import com.example.cargas.database.DB;
import com.example.cargas.message.MSG;
import com.example.cargas.message.MessageHandlerManager;
import com.example.cargas.model.VehicleinformationQueryRequest;
import com.example.cargas.utils.LocationUtils;
import com.example.cargas.utils.LocationUtils.LocationListener;
import com.example.cargas.utils.Utils;
import com.king.photo.adapter.GridAdapter;
import com.king.photo.adapter.GridAdapter.onPicClicked;
import com.king.photo.util.Bimp;
import com.king.photo.util.ImageItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class RoadInspectionRegiseter extends Fragment implements
		OnClickListener {
	private static final String ZIYOUJIASU = "自由加速数据";

	private static final String SHUANGDAISU = "双怠速检测结果";

	private static final String CAIYOU = "柴油";

	private static final String QIYOU = "汽油";

	private static final String TAG = "RoadInspectionRegiseter";

	private TextView back;
	private Button query;
	private EditText mCarNumber;
	private EditText mVin;

	private EditText mCarType;
	private EditText mUseProperty;
	private EditText mRegisterDate;
	private EditText mBrandType;
	private EditText mStandard;
	private EditText mPassengers;
	private EditText mEngineType;
	private EditText mTemp;
	private EditText mPressure;
	private EditText mRelativeTemp;
	private EditText mOilTemp;
	private EditText mAir;
	private EditText mLowCo;
	private EditText mLowHc;
	private EditText mLowNo;
	private EditText mLowCo2;
	private EditText mLowO2;
	private EditText mHighCo;
	private EditText mHighHc;
	private EditText mHighNo;
	private EditText mHighCo2;
	private EditText mHighO2;
	private EditText mPos;
	private TextView submit;

	private String mCarColor = null;
	Context context;
	private ImageView showCarInfo;
	private ImageView showResult;
	private ImageView showPos;
	private ImageView showPic;
	private LinearLayout showCarInfoLayout;
	private LinearLayout showResultLayout;
	private LinearLayout showPosLayout;
	private LinearLayout showPicLayout;

	// 值为false时布局展开，值为true时布局收缩
	private boolean carFlag = false;
	private boolean carResultFlag = false;
	private boolean carPositionFlag = false;
	private boolean carPictureFlag = false;
	private EditText mMaxTotalMass;

	private GridView noScrollgridview;
	private GridAdapter adapter;

	public int max = 0;

	private ArrayList<ImageItem> tempSelectBitmap = new ArrayList<ImageItem>(); // 选择的图片的临时列表

	private LocationUtils location;
	private String mlongitude;
	private String mlatitude;
	boolean flag = false;
	private String spPorpertyStr;// 下拉框获取的文本，使用性质
	private String spEmissionStr;// 下拉框获取的文本，排放标准

	private String sessionID;
	private int emissionlimits;
	private String maxTotalMass;
	private String checkResult = "1";
	private String longitude;
	private String latitude;
	private String registerTime;

	private String carNumber;
	private String carcolo;
	private String carType;
	private String useProperty;
	private String registerDate;
	private String brandType;
	private String engineType;

	private String passengers;
	private String standard;
	private String temp;
	private String pressure;
	private String relativeTemp;
	private String oilTemp;
	private String air;

	private String lowCo;
	private String lowHc;
	private String lowNo;
	private String lowO2;
	private String lowCo2;
	private String highCo;
	private String highHc;
	private String highNo;

	private String highO2;
	private String highCo2;
	private String pos;

	// ////////////////////////////////////////////////////
	public static Handler _message_handler = null;// 界面消息处理句柄
	private Spinner sp;
	private Spinner spEmission;
	private EditText mfueltype;
	private TextView roadTitle;
	private LinearLayout jiasuLayout;
	private EditText mJiaTemp;

	private EditText mJiaPress;
	private EditText mJiaHumi;
	private EditText mJiaSmoke1;
	private EditText mJiaSmoke2;
	private EditText mJiaSmoke3;

	private String ziyouTemp;
	private String huanjingPress;
	private String xiangduiHumi;
	private String yandu1;
	private String yandu2;
	private String yandu3;
	private String avg;

	private String zyXianChangResult;// 自由加速数据的现场结果
	private String shXianChangResult;// 双怠速的现场结果

	private String spCarTypeStr;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.road_data_register2, null);

		location = new LocationUtils(context);
		location.initLocation();

		location.startLocation();
		location.setLocationListener(new LocationListener() {

			@Override
			public void onLocationFinished(String longitude, String latitude,
					String loc) {

				if (location != null) {
					mPos.setText(loc);
					mlongitude = longitude;
					mlatitude = latitude;
					location.stopLocation();
				}
			}

		});

		init(view);

		// 注册消息
		register_message();
		return view;
	}

	public RoadInspectionRegiseter(Context context) {
		this.context = context;
	}

	private void init(View view) {
		back = (TextView) view.findViewById(R.id.bt_data_back);
		query = (Button) view.findViewById(R.id.bt_road_data_query);
		submit = (TextView) view.findViewById(R.id.bt_road_submit);

		showCarInfo = (ImageView) view.findViewById(R.id.bt_data_showcar);
		showResult = (ImageView) view.findViewById(R.id.bt_data_showresult);
		showPos = (ImageView) view.findViewById(R.id.bt_data_showpos);
		showPic = (ImageView) view.findViewById(R.id.bt_data_showpic);

		// 显示和隐藏的布局
		showCarInfoLayout = (LinearLayout) view
				.findViewById(R.id.road_data_car_info_layout);
		showResultLayout = (LinearLayout) view
				.findViewById(R.id.road_data_car_result_layout);
		showPosLayout = (LinearLayout) view
				.findViewById(R.id.road_data_car_pos_layout);
		showPicLayout = (LinearLayout) view
				.findViewById(R.id.road_data_car_pic_layout);

		jiasuLayout = (LinearLayout) view
				.findViewById(R.id.road_data_jiasu_layout);

		mCarNumber = (EditText) view.findViewById(R.id.et_road_data_carnumber);
		mVin = (EditText) view.findViewById(R.id.et_road_data_vin);

		mCarType = (EditText) view.findViewById(R.id.et_road_data_cartype);
		mUseProperty = (EditText) view.findViewById(R.id.et_road_data_property);
		mRegisterDate = (EditText) view
				.findViewById(R.id.et_road_data_register_date);
		mBrandType = (EditText) view.findViewById(R.id.et_road_data_brand);
		mStandard = (EditText) view.findViewById(R.id.et_road_data_standard);
		mPassengers = (EditText) view
				.findViewById(R.id.et_road_data_passenger_count1);
		mEngineType = (EditText) view
				.findViewById(R.id.et_road_data_enginetype);
		mMaxTotalMass = (EditText) view
				.findViewById(R.id.et_road_data_maxtotalmass);

		mfueltype = (EditText) view.findViewById(R.id.et_road_data_fueltype);// 燃油类型

		mTemp = (EditText) view.findViewById(R.id.et_road_data_temp);
		mPressure = (EditText) view.findViewById(R.id.et_road_data_pres);
		mRelativeTemp = (EditText) view
				.findViewById(R.id.et_road_data_relativetemp);
		mOilTemp = (EditText) view.findViewById(R.id.et_road_data_oiltemp);
		mAir = (EditText) view.findViewById(R.id.et_road_data_air);

		mLowCo = (EditText) view.findViewById(R.id.et_road_data_low_co);
		mLowHc = (EditText) view.findViewById(R.id.et_road_data_low_hc);
		mLowNo = (EditText) view.findViewById(R.id.et_road_data_low_no);
		mLowCo2 = (EditText) view.findViewById(R.id.et_road_data_low_co2);
		mLowO2 = (EditText) view.findViewById(R.id.et_road_data_low_o2);

		mHighCo = (EditText) view.findViewById(R.id.et_road_data_high_co);
		mHighHc = (EditText) view.findViewById(R.id.et_road_data_high_hc);
		mHighNo = (EditText) view.findViewById(R.id.et_road_data_high_no);
		mHighCo2 = (EditText) view.findViewById(R.id.et_road_data_high_co2);
		mHighO2 = (EditText) view.findViewById(R.id.et_road_data_high_o2);

		roadTitle = (TextView) view.findViewById(R.id.road_result_title);

		mPos = (EditText) view.findViewById(R.id.et_road_data_checkpos);

		mJiaTemp = (EditText) view.findViewById(R.id.et_road_data_environ_temp);
		mJiaPress = (EditText) view
				.findViewById(R.id.et_road_data_environ_press);
		mJiaHumi = (EditText) view
				.findViewById(R.id.et_road_data_relative_temp);
		mJiaSmoke1 = (EditText) view.findViewById(R.id.et_road_data_yandu1);
		mJiaSmoke2 = (EditText) view.findViewById(R.id.et_road_data_yandu2);
		mJiaSmoke3 = (EditText) view.findViewById(R.id.et_road_data_yandu3);

		back.setOnClickListener(this);
		query.setOnClickListener(this);
		submit.setOnClickListener(this);
		showCarInfo.setOnClickListener(this);
		showResult.setOnClickListener(this);
		showPos.setOnClickListener(this);
		showPic.setOnClickListener(this);

		Spinner colorSpinner = (Spinner) view
				.findViewById(R.id.road_data_register_color);
		final String str[] = getResources().getStringArray(R.array.array_name);// 颜色
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context,
				R.layout.myspinner, str);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		colorSpinner.setAdapter(adapter1);

		colorSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				mCarColor = str[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		noScrollgridview = (GridView) view
				.findViewById(R.id.road_noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(context, tempSelectBitmap);
		// loading();
		adapter.setDeleteListener(new onPicClicked() {

			@Override
			public void notityUpdate(int position) {

				tempSelectBitmap.remove(position);
				max--;

				adapter.notifyDataSetChanged();
			}

		});
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (position == tempSelectBitmap.size()) {// 点击的是加号//cq
					Log.i("ddddddd", "----------");

					String[] array = new String[] { "拍照", "录像" ,"图库"};
					AlertDialog.Builder builder = new AlertDialog.Builder(
							context);
					ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
							context, R.layout.dialog_item, R.id.tv, array);
					// builder.setTitle("选择附件类型");
					builder.setAdapter(arrayAdapter,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									switch (which) {

									case 0:
										photo();
										break;

									case 1:
										video();
										break;
										
									case 2:
										picture();
										break;

									default:
										break;
									}
								}
							});
					AlertDialog dialog = builder.create();
					dialog.show();

					// photo();
				} else {// 点击的是其他图片
					// 使用Intent
					Intent intent = new Intent(Intent.ACTION_VIEW);
					String path = tempSelectBitmap.get(position).getImagePath();

					if (path != null) {

						if (path.contains("jpg")) {

							intent.setDataAndType(Uri.fromFile(new File(path)),
									"image/*");
							startActivity(intent);

						} else if (path.contains("mp4")) {

							Intent videoIntent = new Intent(context,
									PlayVideo.class);
							videoIntent.putExtra("path", path);
							context.startActivity(videoIntent);
						}

					}

				}
			}
		});

		sp = (Spinner) view.findViewById(R.id.et_road_data_property_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				context, R.array.use_property, R.layout.myspinner);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(adapter);

		sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				spPorpertyStr = (String) parent.getItemAtPosition(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		Spinner fuelSp = (Spinner) view
				.findViewById(R.id.et_road_data_fueltype_spinner);
		ArrayAdapter<CharSequence> fueladapter = ArrayAdapter
				.createFromResource(context, R.array.fuel_type,
						R.layout.myspinner);
		fueladapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		fuelSp.setAdapter(fueladapter);

		fuelSp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				type = (String) parent.getItemAtPosition(position);
				if (type.equals(QIYOU)) {
					roadTitle.setText(SHUANGDAISU);
					jiasuLayout.setVisibility(View.GONE);
					showResultLayout.setVisibility(View.VISIBLE);
				} else if (type.equals(CAIYOU)) {
					roadTitle.setText(ZIYOUJIASU);
					showResultLayout.setVisibility(View.GONE);
					jiasuLayout.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		spEmission = (Spinner) view
				.findViewById(R.id.et_road_data_standard_spinner);
		ArrayAdapter<CharSequence> adapterEmission = ArrayAdapter
				.createFromResource(context, R.array.emission_standard,
						R.layout.myspinner);
		adapterEmission
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spEmission.setAdapter(adapterEmission);
		spEmission.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				spEmissionStr = (String) parent.getItemAtPosition(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		// 双怠速的现场结果
		Spinner spShuangResult = (Spinner) view
				.findViewById(R.id.et_xianchang_result);
		ArrayAdapter<CharSequence> adapterResult = ArrayAdapter
				.createFromResource(context, R.array.array_status,
						R.layout.myspinner);
		adapterResult
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spShuangResult.setAdapter(adapterResult);
		spShuangResult.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				shXianChangResult = (String) parent.getItemAtPosition(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		// 自由加速数据的现场结果
		Spinner spZiyouResult = (Spinner) view
				.findViewById(R.id.sp_shuang_xianchang_result);
		ArrayAdapter<CharSequence> adapterZiyouResult = ArrayAdapter
				.createFromResource(context, R.array.array_status,
						R.layout.myspinner);
		adapterZiyouResult
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spZiyouResult.setAdapter(adapterZiyouResult);
		spZiyouResult.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				zyXianChangResult = (String) parent.getItemAtPosition(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		spCarType = (Spinner) view
				.findViewById(R.id.et_road_data_cartype_spinner);
		ArrayAdapter<CharSequence> adapterCarType = ArrayAdapter
				.createFromResource(context, R.array.car_type,
						R.layout.myspinner);
		adapterCarType
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spCarType.setAdapter(adapterCarType);
		spCarType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				spCarTypeStr = (String) parent.getItemAtPosition(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		// TextView lowCo = (TextView) view.findViewById(R.id.low_co_tv);

		mCarNumber.setText("鄂A00073");

		mCarType.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mCarType.setVisibility(View.GONE);
				spCarType.setVisibility(View.VISIBLE);
				return true;
			}
		});

		mUseProperty.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mUseProperty.setVisibility(View.GONE);
				sp.setVisibility(View.VISIBLE);
				return true;
			}
		});

		mStandard.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mStandard.setVisibility(View.GONE);
				spEmission.setVisibility(View.VISIBLE);
				return true;
			}
		});

		mRegisterDate.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub

				if (event.getAction() == MotionEvent.ACTION_UP) {
					Calendar ca = Calendar.getInstance();

					DatePickerDialog dp = new DatePickerDialog(context,
							new OnDateSetListener() {

								@Override
								public void onDateSet(DatePicker view,
										int year, int monthOfYear,
										int dayOfMonth) {
									mRegisterDate.setText(year + "-"
											+ monthOfYear + "-" + dayOfMonth);
								}
							}, ca.get(Calendar.YEAR), ca.get(Calendar.MONTH),
							ca.get(Calendar.DAY_OF_MONTH));
					dp.show();
				}
				return true;
			}
		});

	}

	private String type;
	
	private void picture() {
		Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);;
		
		((FragmentActivity) context).startActivityForResult(intent,
				Utils.Road_PICTURE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_data_back:
			// 点击返回
			getFragmentManager()
					.beginTransaction()
					.replace(R.id.main_framelayout,
							((MainActivity) getActivity()).getmRoad()).commit();

			break;

		case R.id.bt_road_data_query:
			String sessionId = null;
			String carNo = mCarNumber.getText().toString().trim();
			String carColor = mCarColor.toString().trim();
           String VIN = mVin.getText().toString();
			if (TextUtils.isEmpty(carNo) || TextUtils.isEmpty(carColor)) {
				Toast.makeText(context, "需填入车牌和颜色", 0).show();
			} else {

				// 查询数据
				VehicleinformationQueryRequest queryRequest = new VehicleinformationQueryRequest(
						sessionId, carNo, carColor);

				// 查询呢过程，

				ComMsg.VehicleinformationQueryReq vehicleReq = new ComMsg.VehicleinformationQueryReq();
				vehicleReq.CarNo = carNo;
				vehicleReq.CarColor = carColor;
				vehicleReq.SessionID = MSG.SessionID;
				ComInterface.VehicleinformationQuery(context, vehicleReq, 0);

			}

			break;

		case R.id.bt_road_submit:
			// 提交数据

			sessionID = MSG.SessionID;
			emissionlimits = 0;
			maxTotalMass = mMaxTotalMass.getText().toString();
			checkResult = null;
			longitude = mlongitude;
			latitude = mlatitude;
			registerTime = Utils.getCurrentTime();
			carNumber = mCarNumber.getText().toString();
			carcolo = mCarColor;

			// useProperty = mUseProperty.getText().toString();
			registerDate = mRegisterDate.getText().toString();
			brandType = mBrandType.getText().toString();
			engineType = mEngineType.getText().toString();
			passengers = mPassengers.getText().toString();
			// standard = mStandard.getText().toString();
			temp = mTemp.getText().toString();
			pressure = mPressure.getText().toString();
			relativeTemp = mRelativeTemp.getText().toString();
			oilTemp = mOilTemp.getText().toString();
			air = mAir.getText().toString();
			lowCo = mLowCo.getText().toString();
			lowHc = mLowHc.getText().toString();
			lowNo = mLowNo.getText().toString();
			lowO2 = mLowO2.getText().toString();
			lowCo2 = mLowCo2.getText().toString();
			highCo = mHighCo.getText().toString();
			highHc = mHighHc.getText().toString();
			highNo = mHighNo.getText().toString();
			highO2 = mHighO2.getText().toString();
			highCo2 = mHighCo2.getText().toString();
			pos = mPos.getText().toString();

			ziyouTemp = mJiaTemp.getText().toString();
			huanjingPress = mJiaPress.getText().toString();
			xiangduiHumi = mJiaHumi.getText().toString();
			yandu1 = mJiaSmoke1.getText().toString();
			yandu2 = mJiaSmoke2.getText().toString();
			yandu3 = mJiaSmoke3.getText().toString();

			// TODO mStandard.is

			if (mStandard.isShown()) {// 排放标准显示的是文本框
				standard = mStandard.getText().toString();
			} else {// 排放标准显示的是下拉框
				standard = spEmissionStr;
			}

			if (mUseProperty.isShown()) {
				useProperty = mUseProperty.getText().toString();
			} else {
				useProperty = spPorpertyStr;
			}

			if (mCarType.isShown()) {
				carType = mCarType.getText().toString();
			} else {
				carType = spCarTypeStr;
			}

			if (!TextUtils.isEmpty(carNumber)
					&& !TextUtils.isEmpty(carcolo)
					&& !TextUtils.isEmpty(carType)
					&& !TextUtils.isEmpty(useProperty)
					&& !TextUtils.isEmpty(registerDate)
					&& !TextUtils.isEmpty(brandType)
					&& !TextUtils.isEmpty(engineType)
					&& !TextUtils.isEmpty(passengers)
					&& !TextUtils.isEmpty(standard)
					&& ((type.equals(QIYOU) && !TextUtils.isEmpty(temp)
							&& !TextUtils.isEmpty(pressure)
							&& !TextUtils.isEmpty(relativeTemp)
							&& !TextUtils.isEmpty(oilTemp)
							&& !TextUtils.isEmpty(air)
							&& !TextUtils.isEmpty(lowCo)
							&& !TextUtils.isEmpty(lowHc)
							&& !TextUtils.isEmpty(lowNo)
							&& !TextUtils.isEmpty(lowO2)
							&& !TextUtils.isEmpty(lowCo2)
							&& !TextUtils.isEmpty(highCo)
							&& !TextUtils.isEmpty(highHc)
							&& !TextUtils.isEmpty(highNo) && !TextUtils
								.isEmpty(highO2)) || (type.equals(CAIYOU)
							&& !TextUtils.isEmpty(ziyouTemp)
							&& !TextUtils.isEmpty(huanjingPress)
							&& !TextUtils.isEmpty(xiangduiHumi)
							&& !TextUtils.isEmpty(yandu1)
							&& !TextUtils.isEmpty(yandu2) && !TextUtils
								.isEmpty(yandu3)))) {

				roadReq = new ComMsg.RoadCheckUploadReq();
				roadReq.SessionID = MSG.SessionID;
				roadReq.CarNo = carNumber;
				roadReq.CarColor = carcolo;
				roadReq.CarType = carType;
				roadReq.Usenature = useProperty;
				roadReq.Registrationdate = registerDate;
				roadReq.Brandmodel = brandType;
				roadReq.MaxTotalMass = maxTotalMass;
				roadReq.Enginemodel = engineType;
				roadReq.Passengers = passengers;
				roadReq.Emissionstandards = standard;

				roadReq.CheckAddress = pos;
				roadReq.Longitude = mlongitude;
				roadReq.Latitude = mlatitude;
				roadReq.RegisterTime = registerTime;

				roadReq.PenaltyCollected = "";
				roadReq.PenaltyReceivable = "";
				roadReq.UserResult = "";
				roadReq.fueltype = type;// 燃油类型
				if (type.equals(QIYOU)) {// 如果是汽油的话那么自由加速的数据都是空的

					// 提交
					roadReq.Ambienttemperature = temp;
					roadReq.Environmentalpressure = pressure;
					roadReq.Relativehumidity = relativeTemp;
					roadReq.OilTemperature = oilTemp;
					roadReq.Excessairratio = air;
					roadReq.Low_co = lowCo;
					roadReq.Low_hc = lowHc;
					roadReq.Low_no = lowNo;
					roadReq.Low_o2 = lowO2;
					roadReq.Low_co2 = lowCo2;
					roadReq.High_co = highCo;
					roadReq.High_hc = highHc;
					roadReq.High_no = highNo;
					roadReq.High_o2 = highO2;
					roadReq.High_co2 = highCo2;

					// 自由加速的数据为空
					roadReq.Smokeopacity1 = "";
					roadReq.Smokeopacity2 = "";
					roadReq.Smokeopacity3 = "";
					roadReq.Smokeopacityavg = "";

					int toalMass = Integer.valueOf(maxTotalMass);
					int testType = 0;
					int passengerQuantity = Integer.valueOf(passengers);// 载客人数
					// 计算是否合格
					if (toalMass > 3500) {
						testType = 52;// 重型车
					} else if (toalMass <= 2500 && passengerQuantity <= 6) {
						testType = 50;// 第一类轻型车
					} else {
						testType = 51;// 第二类轻型车
					}

					int two_speed_idle_detection_limits_id = 0;
					String RegistrationDate = "";
					if (registerDate.contains("T")) {
						// 服务器返回的日期格式2015-08-11T00:00:00
						RegistrationDate = registerDate.replace("T", " ");
					} else {
						RegistrationDate = registerDate;
					}
					// 查找出vehicle_exhaust_detection_type_id = testType的记录
					HashMap<String, String> query2 = DB
							.instance(context)
							.query("select * from tb_two_speed_idle_detection_limits where vehicle_exhaust_detection_type_id = "
									+ testType + ";");
					if (query2 != null) {

						for (int i = 0; i < Integer.parseInt((query2
								.get("records_num"))); i++) {
							String limit_start_time = null;

							if (!query2.get("limit_start_time_" + i).isEmpty()) {
								limit_start_time = query2
										.get("limit_start_time_" + i);
							}

							String limit_end_time = null;
							if (!query2.get("limit_end_time_" + i).isEmpty()) {
								limit_end_time = query2.get("limit_end_time_"
										+ i);
							}

							int id = Integer.parseInt(query2
									.get("two_speed_idle_detection_limits_id_"
											+ i));

							if (limit_start_time == null
									&& (limit_end_time
											.compareTo(RegistrationDate) >= 0)
									|| (limit_end_time == null && (limit_start_time
											.compareTo(RegistrationDate) <= 0))
									|| (limit_start_time != null
											&& limit_end_time != null
											&& (limit_start_time
													.compareTo(RegistrationDate) <= 0) && (limit_end_time
											.compareTo(RegistrationDate) >= 0)))
								two_speed_idle_detection_limits_id = id;

						}

						emissionlimits = two_speed_idle_detection_limits_id;
					}

					// 根据id找出对应 的那条记录
					HashMap<String, String> query3 = DB
							.instance(context)
							.query("select * from tb_two_speed_idle_detection_limits where two_speed_idle_detection_limits_id = "
									+ two_speed_idle_detection_limits_id + ";");

					// co_idle_speed text co低怠速限值
					// hc_idle_speed text hc低怠速限值
					// co_high_idle_speed text co高怠速限值
					// hc_high_idle_speed text hc高怠速限值
					if (query3 != null) {

						float col = Float.parseFloat(lowCo);
						float hcl = Float.parseFloat(lowHc);
						float coh = Float.parseFloat(highCo);
						float hch = Float.parseFloat(highHc);
						float airfact = Float.parseFloat(air);

						float co_idle_limit = Float.parseFloat(query3
								.get("co_idle_speed_0"));
						float hc_idle_limit = Float.parseFloat(query3
								.get("hc_idle_speed_0"));
						float co_high_idle_limit = Float.parseFloat(query3
								.get("co_high_idle_speed_0"));
						float hc_high_idle_limit = Float.parseFloat(query3
								.get("hc_high_idle_speed_0"));
						float excess_air_factor_lower_limit = Float
								.parseFloat(query3
										.get("excess_air_factor_lower_limit_0"));
						float excess_air_factor_upper_limit = Float
								.parseFloat(query3
										.get("excess_air_factor_upper_limit_0"));

						// 同时成立才为合格
						if (!(col > co_idle_limit || hcl > hc_idle_limit)
								&& !(coh > co_high_idle_limit || hch > hc_high_idle_limit)
								&& !(airfact < excess_air_factor_lower_limit || airfact > excess_air_factor_upper_limit)
								&& !(col == 0 && hcl == 0 && coh == 0 && hch == 0)) {
							// 合格;
							checkResult = "0";

						} else {
							// 不合格;
							checkResult = "1";
						}

					}

					roadReq.Emissionlimits = emissionlimits + "";
					roadReq.CheckResult = checkResult;

					if (shXianChangResult.equals("合格")) {
						roadReq.UserResult = "0";
					} else if (shXianChangResult.equals("不合格")) {
						roadReq.UserResult = "1";
					}

				} else if (type.equals(CAIYOU)) {// 如果是柴油的话，双怠速中的数据就应该是空的

					// 环境温度，环境气压，相对湿度
					roadReq.Ambienttemperature = ziyouTemp;
					roadReq.Environmentalpressure = huanjingPress;
					roadReq.Relativehumidity = xiangduiHumi;

					roadReq.OilTemperature = "";
					roadReq.Excessairratio = "";
					roadReq.Low_co = "";
					roadReq.Low_hc = "";
					roadReq.Low_no = "";
					roadReq.Low_o2 = "";
					roadReq.Low_co2 = "";
					roadReq.High_co = "";
					roadReq.High_hc = "";
					roadReq.High_no = "";
					roadReq.High_o2 = "";
					roadReq.High_co2 = "";

					// roadReq.Emissionlimits = "";
					// roadReq.CheckResult = "";

					// 自由加速的数据为空
					roadReq.Smokeopacity1 = mJiaSmoke1.getText().toString();
					roadReq.Smokeopacity2 = mJiaSmoke2.getText().toString();
					roadReq.Smokeopacity3 = mJiaSmoke3.getText().toString();

					Double smokeopacityavg = (Double
							.parseDouble(roadReq.Smokeopacity1)
							+ Double.parseDouble(roadReq.Smokeopacity2) + Double
							.parseDouble(roadReq.Smokeopacity3)) / 3;

					avg = (String.format("%.2f", smokeopacityavg));
					smokeopacityavg = Double.valueOf(avg);

					roadReq.Smokeopacityavg = smokeopacityavg + "";

					// 计算checkresult 0合格， 1不合格
					String checkresult = "";
					String RegistrationDate = "";
					if (registerDate.contains("T")) {
						// 服务器返回的日期格式2015-08-11T00:00:00
						RegistrationDate = registerDate.replace("T", " ");
					} else {
						RegistrationDate = registerDate;
					}

					int opaueid = 2;
					if (RegistrationDate.compareTo("2005-06-30 23:59:59") < 0)
						opaueid = 1;
					else if (RegistrationDate.compareTo("2005-07-30 00:00:00") > 0)
						opaueid = 3;

					HashMap<String, String> query2 = DB
							.instance(context)
							.query("select * from tb_OPAQUE_FAS_detection_limits where OPAQUE_FAS_detection_limits_id = "
									+ opaueid + ";");

					if (query2 != null) {

						double limits = Double.parseDouble(query2
								.get("smoke_limit_0"));
						if (smokeopacityavg > limits) {
							// 不合格
							checkresult = "1";
						} else {
							// 合格
							checkresult = "0";
						}
					}
					roadReq.Emissionlimits = opaueid + "";
					roadReq.CheckResult = checkresult;

					if (zyXianChangResult.equals("合格")) {
						roadReq.UserResult = "0";
					} else if (zyXianChangResult.equals("不合格")) {
						roadReq.UserResult = "1";
					}

				}

				ComInterface.RoadCheckUpload(context, roadReq);

			} else {
				Toast.makeText(context, "请填写完整。。。", Toast.LENGTH_SHORT).show();
			}

			// if (TextUtils.isEmpty(carNumber) || TextUtils.isEmpty(carcolo)
			// || TextUtils.isEmpty(carType)
			// || TextUtils.isEmpty(useProperty)
			// || TextUtils.isEmpty(registerDate)
			// || TextUtils.isEmpty(brandType)
			// || TextUtils.isEmpty(engineType)
			// || TextUtils.isEmpty(passengers)
			// || TextUtils.isEmpty(standard) || TextUtils.isEmpty(temp)
			// || TextUtils.isEmpty(pressure)
			// || TextUtils.isEmpty(relativeTemp)
			// || TextUtils.isEmpty(oilTemp) || TextUtils.isEmpty(air)
			// || TextUtils.isEmpty(lowCo) || TextUtils.isEmpty(lowHc)
			// || TextUtils.isEmpty(lowNo) || TextUtils.isEmpty(lowO2)
			// || TextUtils.isEmpty(lowCo2) || TextUtils.isEmpty(highCo)
			// || TextUtils.isEmpty(highHc) || TextUtils.isEmpty(highNo)
			// || TextUtils.isEmpty(highO2) || TextUtils.isEmpty(pos)) {
			//
			// Toast.makeText(context, "请填写完整。。。", 0).show();
			// } else {
			//
			//
			// }

			break;

		// 布局的展示和隐藏
		case R.id.bt_data_showcar:
			if (!carFlag) {// 当前是展开的，需要收缩
				showCarInfoLayout.setVisibility(View.GONE);
				carFlag = true;
			} else {// 当前收缩，点击展开
				showCarInfoLayout.setVisibility(View.VISIBLE);
				carFlag = false;
			}
			break;
		case R.id.bt_data_showresult:
			if (!carResultFlag) {// 当前是展开的，需要收缩

				if (roadTitle.getText().equals(ZIYOUJIASU)) {
					jiasuLayout.setVisibility(View.GONE);
				} else if (roadTitle.getText().equals(SHUANGDAISU)) {
					showResultLayout.setVisibility(View.GONE);
				}

				carResultFlag = true;
			} else {// 当前收缩，点击展开
				if (roadTitle.getText().equals(ZIYOUJIASU)) {
					jiasuLayout.setVisibility(View.VISIBLE);
				} else if (roadTitle.getText().equals(SHUANGDAISU)) {
					showResultLayout.setVisibility(View.VISIBLE);
				}

				carResultFlag = false;
			}
			break;
		case R.id.bt_data_showpos:
			if (!carPositionFlag) {// 当前是展开的，需要收缩
				showPosLayout.setVisibility(View.GONE);
				carPositionFlag = true;
			} else {// 当前收缩，点击展开
				showPosLayout.setVisibility(View.VISIBLE);
				carPositionFlag = false;
			}
			break;
		case R.id.bt_data_showpic:
			if (!carPictureFlag) {// 当前是展开的，需要收缩
				showPicLayout.setVisibility(View.GONE);
				carPictureFlag = true;
			} else {// 当前收缩，点击展开
				showPicLayout.setVisibility(View.VISIBLE);
				carPictureFlag = false;
			}
			break;

		default:
			break;
		}

	}

	private String filePath = null;
	private String videoFilePath;

	private ComMsg.RoadCheckUploadReq roadReq;

	private Spinner spCarType;

	// 拍照
	public void photo() {
		// 拍照时开始定位
		// location.startLocation();
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		filePath = Utils.Dir + Utils.getFileDate() + ".jpg";
		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		((FragmentActivity) context).startActivityForResult(openCameraIntent,
				Utils.ROAD_TAKE_PICTURE);
	}

	public void video() {
		Intent intent = new Intent();
		intent.setAction("android.media.action.VIDEO_CAPTURE");
		intent.addCategory("android.intent.category.DEFAULT");

		videoFilePath = Utils.Dir + Utils.getFileDate() + ".mp4";
		File file = new File(videoFilePath);
		if (file.exists()) {
			file.delete();
		}
		Uri uri = Uri.fromFile(file);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

		((FragmentActivity) context).startActivityForResult(intent,
				Utils.ROAD_TAKE_VIDEO);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (tempSelectBitmap.size() < 9
				&& resultCode == getActivity().RESULT_OK) {// cq

			String fileUrl = null;

			Bitmap bm = null;
			if (requestCode == Utils.ROAD_TAKE_PICTURE) {

				bm = Bimp.decodeSampleBitmapFromFile(filePath, 100, 100);
				fileUrl = filePath;
			} else if (requestCode == Utils.ROAD_TAKE_VIDEO) {

				bm = Utils.getVideoThumbnail(videoFilePath, 100, 100,
						MediaStore.Images.Thumbnails.MINI_KIND);

				fileUrl = videoFilePath;
			} else if (requestCode == Utils.Road_PICTURE){
				Uri uri = data.getData();
				String fileUri = uri.toString();
				bm = Bimp.decodeSampleBitmapFromFile(fileUri, 100, 100);
				
	            Uri selectedImage = data.getData();  
	            String[] filePathColumn = { MediaStore.Images.Media.DATA };  
	   
	            Cursor cursor = context.getContentResolver().query(selectedImage,  
	                    filePathColumn, null, null, null);  
	            cursor.moveToFirst();  
	   
	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);  
	           fileUrl = cursor.getString(columnIndex);  
	            cursor.close(); 
				
			}
			ImageItem takePhoto = new ImageItem();
			takePhoto.setImagePath(fileUrl);
			takePhoto.setBitmap(bm);
			takePhoto.setLatitude(mlatitude);
			takePhoto.setLogitude(mlongitude);
			tempSelectBitmap.add(takePhoto);// cq
			adapter.setcontent(tempSelectBitmap);
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (location != null) {
			location.stopLocation();
		}
		unregister_message();
	}

	// 注册地图处理消息
	private void register_message() {
		_message_handler = new MainMessageHandler(Looper.getMainLooper());

		MessageHandlerManager.get_instance().register(_message_handler,
				MSG.Road_Inspection_Register_Query_SUCESS, TAG);
		MessageHandlerManager.get_instance().register(_message_handler,
				MSG.Road_Inspection_Register_Query_FAIL, TAG);

		MessageHandlerManager.get_instance().register(_message_handler,
				MSG.Road_Inspection_Register_Submit_SUCESS, TAG);
		MessageHandlerManager.get_instance().register(_message_handler,
				MSG.Road_Inspection_Register_Submit_FAIL, TAG);
	}

	// 注销地图处理消息
	private void unregister_message() {
		MessageHandlerManager.get_instance().unregister(
				MSG.Road_Inspection_Register_Query_SUCESS, TAG);
		MessageHandlerManager.get_instance().unregister(
				MSG.Road_Inspection_Register_Query_FAIL, TAG);

		MessageHandlerManager.get_instance().unregister(
				MSG.Road_Inspection_Register_Submit_SUCESS, TAG);
		MessageHandlerManager.get_instance().unregister(
				MSG.Road_Inspection_Register_Submit_FAIL, TAG);
	}

	// 地图消息处理句柄
	private class MainMessageHandler extends Handler {
		MainMessageHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG.Road_Inspection_Register_Query_SUCESS:

				// 登录返回的消息
				ComMsg.VehicleinformationQueryRes res = (ComMsg.VehicleinformationQueryRes) (msg.obj);

				// 查询成功之后显示数据
				// 返回成功

				String RegistrationDate = "";
				if (res.RegistrationDate.toString().contains("T")) {
					// 服务器返回的日期格式2015-08-11T00:00:00
					mRegisterDate.setText(res.RegistrationDate.substring(0,
							res.RegistrationDate.indexOf("T")));
				} else {
					RegistrationDate = res.RegistrationDate.toString();
				}

				// 汽车类型
				if (!res.CarType.equals("NULL")) {
					mCarType.setVisibility(View.VISIBLE);
					spCarType.setVisibility(View.GONE);

					mCarType.setText(res.CarType);
				}

				if (!res.UseNature.equals("NULL")) {
					mUseProperty.setVisibility(View.VISIBLE);
					sp.setVisibility(View.GONE);

					mUseProperty.setText(res.UseNature);
				}

				if (!res.BrandModel.equals("NULL")) {

					mBrandType.setText(res.BrandModel);
				}

				if (!res.EmissionStandards.equals("NULL")) {
					mStandard.setVisibility(View.VISIBLE);
					spEmission.setVisibility(View.GONE);

					mStandard.setText(res.EmissionStandards);
				}

				if (!res.EngineModel.equals("NULL")) {
					mEngineType.setText(res.EngineModel);
				}

				mPassengers.setText(res.Passengers + "");

				mMaxTotalMass.setText(res.MaxTotalMass + "");
				break;

			case MSG.Road_Inspection_Register_Query_FAIL:
				Utils.showToast(context, "没有数据");
				break;

			case MSG.Road_Inspection_Register_Submit_SUCESS:
				// 提交成功之后，写进数据库
				// TODO

				// 登录返回的消息
				ComMsg.RoadCheckUploadRes uploadRes = (ComMsg.RoadCheckUploadRes) (msg.obj);

				if (uploadRes.Success.equals("1")) {// 上传成功
					String result = uploadRes.Result;// 成功返回的是上传数据的主键

					// 上传成功，就把图片写进数据库
					for (ImageItem img : tempSelectBitmap) {

						String name = img.getImagePath()
								.substring(
										img.getImagePath().lastIndexOf(
												File.separator) + 1);
						String localPath = img.getImagePath();

						String fileDate = Utils.getRemoteFileDate()
								+ File.separator;

						StringBuffer picSql = new StringBuffer();
						picSql.append("insert into dt_pic (DataID,DataType,LocalPath,RemotePath,Name,Longitude,Latitude,Status,Remark) ");
						picSql.append("values (" + Integer.parseInt(result)
								+ "," + MSG.RoadPic + ",'" + localPath + "','"
								+ MSG.HFS + "','" + (fileDate + name) + "','"
								+ mlongitude + "','" + mlatitude + "'," + 0
								+ ",'" + "');");
						DB.instance(context).execute(picSql.toString());
					}

					// 将登记的数据写入进数据库

					// 上传数据，成功之后再上传图片
					// "Passengers int, ""MaxTotalMass int, ""EmissionLimits int, "
					// 写数据库
					StringBuilder sql = new StringBuilder();
					sql.append("insert into dt_roadCheckRegister (CarNo,CarColor,CarType,UseNature,RegistrationDate,BrandModel,EngineModel,Passengers,MaxTotalMass,EmissionStandards,EmissionLimits,AmbientTemperature,EnvironmentalPressure,RelativeHumidity,OilTemperature,");
					sql.append("ExcessAirRatio,Low_co,Low_hc,Low_no,Low_o2,Low_co2,High_co,High_hc,High_no,High_o2,High_co2,CheckResult,CheckAddress,Longitude,Latitude,RegisterUser,RegisterTime,ServerId,Smokeopacity1,Smokeopacity2,Smokeopacity3,Smokeopacityavg,PenaltyReceivable,PenaltyCollected,UserResult,fueltype) ");
					sql.append("values ('" + carNumber + "','" + carcolo
							+ "','" + carType + "','" + useProperty + "','"
							+ registerDate + "','" + brandType + "','"
							+ engineType + "'," + passengers + ","
							+ maxTotalMass + ",'" + standard + "',"
							+ emissionlimits + ",'"
							+ roadReq.Ambienttemperature + "','"
							+ roadReq.Environmentalpressure + "','"
							+ roadReq.Relativehumidity + "','" + oilTemp
							+ "','" + air + "','" + lowCo + "','" + lowHc
							+ "','" + lowNo + "','" + lowO2 + "','" + lowCo2
							+ "','" + highCo + "','" + highHc + "','" + highNo
							+ "','" + highO2 + "','" + highCo2 + "','"
							+ roadReq.CheckResult + "','" + pos + "','"
							+ mlongitude + "','" + mlatitude + "','"
							+ MSG.RegisterUser + "','" + registerTime + "','"
							+ result + "','" + yandu1 + "','" + yandu2 + "','"
							+ yandu3 + "','" + avg + "','" + "','" + "','"
							+ roadReq.UserResult + "','" + type + "');");

					boolean isSuccess = DB.instance(context).execute(
							sql.toString());

					if (isSuccess) {
						Utils.showToast(context, "数据已存储，正在上传中");
					} else {
						Utils.showToast(context, "数据存储失败");
					}

					submit.setClickable(false);

					// 点击返回
					getFragmentManager()
							.beginTransaction()
							.replace(R.id.main_framelayout,
									((MainActivity) getActivity()).getmRoad())
							.commit();

				} else {
					Utils.showToast(context, uploadRes.Result);
				}

				break;

			case MSG.Road_Inspection_Register_Submit_FAIL:
				Utils.showToast(context, "服务器连接异常");
				break;
			}
		}
	}

}
