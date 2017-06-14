package com.example.cargas.database;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;

public class QPAQUEParser
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
		
			//<node  OPAQUE_FAS_detection_limits_id='1' limit_start_time='' limit_end_time='2005-06-30 00:00:00.00' smoke_limit='2.5' />
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
							
							/*public static final String CREATE_OPAQUE_TABLE_SQL = "create table tb_OPAQUE_FAS_detection_limits (id integer primary key autoincrement, "
									+ "OPAQUE_FAS_detection_limits_id int, "
									+ "admission_method_id int, "
									+ "limit_start_time text, "
									+ "limit_end_time text, "
									+ "smoke_limit  text);";*/
							
							String OPAQUE_FAS_detection_limits_id = "";
							String admission_method_id = "";
							String limit_start_time = "";
							String limit_end_time = "";
							String smoke_limit = "";
							
							for(int i = 0; i < count; ++i)
							{
								String key = parser.getAttributeName(i);
								String value = parser.getAttributeValue(i);
								//Log.v("Baidu", "node" + i + " <" + key + ", " + value + ">");
								
								if(key.equals("OPAQUE_FAS_detection_limits_id")) OPAQUE_FAS_detection_limits_id = value;
								else if(key.equals("admission_method_id")) admission_method_id = value;
								else if(key.equals("limit_start_time")) limit_start_time = value;
								else if(key.equals("limit_end_time")) limit_end_time = value;
								else if(key.equals("smoke_limit")) smoke_limit = value;
							}
							
							String sql = "insert into tb_OPAQUE_FAS_detection_limits (OPAQUE_FAS_detection_limits_id, admission_method_id, limit_start_time, limit_end_time, smoke_limit) values ("
									+ "'" + OPAQUE_FAS_detection_limits_id + "', "
									+ "'" + admission_method_id + "', "
									+ "'" + limit_start_time + "', "
									+ "'" + limit_end_time + "', "
									+ "'" + smoke_limit + "');";
							
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
