package com.example.cargas.database;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/*
 数据库定义
 名称: ExhaustWH.db

 1)冒黑烟车
 表名：dt_blackSmokeRegister
 字段名		类型					说明
 id			integer primary key	记录id，自增量
 RegisterUser	text	数据上传用户
 RegisterTime	text	数据上传时间
 CarNo			text	车牌号码
 CarColor		text	车牌颜色
 Address			text	地址
 FieldEvaluation	text	现场评价
 RegisterStatus	text	0-无需处罚，1-需要处罚
 PunishTime		text	处罚时间，如果无需处罚或者未处罚，则为null
 PunishUser		text	处罚人，无需处罚或者未处罚则为null
 PunishStatus	text	0-未处罚,1-已处罚
 Longitude		text	经度
 Latitude		text	维度
 Remark			text	备注
 ServerId		text	服务器中数据id

 2)路检表
 表名：dt_roadCheckRegister
 id						integer primary key	记录id，自增量
 CarNo					text	车牌号码
 CarColor				text	车牌颜色
 CarType					text	车辆类型
 UseNature				text	使用性质
 RegistrationDate		text	注册日期
 BrandModel				text	品牌型号
 EngineModel				text	发动机型号
 Passengers				int		载客人数
 MaxTotalMass			int		最大总质量
 EmissionStandards		text	排放标准
 EmissionLimits			int		排放限值ID
 AmbientTemperature		text	环境温度
 EnvironmentalPressure	text	环境气压
 RelativeHumidity		text	相对湿度
 OilTemperature			text	油温
 ExcessAirRatio			text	过量空气系数
 Low_co					text	低怠速CO值
 Low_hc					text	低怠速HC值
 Low_no					text	低怠速NO值
 Low_o2					text	低怠速02值
 Low_co2					text	低怠速CO2值
 High_co					text	高怠速CO值
 High_hc					text	高怠速HC值
 High_no					text	高怠速NO值
 High_o2					text	高怠速O2值
 High_co2				text	高怠速CO2值
 CheckResult				int		检测结果0-合格，1-不合格
 CheckAddress			text	检测位置 
 Longitude				text	检测位置 经度
 Latitude				text	检测位置 纬度
 RegisterUser			text	数据上传用户
 RegisterTime			text	数据上传时间
 Remark					text	备注
 ServerId				text	服务器中数据id
 Smokeopacity1			text	烟度1
 Smokeopacity2			text	烟度2
 Smokeopacity3			text	烟度3
 Smokeopacityavg			text	平均烟度值
 PenaltyReceivable		text	应收罚款
 PenaltyCollected		text	罚款入库
 UserResult				text	用户选择结果
 fueltype				text	燃油类型

 3)抽检记录表
 表名：dt_samplingCreate
 id                    integer primary key	记录id，自增量
 SamplingName          text                  抽检名称         
 SamplingAddress       text                  抽检位置
 ChargePerson          text                  负责人
 SamplingPerson        text                  参与人员
 StartTime             text                  抽检开始时间
 EndTime               text                  抽检结束时间
 RegisterUser          text                  数据上传用户
 Longitude             text                  经度
 Latitude              text                  维度
 ServerId				text	服务器中数据id

 4)抽检车辆列表
 表名：dt_samplingRegister
 id                    integer primary key	记录id，自增量
 SamplingCreateID      int   抽检车辆记录关联的抽检记录ID
 CarNo                 text	车牌号码
 CarColor              text	车牌颜色
 CarType               text	车辆类型
 UseNature             text	使用性质
 RegistrationDate      text	注册日期
 BrandModel            text	品牌型号
 EngineModel           text	发动机型号           
 Passengers            int	载客人数            
 MaxTotalMass          int	最大总质量         
 EmissionStandards     text	排放标准
 EmissionLimits        text	排放限值ID
 AmbientTemperature    text	环境温度
 EnvironmentalPressure text	环境气压
 RelativeHumidity      text	相对湿度       
 SmokeOpacity1         text  烟度1
 SmokeOpacity2         text  烟度2
 SmokeOpacity3         text  烟度3
 SmokeOpacityAVG       text  平均烟度值
 CheckResult           int	检测结果0-合格，1-不合格
 Longitude             text	检测位置 经度
 Latitude              text	检测位置 纬度
 RegisterUser          text	数据上传用户
 CheckTime             text  抽检时间
 CheckAddress          text	检测位置
 Remark                text	备注
 ServerId			  text	

 5)监管列表
 表名：dt_vaporRecovery
 id                    integer primary key	记录id，自增量
 ExecutionTime         text    监管记录时间
 Address               text    地点                  
 OrganizationID        int     组织机构ID，关联组织机构表
 CheckResult           int	  检测结果0-合格，1-不合格        
 Opinion               text    意见
 RegisterUser          text    数据上传用户
 Longitude             text	  检测位置 经度          
 Latitude              text	  检测位置 纬度
 Remark                text	备注   
 ServerId			  text	服务器中数据id             


 6)组织机构表
 表名：mi_organization
 id                    integer primary key	记录id，自增量
 OrganizationName      text   组织机构名称
 OrganizationAddress   text   组织机构地址
 Linkman               text   联系人
 Phone                 text   联系电话
 Remark                text   备注

 7)双怠速限值表
 表名：tb_two_speed_idle_detection_limits
 id                    integer primary key	记录id，自增量
 two_speed_idle_detection_limits_id    int  双怠速限值ID
 vehicle_exhaust_detection_type_id     int   车辆尾气检测类型
 limit_start_time   text   开始时间
 limit_end_time     text   结束时间
 co_idle_speed      text   co低怠速限值
 hc_idle_speed      text   hc低怠速限值
 co_high_idle_speed text   co高怠速限值
 hc_high_idle_speed text   hc高怠速限值
 excess_air_factor_lower_limit  text   低空气过量系数限值
 excess_air_factor_upper_limit  text   高空气过量系数限值

 8)自由加速限值表
 表名：tb_OPAQUE_FAS_detection_limits
 id                    integer primary key	记录id，自增量
 OPAQUE_FAS_detection_limits_id int  自由加速限值ID
 admission_method_id   int        过量空气系数
 limit_start_time      text   开始时间
 limit_end_time        text   结束时间
 smoke_limit           text   烟度值

 9)图片表
 表名：dt_pic
 id			integer primary key	记录id，自增量
 DataID			int				对应的数据ID 服务器id
 DataType		int				1—冒黑烟车图片，2—路检图片，3—抽检图片，4--油气回收数据图片
 LocalPath		text			图片本地路径
 RemotePath		text			图片远程路径
 Name			text			文件名
 Longitude		text			图片经度
 Latitude		text			图片纬度
 Status			int				状态，0- 本地未上传，1-已上传文件服务器未代用http接口，2-已上传文件服务器已调用http接口，3-下载的文件
 Remark			text			备注

 */

