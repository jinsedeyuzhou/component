package com.ebrightmoon.derobot.reboot.ui.dialog;

/**
 *  on 2019/4/12
 */
public interface DialogListener {
    boolean onPositive();

    boolean onNegative();

    void onCancel();
}