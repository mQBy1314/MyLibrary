package com.mqby.mqlibrary.tools;

import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author MaQiang
 * @time 2018/6/11 0011 16:18
 * @QQ 1033785970
 * @class EditText工具类
 */
public class ETTool {


    private static SparseArray<OnRightListener> listenerMap;

    public interface OnRightListener {
        void onRight(EditText et, MotionEvent event);
    }

    /**
     * 给EditText添加右侧图片点击事件
     *
     * @param et
     * @param rightListener
     */
    public static void addOnRightClickListiner(EditText et, OnRightListener rightListener) {

        if (null != et && null != rightListener) {

            //将id和接口添加到集合中便于后面使用
            if (null == listenerMap)
                listenerMap = new SparseArray<>();
            listenerMap.put(et.getId(), rightListener);

            //根据触摸位置判断是否点击在右侧图片上
            et.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    EditText et = (EditText) v;
                    // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                    Drawable drawable = et.getCompoundDrawables()[2];
                    //如果右边没有图片，不再处理
                    if (drawable == null)
                        return false;
                    //如果不是按下事件，不再处理
                    if (event.getAction() != MotionEvent.ACTION_UP)
                        return false;
                    //点击了右侧
                    if (event.getX() > et.getWidth() - et.getPaddingRight()
                            - drawable.getIntrinsicWidth()) {
                        //从集合中取出对应的接口 并调用回调方法
                        OnRightListener listener = listenerMap.get(et.getId());
                        if (null != listener)
                            listener.onRight(et, event);
                    }
                    return false;
                }
            });
        }
    }

    /**
     * 获取控件中的文字
     * 仅支持TextView的子类
     *
     * @param view
     * @return
     */
    public static String getData(View view) {
        if (view != null) {
            if (view instanceof EditText) {
                return ((EditText) view).getText().toString().trim();
            } else if (view instanceof TextView) {
                return ((TextView) view).getText().toString().trim();
            }
        }
        return "";
    }

    /**
     * 给文字控件赋值
     * 仅支持TextView的子类
     *
     * @param view
     * @param data
     */
    public static void setData(View view, String data) {
        if (view != null) {
            if (view instanceof EditText) {
                EditText et = (EditText) view;
                et.setText(data);
            } else if (view instanceof TextView) {
                TextView et = (TextView) view;
                et.setText(data);
            }
        }
    }

    //编辑框 是否 可点击、可获取焦点
    public static void setFocus(EditText et, boolean isFocusable) {
        if (et != null)
            if (isFocusable) {
                et.setCursorVisible(true);
                et.setTextIsSelectable(true);
                et.setFocusableInTouchMode(true);
                et.setFocusable(true);
                et.requestFocus();
            } else {
                et.setCursorVisible(false);//不显示光标
                et.setTextIsSelectable(false);//不可编辑状态下文字不可选
                et.setFocusable(false);
                et.setFocusableInTouchMode(false);
            }
    }
}
