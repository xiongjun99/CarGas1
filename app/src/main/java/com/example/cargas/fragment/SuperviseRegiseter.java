package com.example.cargas.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cargas.R;
import com.example.cargas.activity.MainActivity;
import com.example.cargas.activity.PlayVideo;
import com.example.cargas.communication.ComInterface;
import com.example.cargas.communication.ComMsg;
import com.example.cargas.database.DB;
import com.example.cargas.message.MSG;
import com.example.cargas.message.MessageHandlerManager;
import com.example.cargas.utils.LocationUtils;
import com.example.cargas.utils.LocationUtils.LocationListener;
import com.example.cargas.utils.Utils;
import com.example.cargas.view.DoubleDatePickerDialog;
import com.king.photo.adapter.GridAdapter;
import com.king.photo.adapter.GridAdapter.onPicClicked;
import com.king.photo.util.Bimp;
import com.king.photo.util.ImageItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class SuperviseRegiseter extends Fragment implements OnClickListener {

	private static final String TAG = "SuperviseRegiseter";
	private TextView back;
	private TextView submit;
	private EditText time;
	private EditText loc;
	private EditText comment;
	private Context context;
	private String result;// 结果，合格和不合格

	private GridView noScrollgridview;
	private GridAdapter adapter;

	public int max = 0;

	private ArrayList<ImageItem> tempSelectBitmap = new ArrayList<ImageItem>(); // 选择的图片的临时列表
	private LocationUtils location;
	private String mlongitude;
	private String mlatitude;
	
	private String startTime;

	private int orgId;
	boolean flag = false;
	public SuperviseRegiseter(Context context) {
		this.context = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.supervise_list_create, null);

		location = new LocationUtils(context);
		location.initLocation();
		location.startLocation();
//		location.setLocationListener(new LocationListener() {
//
//			@Override
//			public void onLocationFinished(String longitude, String latitude) {
//				// Utils.showToast(context,
//				// longitude + " " + longitude);
//				mlongitude = longitude;
//				mlatitude = latitude;
//			}
//		});
		

		location.setLocationListener(new LocationListener() {

			@Override
			public void onLocationFinished(String longitude, String latitude,
					String loc1) {

				if (location != null) {
					loc.setText(loc1);
					mlongitude = longitude;
					mlatitude = latitude;
					location.stopLocation();
				}
			}

		});

		init(view);

		// 注册消息
		register_message();
		return view;
	}

	private void init(View view) {

		time = (EditText) view.findViewById(R.id.et_supervise_list_create_date);
		loc = (EditText) view.findViewById(R.id.et_register_location);
		comment = (EditText) view
				.findViewById(R.id.et_supervise_list_create_comment);

		back = (TextView) view.findViewById(R.id.bt_supervise_list_create_back);
		submit = (TextView) view.findViewById(R.id.bt_register_submit);

		back.setOnClickListener(this);
		submit.setOnClickListener(this);

		Spinner spinner = (Spinner) view
				.findViewById(R.id.supervise_list_spinner);
		final String str[] = getResources()
				.getStringArray(R.array.array_status);
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context,
				R.layout.myspinner, str);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter1);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// Toast.makeText(context, str[position], 0).show();
				result = str[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		// 组织机构的spinner
		HashMap<String, String> query = DB.instance(context).query(
				"select id,OrganizationName from mi_organization;");
		ArrayList<String> orgName = new ArrayList<String>();// 存放组织机构，让spinner显示，供用户选择
		final HashMap<String, String> orgMap = new HashMap<String, String>();

		if (query != null) {

			for (int i = 0; i < Integer.parseInt(query.get("records_num")); i++) {
				orgName.add(query.get("OrganizationName_" + i));
				orgMap.put(query.get("OrganizationName_" + i),
						query.get("id_" + i));
			}
		}

		Spinner orgSpinner = (Spinner) view
				.findViewById(R.id.et_supervise_list_create_department);
		final String orgStr[] = new String[orgName.size()];
		orgName.toArray(orgStr);// 把list转换成string数组
		ArrayAdapter<String> orgAdapter1 = new ArrayAdapter<String>(context,
				R.layout.myspinner, orgStr);
		orgAdapter1
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		orgSpinner.setAdapter(orgAdapter1);

		orgSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String name = orgStr[position];// 点击的组织机构名称
				orgId = Integer.parseInt(orgMap.get(name));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		noScrollgridview = (GridView) view
				.findViewById(R.id.vp_supervise_list_create_pic);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(context, tempSelectBitmap);
		// loading();
		adapter.setDeleteListener(new onPicClicked() {

			@Override
			public void notityUpdate(int position) {

				tempSelectBitmap.remove(position);
				max--;

				adapter.notifyDataSetChanged();
			}

		});
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (position == tempSelectBitmap.size()) {// 点击的是加号//cq
					Log.i("ddddddd", "----------");

					String[] array = new String[] { "拍照", "录像" ,"图库"};
					AlertDialog.Builder builder = new AlertDialog.Builder(
							context);
					ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
							context, R.layout.dialog_item, R.id.tv, array);
					// builder.setTitle("选择附件类型");
					builder.setAdapter(arrayAdapter,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									switch (which) {

									case 0:
										photo();
										break;

									case 1:
										video();
										break;
										
									case 2:
										picture();
										break;

									default:
										break;
									}
								}
							});
					AlertDialog dialog = builder.create();
					dialog.show();
				} else {// 点击的是其他图片

					// 使用Intent
					Intent intent = new Intent(Intent.ACTION_VIEW);
					String path = tempSelectBitmap.get(position).getImagePath();
					
					
					if (path != null) {
						
						if (path.contains("jpg")){
							
							intent.setDataAndType(Uri.fromFile(new File(path)),
									"image/*");
							startActivity(intent);
							
						} else if (path.contains("mp4")){
							
							Intent videoIntent = new Intent(context,
									PlayVideo.class);
							videoIntent.putExtra("path", path);
							context.startActivity(videoIntent);
						}
						
					}
					
				}
			}
		});

		// 设置日期
		time.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					Calendar c = Calendar.getInstance();

					// 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
					new DoubleDatePickerDialog(context, 0,
							new DoubleDatePickerDialog.OnDateSetListener() {

								@Override
								public void onDateSet(
										DatePicker startDatePicker,
										int startYear, int startMonthOfYear,
										int startDayOfMonth,
										DatePicker endDatePicker, int endYear,
										int endMonthOfYear, int endDayOfMonth,
										int Hour, int minute) {
									startTime = String.format("%d-%d-%d %d:%d",
											startYear, startMonthOfYear + 1,
											startDayOfMonth, Hour, minute);
									time.setText(startTime);
								}

							}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
									.get(Calendar.DATE), false).show();
				}
				return true;
			}
		});
		
		
