package com.mqby.mqlibrary.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.mqby.mqlibrary.tools.KeyBoardTool;

/**
 * @author MaQiang
 * @time 2018/7/3 0003 12:39
 * @class 描述
 */
public class MyEditText  extends AppCompatEditText {

    private int mType = MyItemView.TYPE_TEXT;

    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMyType(int type) {
        mType = type;
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        if (mType == MyItemView.TYPE_EDIT)
//            return super.dispatchTouchEvent(event);
//        else
//            return false;
//    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mType == MyItemView.TYPE_TEXT)
            return false;
        else {
            setFocusable(true);
            setFocusableInTouchMode(true);
            KeyBoardTool.openKeybord(this,getContext());
            requestFocus();
            return super.onTouchEvent(event);
        }
    }
}