package com.mqby.mqlibrary.tools;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.mqby.mqlibrary.widget.MyItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MaQiang
 * @time 2018/6/9 0009 21:06
 * @QQ 1033785970
 * @class 控件验证工具类
 * 验证控件文字是否为空 并给予error提示
 */
public class ValidatorTool {

    private Context mContext;
    private boolean isShowDialog = false;//是否弹出提示
    private List<View> viewList = new ArrayList<>();
    private List<String> msgList = new ArrayList<>();
    private List<Boolean> errorList = new ArrayList<>();

    /**
     * 构造方法
     *
     * @param mContext
     */
    public ValidatorTool(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 添加要验证的View和提示文字
     *
     * @param view
     * @param msg
     * @return
     */
    public ValidatorTool addIsEmptyView(View view, String msg) {
        viewList.add(view);
        msgList.add(msg);
        return this;
    }

    //是否为空
    private boolean isEmpty(View view) {
        if (view instanceof EditText) {
            String content = ((EditText) view).getText().toString().trim();
            return StringTool.isEmpty(content);
        } else if (view instanceof TextView) {
            String content = ((TextView) view).getText().toString().trim();
            return StringTool.isEmpty(content);
        } else if (view instanceof AutoCompleteTextView) {
            String content = ((AutoCompleteTextView) view).getText().toString().trim();
            return StringTool.isEmpty(content);
        } else if (view instanceof MyItemView) {
            String content = ((MyItemView) view).getRightText();
            return StringTool.isEmpty(content) || content.equals("请输入") || content.equals("请选择");
        }
        return false;
    }

    //设置错误信息
    private void setError(View view, String msg) {
        if (view instanceof EditText) {
            ((EditText) view).setError(msg);
            view.requestFocus();
        } else if (view instanceof TextView) {
            ((TextView) view).setError(msg);
            view.requestFocus();
        } else if (view instanceof AutoCompleteTextView) {
            ((AutoCompleteTextView) view).setError(msg);
            view.requestFocus();
        } else if (view instanceof MyItemView) {
            EditText editText = ((MyItemView) view).getRightTextView();
            editText.setError(msg);
            view.requestFocus();
        }
    }

    //清除错误信息
    private void setErrorClear(View view) {
        if (view instanceof EditText) {
            ((EditText) view).setError(null, null);
        } else if (view instanceof TextView) {
            ((TextView) view).setError(null, null);
        } else if (view instanceof MyItemView){
            ((MyItemView) view).getRightTextView().setError(null, null);
        }
    }

    private void showDialog(String msg) {
        if (isShowDialog) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("系统消息");
            builder.setMessage(msg);
            builder.create().show();
        }
    }

    public void setIsShowDialog(boolean showDialog) {
        isShowDialog = showDialog;
    }

    /**
     * 开始验证
     *
     * @return
     */
    public boolean validator() {
        errorList.clear();
        //循环验证
        for (int i = 0; i < viewList.size(); i++) {
            View view = viewList.get(i);
            String msg = msgList.get(i);

            boolean isEmpty = isEmpty(view);

            if (isEmpty) {
                setError(view, msg);
            } else {
                setErrorClear(view);
            }

            errorList.add(isEmpty);
        }

        for (int i = 0; i < errorList.size(); i++) {
            Boolean b = errorList.get(i);
            if (b) {
                viewList.get(i).requestFocus();
                showDialog(msgList.get(i));//弹出第一个错误对话框
                return false;
            }
        }
        return true;
    }
}
