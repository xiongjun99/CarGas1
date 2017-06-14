package com.example.cargas.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.amap.api.location.AMapLocation;
import com.example.cargas.activity.Login;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.widget.Toast;

public class Utils {
	public static boolean CLOSE = false;

	public static final String Dir = Environment.getExternalStorageDirectory()
			.getPath() + File.separator + "TestPhoto" + File.separator;
	public static final String TestDir = Environment
			.getExternalStorageDirectory().getPath()
			+ File.separator
			+ "shanraisshan" + File.separator;

	public static final String packageName = "com.example.cargas";

	public static final int BLACK_TAKE_PICTURE = 701;
	public static final int ROAD_TAKE_PICTURE = 702;
	public static final int Random_TAKE_PICTURE = 703;
	public static final int SUPERVISE_TAKE_PICTURE = 704;

	public static final int BLACK_TAKE_VIDEO = 705;
	public static final int ROAD_TAKE_VIDEO = 706;
	public static final int Random_TAKE_VIDEO = 707;
	public static final int SUPERVISE_TAKE_VIDEO = 708;
	
	public static final int BLACK_PICTURE = 709;//从图库中选择图片
	public static final int Road_PICTURE = 710;//从图库中选择图片
	public static final int Random_PICTURE = 711;//从图库中选择图片
	public static final int Supervise_PICTURE = 712;//从图库中选择图片


	// 日期 cq
	public static String getFileDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HHmmss");
		Date date = new Date(System.currentTimeMillis());
		String file = format.format(date);
		return file;
	}

	public static String getRemoteFileDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date(System.currentTimeMillis());
		String file = format.format(date);
		return file;
	}

	public static String getCurrentTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String file = format.format(date);
		return file;
	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static void showToast(Context context, String msg) {
		Toast.makeText(context, msg, 0).show();
	}

	/**
	 * 根据定位结果返回定位信息的字符串
	 * 
	 * @param loc
	 * @return
	 */
	public synchronized static String getLocationStr(AMapLocation location) {
		if (null == location) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		// errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
		if (location.getErrorCode() == 0) {
			sb.append("定位成功" + "\n");
			sb.append("定位类型: " + location.getLocationType() + "\n");
			sb.append("经    度    : " + location.getLongitude() + "\n");
			sb.append("纬    度    : " + location.getLatitude() + "\n");
			sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
			sb.append("提供者    : " + location.getProvider() + "\n");

			if (location.getProvider().equalsIgnoreCase(
					android.location.LocationManager.GPS_PROVIDER)) {
				// 以下信息只有提供者是GPS时才会有
				sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
				sb.append("角    度    : " + location.getBearing() + "\n");
				// 获取当前提供定位服务的卫星个数
				sb.append("星    数    : " + location.getSatellites() + "\n");
			} else {
				// 提供者是GPS时是没有以下信息的
				sb.append("国    家    : " + location.getCountry() + "\n");
				sb.append("省            : " + location.getProvince() + "\n");
				sb.append("市            : " + location.getCity() + "\n");
				sb.append("城市编码 : " + location.getCityCode() + "\n");
				sb.append("区            : " + location.getDistrict() + "\n");
				sb.append("区域 码   : " + location.getAdCode() + "\n");
				sb.append("地    址    : " + location.getAddress() + "\n");
				sb.append("兴趣点    : " + location.getPoiName() + "\n");
				// 定位完成的时间
				// sb.append("定位时间: " + formatUTC(location.getTime(),
				// "yyyy-MM-dd HH:mm:ss:sss") + "\n");
			}
		} else {
			// 定位失败
			sb.append("定位失败" + "\n");
			sb.append("错误码:" + location.getErrorCode() + "\n");
			sb.append("错误信息:" + location.getErrorInfo() + "\n");
			sb.append("错误描述:" + location.getLocationDetail() + "\n");
		}
		// 定位之后的回调时间
		// sb.append("回调时间: " + formatUTC(System.currentTimeMillis(),
		// "yyyy-MM-dd HH:mm:ss:sss") + "\n");
		return sb.toString();
	}

	public static Bitmap getVideoThumbnail(String videoPath, int width,
			int height, int kind) {
		Bitmap bitmap = null;
		// 获取视频的缩略图
		bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);

		if (bitmap == null) {
			return null;
		}

		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	public static String getMd5Value(String sSecret) {
		try {
			MessageDigest bmd5 = MessageDigest.getInstance("MD5");
			bmd5.update(sSecret.getBytes());
			int i;
			StringBuffer buf = new StringBuffer();
			byte[] b = bmd5.digest();
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String encryptToSHA(String info) {
		byte[] digesta = null;
		try {
			MessageDigest alga = MessageDigest.getInstance("SHA-1");
			alga.update(info.getBytes());
			digesta = alga.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String rs = byte2hex(digesta);
		return rs;
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs;
	}
	public static void saveBitmap(String path, Bitmap bm) {
		File f = new File(path);
		if (f.exists()) {
			f.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
