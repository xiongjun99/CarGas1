package com.example.cargas.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cargas.R;
import com.example.cargas.communication.ComMsg.SamplingCreateUploadReq;
import com.example.cargas.model.SamplingCreateQueryResponse;

public class DetailCustomDialog extends Dialog implements
		android.view.View.OnClickListener {

	Context context;
	private SamplingCreateQueryResponse sampleData;

	public interface ClickListenerInterface {

		public void doConfirm(String name, String location, String startTime,
				String endTime, String people, String renyuan);

		public void doCancel();
	}

	public DetailCustomDialog(Context context,
			SamplingCreateQueryResponse sampleData) {
		super(context);
		this.context = context;
		this.sampleData = sampleData;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		init();
	}

	private void init() {
		// 设置dialog没有标题
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.detail_dialog_show, null);
		setContentView(view);

		ImageView delete = (ImageView) view.findViewById(R.id.dialog_delete);
		delete.setOnClickListener(this);

		et_name = (TextView) view.findViewById(R.id.dialog_name_tv);
		et_loacation = (TextView) view.findViewById(R.id.dialog_location_tv_show);
		et_dialog_time_from = (TextView) view
				.findViewById(R.id.dialog_time_from_tv);
		et_people = (TextView) view.findViewById(R.id.dialog_people_tv);
		et_renyuan = (TextView) view.findViewById(R.id.dialog_renyuan_tv);

		Window dialogWindow = getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
		lp.width = (int) (d.widthPixels * 0.6); // 高度设置为屏幕的0.6
		dialogWindow.setAttributes(lp);

		et_name.setText(sampleData.getSamplingName());
		et_loacation.setText(sampleData.getSamplingAddress());
		if (sampleData.getStartTime().contains("T")
				|| sampleData.getEndTime().contains("T")) {

			et_dialog_time_from.setText(sampleData.getStartTime().replace("T",
					" ")
					+ "  至   " + sampleData.getEndTime().replace("T", " "));
		} else {
			et_dialog_time_from.setText(sampleData.getStartTime()
					+ "  至   " + sampleData.getEndTime());
		}
		et_people.setText(sampleData.getChargePerson());
		et_renyuan.setText(sampleData.getSamplingPerson());

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_delete:// 点击关闭按钮
			dismiss();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	private ClickListenerInterface listener;
	private TextView et_name;
	private TextView et_loacation;
	private TextView et_dialog_time_from;
	private TextView et_people;
	private TextView et_renyuan;

	public void setClickListener(ClickListenerInterface clickListener) {
		this.listener = clickListener;
	}

}
