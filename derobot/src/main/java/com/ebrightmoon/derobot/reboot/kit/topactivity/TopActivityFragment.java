package com.ebrightmoon.derobot.reboot.kit.topactivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.ebrightmoon.derobot.R;
import com.ebrightmoon.derobot.reboot.config.TopActivityConfig;
import com.ebrightmoon.derobot.reboot.ui.base.BaseFragment;
import com.ebrightmoon.derobot.reboot.ui.base.FloatPageManager;
import com.ebrightmoon.derobot.reboot.ui.base.PageIntent;
import com.ebrightmoon.derobot.reboot.ui.setting.SettingItem;
import com.ebrightmoon.derobot.reboot.ui.setting.SettingItemAdapter;
import com.ebrightmoon.derobot.reboot.ui.widget.titlebar.HomeTitleBar;

/**
 * 项目名:    Android
 * 包名       com.didichuxing.doraemonkit.kit.topactivity
 * 文件名:    TopActivityFragment
 * 创建时间:  2019-04-29 on 12:16
 * 描述:
 *
 * @author 阿钟
 */

public class TopActivityFragment extends BaseFragment {
    @Override
    protected int onRequestLayout() {
        return R.layout.dk_fragment_top_activity;
    }

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
                getActivity().finish();
            }
        });
        RecyclerView topActivityList = findViewById(R.id.top_activity_list);
        topActivityList.setLayoutManager(new LinearLayoutManager(getContext()));
        SettingItemAdapter topActivityAdapter = new SettingItemAdapter(getContext());
        topActivityAdapter.append(new SettingItem(R.string.dk_kit_top_activity, TopActivityConfig.isTopActivityOpen(getContext())));
        topActivityAdapter.setOnSettingItemSwitchListener(new SettingItemAdapter.OnSettingItemSwitchListener() {
            @Override
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (data.desc == R.string.dk_kit_top_activity) {
                    if (on) {
                        PageIntent intent = new PageIntent(TopActivityFloatPage.class);
                        FloatPageManager.getInstance().add(intent);
                        getActivity().finish();
                    } else {
                        FloatPageManager.getInstance().removeAll(TopActivityFloatPage.class);
                    }
                    TopActivityConfig.setTopActivityOpen(getContext(), on);
                }
            }
        });
        topActivityList.setAdapter(topActivityAdapter);
    }
}
