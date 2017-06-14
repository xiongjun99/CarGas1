package com.example.cargas.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.cargas.R;
import com.example.cargas.communication.ComInterface;
import com.example.cargas.communication.ComMsg;
import com.example.cargas.fragment.BlackSmokeDetail;
import com.example.cargas.message.MSG;
import com.example.cargas.model.BlackSmokeQueryResponse;
import com.example.cargas.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BlackSmokeShowAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    Context context;
    FragmentManager fm;
    LinearLayout frameLayout;
    List<BlackSmokeQueryResponse> mData = new ArrayList<BlackSmokeQueryResponse>();
    private BlackSmokeQueryResponse showData;


    //默认是false,未选择， 点击变成true,选择状态
    private static HashMap<Integer, Boolean> isSelected = new HashMap<Integer, Boolean>();

    public BlackSmokeShowAdapter(Context context,
                                 List<BlackSmokeQueryResponse> mData, LinearLayout layout,
                                 FragmentManager fm) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.fm = fm;
        frameLayout = layout;
        this.mData = mData;

        //初始化数据
        initData();
    }

    public void setData() {
        initData();
    }

    private void initData() {
        isSelected.clear();
        for (int i = 0; i < mData.size(); i++) {
            BlackSmokeQueryResponse res = mData.get(i);


            if (res.getRegisterStatus().equals("0")) {
                isSelected.put(i, false);//显示的是无，不显示togglebutton
            } else if (res.getRegisterStatus().equals("1")) {

                if (res.getPunishStatus().equals("0")) {
                    isSelected.put(i, false);
                } else if (res.getPunishStatus().equals("1")) {
                    isSelected.put(i, true);//选中状态
                }
            }
        }
    }

    @Override
    public int getCount() {
        // return 10;
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();

            convertView = mInflater.inflate(R.layout.home_show_listview_item,
                    null);
            viewHolder.carNumber = (TextView) convertView
                    .findViewById(R.id.home_listitem_car_number);
            viewHolder.time = (TextView) convertView
                    .findViewById(R.id.home_listitem_time_tv);
            viewHolder.status = (TextView) convertView
                    .findViewById(R.id.home_listitem_status_tv);
            viewHolder.color = (TextView) convertView
                    .findViewById(R.id.home_listitem_color_tv);
            viewHolder.detail = (Button) convertView
                    .findViewById(R.id.home_listitem_detail_bt);
            viewHolder.deal = (TextView) convertView
                    .findViewById(R.id.home_listitem_dealwith_tv);
            viewHolder.toggle = (ToggleButton) convertView
                    .findViewById(R.id.togglebutton);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        showData = mData.get(position);
        viewHolder.carNumber.setText(showData.getCarNo());

        if (showData.getRegisterTime().contains("T")) {
            viewHolder.time.setText(showData.getRegisterTime().replace("T", " "));
        } else {
            viewHolder.time.setText(showData.getRegisterTime());
        }


        viewHolder.color.setText(showData.getCarColor());


        if (showData.getRegisterStatus().equals("0")) {
            viewHolder.toggle.setVisibility(View.GONE);
            viewHolder.deal.setVisibility(View.VISIBLE);
            viewHolder.status.setText("合格");
        } else if (showData.getRegisterStatus().equals("1")) {
            viewHolder.status.setText("不合格");
            viewHolder.toggle.setVisibility(View.VISIBLE);
            viewHolder.deal.setVisibility(View.GONE);
            viewHolder.toggle.setChecked(isSelected.get(position));
            if (isSelected.get(position) == false) {//原来是选中的状态，现在就不能再选了
                viewHolder.toggle.setChecked(false);
                viewHolder.toggle.setClickable(true);
//				viewHolder.toggle.setClickable(false);
//				viewHolder.toggle.setChecked(true);
            } else {
//				viewHolder.toggle.setChecked(false);
//				viewHolder.toggle.setClickable(true);
                viewHolder.toggle.setClickable(false);
                viewHolder.toggle.setChecked(true);
                viewHolder.toggle.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        View layout = View.inflate(context, R.layout.dialog,
                                null);
                        final EditText etListLibraryNote = (EditText) layout.findViewById(R.id.etname);
                        ImageView blacksmoke_photograph_iv = (ImageView) layout.findViewById(R.id.listitem_blacksmoke_photograph_iv);
                        ImageView blacksmoke_photographtwo_iv = (ImageView) layout.findViewById(R.id.listitem_blacksmoke_photographtwo_iv);
                        ImageView blacksmoke_photographthree_iv = (ImageView) layout.findViewById(R.id.listitem_blacksmoke_photographthree_iv);

                        blacksmoke_photograph_iv.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(context, "请上传照片1", Toast.LENGTH_SHORT).show();
                            }
                        });
                        blacksmoke_photographtwo_iv.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(context, "请上传照片2", Toast.LENGTH_SHORT).show();
                            }
                        });
                        blacksmoke_photographthree_iv.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(context, "请上传照片3", Toast.LENGTH_SHORT).show();
                            }
                        });
                        new AlertDialog.Builder(context).setTitle("确定要处罚吗？")
                                .setView(layout)
                                .setPositiveButton("是", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        if (etListLibraryNote.getText().toString().isEmpty()) {
                                            Toast.makeText(context, "请输入罚款金额", Toast.LENGTH_SHORT).show();
                                        }
                                        isSelected.put(position, true);
//								order[position] = true;
                                        ComMsg.BlackSomkePunishReq req = new ComMsg.BlackSomkePunishReq();
                                        req.SessionID = MSG.SessionID;
                                        req.PunishTime = Utils.getCurrentTime();
                                        req.BlackSmokeRegisterID = mData.get(position).getServerId();
                                        ComInterface.BlackSomkePunish(context, req);
                                        viewHolder.toggle.setClickable(false);
                                        notifyDataSetChanged();
                                    }

                                })
                                .setNegativeButton("否", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        isSelected.put(position, false);
//								order[position] = false;
                                        notifyDataSetChanged();
                                    }

                                })
                                .show();

