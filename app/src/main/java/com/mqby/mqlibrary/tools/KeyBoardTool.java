package com.mqby.mqlibrary.tools;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @author MaQiang
 * @time 2018/6/11 0011 18:31
 * @QQ 1033785970
 * @class 软键盘工具类, 需要事件触发
 */
public class KeyBoardTool {

    /**
     * 打开软键盘
     *
     * @param editText
     * @param context
     */
    public static void openKeybord(EditText editText, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    /**
     * 关闭软键盘
     *
     * @param context
     */
    public static void closeKeybord2( Context context) {
        InputMethodManager inputMgr = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMgr.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);

    }

    /**
     * 关闭软键盘
     *
     * @param editText
     * @param context
     */
    public static void closeKeybord(EditText editText, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * 关闭软键盘
     *
     * @param context
     */
    public static void closeKeybord(Context context) {
        if (context instanceof Activity) {
            View focusView = ((Activity) context).getCurrentFocus();
            if (focusView != null) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
            }
        }

    }

}
