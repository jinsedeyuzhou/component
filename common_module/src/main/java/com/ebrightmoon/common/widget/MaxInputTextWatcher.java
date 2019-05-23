package com.ebrightmoon.common.widget;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by wyy on 2018/2/24.
 * 限制Edittext 输入字符的数量
 *   et_message_content.addTextChangedListener(new MaxInputTextWatcher(mContext, et_message_content,
 *                 140, new MaxInputTextWatcher.OnTextChangeListener() {
 *             @Override
 *             public void countListener(int count) {
 *                 if (count>70) {
 *                     mMessageCount=2;
 *                     bt_send.setBackgroundResource(R.drawable.shape_bt_enable_true);
 *                     bt_send.setEnabled(true);
 *                 }else if (count>0&&count<=70){
 *                     bt_send.setBackgroundResource(R.drawable.shape_bt_enable_true);
 *                     bt_send.setEnabled(true);
 *                     mMessageCount=1;
 *                 }else if (count==0){
 *                     bt_send.setBackgroundResource(R.drawable.shape_bt_enable_false);
 *                     bt_send.setEnabled(false);
 *                 }
 *                 tv_content_number.setText(count+"/140 共"+mMessageCount+"条短信");
 *             }
 *         }));
 */

public class MaxInputTextWatcher implements TextWatcher {

    private Context context;

    private EditText editText = null;
    private int maxLength = 0;

    public MaxInputTextWatcher(Context context, EditText editText, int maxLength, OnTextChangeListener onTextChangeListener)
    {
        this.context = context;
        this.editText = editText;
        this.maxLength = maxLength;
        this.onTextChangeListener=onTextChangeListener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Editable editable = editText.getText();
        int length = editable.length();//原字符串长度
        if (onTextChangeListener!=null)
            onTextChangeListener.countListener(length);
        if (length > maxLength) {//如果原字符串长度大于最大长度
            int selectEndIndex = Selection.getSelectionEnd(editable);//getSelectionEnd：获取光标结束的索引值
            String str = editable.toString();//旧字符串
            String newStr = str.substring(0, maxLength);//截取新字符串
            editText.setText(newStr);
            editable = editText.getText();
            int newLength = editable.length();//新字符串长度
            if (selectEndIndex > newLength) {//如果光标结束的索引值超过新字符串长度
                selectEndIndex = editable.length();
//                Toast.makeText(context, "最多只能输入" + selectEndIndex + "个字哦", Toast.LENGTH_SHORT).show();
            }
            Selection.setSelection(editable, selectEndIndex);//设置新光标所在的位置

        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public interface OnTextChangeListener
    {
        void countListener(int count);
    }
    public OnTextChangeListener onTextChangeListener;

    public void setOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
        this.onTextChangeListener = onTextChangeListener;
    }
}