package com.example.cargas.database;

import android.content.Context;
import android.util.Log;

import com.example.cargas.utils.Utils;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//http://lcm-proj.github.io/
//https://github.com/lcm-proj/lcm
//API: http://lcm-proj.github.io/javadocs/index.html

public class ConstantParseThread
{
	//public static Object _exit_flag = new Object();
	
	private static ScheduledExecutorService _osm_parse_service = null;
	
	private static final String xml_file = "nad.xml";
	//private static final String xml_file = "node.xml";
	
	private static Context _ctx = null;
	
	private static Thread _lcm_thread = new Thread()
	{
		@Override
		public void run()
		{
			
			
			long t = System.currentTimeMillis();
			try
			{
				Log.v("Baidu", "ConstantParseThread 1");
				
				
				
				java.io.InputStream is = null;
				
				{
					//表为空就写数据
					if (DB.instance(_ctx).query("select * from tb_two_speed_idle_detection_limits;")== null){
						DB.instance(_ctx).execute("delete from tb_two_speed_idle_detection_limits;");
						
						is = _ctx.getAssets().open("tb_two_speed_idle_detection_limits.xml");
						TwoSpeedParser node_parser = new TwoSpeedParser();
						node_parser.parse(is, _ctx);
					}
					
					
				}
				
				{
					if(DB.instance(_ctx).query("select * from tb_OPAQUE_FAS_detection_limits;") == null){
						
						DB.instance(_ctx).execute("delete from tb_OPAQUE_FAS_detection_limits;");
						
						is = _ctx.getAssets().open("tb_OPAQUE_FAS_detection_limits.xml");
						QPAQUEParser node_parser = new QPAQUEParser();
						node_parser.parse(is, _ctx);
					}
				}
				
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.v("Baidu", "osm parse thread error 1: " + e.toString());
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.v("Baidu", "osm parse thread error 2: " + e.toString());
			}
			
			Log.e("TAG", "OSM parse: " + (System.currentTimeMillis() - t) + "ms");
			
			Utils.showToast(_ctx, (System.currentTimeMillis() - t)/1000 +"s");
			
			
		}
	};
	
	public static void start(Context context)
	{
		_ctx = context;
		
		//if(true) Log.v("Baidu", "LCMThread start " + System.currentTimeMillis());
		
		_osm_parse_service = Executors.newScheduledThreadPool(1);
		//_osm_parse_service.scheduleAtFixedRate(_lcm_thread, 100, 10000, TimeUnit.MILLISECONDS);
		_osm_parse_service.schedule(_lcm_thread, 100, TimeUnit.MILLISECONDS);
	}
	
	public static void stop(Context context)
	{
		_osm_parse_service.shutdownNow();
	}	
}


