package com.example.cargas.communication;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.webkit.URLUtil;

import com.example.cargas.communication.CustomMultipartEntity.ProgressListener;
import com.example.cargas.database.DB;
import com.example.cargas.message.MSG;
import com.example.cargas.utils.Utils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PictureTransfer {
	public static Object _exit_flag = new Object();

	private static ScheduledExecutorService _pic_upload_schedule = null;

	private static Context _ctx = null;

	// public static boolean _running = false;

	// private static Handler _handler = null;

	// ��������
	private volatile static PictureTransfer _unique_instance = null;

	private volatile static Thread attachment_upload_thread = null;

	// ���Բ��ɲ���ͬ�������ķ�ʽ��ͬ�������������һ��ʵ��������
	public static PictureTransfer instance() {

		// ���ʵ��,���ǲ����ھͽ���ͬ��������
		if (null == _unique_instance) {
			// ���������,��ֹ�����߳�ͬʱ����ͬ��������
			synchronized (PictureTransfer.class) {
				// ����˫�ؼ��
				if (null == _unique_instance) {
					_unique_instance = new PictureTransfer();

					attachment_upload_thread = new Thread() {
						@Override
						public void run() {
							try {
								while (true) {
									upload_picture();
									// upload_test();
									Thread.sleep(1000);
								}

							} catch (Exception e) {
								Log.v("Baidu",
										"upload attachment error: "
												+ e.toString());
							}
						}
					};

					// ����һ��ScheduledExecutorService�������������̳߳ش�СΪpoolSize��
					// ��������������poolSize���������һ��queue��ȴ�ִ��
					_pic_upload_schedule = Executors.newScheduledThreadPool(1);
					
					//�ӳ�0.1s��ִ��ָ������
					_pic_upload_schedule.schedule(attachment_upload_thread,
							100, TimeUnit.MILLISECONDS);
					// _pic_upload_schedule.scheduleAtFixedRate(attachment_upload_thread,
					// 500, 10000, TimeUnit.MILLISECONDS);
				}
			}
		}

		return _unique_instance;
	}

	public synchronized void start(Context context) {

		_ctx = context;

	}

	public void stop(Context context) {
		_pic_upload_schedule.shutdownNow();
	}

	private static HashMap<String, String> _query_result = null;

	private static void upload_picture() {
		// Log.d("before", "time");

		// ��ѯ���ݿ�
		// ������һ��δ�ϴ��ĸ���
		_query_result = DB
				.instance(_ctx)
				.query("select * from dt_pic where status = '0' or status = '1' order by id asc limit 1;");

		if (null == _query_result)
			return;

		/*
		 * id integer primary key ��¼id�������� DataID int ��Ӧ������ID Datetype int
		 * 1��ð���̳�ͼƬ��2��·��ͼƬ��3�����ͼƬ��4--������������ͼƬ LocalPath text ͼƬ����·�� RemotePath
		 * text ͼƬԶ��·�� Name text �ļ��� Longitude text ͼƬ���� Latitude text ͼƬγ��
		 * Status int ״̬��0-����δ�ϴ���1-���ϴ��ļ�������δ����http�ӿڣ�2-���ϴ��ļ��������ѵ���http�ӿڣ�3-���ص��ļ�
		 * Remark text ��ע
		 */

		String num = _query_result.get("records_num");

		String id = _query_result.get("id_0");
		String DataID = _query_result.get("DataID_0");
		String Datetype = _query_result.get("DataType_0");
		String LocalPath = _query_result.get("LocalPath_0");
		String RemotePath = _query_result.get("RemotePath_0");
		String Name = _query_result.get("Name_0");
		String Longitude = _query_result.get("Longitude_0");
		String Latitude = _query_result.get("Latitude_0");
		String Status = _query_result.get("Status_0");
		String Remark = _query_result.get("Remark_0");

		
		if (true == Status.equalsIgnoreCase("0")) {
			// HFS�ϴ�
			boolean ret = false;// �ϴ��ɹ����
			// ...
			// MSG.HFS+Utils.getCurrentTime()
			String prefix = Utils.Dir;// NewTask.fileFolder;
			// String nameWithStart = uploadAtt.getUrl();
			String fileName = Name.substring(Name.lastIndexOf(File.separator)
					 + 1);// nameWithStart.substring(nameWithStart.lastIndexOf(File.separator)
									// + 1);

			String uploadUrl = MSG.HFS + Name.substring(0, Name.lastIndexOf(File.separator)
					 + 1);// http://192.168.4.30:60000/MobilePic/
													// + 2016/8/28
			// android.wxapp.service.elec.request.Contants.HFS_URL
			// + File.separator +
			// nameWithStart.substring(0,
			// nameWithStart.lastIndexOf(File.separator));
//			String url = "http://192.168.4.30:60000/MobilePic/" + "20160820/";
			try {
				String result = post(prefix + fileName, uploadUrl);
				if (result.equals("success")) {
					ret = true;
				} else {
					ret = false;
				}

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}

			if (true == ret) {
				DB.instance(_ctx)
						.execute(
								"update dt_pic set status='1' where id = '"
										+ id + "';");
				// AttachmentDatabase.instance(_ctx).complete_transaction();
			}
		}

		if (true == Status.equalsIgnoreCase("1")) {
			// http֪ͨ������
			boolean ret = false;
			// http֪ͨ������
			ComMsg.PicUploadReq uploadReq = new ComMsg.PicUploadReq();
			uploadReq.SessionID = MSG.SessionID;
			uploadReq.DataType = Datetype;
			uploadReq.DataID = DataID;
			uploadReq.PicPath =  Name;
			uploadReq.Longitude = Longitude;
			uploadReq.Latitude = Latitude;
			ComInterface.PicUpload(_ctx, uploadReq, Integer.parseInt(id));
			if (true == ret) {

			}
		}
	}

	private static void upload_test() {
		String file = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/test.jpg";
		String url = "http://192.168.4.30:60000/MobilePic/" + "20160820/";

		File pic = new File(file);
		if (false == pic.exists()) {
			Log.v("Baidu", file + " does not exist! ");
			return;
		}

		// if(true) return;

		Log.v("Baidu", "upload " + file + " to " + url);

		try {
			String result = post(file, url);
			if (result.equals("success")) {
				Log.v("Baidu", "upload file success");
			} else {
				Log.v("Baidu", "upload file fail");
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	//��һ�������Ǳ��ص��ļ�·��
	private static String post(String pathToOurFile, String urlServer)
			throws ClientProtocolException, IOException, JSONException {
		String result = null;
		if (!URLUtil.isNetworkUrl(urlServer)) {
			result = "��������ַ��Ч";
			return result;
		}

		HttpClient httpclient = new DefaultHttpClient();
		// ����ͨ��Э��汾
		httpclient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

		HttpPost httppost = new HttpPost(urlServer);
		File file = new File(pathToOurFile);

		FileBody cbFile = new FileBody(file);

		CustomMultipartEntity multipartContent = new CustomMultipartEntity(
				new ProgressListener() {
					@Override
					public void transferred(long num) {
						// TODO Auto-generated method stub
						// publishProgress((int) ((num / (float) totalSize) *
						// 100));
					}

				});

		multipartContent.addPart("data", cbFile);
		// totalSize = multipartContent.getContentLength();
		// mpEntity.addPart("userfile", cbFile); // <input type="file"
		// name="userfile" /> ��Ӧ��

		httppost.setEntity(multipartContent);
		System.out.println("executing request " + httppost.getRequestLine());

		HttpParams params = httpclient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 3000);
		HttpResponse response = httpclient.execute(httppost);

		System.out.println(response.getStatusLine());// ͨ��Ok

		if (response.getStatusLine().getStatusCode() == 200)
			result = "success";
		else
			result = "failed";

		httpclient.getConnectionManager().shutdown();

		return result;

	}

	public static void download(Context ctx, String remote_url, String filedir,
			String filename) {
		HttpDownloadTask task = (HttpDownloadTask) new HttpDownloadTask(ctx)
				.execute(remote_url, filedir, filename);// ��������������
		
	}

}
