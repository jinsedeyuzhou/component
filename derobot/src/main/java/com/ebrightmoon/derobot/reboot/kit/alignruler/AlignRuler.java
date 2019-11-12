package com.ebrightmoon.derobot.reboot.kit.alignruler;

import android.content.Context;

import com.ebrightmoon.derobot.R;
import com.ebrightmoon.derobot.reboot.config.AlignRulerConfig;
import com.ebrightmoon.derobot.reboot.constant.PageTag;
import com.ebrightmoon.derobot.reboot.kit.Category;
import com.ebrightmoon.derobot.reboot.kit.IKit;
import com.ebrightmoon.derobot.reboot.ui.base.FloatPageManager;
import com.ebrightmoon.derobot.reboot.ui.base.PageIntent;

/**
 *  on 2018/9/19.
 */

public class AlignRuler implements IKit {
    @Override
    public int getCategory() {
        return Category.UI;
    }

    @Override
    public int getName() {
        return R.string.dk_kit_align_ruler;
    }

    @Override
    public int getIcon() {
        return R.drawable.dk_align_ruler;
    }

    @Override
    public void onClick(Context context) {
        PageIntent pageIntent = new PageIntent(AlignRulerMarkerFloatPage.class);
        pageIntent.tag = PageTag.PAGE_ALIGN_RULER_MARKER;
        pageIntent.mode=PageIntent.MODE_SINGLE_INSTANCE;
        FloatPageManager.getInstance().add(pageIntent);

        pageIntent=new PageIntent(AlignRulerLineFloatPage.class);
        pageIntent.mode=PageIntent.MODE_SINGLE_INSTANCE;
        FloatPageManager.getInstance().add(pageIntent);

        pageIntent=new PageIntent(AlignRulerInfoFloatPage.class);
        pageIntent.mode=PageIntent.MODE_SINGLE_INSTANCE;
        FloatPageManager.getInstance().add(pageIntent);

        AlignRulerConfig.setAlignRulerOpen(context, true);
    }

    @Override
    public void onAppInit(Context context) {
        AlignRulerConfig.setAlignRulerOpen(context, false);
    }
}
