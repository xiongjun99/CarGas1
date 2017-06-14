package com.example.cargas.communication;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.example.cargas.fragment.BlackSmokeDetail;
import com.example.cargas.message.MSG;
import com.example.cargas.utils.DownloadMutex;

/**
 * Http���������첽��
 * 
 * @author YuWei
 * 
 */

@SuppressLint("NewApi")
public class HttpDownloadTask extends AsyncTask<String, Integer, Boolean> {

	private String TAG = "Http";

	// 2014-5-28 WeiHao �������û����������ʱ���ظ�������ļ���
	private String mediaName;

	// ����������ɺ��intent
	private class DownloadCompleteReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(
					DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
				Log.i(TAG,
						"download completely "
								+ intent.getLongExtra(
										DownloadManager.EXTRA_DOWNLOAD_ID, -1));
			}
		}
	}

	private Context context;
	private DownloadManager _download_manager;
	// private DownloadCompleteReceiver _download_receiver;
	private Request _download_request;

	public static long _download_id = -1;

	// �÷���������UI�̵߳���,����������UI�̵߳��� ���Զ�UI�ռ��������

	public HttpDownloadTask(Context context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		_download_id = 0;// �������������Ѵ���
		Log.i(TAG, "start download new application.");

		_download_manager = (DownloadManager) context
				.getSystemService(context.DOWNLOAD_SERVICE);

		// _download_receiver = new DownloadCompleteReceiver();

		// Toast.makeText(context, "׼������", Toast.LENGTH_SHORT).show();
	}

	private void delete_old_file(String filepath, String filename) {
		File file = new File(Environment.getExternalStorageDirectory()
				+ "/tencent/1.jpg");

		if (file == null || false == file.exists()
				|| true == file.isDirectory())
			return;

		file.delete();
	}

	// 2016_09_02_190736.jpg
	private void start_downloading(String url, String filepath, String filename) {
		System.out.println("begin download!!!!!!");
		Log.v("Http", "download begin " + url + ", " + filepath + ", "
				+ filename);
		try {
			delete_old_file(filepath, filename);

			Log.i(TAG, "download file: " + filename + ", url: " + url);
			// String url = "http://10.0.2.2/android/film/G3.mp4";
			_download_request = new Request(Uri.parse(url));

			// ������������
			_download_request.setAllowedNetworkTypes(Request.NETWORK_MOBILE
					| Request.NETWORK_WIFI);
			// ����ʱ������
			_download_request.setAllowedOverRoaming(false);

			// ��֪ͨ������ʾ�������̨��Ĭ����
			// _download_request.setShowRunningNotification(true);
			// ��ʾ���ؽ���
			_download_request.setVisibleInDownloadsUi(true);

			// ��������·��Ϊsdcard��Ŀ¼�µ�schedule�ļ���
			_download_request.setDestinationInExternalPublicDir(filepath,
					filename);

			// ֧����TF������
			// ����·��null����Ĭ��Ϊdata/data/com.android.provider.downloads/cache/
			// _download_request.setDestinationInExternalFilesDir(context, null,
			// "1.jpg");

			_download_request.setTitle("���ظ���");

			// �������ض���
			_download_id = _download_manager.enqueue(_download_request);

			// context.registerReceiver(_download_receiver, new
			// IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
		} catch (Exception e) {
			Log.i(TAG, "download error " + e.toString());
		}
	}

	// ��ѯ����״̬
	int query_status(long id) {
		Cursor cursor = _download_manager.query(new DownloadManager.Query()
				.setFilterById(id));

		if (null == cursor || false == cursor.moveToFirst())// �������moveToFirst
		{
			Log.i(TAG, "query cursor is null");
			return DownloadManager.STATUS_SUCCESSFUL;
		}

		int status = cursor.getInt(cursor
				.getColumnIndex(DownloadManager.COLUMN_STATUS));
		cursor.close();
		cursor = null;
		return status;
	}

	// �÷�������������UI�̵߳��У���Ҫ�����첽�����������ڸ÷����в��ܶ�UI���еĿռ�������ú��޸�
	@SuppressLint("NewApi")
	@Override
	protected Boolean doInBackground(String... params) {

		mediaName = params[2];

		start_downloading(params[0], params[1], params[2]);

		Log.i(TAG, "Download app id: " + _download_id);

		if (0 >= _download_id)
			return false;

		int status = -1;
		while (true) {
			status = query_status(_download_id);

			publishProgress(status);

			if (DownloadManager.STATUS_FAILED == status) {
				Log.i(TAG, "Download app fail " + status);
				_download_manager.remove(_download_id);
				return false;
			} else if (DownloadManager.STATUS_PAUSED == status
					|| DownloadManager.STATUS_PENDING == status
					|| DownloadManager.STATUS_RUNNING == status) {
				Log.v("Baidu", "Downloading app " + status);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (DownloadManager.STATUS_SUCCESSFUL == status) {
				Log.i(TAG, "Download app success " + status);
				break;
			}
		}

		// DownloadMutex._download_task_tasks.remove(params[0]);//fym
		return true;
	}

	// ��doInBackground����ִ�н���֮�������У�����������UI�̵߳��� ���Զ�UI�ռ��������
	@Override
	protected void onPostExecute(Boolean result) {
		// Log.v("Baidu", "install new apk 1");

		// context.unregisterReceiver(_download_receiver);
		if (result) {
			Log.v("Http", "download success " + mediaName + ", "
					+ context.getClass().getSimpleName());
			// ���سɹ���֪ͨ��Ӧҳ��

		} else {
			// ����ʧ�ܣ�֪ͨ��Ӧҳ��R
		}

		_download_id = -1;
	}

	// onProgressUpdate����UI�߳���ִ�У����п��Զ�UI�ռ���в���
	@Override
	protected void onProgressUpdate(Integer... values) {
		// Log.v("Baidu", "download onProgressUpdate " + values[0]);
	}

	// onCancelled����������ȡ��ִ���е�����ʱ����UI
	@Override
	protected void onCancelled() {
		_download_id = -1;
	}
}