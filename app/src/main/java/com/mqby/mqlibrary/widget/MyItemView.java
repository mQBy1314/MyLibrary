package com.mqby.mqlibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mqby.mqlibrary.R;
import com.mqby.mqlibrary.tools.StringTool;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author MaQiang
 * @time 2018/6/13 0013 12:44
 * @class 自定义单项控件
 */
public class MyItemView extends RelativeLayout {

    public static final int TYPE_TEXT = 100;
    public static final int TYPE_EDIT = 101;
    private OnClickListener mListener;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(value = {TYPE_TEXT, TYPE_EDIT})
    public @interface Constant {
    }

    private LinearLayout root;
    private ImageView ivLeft, ivRight;
    private TextView tvLeft;
    private MyEditText tvRight;
    private View mTopLineView, mBottomLineView;

    private int mType;

    public MyItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public MyItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyItemView(Context context) {
        super(context);
        init(context, null);
    }

    private void init(final Context context, AttributeSet attrs) {
        setBackgroundResource(R.color.white);
        LayoutInflater.from(context).inflate(R.layout.myitem_layout, this);
        root = (LinearLayout) findViewById(R.id.root);
        ivLeft = (ImageView) findViewById(R.id.iv_left);
        tvLeft = (TextView) findViewById(R.id.tv_left);
        tvRight = (MyEditText) findViewById(R.id.tv_right);
        ivRight = (ImageView) findViewById(R.id.iv_right);
//        tvRight.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mListener != null)
//                    mListener.onClick(MyItemView.this);
//                if (mType == TYPE_EDIT) {
//                    KeyBoardTool.openKeybord(tvRight, context);
//                }
//            }
//        });
        mTopLineView = createLine(context, false);
        mBottomLineView = createLine(context, true);
        addView(mTopLineView);
        addView(mBottomLineView);
        parseStyle(context, attrs);
    }

    private void parseStyle(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyItemView);
            String leftText = ta.getString(R.styleable.MyItemView_left_Text);
            setLeftText(leftText);

            String rightText = ta.getString(R.styleable.MyItemView_right_Text);
            setRightText(rightText);

            String rightHintText = ta.getString(R.styleable.MyItemView_right_HintText);
            setRightHint(rightHintText);

            Drawable leftImage = ta.getDrawable(R.styleable.MyItemView_left_Image);
            if (null != leftImage) {
                setLeftImageDrawable(leftImage);
            }

            Drawable rightImage = ta.getDrawable(R.styleable.MyItemView_right_Image);
            if (null != rightImage) {
                setRightImageDrawable(rightImage);
            }

            boolean topLine = ta.getBoolean(R.styleable.MyItemView_top_line, false);
            mTopLineView.setVisibility(topLine ? VISIBLE : GONE);

            boolean bottomLine = ta.getBoolean(R.styleable.MyItemView_bottom_line, false);
            mBottomLineView.setVisibility(bottomLine ? VISIBLE : GONE);

            int rightTextType = ta.getInt(R.styleable.MyItemView_right_text_type, TYPE_TEXT);
            setRightTextType(rightTextType);

            int inputType = ta.getInt(R.styleable.MyItemView_android_inputType, EditorInfo.TYPE_CLASS_TEXT);
            tvRight.setInputType(inputType);

