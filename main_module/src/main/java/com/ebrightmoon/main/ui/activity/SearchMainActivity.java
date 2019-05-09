package com.ebrightmoon.main.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ebrightmoon.common.adapter.recycler.absrecyclerview.MultiItemTypeAdapter;
import com.ebrightmoon.common.base.BaseActivity;
import com.ebrightmoon.common.view.flowlayout.FlowLayout;
import com.ebrightmoon.common.view.flowlayout.TagAdapter;
import com.ebrightmoon.common.view.flowlayout.TagFlowLayout;
import com.ebrightmoon.data.dao.DbHelper;
import com.ebrightmoon.data.gen.SearchHistoryDao;
import com.ebrightmoon.data.pojo.SearchHistory;
import com.ebrightmoon.main.R;
import com.ebrightmoon.main.adapter.NewsFeedAdapter;
import entity.NewsFeed;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：create by  Administrator on 2018/9/12
 * 邮箱：2315813288@qq.com
 */
public class SearchMainActivity extends BaseActivity {

    private RecyclerView mSearchRecyclerView;
    private LinearLayout mTagFlowLayout;
    private ImageView mIvDelete;
    private TagFlowLayout mHisFlowLayout;
    private TagFlowLayout mHotFlowLayout;
    private LinearLayout mLlHistory;
    private List<NewsFeed> newsFeedList = new ArrayList<>();
    private NewsFeedAdapter mRecyclerViewAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<String> historys = new ArrayList<>();
    private List<String> hots = new ArrayList<>();
    private RelativeLayout mRlHot;
    private TagAdapter mHotAdapter;
    private TagAdapter mHisAdapter;


    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main_search);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

        mSearchRecyclerView = findViewById(R.id.search_recyclerView);
        mTagFlowLayout = findViewById(R.id.ll_tag_flow_layout);

        mRecyclerViewAdapter = new NewsFeedAdapter(mContext, newsFeedList);
        mSearchRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mLayoutManager = new LinearLayoutManager(mContext);
        mSearchRecyclerView.setLayoutManager(mLayoutManager);

        mSearchRecyclerView.setAdapter(mRecyclerViewAdapter);
        initHisTag();
        initHotTag();
    }

    /**
     * 初始化热门标签
     */
    private void initHotTag() {
        //删除历史记录
        mIvDelete = findViewById(R.id.delete);
        //历史记录标签
        mHisFlowLayout = findViewById(R.id.his_flowLayout);
        mLlHistory = findViewById(R.id.ll_history);
        List<SearchHistory> searchHistories = DbHelper.getInstance().history().loadAll();
        if (searchHistories.size() > 0) {
            mLlHistory.setVisibility(View.VISIBLE);
            for (SearchHistory searchHistory : searchHistories) {
                historys.add(searchHistory.getName());
            }
        } else {
            mLlHistory.setVisibility(View.GONE);
        }

        mHisAdapter = new TagAdapter<String>(historys) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView his = (TextView) LayoutInflater.from(mContext).inflate(R.layout.layout_tag,
                        mHisFlowLayout, false);
                his.setText(s);
                return his;
            }
        };

        mHisFlowLayout.setAdapter(mHisAdapter);

    }

    /**
     * 初始化历史标签
     */
    private void initHisTag() {
        //热门标签
        mHotFlowLayout = findViewById(R.id.hot_flowLayout);
        mRlHot = findViewById(R.id.rl_hot);
        hots.add("阿里云");
        hots.add("淘宝");
        hots.add("欧洲");
        hots.add("资讯");
        mHotAdapter = new TagAdapter<String>(hots) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView hot = (TextView) LayoutInflater.from(mContext).inflate(R.layout.layout_tag,
                        mHotFlowLayout, false);
                hot.setText(s);
                return hot;
            }
        };

        mHotFlowLayout.setAdapter(mHotAdapter);

    }

    @Override
    protected void bindEvent() {

        mIvDelete.setOnClickListener(this);
        mRecyclerViewAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


        mHisFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                return true;
            }
        });

        mHotFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                return true;
            }
        });
    }

    @Override
    public void processClick(View paramView) {
        if (paramView.getId()==R.id.delete) {
            DbHelper.getInstance().history().deleteAll();
            historys.clear();
            mHisAdapter.notifyDataChanged();
            mLlHistory.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.search_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        //设置搜索栏的默认提示
        searchView.setQueryHint("请搜索主题或者标题");

        //默认刚进去就打开搜索栏
        searchView.setIconified(false);
        //设置输入文本的EditText
        SearchView.SearchAutoComplete et = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        //设置搜索栏的默认提示，作用和setQueryHint相同
//        et.setHint("输入商品名或首字母");
        //设置提示文本的颜色
        et.setHintTextColor(Color.parseColor("#DC8B8B"));
        //设置输入文本的颜色
        et.setTextColor(Color.WHITE);
        // 当展开无输入内容的时候，没有关闭的图标
        searchView.onActionViewExpanded();
        //设置提交按钮是否可见
        searchView.setSubmitButtonEnabled(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(mContext, "您输入的文本为" + query, Toast.LENGTH_SHORT).show();
                if (!TextUtils.isEmpty(query))
                {
                    SearchHistory unique = DbHelper.getInstance().history().queryBuilder().where(SearchHistoryDao.Properties.Name.eq(query)).unique();
                    if (unique == null) {
                        DbHelper.getInstance().history().insert(new SearchHistory(null, query));
                        historys.add(query);
                        mHisAdapter.notifyDataChanged();
                        if (mLlHistory.getVisibility()==View.GONE)
                            mLlHistory.setVisibility(View.VISIBLE);
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText))
                {
                    mTagFlowLayout.setVisibility(View.GONE);
                }else
                {
                    mTagFlowLayout.setVisibility(View.VISIBLE);
                }

                return false;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
