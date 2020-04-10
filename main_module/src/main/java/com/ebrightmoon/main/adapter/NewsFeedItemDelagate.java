package com.ebrightmoon.main.adapter;

import com.ebrightmoon.common.adapter.recycler.absrecyclerview.ItemViewDelegate;
import com.ebrightmoon.common.adapter.recycler.absrecyclerview.ViewHolder;
import com.ebrightmoon.data.pojo.NewsFeed;
import com.ebrightmoon.main.R;


public class NewsFeedItemDelagate implements ItemViewDelegate<NewsFeed> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_main_home;
    }

    @Override
    public boolean isForViewType(NewsFeed item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, NewsFeed newsFeed, int position) {

    }


}
