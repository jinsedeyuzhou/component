package com.ebrightmoon.derobot.reboot.kit.topactivity;

import android.content.Context;
import android.content.Intent;

import com.ebrightmoon.derobot.R;
import com.ebrightmoon.derobot.reboot.config.TopActivityConfig;
import com.ebrightmoon.derobot.reboot.constant.BundleKey;
import com.ebrightmoon.derobot.reboot.constant.FragmentIndex;
import com.ebrightmoon.derobot.reboot.kit.Category;
import com.ebrightmoon.derobot.reboot.kit.IKit;
import com.ebrightmoon.derobot.reboot.ui.UniversalActivity;

/**
 * 项目名:    Android
 * 包名       com.didichuxing.doraemonkit.kit.topactivity
 * 文件名:    TopActivity
 * 创建时间:  2019-04-29 on 12:13
 * 描述:     当前栈顶的Activity信息
 *
 * @author 阿钟
 */

public class TopActivity implements IKit {

    @Override
    public int getCategory() {
        return Category.TOOLS;
    }

    @Override
    public int getName() {
        return R.string.dk_kit_top_activity;
    }

    @Override
    public int getIcon() {
        return R.drawable.dk_view_check;
    }

    @Override
    public void onClick(Context context) {
        Intent intent = new Intent(context, UniversalActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(BundleKey.FRAGMENT_INDEX, FragmentIndex.FRAGMENT_TOP_ACTIVITY);
        context.startActivity(intent);
    }

    @Override
    public void onAppInit(Context context) {
        TopActivityConfig.setTopActivityOpen(context, false);
    }
}
