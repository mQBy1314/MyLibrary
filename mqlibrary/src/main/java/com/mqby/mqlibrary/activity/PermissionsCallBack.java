package com.mqby.mqlibrary.activity;

/**
 * @author MaQiang
 * @time 2018/6/12 0012 10:39
 * @QQ 1033785970
 * @class 权限请求接口回调
 */
public interface PermissionsCallBack {
    void onAdoptPermissions();//权限通过

    void onRefusePermissions();//拒绝权限
}
