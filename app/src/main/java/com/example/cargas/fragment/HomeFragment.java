package com.example.cargas.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cargas.R;
import com.example.cargas.adapter.HistoricalRecordListViewAdapter;
import com.example.cargas.adapter.SpinerAdapter;
import com.example.cargas.adapter.SpinerAdapter.IOnItemSelectListener;
import com.example.cargas.communication.ComInterface;
import com.example.cargas.communication.ComMsg;
import com.example.cargas.message.MSG;
import com.example.cargas.message.MessageHandlerManager;
import com.example.cargas.utils.Utils;
import com.example.cargas.view.SpinerPopWindow;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnClickListener,
        IOnItemSelectListener, AdapterView.OnItemClickListener {

    private static final String TAG = "HomeFragment";

    private Context context;
    private EditText etCarNumber;
    private TextView tvFlag;
    private TextView tvCarNumber;
    private TextView tvCarColor;
    private TextView tvDate;
    private TextView type;
    private Button query;

    // 下来框
    private List<String> mListType = new ArrayList<String>(); // 类型列表
    private TextView mTView;
    private SpinerAdapter mAdapter;
    private SpinerPopWindow mSpinerPopWindow;
    private LinearLayout arrowDown;
    private String mCarColor;// 车牌颜色


    private ListView HistoricalRecord_lv;
    private HistoricalRecordListViewAdapter historicalrecordListViewadapter;

    public HomeFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, null);
        initView(view);

        // 注册消息
        register_message();
        return view;
    }

    private void initView(View view) {
        etCarNumber = (EditText) view.findViewById(R.id.et_carnumber);
        tvFlag = (TextView) view.findViewById(R.id.tv_flag);
        type = (TextView) view.findViewById(R.id.tv_type);
        tvCarNumber = (TextView) view.findViewById(R.id.tv_carnumber);

        tvCarColor = (TextView) view.findViewById(R.id.tv_color);
        tvDate = (TextView) view.findViewById(R.id.tv_valid_date);
        query = (Button) view.findViewById(R.id.bt_query);

        Spinner spinner = (Spinner) view.findViewById(R.id.home_spinner);
        final String str[] = getResources().getStringArray(R.array.array_name);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                R.layout.myspinner, str);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                // Toast.makeText(context, str[position], 0).show();
                mCarColor = str[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        // initSpinner(view);

        // 点击查询按钮获取车辆信息
        query.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String carNumber = etCarNumber.getText().toString().trim();// 去掉首尾的空格
                String carColor = mCarColor;

                if (TextUtils.isEmpty(carNumber) || TextUtils.isEmpty(carColor)) {
                    Toast.makeText(context, "内容不能为空", Toast.LENGTH_SHORT).show();
                } else {

                    ComMsg.ObtainVehicleInformationReq req = new ComMsg.ObtainVehicleInformationReq();
                    req.CarColor = mCarColor;
                    req.CarNo = carNumber;
                    // 网络请求
                    ComInterface.ObtainVehicleInformation(context, req);
                }

            }
        });

        // 测试
        etCarNumber.setText("鄂ANS965");

        HistoricalRecord_lv = (ListView) view.findViewById(R.id.historicalrecord_lv);
        historicalrecordListViewadapter = new HistoricalRecordListViewAdapter(getActivity(), getListViewData());
        HistoricalRecord_lv.setAdapter(historicalrecordListViewadapter);
        HistoricalRecord_lv.setOnItemClickListener(this);
    }

    private List<String> getListViewData() {

        List<String> data = new ArrayList<String>();
        data.add("2017.03.06");
        data.add("2017.04.06");
        data.add("2017.05.06");

        return data;
    }

    // 设置PopWindow
    private void showSpinWindow() {
        Log.e("", "showSpinWindow");
        mSpinerPopWindow.setWidth(mTView.getWidth());
        mSpinerPopWindow.showAsDropDown(mTView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_dropdown:
                showSpinWindow();
                break;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.gaochun.adapter.SpinerAdapter.IOnItemSelectListener#onItemClick(int)
     */
    public void onItemClick(int pos) {
        // TODO Auto-generated method stub
        if (pos >= 0 && pos <= mListType.size()) {
            String value = mListType.get(pos);
            mTView.setText(value.toString());
            mCarColor = value.toString();
        }
    }

    // ////////////////////////////////////////////////////
    public static Handler _message_handler = null;// 界面消息处理句柄

    // 注册地图处理消息
    private void register_message() {
        _message_handler = new MainMessageHandler(Looper.getMainLooper());

        MessageHandlerManager.get_instance().register(_message_handler,
                MSG.HOME_SUCCESS, TAG);
        MessageHandlerManager.get_instance().register(_message_handler,
                MSG.HOME_FAIL, TAG);
    }

    // 注销地图处理消息
    private void unregister_message() {
        MessageHandlerManager.get_instance().unregister(MSG.HOME_SUCCESS, TAG);
        MessageHandlerManager.get_instance().unregister(MSG.HOME_FAIL, TAG);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    // 地图消息处理句柄
    private class MainMessageHandler extends Handler {
        MainMessageHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG.HOME_SUCCESS:

                    // 登录返回的消息
                    ComMsg.ObtainVehicleInformationRes res = (ComMsg.ObtainVehicleInformationRes) (msg.obj);

                    if (res.Remark.equals("操作成功")) {
                        // 查询成功之后显示数据
                        tvFlag.setText(res.mark_type);
                        type.setText(res.fuel_type);// 类型
                        tvCarNumber.setText(res.CarNo);
                        tvCarColor.setText(res.CarColor);

                        if (res.mark_release_time.contains("T")) {
                            tvDate.setText(res.mark_release_time.substring(0,
                                    res.mark_release_time.indexOf("T")));
                        }

                    } else {
                        Utils.showToast(context, res.Remark);
                        tvFlag.setText("");
                        type.setText("");// 类型
                        tvCarNumber.setText("");
                        tvCarColor.setText("");
                        tvDate.setText("");
                    }

                    break;

                case MSG.HOME_FAIL:
                    Utils.showToast(context, "服务器连接异常");
                    break;
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();

        unregister_message();

    }

}
