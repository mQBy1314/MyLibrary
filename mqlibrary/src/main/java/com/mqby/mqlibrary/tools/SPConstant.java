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

    public static final String SP_NAME = "gjlsj";
    public static final String SP_USER_LIST = "gjlsj_user";
    public static final String SP_USER_AVATAR_LIST = "gjlsj_user_avatar";

    public static final String FIRST_APP = "first_app";//是否首次进入app 1是
    public static final String AUTO_LOGIN = "auto_login";//是否自动登录 1是
    public static final String IS_SAVE = "is_save";//是否保存账号密码 1是
    public static final String USER_TAG = "user_tag";//用户标识 (userId) 没数据代表未登录
    public static final String LOGIN_NAME = "login_name";//登录账号
    public static final String LOGIN_PASSWORD = "login_password";//登录密码
    public static final String USER_NIKNAME = "user_nikname";//用户名称
    public static final String ALL_USERS = "all_users";//所有用户

    public static final String USER_ID = "user_id";
    public static final String TOKEN = "token";

    public static final String NAVI = "navi";

    /**
     * 给常量注册  未注册的常量无法直接使用
     */
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({FIRST_APP, AUTO_LOGIN, IS_SAVE, USER_TAG, LOGIN_NAME, LOGIN_PASSWORD, ALL_USERS, USER_NIKNAME, USER_ID, TOKEN, NAVI})
    @interface Constant {
    }
}