            ta.recycle();
        }
    }

    public MyEditText getRightTextView() {
        return tvRight;
    }

    public TextView getLeftTextView() {
        return tvLeft;
    }

    /**
     * 创建上下两层线
     *
     * @param context
     * @param isBottom 是否在底部
     * @return
     */
    private View createLine(Context context, boolean isBottom) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
        if (isBottom)
            params.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.root);
        View view = new View(context);
        view.setLayoutParams(params);
        view.setBackgroundResource(R.color.line);
        view.setVisibility(GONE);
        return view;
    }

    /**
     * 整个按钮的点击事件
     *
     * @param listener
     * @return
     */
    public MyItemView setOnItemClickListener(@NonNull OnClickListener listener) {
        if (listener != null) {
            mListener = listener;
        }
        setOnClickListener(listener);
        return this;
    }

    /**
     * 设置左侧图片点击事件
     */
    public MyItemView setOnLeftImageClickListener(@NonNull OnClickListener listener) {
        ivLeft.setOnClickListener(listener);
        return this;
    }

    /**
     * 设置左侧图片
     *
     * @param resId
     * @return
     */
    public MyItemView setLeftImageRes(@DrawableRes int resId) {
        ivLeft.setVisibility(VISIBLE);
        ivLeft.setImageResource(resId);
        return this;
    }

    /**
     * 设置左侧图片
     *
     * @param drawable
     * @return
     */
    public MyItemView setLeftImageDrawable(@Nullable Drawable drawable) {
        ivLeft.setVisibility(VISIBLE);
        ivLeft.setImageDrawable(drawable);
        return this;
    }

    /**
     * 设置右侧图片
     *
     * @param resId
     * @return
     */
    public MyItemView setRightImageRes(@DrawableRes int resId) {
        ivRight.setVisibility(VISIBLE);
        ivRight.setImageResource(resId);
        return this;
    }

    /**
     * 设置右侧图片
     *
     * @param drawable
     * @return
     */
    public MyItemView setRightImageDrawable(@Nullable Drawable drawable) {
        ivRight.setVisibility(VISIBLE);
        ivRight.setImageDrawable(drawable);
        return this;
    }

    /**
     * 设置左侧文字
     *
     * @param text
     * @return
     */
    public MyItemView setLeftText(String text) {
        tvLeft.setText(text);
        if (!StringTool.isEmpty(text))
            tvLeft.setError(null, null);
        return this;
    }

    /**
     * 设置右侧文字
     *
     * @param text
     * @return
     */
    public MyItemView setRightText(String text) {
        tvRight.setText(text);
        if (!StringTool.isEmpty(text))
            tvRight.setError(null, null);
        return this;
    }

    /**
     * 设置右侧提示文字
     *
     * @param text
     * @return
     */
    public MyItemView setRightHint(String text) {
        tvRight.setHint(text);
        return this;
    }

    /**
     * 设置右侧文字类型
     *
     * @param type
     * @return
     */
    public MyItemView setRightTextType(@Constant int type) {
//        mType = type;
        if (type == TYPE_TEXT) {
            tvRight.setFocusable(false);
        } else if (type == TYPE_EDIT) {
            tvRight.setFocusable(true);
        }
        tvRight.setMyType(type);
        tvRight.requestLayout();
        return this;
    }

    /**
     * 添加右侧输入监听事件
     *
     * @param watcher
     * @return
     */
    public MyItemView setRightTextChangedListener(TextWatcher watcher) {
        tvRight.addTextChangedListener(watcher);
        return this;
    }

    /**
     * 移除右侧输入监听事件
     *
     * @param watcher
     * @return
     */
    public MyItemView removeRightTextChangedListener(TextWatcher watcher) {
        tvRight.removeTextChangedListener(watcher);
        return this;
    }

    /**
     * 获取右侧文字
     *
     * @return
     */
    public String getRightText() {
        return tvRight.getText().toString().trim();
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
        if (l != null) {
            mListener = l;
        }
    }

    /**
     * 隐藏/显示左侧图标
     *
     * @param visibility
     * @return
     */
    public MyItemView setLeftImageVisibility(int visibility) {
        ivLeft.setVisibility(visibility);
        return this;
    }

    /**
     * 隐藏/显示右侧图标
     *
     * @param visibility
     * @return
     */
    public MyItemView setRightImageVisibility(int visibility) {
        ivRight.setVisibility(visibility);
        return this;
    }

    /**
     * 隐藏/显示顶部线
     *
     * @param visibility
     * @return
     */
    public MyItemView setTopLineVisibility(int visibility) {
        mTopLineView.setVisibility(visibility);
        return this;
    }

    /**
     * 隐藏/显示底部线
     *
     * @param visibility
     * @return
     */
    public MyItemView setBottomLineVisibility(int visibility) {
        mTopLineView.setVisibility(visibility);
        return this;
    }

}
