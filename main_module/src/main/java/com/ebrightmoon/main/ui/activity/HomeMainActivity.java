package com.ebrightmoon.main.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ebrightmoon.common.base.BaseActivity;
import com.ebrightmoon.common.util.StatusBarUtils;
import com.ebrightmoon.main.R;

public class HomeMainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private boolean isSearch;
    private boolean isShare;
    private View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        StatusBarUtils.immersive(mActivity);
    }


    @Override
    public void initData() {

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

//    private void initMagicIndicator4() {
//        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator4);
//        magicIndicator.setBackgroundColor(Color.parseColor("#455a64"));
//        CommonNavigator commonNavigator = new CommonNavigator(this);
//        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
//            @Override
//            public int getCount() {
//                return mDataList == null ? 0 : mDataList.size();
//            }
//
//            @Override
//            public IPagerTitleView getTitleView(Context context, final int index) {
//                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
//                simplePagerTitleView.setText(mDataList.get(index));
//                simplePagerTitleView.setNormalColor(Color.parseColor("#88ffffff"));
//                simplePagerTitleView.setSelectedColor(Color.WHITE);
//                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mViewPager.setCurrentItem(index);
//                    }
//                });
//                return simplePagerTitleView;
//            }
//
//            @Override
//            public IPagerIndicator getIndicator(Context context) {
//                LinePagerIndicator indicator = new LinePagerIndicator(context);
//                indicator.setColors(Color.parseColor("#40c4ff"));
//                return indicator;
//            }
//        });
//        magicIndicator.setNavigator(commonNavigator);
//        ViewPagerHelper.bind(magicIndicator, mViewPager);
//    }


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
            menu.getItem(R.id.action_search).setVisible(true);
        } else {
            menu.getItem(R.id.action_search).setVisible(false);
        }

        if (isShare) {
            menu.findItem(R.id.action_share).setVisible(true);
        } else {
            menu.findItem(R.id.action_share).setVisible(false);
        }
        // 调用  invalidateOptionsMenu(); 改变显示或者隐藏
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
