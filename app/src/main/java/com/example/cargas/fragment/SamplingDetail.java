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
import android.widget.TableRow;
import android.widget.TextView;

import com.example.cargas.R;
import com.example.cargas.activity.MainActivity;
import com.example.cargas.adapter.MyGridViewAdapter;
import com.example.cargas.database.DB;
import com.example.cargas.model.SamplingRegisterQueryResponse;

public class SamplingDetail extends Fragment implements OnClickListener {
	private static final String SHUANGDAISUJIANCE = "双怠速检测结果数据";
	private static final String ZIYOU = "自由加速数据";
	Context context;
	private TextView carNumber;
	private TextView carColor;
	private TextView carType;
	private TextView carProperty;
	private TextView carDate;
	private TextView carBrand;
	private TextView carStandard;
	private TextView carPeople;
	private TextView carEngine;
	private TextView back;
	private ImageView showCar;
	private LinearLayout showCarLayout;
	private LinearLayout showJiasuLayout;
	private TableRow showposLayout;
	private LinearLayout showpicLayout;
	private ImageView showJiasu;
	private ImageView showLoc;
	private ImageView showPic;
	private boolean carResultFlag = false;
	private boolean carFlag = false;
	private boolean carPositionFlag = false;
	private boolean carPictureFlag = false;
	private TextView mTemp;
	private TextView press;
	private TextView relativeTemp;
	private TextView mYandu1;
	private TextView mYandu2;
	private TextView mYandu3;
	private TextView mPingjun;
	private TextView mXianzhi;
	private TextView mJieguo;

	private SamplingRegisterQueryResponse response;

	List<String> content = new ArrayList<String>();// 图片数据
	private TextView maxMass;
	private TextView mPos;
	private TextView shuanTemp;
	private TextView shuanPress;
	private TextView shuanHumi;
	private TextView shuanOilTemp;
	private TextView shuanResultAir;
	private TextView shuanLowCo;
	private TextView shuanLowHc;
	private TextView shuanLowNo;
	private TextView shuanLowCo2;
	private TextView shuanLowO2;
	private TextView shuanHighCo;
	private TextView shuanHighHc;
	private TextView shuanHighNo;
	private TextView shuanHighCo2;
	private TextView shuanHighO2;
	private TextView shuanLimit;
	private TextView shuanLimitLowCo;
	private TextView shuanLimitLowHc;
	private TextView shuanLimitLowNo;
	private TextView shuanLimitLowCo2;
	private TextView shuanLimitLowO2;
	private TextView shuanLimitHighCo;
	private TextView shuanLimitHighHc;
	private TextView shuanLimitHighNo;
	private TextView shuanLimitHighCo2;
	private TextView shuanLimitHighO2;
	private TextView shuanDistinResult;
	private TextView title;
	private LinearLayout showShuangDaiLayout;
	private TextView shXianChangResult;
	private TextView zyXianChangResult;
	private TextView fuelType;

	public SamplingDetail(Context context) {
		this.context = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.sampling_detail, null);

		response = (SamplingRegisterQueryResponse) getArguments()
				.getSerializable("data");

