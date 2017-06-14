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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.cargas.R;
import com.example.cargas.activity.MainActivity;
import com.example.cargas.adapter.MyGridViewAdapter;
import com.example.cargas.database.DB;
import com.example.cargas.model.RoadCheckQueryResponse;

public class RoadInspectionDetail extends Fragment implements OnClickListener {

	private static final String SHUANGDAISUJIANCE = "双怠速检测结果数据";
	private static final String ZIYOU = "自由加速数据";
	private TextView carNumber;
	private TextView back;
	private TextView carColor;
	private TextView carType;
	private TextView carProperty;
	private TextView carDate;
	private TextView carBrand;
	private TextView carStandard;
	private TextView carPeople;
	private TextView carEngine;
	private ImageView showCar;

	private LinearLayout carDetailLayout;
	// private LinearLayout carStadardLayout;
	private LinearLayout carResultLayout;
	private LinearLayout carPositionLayout;
	private LinearLayout carPictureLayout;

	// 值为false时布局展开，值为true时布局收缩
	private boolean carFlag = false;
	private boolean carStadardFlag = false;
	private boolean carResultFlag = false;
	private boolean carPositionFlag = false;
	private boolean carPictureFlag = false;
	// private ImageButton showStandard;
	private ImageView showdResult;
	private ImageView showPos;
	private ImageView showPic;
	private TextView mTemp;
	private TextView press;
	private TextView relativeTemp;
	private TextView resultAir;
	private TextView resultLowCo;
	private TextView resultLowHc;
	private TextView resultLowNo;
	private TextView resultLowCo2;
	private TextView resultLowO2;
	private TextView resultHighCo;
	private TextView resultHighHc;
	private TextView resultHighNo;
	private TextView resultHighCo2;
	private TextView resultHighO2;
	private TextView limit;
	private TextView limitLowCo;
	private TextView limitLowHc;
	private TextView limitLowNo;
	private TextView limitLowCo2;
	private TextView limitLowO2;
	private TextView limitHighCo;
	private TextView limitHighHc;
	private TextView limitHighNo;
	private TextView limitHighCo2;
	private TextView limitHighO2;
	private TextView resultQua;
	private TextView checkPos;
	private RoadCheckQueryResponse response;
	List<String> content = new ArrayList<String>();// 图片数据
	Context context;
	private TextView maxMass;
	private TextView oilTemp;
	private TextView title;
	private TextView roadEnvTemp;
	private TextView roadEnvPress;
	private TextView roadRelHumi;
	private TextView roadYandu1;
	private TextView roadYandu2;
	private TextView roadYandu3;
	private TextView roadPingjun;
	private TextView roadLimit;
	private TextView roadResult;
	private LinearLayout ziYouJiasuLayout;
	private TextView shXianChangResult;
	private TextView zyXianChangResult;
	private TextView fuelType;

	public RoadInspectionDetail(Context context) {
		this.context = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.road_data_detail, null);
		response = (RoadCheckQueryResponse) getArguments().getSerializable(
				"data");

