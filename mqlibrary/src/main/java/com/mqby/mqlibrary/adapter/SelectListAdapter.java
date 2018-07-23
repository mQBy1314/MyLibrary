package com.mqby.mqlibrary.adapter;

import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mqby.mqlibrary.R;
import com.mqby.mqlibrary.entity.SelectBean;
import com.mqby.mqlibrary.tools.StringTool;

import java.util.List;

/**
 * @author MaQiang
 * @time 2018/6/27 0027 10:50
 * @class 选择列表适配器
 */
public class SelectListAdapter extends BaseQuickAdapter<SelectBean, BaseViewHolder> {
    private String changeStr;
    private boolean isSelDouble;//是否多选
    private List<SelectBean> newList;
    private List<SelectBean> oldList;

    public SelectListAdapter(@Nullable List<SelectBean> data, List<SelectBean> oldList) {
        super(R.layout.select_list_item_layout, data);
        this.newList = data;
        this.oldList = oldList;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final SelectBean item) {

        helper.setImageResource(R.id.iv_sel, item.select ? R.drawable.ic_cb_select : R.drawable.ic_cb);

        //处理关键字颜色变化
        if (!StringTool.isEmpty(changeStr) && !StringTool.isEmpty(item.name) && item.name.contains(changeStr)) {
            int index = item.name.indexOf(changeStr);
            int len = changeStr.length();
            Spanned temp = Html.fromHtml(item.name.substring(0, index) +
                    "<font color=#278d39>" + item.name.substring(index, index + len) +
                    "</font>" + item.name.substring(index + len, item.name.length()));
            helper.setText(R.id.textView, temp);
        } else {
            helper.setText(R.id.textView, item.name);
        }

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelDouble) {
                    item.select = !item.select;
                    for (int i = 0; i < oldList.size(); i++) {
                        if (oldList.get(i).id.equals(item.id)) {
                            oldList.get(i).select = item.select;
                            break;
                        }
                    }
                    helper.setImageResource(R.id.iv_sel, item.select ? R.drawable.ic_cb_select : R.drawable.ic_cb);
                } else {
                    for (int i = 0; i < newList.size(); i++) {
                        if (newList.get(i).id.equals(item.id)) {
                            newList.get(i).select = true;
                        } else {
                            newList.get(i).select = false;
                        }
                    }
                    for (int i = 0; i < oldList.size(); i++) {
                        if (oldList.get(i).id.equals(item.id)) {
                            oldList.get(i).select = true;
                        } else {
                            oldList.get(i).select = false;
                        }
                    }
                    notifyDataSetChanged();
                }
            }
        });
    }

    //这个方法很重要，editText监听文本变化需要用到
    public SelectListAdapter changeText(String textStr) {
        this.changeStr = textStr; //别忘了，
        notifyDataSetChanged();
        return this;
    }

    //单选或多选
    public SelectListAdapter selectDouble(boolean isSelDouble) {
        this.isSelDouble = isSelDouble;
        return this;
    }


}