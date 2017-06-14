package com.example.cargas.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cargas.R;

public class Password extends Fragment implements OnClickListener{

	private EditText oldPass;
	private EditText newPass;
	private EditText newPassAgain;
	private Button submit;
	
	private Context context;

	public Password(Context context){
		this.context = context;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.password, null);
		
		initView(view);
		return view;
	}

	private void initView(View view) {
		oldPass = (EditText) view.findViewById(R.id.old_pass);
		newPass = (EditText) view.findViewById(R.id.new_pass);
		newPassAgain = (EditText) view.findViewById(R.id.new_pass_again);
		
		submit = (Button) view.findViewById(R.id.tijiao);
		submit.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.tijiao:
			String sOld = oldPass.getText().toString();
			String sNew = newPass.getText().toString();
			String sNewAgain = newPassAgain.getText().toString();
			
			if (TextUtils.isEmpty(sOld) || TextUtils.isEmpty(sNew) || TextUtils.isEmpty(sNewAgain)){
				Toast.makeText(context, "请填写完整", 0).show();
			}
			
			if (!sNew.equals(sNewAgain) ){
				Toast.makeText(context, "两次新密码不一致，请重新输入", 0).show();
			} else{
				//网络请求
			}
			
			break;

		default:
			break;
		}
	}
}
