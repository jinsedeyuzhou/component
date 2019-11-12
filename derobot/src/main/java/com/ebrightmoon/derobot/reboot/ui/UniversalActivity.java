package com.ebrightmoon.derobot.reboot.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.ebrightmoon.derobot.reboot.constant.BundleKey;
import com.ebrightmoon.derobot.reboot.constant.FragmentIndex;
import com.ebrightmoon.derobot.reboot.kit.alignruler.AlignRulerSettingFragment;
import com.ebrightmoon.derobot.reboot.kit.blockmonitor.BlockMonitorFragment;
import com.ebrightmoon.derobot.reboot.kit.colorpick.ColorPickerSettingFragment;
import com.ebrightmoon.derobot.reboot.kit.crash.CrashCaptureMainFragment;
import com.ebrightmoon.derobot.reboot.kit.custom.MonitorDataUploadFragment;
import com.ebrightmoon.derobot.reboot.kit.dataclean.DataCleanFragment;
import com.ebrightmoon.derobot.reboot.kit.fileexplorer.FileExplorerFragment;
import com.ebrightmoon.derobot.reboot.kit.gpsmock.GpsMockFragment;
import com.ebrightmoon.derobot.reboot.kit.logInfo.LogInfoSettingFragment;
import com.ebrightmoon.derobot.reboot.kit.network.ui.NetWorkMonitorFragment;
import com.ebrightmoon.derobot.reboot.kit.parameter.cpu.CpuMainPageFragment;
import com.ebrightmoon.derobot.reboot.kit.parameter.frameInfo.FrameInfoFragment;
import com.ebrightmoon.derobot.reboot.kit.parameter.ram.RamMainPageFragment;
import com.ebrightmoon.derobot.reboot.kit.sysinfo.SysInfoFragment;
import com.ebrightmoon.derobot.reboot.kit.timecounter.TimeCounterFragment;
import com.ebrightmoon.derobot.reboot.kit.topactivity.TopActivityFragment;
import com.ebrightmoon.derobot.reboot.kit.viewcheck.ViewCheckFragment;
import com.ebrightmoon.derobot.reboot.kit.weaknetwork.WeakNetworkFragment;
import com.ebrightmoon.derobot.reboot.kit.webdoor.WebDoorDefaultFragment;
import com.ebrightmoon.derobot.reboot.kit.webdoor.WebDoorFragment;
import com.ebrightmoon.derobot.reboot.ui.base.BaseActivity;
import com.ebrightmoon.derobot.reboot.ui.base.BaseFragment;

/**
 *  on 2018/10/26.
 */

public class UniversalActivity extends BaseActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            finish();
            return;
        }
        int index = bundle.getInt(BundleKey.FRAGMENT_INDEX);
        if (index == 0) {
            finish();
            return;
        }
        Class<? extends BaseFragment> fragmentClass = null;
        switch (index) {
            case FragmentIndex.FRAGMENT_SYS_INFO:
                fragmentClass = SysInfoFragment.class;
                break;
            case FragmentIndex.FRAGMENT_FILE_EXPLORER:
                fragmentClass = FileExplorerFragment.class;
                break;
            case FragmentIndex.FRAGMENT_LOG_INFO_SETTING:
                fragmentClass = LogInfoSettingFragment.class;
                break;
            case FragmentIndex.FRAGMENT_COLOR_PICKER_SETTING:
                fragmentClass = ColorPickerSettingFragment.class;
                break;
            case FragmentIndex.FRAGMENT_FRAME_INFO:
                fragmentClass = FrameInfoFragment.class;
                break;
            case FragmentIndex.FRAGMENT_GPS_MOCK:
                fragmentClass = GpsMockFragment.class;
                break;
            case FragmentIndex.FRAGMENT_ALIGN_RULER_SETTING:
                fragmentClass = AlignRulerSettingFragment.class;
                break;
            case FragmentIndex.FRAGMENT_WEB_DOOR:
                fragmentClass = WebDoorFragment.class;
                break;
            case FragmentIndex.FRAGMENT_DATA_CLEAN:
                fragmentClass = DataCleanFragment.class;
                break;
            case FragmentIndex.FRAGMENT_WEAK_NETWORK:
                fragmentClass = WeakNetworkFragment.class;
                break;
            case FragmentIndex.FRAGMENT_BLOCK_MONITOR:
                fragmentClass = BlockMonitorFragment.class;
                break;
            case FragmentIndex.FRAGMENT_CRASH:
                fragmentClass = CrashCaptureMainFragment.class;
                break;
            case FragmentIndex.FRAGMENT_VIEW_CHECK:
                fragmentClass = ViewCheckFragment.class;
                break;
            case FragmentIndex.FRAGMENT_NETWORK_MONITOR:
                fragmentClass = NetWorkMonitorFragment.class;
                break;
            case FragmentIndex.FRAGMENT_CPU:
                fragmentClass = CpuMainPageFragment.class;
                break;
            case FragmentIndex.FRAGMENT_RAM:
                fragmentClass = RamMainPageFragment.class;
                break;
            case FragmentIndex.FRAGMENT_TIME_COUNTER:
                fragmentClass = TimeCounterFragment.class;
                break;
            case FragmentIndex.FRAGMENT_WEB_DOOR_DEFAULT:
                fragmentClass = WebDoorDefaultFragment.class;
                break;
            case FragmentIndex.FRAGMENT_CUSTOM:
                fragmentClass = MonitorDataUploadFragment.class;
                break;
            case FragmentIndex.FRAGMENT_TOP_ACTIVITY:
                fragmentClass = TopActivityFragment.class;
                break;
            default:
                break;
        }
        if (fragmentClass == null) {
            finish();
            Toast.makeText(this, String.format("fragment index %s not found", index), Toast.LENGTH_SHORT).show();
            return;
        }
        showContent(fragmentClass, bundle);
    }
}
