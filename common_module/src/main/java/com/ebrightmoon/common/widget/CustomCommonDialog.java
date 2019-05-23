package com.ebrightmoon.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Time: 2019/5/23
 * Author:wyy
 * Description:
 */
public class CustomCommonDialog extends Dialog {

    public CustomCommonDialog(@NonNull Context context) {
        super(context);
    }

    public CustomCommonDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomCommonDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
