package com.ebrightmoon.derobot.reboot.kit.viewcheck;

import android.content.Context;

import com.ebrightmoon.derobot.R;
import com.ebrightmoon.derobot.reboot.config.ViewCheckConfig;
import com.ebrightmoon.derobot.reboot.constant.PageTag;
import com.ebrightmoon.derobot.reboot.kit.Category;
import com.ebrightmoon.derobot.reboot.kit.IKit;
import com.ebrightmoon.derobot.reboot.ui.base.FloatPageManager;
import com.ebrightmoon.derobot.reboot.ui.base.PageIntent;

/**
 *  on 2018/11/20.
 */

public class ViewChecker implements IKit {
    @Override
    public int getCategory() {
        return Category.UI;
    }

    @Override
    public int getName() {
        return R.string.dk_kit_view_check;
    }

    @Override
    public int getIcon() {
        return R.drawable.dk_view_check;
    }

    @Override
    public void onClick(Context context) {
        PageIntent intent = new PageIntent(ViewCheckFloatPage.class);
        intent.tag = PageTag.PAGE_VIEW_CHECK;
        intent.mode = PageIntent.MODE_SINGLE_INSTANCE;
        FloatPageManager.getInstance().add(intent);

        intent = new PageIntent(ViewCheckInfoFloatPage.class);
        intent.mode = PageIntent.MODE_SINGLE_INSTANCE;
        FloatPageManager.getInstance().add(intent);

        intent = new PageIntent(ViewCheckDrawFloatPage.class);
        intent.mode = PageIntent.MODE_SINGLE_INSTANCE;
        FloatPageManager.getInstance().add(intent);

        ViewCheckConfig.setViewCheckOpen(context, true);
    }

    @Override
    public void onAppInit(Context context) {
        ViewCheckConfig.setViewCheckOpen(context, false);
    }
}