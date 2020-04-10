package com.ebrightmoon.main.adapter;


import android.content.Context;

import com.ebrightmoon.common.adapter.recycler.absrecyclerview.MultiItemTypeAdapter;
import com.ebrightmoon.data.pojo.NewsFeed;

import java.util.List;

public class NewsFeedAdapter extends MultiItemTypeAdapter<NewsFeed> {

    public NewsFeedAdapter(Context context, List datas) {
        super(context, datas);
        addItemViewDelegate(new NewsFeedItemDelagate());

    }
}