//						if(isSelected.get(position)){//true,原来是选中状态
//							isSelected.put(position, false);
//
//						} else {//false 原始是未选中状态
//							isSelected.put(position, true);
//						}
//						setIsSelected(isSelected);
                    }
                });
            }


//			viewHolder.toggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//				
//				@Override
//				public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
//					
//					if(isChecked){//true,原来是未选中状态
//						new AlertDialog.Builder(context) 
//					 	.setMessage("确定要处罚吗？")
//					 	.setPositiveButton("是", new DialogInterface.OnClickListener() {
//							
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								Utils.showToast(context, "已处罚");
//								ComMsg.BlackSomkePunishReq req = new ComMsg.BlackSomkePunishReq();
//								req.SessionID = MSG.SessionID;
//								req.PunishTime = Utils.getCurrentTime();
//								req.BlackSmokeRegisterID = mData.get(position).getServerId();
//								ComInterface.BlackSomkePunish(context, req);
//								viewHolder.toggle.setClickable(false);
//							}
//						})
//					 	.setNegativeButton("否", new DialogInterface.OnClickListener() {
//							
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								viewHolder.toggle.setChecked(!isChecked);
//							}
//						})
//					 	.show();
//					} 
//				}
//			});


        }

        viewHolder.detail.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                BlackSmokeDetail blackSmokeDetail = new BlackSmokeDetail(context);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", (Serializable) mData.get(position));
                blackSmokeDetail.setArguments(bundle);
                fm.beginTransaction()
                        .replace(R.id.main_framelayout, blackSmokeDetail)
                        .commit();
            }
        });

        return convertView;
    }

    private class ViewHolder {
        TextView carNumber, time, status;
        TextView color, deal;
        Button detail;
        ToggleButton toggle;
    }


    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        BlackSmokeShowAdapter.isSelected = isSelected;
    }

}