public class DB {
	public static final String DATABASE_NAME = "ExhaustWH.db";

	// public static final String ALARM_TABLE_NAME = "tb_alarm";
	public static final String CREATE_BlackSmokeRegister_TABLE_SQL = "create table dt_blackSmokeRegister (id integer primary key autoincrement, "
			+ "RegisterUser text, "
			+ "RegisterTime text, "
			+ "CarNo text, "
			+ "CarColor text, "
			+ "Address text, "
			+ "FieldEvaluation text, "
			+ "RegisterStatus text, "
			+ "PunishTime text, "
			+ "PunishUser text, "
			+ "PunishStatus text, "
			+ "Longitude text,Latitude text,ServerId text);";

	public static final String CREATE_RoadCheckRegister_TABLE_SQL = "create table dt_roadCheckRegister (id integer primary key autoincrement, "
			+ "CarNo text, "
			+ "CarColor text, "
			+ "CarType text, "
			+ "UseNature text, "
			+ "RegistrationDate text, "
			+ "BrandModel text, "
			+ "EngineModel text, "
			+ "Passengers int, "
			+ "MaxTotalMass int, "
			+ "EmissionStandards text, "
			+ "EmissionLimits text, "
			+ "AmbientTemperature text, "
			+ "EnvironmentalPressure text, "
			+ "RelativeHumidity text, "
			+ "OilTemperature text, "
			+ "ExcessAirRatio text, "
			+ "Low_co text, "
			+ "Low_hc text, "
			+ "Low_no text, "
			+ "Low_o2 text, "
			+ "Low_co2 text, "
			+ "High_co text, "
			+ "High_hc text, "
			+ "High_no text, "
			+ "High_o2 text, "
			+ "High_co2 text, "
			+ "CheckResult text, "
			+ "CheckAddress text, "
			+ "Longitude text, "
			+ "Latitude text, "
			+ "RegisterUser text, "
			+ "RegisterTime text,ServerId text,Smokeopacity1 text,Smokeopacity2 text,Smokeopacity3 text,Smokeopacityavg text,PenaltyReceivable text,PenaltyCollected text,UserResult text,fueltype text);";

