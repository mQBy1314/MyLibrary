package com.mqby.mqlibrary.tools;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

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

    public static Map<String, String> getAllByFileName(String fileName) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return (Map<String, String>) sp.getAll();
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

    public static void saveUserId(long userId) {
        save(SPConstant.USER_ID, userId);
    }

    public static long getUserId() {
        long userId = -1L;
        try {
            userId = (long) get(SPConstant.USER_ID, -1L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userId;
    }

    public static void saveToken(String token) {
        save(SPConstant.TOKEN, token);
    }

    public static String getToken() {
        return (String) get(SPConstant.TOKEN, "-1");
    }

    public static void saveLoginName(String loginName) {
        save(SPConstant.LOGIN_NAME, loginName);
    }

    public static String getLoginName() {
        return (String) get(SPConstant.LOGIN_NAME, "");
    }

    public static void saveLoginPsw(String loginPsw) {
        save(SPConstant.LOGIN_PASSWORD, loginPsw);
    }

    public static String getLoginPsw() {
        return (String) get(SPConstant.LOGIN_PASSWORD, "");
    }

    public static void saveNickName(String nickName) {
        save(SPConstant.USER_NIKNAME, nickName);
    }

    public static String getNickName() {
        return (String) get(SPConstant.USER_NIKNAME, "-1");
    }

    public static void saveUserTag(String tag) {
        save(SPConstant.USER_TAG, tag);
    }

    public static String getUserTag() {
        return (String) get(SPConstant.USER_TAG, "-1");
    }

    /**
     * 当前用户打开用车申请时,需要判断是否显示导航的按钮,用该方法保存用户是否有此权限到sp
     */
    public static void saveNavi() {
        save(SPConstant.NAVI, SPConstant.NAVI);
    }

    /**
     * 从SP中获取,当前用户是否有显示导航按钮的权限
     *
     * @return
     */
    public static String getNavi() {
        return (String) get(SPConstant.NAVI, "");
    }

    public static void saveCurrentUserInfo(Object object) {
        saveObj(context, "CurrentUserInfo", object);
    }

    public static Object getCurrentUserInfo() {
        return getObj(context, "CurrentUserInfo");
    }

    /**
     * 将对象进行base64编码后保存到SharePref中
     *
     * @param context
     * @param key
     * @param object
     */
    public static void saveObj(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            // 将对象的转为base64码
            String objBase64 = new String(Base64.encode(baos.toByteArray(),
                    Base64.DEFAULT));

            sp.edit().putString(key, objBase64).commit();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将SharePref中经过base64编码的对象读取出来
     *
     * @param context
     * @param key
     * @param
     * @return
     */
    public static Object getObj(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        String objBase64 = sp.getString(key, null);
        if (TextUtils.isEmpty(objBase64)) {
            return null;
        }

        // 对Base64格式的字符串进行解码
        byte[] base64Bytes = Base64.decode(objBase64.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);

        ObjectInputStream ois;
        Object obj = null;
        try {
            ois = new ObjectInputStream(bais);
            obj = (Object) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
