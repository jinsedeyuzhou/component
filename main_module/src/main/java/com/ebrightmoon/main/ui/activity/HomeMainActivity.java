package com.ebrightmoon.main.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ebrightmoon.common.base.BaseActivity;
import com.ebrightmoon.common.base.BaseFragment;
import com.ebrightmoon.common.util.DensityUtils;
import com.ebrightmoon.common.util.StatusBarUtils;

import com.ebrightmoon.main.R;
import com.ebrightmoon.main.adapter.NewsChannelAdapter;
import com.ebrightmoon.main.entity.Channel;
import com.ebrightmoon.main.ui.fragment.NewsMainFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;

public class HomeMainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ArrayList<BaseFragment> mFragments;
    public ArrayList<Channel> mSelectedDatas ;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private boolean isSearch=true;
    private boolean isShare=true;
    private View headerView;
    private MagicIndicator mainHomeIndicator;
    private ViewPager mainHomeViewPager;
    private NewsChannelAdapter newsChannelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        StatusBarUtils.immersive(mActivity);
    }


    @Override
    public void initData() {
        mSelectedDatas = new ArrayList<>();
        mSelectedDatas.add(new Channel(1,"新闻",1));
        mSelectedDatas.add(new Channel(2,"视频",2));
        mSelectedDatas.add(new Channel(3,"热点",3));
        mSelectedDatas.add(new Channel(4,"体育",4));
        mSelectedDatas.add(new Channel(5,"文化",5));
        mSelectedDatas.add(new Channel(2,"视频",2));
        mSelectedDatas.add(new Channel(3,"热点",3));
        mSelectedDatas.add(new Channel(4,"体育",4));
        mSelectedDatas.add(new Channel(5,"文化",5));
        mSelectedDatas.add(new Channel(6,"多媒体",6));

        mFragments = new ArrayList<>();
        for (int i=0;i<mSelectedDatas.size();i++)
        {
            NewsMainFragment fragment = NewsMainFragment.newInstance(mSelectedDatas.get(i));
            mFragments.add(fragment);
        }
        newsChannelAdapter = new NewsChannelAdapter(mContext,getSupportFragmentManager(),mFragments,mSelectedDatas);
        mainHomeViewPager.setAdapter(newsChannelAdapter);

        initMagicIndicator();

    }

    @Override
    public void initView() {
        initToolbar();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initNavigationView();

        //左右滑动
        mainHomeIndicator = findViewById(R.id.main_home_indicator);
        mainHomeViewPager = findViewById(R.id.main_home_viewpager);

    }

    /**
     * 初始化右侧布局
     */
    private void initNavigationView() {
        headerView = navigationView.getHeaderView(0);
    }

    /**
     * 初始化titlebar
     */
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initMagicIndicator() {
//        mainHomeIndicator.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mSelectedDatas == null ? 0 : mSelectedDatas.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mSelectedDatas.get(index).getName());
                simplePagerTitleView.setTextSize(DensityUtils.px2dp(mContext,48));
                simplePagerTitleView.setNormalColor(mContext.getResources().getColor(R.color.transparentTitle));
                simplePagerTitleView.setSelectedColor(Color.WHITE);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mainHomeViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(Color.WHITE);
                return indicator;
            }
        });
        mainHomeIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mainHomeIndicator, mainHomeViewPager);
    }


    @Override
    protected void bindEvent() {

    }

    @Override
    public void processClick(View paramView) {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isSearch) {
            menu.findItem(R.id.action_search).setVisible(true);
        } else {
            menu.findItem(R.id.action_search).setVisible(false);
        }

        if (isShare) {
            menu.findItem(R.id.action_share).setVisible(true);
        } else {
            menu.findItem(R.id.action_share).setVisible(false);
        }
//         调用  invalidateOptionsMenu(); 改变显示或者隐藏
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

            return true;
        } else if (id == R.id.action_share) {

            return true;
        } else if (id == R.id.action_settings) {
            toOtherActivity(SettingActivity.class,null,false);
            return true;
        } else if (id == R.id.action_about) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
