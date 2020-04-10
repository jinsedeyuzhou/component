package com.ebrightmoon.derobot.reboot.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ebrightmoon.derobot.reboot.Derobot;
import com.ebrightmoon.derobot.R;
import com.ebrightmoon.derobot.reboot.kit.Category;
import com.ebrightmoon.derobot.reboot.ui.base.BaseFloatPage;
import com.ebrightmoon.derobot.reboot.ui.kit.GroupKitAdapter;
import com.ebrightmoon.derobot.reboot.ui.kit.KitItem;

import java.util.ArrayList;
import java.util.List;

/**
 *  on 2018/10/23.
 */

public class KitFloatPage extends BaseFloatPage{
    private RecyclerView mGroupKitContainer;
    private GroupKitAdapter mGroupKitAdapter;

    @Override
    protected View onCreateView(Context context, ViewGroup view) {
        return LayoutInflater.from(context).inflate(R.layout.dk_float_kit, null);
    }

    @Override
    protected void onViewCreated(View view) {
        super.onViewCreated(view);
        initView();
    }

    private void initView() {
        mGroupKitContainer = findViewById(R.id.group_kit_container);
        mGroupKitContainer.setLayoutManager(new LinearLayoutManager(getContext()));
        mGroupKitAdapter = new GroupKitAdapter(getContext());
        List<List<KitItem>> kitLists = new ArrayList<>();
        List<KitItem> bizs = Derobot.getKitItems(Category.BIZ);
        if (bizs != null && !bizs.isEmpty()) {
            kitLists.add(bizs);
        }
        kitLists.add(Derobot.getKitItems(Category.TOOLS));
        kitLists.add(Derobot.getKitItems(Category.PERFORMANCE));
        kitLists.add(Derobot.getKitItems(Category.UI));
        kitLists.add(Derobot.getKitItems(Category.CLOSE));
        mGroupKitAdapter.setData(kitLists);
        mGroupKitContainer.setAdapter(mGroupKitAdapter);
    }

    @Override
    protected boolean onBackPressed() {
        finish();
        return true;
    }

    @Override
    public void onHomeKeyPress() {
        finish();
    }

    @Override
    public void onRecentAppKeyPress() {
        finish();
    }
}
