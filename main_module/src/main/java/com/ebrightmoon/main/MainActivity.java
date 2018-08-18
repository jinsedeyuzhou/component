package com.ebrightmoon.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ebrightmoon.common.base.BaseActivity;
import com.ebrightmoon.main.arouter.RouterCenter;
import com.ebrightmoon.main.gen.DbHelper;
import com.ebrightmoon.main.ui.activity.HomeMainActivity;

public class MainActivity extends BaseActivity {

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DbHelper.getInstance().init(this.getApplicationContext());

        findViewById(R.id.tv_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, HomeMainActivity.class));
            }
        });
        findViewById(R.id.tv_ui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouterCenter.toUiHome();
            }
        });
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public void processClick(View paramView) {

    }


}
