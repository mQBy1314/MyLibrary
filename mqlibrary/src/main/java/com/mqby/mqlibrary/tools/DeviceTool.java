package com.mqby.mqlibrary.tools;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.mqby.mqlibrary.activity.PermissionsActivity;
import com.mqby.mqlibrary.activity.PermissionsCallBack;

import java.util.List;

/**
 * @author MaQiang
 * @time 2018/6/11 0011 16:29
 * @QQ 1033785970
 * @class 描述
 */
public class DeviceTool {

    private boolean realDevice = false;

    private static class LazyHolder {
        private static final DeviceTool INSTANCE = new DeviceTool();
    }

    private DeviceTool() {
    }

    public static final DeviceTool getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * 获取屏幕尺寸
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public Point getScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            return new Point(display.getWidth(), display.getHeight());
        } else {
            Point point = new Point();
            display.getSize(point);
            return point;
        }
    }

    /**
     * 获取屏幕的宽度
     *
     * @param context
     * @return
     */
    public int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = wm.getDefaultDisplay();
        int width = defaultDisplay.getWidth();
        return width;
    }

    /**
     * 获取屏幕的高度
     *
     * @param context
     * @return
     */
    public int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = wm.getDefaultDisplay();
        int height = defaultDisplay.getHeight();

        return height;
    }

    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    public int getStatusHeight(Context context) {
        int statusHeight = -1;
        //使用反射，可能会出现类找不到的异常ClassNotFoundException
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            String status_bar_height = clazz.getField("status_bar_height").get(object).toString();
            int height = Integer.parseInt(status_bar_height);
            //转化成px返回
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取当前屏幕截图，包括状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap getSnapshot(Activity activity) {
        Window window = activity.getWindow();
        View view = window.getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        int screenWidth = getScreenWidth(activity);
        int screenHeight = getScreenHeight(activity);
        Bitmap bp;
        bp = Bitmap.createBitmap(bitmap, 0, 0, screenWidth, screenHeight);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取应用程序名称
     *
     * @param context
     * @return
     */
    public String getAppName(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            //这种方式是可以的
            //String name = applicationInfo.loadLabel(pm).toString();
            int labelRes = applicationInfo.labelRes;
            String name = context.getResources().getString(labelRes);
            return name;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取应用程序版本名
     *
     * @param context
     * @return
     */
    public String getVersionName(Context context) {
        PackageInfo pi = getPackageInfo(context);
        if (pi != null)
            return pi.versionName;
        else
            return "";
    }

    /**
     * 获取应用程序版本号
     *
     * @param context
     * @return
     */
    public int getVersionCode(Context context) {
        PackageInfo pi = getPackageInfo(context);
        if (pi != null)
            return pi.versionCode;
        else
            return 0;
    }

    private PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    /**
     * 判断程序是否在前台运行
     *
     * @param context
     * @return
     */
    public boolean isAppOnForeground(Context context) {
        ActivityManager activityManager
                = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        String packageName = context.getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses
                = activityManager.getRunningAppProcesses();
        if (appProcesses == null) return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName) &&
                    appProcess.importance ==
                            ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断是否是真机
     *
     * @param context
     * @return
     */
    public boolean isRealDevice(final Activity context) {
        final TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        PermissionsActivity.startActivityForResult(context, new PermissionsCallBack() {
            @Override
            public void onAdoptPermissions() {
                //已授权
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    String deviceId = telephonyManager.getDeviceId();
                    // 如果 运行的 是一个 模拟器
                    realDevice = !(deviceId == null || deviceId.trim().length() == 0
                            || deviceId.matches("0+"));
                }
            }

            @Override
            public void onRefusePermissions() {
                //权限被拒绝
                LogTool.i("未获取权限");
            }
        }, Manifest.permission.READ_PHONE_STATE);
        return realDevice;
    }
}
