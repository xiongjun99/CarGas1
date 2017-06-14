package com.example.cargas.fragment;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cargas.R;
import com.example.cargas.communication.ComInterface;
import com.example.cargas.communication.ComMsg;
import com.example.cargas.communication.ComMsg.Update;
import com.example.cargas.communication.QuerySessionId;
import com.example.cargas.message.MSG;
import com.example.cargas.message.MessageHandlerManager;
import com.example.cargas.utils.DataCleanManager;
import com.example.cargas.utils.Utils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;

public class SettingsFragment extends Fragment implements OnClickListener {

	private static final String TAG = "SettingsFragment";
	Context context;
	private long folderSize;
	private TextView huncun;
	private String localurl;

	public SettingsFragment(Context context) {
		this.context = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.setting, null);

		initView(view);

//		deleteApk(Utils.Dir+"CarGas.apk");
		register_message();
		return view;
	}

	private void initView(View view) {
		RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.xiugai);
		Button tuichu = (Button) view.findViewById(R.id.tuichu);
		ImageView clean = (ImageView) view.findViewById(R.id.clean);

		try {
			folderSize = DataCleanManager.getFolderSize(new File(Utils.Dir));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		huncun = (TextView) view.findViewById(R.id.huancun);
		huncun.setText("清空缓存(" + (DataCleanManager.getFormatSize(folderSize))
				+ ")");

		layout.setOnClickListener(this);
		tuichu.setOnClickListener(this);
		clean.setOnClickListener(this);
		view.findViewById(R.id.update).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.xiugai:

			getFragmentManager().beginTransaction()
					.replace(R.id.setting_frame_layout, new Password(context))
					.commit();

			break;

		case R.id.tuichu:
			// SharedPreferences prefs =
			// context.getSharedPreferences("password",
			// Context.MODE_PRIVATE);
			// prefs.edit().putBoolean("is_save", false)
			// .commit();

			// 关闭线程
			QuerySessionId.stop();
			Utils.CLOSE = true;
			getActivity().finish();
			break;

		case R.id.clean:
			new AlertDialog.Builder(context)
					.setMessage("确定要清空缓存图片吗？")
					.setPositiveButton("是",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									DataCleanManager.deleteDir(new File(
											Utils.Dir));
									huncun.setText("清空缓存("
											+ (DataCleanManager
													.getFormatSize(folderSize))
											+ ")");
								}
							}).setNegativeButton("否", null).show();

			break;

		case R.id.update:

			ComInterface.APPversionQuery(context);

			break;

		default:
			break;
		}

	}

	/**
	 * 检查软件是否有更新版本
	 * 
	 * @return
	 */
	private boolean isUpdate(float version) {
		// 获取当前软件版本
		int versionCode = getVersionCode(context);
		float serviceCode = version;
		// 版本判断
		if (serviceCode > versionCode) {
			return true;
		}
		return false;
	}

	/**
	 * 获取软件版本号
	 * 
	 * @param context
	 * @return
	 */
	private int getVersionCode(Context context) {
		// TODO Auto-generated method stub
		int versionCode = 0;

		// 获取软件版本号，对应AndroidManifest.xml下android:versionCode
		try {
			versionCode = context.getPackageManager().getPackageInfo(
					Utils.packageName, 0).versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return versionCode;
	}

	private void showNoticeDialog() {
		// TODO Auto-generated method stub
		// 构造对话框
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle("软件更新");
		builder.setMessage("检测到新版本，立即更新吗");
		
		
		
		// 更新
		builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {

			

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				// 显示下载对话框
				// showDownloadDialog();

				localurl = Utils.Dir
						+ url.substring(url.lastIndexOf(File.separator) + 1);
				
				deleteApk(localurl);
				
				
				// 下载
				HttpUtils http = new HttpUtils();
				HttpHandler handler = http.download(url, localurl, true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
						true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
						new RequestCallBack<File>() {
							ProgressDialog pd ;

							@Override
							public void onStart() {
								
								pd = new ProgressDialog(context);
								pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
								pd.setMax(100);
								pd.setTitle("下载进度");
								pd.show();
								
							}

							@Override
							public void onLoading(long total, long current,
									boolean isUploading) {
//								Utils.showToast(context, current+"/"+total);
//								Log.e("TAG", "current:"+current +"; total:"+total + ";current/total:"+current/total);
//								Log.e("TAG", (int) ((current/total) * 100)+"");
								
								pd.setProgress((int) (((current*1.0)/total) * 100));
								
								
							}

							@Override
							public void onSuccess(
									ResponseInfo<File> responseInfo) {
								
								pd.dismiss();
								
								Utils.showToast(context, "下载成功");
								installApk();

								
							}
							
							

							@Override
							public void onFailure(HttpException error,
									String msg) {

								Utils.showToast(context, "下载失败");
							}
						});
			}
		});
		// 稍后更新
		builder.setNegativeButton("稍后更新",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
		Dialog noticeDialog = builder.create();
		noticeDialog.show();
	}

	private void deleteApk(String localUrl) {
		File file = new File(localUrl);
		if (file.exists()){
			file.delete();
		}
	}

	/**
	 * 安装APK文件
	 */
	private void installApk() {
		File apkfile = new File(Utils.Dir, url.substring(url
				.lastIndexOf(File.separator) + 1));
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		context.startActivity(i);

//		android.os.Process.killProcess(android.os.Process.myPid());
	}

	private String url;
	// ////////////////////////////////////////////////////
	// 思想：其实这里的MessageHandler是没什么作用的，如果说要有作用的话，那唯一的作用就是在当前页面退出的时候
	// 将handler置为了null

	public static Handler _message_handler = null;// 界面消息处理句柄

	// 注册地图处理消息
	private void register_message() {
		_message_handler = new MainMessageHandler(Looper.getMainLooper());

		// 根据类名和what来标志arraylist中的handler
		// 注册就是将handler添加到ArrayList中，然后可以在其他地方通过handler来发送消息
		MessageHandlerManager.get_instance().register(_message_handler,
				MSG.APP_UPDATE_SUCCESS, TAG);
		MessageHandlerManager.get_instance().register(_message_handler,
				MSG.BLACK_SMOKE_SHOW_FAIL, TAG);
	}

	// 注销地图处理消息
	private void unregister_message() {
		// 将handler从ArrayList中移除
		MessageHandlerManager.get_instance().unregister(MSG.APP_UPDATE_SUCCESS,
				TAG);
		MessageHandlerManager.get_instance().unregister(MSG.APP_UPDATE_FAIL,
				TAG);
	}

	// 地图消息处理句柄
	private class MainMessageHandler extends Handler {

		MainMessageHandler(Looper looper) {// 与指定的looper关联，而不是默认的looper
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG.APP_UPDATE_SUCCESS:

				ComMsg.Update res = (Update) msg.obj;
				String version = res.APPversion;// 服务器返回的版本号
				url = res.Path;
				if (isUpdate(Float.parseFloat(version))) {// 需要更新
					showNoticeDialog();
				} else {
					Utils.showToast(context, "已经是最新版本");
				}

				break;

			case MSG.APP_UPDATE_FAIL:
				Toast.makeText(context, "服务器连接异常", Toast.LENGTH_SHORT).show();

				break;
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregister_message();
	}

}
