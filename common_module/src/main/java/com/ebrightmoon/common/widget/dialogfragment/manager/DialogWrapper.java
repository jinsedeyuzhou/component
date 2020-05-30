package com.ebrightmoon.common.widget.dialogfragment.manager;


import com.ebrightmoon.common.widget.dialogfragment.SystemDialog;

/**
 * 管理多个dialog 按照dialog的优先级依次弹出
 * Created by mq on 2018/9/16 下午9:44
 *
 */

public class DialogWrapper {

    private SystemDialog.Builder dialog;//统一管理dialog的弹出顺序

    public DialogWrapper(SystemDialog.Builder dialog) {
        this.dialog = dialog;
    }

    public SystemDialog.Builder getDialog() {
        return dialog;
    }

    public void setDialog(SystemDialog.Builder dialog) {
        this.dialog = dialog;
    }

}
