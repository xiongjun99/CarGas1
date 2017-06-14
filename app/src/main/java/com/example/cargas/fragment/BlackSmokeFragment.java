package com.example.cargas.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cargas.R;
import com.example.cargas.adapter.BlackSmokeShowAdapter;
import com.example.cargas.communication.ComInterface;
import com.example.cargas.communication.ComMsg;
import com.example.cargas.database.DB;
import com.example.cargas.message.MSG;
import com.example.cargas.message.MessageHandlerManager;
import com.example.cargas.model.BlackSmokeQueryResponse;
import com.example.cargas.utils.DateUtil;
import com.example.cargas.view.DoubleDatePickerDialog2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class BlackSmokeFragment extends Fragment {

    private static final String TAG = "BlackSmokeFragment";
    private TextView mRegister;// 登记
    private ListView mList;
    Context context;
    private BlackSmokeShowAdapter adapter;
    List<BlackSmokeQueryResponse> mData = new ArrayList<BlackSmokeQueryResponse>();
    private BlackSmokeRegiseter blackRegister;
    private EditText startTime;
    private EditText endTime;

    public BlackSmokeFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.black_smoke_show, null);
        blackRegister = new BlackSmokeRegiseter(context);
        initViews(view);
        initData();

        register_message();
        return view;
    }

    // 初始化数据
    private void initData() {


        mData.clear();
        // 查询数据库
        HashMap<String, String> query = DB.instance(context).query(
                "SELECT * FROM dt_blackSmokeRegister;");

        if (query != null) {

            for (int i = 0; i < Integer.parseInt((query.get("records_num"))); i++) {
                BlackSmokeQueryResponse blackData1 = new BlackSmokeQueryResponse(
                        query.get("BlackSmokeRegisterID_" + i),
                        query.get("RegisterUser_" + i),
                        query.get("RegisterTime_" + i).replace("T", " "),
                        query.get("CarNo_" + i), query.get("CarColor_" + i),
                        query.get("Address_" + i), query.get("FieldEvaluation_"
                        + i), query.get("RegisterStatus_" + i),
                        query.get("PunishTime_" + i), query.get("PunishUser_"
                        + i), query.get("PunishStatus_" + i),
                        query.get("Remark_" + i), query.get("ServerId_" + i));
                mData.add(blackData1);
                Collections.sort(mData, new Comparator<BlackSmokeQueryResponse>() {
                    /**
                     * @param lhs
                     * @param rhs
                     * @return an integer < 0 if lhs is less than rhs, 0 if they are
                     * equal, and > 0 if lhs is greater than rhs,比较数据大小时,这里比的是时间
                     */
                    public int compare(BlackSmokeQueryResponse lhs, BlackSmokeQueryResponse rhs) {
                        Date date1 = DateUtil.stringToDate(lhs.getRegisterTime());
                        Date date2 = DateUtil.stringToDate(rhs.getRegisterTime());
                        if (date1 != null && date2 != null) {
                            // 对日期字段进行升序，如果欲降序可采用after方法
                            if (date1.after(date2)) {

                                return 1;
                            }
                        }
                        return -1;
                    }
                });
                for (int j = 0; j < mData.size(); j++) {
                    System.out.println("AAAAAAAAAAAAAAAA" + mData.get(j).getRegisterTime());
                }
            }
        }
        adapter.setData();
        adapter.notifyDataSetChanged();
        // ////////////////////////////////////////////////////////////
//		 HashMap< String, String>_query_result = DB.instance(context)
//		 .query("select * from dt_blackSmokeRegister;");
//		 for (int i = 0; i < Integer.parseInt(_query_result
//		 .get("records_num")); i++) {
//		 Log.v("Att",
//		 "after:" + _query_result.get("BlackSmokeRegisterID_" + i) + ":"
//		 + _query_result.get("RegisterUser_" + i) + ":"
//		 + _query_result.get("RegisterTime_" + i) + ":"
//		 + _query_result.get("CarNo_" + i) + ":"
//		 + _query_result.get("CarColor_" + i) + ":"
//		 + _query_result.get("FieldEvaluation_" + i) + ":"
//		 + _query_result.get("RegisterStatus_" + i) + ":"
//		 + _query_result.get("PunishTime_" + i));
//		 }
        // ///////////////////////////////////////////////////////////
    }

    // 初始化布局
    private void initViews(View view) {
        mRegister = (TextView) view.findViewById(R.id.register);
        mList = (ListView) view.findViewById(R.id.home_listview);
        final LinearLayout frameLayout = (LinearLayout) view
                .findViewById(R.id.black_smoke_show);

        adapter = new BlackSmokeShowAdapter(context, mData, frameLayout,
                getFragmentManager());
        mList.setAdapter(adapter);

        mRegister.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO 登记 跳转到登记页面
                // frameLayout.setVisibility(View.GONE);
                FragmentManager fm = getFragmentManager();
                // fm.beginTransaction()
                // .replace(
                // R.id.main_framelayout,
                // ((MainActivity) getActivity())
                // .getmBlackRegister()).commit();

                fm.beginTransaction()
                        .replace(R.id.main_framelayout, blackRegister).commit();

            }
        });

        startTime = (EditText) view.findViewById(R.id.black_starttime_et);
        endTime = (EditText) view.findViewById(R.id.black_endtime_et);

        startTime.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Calendar c = Calendar.getInstance();

                    // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
                    new DoubleDatePickerDialog2(context, 0,
                            new DoubleDatePickerDialog2.OnDateSetListener() {


                                @Override
                                public void onDateSet(
                                        DatePicker startDatePicker,
                                        int startYear, int startMonthOfYear,
                                        int startDayOfMonth,
                                        DatePicker endDatePicker, int endYear,
                                        int endMonthOfYear, int endDayOfMonth) {
                                    String mstartTime = String.format("%d-%d-%d",
                                            startYear, startMonthOfYear + 1,
                                            startDayOfMonth);
                                    String mendTime = String.format("%d-%d-%d",
                                            endYear, endMonthOfYear + 1,
                                            endDayOfMonth);
                                    startTime.setText(mstartTime);
                                    endTime.setText(mendTime);

                                    //然后从服务器上拉去数据
                                    //冒黑烟查询接口
                                    ComMsg.BlackSmokeQueryReq blackReq = new
                                            ComMsg.BlackSmokeQueryReq();
                                    blackReq.SessionID = MSG.SessionID;
                                    blackReq.StartTime = mstartTime;
                                    blackReq.EndTime = mendTime;

                                    ComInterface.BlackSmokeQuery(context, blackReq);

                                }

                            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                            .get(Calendar.DATE), false).show();

                }
                return true;
            }
        });

        endTime.setOnTouchListener(new OnTouchListener() {

//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				// TODO Auto-generated method stub
//				return true;
//			}

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Calendar c = Calendar.getInstance();

                    // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
                    new DoubleDatePickerDialog2(context, 0,
                            new DoubleDatePickerDialog2.OnDateSetListener() {


                                @Override
                                public void onDateSet(
                                        DatePicker startDatePicker,
                                        int startYear, int startMonthOfYear,
                                        int startDayOfMonth,
                                        DatePicker endDatePicker, int endYear,
                                        int endMonthOfYear, int endDayOfMonth) {
                                    String mstartTime = String.format("%d-%d-%d",
                                            startYear, startMonthOfYear + 1,
                                            startDayOfMonth);
                                    String mendTime = String.format("%d-%d-%d",
                                            endYear, endMonthOfYear + 1,
                                            endDayOfMonth);
                                    startTime.setText(mstartTime);
                                    endTime.setText(mendTime);

                                    //然后从服务器上拉去数据
                                    //冒黑烟查询接口
                                    ComMsg.BlackSmokeQueryReq blackReq = new
                                            ComMsg.BlackSmokeQueryReq();
                                    blackReq.SessionID = MSG.SessionID;
                                    blackReq.StartTime = mstartTime;
                                    blackReq.EndTime = mendTime;

                                    ComInterface.BlackSmokeQuery(context, blackReq);

                                }

                            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                            .get(Calendar.DATE), false).show();

                }
                return true;
            }
        });

    }

    public BlackSmokeRegiseter getmBlackRegister() {

        return blackRegister;
    }

    // ////////////////////////////////////////////////////
    //思想：其实这里的MessageHandler是没什么作用的，如果说要有作用的话，那唯一的作用就是在当前页面退出的时候
    //将handler置为了null

    public static Handler _message_handler = null;// 界面消息处理句柄

    // 注册地图处理消息
    private void register_message() {
        _message_handler = new MainMessageHandler(Looper.getMainLooper());

        //根据类名和what来标志arraylist中的handler
        //注册就是将handler添加到ArrayList中，然后可以在其他地方通过handler来发送消息
        MessageHandlerManager.get_instance().register(_message_handler,
                MSG.BLACK_SMOKE_SHOW_SUCCESS, TAG);
        MessageHandlerManager.get_instance().register(_message_handler,
                MSG.BLACK_SMOKE_SHOW_FAIL, TAG);
    }

    // 注销地图处理消息
    private void unregister_message() {
        //将handler从ArrayList中移除
        MessageHandlerManager.get_instance().unregister(MSG.BLACK_SMOKE_QUERY_SUCCESS,
                TAG);
        MessageHandlerManager.get_instance()
                .unregister(MSG.BLACK_SMOKE_QUERY_FAIL, TAG);
    }

    // 地图消息处理句柄
    private class MainMessageHandler extends Handler {
        MainMessageHandler(Looper looper) {//与指定的looper关联，而不是默认的looper
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG.BLACK_SMOKE_QUERY_SUCCESS:

                    initData();
                    break;

                case MSG.BLACK_SMOKE_QUERY_FAIL:
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
