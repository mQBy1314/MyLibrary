package com.mqby.mqlibrary.tools;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author MaQiang
 * @time 2018/6/9 0009 21:01
 * @QQ 1033785970
 * @class SharedPreferences 常量类
 */
public class SPConstant {


    public static final String FIRST_APP = "first_app";//是否首次进入app 1是
    public static final String AUTO_LOGIN = "auto_login";//是否自动登录 1是
    public static final String IS_SAVE = "is_save";//是否保存账号密码 1是
    public static final String USER_TAG = "user_tag";//用户标识 (uid或token) 没数据代表未登录
    public static final String USER_NAME = "user_name";//用户账号
    public static final String USER_NIKNAME = "user_nikname";//用户名称
    public static final String USER_PASSWORD = "user_password";//用户密码
    public static final String ALL_USERS = "all_users";//所有用户

    /**
     * 给常量注册  未注册的常量无法直接使用
     */
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({FIRST_APP, AUTO_LOGIN, IS_SAVE, USER_TAG, USER_NAME, USER_PASSWORD, ALL_USERS,USER_NIKNAME})
    @interface Constant {
    }
}
