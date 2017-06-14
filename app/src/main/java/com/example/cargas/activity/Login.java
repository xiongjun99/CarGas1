package com.example.cargas.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.Generate_md5;
import com.example.cargas.R;
import com.example.cargas.communication.ComInterface;
import com.example.cargas.communication.ComMsg;
import com.example.cargas.communication.PictureTransfer;
import com.example.cargas.database.ConstantParseThread;
import com.example.cargas.message.MSG;
import com.example.cargas.message.MessageHandlerManager;
import com.example.cargas.utils.Utils;

public class Login extends Activity implements OnClickListener {

	private static final String TAG = "Login";
	private static final String IS_SAVE = "is_save";
	private EditText et_account;
	private EditText et_password;
	private Button login;
	private String mAccount;
	private String mPassword;

	boolean flag = false;// 默认不勾选
	protected ProgressDialog mProgressDialog;
	SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 无标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// 去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 去掉虚拟按键全屏显示
		getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

		// 设置屏幕始终在前面，不然点击鼠标，重新出现虚拟按键
		getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_LAYOUT_STABLE
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav
						// bar
						| View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
		);

		// 隐藏键盘
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		initView();

		// 开启图片上传线程
		PictureTransfer.instance().start(getApplicationContext());

		
//		if (!getSharedPreferences("password", Context.MODE_PRIVATE).getBoolean("isExist", false)){
			// 写双怠速和限值表
			ConstantParseThread.start(getApplicationContext());
			
