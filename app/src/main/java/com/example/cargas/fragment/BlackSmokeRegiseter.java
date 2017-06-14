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
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
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
import com.king.photo.adapter.GridAdapter;
import com.king.photo.adapter.GridAdapter.onPicClicked;
import com.king.photo.util.Bimp;
import com.king.photo.util.ImageItem;

import java.io.File;
import java.util.ArrayList;

public class BlackSmokeRegiseter extends Fragment implements OnClickListener {

	private static final String TAG = "BlackSmokeRegiseter";
	private EditText mCarNumber;
	private EditText mLocation;
	private EditText mComment;
	private TextView mSubmit;
	private TextView mBack;

	private GridView noScrollgridview;
	private GridAdapter adapter;

	private Context context;
	private String filePath = null;
	private String mQualified = null;
	private String mCarColor = null;

	private String carNumber;
	String loc;
	String comment;

	private LocationUtils location;
	boolean flag = false;

	public BlackSmokeRegiseter(Context context) {
		this.context = context;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.black_smoke_register, null);
		location = new LocationUtils(context);
		location.initLocation();
		
		location.startLocation();

		location.setLocationListener(new LocationListener() {

			@Override
			public void onLocationFinished(String longitude, String latitude,
					String loc) {

				if (location != null) {
					mLocation.setText(loc);
					mlongitude = longitude;
					mlatitude = latitude;
					location.stopLocation();
				}
			}

		});

		register_message();

		init(view);
		return view;
	}

	private void init(View view) {
		mCarNumber = (EditText) view.findViewById(R.id.et_register_carnumber);
		mLocation = (EditText) view.findViewById(R.id.et_register_location);
		mComment = (EditText) view.findViewById(R.id.et_register_comment);

		mSubmit = (TextView) view.findViewById(R.id.bt_register_submit);
		mBack = (TextView) view.findViewById(R.id.bt_black_back);

		Spinner colorSpinner = (Spinner) view
				.findViewById(R.id.black_smoke_color);
		Spinner statusSpinner = (Spinner) view
				.findViewById(R.id.black_smoke_status);

		final String str[] = getResources().getStringArray(R.array.array_name);// 颜色
		final String str2[] = getResources().getStringArray(
				R.array.array_status);
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context,
				R.layout.myspinner, str);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(context,
				R.layout.myspinner, str2);

		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		colorSpinner.setAdapter(adapter1);
		statusSpinner.setAdapter(adapter2);

		colorSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				mCarColor = str[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		statusSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				mQualified = str2[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		mSubmit.setOnClickListener(this);
		mBack.setOnClickListener(this);

		noScrollgridview = (GridView) view
				.findViewById(R.id.black_noScrollgridview);
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

					// photo();
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
//							intent.setDataAndType(Uri.fromFile(new File(path)),
//									"video/*");
						}
						
					}
					
				}
			}
		});
		

