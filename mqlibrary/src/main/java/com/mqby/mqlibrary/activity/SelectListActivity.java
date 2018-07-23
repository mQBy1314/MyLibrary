package com.mqby.mqlibrary.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.mqby.mqlibrary.R;
import com.mqby.mqlibrary.adapter.SelectListAdapter;
import com.mqby.mqlibrary.entity.SelectBean;
import com.mqby.mqlibrary.entity.SelectCallBack;
import com.mqby.mqlibrary.tools.ActivityJump;
import com.mqby.mqlibrary.tools.KeyBoardTool;
import com.mqby.mqlibrary.tools.StringTool;
import com.mqby.mqlibrary.tools.ToastTool;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MaQiang
 * @date 2018/6/27 0027
 * @class SelectListActivity
 * 列表选择器
 * 1.单选或多选
 * 2.具有模糊查询 高亮显示搜索结果
 */
public class SelectListActivity extends AppCompatActivity implements TextWatcher {

    private static SelectCallBack mSelectCallBack;

    private ImageView mBtnBack;
    private EditText mEtSearch;
    private ImageView mBtnClear;
    private TextView mBtnEnter;
    private RecyclerView mRecyclerView;

    private ArrayList<SelectBean> list = new ArrayList<>();
    private ArrayList<SelectBean> newList = new ArrayList<>();
    private int tag;
    private boolean isDouble;
    private SelectListAdapter mAdapter;
    /**
     * Activity入口
     * 使用方式
     * SelectListActivity.startActivity()
     * @param context  上下文
     * @param tag      标识
     * @param isDouble 是否多选 (默认单选)
     * @param list     数据源(规定统一数据源,需自行转换)
     * @param callBack 结果回调
     */
    public static void startActivity(Context context, int tag, boolean isDouble, ArrayList<SelectBean> list, SelectCallBack callBack) {
        mSelectCallBack = callBack;
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("list", list);
        bundle.putInt("tag", tag);
        bundle.putBoolean("isDouble", isDouble);
        ActivityJump.startActivity(context, SelectListActivity.class, bundle);
    }

    public static void startActivity(Context context, int tag, ArrayList<SelectBean> list, SelectCallBack callBack) {
        startActivity(context, tag, false, list, callBack);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_list);
        //设置沉浸式标题栏颜色
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ArrayList<SelectBean> arrayList = bundle.getParcelableArrayList("list");
            list.addAll(arrayList);
            newList.addAll(arrayList);
            tag = bundle.getInt("tag");
            isDouble = bundle.getBoolean("isDouble");
        }
        initView();

    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new SelectListAdapter(newList, list);
        mAdapter.selectDouble(isDouble);
        mRecyclerView.setAdapter(mAdapter);
        mBtnBack = (ImageView) findViewById(R.id.btn_back);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mEtSearch = (EditText) findViewById(R.id.et_search);
        mEtSearch.addTextChangedListener(this);
        mBtnClear = (ImageView) findViewById(R.id.btn_clear);
        mBtnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newList.clear();
                newList.addAll(list);
                mAdapter.changeText("");
                mEtSearch.setText("");
            }
        });
        mBtnEnter = (TextView) findViewById(R.id.btn_enter);
        mBtnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<SelectBean> selectList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).select)
                        selectList.add(list.get(i));
                }
                if (mSelectCallBack != null) {
                    if (selectList.size() > 0) {
                        mSelectCallBack.onSelectListener(tag, list, selectList);
                        KeyBoardTool.closeKeybord(mEtSearch, SelectListActivity.this);
                        finish();
                    } else {
                        ToastTool.showShort("您还未选择选项");
                    }
                }
            }
        });
        mBtnClear.setVisibility(View.GONE);

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!StringTool.isEmpty(s.toString())) {
            newList.clear();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).name.indexOf(s.toString().trim()) != -1)
                    newList.add(list.get(i));
            }
            mBtnClear.setVisibility(View.VISIBLE);
        } else {
            mBtnClear.setVisibility(View.GONE);
        }
        mAdapter.changeText(s.toString().trim());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

}
