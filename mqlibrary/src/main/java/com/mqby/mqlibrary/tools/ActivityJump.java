package com.mqby.mqlibrary.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * @author MaQiang
 * @time 2018/6/11 0011 22:07
 * @QQ 1033785970
 * @class Activity跳转工具类
 */
public class ActivityJump {

    //启动页面的常量 用于区分页面类型
    private static final String ADD = "addType";//新增
    private static final String DETAIL = "detailType";//详情
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ADD,DETAIL})
    @interface StartType{}

    /**
     * 普通跳转
     *
     * @param context
     * @param cls
     */
    public static void startActivity(@NonNull Context context, Class<?> cls) {
        context.startActivity(new Intent().setClass(context, cls));
    }

    /**
     * 带数据普通跳转
     *
     * @param context
     * @param bundle
     * @param cls
     */
    public static void startActivity(@NonNull Context context, @NonNull Class<?> cls, Bundle bundle) {
        context.startActivity(new Intent().setClass(context, cls).putExtras(bundle));
    }

    /**
     * 带回传的跳转
     *
     * @param context
     * @param cls
     * @param requestCode
     */
    public static void startActivityForResult(@NonNull Activity context, @NonNull Class<?> cls, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 带数据带回传的跳转
     *
     * @param context
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public static void startActivityForResult(@NonNull Activity context, @NonNull Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 跳转新栈
     *
     * @param context
     * @param cls
     * @param bundle
     */
    public static void startActivityNewTask(@NonNull Context context, @NonNull Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        intent.putExtras(bundle);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