	public static final String CREATE_SamplingCreater_TABLE_SQL = "create table dt_samplingCreate (id integer primary key autoincrement, "
			+ "SamplingName text, "
			+ "SamplingAddress text, "
			+ "ChargePerson text, "
			+ "SamplingPerson text, "
			+ "StartTime text, "
			+ "EndTime text,"
			+ "RegisterUser text, "
			+ "Longitude text, " + "Latitude text,ServerId text);";

	public static final String CREATE_SamplingRegister_TABLE_SQL = "create table dt_samplingRegister (id integer primary key autoincrement, "
			+ "SamplingCreateID  int, "
			+ "CarNo text, "
			+ "CarColor text, "
			+ "CarType text, "
			+ "UseNature text,"
			+ "RegistrationDate text, "
			+ "BrandModel text, "
			+ "EngineModel text, "
			+ "Passengers int, "
			+ "MaxTotalMass int, "
			+ "EmissionStandards text, "
			+ "EmissionLimits int, "
			+ "AmbientTemperature text, "
			+ "EnvironmentalPressure text, "
			+ "RelativeHumidity text, "
			+ "SmokeOpacity1 text, "
			+ "SmokeOpacity2 text, "
			+ "SmokeOpacity3 text, "
			+ "SmokeOpacityAVG text, "
			+ "CheckResult text, "
			+ "Longitude text, "
			+ "Latitude text,"
			+ "RegisterUser text, "
			+ "CheckTime text, "
			+ "CheckAddress text, "
			+ "Remark text,ServerId text,fueltype text, Oiltemperature text, Excessairratio text, Low_co text, Low_hc text, Low_no text, Low_o2 text,Low_co2 text, High_co text, High_hc text, High_no, High_o2 text, High_co2 text, PenaltyReceivable text, PenaltyCollected text, UserResult text);";

	public static final String CREATE_VaporRecovery_TABLE_SQL = "create table dt_vaporRecovery (id integer primary key autoincrement, "
			+ "ExecutionTime text, "
			+ "Address text, "
			+ "OrganizationID int, "
			+ "CheckResult int, "
			+ "Opinion text, "
			+ "EndTime text, "
			+ "RegisterUser text, "
			+ "Longitude text, "
			+ "Latitude text, " + "Remark text, ServerId text);";

	public static final String CREATE_Organization_TABLE_SQL = "create table mi_organization (id integer primary key autoincrement, "
			+ "OrganizationName text, "
			+ "OrganizationAddress text, "
			+ "Linkman text, " + "Phone  text, " + "Remark text);";

