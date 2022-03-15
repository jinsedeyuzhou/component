package com.ebrightmoon.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ebrightmoon.common.base.mvc.BaseActivity;
import com.ebrightmoon.main.arouter.RouterCenter;
import com.ebrightmoon.main.ui.activity.HomeMainActivity;
import com.ebrightmoon.main.ui.activity.TestActivity;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        DbHelper.getInstance().init(this.getApplicationContext());

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

        findViewById(R.id.tv_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, TestActivity.class));
            }
        });
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterCenter.toLogin();
            }
        });

//        findViewById(R.id.tv_login,new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }
    @Override
    protected void bindEvent() {

    }

    @Override
    public void processClick(View paramView) {

    }


}
