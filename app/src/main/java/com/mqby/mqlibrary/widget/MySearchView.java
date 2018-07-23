package com.mqby.mqlibrary.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mqby.mqlibrary.R;

/**
 * @author MaQiang
 * @time 2018/6/13 0013 12:44
 * @class 搜索View样式
 */
public class MySearchView extends RelativeLayout {

    private RelativeLayout root;
    private LinearLayout linearClick;

    public MySearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public MySearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MySearchView(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.search_layout, this);
        root = (RelativeLayout) findViewById(R.id.root);
        linearClick = (LinearLayout) findViewById(R.id.linear_click);

        parseStyle(context,attrs);
        setVisibility(GONE);
    }

    private void parseStyle(Context context, AttributeSet attrs) {
        if (attrs != null) {
//            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EaseTitleBar);
//            ......
//            ta.recycle();
        }
    }

    /**
     * 整个按钮的点击事件
     * @param listener
     * @return
     */
    public MySearchView setOnSearchClickListener(@NonNull OnClickListener listener){
        linearClick.setOnClickListener(listener);
        return this;
    }

}
