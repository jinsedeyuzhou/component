package com.ebrightmoon.derobot.reboot.kit.network.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.ebrightmoon.derobot.R;
import com.ebrightmoon.derobot.reboot.kit.network.NetworkManager;
import com.ebrightmoon.derobot.reboot.ui.base.BaseFragment;
import com.ebrightmoon.derobot.reboot.ui.realtime.OnFloatPageChangeListener;
import com.ebrightmoon.derobot.reboot.ui.realtime.RealTimeChartIconPage;
import com.ebrightmoon.derobot.reboot.ui.realtime.RealTimeChartPage;
import com.ebrightmoon.derobot.reboot.ui.realtime.datasource.DataSourceFactory;
import com.ebrightmoon.derobot.reboot.ui.setting.SettingItem;
import com.ebrightmoon.derobot.reboot.ui.setting.SettingItemAdapter;
import com.ebrightmoon.derobot.reboot.ui.widget.titlebar.HomeTitleBar;

public class NetWorkMonitorFragment extends BaseFragment implements OnFloatPageChangeListener {
    private SettingItemAdapter mSettingItemAdapter;
    private RecyclerView mSettingList;

    @Override
    protected int onRequestLayout() {
        return R.layout.dk_fragment_net_monitor;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        HomeTitleBar homeTitleBar = findViewById(R.id.title_bar);
        homeTitleBar.setListener(new HomeTitleBar.OnTitleBarClickListener() {
            @Override
            public void onRightClick() {
                getActivity().finish();
            }
        });
        mSettingList = findViewById(R.id.setting_list);
        mSettingList.setLayoutManager(new LinearLayoutManager(getContext()));
        mSettingItemAdapter = new SettingItemAdapter(getContext());
        mSettingList.setAdapter(mSettingItemAdapter);
        mSettingItemAdapter.append(new SettingItem(R.string.dk_net_monitor_detection_switch, NetworkManager.isActive()));
        mSettingItemAdapter.setOnSettingItemSwitchListener(new SettingItemAdapter.OnSettingItemSwitchListener() {
            @Override
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (data.desc == R.string.dk_net_monitor_detection_switch) {
                    if (on) {
                        String title = getString(R.string.dk_kit_network_monitor);
                        int type = DataSourceFactory.TYPE_NETWORK;
                        NetworkManager.get().startMonitor();
                        RealTimeChartPage.openChartPage(title, type, RealTimeChartPage.DEFAULT_REFRESH_INTERVAL, NetWorkMonitorFragment.this);
                    } else {
                        NetworkManager.get().stopMonitor();
                        RealTimeChartPage.closeChartPage();
                    }
                }
            }
        });
        findViewById(R.id.btn_net_summary).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContent(NetWorkMainPagerFragment.class);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RealTimeChartPage.removeCloseListener();
    }

    @Override
    public void onFloatPageClose(String tag) {
        if (!TextUtils.equals(RealTimeChartIconPage.TAG, tag)) {
            return;
        }
        if (mSettingList == null || mSettingList.isComputingLayout()) {
            return;
        }
        if (mSettingItemAdapter == null) {
            return;
        }
        if (!mSettingItemAdapter.getData().get(0).isChecked) {
            return;
        }
        mSettingItemAdapter.getData().get(0).isChecked = false;
        mSettingItemAdapter.notifyItemChanged(0);
    }

    @Override
    public void onFloatPageOpen(String tag) {

    }
}
