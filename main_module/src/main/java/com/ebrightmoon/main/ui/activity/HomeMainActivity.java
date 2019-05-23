package com.ebrightmoon.main.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.ebrightmoon.common.base.BaseActivity;
import com.ebrightmoon.common.base.BaseFragment;
import com.ebrightmoon.common.util.DensityUtils;
import com.ebrightmoon.common.util.StatusBarUtils;

import com.ebrightmoon.main.R;
import com.ebrightmoon.main.adapter.NewsChannelAdapter;
import com.ebrightmoon.main.arouter.RouterCenter;
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
    public ArrayList<Channel> mSelectedDatas;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private boolean isSearch = true;
    private boolean isShare = true;
    private View headerView;
    private MagicIndicator mainHomeIndicator;
    private ViewPager mainHomeViewPager;
    private NewsChannelAdapter newsChannelAdapter;
    private CommonNavigator commonNavigator;
    private LinePagerIndicator indicator;
    private static boolean isExit = false;
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        StatusBarUtils.immersive(mActivity);
    }


    @Override
    public void initData() {
        mSelectedDatas = new ArrayList<>();
        mSelectedDatas.add(new Channel(1, "新闻", 1));
        mSelectedDatas.add(new Channel(2, "视频", 2));
        mSelectedDatas.add(new Channel(3, "热点", 3));
        mSelectedDatas.add(new Channel(4, "体育", 4));
        mSelectedDatas.add(new Channel(5, "文化", 5));
        mSelectedDatas.add(new Channel(2, "视频", 2));
        mSelectedDatas.add(new Channel(3, "热点", 3));
        mSelectedDatas.add(new Channel(4, "体育", 4));
        mSelectedDatas.add(new Channel(5, "文化", 5));
        mSelectedDatas.add(new Channel(6, "多媒体", 6));

        mFragments = new ArrayList<>();
        for (int i = 0; i < mSelectedDatas.size(); i++) {
            NewsMainFragment fragment = NewsMainFragment.newInstance(mSelectedDatas.get(i));
            mFragments.add(fragment);
        }
        newsChannelAdapter = new NewsChannelAdapter(mContext, getSupportFragmentManager(), mFragments, mSelectedDatas);
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
        commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mSelectedDatas == null ? 0 : mSelectedDatas.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mSelectedDatas.get(index).getName());
                simplePagerTitleView.setTextSize(DensityUtils.px2dp(mContext, 48));
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
                indicator = new LinePagerIndicator(context);
                indicator.setColors(Color.WHITE);
                return indicator;
            }
        });
        mainHomeIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mainHomeIndicator, mainHomeViewPager);
//        changeTopBgColor(0);
    }


    @Override
    protected void bindEvent() {
        mainHomeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                changeTopBgColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
            toOtherActivity(SearchMainActivity.class,null,false);
            return true;
        } else if (id == R.id.action_share) {
            RouterCenter.toLogin();
            return true;
        } else if (id == R.id.action_settings) {
            RouterCenter.toSetting();
            return true;
        } else if (id == R.id.action_about) {
            RouterCenter.toRegister();
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

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            exit();
//            return false;
//        }
//        return super.onKeyDown(keyCode, event);
//    }


    /**
     * 按两次退出
     */
    private void exit() {
        if (!isExit) {
            isExit = true;

            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }
//======================================================================

    /**
     * 根据Palette提取的颜色，修改tab和toolbar以及状态栏的颜色
     */
    private void changeTopBgColor(int position) {
        // 用来提取颜色的Bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), NewsMainFragment.getBackgroundBitmapPosition(position % 5));
        // Palette的部分
        Palette.Builder builder = Palette.from(bitmap);
        builder.generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                //获取到充满活力的这种色调
                Palette.Swatch vibrant = palette.getVibrantSwatch();
                //根据调色板Palette获取到图片中的颜色设置到toolbar和tab中背景，标题等，使整个UI界面颜色统一
                mainHomeIndicator.setBackgroundColor(vibrant.getRgb());
                toolbar.setBackgroundColor(vibrant.getRgb());
                if (android.os.Build.VERSION.SDK_INT >= 21) {
                    Window window = getWindow();
                    window.setStatusBarColor(colorBurn(vibrant.getRgb()));
                    window.setNavigationBarColor(colorBurn(vibrant.getRgb()));
                }
            }
        });
    }

    /**
     * 颜色加深处理
     *
     * @param RGBValues RGB的值，由alpha（透明度）、red（红）、green（绿）、blue（蓝）构成，
     *                  Android中我们一般使用它的16进制，
     *                  例如："#FFAABBCC",最左边到最右每两个字母就是代表alpha（透明度）、
     *                  red（红）、green（绿）、blue（蓝）。每种颜色值占一个字节(8位)，值域0~255
     *                  所以下面使用移位的方法可以得到每种颜色的值，然后每种颜色值减小一下，在合成RGB颜色，颜色就会看起来深一些了
     * @return
     */
    private int colorBurn(int RGBValues) {
        int alpha = RGBValues >> 24;
        int red = RGBValues >> 16 & 0xFF;
        int green = RGBValues >> 8 & 0xFF;
        int blue = RGBValues & 0xFF;
        red = (int) Math.floor(red * (1 - 0.1));
        green = (int) Math.floor(green * (1 - 0.1));
        blue = (int) Math.floor(blue * (1 - 0.1));
        return Color.rgb(red, green, blue);
    }
}
