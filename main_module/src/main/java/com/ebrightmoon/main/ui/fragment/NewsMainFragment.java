package com.ebrightmoon.main.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebrightmoon.common.adapter.recycler.absrecyclerview.EmptyWrapper;
import com.ebrightmoon.common.adapter.recycler.absrecyclerview.HeaderAndFooterWrapper;
import com.ebrightmoon.common.adapter.recycler.absrecyclerview.LoadMoreWrapper;
import com.ebrightmoon.common.adapter.recycler.divider.DividerItemDecoration;
import com.ebrightmoon.common.base.BaseFragment;
import com.ebrightmoon.common.util.Tools;
import com.ebrightmoon.main.R;
import com.ebrightmoon.main.adapter.NewsFeedAdapter;
import com.ebrightmoon.main.entity.Channel;
import com.ebrightmoon.main.entity.NewsFeed;
import com.ebrightmoon.main.view.LoadMoreView;

import java.util.ArrayList;
import java.util.List;

public class NewsMainFragment extends BaseFragment {
    private static final int[] drawables = {R.mipmap.one, R.mipmap.two, R.mipmap.four, R.mipmap
            .three, R.mipmap.five};
    private SwipeRefreshLayout mainSwipe;
    private RecyclerView mainRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private List<NewsFeed> newsFeedList=new ArrayList<>();
    private NewsFeedAdapter mRecyclerViewAdapter;
    private EmptyWrapper mEmptyWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;
    private int curPage = 1;
    private static final int PAGE_SIZE = 15;
    private LoadMoreView loadMoreView;
    private Handler handler=new Handler();


    public static NewsMainFragment newInstance(Channel courseType) {
        NewsMainFragment newsMainFragment = new NewsMainFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Channel.KEY_COURSE_TYPE, courseType);
        newsMainFragment.setRegisterEvent(true);
        newsMainFragment.setArguments(bundle);
        return newsMainFragment;
    }


    @Override
    protected int getLayoutID() {
        return R.layout.fragment_main_news;
    }

    @Override
    protected void initView(View view) {
        newsFeedList.add(new NewsFeed());
        newsFeedList.add(new NewsFeed());
        newsFeedList.add(new NewsFeed());
        newsFeedList.add(new NewsFeed());
        newsFeedList.add(new NewsFeed());
        newsFeedList.add(new NewsFeed());
        newsFeedList.add(new NewsFeed());
        newsFeedList.add(new NewsFeed());
        newsFeedList.add(new NewsFeed());
        mainSwipe = view.findViewById(R.id.main_swipe);
        mainRecyclerView = view.findViewById(R.id.main_recyclerView);
        mainSwipe.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mainSwipe.setSize(SwipeRefreshLayout.DEFAULT);
        mainSwipe.post(new Runnable() {
            @Override
            public void run() {
//                mainSwipe.setRefreshing(true);
            }
        });
//        mainRecyclerView.addItemDecoration(new DividerItemDecoration(
//                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mainRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mLayoutManager = new LinearLayoutManager(mContext);
        mainRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerViewAdapter = new NewsFeedAdapter(mContext,newsFeedList );
        initEmptyView();

        loadMoreView = new LoadMoreView(mContext);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadMoreView.setLayoutParams(layoutParams);
        mLoadMoreWrapper = new LoadMoreWrapper(mEmptyWrapper);
        mLoadMoreWrapper.setLoadMoreView(loadMoreView);
//        mLoadMoreWrapper.setLoadMoreView(R.layout.default_loading);
        mainRecyclerView.setAdapter(mLoadMoreWrapper);

    }

    /**
     * 无数据布局
     */
    private void initEmptyView() {
        mEmptyWrapper = new EmptyWrapper(mRecyclerViewAdapter);
        View item_empty = View.inflate(mContext, R.layout.item_main_empty, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        item_empty.setLayoutParams(layoutParams);
        mEmptyWrapper.setEmptyView(item_empty);

    }

    /**
     * 初始化头布局
     */
    private void initHeaderAndFooter() {
        HeaderAndFooterWrapper    mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mEmptyWrapper);
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_main_empty, mainRecyclerView, false);
        mHeaderAndFooterWrapper.addHeaderView(view);
    }

    @Override
    protected void bindEvent() {
        mainSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

        mainRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (Tools.isVisBottom(mainRecyclerView)) {
                    loadMoreView.startLoading();
//                    mLoadMoreWrapper.notifyDataSetChanged();
                    curPage++;
                    getData();
                }else
                {
//                    loadMoreView.hideLoading();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        getData();
    }

    /**
     * 获取请求数据
     */
    private void getData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadMoreView.finishLoading();
            }
        },10000);
    }

    @Override
    protected void processClick(View v) {

    }



    /**
     * 提供当前Fragment的主色调的Bitmap对象,供Palette解析颜色
     *
     * @return
     */
    public static int getBackgroundBitmapPosition(int selectViewPagerItem) {
        return drawables[selectViewPagerItem];
    }

}