	public static final String CREATE_TwoSpeedIdle_TABLE_SQL = "create table tb_two_speed_idle_detection_limits (id integer primary key autoincrement, "
			+ "two_speed_idle_detection_limits_id int, "
			+ "vehicle_exhaust_detection_type_id int, "
			+ "limit_start_time text, "
			+ "limit_end_time text, "
			+ "co_idle_speed  text, "
			+ "hc_idle_speed text, "
			+ "co_high_idle_speed text, "
			+ "hc_high_idle_speed text, "
			+ "excess_air_factor_lower_limit  text, "
			+ "excess_air_factor_upper_limit text);";

	public static final String CREATE_OPAQUE_TABLE_SQL = "create table tb_OPAQUE_FAS_detection_limits (id integer primary key autoincrement, "
			+ "OPAQUE_FAS_detection_limits_id int, "
			+ "admission_method_id int, "
			+ "limit_start_time text, "
			+ "limit_end_time text, " + "smoke_limit  text);";

	public static final String CREATE_PIC_TABLE_SQL = "create table dt_pic (id integer primary key autoincrement, "
			+ "DataID int, "
			+ "DataType int, "
			+ "LocalPath text, "
			+ "RemotePath text, "
			+ "Name text, "
			+ "Longitude text, "
			+ "Latitude text, " + "Status int, " + "Remark text);";

	public static final int VERSION = 3;

	// 单键处理
	private volatile static DB _unique_instance = null;

	private DB() {
	}

	// 注意每个进程都会创建一个EvidenceDatabase实例
	// 绝对不可采用同步方法的方式，同步方法仅对类的一个实例起作用
	public static DB instance(Context context) {
		// context实际无用

		// 检查实例,如是不存在就进入同步代码区
		if (null == _unique_instance) {
			// 对其进行锁,防止两个线程同时进入同步代码区
			synchronized (DB.class) {
				// 必须双重检查
				if (null == _unique_instance) {
					_unique_instance = new DB();
					// _unique_instance.open_or_create_database(GD.get_global_context());
					_unique_instance.open_or_create_database(context);
				}
			}
		}

		return _unique_instance;
	}

	private static final String LOG_TAG = "DB";

	private EvidenceDatabaseHelper _evidence_database_helper = null;

	private SQLiteDatabase _db = null;

	private void open_or_create_database(Context context) {
		Log.v(LOG_TAG, "open_or_create_database");

		if (null == _evidence_database_helper) {
			Log.v(LOG_TAG, "create EvidenceDatabaseHelper");

			_evidence_database_helper = new EvidenceDatabaseHelper(context,
					DATABASE_NAME, VERSION);
		}

		if (null == _evidence_database_helper) {
			_db = null;
			return;
		}

		// getWritableDatabase()和getReadableDatabase()方法都可以获取一个用于操作数据库的SQLiteDatabase实例
		// 但getWritableDatabase()
		// 方法以读写方式打开数据库，一旦数据库的磁盘空间满了，数据库就只能读而不能写，倘若使用的是getWritableDatabase()
		// 方法就会出错。
		// getReadableDatabase()方法先以读写方式打开数据库，如果数据库的磁盘空间满了，就会打开失败，当打开失败后会继续尝试以只读方式打开数据库。
		try {
			_db = _evidence_database_helper.getWritableDatabase();
		} catch (SQLiteException e) {
			_db = _evidence_database_helper.getReadableDatabase();
		}
	}

	public HashMap<String, String> query(String sql) {
		// Log.v(LOG_TAG, "SQLite query: " + sql);

		if (null == _db)
			return null;

		if (null == sql)
			return null;

		if (0 == sql.length())
			return null;

		try {
			Cursor cursor = _db.rawQuery(sql, null);

			if (null == cursor)
				return null;

			int raws_num = cursor.getCount();// 记录数
			if (0 == raws_num) {
				cursor.close();
				return null;
			}

			int columns_num = cursor.getColumnCount();// 字段数
			if (0 == columns_num) {
				cursor.close();
				return null;
			}

			HashMap<String, String> map = new HashMap<String, String>();

			map.put("records_num", Integer.toString(raws_num));

			cursor.moveToFirst();

			int index = 0;
			while (false == cursor.isAfterLast()) {
				for (int i = 0; i < columns_num; ++i) {
					map.put(cursor.getColumnName(i) + "_"
							+ Integer.toString(index), cursor.getString(i));
					// Log.v(LOG_TAG, "query (" + cursor.getColumnName(i) + "_"
					// + Integer.toString(index) + ", " + cursor.getString(i) +
					// ")");
				}

				++index;
				cursor.moveToNext();
			}

			cursor.close();

			return map;
		} catch (SQLException e) {
			Log.v(LOG_TAG, "query error: " + e.toString());
		}

		return null;
	}

