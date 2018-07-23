package com.mqby.mqlibrary.tools;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author MaQiang
 * @time 2018/6/9 0009 20:48
 * @QQ 1033785970
 * @class SharedPreferences工具类
 * 用来存储轻量数据
 */
public class SPTool {

    private static Application context;
    /**
     * 保存在手机里面的文件名
     */
    private static String FILE_NAME;

    //初始化方式
    public static void init(Application application, String fileName) {
        context = application;
        FILE_NAME = fileName;
    }

    /**
     * 保存数据
     * 我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public static void save(@SPConstant.Constant String key, Object object) {
        saveByFileName(FILE_NAME, key, object);
    }

    public static void saveByFileName(String fileName, String key, Object object) {
        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) object);
        }

        editor.commit();
    }


    /**
     * 查看已存的数据
     * 我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(@SPConstant.Constant String key, Object defaultObject) {
        return getByFileName(FILE_NAME, key, defaultObject);
    }

    public static Object getByFileName(String fileName, String key, Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 移除指定数据
     */
    public static void remove(@SPConstant.Constant String key) {
        removeByFileName(FILE_NAME, key);
    }

    /**
     * 移除指定数据
     */
    public static void removeByFileName(String fileName, @SPConstant.Constant String key) {
        SharedPreferences sp = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 移除所有数据
     */
    public static void removeAll() {
        removeAllByFileName(FILE_NAME);
    }

    /**
     * 移除所有数据
     */
    public static void removeAllByFileName(String fileName) {
        SharedPreferences sp = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().commit();
    }

}
