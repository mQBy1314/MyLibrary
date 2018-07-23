package com.mqby.mqlibrary.widget;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.mqby.mqlibrary.tools.LogTool;

import java.util.Calendar;
import java.util.Date;

/**
 * @author MaQiang
 * @time 2018/6/14 0014 9:53
 * @class 日期时间选择器(dialog形式)
 * setDateTimeListener()    设定选择完日期后选择时间
 * setDateListener()    设定只选择日期
 * setTimeListener()    设定只选择时间
 * 当两个都设定时,先弹出日期选择器 后弹出时间选择器
 */
public class DateTimeDialog {

    private Context mContext;

    private DateTimeCallback mDateTimeCallback;//日期时间接口
    private DateCallback mDateCallback;//日期接口
    private TimeCallback mTimeCallback;//时间接口


    private DatePickerDialog mDateDialog;//日期对话框
    private TimePickerDialog mTimeDialog;//时间对话框
    private int tag;//标记用于区分调用者
    private int mYear;
    private int mMonth;
    private int mDayOfMonth;
    private int mHourOfDay;
    private int mMinute;
    private Calendar mCalendar;

    public DateTimeDialog(Context context) {
        mContext = context;
        mCalendar = Calendar.getInstance();
        mCalendar.setTime(new Date());
    }

    /**
     * 设置日期和时间事件回调
     *
     * @param dateTimeCallback
     * @return
     */
    public DateTimeDialog setDateTimeListener(@NonNull DateTimeCallback dateTimeCallback) {
        this.mDateTimeCallback = dateTimeCallback;
        initTimeDialog();//初始化时间对话框
        initDateDialog();//初始化日期对话框
        return this;
    }

    /**
     * 设置日期事件回调
     *
     * @param dateCallback
     * @return
     */
    public DateTimeDialog setDateListener(@NonNull DateCallback dateCallback) {
        this.mDateCallback = dateCallback;
        initDateDialog();//初始化日期对话框
        return this;
    }

    /**
     * 设置时间事件回调
     *
     * @param timeCallback
     * @return
     */
    public DateTimeDialog setTimeListener(@NonNull TimeCallback timeCallback) {
        this.mTimeCallback = timeCallback;
        initTimeDialog();//初始化时间对话框
        return this;
    }

    public DateTimeDialog showDialog(int tag) {
        this.tag = tag;
        if (null != mDateDialog)
            mDateDialog.show();
        else if (null != mTimeDialog)
            mTimeDialog.show();
        return this;
    }

    //创建时间对话框
    private void initTimeDialog() {
        if (null == mTimeDialog) {
            //时间对话框
            TimePickerDialog.OnTimeSetListener timeListener =
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timerPicker,
                                              int hourOfDay, int minute) {
                            mHourOfDay = hourOfDay;
                            mMinute = minute;
                            if (null != mTimeCallback) {
                                mTimeCallback.onTimeClickListener(tag, hourOfDay, minute);
                                LogTool.i("时间选择hourOfDay:" + hourOfDay + ",minute:" + minute);
                            }
                            if (null != mDateTimeCallback) {
                                mDateTimeCallback.onDateTimeClickListener(tag, mYear, mMonth, mDayOfMonth,
                                        mHourOfDay, mMinute);
                                LogTool.i("日期时间选择year:" + mYear + ",month:" + mMonth +
                                        ",dayOfMonth:" + mDayOfMonth + ",hourOfDay:" + hourOfDay +
                                        ",minute:" + minute);
                            }
                        }
                    };

            //是否为二十四制
            mTimeDialog = new TimePickerDialog(mContext,
                    AlertDialog.THEME_HOLO_LIGHT, timeListener,
                    mCalendar.get(Calendar.HOUR_OF_DAY),
                    mCalendar.get(Calendar.MINUTE),
                    true);
        }
    }

    //创建日期对话框
    private void initDateDialog() {
        if (null == mDateDialog) {
            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    mYear = year;
                    mMonth = month;
                    mDayOfMonth = dayOfMonth;
                    if (null != mDateCallback) {
                        mDateCallback.onDateClickListener(tag, year, month, dayOfMonth);
                        LogTool.i("日期选择year:" + year + ",month:" + month + ",dayOfMonth:" + dayOfMonth);
                    }
                    if (null != mTimeDialog)
                        mTimeDialog.show();//弹出时间对话框
                }
            };
            mDateDialog = new DatePickerDialog(mContext,
                    AlertDialog.THEME_HOLO_LIGHT,
                    dateSetListener,
                    mCalendar.get(Calendar.YEAR),
                    mCalendar.get(Calendar.MONTH),
                    mCalendar.get(Calendar.DAY_OF_MONTH));
        }
    }


    /**
     * 日期 时间 回调接口
     */
    public interface DateTimeCallback {
        /**
         * @param tag 标识
         */
        void onDateTimeClickListener(int tag, int year, int month, int dayOfMonth, int hourOfDay, int minute);
    }

    /**
     * 日期 回调接口
     */
    public interface DateCallback {
        /**
         * @param tag 标识
         */
        void onDateClickListener(int tag, int year, int month, int dayOfMonth);
    }

    /**
     * 时间 回调接口
     */
    public interface TimeCallback {
        /**
         * @param tag 标识
         */
        void onTimeClickListener(int tag, int hourOfDay, int minute);
    }

}