		init(view);
		setData();
		return view;
	}

	private void setData() {

		if (response != null) {
			carNumber.setText(response.getCarNo());
			carColor.setText(response.getCarColor());
			carType.setText(response.getCarType());
			carProperty.setText(response.getUseNature());

			if (response.getRegistrationDate().contains("T")) {
				carDate.setText(response.getRegistrationDate().substring(0,
						response.getRegistrationDate().indexOf("T")));
			} else if (response.getRegistrationDate().contains(" ")) {
				carDate.setText(response.getRegistrationDate().substring(0, 11));
			}

			carBrand.setText(response.getBrandModel());
			carStandard.setText(response.getEmissionStandards());
			carPeople.setText(response.getPassengers());
			carEngine.setText(response.getEngineModel());

			maxMass.setText(response.getMaxTotalMass());
			mPos.setText(response.getCheckAddress());
			fuelType.setText(response.getFueltype());

			if (response.getFueltype().equals("汽油")) {
				title.setText(SHUANGDAISUJIANCE);
				showShuangDaiLayout.setVisibility(View.VISIBLE);
				showJiasuLayout.setVisibility(View.GONE);
				
				shuanTemp.setText(response.getAmbientTemperature());
				shuanPress.setText(response.getEnvironmentalPressure());
				shuanHumi.setText(response.getRelativeHumidity());
				shuanOilTemp.setText(response.getOiltemperature());
				
				HashMap<String, String> query = DB
						.instance(context)
						.query("select * from tb_two_speed_idle_detection_limits where two_speed_idle_detection_limits_id = "
								+ response.getEmissionLimits() + ";");

				if (query != null) {
					// TODO 几口返回排放限值id,然后根据这个id查询本地数据库，获取值
					shuanLimit.setText(query.get("excess_air_factor_lower_limit_0")
							+ "~" + query.get("excess_air_factor_upper_limit_0"));

					shuanLimitLowCo.setText(query.get("co_idle_speed_0"));
					shuanLimitLowHc.setText(query.get("hc_idle_speed_0"));
					shuanLimitLowNo.setText("-");
					shuanLimitLowCo2.setText("-");
					shuanLimitLowO2.setText("-");

					shuanLimitHighCo.setText(query.get("co_high_idle_speed_0"));
					shuanLimitHighHc.setText(query.get("hc_high_idle_speed_0"));
					shuanLimitHighNo.setText("-");
					shuanLimitHighCo2.setText("-");
					shuanLimitHighO2.setText("-");
				}

				// TODO result对应接口文档
				shuanResultAir.setText(response.getExcessairratio());
				shuanLowCo.setText(response.getLow_co());
				shuanLowHc.setText(response.getLow_hc());
				shuanLowNo.setText(response.getLow_no());
				shuanLowCo2.setText(response.getLow_co2());
				shuanLowO2.setText(response.getLow_o2());

				shuanHighCo.setText(response.getHigh_co());
				shuanHighHc.setText(response.getHigh_hc());
				shuanHighNo.setText(response.getHigh_no());
				shuanHighCo2.setText(response.getHigh_co2());
				shuanHighO2.setText(response.getHigh_o2());

				if (response.getCheckResult().equals("0")) {
					shuanDistinResult.setText("合格");
				} else {
					shuanDistinResult.setText("不合格");
					shuanDistinResult.setTextColor(getResources().getColor(R.color.red));
				}
				

				if (response.getUserResult().equals("0")) {
					shXianChangResult.setText("合格");
				} else if (response.getUserResult().equals("1")) {
					shXianChangResult.setText("不合格");
					shXianChangResult.setTextColor(getResources().getColor(R.color.red));
				}
				

			} else if (response.getFueltype().equals("柴油")) {
				title.setText(ZIYOU);
				showShuangDaiLayout.setVisibility(View.GONE);
				showJiasuLayout.setVisibility(View.VISIBLE);
				
				mTemp.setText(response.getAmbientTemperature());
				press.setText(response.getEnvironmentalPressure());
				relativeTemp.setText(response.getRelativeHumidity());

				mYandu1.setText(response.getSmokeOpacity1());
				mYandu2.setText(response.getSmokeOpacity2());
				mYandu3.setText(response.getSmokeOpacity3());
				mPingjun.setText(response.getSmokeOpacityAVG());

				// mXianzhi.setText(response.get);

				if (response.getCheckResult().equals("0")) {

					mJieguo.setText("合格");
					mJieguo.setTextColor(getResources().getColor(
							R.color.myblue1));
				} else if (response.getCheckResult().equals("1")) {
					mJieguo.setText("不合格");
					mJieguo.setTextColor(getResources().getColor(R.color.red));
				}
			}

			// 获取限值
			String RegistrationDate = "";
			if (response.getRegistrationDate().contains("T")) {
				// 服务器返回的日期格式2015-08-11T00:00:00,不要具体的时间
				RegistrationDate = response.getRegistrationDate().replace("T",
						" ");
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
				mXianzhi.setText(limits + "");
			}
			
			//现场结果
			if (response.getUserResult().equals("0")) {
				zyXianChangResult.setText("合格");
			} else if (response.getUserResult().equals("1")) {
				zyXianChangResult.setText("不合格");
				zyXianChangResult.setTextColor(getResources().getColor(R.color.red));
			}
		}

	}

	private void init(View view) {
		
		title = (TextView) view
				.findViewById(R.id.ziyoutitle);
		
		back = (TextView) view
				.findViewById(R.id.bt_sampling_detail_detail_back);
		showCar = (ImageView) view
				.findViewById(R.id.bt_sampling_detail_showcar);
		showJiasu = (ImageView) view
				.findViewById(R.id.bt_sampling_detail_showdResult);
		showLoc = (ImageView) view
				.findViewById(R.id.bt_sampling_detail_showpos);
		showPic = (ImageView) view
				.findViewById(R.id.bt_sampling_detail_showpic);

		carNumber = (TextView) view
				.findViewById(R.id.tv_sampling_detail_carnumber);
		carColor = (TextView) view
				.findViewById(R.id.tv_sampling_detail_carcolor);
		carType = (TextView) view.findViewById(R.id.tv_sampling_detail_cartype);
		carProperty = (TextView) view
				.findViewById(R.id.tv_sampling_detail_property);

		maxMass = (TextView) view
				.findViewById(R.id.tv_sampling_detail_maxtotalmass);

		carDate = (TextView) view
				.findViewById(R.id.tv_sampling_detail_register_date);
		carBrand = (TextView) view.findViewById(R.id.tv_sampling_detail_brand);
		carStandard = (TextView) view
				.findViewById(R.id.tv_sampling_detail_standard);
		carPeople = (TextView) view
				.findViewById(R.id.tv_sampling_detail_passenger_count);
		carEngine = (TextView) view
				.findViewById(R.id.tv_sampling_detail_enginetype);

		mTemp = (TextView) view.findViewById(R.id.sampling_reusult_temp_tv);
		press = (TextView) view.findViewById(R.id.sampling_reusult_pres_tv);
		relativeTemp = (TextView) view
				.findViewById(R.id.sampling_reusult_relative_temp_tv);

		mYandu1 = (TextView) view.findViewById(R.id.yandu1_tv);
		mYandu2 = (TextView) view.findViewById(R.id.yandu2_tv);
		mYandu3 = (TextView) view.findViewById(R.id.yandu3_tv);
		mPingjun = (TextView) view.findViewById(R.id.pingjun_tv);
		mXianzhi = (TextView) view.findViewById(R.id.xianzhi_tv);
		mJieguo = (TextView) view.findViewById(R.id.jieguo_tv);

		mPos = (TextView) view.findViewById(R.id.sampling_check_pos_tv);
		
		fuelType = (TextView) view.findViewById(R.id.tv_sampling_detail_fueltype);

		// 双怠速检测结果

		shuanTemp = (TextView) view
				.findViewById(R.id.sampling_shuandaisu_reusult_temp_tv);
		shuanPress = (TextView) view
				.findViewById(R.id.sampling_shuandaisu_reusult_pres_tv);
		shuanHumi = (TextView) view
				.findViewById(R.id.sampling_shuandaisu_reusult_relative_temp_tv);
		shuanOilTemp = (TextView) view
				.findViewById(R.id.sampling_shuandaisu_reusult_relative_oiltemp_tv);
		shuanResultAir = (TextView) view
				.findViewById(R.id.sampling_shuandaisu_result);//过空气系数
		shuanLowCo = (TextView) view
				.findViewById(R.id.sampling_shuandaisu_result_low_co);
		shuanLowHc = (TextView) view
				.findViewById(R.id.sampling_shuandaisu_result_low_hc);
		shuanLowNo = (TextView) view
				.findViewById(R.id.sampling_shuandaisu_result_low_no);
		shuanLowCo2 = (TextView) view
				.findViewById(R.id.sampling_shuandaisu_result_low_co2);
		shuanLowO2 = (TextView) view
				.findViewById(R.id.sampling_shuandaisu_result_low_o2);
		shuanHighCo = (TextView) view
				.findViewById(R.id.sampling_shuandaisu_result_high_co);
		shuanHighHc = (TextView) view
				.findViewById(R.id.sampling_shuandaisu_result_high_hc);
		shuanHighNo = (TextView) view
				.findViewById(R.id.sampling_shuandaisu_result_high_no);
		shuanHighCo2 = (TextView) view
				.findViewById(R.id.sampling_shuandaisu_result_high_co2);
		shuanHighO2 = (TextView) view
				.findViewById(R.id.sampling_shuandaisu_result_high_o2);
		shuanLimit = (TextView) view
				.findViewById(R.id.sampling_shuandaisu_result_limit);
		shuanLimitLowCo = (TextView) view
				.findViewById(R.id.sampling_shuandaisu_limit_low_co);
		shuanLimitLowHc = (TextView) view
				.findViewById(R.id.sampling_shuandaisu_limit_low_hc);
		shuanLimitLowNo = (TextView) view
				.findViewById(R.id.sampling_shuandaisu_limit_low_no);
		shuanLimitLowCo2 = (TextView) view
				.findViewById(R.id.sampling_shuandaisu_limit_low_co2);
		shuanLimitLowO2 = (TextView) view
				.findViewById(R.id.sampling_shuandaisu_limit_low_o2);
		shuanLimitHighCo = (TextView) view
				.findViewById(R.id.sampling_shuandaisu_limit_high_co);
		shuanLimitHighHc = (TextView) view
				.findViewById(R.id.sampling_shuandaisu_limit_high_hc);
		shuanLimitHighNo = (TextView) view
				.findViewById(R.id.sampling_shuandaisu_limit_high_no);
		shuanLimitHighCo2 = (TextView) view
				.findViewById(R.id.sampling_shuandaisu_limit_high_co2);
		shuanLimitHighO2 = (TextView) view
				.findViewById(R.id.sampling_shuandaisu_limit_high_o2);
		shuanDistinResult = (TextView) view
				.findViewById(R.id.sampling_shuandaisu_distinguish_result);
		
		
		shXianChangResult = (TextView) view.findViewById(R.id.sampling_shuandaisu_xianchang_reusult);
		zyXianChangResult = (TextView) view.findViewById(R.id.random_ziyou_xianchang_reusult);

		showCarLayout = (LinearLayout) view
				.findViewById(R.id.road_sampling_detail_layout);
		showJiasuLayout = (LinearLayout) view
				.findViewById(R.id.road_sampling_result_layout);
		showposLayout = (TableRow) view
				.findViewById(R.id.sampling_data_position_layout);
		showpicLayout = (LinearLayout) view
				.findViewById(R.id.sampling_data_picture_layout);
		
		
		showShuangDaiLayout = (LinearLayout) view
		.findViewById(R.id.road_shuangdaisu_layout);

		back.setOnClickListener(this);
		showCar.setOnClickListener(this);
		showJiasu.setOnClickListener(this);
		showLoc.setOnClickListener(this);
		showPic.setOnClickListener(this);

		// 显示图片
		String path = Environment.getExternalStorageDirectory().getPath()
				+ File.separator + "TestPhoto" + File.separator;

		HashMap<String, String> query = DB.instance(context).query(
				"select * from dt_pic where DataType = 3 AND DataID = "
						+ response.getServerId() + ";");

		if (query != null) {

			for (int i = 0; i < Integer.parseInt((query.get("records_num"))); i++) {
				// 数据库中的图片名带有日期
				String name = query.get("Name_" + i).substring(
						query.get("Name_" + i).indexOf(File.separator) + 1);
				content.add(path + name);
			}
		}

		GridView gridview = (GridView) view
				.findViewById(R.id.sampling_gridView1);
		MyGridViewAdapter adapter = new MyGridViewAdapter(context, content);
		gridview.setAdapter(adapter);
		// //////////////////////////////////////////////////////////////////
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.bt_sampling_detail_detail_back:
			getFragmentManager()
					.beginTransaction()
					.replace(
							R.id.main_framelayout,
							((MainActivity) getActivity()).getmRandom()
									.getRandomInspectionDetail()).commit();
			break;

		// 收缩
		case R.id.bt_sampling_detail_showcar:
			if (!carFlag) {// 当前是展开的，需要收缩
				showCarLayout.setVisibility(View.GONE);
				carFlag = true;
			} else {// 当前收缩，点击展开
				showCarLayout.setVisibility(View.VISIBLE);
				carFlag = false;
			}
			break;

		case R.id.bt_sampling_detail_showdResult:
			if (!carResultFlag) {// 当前是展开的，需要收缩
				
				if(title.getText().equals(SHUANGDAISUJIANCE)){
					showShuangDaiLayout.setVisibility(View.GONE);
				} else if (title.getText().equals(ZIYOU)){
					showJiasuLayout.setVisibility(View.GONE);
				}
				carResultFlag = true;
			} else {// 当前收缩，点击展开
				
				
				if(title.getText().equals(SHUANGDAISUJIANCE)){
					showShuangDaiLayout.setVisibility(View.VISIBLE);
				} else if (title.getText().equals(ZIYOU)){
					showJiasuLayout.setVisibility(View.VISIBLE);
				}
				carResultFlag = false;
			}

			break;

		case R.id.bt_sampling_detail_showpos:
			if (!carPositionFlag) {// 当前是展开的，需要收缩
				showposLayout.setVisibility(View.GONE);
				carPositionFlag = true;
			} else {// 当前收缩，点击展开
				showposLayout.setVisibility(View.VISIBLE);
				carPositionFlag = false;
			}
			break;

		case R.id.bt_sampling_detail_showpic:
			if (!carPictureFlag) {// 当前是展开的，需要收缩
				showpicLayout.setVisibility(View.GONE);
				carPictureFlag = true;
			} else {// 当前收缩，点击展开
				showpicLayout.setVisibility(View.VISIBLE);
				carPictureFlag = false;
			}
			break;

		default:
			break;
		}

	}

}
