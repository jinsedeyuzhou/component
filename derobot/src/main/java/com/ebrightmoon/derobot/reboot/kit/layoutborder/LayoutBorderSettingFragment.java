package com.ebrightmoon.derobot.reboot.kit.layoutborder;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.ebrightmoon.derobot.R;
import com.ebrightmoon.derobot.reboot.config.LayoutBorderConfig;
import com.ebrightmoon.derobot.reboot.ui.base.BaseFragment;
import com.ebrightmoon.derobot.reboot.ui.base.FloatPageManager;
import com.ebrightmoon.derobot.reboot.ui.base.PageIntent;
import com.ebrightmoon.derobot.reboot.ui.setting.SettingItem;
import com.ebrightmoon.derobot.reboot.ui.setting.SettingItemAdapter;
import com.ebrightmoon.derobot.reboot.ui.widget.titlebar.HomeTitleBar;

/**
 *  on 2018/10/9.
 */

public class LayoutBorderSettingFragment extends BaseFragment {
    private static final String TAG = "LayoutBorderSettingFragment";
    private RecyclerView mSettingList;
    private SettingItemAdapter mSettingItemAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        HomeTitleBar titleBar = findViewById(R.id.title_bar);
        titleBar.setListener(new HomeTitleBar.OnTitleBarClickListener() {
            @Override
            public void onRightClick() {
                finish();
            }
        });
        mSettingList = findViewById(R.id.setting_list);
        mSettingList.setLayoutManager(new LinearLayoutManager(getContext()));
        mSettingItemAdapter = new SettingItemAdapter(getContext());
        mSettingItemAdapter.append(new SettingItem(R.string.dk_kit_layout_border, LayoutBorderConfig.isLayoutBorderOpen()));
        mSettingItemAdapter.append(new SettingItem(R.string.dk_layout_level, LayoutBorderConfig.isLayoutLevelOpen()));
        mSettingItemAdapter.setOnSettingItemSwitchListener(new SettingItemAdapter.OnSettingItemSwitchListener() {
            @Override
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (data.desc == R.string.dk_kit_layout_border) {
                    if (on) {
                        LayoutBorderManager.getInstance().start();
                    } else {
                        LayoutBorderManager.getInstance().stop();
                    }
                    LayoutBorderConfig.setLayoutBorderOpen(on);
                } else if (data.desc == R.string.dk_layout_level) {
                    if (on) {
                        PageIntent intent = new PageIntent(LayoutLevelFloatPage.class);
                        intent.mode = PageIntent.MODE_SINGLE_INSTANCE;
                        FloatPageManager.getInstance().add(intent);
                    } else {
                        FloatPageManager.getInstance().removeAll(LayoutLevelFloatPage.class);
                    }
                    LayoutBorderConfig.setLayoutLevelOpen(on);
                }
            }
        });
        mSettingList.setAdapter(mSettingItemAdapter);
    }

    @Override
    protected int onRequestLayout() {
        return R.layout.dk_fragment_layout_border_setting;
    }
}