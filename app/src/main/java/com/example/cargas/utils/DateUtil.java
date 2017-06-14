package com.example.cargas.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DateUtil {

    public static Date stringToDate(String dateString) {
        ParsePosition position = new ParsePosition(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dateValue = simpleDateFormat.parse(dateString, position);
        return dateValue;
    }
    public static ArrayList DateList(ArrayList<String> timeList) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd HH:mm");
        ArrayList<Date> dateList = new ArrayList<Date>();
        /**
         * 字符串转时间
         */
        for (String str : timeList) {
            try {
                dateList.add(format.parse(str));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        /**
         * 打印时间
         */
        System.out.println("排序前：");
        for (Date d : dateList) {
            System.out.println(format.format(d));
        }
        /**
         * 冒泡排序
         * 如果不喜欢可以使用其他的排序方法
         */
        Date tempDate = null;
        for (int i = dateList.size() - 1; i > 0; --i) {
            for (int j = 0; j < i; ++j) {
                /**
                 * 从大到小的排序
                 */
                if (dateList.get(j + 1).after(dateList.get(j))) {
                    tempDate = dateList.get(j);
                    dateList.set(j, dateList.get(j + 1));
                    dateList.set(j + 1, tempDate);
                } else {
                    /**
                     * 从小到大
                     */
//                  tempDate = dateList.get(j);
//                  dateList.set(j, dateList.get(j+1));
//                  dateList.set(j+1, tempDate);
                }
            }
        }
        /**
         * 打印排序之后的时间
         */
        ArrayList arrayList = new ArrayList();
        for (Date d : dateList) {
            arrayList.add(format.format(d));
            System.out.println(format.format(d));
        }
        return arrayList;
    }
}  