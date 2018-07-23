package com.mqby.mqlibrary.entity;

import java.util.List;

/**
 * @author MaQiang
 * @time 2018/6/27 0027 10:41
 * @class 选择器回调接口
 */
public interface SelectCallBack {
    /**
     * 选择后数据回调方法
     *
     * @param tag        标识
     * @param list       数据源(下次进来传送这个有选择标识)
     * @param selectList 选择的数据
     */
     void onSelectListener(int tag, List<SelectBean> list, List<SelectBean> selectList);
}