//loc.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				if (event.getAction() == MotionEvent.ACTION_UP) {
//					if (!flag) {
//						location.startLocation();
//						flag = true;
//					}
//				}
//				return false;
//			}
//		});
	}
	
	private void picture() {
		Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);;
		
		((FragmentActivity) context).startActivityForResult(intent,
				Utils.Supervise_PICTURE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.bt_supervise_list_create_back:
			getFragmentManager()
					.beginTransaction()
					.replace(R.id.main_framelayout,
							((MainActivity) getActivity()).getmSuper())
					.commit();
			break;

		case R.id.bt_register_submit:

			sTime = time.getText().toString().trim();
			sLoc = loc.getText().toString().trim();
			sComment = comment.getText().toString().trim();
			String sOrg = null;
			sResult = result;

			if (TextUtils.isEmpty(sTime) || TextUtils.isEmpty(sLoc)
					|| TextUtils.isEmpty(sComment)
					|| TextUtils.isEmpty(sResult)) {
				Toast.makeText(context, "内容不能为空", 0).show();
			} else {

				isQulified = 0;

				// 0是合格，1是不合格
				if (sResult.equals("合格")) {
					isQulified = 0;
				} else if (sResult.equals("不合格")) {
					isQulified = 1;
				}

				ComMsg.VaporRecoveryUploadReq uploadReq = new ComMsg.VaporRecoveryUploadReq();
				uploadReq.SessionID = MSG.SessionID;
				uploadReq.Executiontime = sTime;
				uploadReq.Address = sLoc;
				uploadReq.OrganizationID = orgId + "";
				uploadReq.Checkresult = isQulified + "";
				uploadReq.Opinion = sComment;
				uploadReq.Longitude = mlongitude;
				uploadReq.Latitude = mlatitude;

				// TODO
				ComInterface.VaporRecoveryUpload(context, uploadReq);

			}

			break;
		default:
			break;
		}

	}

	private String filePath = null;
	private String videoFilePath = null;
	private String sTime;
	private String sLoc;
	private String sComment;
	private String sResult;
	private int isQulified;

	// 拍照
	public void photo() {
		// 拍照时开始定位
//		location.startLocation();
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		filePath = Utils.Dir + Utils.getFileDate()+".jpg";
		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		((FragmentActivity) context).startActivityForResult(openCameraIntent,
				Utils.SUPERVISE_TAKE_PICTURE);
	}
	
	public void video() {
		Intent intent = new Intent();
		intent.setAction("android.media.action.VIDEO_CAPTURE");
		intent.addCategory("android.intent.category.DEFAULT");
		
		videoFilePath = Utils.Dir + Utils.getFileDate() + ".mp4";
		File file = new File(videoFilePath);
		if (file.exists()) {
			file.delete();
		}
		Uri uri = Uri.fromFile(file);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		
		((FragmentActivity) context).startActivityForResult(intent,
				Utils.SUPERVISE_TAKE_VIDEO);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (tempSelectBitmap.size() < 9
				&& resultCode == getActivity().RESULT_OK) {// cq


			String fileUrl = null;

			Bitmap bm = null;
			if (requestCode == Utils.SUPERVISE_TAKE_PICTURE){
				
				bm = Bimp.decodeSampleBitmapFromFile(filePath, 100, 100);
				fileUrl = filePath;
			} else if (requestCode == Utils.SUPERVISE_TAKE_VIDEO){
				
				bm =  Utils.getVideoThumbnail(videoFilePath, 100, 100,
						MediaStore.Images.Thumbnails.MINI_KIND);
				
				fileUrl = videoFilePath;
			}else if (requestCode == Utils.Supervise_PICTURE){
				Uri uri = data.getData();
				String fileUri = uri.toString();
				bm = Bimp.decodeSampleBitmapFromFile(fileUri, 100, 100);
				
	            Uri selectedImage = data.getData();  
	            String[] filePathColumn = { MediaStore.Images.Media.DATA };  
	   
	            Cursor cursor = context.getContentResolver().query(selectedImage,  
	                    filePathColumn, null, null, null);  
	            cursor.moveToFirst();  
	   
	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);  
	           fileUrl = cursor.getString(columnIndex);  
	            cursor.close(); 
				
			}
			
			
			ImageItem takePhoto = new ImageItem();
			takePhoto.setImagePath(fileUrl);
			takePhoto.setBitmap(bm);
			takePhoto.setLatitude(mlatitude);
			takePhoto.setLogitude(mlongitude);
			tempSelectBitmap.add(takePhoto);// cq
			adapter.setcontent(tempSelectBitmap);
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (location != null) {
			location.stopLocation();
		}
		unregister_message();
	}

	// ////////////////////////////////////////////////////
	public static Handler _message_handler = null;// 界面消息处理句柄

	// 注册地图处理消息
	private void register_message() {
		_message_handler = new MainMessageHandler(Looper.getMainLooper());

		MessageHandlerManager.get_instance().register(_message_handler,
				MSG.Supervise_Submit_SUCESS, TAG);
		MessageHandlerManager.get_instance().register(_message_handler,
				MSG.Supervise_Submit_FAIL, TAG);

	}

	// 注销地图处理消息
	private void unregister_message() {
		MessageHandlerManager.get_instance().unregister(
				MSG.Supervise_Submit_SUCESS, TAG);
		MessageHandlerManager.get_instance().unregister(
				MSG.Supervise_Submit_FAIL, TAG);

	}

	// 地图消息处理句柄
	private class MainMessageHandler extends Handler {
		MainMessageHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case MSG.Supervise_Submit_SUCESS:
				// 提交成功之后，写进数据库
				// TODO

				// 登录返回的消息
				ComMsg.VaporRecoveryUploadRes uploadRes = (ComMsg.VaporRecoveryUploadRes) (msg.obj);

				if (uploadRes.Success.equals("1")) {// 上传成功
					String result = uploadRes.Result;// 成功返回的是上传数据的主键

					// 上传成功，就把图片写进数据库
					for (ImageItem img : tempSelectBitmap) {

						String name = img.getImagePath()
								.substring(
										img.getImagePath().lastIndexOf(
												File.separator) + 1);
						String localPath = img.getImagePath();
						
						String fileDate = Utils.getRemoteFileDate()
								+ File.separator;

						StringBuffer picSql = new StringBuffer();
						picSql.append("insert into dt_pic (DataID,DataType,LocalPath,RemotePath,Name,Longitude,Latitude,Status,Remark) ");
						picSql.append("values (" + Integer.parseInt(result)
								+ "," + MSG.SupervisePic + ",'" + localPath
								+ "','" + MSG.HFS + "','" + (fileDate + name) + "','"
								+ mlongitude + "','" + mlatitude + "'," + 0
								+ ",'" + "');");
						DB.instance(context).execute(picSql.toString());
					}

					// 拼接SQL
					StringBuffer sql = new StringBuffer();
					sql.append("insert into dt_vaporRecovery (ExecutionTime,Address,OrganizationID,CheckResult,Opinion,EndTime,RegisterUser,Longitude,Latitude,ServerId) ");
					sql.append("values (");
					sql.append("'" + sTime + "','" + sLoc + "'," + orgId + ","
							+ isQulified + ",'" + sComment + "','" + "','"
							+ MSG.RegisterUser + "','" + mlongitude + "','"
							+ mlatitude + "','" + result + "');");
					boolean isSuccess = DB.instance(context).execute(
							sql.toString());

					if (isSuccess) {
						Utils.showToast(context, "数据已存储，正在上传中");
					} else {
						Utils.showToast(context, "数据存储失败");
					}

					getFragmentManager()
					.beginTransaction()
					.replace(R.id.main_framelayout,
							((MainActivity) getActivity()).getmSuper())
					.commit();
					submit.setClickable(false);

				} else {
					Utils.showToast(context, uploadRes.Result);
				}

				break;

			case MSG.Supervise_Submit_FAIL:
				Utils.showToast(context, "服务器连接异常");
				break;
			}
		}
	}

}
