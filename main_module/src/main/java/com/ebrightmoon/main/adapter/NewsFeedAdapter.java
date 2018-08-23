package com.ebrightmoon.main.adapter;


import android.content.Context;

import com.ebrightmoon.common.adapter.recycler.absrecyclerview.MultiItemTypeAdapter;

import java.util.List;

public class NewsFeedAdapter extends MultiItemTypeAdapter {

    public NewsFeedAdapter(Context context, List datas) {
        super(context, datas);
        addItemViewDelegate(new NewsFeedItemDelagate());

    }
}
