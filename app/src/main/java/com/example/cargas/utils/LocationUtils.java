package com.example.cargas.utils;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.bumptech.glide.load.data.LocalUriFetcher;

public class LocationUtils {

	private AMapLocationClient locationClient = null;
	private AMapLocationClientOption locationOption = new AMapLocationClientOption();
	Context context;

	public LocationUtils(Context context) {
		this.context = context;
	}

	/**
	 * 初始化定位
	 * 
	 * @since 2.8.0
	 * @author hongming.wang
	 * 
	 */
	public void initLocation() {
		// 初始化client
		locationClient = new AMapLocationClient(context);
		// 设置定位参数
		locationClient.setLocationOption(getDefaultOption());
		// 设置定位监听
		locationClient.setLocationListener(locationListener);
	}

	/**
	 * 默认的定位参数
	 * 
	 * @since 2.8.0
	 * @author hongming.wang
	 * 
	 */
	private AMapLocationClientOption getDefaultOption() {
		AMapLocationClientOption mOption = new AMapLocationClientOption();
		mOption.setLocationMode(AMapLocationMode.Hight_Accuracy);// 可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
		mOption.setGpsFirst(false);// 可选，设置是否gps优先，只在高精度模式下有效。默认关闭
		mOption.setHttpTimeOut(30000);// 可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
		mOption.setInterval(2000);// 可选，设置定位间隔。默认为2秒
		mOption.setNeedAddress(true);// 可选，设置是否返回逆地理地址信息。默认是ture
		mOption.setOnceLocation(false);// 可选，设置是否单次定位。默认是false
		mOption.setOnceLocationLatest(false);// 可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
		return mOption;
	}

	/**
	 * 定位监听
	 */
	AMapLocationListener locationListener = new AMapLocationListener() {
		@Override
		public void onLocationChanged(AMapLocation loc) {
			if (null != loc) {
				// 解析定位结果
				String result = Utils.getLocationStr(loc);
				// Utils.showToast(context, result);

				if (loc.getErrorCode() == 0) {

//					Utils.showToast(context,
//							loc.getLatitude() + " " + loc.getLongitude());
					
					if (listener != null){
						
						listener.onLocationFinished(String.valueOf(loc.getLongitude()),
								String.valueOf(loc.getLatitude()),loc.getCity()+loc.getDistrict()+loc.getPoiName());
					}
				}
				
			} else {
				// Utils.showToast(context, "定位失败");
			}
		}
	};

	LocationListener listener;

	public void setLocationListener(LocationListener listener) {
		this.listener = listener;
	}

	public interface LocationListener {
		public void onLocationFinished(String longitude, String latitude, String loc);
	}

	/**
	 * 开始定位
	 * 
	 * @since 2.8.0
	 * @author hongming.wang
	 * 
	 */
	public void startLocation() {
		// 根据控件的选择，重新设置定位参数
		// resetOption();
		// 设置定位参数
		locationClient.setLocationOption(locationOption);
		// 启动定位
		locationClient.startLocation();
	}

	/**
	 * 停止定位
	 * 
	 * @since 2.8.0
	 * @author hongming.wang
	 * 
	 */
	public void stopLocation() {
		// 停止定位
		locationClient.stopLocation();
	}

	/**
	 * 销毁定位
	 * 
	 * @since 2.8.0
	 * @author hongming.wang
	 * 
	 */
	public void destroyLocation() {
		if (null != locationClient) {
			/**
			 * 如果AMapLocationClient是在当前Activity实例化的，
			 * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
			 */
			locationClient.onDestroy();
			locationClient = null;
			locationOption = null;
		}
	}

	private LocationUtils location;
}
