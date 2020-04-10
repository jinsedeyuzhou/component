package com.ebrightmoon.derobot.reboot.kit.crash;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.View;

import com.ebrightmoon.derobot.R;
import com.ebrightmoon.derobot.reboot.config.CrashCaptureConfig;
import com.ebrightmoon.derobot.reboot.constant.BundleKey;
import com.ebrightmoon.derobot.reboot.kit.fileexplorer.FileExplorerFragment;
import com.ebrightmoon.derobot.reboot.ui.base.BaseFragment;
import com.ebrightmoon.derobot.reboot.ui.setting.SettingItem;
import com.ebrightmoon.derobot.reboot.ui.setting.SettingItemAdapter;
import com.ebrightmoon.derobot.reboot.ui.widget.titlebar.HomeTitleBar;
import com.ebrightmoon.derobot.reboot.util.FileUtil;

public class CrashCaptureMainFragment extends BaseFragment {
    @Override
    protected int onRequestLayout() {
        return R.layout.dk_fragment_crash_capture_main;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initview();
    }

    private void initview() {
        HomeTitleBar titleBar = findViewById(R.id.title_bar);
        titleBar.setListener(new HomeTitleBar.OnTitleBarClickListener() {
            @Override
            public void onRightClick() {
                finish();
            }
        });
        RecyclerView settingList = findViewById(R.id.setting_list);
        settingList.setLayoutManager(new LinearLayoutManager(getContext()));
        SettingItemAdapter mSettingItemAdapter = new SettingItemAdapter(getContext());
        mSettingItemAdapter.append(new SettingItem(R.string.dk_crash_capture_switch, CrashCaptureConfig.isCrashCaptureOpen(getContext())));
        mSettingItemAdapter.append(new SettingItem(R.string.dk_crash_capture_look, R.drawable.dk_more_icon));
        SettingItem item = new SettingItem(R.string.dk_crash_capture_clean_data);
        item.rightDesc = Formatter.formatFileSize(getContext(), FileUtil.getDirectorySize(CrashCaptureManager.getInstance().getCrashCacheDir()));
        mSettingItemAdapter.append(item);
        mSettingItemAdapter.setOnSettingItemSwitchListener(new SettingItemAdapter.OnSettingItemSwitchListener() {
            @Override
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (data.desc == R.string.dk_crash_capture_switch) {
                    CrashCaptureConfig.setCrashCaptureOpen(getContext(), on);
                    if (on) {
                        CrashCaptureManager.getInstance().start();
                    } else {
                        CrashCaptureManager.getInstance().stop();
                    }
                }
            }
        });
        mSettingItemAdapter.setOnSettingItemClickListener(new SettingItemAdapter.OnSettingItemClickListener() {
            @Override
            public void onSettingItemClick(View view, SettingItem data) {
                if (data.desc == R.string.dk_crash_capture_look) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(BundleKey.DIR_KEY, CrashCaptureManager.getInstance().getCrashCacheDir());
                    showContent(FileExplorerFragment.class, bundle);
                } else if (data.desc == R.string.dk_crash_capture_clean_data) {
                    CrashCaptureManager.getInstance().clearCacheHistory();
                    showToast(R.string.dk_crash_capture_clean_data);
                }
            }
        });
        settingList.setAdapter(mSettingItemAdapter);
    }
}
