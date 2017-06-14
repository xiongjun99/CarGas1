package com.example.cargas.database;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;

public class TwoSpeedParser
{
	//public List<Node> parse(InputStream is)
	public void parse(InputStream is, Context ctx)
	{
		//List<Node> nodes = null;
		//Node node = null;
		
		try
		{
			//Log.v("Baidu", "node parse 0");
			XmlPullParser parser = Xml.newPullParser();	//由android.util.Xml创建一个XmlPullParser实例
			
			//Log.v("Baidu", "node parse 0.1");
			
			parser.setInput(is, "UTF-8");//设置输入流 并指明编码方式
			
			//Log.v("Baidu", "node parse 1");
			
			int eventType = parser.getEventType();
			
			DB.instance(ctx).begin_transaction();
		
			//<node  two_speed_idle_detection_limits_id='2'
			//vehicle_exhaust_detection_type_id='50'
			//limit_start_time='1995-07-01 00:00:00.00'
			//limit_end_time='2000-06-30 00:00:00.00'
			//co_idle_speed='4.5'
			//hc_idle_speed='900'
			//co_high_idle_speed='3'
			//hc_high_idle_speed='900'
			//excess_air_factor_lower_limit='0.97'
			//excess_air_factor_upper_limit='1.03'/>
			while(eventType != XmlPullParser.END_DOCUMENT)
			{
				switch (eventType)
				{
					case XmlPullParser.START_DOCUMENT://0--文档开始事件,可以进行数据初始化处理
						//nodes = new ArrayList<Node>();
						break;
						
					case XmlPullParser.START_TAG://2--开始元素事件
						if(true == parser.getName().equals("node"))
						{
							int count = parser.getAttributeCount();
							
							/*public static final String CREATE_TwoSpeedIdle_TABLE_SQL = "create table tb_two_speed_idle_detection_limits (id integer primary key autoincrement, "
									+ "two_speed_idle_detection_limits_id int, "
									+ "vehicle_exhaust_detection_type_id int, "
									+ "limit_start_time text, "
									+ "limit_end_time text, "
									+ "co_idle_speed  text, "
									+ "hc_idle_speed text, "
									+ "co_high_idle_speed text, "
									+ "hc_high_idle_speed text, "
									+ "excess_air_factor_lower_limit  text, "
									+ "excess_air_factor_upper_limit text);";*/
							
							String two_speed_idle_detection_limits_id = "";
							String vehicle_exhaust_detection_type_id = "";
							String limit_start_time = "";
							String limit_end_time = "";
							String co_idle_speed = "";
							String hc_idle_speed = "";
							String co_high_idle_speed = "";
							String hc_high_idle_speed = "";
							String excess_air_factor_lower_limit = "";
							String excess_air_factor_upper_limit = "";
							
							for(int i = 0; i < count; ++i)
							{
								String key = parser.getAttributeName(i);
								String value = parser.getAttributeValue(i);
								//Log.v("Baidu", "node" + i + " <" + key + ", " + value + ">");
								
								if(key.equals("two_speed_idle_detection_limits_id")) two_speed_idle_detection_limits_id = value;
								else if(key.equals("vehicle_exhaust_detection_type_id")) vehicle_exhaust_detection_type_id = value;
								else if(key.equals("limit_start_time")) limit_start_time = value;
								else if(key.equals("limit_end_time")) limit_end_time = value;
								else if(key.equals("co_idle_speed")) co_idle_speed = value;
								else if(key.equals("hc_idle_speed")) hc_idle_speed = value;
								else if(key.equals("co_high_idle_speed")) co_high_idle_speed = value;
								else if(key.equals("hc_high_idle_speed")) hc_high_idle_speed = value;
								else if(key.equals("excess_air_factor_lower_limit")) excess_air_factor_lower_limit = value;
								else if(key.equals("excess_air_factor_upper_limit")) excess_air_factor_upper_limit = value;
							}
							
							String sql = "insert into tb_two_speed_idle_detection_limits (two_speed_idle_detection_limits_id, vehicle_exhaust_detection_type_id, limit_start_time, limit_end_time, co_idle_speed, hc_idle_speed, co_high_idle_speed, hc_high_idle_speed, excess_air_factor_lower_limit, excess_air_factor_upper_limit) values ("
									+ "'" + two_speed_idle_detection_limits_id + "', "
									+ "'" + vehicle_exhaust_detection_type_id + "', "
									+ "'" + limit_start_time + "', "
									+ "'" + limit_end_time + "', "
									+ "'" + co_idle_speed + "', "
									+ "'" + hc_idle_speed + "', "
									+ "'" + co_high_idle_speed + "', "
									+ "'" + hc_high_idle_speed + "', "
									+ "'" + excess_air_factor_lower_limit + "', "
									+ "'" + excess_air_factor_upper_limit + "');";
							
							//Log.v("Baidu", "node: " + sql);
									
							DB.instance(ctx).execute(sql);
						}
						eventType = parser.next();
						break;
						
					case XmlPullParser.END_TAG://3--结束元素事件
						if(parser.getName().equals("node"))
						{
							//nodes.add(node);
							Log.v("Baidu", "node end: ");// + node.toString());
							//node = null;
						}
						break;
						
					case XmlPullParser.TEXT://4
						//Log.v("Baidu", "node text: " + parser.getText());
						break;
				}
				eventType = parser.next();
			}
			
			DB.instance(ctx).complete_transaction();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.v("Baidu", "osm parse error: " + e.toString());
		}
		
		//return nodes;
	}
}