		initView(view);
		showData();
		return view;
	}

	// 数据展示
	private void showData() {

		if (response != null) {
			carNumber.setText(response.getCarNo());
			carColor.setText(response.getCarColor());
			carType.setText(response.getCarType());
			carProperty.setText(response.getUseNature());
			carDate.setText(response.getRegistrationDate());
			carBrand.setText(response.getBrandModel());
			carStandard.setText(response.getEmissionStandards());
			carPeople.setText(response.getPassengers());
			carEngine.setText(response.getEngineModel());
			maxMass.setText(response.getMaxTotalMass());
			fuelType.setText(response.getFuelType());
			
			if (response.getFuelType().equals("汽油")){
				
				carResultLayout.setVisibility(View.VISIBLE);
				ziYouJiasuLayout.setVisibility(View.GONE);
				title.setText(SHUANGDAISUJIANCE);
				
				mTemp.setText(response.getAmbientTemperature());
				press.setText(response.getEnvironmentalPressure());
				relativeTemp.setText(response.getRelativeHumidity());
				oilTemp.setText(response.getOilTemperature());
				
				HashMap<String, String> query = DB
						.instance(context)
						.query("select * from tb_two_speed_idle_detection_limits where two_speed_idle_detection_limits_id = "
								+ response.getEmissionLimits() + ";");

				if (query != null) {
					// TODO 几口返回排放限值id,然后根据这个id查询本地数据库，获取值
					limit.setText(query.get("excess_air_factor_lower_limit_0")
							+ "~" + query.get("excess_air_factor_upper_limit_0"));

					limitLowCo.setText(query.get("co_idle_speed_0"));
					limitLowHc.setText(query.get("hc_idle_speed_0"));
					limitLowNo.setText("-");
					limitLowCo2.setText("-");
					limitLowO2.setText("-");

					limitHighCo.setText(query.get("co_high_idle_speed_0"));
					limitHighHc.setText(query.get("hc_high_idle_speed_0"));
					limitHighNo.setText("-");
					limitHighCo2.setText("-");
					limitHighO2.setText("-");
				}

				// TODO result对应接口文档
				resultAir.setText(response.getExcessAirRatio());
				resultLowCo.setText(response.getLow_co());
				resultLowHc.setText(response.getLow_hc());
				resultLowNo.setText(response.getLow_no());
				resultLowCo2.setText(response.getLow_co2());
				resultLowO2.setText(response.getLow_o2());

				resultHighCo.setText(response.getHigh_co());
				resultHighHc.setText(response.getHigh_hc());
				resultHighNo.setText(response.getHigh_no());
				resultHighCo2.setText(response.getHigh_co2());
				resultHighO2.setText(response.getHigh_o2());

				if (response.getCheckResult().equals("0")) {
					resultQua.setText("合格");
				} else {
					resultQua.setText("不合格");
					resultQua.setTextColor(getResources().getColor(R.color.red));
				}
				
				if (response.getUserResult().equals("0")) {
					shXianChangResult.setText("合格");
				} else if (response.getUserResult().equals("1")) {
					shXianChangResult.setText("不合格");
					shXianChangResult.setTextColor(getResources().getColor(R.color.red));
				}
				
				
				
			} else if (response.getFuelType().equals("柴油")){
				
				carResultLayout.setVisibility(View.GONE);
				ziYouJiasuLayout.setVisibility(View.VISIBLE);
				
				title.setText(ZIYOU);
				
				roadEnvTemp.setText(response.getAmbientTemperature());
				roadEnvPress.setText(response.getEnvironmentalPressure());
				roadRelHumi.setText(response.getRelativeHumidity());
				roadYandu1.setText(response.getSmokeopacity1());
				roadYandu2.setText(response.getSmokeopacity2());
				roadYandu3.setText(response.getSmokeopacity3());
				roadPingjun.setText(response.getSmokeopacityavg());
				
				if (response.getCheckResult().equals("0")){
					roadResult.setText("合格");
//					roadResult.setTextColor(getResources().getColor(R.color.myblue1));
				} else if (response.getCheckResult().equals("1")) {
					roadResult.setText("不合格");
					roadResult.setTextColor(getResources().getColor(R.color.red));
				}
				
				
				// 获取限值
				String RegistrationDate = "";
				if (response.getRegistrationDate().contains("T")) {
					// 服务器返回的日期格式2015-08-11T00:00:00,不要具体的时间
					RegistrationDate = response.getRegistrationDate().replace("T", " ");
				} else {
					RegistrationDate = response.getRegistrationDate();
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

					double limits = Double.parseDouble(query2.get("smoke_limit_0"));
					roadLimit.setText(limits + "");
				}
				
			}
			
			
			checkPos.setText(response.getCheckAddress());
			
			//现场结果
			if (response.getUserResult().equals("0")) {
				zyXianChangResult.setText("合格");
			} else if (response.getUserResult().equals("1")) {
				zyXianChangResult.setText("不合格");
				zyXianChangResult.setTextColor(getResources().getColor(R.color.red));
			}

		}

	}

	private void initView(View view) {
		back = (TextView) view.findViewById(R.id.bt_data_detail_detail_back);

		// 车辆信息
		carNumber = (TextView) view
				.findViewById(R.id.tv_road_data_detail_carnumber);
		carColor = (TextView) view
				.findViewById(R.id.tv_road_data_detail_carcolor);
		carType = (TextView) view
				.findViewById(R.id.tv_road_data_detail_cartype);
		carProperty = (TextView) view
				.findViewById(R.id.tv_road_data_detail_property);

		carDate = (TextView) view
				.findViewById(R.id.tv_road_data_detail_register_date);
		carBrand = (TextView) view.findViewById(R.id.tv_road_data_detail_brand);
		carStandard = (TextView) view
				.findViewById(R.id.tv_road_data_detail_standard);
		carPeople = (TextView) view
				.findViewById(R.id.tv_road_data_detail_passenger_count);
		carEngine = (TextView) view
				.findViewById(R.id.tv_road_data_detail_enginetype);
		maxMass = (TextView) view.findViewById(R.id.tv_road_data_maxtotalmass);
		fuelType = (TextView) view.findViewById(R.id.tv_road_data_fueltype);

		// 栓怠速结果

		mTemp = (TextView) view.findViewById(R.id.shuandaisu_reusult_temp_tv);
		press = (TextView) view.findViewById(R.id.shuandaisu_reusult_pres_tv);
		relativeTemp = (TextView) view
				.findViewById(R.id.shuandaisu_reusult_relative_temp_tv);
		
		oilTemp = (TextView) view
				.findViewById(R.id.shuandaisu_reusult_relative_oiltemp_tv);

		resultAir = (TextView) view.findViewById(R.id.shuandaisu_result);
		resultLowCo = (TextView) view
				.findViewById(R.id.shuandaisu_result_low_co);
		resultLowHc = (TextView) view
				.findViewById(R.id.shuandaisu_result_low_hc);
		resultLowNo = (TextView) view
				.findViewById(R.id.shuandaisu_result_low_no);
		resultLowCo2 = (TextView) view
				.findViewById(R.id.shuandaisu_result_low_co2);
		resultLowO2 = (TextView) view
				.findViewById(R.id.shuandaisu_result_low_o2);
		resultHighCo = (TextView) view
				.findViewById(R.id.shuandaisu_result_high_co);
		resultHighHc = (TextView) view
				.findViewById(R.id.shuandaisu_result_high_hc);
		resultHighNo = (TextView) view
				.findViewById(R.id.shuandaisu_result_high_no);
		resultHighCo2 = (TextView) view
				.findViewById(R.id.shuandaisu_result_high_co2);
		resultHighO2 = (TextView) view
				.findViewById(R.id.shuandaisu_result_high_o2);

		limit = (TextView) view.findViewById(R.id.shuandaisu_result_limit);
		limitLowCo = (TextView) view.findViewById(R.id.shuandaisu_limit_low_co);
		limitLowHc = (TextView) view.findViewById(R.id.shuandaisu_limit_low_hc);
		limitLowNo = (TextView) view.findViewById(R.id.shuandaisu_limit_low_no);
		limitLowCo2 = (TextView) view
				.findViewById(R.id.shuandaisu_limit_low_co2);
		limitLowO2 = (TextView) view.findViewById(R.id.shuandaisu_limit_low_o2);
		limitHighCo = (TextView) view
				.findViewById(R.id.shuandaisu_limit_high_co);
		limitHighHc = (TextView) view
				.findViewById(R.id.shuandaisu_limit_high_hc);
		limitHighNo = (TextView) view
				.findViewById(R.id.shuandaisu_limit_high_no);
		limitHighCo2 = (TextView) view
				.findViewById(R.id.shuandaisu_limit_high_co2);
		limitHighO2 = (TextView) view
				.findViewById(R.id.shuandaisu_limit_high_o2);
		resultQua = (TextView) view
				.findViewById(R.id.shuandaisu_distinguish_result);
		
		
		title = (TextView) view
				.findViewById(R.id.title);
		roadEnvTemp = (TextView) view
				.findViewById(R.id.road_reusult_temp_tv);
		roadEnvPress = (TextView) view
				.findViewById(R.id.road_reusult_pres_tv);
		roadRelHumi = (TextView) view
				.findViewById(R.id.road_reusult_relative_temp_tv);
		roadYandu1 = (TextView) view
				.findViewById(R.id.road_yandu1_tv);
		roadYandu2 = (TextView) view
				.findViewById(R.id.road_yandu2_tv);
		roadYandu3 = (TextView) view
				.findViewById(R.id.road_yandu3_tv);
		roadPingjun = (TextView) view
				.findViewById(R.id.road_pingjun_tv);
		roadLimit = (TextView) view
				.findViewById(R.id.road_xianzhi_tv);
		roadResult = (TextView) view
				.findViewById(R.id.road_jieguo_tv);
		
		

		// 检测位置
		checkPos = (TextView) view.findViewById(R.id.road_data_check_pos_tv);

		showCar = (ImageView) view.findViewById(R.id.bt_data_detail_showcar);
		// showStandard = (ImageButton)
		// view.findViewById(R.id.bt_data_detail_showstandard);
		showdResult = (ImageView) view
				.findViewById(R.id.bt_data_detail_showdResult);
		showPos = (ImageView) view.findViewById(R.id.bt_data_detail_showpos);
		showPic = (ImageView) view.findViewById(R.id.bt_data_detail_showpic);

		carDetailLayout = (LinearLayout) view
				.findViewById(R.id.road_data_car_detail_layout);
		// carStadardLayout = (LinearLayout) view
		// .findViewById(R.id.road_data_output_standard_layout);
		carResultLayout = (LinearLayout) view
				.findViewById(R.id.road_data_result_layout);
		carPositionLayout = (LinearLayout) view
				.findViewById(R.id.road_data_position_layout);
		carPictureLayout = (LinearLayout) view
				.findViewById(R.id.road_data_picture_layout);
		
		
		ziYouJiasuLayout = (LinearLayout) view
		.findViewById(R.id.road_ziyou_layout);
		
		
		shXianChangResult = (TextView) view.findViewById(R.id.shuandaisu_xianchang_reusult);
		zyXianChangResult = (TextView) view.findViewById(R.id.ziyou_xianchang_reusult);
		

		back.setOnClickListener(this);
		showCar.setOnClickListener(this);
		// showStandard.setOnClickListener(this);
		showdResult.setOnClickListener(this);
		showPos.setOnClickListener(this);
		showPic.setOnClickListener(this);

		// 显示图片
		String path = Environment.getExternalStorageDirectory().getPath()
				+ File.separator + "TestPhoto" + File.separator;

		HashMap<String, String> query = DB.instance(context).query(
				"select * from dt_pic where DataType = 2 AND DataID = "
						+ response.getServerId() + ";");

		if (query != null) {

			for (int i = 0; i < Integer.parseInt((query.get("records_num"))); i++) {
				// 数据库中的图片名带有日期
				String name = query.get("Name_" + i).substring(
						query.get("Name_" + i).indexOf(File.separator) + 1);
				content.add(path + name);
			}
		}

		GridView gridview = (GridView) view.findViewById(R.id.road_gridView1);
		MyGridViewAdapter adapter = new MyGridViewAdapter(context, content);
		gridview.setAdapter(adapter);
		// //////////////////////////////////////////////////////////////////

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.bt_data_detail_detail_back:
			// TODO 点击返回
			getFragmentManager()
					.beginTransaction()
					.replace(R.id.main_framelayout,
							((MainActivity) getActivity()).getmRoad()).commit();

			break;

		case R.id.bt_data_detail_showcar:
			// TODO 点击该按钮进行汽车部分的布局伸缩

			// 提取不了方法，因为要在方法里面改变carFlag的值，改变不了
			if (!carFlag) {// 当前是展开的，需要收缩
				carDetailLayout.setVisibility(View.GONE);
				carFlag = true;
			} else {// 当前收缩，点击展开
				carDetailLayout.setVisibility(View.VISIBLE);
				carFlag = false;
			}
			break;

		// case R.id.bt_data_detail_showstandard:
		// // TODO 点击该按钮进行汽车部分的布局伸缩
		//
		// // 提取不了方法，因为要在方法里面改变carFlag的值，改变不了
		// if (!carStadardFlag) {// 当前是展开的，需要收缩
		// carStadardLayout.setVisibility(View.GONE);
		// carStadardFlag = true;
		// } else {// 当前收缩，点击展开
		// carStadardLayout.setVisibility(View.VISIBLE);
		// carStadardFlag = false;
		// }
		// break;
		case R.id.bt_data_detail_showdResult:
			// TODO 点击该按钮进行汽车部分的布局伸缩

			// 提取不了方法，因为要在方法里面改变carFlag的值，改变不了
			if (!carResultFlag) {// 当前是展开的，需要收缩
				
				if(title.getText().equals(SHUANGDAISUJIANCE)){
					carResultLayout.setVisibility(View.GONE);
				} else if (title.getText().equals(ZIYOU)){
					ziYouJiasuLayout.setVisibility(View.GONE);
				}
				
				carResultFlag = true;
			} else {// 当前收缩，点击展开
				if(title.getText().equals(SHUANGDAISUJIANCE)){
					carResultLayout.setVisibility(View.VISIBLE);
				} else if (title.getText().equals(ZIYOU)){
					ziYouJiasuLayout.setVisibility(View.VISIBLE);
				}
				
				carResultFlag = false;
			}
			break;
		case R.id.bt_data_detail_showpos:
			// TODO 点击该按钮进行汽车部分的布局伸缩

			// 提取不了方法，因为要在方法里面改变carFlag的值，改变不了
			if (!carPositionFlag) {// 当前是展开的，需要收缩
				carPositionLayout.setVisibility(View.GONE);
				carPositionFlag = true;
			} else {// 当前收缩，点击展开
				carPositionLayout.setVisibility(View.VISIBLE);
				carPositionFlag = false;
			}
			break;
		case R.id.bt_data_detail_showpic:
			// TODO 点击该按钮进行汽车部分的布局伸缩

			// 提取不了方法，因为要在方法里面改变carFlag的值，改变不了
			if (!carPictureFlag) {// 当前是展开的，需要收缩
				carPictureLayout.setVisibility(View.GONE);
				carPictureFlag = true;
			} else {// 当前收缩，点击展开
				carPictureLayout.setVisibility(View.VISIBLE);
				carPictureFlag = false;
			}
			break;

		default:
			break;
		}

	}

	// private void showAndHideLayout(LinearLayout layout, Boolean flag) {
	// if (!flag){//当前是展开的，需要收缩
	// layout.setVisibility(View.GONE);
	// flag = true;
	// } else {//当前收缩，点击展开
	// layout.setVisibility(View.VISIBLE);
	// flag = false;
	// }
	// }
}
