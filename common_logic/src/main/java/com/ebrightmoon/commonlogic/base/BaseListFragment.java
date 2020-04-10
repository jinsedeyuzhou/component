package com.ebrightmoon.commonlogic.base;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ebrightmoon.common.adapter.recycler.absrecyclerview.EmptyWrapper;
import com.ebrightmoon.common.adapter.recycler.absrecyclerview.HeaderAndFooterWrapper;
import com.ebrightmoon.common.adapter.recycler.absrecyclerview.LoadMoreWrapper;
import com.ebrightmoon.common.adapter.recycler.absrecyclerview.MultiItemTypeAdapter;
import com.ebrightmoon.common.base.BaseFragment;
import com.ebrightmoon.common.util.Tools;
import com.ebrightmoon.commonlogic.R;
import com.ebrightmoon.commonlogic.widget.LoadMoreView;
import com.ebrightmoon.commonlogic.widget.SimpleMultiStateView;



public abstract class BaseListFragment<T, V extends RecyclerView.LayoutManager, S extends MultiItemTypeAdapter<T>> extends BaseFragment {

    protected SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    protected SimpleMultiStateView multiState;
    private S recyclerAdapter;
    // 是否可以刷新
    private boolean isCanRresh = false;
    private V layoutManager;
    private EmptyWrapper mEmptyWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;
    private View headerView;
    private View footerView;
    protected LoadMoreView loadMoreView;
    protected int curPage = 1;
    protected int PAGE_SIZE = 15;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_base_list;
    }

    @Override
    protected void initView(View view) {
        initTitleBar(view);
        swipeRefreshLayout = view.findViewById(R.id.swipe);
        recyclerView = view.findViewById(R.id.recyclerView);


        layoutManager = createLayoutManager();
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = createAdapter();
        initEmptyView();
        initHeaderAndFooter();

        loadMoreView = new LoadMoreView(mContext);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadMoreView.setLayoutParams(layoutParams);
        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);
        mLoadMoreWrapper.setLoadMoreView(loadMoreView);
        recyclerView.setAdapter(mLoadMoreWrapper);
    }

    /**
     * 无数据布局
     */
    private void initEmptyView() {
        mEmptyWrapper = new EmptyWrapper(recyclerAdapter);
        multiState = new SimpleMultiStateView(mContext);
        mEmptyWrapper.setEmptyView(multiState);

    }

    /**
     * 初始化头布局
     */
    private void initHeaderAndFooter() {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mEmptyWrapper);
        if (headerView != null) {
            mHeaderAndFooterWrapper.addHeaderView(headerView);
        }
        if (footerView != null) {
            mHeaderAndFooterWrapper.addFootView(footerView);
        }
        recyclerView.setAdapter(mHeaderAndFooterWrapper);
    }


    protected abstract S createAdapter();

    protected abstract V createLayoutManager();

    /**
     * 请求数据
     */
    protected abstract  void getData() ;

    /**
     * 设置是否可以刷新
     *
     * @param canRresh
     */
    public void setCanRresh(boolean canRresh) {
        isCanRresh = canRresh;
    }

    /**
     * 设置头布局
     *
     * @param headerView
     */
    public void setHeaderView(View headerView) {
        this.headerView = headerView;
    }


    /**
     * 设置底部
     *
     * @param footerView
     */
    public void setFooterView(View footerView) {
        this.footerView = footerView;
    }

    /**
     * 初始化title
     */
    private void initTitleBar(View view) {


    }

    @Override
    protected void bindEvent() {

        if (isCanRresh) {
            swipeRefreshLayout.setRefreshing(true);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    curPage=1;
                    loadMoreView.startLoading();
                    getData();
                }
            });
            swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                    android.R.color.holo_orange_light, android.R.color.holo_red_light);
            swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (Tools.isVisBottom(recyclerView)) {
                    loadMoreView.startLoading();
                    swipeRefreshLayout.setRefreshing(false);
//                    mLoadMoreWrapper.notifyDataSetChanged();
                    curPage++;
                    getData();
                } else {
                    loadMoreView.hideLoading();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        recyclerAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                itemClick(view, holder, position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


    }

    /**
     * 点击事件
     *
     * @param view
     * @param holder
     * @param position
     */
    protected void itemClick(View view, RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void processClick(View v) {

    }
}
