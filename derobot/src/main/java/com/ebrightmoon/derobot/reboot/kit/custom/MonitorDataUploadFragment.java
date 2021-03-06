package com.ebrightmoon.derobot.reboot.kit.custom;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ebrightmoon.derobot.R;
import com.ebrightmoon.derobot.reboot.config.PerformanceInfoConfig;
import com.ebrightmoon.derobot.reboot.kit.common.PerformanceDataManager;
import com.ebrightmoon.derobot.reboot.ui.base.BaseFragment;
import com.ebrightmoon.derobot.reboot.ui.base.FloatPageManager;
import com.ebrightmoon.derobot.reboot.ui.base.PageIntent;
import com.ebrightmoon.derobot.reboot.ui.dialog.DialogInfo;
import com.ebrightmoon.derobot.reboot.ui.dialog.SimpleDialogListener;
import com.ebrightmoon.derobot.reboot.ui.realtime.OnFloatPageChangeListener;
import com.ebrightmoon.derobot.reboot.ui.realtime.RealTimeChartIconPage;
import com.ebrightmoon.derobot.reboot.ui.realtime.RealTimeChartPage;
import com.ebrightmoon.derobot.reboot.ui.setting.SettingItem;
import com.ebrightmoon.derobot.reboot.ui.setting.SettingItemAdapter;
import com.ebrightmoon.derobot.reboot.ui.widget.titlebar.HomeTitleBar;

public class MonitorDataUploadFragment extends BaseFragment implements OnFloatPageChangeListener {
    private static final String TAG = "MonitorDataUploadFragment";
    private SettingItemAdapter mSettingItemAdapter;
    private RecyclerView mSettingList;
    private TextView mCommitButton;

    @Override
    protected int onRequestLayout() {
        return R.layout.dk_fragment_monitor_data_upload_page;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initCommitButton();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PerformanceDataManager.getInstance().init(getContext());
    }

    private void initView() {
        HomeTitleBar titleBar = findViewById(R.id.title_bar);
        titleBar.setTitle(R.string.dk_category_performance);
        titleBar.setListener(new HomeTitleBar.OnTitleBarClickListener() {
            @Override
            public void onRightClick() {
                getActivity().finish();
            }
        });
        mCommitButton = findViewById(R.id.commit);
        mSettingList = findViewById(R.id.setting_list);
        mSettingList.setLayoutManager(new LinearLayoutManager(getContext()));
        mSettingItemAdapter = new SettingItemAdapter(getContext());
        mSettingItemAdapter.append(new SettingItem(R.string.dk_frameinfo_fps, PerformanceInfoConfig.isFPSOpen(getContext())));
        mSettingItemAdapter.append(new SettingItem(R.string.dk_frameinfo_cpu, PerformanceInfoConfig.isCPUOpen(getContext())));
        mSettingItemAdapter.append(new SettingItem(R.string.dk_frameinfo_ram, PerformanceInfoConfig.isMemoryOpen(getContext())));
        mSettingItemAdapter.append(new SettingItem(R.string.dk_kit_net_monitor, PerformanceInfoConfig.isTrafficOpen(getContext())));
        mSettingItemAdapter.append(new SettingItem(R.string.dk_platform_monitor_view_stat_data, R.drawable.dk_more_icon));

        mSettingItemAdapter.setOnSettingItemSwitchListener(new SettingItemAdapter.OnSettingItemSwitchListener() {
            @Override
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (data.desc == R.string.dk_frameinfo_fps) {
                    PerformanceInfoConfig.setFPSOpen(getContext(), on);
                } else if (data.desc == R.string.dk_frameinfo_cpu) {
                    PerformanceInfoConfig.setCPUOpen(getContext(), on);
                } else if (data.desc == R.string.dk_frameinfo_ram) {
                    PerformanceInfoConfig.setMemoryOpen(getContext(), on);
                } else if (data.desc == R.string.dk_kit_net_monitor) {
                    PerformanceInfoConfig.setTrafficOpen(getContext(), on);
                }
                setCommitButtonState();
            }
        });
        mSettingItemAdapter.setOnSettingItemClickListener(new SettingItemAdapter.OnSettingItemClickListener() {
            @Override
            public void onSettingItemClick(View view, SettingItem data) {
                if (data.desc == R.string.dk_platform_monitor_view_stat_data) {
                    showContent(PageDataFragment.class);
                }
            }
        });
        mSettingList.setAdapter(mSettingItemAdapter);
      mCommitButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (mCommitButton.getText().equals(getString(R.string.dk_platform_monitor_data_button_stop))) {
                  DialogInfo dialogInfo = new DialogInfo();
                  dialogInfo.title = getString(R.string.dk_platform_monitor_data_button_stop);
                  dialogInfo.listener = new SimpleDialogListener() {
                      @Override
                      public boolean onPositive() {
                          mCommitButton.setText(R.string.dk_platform_monitor_data_button);
                          PerformanceDataManager.getInstance().stopUploadMonitorData();
                          FloatPageManager.getInstance().removeAll(RealTimePerformDataFloatPage.class);
                          return true;
                      }

                      @Override
                      public boolean onNegative() {
                          return true;
                      }
                  };
                  showDialog(dialogInfo);
              } else {
                  DialogInfo dialogInfo = new DialogInfo();
                  dialogInfo.title = getString(R.string.dk_platform_monitor_data_button);
                  dialogInfo.listener = new SimpleDialogListener() {
                      @Override
                      public boolean onPositive() {
                          mCommitButton.setText(R.string.dk_platform_monitor_data_button_stop);
                          PerformanceDataManager.getInstance().startUploadMonitorData();
                          PageIntent pageIntent = new PageIntent(RealTimePerformDataFloatPage.class);
                          pageIntent.mode = PageIntent.MODE_SINGLE_INSTANCE;
                          FloatPageManager.getInstance().add(pageIntent);
                          return true;
                      }

                      @Override
                      public boolean onNegative() {
                          return true;
                      }
                  };
                  showDialog(dialogInfo);
              }
          }
      });
    }

    private boolean checkCommitButtonEnable() {
        if (PerformanceInfoConfig.isCPUOpen(getContext()) ||
                PerformanceInfoConfig.isFPSOpen(getContext()) ||
                PerformanceInfoConfig.isMemoryOpen(getContext()) ||
                PerformanceInfoConfig.isTrafficOpen(getContext())) {
            return true;
        } else {
            return false;
        }
    }

    private void setCommitButtonState(){
        if (checkCommitButtonEnable()) {
            mCommitButton.setEnabled(true);
        } else {
            mCommitButton.setEnabled(false);
        }
    }

    private void initCommitButton() {
        setCommitButtonState();
        if (checkCommitButtonEnable() && PerformanceDataManager.getInstance().isUploading()) {
            mCommitButton.setText(R.string.dk_platform_monitor_data_button_stop);
        } else {
            mCommitButton.setText(R.string.dk_platform_monitor_data_button);
        }
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
