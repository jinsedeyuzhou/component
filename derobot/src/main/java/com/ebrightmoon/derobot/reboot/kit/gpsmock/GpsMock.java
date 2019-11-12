package com.ebrightmoon.derobot.reboot.kit.gpsmock;

import android.content.Context;
import android.content.Intent;

import com.ebrightmoon.derobot.R;
import com.ebrightmoon.derobot.reboot.config.GpsMockConfig;
import com.ebrightmoon.derobot.reboot.constant.BundleKey;
import com.ebrightmoon.derobot.reboot.constant.FragmentIndex;
import com.ebrightmoon.derobot.reboot.kit.Category;
import com.ebrightmoon.derobot.reboot.kit.IKit;
import com.ebrightmoon.derobot.reboot.model.LatLng;
import com.ebrightmoon.derobot.reboot.ui.UniversalActivity;

/**
 *  on 2018/9/20.
 */

public class GpsMock implements IKit {
    @Override
    public int getCategory() {
        return Category.TOOLS;
    }

    @Override
    public int getName() {
        return R.string.dk_kit_gps_mock;
    }

    @Override
    public int getIcon() {
        return  R.drawable.dk_gps_mock;
    }

    @Override
    public void onClick(Context context) {
        Intent intent = new Intent(context, UniversalActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(BundleKey.FRAGMENT_INDEX, FragmentIndex.FRAGMENT_GPS_MOCK);
        context.startActivity(intent);
    }

    @Override
    public void onAppInit(Context context) {
        if (GpsMockConfig.isGPSMockOpen(context)) {
            GpsMockManager.getInstance().startMock();
            LatLng latLng = GpsMockConfig.getMockLocation(context);
            if (latLng == null) {
                return;
            }
            GpsMockManager.getInstance().mockLocation(latLng.latitude, latLng.longitude);
        }
    }

}