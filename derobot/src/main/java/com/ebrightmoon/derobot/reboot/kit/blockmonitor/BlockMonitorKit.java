package com.ebrightmoon.derobot.reboot.kit.blockmonitor;

import android.content.Context;
import android.content.Intent;

import com.ebrightmoon.derobot.R;
import com.ebrightmoon.derobot.reboot.constant.BundleKey;
import com.ebrightmoon.derobot.reboot.constant.FragmentIndex;
import com.ebrightmoon.derobot.reboot.kit.Category;
import com.ebrightmoon.derobot.reboot.kit.IKit;
import com.ebrightmoon.derobot.reboot.ui.UniversalActivity;


/**
 * @desc: 卡顿检测kit
 */
public class BlockMonitorKit implements IKit {



    @Override
    public int getCategory() {
        return Category.PERFORMANCE;
    }

    @Override
    public int getName() {
        return R.string.dk_kit_block_monitor;
    }

    @Override
    public int getIcon() {
        return R.drawable.dk_block_monitor;
    }


    @Override
    public void onClick(Context context) {
        Intent intent = new Intent(context, UniversalActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(BundleKey.FRAGMENT_INDEX, FragmentIndex.FRAGMENT_BLOCK_MONITOR);
        context.startActivity(intent);
    }

    @Override
    public void onAppInit(Context context) {

    }
}
