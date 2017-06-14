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

public class RandomInspectionDetailRegister extends Fragment implements
		OnClickListener {
	private static final String ZIYOUJIA = "自由加速数据";

	private static final String SHUANGDAI = "双怠速检测结果";

	private static final String CAIYOU = "柴油";

	private static final String QIYOU = "汽油";

	private static final String TAG = "RandomInspectionDetailRegister";

	Context context;
	private TextView submit;
	private TextView back;
	private Button query;
	private EditText mCarNumber;
	private EditText mCarType;
	private EditText mUseProperty;
	private EditText mRegisterDate;
	private EditText mBrandType;
	private EditText mStandard;
	private EditText mPassengers;
	private EditText mEngineType;
	private EditText mMaxTotalMass;
	private EditText mTemp;
	private EditText mPressure;
	private EditText mRelativeTemp;
	private EditText mYandu1;
	private EditText mYandu2;
	private EditText mYandu3;
	// private EditText mAvYandu;
	private ImageView showCar;
	private ImageView showJiasu;
	private LinearLayout showCarInfoLayout;
	private LinearLayout showCarJiasuLayout;
	private boolean carFlag = false;
	private boolean jiasuFlag = false;
	private boolean carPictureFlag = false;
	private LinearLayout showPicLayout;
	private ImageView showPic;
	private String mCarColor;

	private LocationUtils location;
	private String mlongitude;
	private String mlatitude;

	private EditText checkpos;
	private ImageView showPos;
	private LinearLayout showPosLayout;
	private String sessionID;

	private String emissionlimits;
	private String maxTotalMass;
	private String checkresult;
	private String carNumber;
	private String carcolo;
	private String carType;

	private String usenature;
	private String registrationdate;
	private String brandmodel;
	private String enginemodel;
	private String passengers;

	private String emissionstandards;
	private String ambienttemperature;
	private String environmentalpressure;
	private String checkaddress;
	private String relativehumidity;
	private String smokeopacity1;
	private String smokeopacity2;
	private String smokeopacity3;

	private double smokeopacityavg;

	private String checktime;

	private GridView noScrollgridview;
	private GridAdapter adapter;
	public int max = 0;
	private ArrayList<ImageItem> tempSelectBitmap = new ArrayList<ImageItem>(); // 选择的图片的临时列表
	
	private String spPorpertyStr;
	private String spEmissionStr;

	public RandomInspectionDetailRegister(Context context) {
		this.context = context;
	}

	private int samplingCreateID;

	private boolean carPosFlag = false;
	boolean flag = false;

	
	// ////////////////////////////////////////////////////
	public static Handler _message_handler = null;// 界面消息处理句柄
	private Spinner spEmission;
	private Spinner sp;
	private EditText mfuelType;
	private TextView randomTitle;
	private LinearLayout resultLayout;

	private EditText mShuangTemp;
	private EditText mShuangPress;
	private EditText mShuangHumi;
	private EditText mShuangOilTemp;
	private EditText mShuangExcessRatio;
	private EditText mShuangLowCo;

	private EditText mShuangLowHc;
	private EditText mShuangLowNo;
	private EditText mShuangLowCo2;
	private EditText mShuangLowO2;
	private EditText mShuangHighCo;
	private EditText mShuangHighHc;

	private EditText mShuangHighNo;
	private EditText mShuangHighCo2;
	private EditText mShuangHighO2;

	private String shuangTemp;
	private String shuangPress;
	private String shuangHumi;
	private String shuangOilTemp;
	private String shuangExcessRatio;
	private String shuangLowCo;
	private String shuangLowHc;

	private String shuangLowNo;
	private String shuangLowCo2;
	private String shuangLowO2;
	private String shuangHighCo;
	private String shuangHighNo;
	private String shuangHighCo2;
	private String shuangHighO2;
	private String shuangHighHc;
	
	private String zyXianChangResult;//自由加速数据的现场结果
	private String shXianChangResult;//双怠速的现场结果
	
	private String spCarTypeStr;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.random_check_register, null);

		location = new LocationUtils(context);
		location.initLocation();
		location.startLocation();

		location.setLocationListener(new LocationListener() {

			@Override
			public void onLocationFinished(String longitude, String latitude,
					String loc) {

				if (location != null) {
					checkpos.setText(loc);
					mlongitude = longitude;
					mlatitude = latitude;
					location.stopLocation();
				}
			}

		});

		samplingCreateID = getArguments().getInt("data");

		// 注册消息
		register_message();
		init(view);
		return view;
	}

	private void init(View view) {
		back = (TextView) view.findViewById(R.id.bt_data_back);
		query = (Button) view.findViewById(R.id.bt_random_data_query);
		submit = (TextView) view.findViewById(R.id.bt_random_submit);

		mCarNumber = (EditText) view
				.findViewById(R.id.et_random_data_carnumber);

		mCarType = (EditText) view.findViewById(R.id.et_random_data_cartype);
		mUseProperty = (EditText) view
				.findViewById(R.id.et_random_data_property);
		mRegisterDate = (EditText) view
				.findViewById(R.id.et_random_data_register_date);
		mBrandType = (EditText) view.findViewById(R.id.et_random_data_brand);
		mStandard = (EditText) view.findViewById(R.id.et_random_data_standard);
		mPassengers = (EditText) view
				.findViewById(R.id.et_random_data_passenger_count);
		mEngineType = (EditText) view
				.findViewById(R.id.et_random_data_enginetype);
		mMaxTotalMass = (EditText) view
				.findViewById(R.id.et_random_data_maxtotalmass);
		mfuelType = (EditText) view
				.findViewById(R.id.et_random_data_fueltype);

		mTemp = (EditText) view.findViewById(R.id.et_random_data_environ_temp);
		mPressure = (EditText) view
				.findViewById(R.id.et_random_data_environ_press);
		mRelativeTemp = (EditText) view
				.findViewById(R.id.et_random_data_relative_temp);

		// 检测结果 烟度
		mYandu1 = (EditText) view.findViewById(R.id.et_random_data_yandu1);
		mYandu2 = (EditText) view.findViewById(R.id.et_random_data_yandu2);
		mYandu3 = (EditText) view.findViewById(R.id.et_random_data_yandu3);
		// mAvYandu = (EditText) view
		// .findViewById(R.id.et_random_data_pingjun_yandu);
		
		//双怠速的检测结果
		mShuangTemp = (EditText) view.findViewById(R.id.et_random_data_temp);
		mShuangPress = (EditText) view.findViewById(R.id.et_random_data_pres);
		mShuangHumi = (EditText) view.findViewById(R.id.et_random_data_relativetemp);
		mShuangOilTemp = (EditText) view.findViewById(R.id.et_random_data_oiltemp);
		mShuangExcessRatio = (EditText) view.findViewById(R.id.et_random_data_air);
		mShuangLowCo = (EditText) view.findViewById(R.id.et_random_data_low_co);
		mShuangLowHc = (EditText) view.findViewById(R.id.et_random_data_low_hc);
		mShuangLowNo = (EditText) view.findViewById(R.id.et_random_data_low_no);
		mShuangLowCo2 = (EditText) view.findViewById(R.id.et_random_data_low_co2);
		mShuangLowO2 = (EditText) view.findViewById(R.id.et_random_data_low_o2);
		mShuangHighCo = (EditText) view.findViewById(R.id.et_random_data_high_co);
		mShuangHighHc = (EditText) view.findViewById(R.id.et_random_data_high_hc);
		mShuangHighNo = (EditText) view.findViewById(R.id.et_random_data_high_no);
		mShuangHighCo2 = (EditText) view.findViewById(R.id.et_random_data_high_co2);
		mShuangHighO2 = (EditText) view.findViewById(R.id.et_random_data_high_o2);

		checkpos = (EditText) view.findViewById(R.id.et_sampling_checkpos);

		showCar = (ImageView) view.findViewById(R.id.bt_data_showcar);
		showJiasu = (ImageView) view.findViewById(R.id.bt_data_jiasu);
		showPic = (ImageView) view.findViewById(R.id.bt_data_showpic);
		showPos = (ImageView) view.findViewById(R.id.bt_sampling_showpos);

		// 显示和隐藏布局
		showCarInfoLayout = (LinearLayout) view
				.findViewById(R.id.random_data_car_info_layout);

		showCarJiasuLayout = (LinearLayout) view
				.findViewById(R.id.random_data_jiasu_layout);
		showPicLayout = (LinearLayout) view
				.findViewById(R.id.random_data_car_pic_layout);
		showPosLayout = (LinearLayout) view
				.findViewById(R.id.sampling_data_car_pos_layout);
		
		
		resultLayout = (LinearLayout) view
		.findViewById(R.id.random_data_car_result_layout);
		
		
		randomTitle = (TextView)view
				.findViewById(R.id.random_title);

		back.setOnClickListener(this);
		query.setOnClickListener(this);
		submit.setOnClickListener(this);
		showCar.setOnClickListener(this);
		showJiasu.setOnClickListener(this);
		showPic.setOnClickListener(this);
		showPos.setOnClickListener(this);

		Spinner colorSpinner = (Spinner) view
				.findViewById(R.id.random_data_register_color);
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
				// TODO Auto-generated method stub

			}
		});

		noScrollgridview = (GridView) view
				.findViewById(R.id.random_noScrollgridview);
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
				} else {// 点击的是其他图片

					// 使用Intent
					Intent intent = new Intent(Intent.ACTION_VIEW);
					String path = tempSelectBitmap.get(position).getImagePath();
					
					
					if (path != null) {
						
						if (path.contains("jpg")){
							
							intent.setDataAndType(Uri.fromFile(new File(path)),
									"image/*");
							startActivity(intent);
							
						} else if (path.contains("mp4")){
							
							Intent videoIntent = new Intent(context,
									PlayVideo.class);
							videoIntent.putExtra("path", path);
							context.startActivity(videoIntent);
//							intent.setDataAndType(Uri.fromFile(new File(path)),
//									"video/*");
						}
						
					}
					
				}
			}
		});

		
		sp = (Spinner) view.findViewById(R.id.et_random_data_property_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.use_property, R.layout.myspinner);
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
		
		
		
		spEmission = (Spinner) view.findViewById(R.id.et_random_data_standard_spinner);
		ArrayAdapter<CharSequence> adapterEmission = ArrayAdapter.createFromResource(context, R.array.emission_standard, R.layout.myspinner);
		adapterEmission.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
		
		
		Spinner fuelSp = (Spinner) view.findViewById(R.id.et_random_data_fueltype_spinner);
		ArrayAdapter<CharSequence> fuelAdapter = ArrayAdapter.createFromResource(context, R.array.random_fuel_type, R.layout.myspinner);
		fuelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		fuelSp.setAdapter(fuelAdapter);
		fuelSp.setOnItemSelectedListener(new OnItemSelectedListener() {


			

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				type = (String) parent.getItemAtPosition(position);
				if (type.equals(QIYOU)) {
					randomTitle.setText(SHUANGDAI);
					resultLayout.setVisibility(View.VISIBLE);
					showCarJiasuLayout.setVisibility(View.GONE);
				} else if (type.equals(CAIYOU)) {
					randomTitle.setText(ZIYOUJIA);
					resultLayout.setVisibility(View.GONE);
					showCarJiasuLayout.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		
		
		// 双怠速的现场结果
				Spinner spShuangResult = (Spinner) view
						.findViewById(R.id.et_random_xianchang_result);
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
						shXianChangResult = (String) parent
								.getItemAtPosition(position);
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});

				// 自由加速数据的现场结果
				Spinner spZiyouResult = (Spinner) view
						.findViewById(R.id.sp_random_shuang_xianchang_result);
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
						zyXianChangResult = (String) parent
								.getItemAtPosition(position);
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});
				
				
				spCarType = (Spinner) view
						.findViewById(R.id.et_random_data_cartype_spinner);
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
						spCarTypeStr = (String) parent
								.getItemAtPosition(position);
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});
		

		mCarNumber.setText("鄂A00073");
		// mBrandType.setText("daf");
		// mEngineType.setText("dfa");
		// mTemp.setText("11");
		// mPressure.setText("12");
		// mRelativeTemp.setText("13");
		// mYandu1.setText("5");
		// mYandu2.setText("5");
		// mYandu3.setText("5");
		// mAvYandu.setText("5");
		
		
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
		
		mCarType.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCarType.setVisibility(View.GONE);
				spCarType.setVisibility(View.VISIBLE);
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
				Utils.Random_PICTURE);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.bt_data_back:
			getFragmentManager()
					.beginTransaction()
					.replace(
							R.id.main_framelayout,
							((MainActivity) getActivity()).getmRandom()
									.getRandomInspectionDetail()).commit();
			break;

		case R.id.bt_random_data_query:
			String sessionId = null;
			String carNo = mCarNumber.getText().toString().trim();
			String carColor = mCarColor;// TODO

			if (TextUtils.isEmpty(carNo) || TextUtils.isEmpty(carColor)) {
				Toast.makeText(context, "需填入车牌和颜色", 0).show();
			} else {

				// 查询数据
				VehicleinformationQueryRequest queryRequest = new VehicleinformationQueryRequest(
						sessionId, carNo, carColor);

				// TODO 查询的过程，
				ComMsg.VehicleinformationQueryReq vehicleReq = new ComMsg.VehicleinformationQueryReq();
				vehicleReq.CarColor = carColor;
				vehicleReq.CarNo = carNo;
				vehicleReq.SessionID = MSG.SessionID;
				ComInterface.VehicleinformationQuery(context, vehicleReq, 1);

			}

			break;

		case R.id.bt_random_submit:
			sessionID = MSG.SessionID;
			emissionlimits = null;
			maxTotalMass = mMaxTotalMass.getText().toString();
			checkresult = null;
			carNumber = mCarNumber.getText().toString().trim();
			carcolo = mCarColor;
			
			usenature = mUseProperty.getText().toString().trim();
			registrationdate = mRegisterDate.getText().toString().trim();
			brandmodel = mBrandType.getText().toString().trim();
			enginemodel = mEngineType.getText().toString().trim();
			passengers = mPassengers.getText().toString().trim();
			emissionstandards = mStandard.getText().toString().trim();
			ambienttemperature = mTemp.getText().toString().trim();
			environmentalpressure = mPressure.getText().toString().trim();
			relativehumidity = mRelativeTemp.getText().toString().trim();
			checkaddress = checkpos.getText().toString().trim();
			smokeopacity1 = mYandu1.getText().toString().trim();
			smokeopacity2 = mYandu2.getText().toString().trim();
			smokeopacity3 = mYandu3.getText().toString().trim();
			
			//双怠速结果
			shuangTemp = mShuangTemp.getText().toString().trim();
			shuangPress = mShuangPress.getText().toString().trim();
			shuangHumi = mShuangHumi.getText().toString().trim();
			shuangOilTemp = mShuangOilTemp.getText().toString().trim();
			shuangExcessRatio = mShuangExcessRatio.getText().toString().trim();
			shuangLowCo = mShuangLowCo.getText().toString().trim();
			shuangLowHc = mShuangLowHc.getText().toString().trim();
			shuangLowNo = mShuangLowNo.getText().toString().trim();
			shuangLowCo2 = mShuangLowCo2.getText().toString().trim();
			shuangLowO2 = mShuangLowO2.getText().toString().trim();
			shuangHighCo = mShuangHighCo.getText().toString().trim();
			shuangHighNo = mShuangHighNo.getText().toString().trim();
			shuangHighCo2 = mShuangHighCo2.getText().toString().trim();
			shuangHighO2 = mShuangHighO2.getText().toString().trim();
			shuangHighHc = mShuangHighHc.getText().toString().trim();
			
			
			if (mStandard.isShown()){//排放标准显示的是文本框
				emissionstandards = mStandard.getText().toString();
			} else {//排放标准显示的是下拉框
				emissionstandards = spEmissionStr;
			}
			
			if (mUseProperty.isShown()){
				usenature = mUseProperty.getText().toString();
			} else {
				usenature = spPorpertyStr;
			}
			
			
			if (mUseProperty.isShown()){
				carType = mCarType.getText().toString().trim();
			} else {
				carType = spCarTypeStr;
			}
			

			if (!TextUtils.isEmpty(smokeopacity1)
					&& !TextUtils.isEmpty(smokeopacity2)
					&& !TextUtils.isEmpty(smokeopacity3)) {

				smokeopacityavg = (Double.parseDouble(smokeopacity1)
						+ Double.parseDouble(smokeopacity2) + Double
						.parseDouble(smokeopacity3)) / 3;
			}

			String avg = (String.format("%.2f", smokeopacityavg));
			smokeopacityavg = Double.valueOf(avg);
			checktime = Utils.getCurrentTime();
			
			if (!TextUtils.isEmpty(carNumber) && !TextUtils.isEmpty(carcolo)
					&& !TextUtils.isEmpty(carType)
					&& !TextUtils.isEmpty(usenature)
					&& !TextUtils.isEmpty(registrationdate)
					&& !TextUtils.isEmpty(brandmodel)
					&& !TextUtils.isEmpty(enginemodel)
					&& !TextUtils.isEmpty(passengers)
					&& !TextUtils.isEmpty(emissionstandards)  && ((type.equals(QIYOU) && !TextUtils.isEmpty(shuangTemp)
							&& !TextUtils.isEmpty(shuangPress)
							&& !TextUtils.isEmpty(shuangHumi)
							&& !TextUtils.isEmpty(shuangOilTemp) && !TextUtils.isEmpty(shuangExcessRatio)
							&& !TextUtils.isEmpty(shuangLowCo) && !TextUtils.isEmpty(shuangLowHc)
							&& !TextUtils.isEmpty(shuangLowNo) && !TextUtils.isEmpty(shuangLowO2)
							&& !TextUtils.isEmpty(shuangLowCo2) && !TextUtils.isEmpty(shuangHighCo)
							&& !TextUtils.isEmpty(shuangHighHc) && !TextUtils.isEmpty(shuangHighNo)
							&& !TextUtils.isEmpty(shuangHighO2)) || ( type.equals(CAIYOU)  && !TextUtils.isEmpty(ambienttemperature) && !TextUtils.isEmpty(environmentalpressure)
									&& !TextUtils.isEmpty(relativehumidity) && !TextUtils.isEmpty(smokeopacity1) && !TextUtils.isEmpty(smokeopacity2) && !TextUtils.isEmpty(smokeopacity3) ) )){
				
				
				samplingReq = new ComMsg.SamplingRegisterUploadReq();
				samplingReq.SessionID = MSG.SessionID;
				samplingReq.SamplingCreateid = samplingCreateID + "";
				samplingReq.CarNo = carNumber;
				samplingReq.CarColor = carcolo;
				samplingReq.CarType = carType;
				samplingReq.Usenature = usenature;
				samplingReq.Registrationdate = registrationdate;
				samplingReq.Brandmodel = brandmodel;
				samplingReq.MaxTotalMass = maxTotalMass;
				samplingReq.Enginemodel = enginemodel;
				samplingReq.Passengers = passengers;
				samplingReq.Emissionstandards = emissionstandards;
				samplingReq.Checkaddress = checkaddress;
				
				samplingReq.fueltype = type;//油气类型
				samplingReq.Checktime = checktime;
				samplingReq.Longitude = mlongitude;
				samplingReq.Latitude = mlatitude;
				
				samplingReq.PenaltyReceivable = "";
				samplingReq.PenaltyCollected = "";
				samplingReq.UserResult = "";
				//根据柴油和汽油来确定
				if (type.equals(CAIYOU)){
					// TODO 提交数据
					// 上传数据，成功之后再上传图片
					

					samplingReq.Ambienttemperature = ambienttemperature;
					samplingReq.Environmentalpressure = environmentalpressure;
					samplingReq.Relativehumidity = relativehumidity;
					samplingReq.Smokeopacity1 = smokeopacity1;
					samplingReq.Smokeopacity2 = smokeopacity2;
					samplingReq.Smokeopacity3 = smokeopacity3;
					samplingReq.Smokeopacityavg = smokeopacityavg + "";
					
					//双怠速
					samplingReq.Oiltemperature = "";
					samplingReq.Excessairratio = "";
					samplingReq.Low_co = "";
					samplingReq.Low_hc = "";
					samplingReq.Low_no = "";
					samplingReq.Low_o2 = "";
					samplingReq.Low_co2 = "";
					
					samplingReq.High_co = "";
					samplingReq.High_hc = "";
					samplingReq.High_no = "";
					samplingReq.High_o2 = "";
					samplingReq.High_co2 = "";
					
					
					

					// 计算checkresult 0合格， 1不合格

					String RegistrationDate = "";
					if (registrationdate.contains("T")) {
						// 服务器返回的日期格式2015-08-11T00:00:00
						RegistrationDate = registrationdate.replace("T", " ");
					} else {
						RegistrationDate = registrationdate;
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
					samplingReq.Emissionlimits = opaueid + "";
					samplingReq.Checkresult = checkresult;
					
					if (zyXianChangResult.equals("合格")){
						samplingReq.UserResult = "0";
					} else if (zyXianChangResult.equals("不合格")){
						samplingReq.UserResult = "1";
					}
					
				} else if (type.equals(QIYOU)){
					
					// 提交
					samplingReq.Ambienttemperature = shuangTemp;
					samplingReq.Environmentalpressure = shuangPress;
					samplingReq.Relativehumidity = shuangHumi;
					samplingReq.Oiltemperature = shuangOilTemp;
					samplingReq.Excessairratio = shuangExcessRatio;
					samplingReq.Low_co = shuangLowCo;
					samplingReq.Low_hc = shuangLowHc;
					samplingReq.Low_no = shuangLowNo;
					samplingReq.Low_o2 = shuangLowO2;
					samplingReq.Low_co2 = shuangLowCo2;
					samplingReq.High_co = shuangHighCo;
					samplingReq.High_hc = shuangHighHc;
					samplingReq.High_no = shuangHighNo;
					samplingReq.High_o2 = shuangHighO2;
					samplingReq.High_co2 = shuangHighCo2;

					
					
					//自由加速的数据为空
					samplingReq.Smokeopacity1 = "";
					samplingReq.Smokeopacity2 = "";
					samplingReq.Smokeopacity3 = "";
					samplingReq.Smokeopacityavg = "";

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
					if (registrationdate.contains("T")) {
						// 服务器返回的日期格式2015-08-11T00:00:00
						RegistrationDate = registrationdate.replace("T", " ");
					} else {
						RegistrationDate = registrationdate;
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
								limit_start_time = query2.get("limit_start_time_"
										+ i);
							}

							String limit_end_time = null;
							if (!query2.get("limit_end_time_" + i).isEmpty()) {
								limit_end_time = query2.get("limit_end_time_" + i);
							}

							int id = Integer
									.parseInt(query2
											.get("two_speed_idle_detection_limits_id_"
													+ i));

							if (limit_start_time == null
									&& (limit_end_time.compareTo(RegistrationDate) >= 0)
									|| (limit_end_time == null && (limit_start_time
											.compareTo(RegistrationDate) <= 0))
									|| (limit_start_time != null
											&& limit_end_time != null
											&& (limit_start_time
													.compareTo(RegistrationDate) <= 0) && (limit_end_time
											.compareTo(RegistrationDate) >= 0)))
								two_speed_idle_detection_limits_id = id;

						}

						emissionlimits = two_speed_idle_detection_limits_id +"";
					}

					// 根据id找出对应 的那条记录
					HashMap<String, String> query3 = DB
							.instance(context)
							.query("select * from tb_two_speed_idle_detection_limits where two_speed_idle_detection_limits_id = "
									+ two_speed_idle_detection_limits_id + ";");

					String checkResult = "";
					// co_idle_speed text co低怠速限值
					// hc_idle_speed text hc低怠速限值
					// co_high_idle_speed text co高怠速限值
					// hc_high_idle_speed text hc高怠速限值
					if (query3 != null) {

						float col = Float.parseFloat(shuangLowCo);
						float hcl = Float.parseFloat(shuangLowHc);
						float coh = Float.parseFloat(shuangHighCo);
						float hch = Float.parseFloat(shuangHighHc);
						float airfact = Float.parseFloat(shuangExcessRatio);

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

					samplingReq.Emissionlimits = emissionlimits + "";
					samplingReq.Checkresult = checkResult;
					
					
					
					if (shXianChangResult.equals("合格")){
						samplingReq.UserResult = "0";
					} else if (shXianChangResult.equals("不合格")){
						samplingReq.UserResult = "1";
					}
					
				}
				ComInterface.SamplingRegisterUpload(context, samplingReq);
				
				
			} else {
				Toast.makeText(context, "请填写完整。。。", 0).show();
			}


			break;

		case R.id.bt_data_showcar:
			if (!carFlag) {// 当前是展开的，需要收缩
				showCarInfoLayout.setVisibility(View.GONE);
				carFlag = true;
			} else {// 当前收缩，点击展开
				showCarInfoLayout.setVisibility(View.VISIBLE);
				carFlag = false;
			}
			break;

		case R.id.bt_data_jiasu:
			if (!jiasuFlag) {// 当前是展开的，需要收缩
				
				if (randomTitle.getText().equals(ZIYOUJIA)){
					showCarJiasuLayout.setVisibility(View.GONE);
				} else if (randomTitle.getText().equals(SHUANGDAI)){
					resultLayout.setVisibility(View.GONE);
				}
				
				jiasuFlag = true;
			} else {// 当前收缩，点击展开
				
				if (randomTitle.getText().equals(ZIYOUJIA)){
					showCarJiasuLayout.setVisibility(View.VISIBLE);
				} else if (randomTitle.getText().equals(SHUANGDAI)){
					resultLayout.setVisibility(View.VISIBLE);
				}
				
				jiasuFlag = false;
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

		case R.id.bt_sampling_showpos:
			if (!carPosFlag) {// 当前是展开的，需要收缩
				showPosLayout.setVisibility(View.GONE);
				carPosFlag = true;
			} else {// 当前收缩，点击展开
				showPosLayout.setVisibility(View.VISIBLE);
				carPosFlag = false;
			}
			break;

		default:
			break;
		}

	}

	private String filePath = null;
	private String videoFilePath = null;

	private ComMsg.SamplingRegisterUploadReq samplingReq;

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
				Utils.Random_TAKE_PICTURE);
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
				Utils.Random_TAKE_VIDEO);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (tempSelectBitmap.size() < 9
				&& resultCode == getActivity().RESULT_OK) {// cq

			String fileUrl = null;

			Bitmap bm = null;
			if (requestCode == Utils.Random_TAKE_PICTURE){
				
				bm = Bimp.decodeSampleBitmapFromFile(filePath, 100, 100);
				fileUrl = filePath;
			} else if (requestCode == Utils.Random_TAKE_VIDEO){
				
				bm =  Utils.getVideoThumbnail(videoFilePath, 100, 100,
						MediaStore.Images.Thumbnails.MINI_KIND);
				
				fileUrl = videoFilePath;
			}else if (requestCode == Utils.Random_PICTURE){
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
				MSG.RandomInspectionDetailRegister_SUCESS, TAG);
		MessageHandlerManager.get_instance().register(_message_handler,
				MSG.RandomInspectionDetailRegister_FAIL, TAG);

		MessageHandlerManager.get_instance().register(_message_handler,
				MSG.RandomInspectionDetailRegister_Submit_SUCESS, TAG);
		MessageHandlerManager.get_instance().register(_message_handler,
				MSG.RandomInspectionDetailRegister_Submit_FAIL, TAG);
	}

	// 注销地图处理消息
	private void unregister_message() {
		MessageHandlerManager.get_instance().unregister(
				MSG.RandomInspectionDetailRegister_SUCESS, TAG);
		MessageHandlerManager.get_instance().unregister(
				MSG.RandomInspectionDetailRegister_FAIL, TAG);

		MessageHandlerManager.get_instance().unregister(
				MSG.RandomInspectionDetailRegister_Submit_SUCESS, TAG);
		MessageHandlerManager.get_instance().unregister(
				MSG.RandomInspectionDetailRegister_Submit_FAIL, TAG);
	}

	// 地图消息处理句柄
	private class MainMessageHandler extends Handler {
		MainMessageHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG.RandomInspectionDetailRegister_SUCESS:

				// 登录返回的消息
				ComMsg.VehicleinformationQueryRes res = (ComMsg.VehicleinformationQueryRes) (msg.obj);

				// 查询成功之后显示数据
				// 返回成功
				// TODO 不完整，看接口
				

				if (res.RegistrationDate.contains("T")) {
					mRegisterDate.setText(res.RegistrationDate.substring(0,
							res.RegistrationDate.indexOf("T")));
				}else {
					mRegisterDate.setText(res.RegistrationDate);
				}


				if (!res.BrandModel.equals("NULL")) {
					mBrandType.setText(res.BrandModel);
				}

//				if (!res.EmissionStandards.equals("NULL")) {
//					mStandard.setText(res.EmissionStandards);
//				}

				if (!res.EngineModel.equals("NULL")) {
					mEngineType.setText(res.EngineModel);
				}
				
				
				//输入框可见，下来框不可见
				if (!res.UseNature.equals("NULL")){
					mUseProperty.setVisibility(View.VISIBLE);
					sp.setVisibility(View.GONE);
					
					mUseProperty.setText(res.UseNature);
				} 
				
				if (!res.CarType.equals("NULL")){
					mCarType.setVisibility(View.VISIBLE);
					spCarType.setVisibility(View.GONE);
					
					mCarType.setText(res.CarType);
				} 
				


				if (!res.BrandModel.equals("NULL")) {
					
					mBrandType.setText(res.BrandModel);
				}

				if (!res.EmissionStandards.equals("NULL")) {
					mStandard.setVisibility(View.VISIBLE);
					spEmission.setVisibility(View.GONE);
					
					mStandard.setText(res.EmissionStandards);
				} 
				
				mPassengers.setText(res.Passengers + "");
				mMaxTotalMass.setText(res.MaxTotalMass + "");
				break;

			case MSG.RandomInspectionDetailRegister_FAIL:
				Utils.showToast(context, "服务器连接异常");
				break;

			case MSG.RandomInspectionDetailRegister_Submit_SUCESS:

				// 返回的消息
				ComMsg.SamplingRegisterUploadRes uploadRes = (ComMsg.SamplingRegisterUploadRes) (msg.obj);
				if (uploadRes.Success.equals("1")) {
					String result = uploadRes.Result;// 成功返回的是上传数据的主键

					// 成功,写数据库，图片和文字两张表
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
								+ "," + MSG.RandomPic + ",'" + localPath
								+ "','" + MSG.HFS + "','" + (fileDate + name)
								+ "','" + mlongitude + "','" + mlatitude + "',"
								+ 0 + ",'" + "');");
						DB.instance(context).execute(picSql.toString());
					}

					// 写数据库,这里的samplingCreateId是从上个页面传递过来的
					StringBuilder sql = new StringBuilder();
					sql.append("insert into dt_samplingRegister (SamplingCreateID,CarNo,CarColor,CarType,UseNature,RegistrationDate,BrandModel,EngineModel,Passengers,MaxTotalMass,EmissionStandards,EmissionLimits,AmbientTemperature,EnvironmentalPressure,RelativeHumidity,");
					sql.append("SmokeOpacity1,SmokeOpacity2,SmokeOpacity3,SmokeOpacityAVG,CheckResult,Longitude,Latitude,RegisterUser,CheckTime,CheckAddress,ServerId,fueltype,Oiltemperature,Excessairratio,Low_co,Low_hc,Low_no,Low_o2,Low_co2,High_co,High_hc,High_no,High_o2,High_co2,PenaltyReceivable,PenaltyCollected,UserResult) ");
					sql.append("values (" + samplingCreateID + ",'" + carNumber
							+ "','" + carcolo + "','" + carType + "','"
							+ usenature + "','" + registrationdate + "','"
							+ brandmodel + "','" + enginemodel + "',"
							+ passengers + "," + maxTotalMass + ",'"
							+ emissionstandards + "','" + emissionlimits
							+ "','" + samplingReq.Ambienttemperature + "','"
							+ samplingReq.Environmentalpressure + "','" + samplingReq.Relativehumidity
							+ "','" + smokeopacity1 + "','" + smokeopacity2
							+ "','" + smokeopacity3 + "','" + smokeopacityavg
							+ "','" + checkresult + "','" + mlongitude + "','"
							+ mlatitude + "','" + MSG.RegisterUser + "','"
							+ checktime + "','" + checkaddress + "','" + result+ "','" + type+ "','" + shuangOilTemp+ "','" + shuangExcessRatio+ "','" + shuangLowCo+ "','" + shuangLowHc+ "','" + shuangLowNo+ "','" + shuangLowO2+ "','" + shuangLowCo2+ "','" + shuangHighCo+ "','" + shuangHighHc+ "','" + shuangHighNo+ "','" + shuangHighO2+ "','" + shuangHighCo2+ "','" +  "','" + "','"+samplingReq.UserResult 
							+ "');");

					boolean isSuccess = DB.instance(context).execute(
							sql.toString());

					if (isSuccess) {
						Utils.showToast(context, "数据已存储，正在上传中");
					} else {
						Utils.showToast(context, "数据存储失败");
					}

					// 提交成功返回
					getFragmentManager()
							.beginTransaction()
							.replace(
									R.id.main_framelayout,
									((MainActivity) getActivity()).getmRandom()
											.getRandomInspectionDetail())
							.commit();

					submit.setClickable(false);

				} else {
					Utils.showToast(context, uploadRes.Result);
				}

				break;

			case MSG.RandomInspectionDetailRegister_Submit_FAIL:
				Utils.showToast(context, "服务器连接异常");
				break;

			}
		}
	}
}
