package com.ebrightmoon.derobot.reboot.kit;

import android.content.Context;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

/**
 * Created by zhangweida on 2018/6/22.
 */

public interface IKit {
    int getCategory();

    @StringRes
    int getName();

    @DrawableRes
    int getIcon();

    void onClick(Context context);

    void onAppInit(Context context);
}
