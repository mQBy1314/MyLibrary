package com.mqby.mqlibrary.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mqby.mqlibrary.R;
import com.mqby.mqlibrary.tools.StringTool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author MaQiang
 * @time 2018/6/12 0012 11:32
 * @QQ 1033785970
 * @class 以popupwindow形式弹出菜单
 */
public class PopMenu {
    private RecyclerView mRecyclerView;
    private PopupWindow mPopupWindow;
    private PopAdapter mAdapter;
    private Context context;
    private List<String> itemList;
    private View showView;
    private CallBack callBack;

    public PopMenu(Context context) {
        this.context = context;
        itemList = new ArrayList<>();
        mAdapter = new PopAdapter(itemList);
        mAdapter.setEmptyView(View.inflate(context, R.layout.popmenu_empty_layout, null));
    }

    // 设置菜单项点击监听器
    public PopMenu setOnItemClickListener(CallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    // 批量添加菜单项
    public PopMenu addItems(Collection<String> items) {
        itemList.clear();
        if (items != null)
            for (String s : items)
                itemList.add(s);
        return this;
    }


    // 单个添加菜单项
    public PopMenu addItem(String item) {
        itemList.add(item);
        return this;
    }

    // 批量添加菜单项
    public PopMenu clear() {
        itemList.clear();
        return this;
    }

    // 下拉式 弹出 pop菜单 parent 右下角
    public PopMenu showAsDropDown(View parent) {
        initPopMenu();
        showView = parent;
        mAdapter.notifyDataSetChanged();
//        int[] position = new int[2];
//        parent.getLocationOnScreen(position);
//        mPopupWindow.showAtLocation(context.findViewById(android.R.id.content), Gravity.START | Gravity.TOP, position[0] + parent.getWidth(), position[1]);
        if (parent.getWidth() > 0)
            mPopupWindow.setWidth(parent.getWidth());
        mPopupWindow.showAsDropDown(parent);
        return this;
    }

    private void initPopMenu() {
        if (null == mPopupWindow) {
            View view = LayoutInflater.from(context).inflate(R.layout.popmenu_layout, null);
            //listView
            mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            mRecyclerView.setAdapter(mAdapter);

            //popupwindow
            mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            // 使其聚集
            mPopupWindow.setFocusable(true);
            // 设置允许在外点击消失
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        }
    }

    // 隐藏菜单
    public PopMenu dismiss() {
        mPopupWindow.dismiss();
        return this;
    }

    public interface CallBack {
        void OnItemClickListener(View v, int position, String result);
    }

    // 适配器
    private final class PopAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        private String changeStr;

        public PopAdapter(@Nullable List<String> data) {
            super(R.layout.popmenu_item, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final String item) {
            helper.setText(R.id.textView, item);
            if (helper.getLayoutPosition() == itemList.size() - 1) {
                helper.setVisible(R.id.line, false);
            } else {
                helper.setVisible(R.id.line, true);
            }

            //处理关键字颜色变化
            if (!StringTool.isEmpty(changeStr) && !StringTool.isEmpty(item) && item.contains(changeStr)) {
                int index = item.indexOf(changeStr);
                int len = changeStr.length();
                Spanned temp = Html.fromHtml(item.substring(0, index) +
                        "<font color=#278d39>" + item.substring(index, index + len) +
                        "</font>" + item.substring(index + len, item.length()));
                helper.setText(R.id.textView, temp);
            } else {
                helper.setText(R.id.textView, item);
            }


            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopupWindow.dismiss();
                    if (showView != null) {
                        if (showView instanceof TextView) {
                            ((TextView) showView).setText(item);
                        } else if (showView instanceof EditText) {
                            ((EditText) showView).setText(item);
                        } else if (showView instanceof MyItemView) {
                            ((MyItemView) showView).setRightText(item);
                        }
                    }
                    if (callBack != null) {
                        callBack.OnItemClickListener(showView, helper.getLayoutPosition(), item);
                    }
                }
            });
        }

        //这个方法很重要，editText监听文本变化需要用到
        public void changeText(String textStr) {
            this.changeStr = textStr; //别忘了，
            notifyDataSetChanged();
        }


    }
}
