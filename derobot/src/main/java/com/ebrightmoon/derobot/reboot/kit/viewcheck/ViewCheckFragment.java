package com.ebrightmoon.derobot.reboot.kit.viewcheck;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.ebrightmoon.derobot.R;
import com.ebrightmoon.derobot.reboot.config.ViewCheckConfig;
import com.ebrightmoon.derobot.reboot.constant.PageTag;
import com.ebrightmoon.derobot.reboot.ui.base.BaseFragment;
import com.ebrightmoon.derobot.reboot.ui.base.FloatPageManager;
import com.ebrightmoon.derobot.reboot.ui.base.PageIntent;
import com.ebrightmoon.derobot.reboot.ui.setting.SettingItem;
import com.ebrightmoon.derobot.reboot.ui.setting.SettingItemAdapter;
import com.ebrightmoon.derobot.reboot.ui.widget.titlebar.HomeTitleBar;

/**
 *  on 2018/11/23.
 */

public class ViewCheckFragment extends BaseFragment {
    private HomeTitleBar mTitleBar;
    private RecyclerView mSettingList;
    private SettingItemAdapter mSettingItemAdapter;

    @Override
    protected int onRequestLayout() {
        return R.layout.dk_fragment_view_check;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        mTitleBar = findViewById(R.id.title_bar);
        mTitleBar.setListener(new HomeTitleBar.OnTitleBarClickListener() {
            @Override
            public void onRightClick() {
                getActivity().finish();
            }
        });
        mSettingList = findViewById(R.id.setting_list);
        mSettingList.setLayoutManager(new LinearLayoutManager(getContext()));
        mSettingItemAdapter = new SettingItemAdapter(getContext());
        mSettingItemAdapter.setOnSettingItemSwitchListener(new SettingItemAdapter.OnSettingItemSwitchListener() {
            @Override
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (data.desc == R.string.dk_kit_view_check) {
                    if (on) {
                        PageIntent intent = new PageIntent(ViewCheckFloatPage.class);
                        intent.tag = PageTag.PAGE_VIEW_CHECK;
                        FloatPageManager.getInstance().add(intent);
                        FloatPageManager.getInstance().add(new PageIntent(ViewCheckInfoFloatPage.class));
                        FloatPageManager.getInstance().add(new PageIntent(ViewCheckDrawFloatPage.class));
                        getActivity().finish();
                    } else {
                        FloatPageManager.getInstance().removeAll(ViewCheckDrawFloatPage.class);
                        FloatPageManager.getInstance().removeAll(ViewCheckInfoFloatPage.class);
                        FloatPageManager.getInstance().removeAll(ViewCheckFloatPage.class);
                    }
                    ViewCheckConfig.setViewCheckOpen(getContext(), on);
                }
            }
        });
        mSettingItemAdapter.append(new SettingItem(R.string.dk_kit_view_check, ViewCheckConfig.isViewCheckOpen(getContext())));
        mSettingList.setAdapter(mSettingItemAdapter);
    }
}