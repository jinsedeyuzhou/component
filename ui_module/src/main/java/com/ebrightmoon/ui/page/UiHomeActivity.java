package com.ebrightmoon.ui.page;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

/**
 * UI相关主页
 */

@Route(path = RouterURLS.UI_HOME)
public class UiHomeActivity extends BaseActivity {
    private ViewPager2 viewpager2;
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
        viewpager2 = findViewById(R.id.viewpager2);
        bottomNavigationView =  findViewById(R.id.bottom_navigation);

        tabTitles = new ArrayList();
        tabTitles.add("发现");
        tabTitles.add("消息");
        fragmentList = new ArrayList();
        fragmentList.add(new UiHomeFragment());
        fragmentList.add(new UiAccountFragment());


        newsAdapter = new NewsAdapter(getSupportFragmentManager(),getLifecycle(), fragmentList);
        viewpager2.setAdapter(newsAdapter);
//        viewpager2.setOffscreenPageLimit(4);
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
//        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if (item.getItemId() == R.id.navigation_practice) {
                            viewpager2.setCurrentItem(0);
                        } else if (item.getItemId() == R.id.navigation_home) {
                            viewpager2.setCurrentItem(1);
                        }

                        return false;
                    }
                });


        viewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
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
                super.onPageScrollStateChanged(state);
            }
        });

        viewpager2.setUserInputEnabled(false);



    }


    @Override
    protected void bindEvent() {

    }

    @Override
    public void processClick(View paramView) {

    }
}
