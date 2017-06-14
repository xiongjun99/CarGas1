package com.example.cargas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cargas.R;

import java.util.List;

/**
 * Created by Administrator on 2017/6/8 0008.
 */
public class HistoricalRecordListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> data;
    private static LayoutInflater inflater = null;

    public HistoricalRecordListViewAdapter(Context context, List<String> list) {
        mContext = context;
        data = list;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            convertView = mInflater.inflate(R.layout.list_item_photochoice, null);

            viewHolder.date_tv = (TextView) convertView.findViewById(R.id.historical_date_tv);
            viewHolder.date_tv.setText(data.get(position));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    private final class ViewHolder {
        TextView date_tv;
    }
}
