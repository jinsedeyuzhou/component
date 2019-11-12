package com.ebrightmoon.derobot.reboot.kit.temporaryclose;

import android.content.Context;

import com.ebrightmoon.derobot.R;
import com.ebrightmoon.derobot.reboot.kit.Category;
import com.ebrightmoon.derobot.reboot.kit.IKit;
import com.ebrightmoon.derobot.reboot.ui.KitFloatPage;
import com.ebrightmoon.derobot.reboot.ui.base.FloatPageManager;

/**
 *  on 2018/10/26.
 */

public class TemporaryClose implements IKit {
    @Override
    public int getCategory() {
        return Category.CLOSE;
    }

    @Override
    public int getName() {
        return R.string.dk_kit_temporary_close;
    }

    @Override
    public int getIcon() {
        return R.drawable.dk_temporary_close;
    }

    @Override
    public void onClick(Context context) {
        FloatPageManager.getInstance().removeAll(KitFloatPage.class);
    }

    @Override
    public void onAppInit(Context context) {

    }

}