//		}
			

		int width = getWindowManager().getDefaultDisplay().getWidth();
		int height = getWindowManager().getDefaultDisplay().getHeight();

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		float dp = metrics.density;

		// 注册消息
		register_message();
	}

	private void initView() {
		mProgressDialog = new ProgressDialog(this);
		prefs = getSharedPreferences("password", Context.MODE_PRIVATE);
		editor = prefs.edit();

		et_account = (EditText) findViewById(R.id.et_account);
		et_password = (EditText) findViewById(R.id.et_password);
		login = (Button) findViewById(R.id.bt_login);
		rem = (ImageView) findViewById(R.id.rem);

		LinearLayout layout = (LinearLayout) findViewById(R.id.rempass);

		// 记住密码了，显示
		if (prefs.getBoolean(IS_SAVE, false)) {
			et_account.setText(prefs.getString("username", ""));
			et_password.setText(prefs.getString("password", ""));
			rem.setImageResource(R.drawable.rempass2_2);
		}

		login.setOnClickListener(this);
		rem.setOnClickListener(this);
		layout.setOnClickListener(this);

	}

	@Override
	protected void onResume() {
		super.onResume();
		// 记住密码了，显示
		if (prefs.getBoolean(IS_SAVE, false)) {
			et_account.setText(prefs.getString("username", ""));
			et_password.setText(prefs.getString("password", ""));
			rem.setImageResource(R.drawable.rempass2_2);
		} else {
			et_account.setText(prefs.getString("username", ""));
			et_password.setText("");
			rem.setImageResource(R.drawable.rempass1_1);
		}

		if (Utils.CLOSE) {
			Utils.CLOSE = false;

			// 开启图片上传线程
			PictureTransfer.instance().stop(getApplicationContext());

			// 写双怠速和限值表
			ConstantParseThread.stop(getApplicationContext());
			finish();
			System.exit(0);
		}
	}

	protected void showProgressDialog(String title, String message) {
		mProgressDialog.setTitle(title);
		if (message != null)
			mProgressDialog.setMessage(message);
		if (!mProgressDialog.isShowing())
			mProgressDialog.show();
	}

	protected void showProgressDialog(String title) {
		showProgressDialog(title, null);
	}

	protected void dismissProgressDialog() {
		if (mProgressDialog.isShowing())
			mProgressDialog.dismiss();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_login:
			// ComInterface.LoginIn(getApplicationContext(), "admin",
			// "aKJ/Sc3qO1/A3Xsh/vOwToKpWz0a5qj8gxmjloidxQ2XathGeKbHBqL3gHYrdPcOQ");

			mAccount = et_account.getText().toString();
			mPassword = et_password.getText().toString();

			// "UserName":"admin","PassWord":"KJ/Sc3qO1/A3Xsh/vOwToKpWz0a5qj8gxmjloidxQ2XathGeKbHBqL3gHYrdPcOQ"
			// 测试
			// mAccount = "admin";
			// mPassword =
			// "KJ/Sc3qO1/A3Xsh/vOwToKpWz0a5qj8gxmjloidxQ2XathGeKbHBqL3gHYrdPcOQ";
			if (TextUtils.isEmpty(mAccount) || TextUtils.isEmpty(mPassword)) {
				Utils.showToast(Login.this, "用户名或密码不能为空");
			} else {

			if (mPassword.length() != 32) {
					mPassword = Generate_md5.generate_md5(et_password.getText()
							.toString());
				}
				// 保存密码
				editor.putString("username", mAccount).commit();
				editor.putString("password", mPassword).commit();

				// 1.显示进度条
				mProgressDialog.setCancelable(true);
				mProgressDialog.setCanceledOnTouchOutside(false);
				showProgressDialog("正在登录", "努力加载数据中...请稍后~");
				// showProgressDialog("正在登录", prefs.getString("username", ""));

				// 检查网络是否正常
				if (Utils.isNetworkAvailable(Login.this)) {
					ComMsg.LoginInReq loginReq = new ComMsg.LoginInReq();
					loginReq.UserName = mAccount;
					loginReq.PassWord = mPassword;
					ComInterface.LoginIn(Login.this, loginReq, 0);

				} else {

					Utils.showToast(Login.this, "请检测网络是否连接正常");
				}
			}

			break;

		case R.id.rempass:

			 Intent intent = new Intent(Login.this,
			 AndroidDatabaseManager.class);
			 startActivity(intent);

			if (flag) {
				rem.setImageResource(R.drawable.rempass2_2);
				// 此时是勾选状态，点击之后不勾选
				editor.putBoolean(IS_SAVE, true).commit();
				flag = false;
			} else {
				rem.setImageResource(R.drawable.rempass1_1);
				editor.putBoolean(IS_SAVE, false).commit();
				flag = true;
			}
			break;

		default:
			break;
		}
	}

	// ////////////////////////////////////////////////////
	public static Handler _message_handler = null;// 界面消息处理句柄
	private ImageView rem;
	private Editor editor;

	// 注册地图处理消息
	private void register_message() {
		_message_handler = new MainMessageHandler(Looper.getMainLooper());

		MessageHandlerManager.get_instance().register(_message_handler,
				MSG.LOGIN_SUCCESS, TAG);
		MessageHandlerManager.get_instance().register(_message_handler,
				MSG.LOGIN_FAIL, TAG);
	}

	// 注销地图处理消息
	private void unregister_message() {
		MessageHandlerManager.get_instance().unregister(MSG.LOGIN_SUCCESS, TAG);
		MessageHandlerManager.get_instance().unregister(MSG.LOGIN_FAIL, TAG);
	}

	// 地图消息处理句柄
	private class MainMessageHandler extends Handler {
		MainMessageHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG.LOGIN_SUCCESS:

				// 登录返回的消息
				ComMsg.LoginInRes res = (ComMsg.LoginInRes) (msg.obj);

				if (res.Success.equals("1")) {// 登录成功

					Intent intent = new Intent(Login.this, MainActivity.class);
					startActivity(intent);

					String sesssionId = res.Result;
					MSG.SessionID = res.Result;
					MSG.RegisterUser = mAccount;
					MSG.Password = mPassword;
					// Utils.showToast(Login.this, "sesssionid: " + sesssionId);

				} else {
					// 显示失败原因
					Utils.showToast(getApplicationContext(), res.Result);
				}

				dismissProgressDialog();
				break;

			case MSG.LOGIN_FAIL:
				Toast.makeText(getApplicationContext(), "网络连接异常",
						Toast.LENGTH_SHORT).show();

				dismissProgressDialog();
				break;
			}
		}
	}

	public void onDestroy() {
		super.onDestroy();
		unregister_message();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}

}
