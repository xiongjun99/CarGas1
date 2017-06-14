package com.example.cargas.communication;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.example.cargas.message.MSG;
import com.example.cargas.utils.Utils;

public class QuerySessionId {

	private static ScheduledExecutorService _sessionid_schedule = null;

	private volatile static Thread _thread = null;

	public static void instance(final Context ctx) {

		final ComMsg.LoginInReq loginReq = new ComMsg.LoginInReq();

		_thread = new Thread() {

			@Override
			public void run() {
				Looper.prepare();
				
				while (true) {
					try {

						Thread.sleep(5000);
						loginReq.UserName = MSG.RegisterUser;
						loginReq.PassWord = MSG.Password;
						ComInterface.LoginIn(ctx, loginReq, 1);
						Utils.showToast(ctx, MSG.SessionID);

						Log.e("qq", "chenqiang111122222");
					} catch (Exception e) {
						Log.v("Baidu",
								"upload attachment error: " + e.toString());
					}
				}
				
			}
		};

		_sessionid_schedule = Executors.newScheduledThreadPool(1);
		_sessionid_schedule.schedule(_thread, 100, TimeUnit.MILLISECONDS);
	}
	
	public static void stop() {
		_sessionid_schedule.shutdownNow();
	}

}
