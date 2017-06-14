package com.example.cargas.message;

public class MSG {
	// 1—冒黑烟车图片，2—路检图片，3—抽检图片，4--油气回收数据图片
	public static int BlackPic = 1;
	public static int RoadPic = 2;
	public static int RandomPic = 3;
	public static int SupervisePic = 4;

	// public static String HFS = "http://192.168.1.100:3000/Mobile/";
//	public static String HFS = "http://192.168.4.30:60000/MobilePic/";
	
	public static String HFS = "http://220.249.108.130:8788/MobilePic/";
	// 全局SessionId
	public static String SessionID = "";
	public static String RegisterUser = "";
	public static String Password = "";

	// msg name
	public static final int BASE_ID = 10000; // 消息ID的基准值

	public static final int LOGIN_SUCCESS = BASE_ID + 1;
	public static final int LOGIN_FAIL = BASE_ID + 2;

	// 首页
	public static final int HOME_SUCCESS = BASE_ID + 3;
	public static final int HOME_FAIL = BASE_ID + 4;

	// 黑烟
	public static final int BLACK_SMOKE_SHOW_SUCCESS = BASE_ID + 5;
	public static final int BLACK_SMOKE_SHOW_FAIL = BASE_ID + 6;

	// 路检查询
	public static final int Road_Inspection_Register_Query_SUCESS = BASE_ID + 7;
	public static final int Road_Inspection_Register_Query_FAIL = BASE_ID + 8;

	// 路检登记提交
	public static final int Road_Inspection_Register_Submit_SUCESS = BASE_ID + 11;
	public static final int Road_Inspection_Register_Submit_FAIL = BASE_ID + 12;

	// 抽检详情的登记查询
	public static final int RandomInspectionDetailRegister_SUCESS = BASE_ID + 9;
	public static final int RandomInspectionDetailRegister_FAIL = BASE_ID + 10;

	// 抽检详情的登记
	public static final int RandomInspectionCreate_SUCESS = BASE_ID + 13;
	public static final int RandomInspectionCreate_FAIL = BASE_ID + 14;

	// 抽检详情的登记查询
	public static final int RandomInspectionDetailRegister_Submit_SUCESS = BASE_ID + 15;
	public static final int RandomInspectionDetailRegister_Submit_FAIL = BASE_ID + 16;

	// 抽检详情的登记查询
	public static final int Supervise_Submit_SUCESS = BASE_ID + 17;
	public static final int Supervise_Submit_FAIL = BASE_ID + 18;

	// 黑烟查询
	public static final int BLACK_SMOKE_QUERY_SUCCESS = BASE_ID + 19;
	public static final int BLACK_SMOKE_QUERY_FAIL = BASE_ID + 20;

	// 路检查询
	public static final int Road_Inspection_QUERY_SUCCESS = BASE_ID + 21;
	public static final int Road_Inspection_QUERY_FAIL = BASE_ID + 22;

	// 抽检检查询
	public static final int Random_Inspection_QUERY_SUCCESS = BASE_ID + 23;
	public static final int Random_Inspection_QUERY_FAIL = BASE_ID + 24;

	// 抽检检查询
	public static final int Random_Inspection_Detail_QUERY_SUCCESS = BASE_ID + 25;
	public static final int Random_Inspection_Detail_QUERY_FAIL = BASE_ID + 26;

	// 监管检查询
	public static final int Supervise_Detail_QUERY_SUCCESS = BASE_ID + 27;
	public static final int Supervise_Detail_QUERY_FAIL = BASE_ID + 28;

	// 黑烟
	public static final int BLACK_SMOKE_DETAIL_SUCCESS = BASE_ID + 29;
	public static final int BLACK_SMOKE_DETAIL_FAIL = BASE_ID + 30;

	// 黑烟
	public static final int APP_UPDATE_SUCCESS = BASE_ID + 31;
	public static final int APP_UPDATE_FAIL = BASE_ID + 32;
}
