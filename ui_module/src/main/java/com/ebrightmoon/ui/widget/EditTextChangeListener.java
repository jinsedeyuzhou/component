package com.ebrightmoon.ui.widget;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;


import com.ebrightmoon.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2018/1/30
 * @Author Mr.WEI
 * @Description 统一控制按钮显示
 **/
public class EditTextChangeListener<T> implements TextWatcher {
    private List<EditText> edits = new ArrayList<>();
    private Boolean isAllHas = true;
    private Button mBt;

    public EditTextChangeListener(Button bt, T... args) {
        if (args[0] instanceof EditText) {
            for (int i = 0; i < args.length; i++) {
                edits.add((EditText)args[i]);
            }
            mBt = bt;
            initEditUi();
        }


    }

    public void initEditUi() {
        for (EditText edit : edits) {
            if (edit.getText().toString().isEmpty()) {
                isAllHas = false;
            }
        }
        if (isAllHas) {
            mBt.setBackgroundResource(R.drawable.shape_bt_enable_true);
            mBt.setEnabled(true);
        } else {
            mBt.setBackgroundResource(R.drawable.shape_bt_enable_false);
            mBt.setEnabled(false);
        }
    }


    /**
     * 在特殊场景下调用
     * @param isAllHas
     */
    public void initEditUi(boolean isAllHas) {
        for (EditText edit : edits) {
            if (edit.getText().toString().isEmpty()) {
                isAllHas = false;
            }
        }
        if (isAllHas) {
            mBt.setBackgroundResource(R.drawable.shape_bt_enable_true);
            mBt.setEnabled(true);
        } else {
            mBt.setBackgroundResource(R.drawable.shape_bt_enable_false);
            mBt.setEnabled(false);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        isAllHas = true;
        initEditUi();

    }

    public void clear()
    {
        edits.clear();
    }

    public void add(List<EditText> args)
    {   edits.clear();
        edits.addAll(args);
        isAllHas = true;
        initEditUi();
    }
}
