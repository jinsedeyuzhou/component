package com.ebrightmoon.main.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ebrightmoon.common.adapter.recycler.absrecyclerview.MultiItemTypeAdapter;
import com.ebrightmoon.commonlogic.base.BaseListFragment;
import com.ebrightmoon.data.pojo.NewsFeed;
import com.ebrightmoon.main.adapter.NewsFeedAdapter;

import java.util.ArrayList;

/**
 * Time: 2020-04-10
 * Author:wyy
 * Description:
 */
public class NewsListFragment extends BaseListFragment<NewsFeed,LinearLayoutManager ,NewsFeedAdapter> {

    private ArrayList<NewsFeed> feeds = new ArrayList<>();


    @Override
    protected NewsFeedAdapter createAdapter() {
        feeds.add(new NewsFeed());
        feeds.add(new NewsFeed());
        feeds.add(new NewsFeed());
        feeds.add(new NewsFeed());
        feeds.add(new NewsFeed());
        feeds.add(new NewsFeed());
        feeds.add(new NewsFeed());
        feeds.add(new NewsFeed());
        return new NewsFeedAdapter(mContext,feeds);
    }

    @Override
    protected LinearLayoutManager createLayoutManager() {
        return new LinearLayoutManager(mContext);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

    }

    @Override
    protected void getData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadMoreView.finishLoading();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 10000);
    }
}
