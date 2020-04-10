package com.ebrightmoon.common.widget.toast;

import android.view.View;

/**
 * 作者：create by  Administrator on 2019/3/7
 * 邮箱：
 */
public interface IToast {
    IToast setGravity(int gravity, int xOffset, int yOffset);
    IToast setDuration(long durationMillis);
    IToast setView(View view);
    IToast setMargin(float horizontalMargin, float verticalMargin);
    IToast setText(String text);
    void show();
    void cancel();
}