//		mLocation.setOnTouchListener(new OnTouchListener() {
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
				Utils.BLACK_PICTURE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.bt_register_submit:
			carNumber = mCarNumber.getText().toString().trim();
			loc = mLocation.getText().toString().trim();
			comment = mComment.getText().toString().trim();

			if (TextUtils.isEmpty(carNumber) || TextUtils.isEmpty(loc)
					|| TextUtils.isEmpty(comment)
					|| TextUtils.isEmpty(mQualified)
					|| TextUtils.isEmpty(mCarColor)) {
				Toast.makeText(context, "内容不能为空", Toast.LENGTH_SHORT).show();
			} else {
				isQulified = null;

				// 0是合格，1是不合格
				if (mQualified.equals("合格")) {
					isQulified = "0";
				} else if (mQualified.equals("不合格")) {
					isQulified = "1";
				}

				// Toast.makeText(
				// context,
				// carNumber + " " + mCarColor + " " + loc + " " + comment
				// + " " + isQulified, 0).show();

				registerTime = Utils.getCurrentTime();

				// 检查网络是否正常
				if (Utils.isNetworkAvailable(context)) {
					ComMsg.BlackSmokeRegisterUploadReq blackReq = new ComMsg.BlackSmokeRegisterUploadReq();
					blackReq.SessionID = MSG.SessionID;
					blackReq.Registertime = registerTime;
					blackReq.CarNo = carNumber;
					blackReq.CarColor = mCarColor;
					blackReq.Fieldevaluation = comment;
					blackReq.RegisterStatus = isQulified;
					blackReq.Longitude = mlongitude;
					blackReq.Latitude = mlatitude;
					blackReq.Address = loc;

					Log.v("Baidu",
							"ComInterface.BlackSmokeRegisterUpload(context, blackReq);");
					ComInterface.BlackSmokeRegisterUpload(context, blackReq);

				} else {

					Utils.showToast(context, "请检测网络是否连接正常");
				}

			}

			break;

		case R.id.bt_black_back:
			// TODO 返回
			FragmentManager fm = getFragmentManager();
			fm.beginTransaction()
					.replace(R.id.main_framelayout,
							((MainActivity) getActivity()).getmBlack())
					.commit();

			break;

		default:
			break;
		}

	}

	// 拍照
	public void photo() {
		// 拍照时开始定位
		// location.startLocation();

		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		filePath = Utils.Dir + Utils.getFileDate() + ".jpg";
		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		((FragmentActivity) context).startActivityForResult(openCameraIntent,
				Utils.BLACK_TAKE_PICTURE);

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
				Utils.BLACK_TAKE_VIDEO);
	}

	public static int max = 0;

	private ArrayList<ImageItem> tempSelectBitmap = new ArrayList<ImageItem>(); // 选择的图片的临时列表

	private String mlongitude;
	private String mlatitude;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (tempSelectBitmap.size() < 9
				&& resultCode == getActivity().RESULT_OK) {// cq

			String fileName = String.valueOf(System.currentTimeMillis());
			String fileUrl = null;

			Bitmap bm = null;
			if (requestCode == Utils.BLACK_TAKE_PICTURE){
				
				bm = Bimp.decodeSampleBitmapFromFile(filePath, 100, 100);
				fileUrl = filePath;
			} else if (requestCode == Utils.BLACK_TAKE_VIDEO){
				
				bm =  Utils.getVideoThumbnail(videoFilePath, 100, 100,
						MediaStore.Images.Thumbnails.MINI_KIND);
				
				fileUrl = videoFilePath;
			} else if (requestCode == Utils.BLACK_PICTURE){
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
			// Utils.showToast(context, "onActivityResult"+mlongitude + " " +
			// mlatitude);
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

	@Override
	public void onResume() {
		super.onResume();
	}

	// ////////////////////////////////////////////////////
	// 思想：其实这里的MessageHandler是没什么作用的，如果说要有作用的话，那唯一的作用就是在当前页面退出的时候
	// 将handler置为了null

	public static Handler _message_handler = null;// 界面消息处理句柄
	private String registerTime;
	private String isQulified;
	private String videoFilePath;

	// 注册地图处理消息
	private void register_message() {
		_message_handler = new MainMessageHandler(Looper.getMainLooper());

		// 根据类名和what来标志arraylist中的handler
		// 注册就是将handler添加到ArrayList中，然后可以在其他地方通过handler来发送消息
		MessageHandlerManager.get_instance().register(_message_handler,
				MSG.BLACK_SMOKE_SHOW_SUCCESS, TAG);
		MessageHandlerManager.get_instance().register(_message_handler,
				MSG.BLACK_SMOKE_SHOW_FAIL, TAG);
	}

	// 注销地图处理消息
	private void unregister_message() {
		// 将handler从ArrayList中移除
		MessageHandlerManager.get_instance().unregister(
				MSG.BLACK_SMOKE_SHOW_SUCCESS, TAG);
		MessageHandlerManager.get_instance().unregister(
				MSG.BLACK_SMOKE_SHOW_FAIL, TAG);
	}

	// 地图消息处理句柄
	private class MainMessageHandler extends Handler {
		MainMessageHandler(Looper looper) {// 与指定的looper关联，而不是默认的looper
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG.BLACK_SMOKE_SHOW_SUCCESS:

				// 登录返回的消息
				ComMsg.BlackSmokeRegisterUploadRes res = (ComMsg.BlackSmokeRegisterUploadRes) (msg.obj);

				if (res.Success.equals("1")) {// 上传成功
					String result = res.Result;// 成功返回的是上传数据的主键

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
								+ "," + MSG.BlackPic + ",'" + localPath + "','"
								+ MSG.HFS + "','" + (fileDate + name) + "','"
								+ mlongitude + "','" + mlatitude + "'," + 0
								+ ",'" + "');");
						DB.instance(context).execute(picSql.toString());
					}

					// 拼接SQL
					StringBuffer sql = new StringBuffer();
					sql.append("insert into dt_blackSmokeRegister (RegisterUser,RegisterTime,CarNo,CarColor,Address,FieldEvaluation,RegisterStatus,PunishTime,PunishUser,PunishStatus,Longitude,Latitude,ServerId) ");
					sql.append("values (");
					sql.append("'" + MSG.RegisterUser + "','" + registerTime
							+ "','" + carNumber + "','" + mCarColor + "','"
							+ loc + "','" + comment + "','" + isQulified
							+ "','" + "','" + "','" + "0','" + mlongitude
							+ "','" + mlatitude + "','" + result + "');");
					boolean isSuccess = DB.instance(context).execute(
							sql.toString());
					// 上传数据，外加写数据库

					if (isSuccess) {
						Utils.showToast(context, "数据已存储，正在上传中");
					} else {
						Utils.showToast(context, "数据存储失败");
					}
					if (!mSubmit.isClickable()) {
						Utils.showToast(context, "不能重复提交");
					}
					// 点击了一次就不能点击了
					mSubmit.setClickable(false);

					// 返回
					FragmentManager fm = getFragmentManager();
					fm.beginTransaction()
							.replace(R.id.main_framelayout,
									((MainActivity) getActivity()).getmBlack())
							.commit();

				} else {
					// 显示失败原因
					Utils.showToast(context, res.Result);
				}

				break;

			case MSG.BLACK_SMOKE_SHOW_FAIL:
				Toast.makeText(context, "服务器连接异常", Toast.LENGTH_SHORT).show();

				break;
			}
		}
	}

}
