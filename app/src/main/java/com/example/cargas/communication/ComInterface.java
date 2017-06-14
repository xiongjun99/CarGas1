package com.example.cargas.communication;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.cargas.activity.Login;
import com.example.cargas.communication.ComMsg.BlackSmokePicQueryReq;
import com.example.cargas.communication.ComMsg.BlackSmokeQueryReq;
import com.example.cargas.communication.ComMsg.BlackSmokeRegisterUploadReq;
import com.example.cargas.communication.ComMsg.BlackSomkePunishReq;
import com.example.cargas.communication.ComMsg.LoginInReq;
import com.example.cargas.communication.ComMsg.ObtainVehicleInformationReq;
import com.example.cargas.communication.ComMsg.OrganizationQueryReq;
import com.example.cargas.communication.ComMsg.PicUploadReq;
import com.example.cargas.communication.ComMsg.RoadCheckPicQueryReq;
import com.example.cargas.communication.ComMsg.RoadCheckQueryReq;
import com.example.cargas.communication.ComMsg.RoadCheckUploadReq;
import com.example.cargas.communication.ComMsg.SamplingCreateQueryReq;
import com.example.cargas.communication.ComMsg.SamplingCreateUploadReq;
import com.example.cargas.communication.ComMsg.SamplingPicQueryReq;
import com.example.cargas.communication.ComMsg.SamplingRegisterQueryReq;
import com.example.cargas.communication.ComMsg.SamplingRegisterRelyIDQueryReq;
import com.example.cargas.communication.ComMsg.SamplingRegisterUploadReq;
import com.example.cargas.communication.ComMsg.Update;
import com.example.cargas.communication.ComMsg.VaporRecoveryPicQueryReq;
import com.example.cargas.communication.ComMsg.VaporRecoveryQueryReq;
import com.example.cargas.communication.ComMsg.VaporRecoveryUploadReq;
import com.example.cargas.communication.ComMsg.VehicleinformationQueryReq;
import com.example.cargas.database.DB;
import com.example.cargas.fragment.BlackSmokeFragment;
import com.example.cargas.fragment.BlackSmokeRegiseter;
import com.example.cargas.fragment.HomeFragment;
import com.example.cargas.fragment.RandomInspectionDetail;
import com.example.cargas.fragment.RandomInspectionDetailRegister;
import com.example.cargas.fragment.RandomInspectionFragment;
import com.example.cargas.fragment.RoadInspectionFragment;
import com.example.cargas.fragment.RoadInspectionRegiseter;
import com.example.cargas.fragment.SettingsFragment;
import com.example.cargas.fragment.SuperviseFragment;
import com.example.cargas.fragment.SuperviseRegiseter;
import com.example.cargas.message.MSG;
import com.example.cargas.utils.Utils;
import com.example.cargas.view.CustomDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ComInterface {
	// private static final String PREFIX_URL =
	// "http://publicamms.oicp.net:10054/Exhaust/ExhaustServices.ashx/";//
	// XXXXX?param=";
	// http://publicamms.oicp.net:10054/Exhaust/ExhaustServices.ashx/LoginIn?param={"UserName":"admin","PassWord":"KJ/Sc3qO1/A3Xsh/vOwToKpWz0a5qj8gxmjloidxQ2XathGeKbHBqL3gHYrdPcOQ"}

	private static final String PREFIX_URL = "http://220.249.108.130:8986/Exhaust/ExhaustServices.ashx/";
	
	//锟斤拷锟皆接匡拷
//	private static final String PREFIX_URL = "http://publicamms.oicp.net:10054/Exhaust/ExhaustServices.ashx/";

	private static Gson gson = new GsonBuilder().setDateFormat(
			"yyyy-MM-dd HH:mm:ss").create();
	private static JsonParser parser = new JsonParser();// 锟斤拷锟斤拷json锟斤拷锟斤拷

	// 0锟斤拷示锟接碉拷陆锟斤拷锟芥发锟斤拷锟斤拷锟斤拷锟?1锟斤拷示锟斤拷锟竭程凤拷锟斤拷锟斤拷锟斤拷锟?
	public static void LoginIn(final Context ctx, LoginInReq req, final int flag) {
		String url = PREFIX_URL + "LoginIn?param=" + gson.toJson(req);// URLEncoder.encode(gson.toJson(req));

		Log.v("Baidu", "URL: " + url);

		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST,
		// "http://api.1-blog.com/biz/bizserver/article/list.do",
				url, null, new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject jsonObject) {
						Log.v("Baidu", jsonObject.toString());

						if (!TextUtils.isEmpty(jsonObject.toString())) {
							
							ComMsg.LoginInRes res = gson.fromJson(
									jsonObject.toString(),
									ComMsg.LoginInRes.class);

							if (flag == 0) {

								Login._message_handler.sendMessage(Login._message_handler
										.obtainMessage(MSG.LOGIN_SUCCESS, res));

								Log.v("Baidu", "ret " + res.Result + ", "
										+ res.Success);

								// res.Result锟斤拷sessionId,锟斤拷织锟接口革拷锟斤拷
								if (res.Success.equals("1")) {

									OrganizationQueryReq orgReq = new OrganizationQueryReq();
									orgReq.SessionID = res.Result;
									OrganizationQuery(ctx, orgReq);
								}

							} else if (flag == 1) {
								MSG.SessionID = res.Result;
							}
						}

					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {

						if (flag == 0) {

							Login._message_handler
									.sendMessage(Login._message_handler
											.obtainMessage(MSG.LOGIN_FAIL));

						} else if (flag == 1) {

						}

						Log.i("Baidu", "error " + volleyError.toString());
					}
				});

		VolleySingleton.getVolleySingleton(ctx.getApplicationContext())
				.addToRequestQueue(jr);
	}

	public static void OrganizationQuery(final Context ctx,
			OrganizationQueryReq req) {
		String url = PREFIX_URL + "OrganizationQuery?param=" + gson.toJson(req);

		Log.v("Baidu", "URL: " + url);

		StringRequest sr = new StringRequest(Request.Method.POST, url,
				new Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.i("Baidu", response);

						// 写锟斤拷织锟结构锟斤拷锟绞憋拷锟揭拷锟缴撅拷锟斤拷锟斤拷械锟斤拷锟斤拷锟斤拷锟斤拷锟?
						DB.instance(ctx)
								.execute("delete from mi_organization;");

						if (!TextUtils.isEmpty(response.toString())) {
							// 锟斤拷锟斤拷json锟斤拷锟斤拷拇锟斤拷锟?
							JsonArray array = parser.parse(response)
									.getAsJsonArray();
							for (JsonElement obj : array) {
								ComMsg.OrganizationQueryRes j = gson.fromJson(
										obj, ComMsg.OrganizationQueryRes.class);

								Log.v("Baidu", "JA: " + j.OrganizationName);

								// 锟斤拷锟斤拷锟斤拷写锟斤拷锟斤拷锟斤拷菘锟?
								StringBuffer sql = new StringBuffer();
								sql.append("insert into mi_organization (id,OrganizationName,OrganizationAddress,Linkman,Phone,Remark) ");
								sql.append("values (" + j.OrganizationID + ",'"
										+ j.OrganizationName + "','"
										+ j.OrganizationAddress + "','"
										+ j.Linkman + "','" + j.Phone + "','"
										+ j.Remark + "');");
								DB.instance(ctx).execute(sql.toString());
							}
						}
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("Baidu", error.getMessage(), error);
					}
				});

		VolleySingleton.getVolleySingleton(ctx.getApplicationContext())
				.addToRequestQueue(sr);
	}

	public static void ObtainVehicleInformation(final Context ctx,
			ObtainVehicleInformationReq req) {
		// 锟斤拷锟斤拷锟斤拷志锟斤拷锟叫碉拷url
		String url = PREFIX_URL + "ObtainVehicleInformation?param="
				+ gson.toJson(req);

		Log.v("Baidu", "URL: " + url);

		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, url,
				null, new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject jsonObject) {
						Log.v("Baidu", jsonObject.toString());
						
						if (!TextUtils.isEmpty(jsonObject.toString())) {
							ComMsg.ObtainVehicleInformationRes res = gson.fromJson(
									jsonObject.toString(),
									ComMsg.ObtainVehicleInformationRes.class);

							HomeFragment._message_handler
									.sendMessage(HomeFragment._message_handler
											.obtainMessage(MSG.HOME_SUCCESS,
													res));
						} else {
							Utils.showToast(ctx, "没锟斤拷锟斤拷锟斤拷");
						}

					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Log.i("Baidu", "error " + volleyError.toString());

						HomeFragment._message_handler
								.sendMessage(HomeFragment._message_handler
										.obtainMessage(MSG.HOME_FAIL));

					}
				});

		VolleySingleton.getVolleySingleton(ctx.getApplicationContext())
				.addToRequestQueue(jr);
	}

	public static void BlackSmokeRegisterUpload(Context ctx,
			BlackSmokeRegisterUploadReq req) {

		Log.v("Baidu", "URL: Before");
		String url = PREFIX_URL + "BlackSmokeRegisterUpload?param="
				+ URLEncoder.encode(gson.toJson(req));

		Log.v("Baidu", "URL: " + url);

		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, url,
				null, new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject jsonObject) {
						Log.v("Baidu", jsonObject.toString());

						ComMsg.BlackSmokeRegisterUploadRes res = gson.fromJson(
								jsonObject.toString(),
								ComMsg.BlackSmokeRegisterUploadRes.class);

						BlackSmokeRegiseter._message_handler
								.sendMessage(BlackSmokeRegiseter._message_handler
										.obtainMessage(
												MSG.BLACK_SMOKE_SHOW_SUCCESS,
												res));
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Log.i("Baidu", "error " + volleyError.toString());

						BlackSmokeRegiseter._message_handler
								.sendMessage(BlackSmokeRegiseter._message_handler
										.obtainMessage(MSG.BLACK_SMOKE_SHOW_FAIL));
					}
				});

		VolleySingleton.getVolleySingleton(ctx.getApplicationContext())
				.addToRequestQueue(jr);
	}

	public static void BlackSmokeQuery(final Context ctx, BlackSmokeQueryReq req) {
		String url = PREFIX_URL + "BlackSmokeQuery?param="
				+ URLEncoder.encode(gson.toJson(req));

		Log.v("Baidu", "URL: " + url);

		StringRequest sr = new StringRequest(Request.Method.POST, url,
				new Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.i("Baidu", response);

						// 锟斤拷锟斤拷锟斤拷锟斤拷菘锟?
						DB.instance(ctx).execute(
								"delete from dt_blackSmokeRegister;");

						if (!TextUtils.isEmpty(response)) {

							// 锟斤拷锟斤拷json锟斤拷锟斤拷拇锟斤拷锟?
							JsonArray array = parser.parse(response)
									.getAsJsonArray();

							for (JsonElement obj : array) {
								ComMsg.BlackSmokeQueryRes j = gson.fromJson(
										obj, ComMsg.BlackSmokeQueryRes.class);

								Log.v("Baidu", "JA: " + j.Remark);

								// 锟斤拷询锟缴癸拷之锟斤拷写锟斤拷锟捷匡拷
								// 拼锟斤拷SQL
								StringBuffer sql = new StringBuffer();
								sql.append("insert into dt_blackSmokeRegister (RegisterUser,RegisterTime,CarNo,CarColor,Address,FieldEvaluation,RegisterStatus,PunishTime,PunishUser,PunishStatus,Longitude,Latitude,ServerId) ");
								sql.append("values (");
								sql.append("'" + j.RegisterUser + "','"
										+ j.RegisterTime + "','" + j.CarNo
										+ "','" + j.CarColor + "','"
										+ j.Address + "','" + j.FieldEvaluation
										+ "','" + j.RegisterStatus + "','"
										+ j.PunishTime + "','" + j.PunishUser
										+ "','" + j.PunishStatus + "','"
										+ j.Longitude + "','" + j.Latitude
										+ "','" + j.BlackSmokeRegisterID
										+ "');");
								boolean isSuccess = DB.instance(ctx).execute(
										sql.toString());

							}

							BlackSmokeFragment._message_handler
									.sendMessage(BlackSmokeFragment._message_handler
											.obtainMessage(MSG.BLACK_SMOKE_QUERY_SUCCESS));
							Utils.showToast(ctx, "刷锟铰成癸拷");
						} else {
							Utils.showToast(ctx, "没锟斤拷锟斤拷锟斤拷");
							BlackSmokeFragment._message_handler
									.sendMessage(BlackSmokeFragment._message_handler
											.obtainMessage(MSG.BLACK_SMOKE_QUERY_SUCCESS));
						}
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("Baidu", error.getMessage(), error);
						BlackSmokeFragment._message_handler
								.sendMessage(BlackSmokeFragment._message_handler
										.obtainMessage(MSG.BLACK_SMOKE_QUERY_FAIL));
					}
				});

		VolleySingleton.getVolleySingleton(ctx.getApplicationContext())
				.addToRequestQueue(sr);
	}

	public static void BlackSomkePunish(final Context ctx,
			final BlackSomkePunishReq req) {
		String url = PREFIX_URL + "BlackSomkePunish?param="
				+ URLEncoder.encode(gson.toJson(req));

		Log.v("Baidu", "URL: " + url);

		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, url,
				null, new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject jsonObject) {
						Log.v("Baidu", jsonObject.toString());

						ComMsg.BlackSomkePunishRes res = gson.fromJson(
								jsonObject.toString(),
								ComMsg.BlackSomkePunishRes.class);

						if (res.Success.equals("1")) {
							// 锟斤拷锟斤拷锟斤拷锟捷匡拷
							DB.instance(ctx).execute(
									"UPDATE dt_blackSmokeRegister SET PunishUser = '"
											+ MSG.RegisterUser
											+ "', PunishStatus = '1' "
											+ ", PunishTime = '"
											+ Utils.getCurrentTime() + "' "
											+ "WHERE ServerId = "
											+ req.BlackSmokeRegisterID + ";");
						} else {
							Utils.showToast(ctx, res.Result);
						}

					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Log.i("Baidu", "error " + volleyError.toString());
					}
				});

		VolleySingleton.getVolleySingleton(ctx.getApplicationContext())
				.addToRequestQueue(jr);
	}

	// flag为0锟斤拷1锟街憋拷锟斤拷锟铰凤拷斐碉拷锟斤拷锟窖拷锟斤拷锟届车锟斤拷锟斤拷询
	public static void VehicleinformationQuery(final Context ctx,
			VehicleinformationQueryReq req, final int flag) {
		String url = PREFIX_URL + "VehicleinformationQuery?param="
				+ gson.toJson(req);

		Log.v("Baidu", "URL: " + url);
		

		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, url,
				null, new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject jsonObject) {
						
						Log.v("Baidu", jsonObject.toString());

						if (!TextUtils.isEmpty(jsonObject.toString())) {

							ComMsg.VehicleinformationQueryRes res = gson.fromJson(
									jsonObject.toString(),
									ComMsg.VehicleinformationQueryRes.class);

							if (flag == 0) {

								RoadInspectionRegiseter._message_handler
										.sendMessage(RoadInspectionRegiseter._message_handler
												.obtainMessage(
														MSG.Road_Inspection_Register_Query_SUCESS,
														res));
							} else if (flag == 1) {
								RandomInspectionDetailRegister._message_handler
										.sendMessage(RandomInspectionDetailRegister._message_handler
												.obtainMessage(
														MSG.RandomInspectionDetailRegister_SUCESS,
														res));
							}
						} else {
							Utils.showToast(ctx, "没锟斤拷锟斤拷锟斤拷");
						}

					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Log.i("Baidu", "error " + volleyError.toString());

						if (flag == 0) {

							RoadInspectionRegiseter._message_handler
									.sendMessage(RoadInspectionRegiseter._message_handler
											.obtainMessage(MSG.Road_Inspection_Register_Query_FAIL));
						} else if (flag == 1) {
							RandomInspectionDetailRegister._message_handler
									.sendMessage(RandomInspectionDetailRegister._message_handler
											.obtainMessage(MSG.RandomInspectionDetailRegister_FAIL));
						}

					}
				});

		VolleySingleton.getVolleySingleton(ctx.getApplicationContext())
				.addToRequestQueue(jr);
	}

	public static void RoadCheckQuery(final Context ctx, RoadCheckQueryReq req) {
		String url = PREFIX_URL + "RoadCheckQuery?param="
				+ URLEncoder.encode(gson.toJson(req));

		Log.v("Baidu", "URL: " + url);

		StringRequest sr = new StringRequest(Request.Method.POST, url,
				new Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.i("Baidu", response);

						DB.instance(ctx).execute(
								"delete from dt_roadCheckRegister;");
						if (!TextUtils.isEmpty(response)) {

							// 锟斤拷锟斤拷json锟斤拷锟斤拷拇锟斤拷锟?
							JsonArray array = parser.parse(response)
									.getAsJsonArray();

							for (JsonElement obj : array) {
								ComMsg.RoadCheckQueryRes j = gson.fromJson(obj,
										ComMsg.RoadCheckQueryRes.class);

								Log.v("Baidu", "JA: " + j.Remark);

								// 写锟斤拷锟捷匡拷
								StringBuilder sql = new StringBuilder();
								sql.append("insert into dt_roadCheckRegister (CarNo,CarColor,CarType,UseNature,RegistrationDate,BrandModel,EngineModel,Passengers,MaxTotalMass,EmissionStandards,EmissionLimits,AmbientTemperature,EnvironmentalPressure,RelativeHumidity,OilTemperature,");
								sql.append("ExcessAirRatio,Low_co,Low_hc,Low_no,Low_o2,Low_co2,High_co,High_hc,High_no,High_o2,High_co2,CheckResult,CheckAddress,Longitude,Latitude,RegisterUser,RegisterTime,ServerId) ");
								sql.append("values ('" + j.CarNo + "','"
										+ j.CarColor + "','" + j.CarType
										+ "','" + j.UseNature + "','"
										+ j.RegistrationDate + "','"
										+ j.BrandModel + "','" + j.EngineModel
										+ "'," + j.Passengers + ","
										+ j.MaxTotalMass + ",'"
										+ j.EmissionStandards + "',"
										+ j.EmissionLimits + ",'"
										+ j.AmbientTemperature + "','"
										+ j.EnvironmentalPressure + "','"
										+ j.RelativeHumidity + "','"
										+ j.OilTemperature + "','"
										+ j.ExcessAirRatio + "','" + j.Low_co
										+ "','" + j.Low_co2 + "','" + j.Low_hc
										+ "','" + j.Low_no + "','" + j.Low_o2
										+ "','" + j.High_co + "','" + j.High_hc
										+ "','" + j.High_no + "','" + j.High_o2
										+ "','" + j.High_co2 + "','"
										+ j.CheckResult + "','"
										+ j.CheckAddress + "','" + j.Longitude
										+ "','" + j.Latitude + "','"
										+ j.RegisterUser + "','"
										+ j.RegisterTime + "','"
										+ j.RoadCheckRegisterID + "');");

								boolean isSuccess = DB.instance(ctx).execute(
										sql.toString());

							}
							Utils.showToast(ctx, "刷锟铰成癸拷");

						} else {
							Utils.showToast(ctx, "没锟斤拷锟斤拷锟斤拷");
						}

						RoadInspectionFragment._message_handler
								.sendMessage(RoadInspectionFragment._message_handler
										.obtainMessage(MSG.Road_Inspection_QUERY_SUCCESS));
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("Baidu", error.getMessage(), error);
						RoadInspectionFragment._message_handler
								.sendMessage(RoadInspectionFragment._message_handler
										.obtainMessage(MSG.Road_Inspection_QUERY_FAIL));
					}
				});

		VolleySingleton.getVolleySingleton(ctx.getApplicationContext())
				.addToRequestQueue(sr);
	}

	public static void RoadCheckUpload(Context ctx, RoadCheckUploadReq req) {
		String url = PREFIX_URL + "RoadCheckUpload?param="
				+ URLEncoder.encode(gson.toJson(req));
		String url2 = PREFIX_URL + "RoadCheckUpload?param="
				+ gson.toJson(req);

		Log.v("Baidu", "URL: " + url);

		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, url,
				null, new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject jsonObject) {
						Log.v("Baidu", jsonObject.toString());

						ComMsg.RoadCheckUploadRes res = gson.fromJson(
								jsonObject.toString(),
								ComMsg.RoadCheckUploadRes.class);

						RoadInspectionRegiseter._message_handler
								.sendMessage(RoadInspectionRegiseter._message_handler
										.obtainMessage(
												MSG.Road_Inspection_Register_Submit_SUCESS,
												res));
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Log.i("Baidu", "error " + volleyError.toString());
						RoadInspectionRegiseter._message_handler
								.sendMessage(RoadInspectionRegiseter._message_handler
										.obtainMessage(MSG.Road_Inspection_Register_Submit_FAIL));
					}
				});

		VolleySingleton.getVolleySingleton(ctx.getApplicationContext())
				.addToRequestQueue(jr);
	}

	public static void SamplingCreateUpload(Context ctx,
			SamplingCreateUploadReq req) {
		String url = PREFIX_URL + "SamplingCreateUpload?param="
				+ URLEncoder.encode(gson.toJson(req));

		Log.v("Baidu", "URL: " + url);

		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, url,
				null, new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject jsonObject) {
						Log.v("Baidu", jsonObject.toString());

						ComMsg.SamplingCreateUploadRes res = gson.fromJson(
								jsonObject.toString(),
								ComMsg.SamplingCreateUploadRes.class);

						CustomDialog._message_handler.sendMessage(CustomDialog._message_handler
								.obtainMessage(
										MSG.RandomInspectionCreate_SUCESS, res));
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Log.i("Baidu", "error " + volleyError.toString());

						CustomDialog._message_handler.sendMessage(CustomDialog._message_handler
								.obtainMessage(MSG.RandomInspectionCreate_FAIL));
					}
				});

		VolleySingleton.getVolleySingleton(ctx.getApplicationContext())
				.addToRequestQueue(jr);
	}

	public static void SamplingCreateQuery(final Context ctx,
			SamplingCreateQueryReq req) {
		String url = PREFIX_URL + "SamplingCreateQuery?param="
				+ URLEncoder.encode(gson.toJson(req));

		Log.v("Baidu", "URL: " + url);

		StringRequest sr = new StringRequest(Request.Method.POST, url,
				new Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.i("Baidu", response);

						DB.instance(ctx).execute(
								"delete from dt_samplingCreate;");
						if (!TextUtils.isEmpty(response)) {
							// 锟斤拷锟斤拷json锟斤拷锟斤拷拇锟斤拷锟?
							JsonArray array = parser.parse(response)
									.getAsJsonArray();

							for (JsonElement obj : array) {
								ComMsg.SamplingCreateQueryRes j = gson
										.fromJson(
												obj,
												ComMsg.SamplingCreateQueryRes.class);

								Log.v("Baidu", "JA: " + j.Remark);
								StringBuffer sql = new StringBuffer();
								sql.append("insert into dt_samplingCreate (SamplingName,SamplingAddress,ChargePerson,SamplingPerson,StartTime,EndTime,RegisterUser,Longitude,Latitude,ServerId) ");
								sql.append("values ('" + j.SamplingName + "','"
										+ j.SamplingAddress + "','"
										+ j.ChargePerson + "','"
										+ j.SamplingPerson + "','"
										+ j.StartTime + "','" + j.EndTime
										+ "','" + j.RegisterUser + "','"
										+ j.Longitude + "','" + j.Latitude
										+ "','" + j.SamplingCreateID + "');");
								boolean isSuccess = DB.instance(ctx).execute(
										sql.toString());

							}

							Utils.showToast(ctx, "刷锟铰成癸拷");
						} else {
							Utils.showToast(ctx, "没锟斤拷锟斤拷锟斤拷");
						}

						RandomInspectionFragment._message_handler
								.sendMessage(RandomInspectionFragment._message_handler
										.obtainMessage(MSG.Random_Inspection_QUERY_SUCCESS));

					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("Baidu", error.getMessage(), error);
						RandomInspectionFragment._message_handler
								.sendMessage(RandomInspectionFragment._message_handler
										.obtainMessage(MSG.Random_Inspection_QUERY_FAIL));
					}
				});

		VolleySingleton.getVolleySingleton(ctx.getApplicationContext())
				.addToRequestQueue(sr);
	}

	public static void SamplingRegisterUpload(Context ctx,
			SamplingRegisterUploadReq req) {
		String url = PREFIX_URL + "SamplingRegisterUpload?param="
				+ URLEncoder.encode((gson.toJson(req)));
		
		String url2 = PREFIX_URL + "SamplingRegisterUpload?param=" +gson.toJson(req);

		Log.v("Baidu", "URL: " + url);

		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, url,
				null, new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject jsonObject) {
						Log.v("Baidu", jsonObject.toString());

						ComMsg.SamplingRegisterUploadRes res = gson.fromJson(
								jsonObject.toString(),
								ComMsg.SamplingRegisterUploadRes.class);
						RandomInspectionDetailRegister._message_handler
								.sendMessage(RandomInspectionDetailRegister._message_handler
										.obtainMessage(
												MSG.RandomInspectionDetailRegister_Submit_SUCESS,
												res));
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Log.i("Baidu", "error " + volleyError.toString());

						RandomInspectionDetailRegister._message_handler
								.sendMessage(RandomInspectionDetailRegister._message_handler
										.obtainMessage(MSG.RandomInspectionDetailRegister_Submit_FAIL));
					}
				});

		VolleySingleton.getVolleySingleton(ctx.getApplicationContext())
				.addToRequestQueue(jr);
	}

	public static void SamplingRegisterQuery(final Context ctx,
			SamplingRegisterQueryReq req) {
		String url = PREFIX_URL + "SamplingRegisterQuery?param="
				+ URLEncoder.encode(gson.toJson(req));

		Log.v("Baidu", "URL: " + url);

		StringRequest sr = new StringRequest(Request.Method.POST, url,
				new Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.i("Baidu", response);

						DB.instance(ctx).execute(
								"delete from dt_samplingRegister;");
						if (!TextUtils.isEmpty(response)) {
							// 锟斤拷锟斤拷json锟斤拷锟斤拷拇锟斤拷锟?
							JsonArray array = parser.parse(response)
									.getAsJsonArray();

							for (JsonElement obj : array) {
								ComMsg.SamplingRegisterQueryRes j = gson
										.fromJson(
												obj,
												ComMsg.SamplingRegisterQueryRes.class);

								Log.v("Baidu", "JA: " + j.Remark);
								// 写锟斤拷锟捷匡拷
								StringBuilder sql = new StringBuilder();
								sql.append("insert into dt_samplingRegister (SamplingCreateID,CarNo,CarColor,CarType,UseNature,RegistrationDate,BrandModel,EngineModel,Passengers,MaxTotalMass,EmissionStandards,EmissionLimits,AmbientTemperature,EnvironmentalPressure,RelativeHumidity,");
								sql.append("SmokeOpacity1,SmokeOpacity2,SmokeOpacity3,SmokeOpacityAVG,CheckResult,Longitude,Latitude,RegisterUser,CheckTime,CheckAddress) ");
								sql.append("values (" + j.SamplingCreateID
										+ ",'" + j.CarNo + "','" + j.CarColor
										+ "','" + j.CarType + "','"
										+ j.UseNature + "','"
										+ j.RegistrationDate + "','"
										+ j.BrandModel + "','" + j.EngineModel
										+ "'," + j.Passengers + ","
										+ j.MaxTotalMass + ",'"
										+ j.EmissionStandards + "','"
										+ j.EmissionLimits + "','"
										+ j.AmbientTemperature + "','"
										+ j.EnvironmentalPressure + "','"
										+ j.RelativeHumidity + "','"
										+ j.SmokeOpacity1 + "','"
										+ j.SmokeOpacity2 + "','"
										+ j.SmokeOpacity3 + "','"
										+ j.SmokeOpacityAVG + "','"
										+ j.CheckResult + "','" + j.Longitude
										+ "','" + j.Latitude + "','"
										+ j.RegisterUser + "','" + j.CheckTime
										+ "','" + j.CheckAddress + "');");

								boolean isSuccess = DB.instance(ctx).execute(
										sql.toString());

							}

							Utils.showToast(ctx, "刷锟铰成癸拷");
						} else {
							Utils.showToast(ctx, "没锟斤拷锟斤拷锟斤拷");
						}

						RandomInspectionDetail._message_handler
								.sendMessage(RandomInspectionDetail._message_handler
										.obtainMessage(MSG.Random_Inspection_Detail_QUERY_SUCCESS));
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("Baidu", error.getMessage(), error);
						RandomInspectionDetail._message_handler
								.sendMessage(RandomInspectionDetail._message_handler
										.obtainMessage(MSG.Random_Inspection_Detail_QUERY_FAIL));
					}
				});

		VolleySingleton.getVolleySingleton(ctx.getApplicationContext())
				.addToRequestQueue(sr);
	}

	public static void SamplingRegisterRelyIDQuery(Context ctx,
			SamplingRegisterRelyIDQueryReq req) {
		String url = PREFIX_URL + "SamplingRegisterRelyIDQuery?param="
				+ URLEncoder.encode(gson.toJson(req));

		Log.v("Baidu", "URL: " + url);

		StringRequest sr = new StringRequest(Request.Method.POST, url,
				new Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.i("Baidu", response);

						// 锟斤拷锟斤拷json锟斤拷锟斤拷拇锟斤拷锟?
						JsonArray array = parser.parse(response)
								.getAsJsonArray();
						for (JsonElement obj : array) {
							ComMsg.SamplingRegisterRelyIDQueryRes j = gson
									.fromJson(
											obj,
											ComMsg.SamplingRegisterRelyIDQueryRes.class);

							Log.v("Baidu", "JA: " + j.Remark);
						}
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("Baidu", error.getMessage(), error);
					}
				});

		VolleySingleton.getVolleySingleton(ctx.getApplicationContext())
				.addToRequestQueue(sr);
	}

	public static void VaporRecoveryUpload(Context ctx,
			VaporRecoveryUploadReq req) {
		String url = PREFIX_URL + "VaporRecoveryUpload?param="
				+ URLEncoder.encode(gson.toJson(req));

		Log.v("Baidu", "URL: " + url);

		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, url,
				null, new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject jsonObject) {
						Log.v("Baidu", jsonObject.toString());

						ComMsg.VaporRecoveryUploadRes res = gson.fromJson(
								jsonObject.toString(),
								ComMsg.VaporRecoveryUploadRes.class);
						SuperviseRegiseter._message_handler
								.sendMessage(SuperviseRegiseter._message_handler
										.obtainMessage(
												MSG.Supervise_Submit_SUCESS,
												res));
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Log.i("Baidu", "error " + volleyError.toString());
						SuperviseRegiseter._message_handler
								.sendMessage(SuperviseRegiseter._message_handler
										.obtainMessage(MSG.Supervise_Submit_FAIL));
					}
				});

		VolleySingleton.getVolleySingleton(ctx.getApplicationContext())
				.addToRequestQueue(jr);
	}

	public static void VaporRecoveryQuery(final Context ctx,
			VaporRecoveryQueryReq req) {
		String url = PREFIX_URL + "VaporRecoveryQuery?param="
				+ URLEncoder.encode(gson.toJson(req));

		Log.v("Baidu", "URL: " + url);

		StringRequest sr = new StringRequest(Request.Method.POST, url,
				new Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.i("Baidu", response);
						DB.instance(ctx).execute(
								"delete from dt_vaporRecovery;");
						if (!TextUtils.isEmpty(response)) {
							// 锟斤拷锟斤拷json锟斤拷锟斤拷拇锟斤拷锟?
							JsonArray array = parser.parse(response)
									.getAsJsonArray();

							for (JsonElement obj : array) {
								ComMsg.VaporRecoveryQueryRes j = gson
										.fromJson(
												obj,
												ComMsg.VaporRecoveryQueryRes.class);

								Log.v("Baidu", "JA: " + j.Remark);

								// 拼锟斤拷SQL
								StringBuffer sql = new StringBuffer();
								sql.append("insert into dt_vaporRecovery (ExecutionTime,Address,OrganizationID,CheckResult,Opinion,EndTime,RegisterUser,Longitude,Latitude,ServerId) ");
								sql.append("values (");
								sql.append("'" + j.ExecutionTime + "','"
										+ j.Address + "'," + j.OrganizationID
										+ "," + j.Checkresult + ",'"
										+ j.Opinion + "','" + "','"
										+ j.RegisterUser + "','" + j.Longitude
										+ "','" + j.Latitude + "','"
										+ j.VaporRecoveryID + "');");
								boolean isSuccess = DB.instance(ctx).execute(
										sql.toString());

							}

							Utils.showToast(ctx, "刷锟铰成癸拷");

						} else {
							Utils.showToast(ctx, "没锟斤拷锟斤拷锟斤拷");
						}

						SuperviseFragment._message_handler.sendMessage(SuperviseFragment._message_handler
								.obtainMessage(MSG.Supervise_Detail_QUERY_SUCCESS));

					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("Baidu", error.getMessage(), error);

						SuperviseFragment._message_handler.sendMessage(SuperviseFragment._message_handler
								.obtainMessage(MSG.Supervise_Detail_QUERY_FAIL));
					}
				});

		VolleySingleton.getVolleySingleton(ctx.getApplicationContext())
				.addToRequestQueue(sr);
	}

	public static void PicUpload(final Context ctx, final PicUploadReq req,
			final int id) {
		String url = PREFIX_URL + "PicUpload?param="
				+ URLEncoder.encode(gson.toJson(req));

		Log.v("Baidu", "URL: " + url);

		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, url,
				null, new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject jsonObject) {
						Log.v("Baidu", jsonObject.toString());

						ComMsg.PicUploadRes res = gson.fromJson(
								jsonObject.toString(),
								ComMsg.PicUploadRes.class);

						DB.instance(ctx).execute(
								"update dt_pic set status='2' where id = '"
										+ id + "';");
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Log.i("Baidu", "error " + volleyError.toString());
					}
				});

		VolleySingleton.getVolleySingleton(ctx.getApplicationContext())
				.addToRequestQueue(jr);
	}

	public static void BlackSmokePicQuery(Context ctx, BlackSmokePicQueryReq req) {
		String url = PREFIX_URL + "BlackSmokePicQuery?param="
				+ URLEncoder.encode(gson.toJson(req));

		Log.v("Baidu", "URL: " + url);

		StringRequest sr = new StringRequest(Request.Method.POST, url,
				new Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.i("Baidu", response);

						// 锟斤拷锟斤拷json锟斤拷锟斤拷拇锟斤拷锟?
						JsonArray array = parser.parse(response)
								.getAsJsonArray();
						for (JsonElement obj : array) {
							ComMsg.BlackSmokePicQueryRes j = gson.fromJson(obj,
									ComMsg.BlackSmokePicQueryRes.class);

							Log.v("Baidu", "JA: " + j.PicPath);
						}
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("Baidu", error.getMessage(), error);
					}
				});

		VolleySingleton.getVolleySingleton(ctx.getApplicationContext())
				.addToRequestQueue(sr);
	}

	public static void RoadCheckPicQuery(Context ctx, RoadCheckPicQueryReq req) {
		String url = PREFIX_URL + "RoadCheckPicQuery?param="
				+ URLEncoder.encode(gson.toJson(req));

		Log.v("Baidu", "URL: " + url);

		StringRequest sr = new StringRequest(Request.Method.POST, url,
				new Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.i("Baidu", response);
						if (!TextUtils.isEmpty(response)) {
							// 锟斤拷锟斤拷json锟斤拷锟斤拷拇锟斤拷锟?
							JsonArray array = parser.parse(response)
									.getAsJsonArray();
							for (JsonElement obj : array) {
								ComMsg.RoadCheckPicQueryRes j = gson.fromJson(
										obj, ComMsg.RoadCheckPicQueryRes.class);

								Log.v("Baidu", "JA: " + j.PicPath);
							}
						}
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("Baidu", error.getMessage(), error);
					}
				});

		VolleySingleton.getVolleySingleton(ctx.getApplicationContext())
				.addToRequestQueue(sr);

	}

	public static void SamplingPicQuery(Context ctx, SamplingPicQueryReq req) {
		String url = PREFIX_URL + "SamplingPicQuery?param="
				+ URLEncoder.encode(gson.toJson(req));

		Log.v("Baidu", "URL: " + url);

		StringRequest sr = new StringRequest(Request.Method.POST, url,
				new Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.i("Baidu", response);

						// 锟斤拷锟斤拷json锟斤拷锟斤拷拇锟斤拷锟?
						JsonArray array = parser.parse(response)
								.getAsJsonArray();
						for (JsonElement obj : array) {
							ComMsg.SamplingPicQueryRes j = gson.fromJson(obj,
									ComMsg.SamplingPicQueryRes.class);

							Log.v("Baidu", "JA: " + j.PicPath);
						}
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("Baidu", error.getMessage(), error);
					}
				});

		VolleySingleton.getVolleySingleton(ctx.getApplicationContext())
				.addToRequestQueue(sr);
	}

	public static void VaporRecoveryPicQuery(Context ctx,
			VaporRecoveryPicQueryReq req) {
		String url = PREFIX_URL + "VaporRecoveryPicQuery?param="
				+ URLEncoder.encode(gson.toJson(req));

		Log.v("Baidu", "URL: " + url);

		StringRequest sr = new StringRequest(Request.Method.POST, url,
				new Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.i("Baidu", response);

						// 锟斤拷锟斤拷json锟斤拷锟斤拷拇锟斤拷锟?
						JsonArray array = parser.parse(response)
								.getAsJsonArray();
						for (JsonElement obj : array) {
							ComMsg.VaporRecoveryPicQueryRes j = gson.fromJson(
									obj, ComMsg.VaporRecoveryPicQueryRes.class);

							Log.v("Baidu", "JA: " + j.PicPath);
						}
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("Baidu", error.getMessage(), error);
					}
				});

		VolleySingleton.getVolleySingleton(ctx.getApplicationContext())
				.addToRequestQueue(sr);
	}

	public static void APPversionQuery(final Context ctx) {
		String url = PREFIX_URL + "APPversionQuery";

		Log.v("Baidu", "URL: " + url);

		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, url,
				null, new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject jsonObject) {
						Log.v("Baidu", jsonObject.toString());

						Update res = gson.fromJson(
								jsonObject.toString(), Update.class);

						SettingsFragment._message_handler
								.sendMessage(SettingsFragment._message_handler
										.obtainMessage(MSG.APP_UPDATE_SUCCESS,
												res));

					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Log.i("Baidu", "error " + volleyError.toString());
						SettingsFragment._message_handler
								.sendMessage(SettingsFragment._message_handler
										.obtainMessage(MSG.APP_UPDATE_FAIL));
					}
				});

		VolleySingleton.getVolleySingleton(ctx.getApplicationContext())
				.addToRequestQueue(jr);
	}

	public static void array_test() {
		/*
		 * String json =
		 * "[{\"s\":\"aaa\",\"i\":1, \"f\":1.1},{\"s\":\"bbb\",\"i\":2, \"f\":2.2},{\"s\":\"ccc\", \"i\":3, \"f\":3.3}]"
		 * ;
		 * 
		 * JsonArray Jarray = parser.parse(json).getAsJsonArray();
		 * ArrayList<ComMsg.jtest> ja = new ArrayList<ComMsg.jtest>();
		 * 
		 * for(JsonElement obj : Jarray) { ComMsg.jtest t = gson.fromJson(obj
		 * ,ComMsg.jtest.class);
		 * 
		 * Log.v("Baidu", "JA: " + t.s + ", " + t.i + ", " + t.f);
		 * 
		 * ja.add(t); }
		 */

		String json = "[{\"OrganizationID\":2,\"OrganizationName\":\"锟斤拷锟皆伙拷锟斤拷\",\"OrganizationAddress\":\"锟斤拷锟皆碉拷址\",\"Linkman\":\"锟斤拷锟斤拷锟斤拷员\",\"Phone\":\"13271417458\",\"ManagePerson\":\"admin\",\"Modifytime\":\"2016-08-20T22:55:54\",\"Remark\":\"\"},{\"OrganizationID\":3,\"OrganizationName\":\"ccs1\",\"OrganizationAddress\":\"33\",\"Linkman\":\"css\",\"Phone\":\"12345566777\",\"ManagePerson\":\"\",\"Modifytime\":\"\",\"Remark\":\"\"},{\"OrganizationID\":4,\"OrganizationName\":\"aa\",\"OrganizationAddress\":\"12233333\",\"Linkman\":\"sss\",\"Phone\":\"12234566788\",\"ManagePerson\":\"\",\"Modifytime\":\"\",\"Remark\":\"\"}]";

		JsonArray Jarray = parser.parse(json).getAsJsonArray();
		ArrayList<ComMsg.OrganizationQueryRes> ja = new ArrayList<ComMsg.OrganizationQueryRes>();

		for (JsonElement obj : Jarray) {
			ComMsg.OrganizationQueryRes t = gson.fromJson(obj,
					ComMsg.OrganizationQueryRes.class);

			Log.v("Baidu", "JA: " + t.OrganizationName + ", "
					+ t.OrganizationID + ", " + t.OrganizationAddress);

			ja.add(t);
		}
	}

}

// 锟斤拷锟酵凤拷锟?
class JsonObjectRequest extends com.android.volley.toolbox.JsonObjectRequest {

	public JsonObjectRequest(int method, String url, JSONObject jsonRequest,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		super(method, url, jsonRequest, listener, errorListener);
		// 锟斤拷锟矫凤拷锟斤拷锟斤拷锟斤拷时时锟斤拷
		setRetryPolicy(new DefaultRetryPolicy(5000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		HashMap<String, String> headers = new HashMap<String, String>();
		// 锟斤拷锟斤拷http header
		// headers.put("Content-Type", "application/json; charset=utf-8");
		// headers.put("Content-Type", "application/json");
		return headers;
	}

}
