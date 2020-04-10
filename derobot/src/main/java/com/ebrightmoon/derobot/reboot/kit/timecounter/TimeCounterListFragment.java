package com.ebrightmoon.derobot.reboot.kit.timecounter;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.ebrightmoon.derobot.R;
import com.ebrightmoon.derobot.reboot.kit.timecounter.bean.CounterInfo;
import com.ebrightmoon.derobot.reboot.ui.base.BaseFragment;
import com.ebrightmoon.derobot.reboot.ui.widget.recyclerview.DividerItemDecoration;
import com.ebrightmoon.derobot.reboot.ui.widget.titlebar.TitleBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @desc: 跳转耗时历史记录列表
 */

public class TimeCounterListFragment extends BaseFragment {
    private static final String TAG = "TimeCounterListFragment";

    private RecyclerView mBlockList;
    private TimeCounterListAdapter mAdapter;
    private TitleBar mTitleBar;

    @Override
    protected int onRequestLayout() {
        return R.layout.dk_fragment_time_counter_list;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        load();
    }


    private void initView() {
        mBlockList = findViewById(R.id.block_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBlockList.setLayoutManager(layoutManager);
        mAdapter = new TimeCounterListAdapter(getContext());
        mBlockList.setAdapter(mAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.dk_divider));
        mBlockList.addItemDecoration(decoration);
        mTitleBar = findViewById(R.id.title_bar);
        mTitleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftClick() {
                getActivity().onBackPressed();
            }

            @Override
            public void onRightClick() {

            }
        });
    }


    private void load() {
        List<CounterInfo> infos = new ArrayList<>(TimeCounterManager.get().getHistory());
        infos.add(0, TimeCounterManager.get().getAppSetupInfo());
        Collections.sort(infos, new Comparator<CounterInfo>() {
            @Override
            public int compare(CounterInfo lhs, CounterInfo rhs) {
                return Long.valueOf(rhs.time)
                        .compareTo(lhs.time);
            }
        });
        mAdapter.setData(infos);
    }


}