	public boolean is_exist(String sql) {
		// Log.v(LOG_TAG, "SQLite is_exist: " + sql);

		if (null == _db)
			return false;

		if (null == sql)
			return false;

		if (0 == sql.length())
			return false;

		try {
			Cursor cursor = _db.rawQuery(sql, null);

			if (null != cursor && 0 != cursor.getCount()) {
				cursor.close();
				return true;
			}
		} catch (SQLException e) {
			Log.v(LOG_TAG, "query error: " + e.toString());
		}

		return false;
	}

	public boolean execute(String sql) {
		// Log.v(LOG_TAG, "SQLite execute: " + sql);

		if (null == _db)
			return false;

		if (null == sql)
			return false;

		if (0 == sql.length())
			return false;

		try {
			_db.execSQL(sql);
		} catch (SQLException e) {
			Log.v(LOG_TAG, "execute error: " + e.toString());
		}

		return true;
	}

	public static class EvidenceDatabaseHelper extends SQLiteOpenHelper {
		public EvidenceDatabaseHelper(Context context, String database_name,
				int version) {
			super(context, database_name, null, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.v(LOG_TAG, "SQLite: onCreate");

			/*
			 * CREATE_BlackSmokeRegister_TABLE_SQL
			 * CREATE_RoadCheckRegister_TABLE_SQL
			 * CREATE_SamplingCreater_TABLE_SQL
			 * CREATE_SamplingRegister_TABLE_SQL CREATE_VaporRecovery_TABLE_SQL
			 * CREATE_Organization_TABLE_SQL CREATE_TwoSpeedIdle_TABLE_SQL
			 * CREATE_OPAQUE_TABLE_SQL CREATE_PIC_TABLE_SQL
			 */

			db.execSQL(CREATE_BlackSmokeRegister_TABLE_SQL);
			Log.v(LOG_TAG, "SQLite: onCreate1");
			db.execSQL(CREATE_RoadCheckRegister_TABLE_SQL);
			Log.v(LOG_TAG, "SQLite: onCreate2");
			db.execSQL(CREATE_SamplingCreater_TABLE_SQL);
			Log.v(LOG_TAG, "SQLite: onCreate3");
			db.execSQL(CREATE_SamplingRegister_TABLE_SQL);
			Log.v(LOG_TAG, "SQLite: onCreate4");
			db.execSQL(CREATE_VaporRecovery_TABLE_SQL);
			Log.v(LOG_TAG, "SQLite: onCreate5");
			db.execSQL(CREATE_Organization_TABLE_SQL);
			Log.v(LOG_TAG, "SQLite: onCreate6");
			db.execSQL(CREATE_TwoSpeedIdle_TABLE_SQL);
			Log.v(LOG_TAG, "SQLite: onCreate7");
			db.execSQL(CREATE_OPAQUE_TABLE_SQL);
			Log.v(LOG_TAG, "SQLite: onCreate8");
			db.execSQL(CREATE_PIC_TABLE_SQL);
			Log.v(LOG_TAG, "SQLite: onCreate9");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.v(LOG_TAG, "SQLite: onUpgrade");

			/*
			 * 数据库定义 名称: ExhaustWH.db
			 * 
			 * 1)冒黑烟车 表名：dt_blackSmokeRegister
			 * 
			 * 2)路检表 表名：dt_roadCheckRegister
			 * 
			 * 3)抽检记录表 表名：dt_samplingCreate
			 * 
			 * 4)抽检车辆列表 表名：dt_samplingRegister
			 * 
			 * 5)监管列表 表名：dt_vaporRecovery
			 * 
			 * 6)组织机构表 表名：mi_organization
			 * 
			 * 7)双怠速限值表 表名：tb_two_speed_idle_detection_limits
			 * 
			 * 8)自由加速限值表 表名：tb_OPAQUE_FAS_detection_limits
			 * 
			 * 9)图片表 表名：dt_pic
			 */

			db.execSQL("DROP TABLE IF EXISTS dt_blackSmokeRegister");
			db.execSQL("DROP TABLE IF EXISTS dt_roadCheckRegister");
			db.execSQL("DROP TABLE IF EXISTS dt_samplingCreate");
			db.execSQL("DROP TABLE IF EXISTS dt_samplingRegister");
			db.execSQL("DROP TABLE IF EXISTS dt_vaporRecovery");
			db.execSQL("DROP TABLE IF EXISTS mi_organization");
			db.execSQL("DROP TABLE IF EXISTS tb_two_speed_idle_detection_limits");
			db.execSQL("DROP TABLE IF EXISTS tb_OPAQUE_FAS_detection_limits");
			db.execSQL("DROP TABLE IF EXISTS dt_pic");

			onCreate(db);
		}

		public ArrayList<Cursor> getData(String Query) {
			// get writable database
			SQLiteDatabase sqlDB = this.getWritableDatabase();
			String[] columns = new String[] { "mesage" };
			// an array list of cursor to save two cursors one has results from
			// the query
			// other cursor stores error message if any errors are triggered
			ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
			MatrixCursor Cursor2 = new MatrixCursor(columns);
			alc.add(null);
			alc.add(null);

			try {
				String maxQuery = Query;
				// execute the query results will be save in Cursor c
				Cursor c = sqlDB.rawQuery(maxQuery, null);

				// add value to cursor2
				Cursor2.addRow(new Object[] { "Success" });

				alc.set(1, Cursor2);
				if (null != c && c.getCount() > 0) {

					alc.set(0, c);
					c.moveToFirst();

					return alc;
				}
				return alc;
			} catch (SQLException sqlEx) {
				Log.d("printing exception", sqlEx.getMessage());
				// if any exceptions are triggered save the error message to
				// cursor an return the arraylist
				Cursor2.addRow(new Object[] { "" + sqlEx.getMessage() });
				alc.set(1, Cursor2);
				return alc;
			} catch (Exception ex) {

				Log.d("printing exception", ex.getMessage());

				// if any exceptions are triggered save the error message to
				// cursor an return the arraylist
				Cursor2.addRow(new Object[] { "" + ex.getMessage() });
				alc.set(1, Cursor2);
				return alc;
			}

		}
	}

	public void begin_transaction() {
		if (null == _db)
			return;

		_db.beginTransaction();
	}

	public void complete_transaction() {
		if (null == _db)
			return;

		_db.setTransactionSuccessful();
		_db.endTransaction();
	}

	public void show_gps_data() {
		new Thread() {
			@Override
			public void run() {
				try {
					// 查询日志
					HashMap<String, String> query_result = query("select * from tb_gps order by timestamp asc;");

					// 查询失败
					if (null == query_result) {
						Log.v("GPS", "query tb_log fail!");
						return;
					}

					int records_num = Integer.valueOf(query_result
							.get("records_num"));

					for (int i = 0; i < records_num; ++i) {
						Log.v("GPS",
								"Record: "
										+ query_result.get("provider_"
												+ Integer.toString(i))
										+ ", "
										+ new java.text.SimpleDateFormat(
												"yyyy-MM-dd HH:mm:ss").format(new java.util.Date(
												1000 * (Long.valueOf(query_result.get("timestamp_"
														+ Integer.toString(i))) - 8 * 3600))));
					}
				} catch (Exception e) {
				}
			}
		}.start();
	}

	public void test() {
		Log.v(LOG_TAG, "DataBase test.");
	}

}