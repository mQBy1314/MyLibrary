package com.mqby.mqlibrary.tools;

import android.app.Application;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * @author MaQiang
 * @time 2018/6/7 0007 16:24
 * @QQ 1033785970
 * @class Toast工具类
 */
public class ToastTool {
    private static boolean isShow = true;//默认显示Toast
    private static Application context;

    /**
     * 在Application中初始化
     *
     * @param application
     */
    public static void init(Application application) {
        context = application;
    }

    /**
     * 全局设置是否可显示Toast
     *
     * @param isShowToast
     */
    public static void setShow(boolean isShowToast) {
        isShow = isShowToast;
    }

    /**
     * 取消Toast显示
     */
    public static void cancelToast(Toast toast) {
        if (null != toast && isShow) {
            toast.cancel();
        }
    }

    /**
     * 短时间显示Toast 2秒
     *
     * @param msg 显示内容
     */
    public static  Toast showShort(CharSequence msg) {
        Toast toast = null;
        if (isShow) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            toast.show();
        }
        return toast;
    }

    /**
     * 短时间显示Toast 2秒
     *
     * @param resId 显示内容
     */
    public static Toast showShort(@StringRes int resId) {
        Toast toast = null;
        if (isShow) {
            toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
            toast.show();
        }
        return toast;
    }

    /**
     * 长时间显示Toast 3.5秒
     *
     * @param msg
     */
    public static Toast showLong(CharSequence msg) {
        Toast toast = null;
        if (isShow) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            toast.show();
        }
        return toast;
    }

    /**
     * 长时间显示Toast 3.5秒
     *
     * @param resId
     */
    public static Toast showLong(@StringRes int resId) {
        Toast toast = null;
        if (isShow) {
            toast = Toast.makeText(context, resId, Toast.LENGTH_LONG);
            toast.show();
        }
        return toast;
    }

    /**
     * 自定义Toast显示时间
     *
     * @param msg
     * @param duration 毫秒
     */
    public static Toast show(CharSequence msg, int duration) {
        Toast toast = null;
        if (isShow) {
            toast = Toast.makeText(context, msg, duration);
            toast.show();
        }
        return toast;
    }

    /**
     * 自定义Toast显示时间
     *
     * @param resId
     * @param duration 毫秒
     */
    public static Toast show(@StringRes int resId, int duration) {
        Toast toast = null;
        if (isShow) {
            toast = Toast.makeText(context, resId, duration);
            toast.show();
        }
        return toast;
    }

    /**
     * 自定义Toast显示位置
     *
     * @param msg
     * @param duration 毫秒
     * @param gravity
     * @param xOffset
     * @param yOffset
     * @return
     */
    public static Toast showLocation(CharSequence msg, int duration, int gravity, int xOffset, int yOffset) {
        Toast toast = null;
        if (isShow) {
            toast = Toast.makeText(context, msg, duration);
            toast.setGravity(gravity, xOffset, yOffset);
            toast.show();
        }
        return toast;
    }

    /**
     * 自定义Toast显示位置
     *
     * @param resId
     * @param duration 毫秒
     * @param gravity
     * @param xOffset
     * @param yOffset
     * @return
     */
    public static Toast showLocation(@StringRes int resId, int duration, int gravity, int xOffset, int yOffset) {
        Toast toast = null;
        if (isShow) {
            toast = Toast.makeText(context, resId, duration);
            toast.setGravity(gravity, xOffset, yOffset);
            toast.show();
        }
        return toast;
    }

    /**
     * 带图片的Toast  上面图片 下面文字
     *
     * @param msg
     * @param duration
     * @param imgId    图片资源id
     * @return
     */
    public static Toast showToastAddImage(CharSequence msg, int duration, @DrawableRes int imgId) {
        Toast toast = null;
        if (isShow) {
            toast = Toast.makeText(context, msg, duration);
            LinearLayout toastView = (LinearLayout) toast.getView();
            ImageView imageView = new ImageView(toastView.getContext());
            imageView.setImageResource(imgId);
            toastView.addView(imageView, 0);
            toast.show();
        }
        return toast;
    }

    /**
     * 带图片的Toast  上面图片 下面文字
     *
     * @param resId
     * @param duration
     * @param imgId    图片资源id
     * @return
     */
    public static Toast showToastAddImage(@StringRes int resId, int duration, @DrawableRes int imgId) {
        Toast toast = null;
        if (isShow) {
            toast = Toast.makeText(context, resId, duration);
            LinearLayout toastView = (LinearLayout) toast.getView();
            ImageView imageView = new ImageView(toastView.getContext());
            imageView.setImageResource(imgId);
            toastView.addView(imageView, 0);
            toast.show();
        }
        return toast;
    }

    /**
     * 自定义Toast的View
     *
     * @param duration
     * @param view
     */
    public static Toast customToastView(int duration, View view) {
        Toast toast = null;
        if (isShow) {
            toast = new Toast(context);
            toast.setDuration(duration);
            if (null == view) {
                toast.setView(view);
            }
            toast.show();
        }
        return toast;
    }


    /**
     * 完全自定义Toast
     *
     * @param duration
     * @param view
     * @param isGravity        是否设定Gravity  false后面三项不生效  true生效
     * @param gravity
     * @param xOffset
     * @param yOffset
     * @param isMargin         是否设定Margin  false后面两项不生效  true生效
     * @param horizontalMargin
     * @param verticalMargin
     * @return
     */
    public static Toast customToastAll(int duration, View view, boolean isGravity, int gravity, int xOffset,
                                int yOffset, boolean isMargin, float horizontalMargin, float verticalMargin) {
        Toast toast = null;
        if (isShow) {
            toast = new Toast(context);
            toast.setDuration(duration);
            toast.setView(view);
            if (isGravity)
                toast.setGravity(gravity, xOffset, yOffset);
            if (isMargin)
                toast.setMargin(horizontalMargin, verticalMargin);
            toast.show();
        }
        return toast;
    }


}
