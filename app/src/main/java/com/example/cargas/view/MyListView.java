package com.example.cargas.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;

/**
 * create：${lufoei} on 2016-12-6 16:26
 * <p>
 * email：luofei@ctdcn.com
 */

public class MyListView extends ListView {

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    //设置不滚动
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        Log.e("到这里来", String.valueOf(expandSpec));
        super.onMeasure(widthMeasureSpec,expandSpec);
    }

}
