package com.ebrightmoon.ui.page;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ebrightmoon.common.base.BaseActivity;
import com.ebrightmoon.common.widget.CustomViewPager;
import com.ebrightmoon.data.router.RouterURLS;
import com.ebrightmoon.ui.R;
import com.ebrightmoon.ui.adapter.NewsAdapter;
import com.ebrightmoon.ui.page.fragment.UiAccountFragment;
import com.ebrightmoon.ui.page.fragment.UiHomeFragment;
import com.ebrightmoon.ui.utils.BottomNavigationViewHelper;

import java.util.ArrayList;

import kotlin.jvm.JvmField;

/**
 * UI相关主页
 */

@Route(path = RouterURLS.UI_HOME)
public class UiHomeActivity extends BaseActivity {
    private CustomViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private MenuItem menuItem;
    private ArrayList tabTitles;
    private ArrayList fragmentList;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_activity_home);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        tabTitles = new ArrayList();
        tabTitles.add("发现");
        tabTitles.add("消息");
        fragmentList = new ArrayList();
        fragmentList.add(new UiHomeFragment());
        fragmentList.add(new UiAccountFragment());


        newsAdapter = new NewsAdapter(getSupportFragmentManager(), fragmentList, tabTitles);
        viewPager.setAdapter(newsAdapter);
//        viewPager.setCurrentItem(3);
        viewPager.setScrollable(true);
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if (item.getItemId() == R.id.navigation_practice) {
                            viewPager.setCurrentItem(0);
                        } else if (item.getItemId() == R.id.navigation_home) {
                            viewPager.setCurrentItem(1);
                        }

                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()

        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //禁止ViewPager滑动
//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });

    }


    @Override
    protected void bindEvent() {

    }

    @Override
    public void processClick(View paramView) {

    }
}
