package com.ebrightmoon.derobot.reboot.kit.parameter.frameInfo;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;

import com.ebrightmoon.derobot.R;
import com.ebrightmoon.derobot.reboot.constant.BundleKey;
import com.ebrightmoon.derobot.reboot.kit.common.PerformanceDataManager;
import com.ebrightmoon.derobot.reboot.kit.common.PerformanceFragment;
import com.ebrightmoon.derobot.reboot.kit.parameter.AbsParameterFragment;
import com.ebrightmoon.derobot.reboot.ui.realtime.datasource.DataSourceFactory;
import com.ebrightmoon.derobot.reboot.ui.setting.SettingItem;
import com.ebrightmoon.derobot.reboot.ui.setting.SettingItemAdapter;

import java.util.Collection;
import java.util.List;

/**
 *  on 2018/9/13.
 */

public class FrameInfoFragment extends AbsParameterFragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PerformanceDataManager.getInstance().init(getContext());
    }

    @Override
    protected int getTitle() {
        return R.string.dk_kit_frame_info_desc;
    }

    @Override
    protected Collection<SettingItem> getSettingItems(List<SettingItem> list) {
        list.add(new SettingItem(R.string.dk_frameinfo_detection_switch, false));
        list.add(new SettingItem(R.string.dk_item_cache_log, R.drawable.dk_more_icon));
        return list;
    }

    @Override
    protected SettingItemAdapter.OnSettingItemSwitchListener getItemSwitchListener() {
        return new SettingItemAdapter.OnSettingItemSwitchListener() {
            @Override
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (on) {
                    startMonitor();
                } else {
                    stopMonitor();
                }
//                PerformanceInfoConfig.setFPSOpen(getContext(), on);
            }
        };
    }

    @Override
    protected SettingItemAdapter.OnSettingItemClickListener getItemClickListener() {
        return new SettingItemAdapter.OnSettingItemClickListener() {
            @Override
            public void onSettingItemClick(View view, SettingItem data) {
                if (data.desc == R.string.dk_item_cache_log) {

                    Bundle bundle = new Bundle();
                    bundle.putInt(BundleKey.PERFORMANCE_TYPE, PerformanceFragment.FPS);
                    showContent(PerformanceFragment.class, bundle);
                }

            }
        };
    }

    private void startMonitor() {
        PerformanceDataManager.getInstance().startMonitorFrameInfo();
        openChartPage(R.string.dk_frameinfo_fps, DataSourceFactory.TYPE_FRAME);
    }

    private void stopMonitor() {
        PerformanceDataManager.getInstance().stopMonitorFrameInfo();
        closeChartPage();
    }

}
