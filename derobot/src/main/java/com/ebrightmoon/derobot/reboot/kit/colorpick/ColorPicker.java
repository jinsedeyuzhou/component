package com.ebrightmoon.derobot.reboot.kit.colorpick;

import android.content.Context;
import android.content.Intent;

import com.ebrightmoon.derobot.R;
import com.ebrightmoon.derobot.reboot.config.ColorPickConfig;
import com.ebrightmoon.derobot.reboot.constant.BundleKey;
import com.ebrightmoon.derobot.reboot.constant.FragmentIndex;
import com.ebrightmoon.derobot.reboot.kit.Category;
import com.ebrightmoon.derobot.reboot.kit.IKit;
import com.ebrightmoon.derobot.reboot.ui.TranslucentActivity;

/**
 *  on 2018/9/13.
 */

public class ColorPicker implements IKit {
    private static final String TAG = "ColorPicker";

    @Override
    public int getCategory() {
        return Category.UI;
    }

    @Override
    public int getName() {
        return R.string.dk_kit_color_picker;
    }

    @Override
    public int getIcon() {
        return R.drawable.dk_color_picker;
    }

    @Override
    public void onClick(Context context) {
        Intent intent = new Intent(context, TranslucentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(BundleKey.FRAGMENT_INDEX, FragmentIndex.FRAGMENT_COLOR_PICKER_SETTING);
        context.startActivity(intent);
    }

    @Override
    public void onAppInit(Context context) {
        ColorPickConfig.setColorPickOpen(context, false);
    }
}